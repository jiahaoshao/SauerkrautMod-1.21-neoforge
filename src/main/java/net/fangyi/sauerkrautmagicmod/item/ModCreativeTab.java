package net.fangyi.sauerkrautmagicmod.item;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SauerkrautMagicMod.MODID);

    public static final Supplier<CreativeModeTab.Builder> SAUERKRAUT_MAGIC_MOD_TAB_SUPPLIER =  () -> CreativeModeTab.builder()
            .title(Component.translatable("itemgroup.sauerkrautmagicmod.sauerkraut_magic_mod_tab")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.RUBY.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                ModItems.ITEMS_SUPPLIER.forEach(item->output.accept(item.get()));
            });

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SAUERKRAUT_MAGIC_MOD_TAB = CREATIVE_MODE_TABS.register("sauerkraut_magic_mod_tab",()-> SAUERKRAUT_MAGIC_MOD_TAB_SUPPLIER.get().build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
