package manueh.marvel_themod;

import manueh.marvel_themod.client.ClientEvents;
import manueh.marvel_themod.core.enums.KeyConflictNone;
import manueh.marvel_themod.core.event.EventHandler;
import manueh.marvel_themod.core.init.BlockInit;
import manueh.marvel_themod.core.init.ItemInit;
import manueh.marvel_themod.world.OreGeneration;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "marvel_themod";
    public static final ItemGroup GROUP = new MainTab("marvel_themod");

    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::generateOres);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static final KeyBinding MJOLNIR_FLY_KEY = new KeyBinding("key.marvel_themod.mjolnir_fly", KeyConflictNone.NONE, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_SPACE, "key.categories.marvel_themod");

    private void clientSetup(final FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(MJOLNIR_FLY_KEY);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    public static class MainTab extends ItemGroup {

        public MainTab(String label) {
            super(label);
        }

        @Override
        public ItemStack makeIcon() {
            return ItemInit.MJOLNIR.get().getDefaultInstance();
        }
    }

}
