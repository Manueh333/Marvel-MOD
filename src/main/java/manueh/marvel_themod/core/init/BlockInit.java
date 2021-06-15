package manueh.marvel_themod.core.init;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.blocks.MjolnirBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);

    public static final RegistryObject<Block> URU_ORE = BLOCKS.register("uru_ore",
            () -> new Block(AbstractBlock.Properties.of(Material.HEAVY_METAL,
            MaterialColor.COLOR_LIGHT_GRAY).strength(50f, 1200f)
            .requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(3)
            .sound(SoundType.METAL)));
    public static final RegistryObject<Block> URU_BLOCK = BLOCKS.register("uru_block",
            () -> new Block(AbstractBlock.Properties.of(Material.HEAVY_METAL,
                    MaterialColor.COLOR_LIGHT_GRAY).strength(25f, 100f)
                    .requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(2)
                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> MJOLNIR_BLOCK = BLOCKS.register("mjolnir_block",
            () -> new MjolnirBlock(AbstractBlock.Properties.of(Material.HEAVY_METAL,
                    MaterialColor.COLOR_LIGHT_GRAY)
                    .strength(-1.0F, 3600000.0F)
                    .sound(SoundType.METAL)));

}
