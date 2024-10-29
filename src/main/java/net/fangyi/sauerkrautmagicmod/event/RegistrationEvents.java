package net.fangyi.sauerkrautmagicmod.event;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.fangyi.sauerkrautmagicmod.util.ModDataComponents;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

import java.util.ArrayList;
import java.util.List;

public class RegistrationEvents {

    @EventBusSubscriber(modid = SauerkrautMagicMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class GameEvents {
        @SubscribeEvent
        public static void bakeCustomModels(ModelEvent.ModifyBakingResult event){
            ItemProperties.register(ModItems.ICE_BOW.get(), ResourceLocation.parse("pull"), (stack, level, entity, idk) -> {
                if (entity == null) return 0.0F;
                else
                    return entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
            });

            ItemProperties.register(ModItems.ICE_BOW.get(), ResourceLocation.parse("pulling"), (stack, level, entity, idk) ->
                    entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);

            List<Item> TP_SWORD = new ArrayList<>();
            TP_SWORD.add(ModItems.RUBY_SWORD.get());
            TP_SWORD.add(Items.WOODEN_SWORD.asItem());
            TP_SWORD.add(Items.STONE_SWORD.asItem());
            TP_SWORD.add(Items.IRON_SWORD.asItem());
            TP_SWORD.add(Items.GOLDEN_SWORD.asItem());
            TP_SWORD.add(Items.DIAMOND_SWORD.asItem());
            TP_SWORD.add(Items.NETHERITE_SWORD.asItem());

            TpSwordModels(TP_SWORD);
        }

        private static void TpSwordModels(List<Item> items)
        {
            items.forEach(
                    item -> {
                        ItemProperties.register(item, SauerkrautMagicMod.prefix("can_tp"), (stack, level, entity, idk) ->{
                            return CanTP(stack) ? 1.0F : 0.0F;
                        });
                    }
            );
        }

        private static boolean CanTP(ItemStack stack) {
            return stack.has(ModDataComponents.CAN_TP);
        }
    }
//    @EventBusSubscriber(modid = SauerkrautMagicMod.MODID, bus = EventBusSubscriber.Bus.MOD)
//    public static class ModEvents {
//
//    }
}
