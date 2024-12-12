package net.fangyi.sauerkrautmagicmod;

import com.mojang.logging.LogUtils;
import net.fangyi.sauerkrautmagicmod.block.ModBlocks;
import net.fangyi.sauerkrautmagicmod.effect.ModMobEffects;
import net.fangyi.sauerkrautmagicmod.enchatment.ModEnchantmentEntityEffect;
import net.fangyi.sauerkrautmagicmod.enchatment.ModEnchantmentValueEffect;
import net.fangyi.sauerkrautmagicmod.entity.ModEntityTypes;
import net.fangyi.sauerkrautmagicmod.item.ModCreativeTabs;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.fangyi.sauerkrautmagicmod.painting.ModPaintingVariants;
import net.fangyi.sauerkrautmagicmod.util.ModDataComponents;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import java.util.Locale;

@Mod(SauerkrautMagicMod.MODID)
public class SauerkrautMagicMod {
    public static final String MODID = "sauerkrautmagicmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SauerkrautMagicMod(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModMobEffects.register(modEventBus);
        ModEnchantmentEntityEffect.register(modEventBus);
        ModEnchantmentValueEffect.register(modEventBus);
        ModPaintingVariants.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModEntityTypes.register(modEventBus);


        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
