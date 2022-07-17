package manueh.marvel_themod.client;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.client.armor.IronManArmorRenderer;
import manueh.marvel_themod.client.armor.IronManReactorRenderer;
import manueh.marvel_themod.client.util.ClientUtils;
import manueh.marvel_themod.common.blocks.containers.InfinityGauntletScreen;
import manueh.marvel_themod.common.items.InfinityGauntlet;
import manueh.marvel_themod.common.items.IronManArmor;
import manueh.marvel_themod.common.items.IronManReactorArmorItem;
import manueh.marvel_themod.common.particles.IronManBeamParticle;
import manueh.marvel_themod.common.particles.SpaceGemParticle;
import manueh.marvel_themod.core.enums.ModItemModelProperties;
import manueh.marvel_themod.core.init.ContainerInit;
import manueh.marvel_themod.core.init.EntityTypesInit;
import manueh.marvel_themod.core.init.ItemInit;
import manueh.marvel_themod.core.init.ParticlesInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)

public class ClientSetup {

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ClientRegistry.registerKeyBinding(ClientEvents.keyFly);
            ClientRegistry.registerKeyBinding(ClientEvents.keyOpenGauntlet);
            ClientRegistry.registerKeyBinding(ClientEvents.shootIronManBeam);
            ClientRegistry.registerKeyBinding(ClientEvents.equipIronManArmor);
            initTestItemOverrides();
            MenuScreens.register(ContainerInit.INFINITY_GAUNTLET_CONTAINER.get(), InfinityGauntletScreen::new);


        });
        ModItemModelProperties.makeBow(ItemInit.HAWK_EYE_BOW.get());
    }

    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticlesInit.SPACE_GEM_PARTICLE.get(), SpaceGemParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticlesInit.IRONMAN_BEAM_PARTICLE.get(), IronManBeamParticle.Factory::new);
    }


    public static void initTestItemOverrides() {
        InfinityGauntlet item = ItemInit.INFINITY_GAUNTLET.get();
        ItemProperties.register(item, Main.INFINITY_GAUNTLET_GEM_PROPERTY,
                (stack, entity, damage, var) -> {
            return item.getGem(stack);
                });

    }


}
