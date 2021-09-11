package manueh.marvel_themod.world;

import manueh.marvel_themod.core.init.BlockInit;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGeneration {
    // 5 0 15 16 15
    /**
     * event, configuration, block, veinSize, minH, maxH, count
     * **/
    public static void generateOres(final BiomeLoadingEvent event) {
        if (!event.getCategory().equals(Biome.BiomeCategory.NETHER) && !event.getCategory().equals(Biome.BiomeCategory.THEEND)) {
            OreGenerate(event.getGeneration(), OreConfiguration.Predicates.NATURAL_STONE, ((Block) BlockInit.URU_ORE.get()).defaultBlockState(), (Integer) 5, (Integer) 0, (Integer) 15, (Integer) 16, (Integer) 9);
        }
    }

    private static void OreGenerate(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state, int veinSize, int minHeight, int maxHeight, int amount, int rarity) {
        settings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature) Feature.ORE.configured(new OreConfiguration(fillerType, state, veinSize)).rarity(rarity).rangeUniform(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight))).squared()).count(amount));
    }

}
