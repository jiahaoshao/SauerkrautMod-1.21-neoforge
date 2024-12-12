package net.fangyi.sauerkrautmagicmod.item.custom;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.ArrayList;
import java.util.List;

import static net.fangyi.sauerkrautmagicmod.entity.ai.behavior.SonicBoomHandler.SonicBoom;

public class RubyItem extends Item {
    public RubyItem() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        super.use(level, player, usedHand);
        Vec3 vec3 = player.position().add(player.getAttachments().get(EntityAttachment.WARDEN_CHEST, 0, player.getYRot()));
        Vec3 vec31 = player.getLookAngle().normalize().scale(32f).add(vec3);;
        if(!level.isClientSide)
        {
            ServerLevel serverLevel = (ServerLevel) level;
            BlockHitResult blockHitResult = level.clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            vec31 = blockHitResult.getLocation();
            // 创建一个以玩家为中心，向玩家视线方向扩展的轴对齐包围盒（AABB）
            AABB range = player.getBoundingBox().expandTowards(vec31.subtract(vec3));
            List<HitResult> hits = new ArrayList<>();
            List<? extends Entity> entities = level.getEntities(player, range, entity -> entity.isPickable() && entity.isAlive());
            for(var e : entities){
                Vec3 vec34 = e.getBoundingBox().clip(vec3, vec31).orElse(null);
                if(vec34 != null){
                    EntityHitResult entityHitResult = new EntityHitResult(e, vec34);
                    hits.add(entityHitResult);
                }
            }

            if(!hits.isEmpty()){
                hits.sort((o1, o2) -> o1.getLocation().distanceToSqr(vec3) < o2.getLocation().distanceToSqr(vec3) ? -1 : 1);
                HitResult hitResult = hits.getFirst();
                if(hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity livingEntity){
                    SonicBoom(serverLevel, player, livingEntity);
                }
            } else {
                int i = 32 + 7;
                for(int j = 1; j < i; j ++) {
                    Vec3 vec32 = player.getLookAngle();
                    Vec3 vec33 = vec3.add(vec32.scale((double)j));
                    serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, vec33.x, vec33.y, vec33.z, 1, 0.0, 0.0, 0.0, 0.0);
                }
                SauerkrautMagicMod.LOGGER.info("No entity in range");
            }
        }
        player.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }


}
