package net.fangyi.sauerkrautmagicmod.item.custom;

import net.fangyi.sauerkrautmagicmod.item.custom.tool.ModTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

public class RubySwordItem extends SwordItem {
    public RubySwordItem() {
        super(ModTiers.RUBY, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.RUBY, 3, -2.4F)));
    }
}
