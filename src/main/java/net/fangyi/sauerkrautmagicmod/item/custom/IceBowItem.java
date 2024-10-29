package net.fangyi.sauerkrautmagicmod.item.custom;

import net.fangyi.sauerkrautmagicmod.item.custom.tool.ModToolMaterials;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

public class IceBowItem extends BowItem {

    public IceBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
        return new Arrow(arrow.level(), (LivingEntity) arrow.getOwner(), projectileStack.copyWithCount(1), weaponStack);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairWith) {
        return ModToolMaterials.RUBY.getRepairIngredient().test(repairWith);
    }
}
