package net.fangyi.sauerkrautmagicmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RubyWandItem extends Item {
    public RubyWandItem() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        //检查当前的逻辑是否运行在服务端，因为客户端和服务端的逻辑是分开的
        if(!level.isClientSide){
            ItemStack stack = player.getMainHandItem();
            if(stack.getItem() instanceof RubyWandItem){
                Vec3 start = player.getEyePosition();
                Vec3 end = player.getLookAngle().normalize().scale(32f).add(start);
                //使用射线投射检测玩家视线方向上的方块碰撞，获取碰撞结果
                BlockHitResult blockHitResult = level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
                end = blockHitResult.getLocation();
                //创建一个以玩家为中心，向玩家视线方向扩展的轴对齐包围盒（AABB）
                AABB range = player.getBoundingBox().expandTowards(end.subtract(start));
                List<HitResult> hits = new ArrayList<>();
                List<? extends Entity> entities = level.getEntities(player, range, entity -> entity.isPickable() && entity.isAlive());
                for(var e : entities){
                    Vec3 vec3 = e.getBoundingBox().clip(start, end).orElse(null);
                    if(vec3 != null){
                        EntityHitResult entityHitResult = new EntityHitResult(e, vec3);
                        hits.add(entityHitResult);
                    }
                }
                if(!hits.isEmpty()){
                    hits.sort((o1, o2) -> o1.getLocation().distanceToSqr(start) < o2.getLocation().distanceToSqr(start) ? -1 : 1);
                    HitResult hitResult = hits.getFirst();
                    if(hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity livingEntity){
                        livingEntity.lavaHurt();
                    }
                }else{
                    BlockPos pos = blockHitResult.getBlockPos();
                    Set<BlockPos> posSet = new HashSet<>();
                    int x = stack.getCount() - 1;
                    if(blockHitResult.getDirection() == Direction.DOWN || blockHitResult.getDirection() == Direction.UP){
                        for(int i = -x; i <= x; i ++)
                        {
                            for(int j = -x; j <= x; j ++){
                                BlockPos pos1 = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j);
                                posSet.add(pos1);
                            }
                        }
                    }
                    if(blockHitResult.getDirection() == Direction.NORTH || blockHitResult.getDirection() == Direction.SOUTH){
                        for(int i = -x; i <= x; i ++)
                        {
                            for(int j = -x; j <= x; j ++){
                                BlockPos pos1 = new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ());
                                posSet.add(pos1);
                            }
                        }
                    }
                    if(blockHitResult.getDirection() == Direction.EAST || blockHitResult.getDirection() == Direction.WEST){
                        for(int i = -x; i <= x; i ++)
                        {
                            for(int j = -x; j <= x; j ++){
                                BlockPos pos1 = new BlockPos(pos.getX(), pos.getY() + i, pos.getZ() + j);
                                posSet.add(pos1);
                            }
                        }
                    }
                    for(BlockPos pos1 : posSet){
                        if(x > 8)
                        {
                            level.destroyBlock(pos1, false);
                        }
                        else{
                            level.destroyBlock(pos1, true);
                        }
                    }
                }
            }
        }
        return super.use(level, player, usedHand);
    }
}
