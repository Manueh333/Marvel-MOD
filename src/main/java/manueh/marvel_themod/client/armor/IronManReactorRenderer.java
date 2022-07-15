package manueh.marvel_themod.client.armor;

import manueh.marvel_themod.common.items.IronManReactorArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class IronManReactorRenderer extends GeoArmorRenderer<IronManReactorArmorItem> {

    public IronManReactorRenderer() {
        super(new IronManReactorArmorModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorLeftLeg";
        this.leftLegBone = "armorRightLeg";
        this.rightBootBone = "armorLeftBoot";
        this.leftBootBone = "armorRightBoot";
    }
}
