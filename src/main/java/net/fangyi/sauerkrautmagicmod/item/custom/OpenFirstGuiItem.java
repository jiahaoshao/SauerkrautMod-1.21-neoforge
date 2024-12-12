package net.fangyi.sauerkrautmagicmod.item.custom;

import net.fangyi.sauerkrautmagicmod.client.gui.GuiOpenWrapper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class OpenFirstGuiItem extends Item {
    public OpenFirstGuiItem() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pLevel.isClientSide){
            GuiOpenWrapper.openFirstGui();
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
