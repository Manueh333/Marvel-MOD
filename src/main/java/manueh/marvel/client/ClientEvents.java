package manueh.marvel.client;

import manueh.marvel.Main;
import manueh.marvel.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sun.util.resources.cldr.kea.TimeZoneNames_kea;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void activateFly(final TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if(player != null) {
            if(player.getItemBySlot(EquipmentSlotType.HEAD).getStack().sameItemStackIgnoreDurability(ItemInit.IRONMAN_HELMET.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlotType.CHEST).getStack().sameItemStackIgnoreDurability(ItemInit.IRONMAN_CHESTPLATE.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlotType.LEGS).getStack().sameItemStackIgnoreDurability(ItemInit.IRONMAN_LEGGINS.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlotType.FEET).getStack().sameItemStackIgnoreDurability(ItemInit.IRONMAN_BOOTS.get().getDefaultInstance())) {
                player.abilities.mayfly = true;
                changeFlySpeed(player,0.05001f);
            }else {
                if(player.abilities.getFlyingSpeed() == 0.05001f) {
                    if(!player.isCreative() && !player.isSpectator()) {
                        player.abilities.mayfly = false;
                        player.abilities.flying = false;
                    }
                    changeFlySpeed(player, 0.05f);
                }
            }
        }
    }

    public static void changeFlySpeed(PlayerEntity player, float speed) {
        player.abilities.setFlyingSpeed(speed);
    }

    @SubscribeEvent
    public static void MjolnirOnHand(final TickEvent.PlayerTickEvent event) {

        PlayerEntity player = event.player;
        if(player != null) {
            if(player.getItemInHand(Hand.MAIN_HAND).sameItemStackIgnoreDurability(ItemInit.MJOLNIR.get().getDefaultInstance())) {
                player.fallDistance = 0;
                player.abilities.setWalkingSpeed(0.06f);
            }else {
                player.abilities.setWalkingSpeed(0.1F);
            }
        }
    }

    @SubscribeEvent
    public static void jumpMjolnir(final LivingEvent.LivingJumpEvent event) {
        PlayerEntity player = null;
        if(event.getEntity().is(Minecraft.getInstance().player)) {
            player = (PlayerEntity) event.getEntity();
        }
        if(player != null) {
            if (player.getItemInHand(Hand.MAIN_HAND).sameItemStackIgnoreDurability(ItemInit.MJOLNIR.get().getDefaultInstance())) {
                for (int i=0;i<10;i++) {
                    player.lerpMotion(player.getLookAngle().x, player.getDeltaMovement().y + 0.05f, player.getLookAngle().z);
                    player.push(player.getLookAngle().x, 0, player.getLookAngle().z);
                }

            }
        }
    }

}
