package com.extra.leaguecraft.world.gen;

import com.extra.leaguecraft.block.ModBlocks;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.PlainFlowerBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;

public class ModConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> SHIMMER_FLOWER_CONFIG = Feature.FLOWER.withConfiguration(
            (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.SHIMMER_FLOWER.get().getDefaultState()),
                    SimpleBlockPlacer.PLACER)).tries(1).build()).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).count(1);
}