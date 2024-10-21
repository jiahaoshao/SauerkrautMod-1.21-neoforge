package net.fangyi.sauerkrautmagicmod.item.custom;

import net.fangyi.sauerkrautmagicmod.item.custom.tool.ModTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SwordItem;

public class RubyPickaxeItem extends PickaxeItem {
    public RubyPickaxeItem() {
        super(ModTiers.RUBY, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.RUBY, 1.0F, -2.8F)));
    }
}
