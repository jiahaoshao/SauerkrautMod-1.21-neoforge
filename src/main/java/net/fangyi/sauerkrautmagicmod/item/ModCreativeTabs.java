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

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SauerkrautMagicMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB = TABS.register("mod_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.sauerkrautmagicmod.mod_tab")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.RUBY.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                ModItems.ITEMS_SUPPLIER.forEach(item->output.accept(item.get()));
            })
            .build());

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
