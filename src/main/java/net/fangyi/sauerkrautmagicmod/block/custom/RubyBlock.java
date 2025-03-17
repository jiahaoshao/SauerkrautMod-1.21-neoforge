package net.fangyi.sauerkrautmagicmod.block.custom;

import com.jcraft.jorbis.DspState;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;

public class RubyBlock extends FallingBlock {
    public static final MapCodec<RubyBlock> CODEC = simpleCodec(RubyBlock::new);
    public RubyBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }
}
