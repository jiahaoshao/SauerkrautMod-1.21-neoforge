package net.fangyi.sauerkrautmagicmod.mixin;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.entity.ai.behavior.SonicBoomHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ProjectileWeaponItem.class)
public abstract class BowSonicBoomMixin {

    @Shadow protected abstract Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit);

    @Shadow protected abstract void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @org.jetbrains.annotations.Nullable LivingEntity target);

    @Shadow protected abstract int getDurabilityUse(ItemStack stack);

    /**实现有目标时放出音爆
     * @author
     * @reason
     */
    @Overwrite
    protected void shoot(
            ServerLevel level,
            LivingEntity shooter,
            InteractionHand hand,
            ItemStack weapon,
            List<ItemStack> projectileItems,
            float velocity,
            float inaccuracy,
            boolean isCrit,
            @Nullable LivingEntity target
    ) {
        float f = EnchantmentHelper.processProjectileSpread(level, weapon, shooter, 0.0F);
        float f1 = projectileItems.size() == 1 ? 0.0F : 2.0F * f / (float)(projectileItems.size() - 1);
        float f2 = (float)((projectileItems.size() - 1) % 2) * f1 / 2.0F;
        float f3 = 1.0F;

        for (int i = 0; i < projectileItems.size(); i++) {
            ItemStack itemstack = projectileItems.get(i);
            if (!itemstack.isEmpty()) {
                float f4 = f2 + f3 * (float)((i + 1) / 2) * f1;
                f3 = -f3;
               // SauerkrautMagicMod.LOGGER.info("HoverName: " + weapon.getHoverName() + "DisplayName: " + weapon.getDisplayName());
                if (target == null) {
                    target = SonicBoomHandler.getTarget(level, shooter);
                }
                SonicBoomHandler.SonicBoom(level, shooter, target);
//                if (target != null) {
//
//                }else {
//                    Projectile projectile = this.createProjectile(level, shooter, weapon, itemstack, isCrit);
//                    this.shootProjectile(shooter, projectile, i, velocity, inaccuracy, f4, target);
//                    level.addFreshEntity(projectile);
//                }
                weapon.hurtAndBreak(this.getDurabilityUse(itemstack), shooter, LivingEntity.getSlotForHand(hand));
                if (weapon.isEmpty()) {
                    break;
                }
            }
        }
    }
}
