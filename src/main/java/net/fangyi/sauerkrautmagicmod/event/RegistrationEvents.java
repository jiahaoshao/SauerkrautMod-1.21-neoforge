package net.fangyi.sauerkrautmagicmod.event;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.client.hud.ExampleHud;
import net.fangyi.sauerkrautmagicmod.client.render.entity.FlyingSwordEntityRenderer;
import net.fangyi.sauerkrautmagicmod.entity.model.FlyingSwordModel;
import net.fangyi.sauerkrautmagicmod.entity.ModEntityTypes;
import net.fangyi.sauerkrautmagicmod.event.client.KeyBoardInput;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.fangyi.sauerkrautmagicmod.util.ModDataComponents;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

import java.util.ArrayList;
import java.util.List;

public class RegistrationEvents {

    @EventBusSubscriber(modid = SauerkrautMagicMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class GameEvents {

        @SubscribeEvent
        public static void onClientSetup(RegisterKeyMappingsEvent event) {
            event.register(KeyBoardInput.UP_KEY);
            event.register(KeyBoardInput.DOWN_KEY);
        }

        @SubscribeEvent
        public static void onClientEvent(FMLClientSetupEvent event){
            event.enqueueWork(()->{
                EntityRenderers.register(ModEntityTypes.FLYING_SWORD.get(), FlyingSwordEntityRenderer::new);
            });
        }

        @SubscribeEvent
        public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions evt) {
            evt.registerLayerDefinition(FlyingSwordModel.LAYER_LOCATION, FlyingSwordModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void bakeCustomModels(ModelEvent.ModifyBakingResult event){
            ItemProperties.register(ModItems.ICE_BOW.get(), ResourceLocation.parse("pull"), (stack, level, entity, idk) -> {
                if (entity == null) return 0.0F;
                else
                    return entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
            });

            ItemProperties.register(ModItems.ICE_BOW.get(), ResourceLocation.parse("pulling"), (stack, level, entity, idk) ->
                    entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);

            ItemProperties.register(ModItems.MAGIC_INGOT.get(), SauerkrautMagicMod.prefix("size"), (stack, level, entity, idk) ->
                        stack.getCount());

            //末影刀模型变化注册
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

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
            event.registerAboveAll(SauerkrautMagicMod.prefix("example_hud"), ExampleHud.getInstance());
        }


        private static void TpSwordModels(List<Item> items)
        {
            items.forEach(
                    item -> {
                        ItemProperties.register(item, SauerkrautMagicMod.prefix("can_tp"), (stack, level, entity, idk) -> CanTP(stack) ? 1.0F : 0.0F);
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
