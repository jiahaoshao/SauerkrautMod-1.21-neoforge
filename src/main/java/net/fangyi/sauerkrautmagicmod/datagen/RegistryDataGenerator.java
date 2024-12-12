package net.fangyi.sauerkrautmagicmod.datagen;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.enchatment.ModEnchantments;
import net.fangyi.sauerkrautmagicmod.world.ModBiomeModifiers;
import net.fangyi.sauerkrautmagicmod.world.ModOreFeatures;
import net.fangyi.sauerkrautmagicmod.world.ModOrePlacements;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ModOreFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModOrePlacements::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, BUILDER, Set.of("minecraft", SauerkrautMagicMod.MODID));
    }
}
