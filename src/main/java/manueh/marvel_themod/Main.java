package manueh.marvel_themod;

import manueh.marvel_themod.client.ClientSetup;
import manueh.marvel_themod.core.enums.TimeGemAPI;
import manueh.marvel_themod.core.init.*;
import manueh.marvel_themod.core.network.Network;
import manueh.marvel_themod.world.ModBiomes;
import manueh.marvel_themod.world.OreGeneration;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(Main.MODID)
public class Main
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "marvel_themod";
    public static final ItemGroup GROUP = new MainTab("marvel_themod");
    public static SimpleChannel NETWORK;

    public static int timer = 0;
    public static boolean makeTeleportAnimation = false;

     public static boolean shootingIronManBeam = false;

    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        ParticlesInit.PARTICLE_TYPES.register(bus);

        ItemInit.ITEMS.register(bus);
        TileEntityTypeInit.TILE_ENTITY_TYPE.register(bus);
        BlockInit.BLOCKS.register(bus);
        EntityTypesInit.ENTITY_TYPES.register(bus);
        TimeGemAPI.INSTANCE.blacklistBlock(Blocks.WATER);
        TimeGemAPI.INSTANCE.blacklistBlock(Blocks.LAVA);
        TimeGemAPI.INSTANCE.blacklistBlock(Blocks.AIR);
        TimeGemAPI.INSTANCE.blacklistBlock(Blocks.CAVE_AIR);
        TimeGemAPI.INSTANCE.blacklistBlock(Blocks.VOID_AIR);

        ContainerInit.register(bus);

        ModBiomes.register(bus);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::generateOres);
        MinecraftForge.EVENT_BUS.register(this);

        bus.addListener(ClientSetup::clientSetup);
        bus.addListener(ClientSetup::registerParticleFactories);

    }


    private void setup(final FMLCommonSetupEvent event) {
        NETWORK = Network.register();
    }


    public static class MainTab extends ItemGroup {

        public MainTab(String label) {
            super(label);
        }

        @Override
        public ItemStack makeIcon() {
            return ItemInit.CAPTAIN_AMERICA_SHIELD.get().getDefaultInstance();
        }
    }
    public static final ResourceLocation INFINITY_GAUNTLET_GEM_PROPERTY = new ResourceLocation(Main.MODID, "gem");

}
