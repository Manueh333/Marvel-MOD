package manueh.marvel_themod.client;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.core.init.ItemInit;
import manueh.marvel_themod.core.init.ParticlesInit;
import manueh.marvel_themod.core.network.message.InputMessage;
import manueh.marvel_themod.core.network.message.PacketChangeArmorEntity;
import manueh.marvel_themod.core.network.message.PacketDamageEntity;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;


import java.util.Optional;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {

    public static final KeyBinding shootIronManBeam = new KeyBinding("key.marvel_themod.shootIronManBeam", GLFW.GLFW_KEY_C, "key.categories.marvel_themod");
    public static final KeyBinding equipIronManArmor = new KeyBinding("key.marvel_themod.equipIronManArmor", GLFW.GLFW_KEY_Z, "key.categories.marvel_themod");

    public static final KeyBinding keyFly = new KeyBinding("key.marvel_themod.fly", GLFW.GLFW_KEY_LEFT_ALT, "key.categories.marvel_themod");
    public static final KeyBinding keyOpenGauntlet = new KeyBinding("key.marvel_themod.openInfinityGauntlet", GLFW.GLFW_KEY_B, "key.categories.marvel_themod");


    private static boolean flying = false;


    @SubscribeEvent
    public static void gauntletGUI(final TickEvent.ClientTickEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        if(player != null)  {
            ItemStack stack = player.getMainHandItem();

            if (keyOpenGauntlet.isDown() && stack.getItem().equals(ItemInit.INFINITY_GAUNTLET.get())) {
                Main.NETWORK.sendToServer(new InputMessage());
            }
        }

    }

    @SubscribeEvent
    public static void particles(final TickEvent.ClientTickEvent event) {
        Main.timer++;

        PlayerEntity p = Minecraft.getInstance().player;
        if(p != null) {
            World level =  p.getCommandSenderWorld();
            if(Main.makeTeleportAnimation && Main.timer > 1) {
                for (double x = -1; x < 1; x += 0.33) {
                    p.getCommandSenderWorld().addParticle(ParticlesInit.SPACE_GEM_PARTICLE.get(), p.position().x + x, p.position().y, p.position().z, 0, -0.3, 0);

                    for (double y = -1; y < 3; y += 0.33) {
                        p.getCommandSenderWorld().addParticle(ParticlesInit.SPACE_GEM_PARTICLE.get(), p.position().x + x, p.position().y + y, p.position().z, 0, -0.3, 0);

                        for (double z = -1; z < 1; z += 0.33) {
                            p.getCommandSenderWorld().addParticle(ParticlesInit.SPACE_GEM_PARTICLE.get(), p.position().x + x, p.position().y + y, p.position().z + z, 0, -0.3, 0);

                        }
                    }

                    Main.makeTeleportAnimation = false;


                }
            }



        }

    }

    public static boolean changingArmor = false;
    @SubscribeEvent
    public static void ironManTickEvent(final TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;

        if(player != null) {
            GameSettings settings = Minecraft.getInstance().options;
            KeyBinding jump = settings.keyJump;
            KeyBinding crouch = settings.keyShift;
            KeyBinding fly = ClientEvents.keyFly;
            KeyBinding shoot = ClientEvents.shootIronManBeam;
            KeyBinding equip = ClientEvents.equipIronManArmor;
            World level = player.level;

            if(fullIronManArmor(player)) {
                if(flying) {
                    if(jump.isDown()) {
                        player.setDeltaMovement(player.getDeltaMovement().x,0.3f, player.getDeltaMovement().z);

                    }if(crouch.isDown()) {
                        player.setDeltaMovement(player.getDeltaMovement().x,-0.3f, player.getDeltaMovement().z);

                    }if(!jump.isDown() && !crouch.isDown()) {
                        player.setDeltaMovement(player.getDeltaMovement().x * 1.03,0, player.getDeltaMovement().z * 1.03);

                    }

                }
                if(equipIronManArmor.consumeClick() && !changingArmor)
                {
                    Main.NETWORK.sendToServer(new PacketChangeArmorEntity(player.getId()));
                    changingArmor = true;
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


                if(shoot.isDown()) {

                    Double rayLength = new Double(100);
                    Vector3d playerRotation = player.getViewVector(0);
                    Vector3d rayPath = playerRotation.scale(rayLength);

                    //RAY START AND END POINTS
                    Vector3d from = player.getEyePosition(0);
                    Vector3d to = from.add(rayPath);
                    Entity entity = null;

                    EntityRayTraceResult entityRayTraceResult = getPlayerPOVHitResult(player);

                    if (entityRayTraceResult != null) {
                        if (entityRayTraceResult.getType() == RayTraceResult.Type.ENTITY) {
                            entity = entityRayTraceResult.getEntity();
                     }
                        if (!level.isClientSide && entity != null) {
                            Main.NETWORK.sendToServer(new PacketDamageEntity(entity.getId(), 10F));
                        }
                    }
                    if (level.isClientSide) {

                        Vector3d playerAngle = player.getLookAngle();
                        for (double i = 0; i < 100; i += 0.33) {
                            level.addParticle(ParticlesInit.IRONMAN_BEAM_PARTICLE.get(), player.getEyePosition(0).x + (playerAngle.x * i), player.getEyePosition(0).y + (playerAngle.y * i), player.getEyePosition(0).z + (playerAngle.z * i), 0, 0, 0);

                        }
                    }
                }

           }else if( !changingArmor && equipIronManArmor.consumeClick() && player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == ItemInit.REACTOR.get())
           {
               Main.NETWORK.sendToServer(new PacketChangeArmorEntity(player.getId()));
               changingArmor = true;
           }


        }
    }


    private static boolean fullIronManArmor(PlayerEntity player) {
        return player.getItemBySlot(EquipmentSlotType.FEET).getItem() == (ItemInit.IRONMAN_BOOTS.get()) && player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == ItemInit.IRONMAN_LEGGINS.get() && player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == ItemInit.IRONMAN_CHESTPLATE.get() && player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ItemInit.IRONMAN_HELMET.get();

    }

    public static EntityRayTraceResult getPlayerPOVHitResult(PlayerEntity player) {
        float playerRotX = player.getViewXRot(0);
        float playerRotY = player.getViewYRot(0);
        Vector3d startPos = player.getEyePosition(0);
        float f2 = MathHelper.cos(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = MathHelper.sin(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -MathHelper.cos(-playerRotX * ((float) Math.PI / 180F));
        float additionY = MathHelper.sin(-playerRotX * ((float) Math.PI / 180F));
        float additionX = f3 * f4;
        float additionZ = f2 * f4;
        double d0 = 1000;
        Vector3d endVec = startPos.add((double) additionX * d0, (double) additionY * d0, (double) additionZ * d0);
        AxisAlignedBB startEndBox = new AxisAlignedBB(startPos, endVec);
        Entity entity = null;
        for (Entity entity1 : player.level.getEntities(player, startEndBox, (val) -> true)) {
            AxisAlignedBB aabb = entity1.getBoundingBox().inflate(entity1.getPickRadius());
            Optional<Vector3d> optional = aabb.clip(startPos, endVec);
            if (aabb.contains(startPos)) {
                if (d0 >= 0.0D) {
                    entity = entity1;
                    startPos = optional.orElse(startPos);
                    d0 = 0.0D;
                }
            } else if (optional.isPresent()) {
                Vector3d vec31 = optional.get();
                double d1 = startPos.distanceToSqr(vec31);
                if (d1 < d0 || d0 == 0.0D) {
                    if (entity1.getRootVehicle() == player.getRootVehicle() && !entity1.canRiderInteract()) {
                        if (d0 == 0.0D) {
                            entity = entity1;
                            startPos = vec31;
                        }
                    } else {
                        entity = entity1;
                        startPos = vec31;
                        d0 = d1;
                    }
                }
            }
        }

        return (entity == null) ? null : new EntityRayTraceResult(entity);
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
