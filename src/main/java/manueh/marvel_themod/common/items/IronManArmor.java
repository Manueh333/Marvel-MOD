package manueh.marvel_themod.common.items;

import com.google.common.collect.ImmutableMap;
import manueh.marvel_themod.Main;
import manueh.marvel_themod.client.ClientEvents;
import manueh.marvel_themod.core.enums.IronManMaterial;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
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
import java.time.chrono.MinguoEra;
import java.util.List;
import java.util.Map;

public class IronManArmor extends GeoArmorItem implements IAnimatable  {
    private AnimationFactory factory = new AnimationFactory(this);

    public IronManArmor(IArmorMaterial material, EquipmentSlotType slotType, Properties properties) {
        super(material, slotType, properties);
    }


    public boolean fullArmor = false;
    public boolean flying = false;
  @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, world, tooltip, flagIn);
        Minecraft mc = Minecraft.getInstance();

        if (world == null || mc.player == null) {
            return;
        }
        boolean sneakPressed = Screen.hasShiftDown();

        if(sneakPressed) {
            tooltip.add(new TranslationTextComponent("tooltip.marvel_themod.ironman_armor",  ClientEvents.keyFly.getKey().getDisplayName(), ClientEvents.shootIronManBeam.getKey().getDisplayName()));
            tooltip.add(new TranslationTextComponent("tooltip.marvel_themod.ironman_repulsor", ClientEvents.shootIronManBeam.getKey().getDisplayName()));
        }else {
            tooltip.add(new TranslationTextComponent("tooltip.marvel_themod.hold_shift"));
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
