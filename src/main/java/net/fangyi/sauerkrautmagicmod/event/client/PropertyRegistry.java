package net.fangyi.sauerkrautmagicmod.event.client;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = SauerkrautMagicMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PropertyRegistry {
    @SubscribeEvent
    public static void propertyOverrideRegistry(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(
                    ModItems.MAGIC_INGOT.get(),
                    ResourceLocation.fromNamespaceAndPath(SauerkrautMagicMod.MODID, "size"),
                    (itemStack, clientLevel, livingEntity, i) ->{
                        return itemStack.getCount();
                    });
        });
    }
}
