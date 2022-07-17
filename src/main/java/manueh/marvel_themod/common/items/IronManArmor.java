package manueh.marvel_themod.common.items;

import manueh.marvel_themod.client.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

import javax.annotation.Nullable;
import java.util.List;

public class IronManArmor extends GeoArmorItem implements IAnimatable  {
    private AnimationFactory factory = new AnimationFactory(this);

    public IronManArmor(ArmorMaterial material, EquipmentSlot slotType, Properties properties) {
        super(material, slotType, properties);
    }


    public boolean fullArmor = false;
    public boolean flying = false;
  @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, world, tooltip, flagIn);
        Minecraft mc = Minecraft.getInstance();

        if (world == null || mc.player == null) {
            return;
        }
        boolean sneakPressed = Screen.hasShiftDown();

        if(sneakPressed) {
            tooltip.add(new TranslatableComponent("tooltip.marvel_themod.ironman_armor",  ClientEvents.keyFly.getKey().getDisplayName(), ClientEvents.shootIronManBeam.getKey().getDisplayName()));
            tooltip.add(new TranslatableComponent("tooltip.marvel_themod.ironman_repulsor"));
        }else {
            tooltip.add(new TranslatableComponent("tooltip.marvel_themod.hold_shift"));
        }

    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<IronManArmor>(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {

      event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));


        return PlayState.CONTINUE;
    }



}
