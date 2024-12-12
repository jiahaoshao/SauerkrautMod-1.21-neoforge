package net.fangyi.sauerkrautmagicmod.mixin;

import net.fangyi.sauerkrautmagicmod.enchatment.ModEnchantments;
import net.fangyi.sauerkrautmagicmod.entity.ai.behavior.SonicBoomHandler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

import static net.minecraft.world.item.BowItem.getPowerForTime;

public class RapidShootMixin {
    @Mixin(BowItem.class)
    public abstract static class BowItemMixin extends ProjectileWeaponItem {

        public BowItemMixin(Properties properties) {
            super(properties);
        }


        @Override
        public Predicate<ItemStack> getAllSupportedProjectiles() {
            return ARROW_ONLY;
        }

        @Override
        public int getDefaultProjectileRange() {
            return 15;
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            ItemStack itemstack = player.getItemInHand(hand);
            boolean flag = !player.getProjectile(itemstack).isEmpty();

            InteractionResultHolder<ItemStack> ret = net.neoforged.neoforge.event.EventHooks.onArrowNock(itemstack, level, player, hand, flag);
            if (ret != null) return ret;

            if (!player.hasInfiniteMaterials() && !flag) {
                return InteractionResultHolder.fail(itemstack);
            } else {
                int i = itemstack.getEnchantmentLevel(level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(ModEnchantments.RAPID_SHOOT));
                if(i != 0){
                    this.releaseUsing(itemstack, level, player, 0);
                }else{
                    player.startUsingItem(hand);
                }
                return InteractionResultHolder.consume(itemstack);
            }
        }
    }

    @Mixin(CrossbowItem.class)
    public abstract static class CrossbowItemMixin extends ProjectileWeaponItem {

        @Shadow public abstract void performShooting(Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, float velocity, float inaccuracy, @org.jetbrains.annotations.Nullable LivingEntity target);

        @Shadow
        protected static float getShootingPower(ChargedProjectiles projectile) {
            return 0;
        }

        @Shadow private boolean startSoundPlayed;

        @Shadow private boolean midLoadSoundPlayed;


        public CrossbowItemMixin(Properties properties) {
            super(properties);
        }


        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            ItemStack itemstack = player.getItemInHand(hand);
            ChargedProjectiles chargedprojectiles = itemstack.get(DataComponents.CHARGED_PROJECTILES);
            if (chargedprojectiles != null && !chargedprojectiles.isEmpty()) {
                this.performShooting(level, player, hand, itemstack, getShootingPower(chargedprojectiles), 1.0F, null);
                return InteractionResultHolder.consume(itemstack);
            } else if (!player.getProjectile(itemstack).isEmpty()) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                int i = itemstack.getEnchantmentLevel(level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(ModEnchantments.RAPID_SHOOT));
                if(i != 0){
                    this.releaseUsing(itemstack, level, player, 0);
                }else{
                    player.startUsingItem(hand);
                }
                return InteractionResultHolder.consume(itemstack);
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
    }
}
