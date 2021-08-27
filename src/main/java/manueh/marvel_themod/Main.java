package manueh.marvel_themod;

import manueh.marvel_themod.client.ClientSetup;
import manueh.marvel_themod.core.enums.KeyConflictNone;
import manueh.marvel_themod.core.init.BlockInit;
import manueh.marvel_themod.core.init.EntityTypesInit;
import manueh.marvel_themod.core.init.ItemInit;
import manueh.marvel_themod.world.OreGeneration;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;


@Mod(Main.MODID)
public class Main
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "marvel_themod";
    public static final ItemGroup GROUP = new MainTab("marvel_themod");

    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        //bus.addListener(this::clientSetup);
        bus.addListener(ClientSetup::clientSetup);
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        EntityTypesInit.ENTITY_TYPES.register(bus);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::generateOres);
        MinecraftForge.EVENT_BUS.register(this);
    }




    //private void clientSetup(final FMLClientSetupEvent event) {
   //     ClientRegistry.registerKeyBinding(keyFly);
    //}

    private void setup(final FMLCommonSetupEvent event) {

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

}