package net.fangyi.sauerkrautmagicmod.datagen.loot;

import net.fangyi.sauerkrautmagicmod.block.ModBlocks;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootProvider extends BlockLootSubProvider {
    public ModBlockLootProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
    }

    public static final Set<Block> BLOCK = Set.of(
            ModBlocks.RUBY_BLOCK.get()
    );

    @Override
    protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.BLOCKS.getEntries().stream().map(blockDeferredHolder -> blockDeferredHolder.get()).collect(Collectors.toSet());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.RUBY_BLOCK.get());
        this.dropSelf(ModBlocks.LAMP_BLOCK.get());
        this.dropSelf(ModBlocks.RUBY_FRAME.get());
        this.dropSelf(ModBlocks.GLASS_JAR.get());
        this.dropOther(ModBlocks.RUBY_ORE.get(), ModItems.RUBY);
        this.dropSelf(ModBlocks.OBSIDIAN_OBJ.get());
        this.dropSelf(ModBlocks.DATA_SAVE_BLOCK.get());
        this.dropSelf(ModBlocks.SWORD_BLOCK.get());
    }
}
