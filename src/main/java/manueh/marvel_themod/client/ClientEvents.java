package manueh.marvel_themod.client;

import com.ibm.icu.impl.UResource;
import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.containers.InfinityGauntletScreen;
import manueh.marvel_themod.common.items.InfinityGauntlet;
import manueh.marvel_themod.core.init.ItemInit;
import manueh.marvel_themod.core.network.Network;
import manueh.marvel_themod.core.network.message.InputMessage;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Keyboard;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.CloudParticle;
import net.minecraft.client.particle.WhiteAshParticle;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import org.lwjgl.glfw.GLFW;


import javax.annotation.Nullable;
import javax.swing.text.JTextComponent;
import java.time.chrono.MinguoEra;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {

    public static final KeyBinding keyFly = new KeyBinding("key.marvel_themod.fly", GLFW.GLFW_KEY_LEFT_ALT, "key.categories.marvel_themod");
    public static final KeyBinding keyOpenGauntlet = new KeyBinding("key.marvel_themod.openInfinityGauntlet", GLFW.GLFW_KEY_B, "key.categories.marvel_themod");


    private static boolean flying = false;


    @SubscribeEvent
    public static void gauntletGUI(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        ItemStack stack = event.player.getItemBySlot(EquipmentSlotType.MAINHAND);

        if (keyOpenGauntlet.isDown() && keyOpenGauntlet.consumeClick() && stack.getItem().equals(ItemInit.INFINITY_GAUNTLET.get())) {
            Main.NETWORK.sendToServer(new InputMessage(stack));
        }
    }

    @SubscribeEvent
    public static void activateFly(final TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if(player != null) {
            GameSettings settings = Minecraft.getInstance().options;
            KeyBinding jump = settings.keyJump;
            KeyBinding crouch = settings.keyShift;
            KeyBinding fly = ClientEvents.keyFly;
            if(player.getItemBySlot(EquipmentSlotType.HEAD).getStack().sameItemStackIgnoreDurability(ItemInit.IRONMAN_HELMET.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlotType.CHEST).getStack().sameItemStackIgnoreDurability(ItemInit.IRONMAN_CHESTPLATE.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlotType.LEGS).getStack().sameItemStackIgnoreDurability(ItemInit.IRONMAN_LEGGINS.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlotType.FEET).getStack().sameItemStackIgnoreDurability(ItemInit.IRONMAN_BOOTS.get().getDefaultInstance())) {
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

    @OnlyIn(Dist.CLIENT)
    public static void changeWalkSpeed(PlayerEntity player, float speed) {
        player.abilities.setWalkingSpeed(speed);
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
        GameSettings settings = Minecraft.getInstance().options;
        KeyBinding jump = settings.keyJump;
        if(jump.isDown()) {
            if(Minecraft.getInstance().player.getItemInHand(Hand.MAIN_HAND).sameItemStackIgnoreDurability(ItemInit.MJOLNIR.get().getDefaultInstance())) {

                Minecraft.getInstance().player.setDeltaMovement(Minecraft.getInstance().player.getDeltaMovement().x ,0.6, Minecraft.getInstance().player.getDeltaMovement().z);

            }
 }



  }

    @SubscribeEvent
    public static void fallDamage(LivingDamageEvent event) {
        PlayerEntity player = null;
        if (event.getEntity().is(Minecraft.getInstance().player)) {
            player = (PlayerEntity) event.getEntity();
        }
        if(player != null) {
            if(player.getItemBySlot(EquipmentSlotType.HEAD).getStack().sameItemStackIgnoreDurability(ItemInit.IRONMAN_HELMET.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlotType.CHEST).getStack().sameItemStackIgnoreDurability(ItemInit.IRONMAN_CHESTPLATE.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlotType.LEGS).getStack().sameItemStackIgnoreDurability(ItemInit.IRONMAN_LEGGINS.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlotType.FEET).getStack().sameItemStackIgnoreDurability(ItemInit.IRONMAN_BOOTS.get().getDefaultInstance())) {
                if(event.getSource().equals(DamageSource.FALL)) {
                        event.setCanceled(true);
                        player.fallDistance = 0;
                }
            }

            if(player.getItemInHand(Hand.MAIN_HAND).sameItemStackIgnoreDurability(ItemInit.MJOLNIR.get().getDefaultInstance())) {
                if(event.getSource().equals(DamageSource.FALL)) {
                    event.setCanceled(true);
                    player.fallDistance = 0;
                }
            }
        }


    }

    @SubscribeEvent
    public static void mjolnirLightningProtection(LivingDamageEvent event) {
        PlayerEntity player = null;
        if(event.getEntity().is(Minecraft.getInstance().player)) {
            player = (PlayerEntity) event.getEntity();
        }
        if(player != null) {
            if(player.getItemInHand(Hand.MAIN_HAND).sameItemStackIgnoreDurability(ItemInit.MJOLNIR.get().getDefaultInstance())) {
                if(event.getEntity() instanceof PlayerEntity) {
                    if(event.getSource().equals(DamageSource.LIGHTNING_BOLT) || event.getSource().equals(DamageSource.ON_FIRE) || event.getSource().equals(DamageSource.IN_FIRE)) {
                        event.getEntity().clearFire();
                        event.setCanceled(true);
                    }
                }


            }
        }

    }


    /*@SubscribeEvent
    public static void rightClickEntity(PlayerInteractEvent.EntityInteract event) {
        PlayerEntity p = event.getPlayer();
        World level = event.getWorld();
        Entity target = event.getTarget();
        ItemStack stack = event.getItemStack();

        if(stack == ItemInit.INFINITY_GAUNTLET.get().getDefaultInstance()) {
            if(stack.getTag().getInt("gem") == 4) {

                stack.getOrCreateTag().get("EntityData");
                stack.getOrCreateTag().get("EntityName");
                stack.getTag().put("EntityData", target.serializeNBT());
                stack.getTag().putString("EntityName", target.getName().getString());
                target.remove();

            }
        }
    }*/

}
