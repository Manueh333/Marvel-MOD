package manueh.marvel_themod.client;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.PowerGemRenderer;
import manueh.marvel_themod.common.items.InfinityGauntlet;
import manueh.marvel_themod.core.enums.ModItemModelProperties;
import manueh.marvel_themod.core.init.EntityTypesInit;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.item.ItemModelsProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)

public class ClientSetup {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ClientRegistry.registerKeyBinding(ClientEvents.keyFly);
            RenderingRegistry.registerEntityRenderingHandler(EntityTypesInit.POWER_GEM.get(), PowerGemRenderer::new);
            initTestItemOverrides();
        });
        ModItemModelProperties.makeBow(ItemInit.HAWK_EYE_BOW.get());
    }
    public static void initTestItemOverrides() {
        InfinityGauntlet item = ItemInit.INFINITY_GAUNTLET.get();
        ItemModelsProperties.register(item, Main.INFINITY_GAUNTLET_GEM_PROPERTY,
                (stack, entity, damage) -> item.getGem(stack));
    }
}
