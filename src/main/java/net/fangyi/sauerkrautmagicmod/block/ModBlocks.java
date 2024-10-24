package net.fangyi.sauerkrautmagicmod.block;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.block.custom.*;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SauerkrautMagicMod.MODID);

    public static final DeferredBlock<RubyBlock> RUBY_BLOCK = registerBlockAndItem("ruby_block", () -> new RubyBlock(
            BlockBehaviour.Properties
                    .ofFullCopy(Blocks.STONE)
                    .strength(5f)
    ));
    public static final DeferredBlock<LampBlock> LAMP_BLOCK = registerBlockAndItem("lamp_block", () -> new LampBlock(
            BlockBehaviour.Properties
                    .ofFullCopy(Blocks.GLASS)
                    .lightLevel(state -> state.getValue(LampBlock.LIT) ? 15 : 0)
    ));
    public static final DeferredBlock<RubyFrameBlock> RUBY_FRAME = registerBlockAndItem("ruby_frame", RubyFrameBlock::new);
    public static final DeferredBlock<GlassJarBlock> GLASS_JAR = registerBlockAndItem("glass_jar", GlassJarBlock::new);
    public static final DeferredBlock<Block> RUBY_ORE = registerBlockAndItem("ruby_ore",()->new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)));
    public static final DeferredBlock<Block> OBSIDIAN_OBJ = registerBlockAndItem("obsidian_obj", ObsidianObj::new);

    public static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Supplier<T> Block){
        DeferredBlock<T> block = BLOCKS.register(name, Block);
        ModItems.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
