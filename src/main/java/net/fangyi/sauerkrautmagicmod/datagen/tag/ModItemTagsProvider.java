package net.fangyi.sauerkrautmagicmod.datagen.tag;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, SauerkrautMagicMod.MODID, existingFileHelper);
    }

    // static final TagKey<Item> BLOCK_AND_CHAIN_ENCHANTABLE = create("enchantable/block_and_chain");

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //this.tag(BLOCK_AND_CHAIN_ENCHANTABLE).add(ModItems.BLOCK_AND_CHAIN.get());


        tag(ItemTags.SWORDS)
                .add(ModItems.RUBY_SWORD.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.RUBY_PICKAXE.get());

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.RUBY_HELMET.get())
                .add(ModItems.RUBY_CHESTPLATE.get())
                .add(ModItems.RUBY_LEGGINGS.get())
                .add(ModItems.RUBY_BOOTS.get());
    }

    public static TagKey<Item> create(String tagName) {
        return ItemTags.create(SauerkrautMagicMod.prefix(tagName));
    }
}
