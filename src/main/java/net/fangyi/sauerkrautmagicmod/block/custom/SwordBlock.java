package net.fangyi.sauerkrautmagicmod.block.custom;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SwordBlock extends Block {
    public SwordBlock() {
        super(Properties
                .ofFullCopy(Blocks.GLASS)
                .strength(5f)
                .noOcclusion()
                .noCollission()
        );
    }

    public static final BooleanProperty SAT_IN = BooleanProperty.create("sat_in");
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,2,16);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
//        if(state.getValue(SAT_IN) || !level.getBlockState(pos.above()).isAir() || player.getVehicle() != null) {
//            return super.useWithoutItem(state, level, pos, player, hitResult);
//        }
//        if(!level.isClientSide) {
//
//        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
