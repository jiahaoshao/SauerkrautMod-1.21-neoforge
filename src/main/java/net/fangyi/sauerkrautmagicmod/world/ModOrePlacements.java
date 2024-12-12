package net.fangyi.sauerkrautmagicmod.world;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModOrePlacements {
    public static final ResourceKey<PlacedFeature> ORE_RUBY = createKey("ore_ruby");

    public static void bootstrap(BootstrapContext<PlacedFeature> pContext) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = pContext.lookup(Registries.CONFIGURED_FEATURE);

        Holder<ConfiguredFeature<?, ?>> oreRubyHolder = holdergetter.getOrThrow(ModOreFeatures.ORE_RUBY);

        PlacementUtils.register(
                pContext, ORE_RUBY, oreRubyHolder, commonOrePlacement(16, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(72)))
        );
    }



    private static List<PlacementModifier> orePlacement(PlacementModifier countPlacement, PlacementModifier heightRange) {
        return List.of(countPlacement, InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(count), heightRange);
    }

    private static List<PlacementModifier> rareOrePlacement(int chance, PlacementModifier heightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(chance), heightRange);
    }

    public static ResourceKey<PlacedFeature> createKey(String pKey) {
        return ResourceKey.create(Registries.PLACED_FEATURE, SauerkrautMagicMod.prefix(pKey));
    }

}