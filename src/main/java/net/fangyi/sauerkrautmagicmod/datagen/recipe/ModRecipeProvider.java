package net.fangyi.sauerkrautmagicmod.datagen.recipe;

import com.google.common.collect.ImmutableList;
import net.fangyi.sauerkrautmagicmod.block.ModBlocks;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    public static final ImmutableList<ItemLike> RUBY_SMELTABLES = ImmutableList.of(ModBlocks.RUBY_ORE.asItem());


    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        // 有序合成
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.RUBY_PICKAXE.get())
                .define('a',ModItems.RUBY.get())
                .define('b', Items.STICK)
                .pattern("aaa")
                .pattern(" b ")
                .pattern(" b ")
                .unlockedBy("has_ruby",has(ModItems.RUBY.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.RUBY_SWORD.get())
                .define('a',ModItems.RUBY.get())
                .define('b', Items.STICK)
                .pattern("a")
                .pattern("a")
                .pattern("b")
                .unlockedBy("has_ruby",has(ModItems.RUBY.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.RUBY_APPLE)
                .define('#', ModItems.RUBY.get())
                .define('X', Items.APPLE)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy("has_ruby",has(ModItems.RUBY.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.RUBY_HELMET.get())
                .define('X', ModItems.RUBY.get())
                .pattern("XXX")
                .pattern("X X")
                .unlockedBy("has_ruby",has(ModItems.RUBY.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.RUBY_CHESTPLATE.get())
                .define('X', ModItems.RUBY.get())
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_ruby",has(ModItems.RUBY.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.RUBY_BOOTS.get())
                .define('X', ModItems.RUBY.get())
                .pattern("X X")
                .pattern("X X")
                .unlockedBy("has_ruby",has(ModItems.RUBY.get()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.RUBY_LEGGINGS.get())
                .define('X', ModItems.RUBY.get())
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .unlockedBy("has_ruby",has(ModItems.RUBY.get()))
                .save(pRecipeOutput);
        // 无序合成
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.MAGIC_INGOT.get())
                .requires(Items.IRON_INGOT,3)
                .unlockedBy("has_iron_ingot",has(Items.IRON_INGOT))
                .save(pRecipeOutput);

        //九格合成
        nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.MISC, ModItems.RUBY, RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBY_BLOCK);

        // 冶炼
        oreSmelting(pRecipeOutput, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY.get(), 1.0F, 200, "ruby");
        oreBlasting(pRecipeOutput, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY.get(), 1.0F, 100, "ruby");
        // 切割石头
//        SingleItemRecipeBuilder.stonecutting()
    }
}
