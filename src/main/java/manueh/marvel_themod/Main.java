package manueh.marvel_themod;

import manueh.marvel_themod.client.ClientSetup;
import manueh.marvel_themod.core.init.BlockInit;
import manueh.marvel_themod.core.init.EntityTypesInit;
import manueh.marvel_themod.core.init.ItemInit;
import manueh.marvel_themod.server.ServerEvents;
import manueh.marvel_themod.world.OreGeneration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(Main.MODID)
public class Main
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "marvel_themod";
    public static final CreativeModeTab GROUP = new MainTab("marvel_themod");

    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        //bus.addListener(this::clientSetup);
        bus.addListener(ClientSetup::clientSetup);
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        EntityTypesInit.ENTITY_TYPES.register(bus);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ServerEvents::entityFall);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::generateOres);
        MinecraftForge.EVENT_BUS.register(this);
    }




    //private void clientSetup(final FMLClientSetupEvent event) {
   //     ClientRegistry.registerKeyBinding(keyFly);
    //}

    private void setup(final FMLCommonSetupEvent event) {

    }

    public static class MainTab extends CreativeModeTab {

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
