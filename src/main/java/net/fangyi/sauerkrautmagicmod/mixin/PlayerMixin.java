package net.fangyi.sauerkrautmagicmod.mixin;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class PlayerMixin{
    @Shadow public abstract ItemStack getUseItem();

    @Shadow public abstract ItemStack getMainHandItem();

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        ItemStack useItem = this.getUseItem();
        ItemStack stack = this.getMainHandItem();
//        SauerkrautMagicMod.LOGGER.info(useItem.toString());
//        SauerkrautMagicMod.LOGGER.info(stack.toString());
    }
}
