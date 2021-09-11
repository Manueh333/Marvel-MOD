package manueh.marvel_themod.client;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.items.InfinityGauntlet;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ItemModelMesherForge;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)

public class ClientSetup {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ClientRegistry.registerKeyBinding(ClientEvents.keyFly);
            initTestItemOverrides();
        });
    }
    public static void initTestItemOverrides() {
        InfinityGauntlet item = ItemInit.INFINITY_GAUNTLET.get();
        ItemProperties.register(item, Main.INFINITY_GAUNTLET_GEM_PROPERTY,
                (stack, level, entity, damage) -> item.getGem(stack));
    }

}
