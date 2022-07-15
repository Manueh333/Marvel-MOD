package manueh.marvel_themod.client.armor;

import manueh.marvel_themod.common.items.IronManArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class IronManArmorRenderer extends GeoArmorRenderer<IronManArmor> {

    public IronManArmorRenderer() {
        super(new IronManArmorModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.rightBootBone = "armorRightBoot";
        this.leftBootBone = "armorLeftBoot";
    }
}
