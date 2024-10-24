package net.fangyi.sauerkrautmagicmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GlassJarBlock extends Block {
    public GlassJarBlock() {
        super(Properties
                .ofFullCopy(Blocks.GLASS)
                .strength(5f)
                .noOcclusion()
        );
    }

    public static final VoxelShape SHAPE = Shapes.or(Block.box(5,0,5,11,7,11), Block.box(6,7,6,10,8,10));

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
