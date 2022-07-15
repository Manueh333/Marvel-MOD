package manueh.marvel_themod.client.armor;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.items.IronManReactorArmorItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IronManReactorArmorModel extends AnimatedGeoModel<IronManReactorArmorItem> {
    @Override
    public ResourceLocation getModelLocation(IronManReactorArmorItem object) {
        return new ResourceLocation(Main.MODID, "geo/ironman_reactor.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(IronManReactorArmorItem object) {
        return new ResourceLocation(Main.MODID, "textures/models/armor/ironman_reactor_armor.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(IronManReactorArmorItem animatable) {
        return new ResourceLocation(Main.MODID, "animations/armor_animation.json");
    }
}
