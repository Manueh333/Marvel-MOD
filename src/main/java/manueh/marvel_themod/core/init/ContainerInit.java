package manueh.marvel_themod.core.init;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.blocks.containers.InfinityGauntletContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerInit {

    public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Main.MODID);


    public static final RegistryObject<ContainerType<InfinityGauntletContainer>> INFINITY_GAUNTLET_CONTAINER = CONTAINERS.register("infinity_gauntlet_container", () -> IForgeContainerType.create(InfinityGauntletContainer::new));


    public static void register (IEventBus bus) {
        CONTAINERS.register(bus);
    }
}
