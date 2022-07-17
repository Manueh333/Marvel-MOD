package manueh.marvel_themod.client.util;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.client.armor.IronManArmorRenderer;
import manueh.marvel_themod.client.armor.IronManReactorRenderer;
import manueh.marvel_themod.common.items.IronManArmor;
import manueh.marvel_themod.common.items.IronManReactorArmorItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;


@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ArmorRenderer {

    @SubscribeEvent
    public static void renderArmor(final EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(IronManReactorArmorItem.class, new IronManReactorRenderer());
        GeoArmorRenderer.registerArmorRenderer(IronManArmor.class, new IronManArmorRenderer());
    }


}
