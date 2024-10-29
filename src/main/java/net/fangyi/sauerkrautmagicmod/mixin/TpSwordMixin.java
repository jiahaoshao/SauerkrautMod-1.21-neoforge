package net.fangyi.sauerkrautmagicmod.mixin;

import net.fangyi.sauerkrautmagicmod.enchatment.ModEnchantments;
import net.fangyi.sauerkrautmagicmod.util.ItemUtil;
import net.fangyi.sauerkrautmagicmod.util.ModDataComponents;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SwordItem.class)
public class TpSwordMixin extends Item {
    public TpSwordMixin(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        int can_tp_enchantmentLevel = itemstack.getEnchantmentLevel(level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(ModEnchantments.CAN_TP));
        if(can_tp_enchantmentLevel >= 1)
        {
            ItemStack itemstack1 = hasEnderpearl(player);
            if (!level.isClientSide) {
                if (!itemstack.has(ModDataComponents.CAN_TP)) {
                    if (itemstack1.isEmpty()) {
                        return InteractionResultHolder.fail(itemstack);
                    }
                    itemstack1.consume(1, player);
                    ItemUtil.damage(itemstack, 1, player);
                    itemstack.set(ModDataComponents.CAN_TP, 1);
                    return InteractionResultHolder.success(itemstack);
                } else if (itemstack.has(ModDataComponents.CAN_TP)) {
                    itemstack.remove(ModDataComponents.CAN_TP);

                    level.playSound(
                            null,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            SoundEvents.ENDER_PEARL_THROW,
                            SoundSource.NEUTRAL,
                            0.5F,
                            0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
                    );
                    player.getCooldowns().addCooldown(this, 20);
                    if (!level.isClientSide) {
                        ThrownEnderpearl thrownenderpearl = new ThrownEnderpearl(level, player);
                        //thrownenderpearl.setItem(itemstack);
                        thrownenderpearl.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                        level.addFreshEntity(thrownenderpearl);
                    }
                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                }
            }
        }
        return InteractionResultHolder.fail(itemstack);
    }



    @Unique
    private static ItemStack hasEnderpearl(Player player) {
        NonNullList<ItemStack> items = player.getInventory().items;
        ItemStack itemstack1 = items.get(0);
        for (ItemStack item : items) {
            if (item.getItem() instanceof EnderpearlItem) {
                itemstack1 = item;
                return itemstack1;
            }
        }
        return ItemStack.EMPTY;
    }
}