package net.fangyi.sauerkrautmagicmod.datagen;


import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.datagen.lang.ModChineseLangProvider;
import net.fangyi.sauerkrautmagicmod.datagen.loot.ModLootTableProvider;
import net.fangyi.sauerkrautmagicmod.datagen.model.ModBlockStateProvider;
import net.fangyi.sauerkrautmagicmod.datagen.model.ModItemModelProvider;
import net.fangyi.sauerkrautmagicmod.datagen.recipe.ModRecipeProvider;
import net.fangyi.sauerkrautmagicmod.datagen.tag.ModBlockTagsProvider;
import net.fangyi.sauerkrautmagicmod.datagen.tag.ModEnchantmentTagsProvider;
import net.fangyi.sauerkrautmagicmod.datagen.tag.ModItemTagsProvider;
import net.fangyi.sauerkrautmagicmod.datagen.tag.ModPaintingVariantTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = SauerkrautMagicMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModChineseLangProvider(output, "zh_cn"));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModLootTableProvider(output, lookupProvider));
        BlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModPaintingVariantTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModRecipeProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new RegistryDataGenerator(output, lookupProvider));
        generator.addProvider(event.includeServer(), new ModEnchantmentTagsProvider(output, lookupProvider));
    }
}
