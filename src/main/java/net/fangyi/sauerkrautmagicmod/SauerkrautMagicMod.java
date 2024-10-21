package net.fangyi.sauerkrautmagicmod;

import com.mojang.logging.LogUtils;
import net.fangyi.sauerkrautmagicmod.block.ModBlocks;
import net.fangyi.sauerkrautmagicmod.item.ModCreativeTab;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(SauerkrautMagicMod.MODID)
public class SauerkrautMagicMod {
    public static final String MODID = "sauerkrautmagicmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public SauerkrautMagicMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        ModItems.register(modEventBus);
        ModCreativeTab.register(modEventBus);
        ModBlocks.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
