package net.fangyi.sauerkrautmagicmod.mixin;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.item.custom.RubySwordItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class UseItemMixin {
    @Shadow public abstract ItemStack getUseItem();

    @Shadow public abstract InteractionHand getUsedItemHand();

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;updatingUsingItem()V"))
    public void tick(CallbackInfo ci) {
        if(!(this.getUseItem().getItem() instanceof AirItem))
        {
            //SauerkrautMagicMod.LOGGER.info("getUseItem" + this.getUseItem().toString());
            //SauerkrautMagicMod.LOGGER.info("getUsedItemHand" + this.getUsedItemHand().toString());
        }

    }
}
