package net.fangyi.sauerkrautmagicmod.mixin;

import com.mojang.authlib.GameProfile;
import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.block.ModBlocks;
import net.fangyi.sauerkrautmagicmod.entity.ModEntityTypes;
import net.fangyi.sauerkrautmagicmod.entity.custom.FlyingSwordEntity;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;


public class SwordFlyingMixin {
    @Mixin(SwordItem.class)
    public abstract static class SwordItemMixin extends Item {
        public SwordItemMixin(Properties properties) {
            super(properties);
        }

        @Override
        public InteractionResult useOn(UseOnContext context) {
//            if(context.getItemInHand().getItem() == Items.DIAMOND_SWORD){
//                ModBlocks.SWORD_BLOCK_ITEM.get().useOn(context);
//                return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
//            }
            if(context.getItemInHand().getItem() == Items.DIAMOND_SWORD){
                    return spawnFlyingSword(context);
            }
            return InteractionResult.PASS;
        }

        private InteractionResult spawnFlyingSword(UseOnContext context) {
            Level level = context.getLevel();
            if (!(level instanceof ServerLevel)) {
                return InteractionResult.SUCCESS;
            } else {
                ItemStack itemstack = context.getItemInHand();
                BlockPos blockpos = context.getClickedPos();
                Direction direction = context.getClickedFace();
                BlockState blockstate = level.getBlockState(blockpos);
                if (level.getBlockEntity(blockpos) instanceof Spawner spawner) {
                    EntityType<FlyingSwordEntity> entitytype1 = ModEntityTypes.FLYING_SWORD.get();
                    spawner.setEntityId(entitytype1, level.getRandom());
                    level.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
                    level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockpos);
                    itemstack.shrink(1);
                    return InteractionResult.CONSUME;
                } else {
                    BlockPos blockpos1;
                    if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                        blockpos1 = blockpos;
                    } else {
                        blockpos1 = blockpos.relative(direction);
                    }

                    EntityType<FlyingSwordEntity> entitytype = ModEntityTypes.FLYING_SWORD.get();
                    if (entitytype.spawn(
                            (ServerLevel)level,
                            itemstack,
                            context.getPlayer(),
                            blockpos1,
                            MobSpawnType.SPAWN_EGG,
                            true,
                            !Objects.equals(blockpos, blockpos1) && direction == Direction.UP
                    )
                            != null) {
                        FlyingSwordEntity flyingSwordEntity = new FlyingSwordEntity(entitytype, level);
                        flyingSwordEntity.setItemStack(itemstack);
                        itemstack.shrink(1);
                        level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);
                    }

                    return InteractionResult.CONSUME;
                }
            }
        }
    }

    @Mixin(LocalPlayer.class)
    public abstract static class LocalPlayerMixin extends Player {

        @Shadow public Input input;

        @Shadow private boolean handsBusy;

        @Shadow private boolean wasFallFlying;

        public LocalPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
            super(level, pos, yRot, gameProfile);
        }

        @Override
        public void rideTick() {
            super.rideTick();
            if (this.wantsToStopRiding() && this.isPassenger()) this.input.shiftKeyDown = false;
            this.handsBusy = false;
            if (this.getControlledVehicle() instanceof Boat boat) {
                boat.setInput(this.input.left, this.input.right, this.input.up, this.input.down);
                this.handsBusy = this.handsBusy | (this.input.left || this.input.right || this.input.up || this.input.down);
            }
//            if (this.getControlledVehicle() instanceof FlyingSwordEntity sword) {
//                sword.setInput(this.input.left, this.input.right, this.input.up, this.input.down, this.input.jumping, this.input.shiftKeyDown);
//            }
        }
    }
}
