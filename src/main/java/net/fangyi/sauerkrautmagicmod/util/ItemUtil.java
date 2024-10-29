package net.fangyi.sauerkrautmagicmod.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ItemUtil {
    private ItemUtil() {
    }
    public static void damage(ItemStack itemstack, int amount, @Nullable LivingEntity entity) {
        if (!entity.hasInfiniteMaterials()) {
            int i = itemstack.getDamageValue() + amount;
            itemstack.setDamageValue(i);
        }
    }
}
