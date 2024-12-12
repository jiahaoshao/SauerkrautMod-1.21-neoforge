package net.fangyi.sauerkrautmagicmod.world;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModOreFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_RUBY = createKey("ruby");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> pContext) {
        RuleTest BaseStoneOverworldRuleTest = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        RuleTest StoneOreReplaceRuleTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest DeepslateOreReplaceRuleTest = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest NetherrackRuleTest = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest BaseStoneNetherRuleTest = new TagMatchTest(BlockTags.BASE_STONE_NETHER);


        List<OreConfiguration.TargetBlockState> ORE_RUBY_TARGET_LIST = List.of(
                OreConfiguration.target(StoneOreReplaceRuleTest, ModBlocks.RUBY_ORE.get().defaultBlockState()),
                OreConfiguration.target(DeepslateOreReplaceRuleTest, ModBlocks.RUBY_BLOCK.get().defaultBlockState())
        );

        FeatureUtils.register(pContext, ORE_RUBY, Feature.ORE, new OreConfiguration(ORE_RUBY_TARGET_LIST, 9));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String pName) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, SauerkrautMagicMod.prefix(pName));
    }
}
