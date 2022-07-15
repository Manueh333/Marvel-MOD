package manueh.marvel_themod.client.armor;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.items.IronManArmor;
import manueh.marvel_themod.common.items.IronManReactorArmorItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IronManArmorModel extends AnimatedGeoModel<IronManArmor> {
    @Override
    public ResourceLocation getModelLocation(IronManArmor object) {
        return new ResourceLocation(Main.MODID, "geo/ironman_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(IronManArmor object) {
        return new ResourceLocation(Main.MODID, "textures/models/armor/ironman_armor.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(IronManArmor animatable) {
        return new ResourceLocation(Main.MODID, "animations/ironman_armor_animation.json");
    }
}
