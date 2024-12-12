package net.fangyi.sauerkrautmagicmod.block.custom;

import net.fangyi.sauerkrautmagicmod.level.ModLevelSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class DataSaveBlock extends Block {
    public DataSaveBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if(!pLevel.isClientSide){
            ModLevelSaveData data  = ModLevelSaveData.get(pLevel);
            ItemStack mainHandItem = pPlayer.getMainHandItem();
            if(mainHandItem.isEmpty()){
                ItemStack itemStack = data.getItemStack();
                pPlayer.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
            }else{
                ItemStack itemStack = mainHandItem.copy();
                mainHandItem.shrink(mainHandItem.getCount());
                data.putItemStack(itemStack);
                //InitTrigger.GIVE_RUBY_APPLE.get().trigger((ServerPlayer) pPlayer);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {

        return InteractionResult.SUCCESS;
    }
}
