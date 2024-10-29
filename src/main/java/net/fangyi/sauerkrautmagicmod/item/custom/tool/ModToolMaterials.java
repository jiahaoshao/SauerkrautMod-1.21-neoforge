package net.fangyi.sauerkrautmagicmod.item.custom.tool;

import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolMaterials {
    public static final Tier RUBY = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2000, 25F, 10F, 30, () -> Ingredient.of(ModItems.RUBY.get()));
}
