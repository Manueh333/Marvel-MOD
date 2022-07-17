package manueh.marvel_themod.world;

import manueh.marvel_themod.core.init.BlockInit;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGeneration {

    public static void generateOres(final BiomeLoadingEvent event) {
        if(!event.getCategory().equals(Biome.BiomeCategory.NETHER) || !event.getCategory().equals(Biome.BiomeCategory.THEEND) || !event.getCategory().equals(ModBiomes.REALITY_BIOME)) {
            generateOre(event.getGeneration(), OreConfiguration.Predicates.NATURAL_STONE, BlockInit.URU_ORE.get().defaultBlockState(), 10, 0, 15, 28, 45);
        }
        else if(!event.getCategory().equals(Biome.BiomeCategory.NETHER) || !event.getCategory().equals(Biome.BiomeCategory.THEEND)) {
            generateOre(event.getGeneration(), OreConfiguration.Predicates.NATURAL_STONE, BlockInit.URU_ORE.get().defaultBlockState(), 20, 0, 20, 64, 60);
        }
    }

    public static void generateOre(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state, int veinSize, int minHeight, int maxHeight, int amount, int chance) {
        settings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
                Feature.ORE.configured(new OreConfiguration(fillerType, state, veinSize)).rarity(chance).rangeUniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(15)).squared().squared().count(amount));
    }


}
