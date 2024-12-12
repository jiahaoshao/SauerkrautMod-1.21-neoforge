package net.fangyi.sauerkrautmagicmod.item;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.entity.ModEntityTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import twilightforest.init.TFEntities;

import java.util.Collection;
import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SauerkrautMagicMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB = TABS.register("mod_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.sauerkrautmagicmod.mod_tab")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.RUBY.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                ModItems.ITEMS_SUPPLIER.forEach(item->output.accept(item.get()));
                createSpawnEggsAlphabetical(output);
            })
            .build());

    private static void createSpawnEggsAlphabetical(CreativeModeTab.Output output) {
        Collection<? extends Item> eggs = ModEntityTypes.SPAWN_EGGS.getEntries().stream().map(DeferredHolder::value).toList();
        eggs.forEach(output::accept);
    }

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
