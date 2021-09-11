package manueh.marvel_themod.client;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {

    public static final KeyMapping keyFly = new KeyMapping("key.marvel_themod.fly", GLFW.GLFW_KEY_LEFT_ALT, "key.categories.marvel_themod");




    private static boolean flying = false;

    @SubscribeEvent
    public static void activateFly(final TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if(player != null) {
            Options settings = Minecraft.getInstance().options;
            KeyMapping jump = settings.keyJump;
            KeyMapping crouch = settings.keyShift;
            KeyMapping fly = ClientEvents.keyFly;
            if(player.getItemBySlot(EquipmentSlot.HEAD).sameItemStackIgnoreDurability(ItemInit.IRONMAN_HELMET.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlot.CHEST).sameItemStackIgnoreDurability(ItemInit.IRONMAN_CHESTPLATE.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlot.LEGS).sameItemStackIgnoreDurability(ItemInit.IRONMAN_LEGGINS.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlot.FEET).sameItemStackIgnoreDurability(ItemInit.IRONMAN_BOOTS.get().getDefaultInstance())) {
                if(flying) {
                    if(jump.isDown()) {
                        player.setDeltaMovement(player.getDeltaMovement().x,0.3f, player.getDeltaMovement().z);

                    }if(crouch.isDown()) {
                        player.setDeltaMovement(player.getDeltaMovement().x,-0.3f, player.getDeltaMovement().z);

                    }if(!jump.isDown() && !crouch.isDown()) {
                        player.setDeltaMovement(player.getDeltaMovement().x * 1.03,0, player.getDeltaMovement().z * 1.03);

                    }

                }

                if(fly.consumeClick()) {
                    flying = !flying;
                    if(flying) {
                        player.setDeltaMovement(player.getDeltaMovement().x ,1, player.getDeltaMovement().z);
                    }
                }
                if(flying) {
                    player.getCommandSenderWorld().addParticle(ParticleTypes.CLOUD, player.position().x + (player.getCommandSenderWorld().random.nextFloat() - player.getCommandSenderWorld().random.nextFloat()) / 2, player.position().y + (player.getCommandSenderWorld().random.nextFloat() / 2 - player.getCommandSenderWorld().random.nextFloat()) / 2, player.position().z + (player.getCommandSenderWorld().random.nextFloat() - player.getCommandSenderWorld().random.nextFloat()) / 4, 0, -0.5, 0);
                }
                player.fallDistance = 0;



           }
        }
    }


    @SubscribeEvent
    public static void MjolnirOnHand(final TickEvent.PlayerTickEvent event) {

        Player player = event.player;
        if(player != null) {
            if(player.getItemInHand(InteractionHand.MAIN_HAND).sameItemStackIgnoreDurability(ItemInit.MJOLNIR.get().getDefaultInstance())) {
                player.fallDistance = 0;
               // player.abilities.setWalkingSpeed(0.06f);


            }/*else {
                player.abilities.setWalkingSpeed(0.1F);
            }*/
        }
    }

 /*   @SubscribeEvent
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
    }*/

    @SubscribeEvent
    public static void mjolnirFly(TickEvent.ClientTickEvent event) {
        Options settings = Minecraft.getInstance().options;
        KeyMapping jump = settings.keyJump;
        if(jump.isDown()) {
            if(Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND).sameItemStackIgnoreDurability(ItemInit.MJOLNIR.get().getDefaultInstance())) {

                Minecraft.getInstance().player.setDeltaMovement(Minecraft.getInstance().player.getDeltaMovement().x ,0.6, Minecraft.getInstance().player.getDeltaMovement().z);

            }
 }



  }

    @SubscribeEvent
    public static void fallDamage(LivingDamageEvent event) {
        Player player = null;
        if (event.getEntity().is(Minecraft.getInstance().player)) {
            player = (Player) event.getEntity();
        }
        if(player != null) {
            if(player.getItemBySlot(EquipmentSlot.HEAD).sameItemStackIgnoreDurability(ItemInit.IRONMAN_HELMET.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlot.CHEST).sameItemStackIgnoreDurability(ItemInit.IRONMAN_CHESTPLATE.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlot.LEGS).sameItemStackIgnoreDurability(ItemInit.IRONMAN_LEGGINS.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlot.FEET).sameItemStackIgnoreDurability(ItemInit.IRONMAN_BOOTS.get().getDefaultInstance())) {
                if(event.getSource().equals(DamageSource.FALL)) {
                        event.setCanceled(true);
                        player.fallDistance = 0;
                }
            }

            if(player.getItemInHand(InteractionHand.MAIN_HAND).sameItemStackIgnoreDurability(ItemInit.MJOLNIR.get().getDefaultInstance())) {
                if(event.getSource().equals(DamageSource.FALL)) {
                    event.setCanceled(true);
                    player.fallDistance = 0;
                }
            }
        }


    }

    @SubscribeEvent
    public static void mjolnirLightningProtection(LivingDamageEvent event) {
        Player player = null;
        if(event.getEntity().is(Minecraft.getInstance().player)) {
            player = (Player) event.getEntity();
        }
        if(player != null) {
            if(player.getItemInHand(InteractionHand.MAIN_HAND).sameItemStackIgnoreDurability(ItemInit.MJOLNIR.get().getDefaultInstance())) {
                if(event.getEntity() instanceof Player) {
                    if(event.getSource().equals(DamageSource.LIGHTNING_BOLT) || event.getSource().equals(DamageSource.ON_FIRE) || event.getSource().equals(DamageSource.IN_FIRE)) {
                        event.getEntity().clearFire();
                        event.setCanceled(true);
                    }
                }


            }
        }

    }

}
