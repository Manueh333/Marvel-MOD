package manueh.marvel_themod.core.init;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.blocks.TimeGemBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);

    public static final RegistryObject<Block> URU_ORE = BLOCKS.register("uru_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL,
            MaterialColor.COLOR_LIGHT_GRAY).strength(50f, 1200f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.METAL)));

    //.harvestTool(ToolType.PICKAXE).harvestLevel(3)
    public static final RegistryObject<Block> URU_BLOCK = BLOCKS.register("uru_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL,
                    MaterialColor.COLOR_LIGHT_GRAY).strength(25f, 100f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)));
//.harvestTool(ToolType.PICKAXE).harvestLevel(2)
    public static final RegistryObject<Block> CORRUPTED_BLOCK = BLOCKS.register("corrupted_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT,
                            MaterialColor.COLOR_RED).strength(5f, 1200f)
                    .sound(SoundType.WET_GRASS)));




    public static final RegistryObject<Block> TIME_GEM_BLOCK = BLOCKS.register("time_gem",
            () -> new TimeGemBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noCollission(), new ResourceLocation("marvel_themod", "normal")));

}
