package manueh.marvel_themod.core.event;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.items.InfinityGauntlet;
import manueh.marvel_themod.core.init.BlockInit;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockEventData;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FaceDirection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.core.jmx.Server;

import java.awt.event.ItemEvent;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {



    @SubscribeEvent
    public static void rightClickEntity(PlayerInteractEvent.EntityInteract event) {
        PlayerEntity p = event.getPlayer();
        World level = event.getWorld();
        Entity target = event.getTarget();
        ItemStack stack = event.getItemStack();
        if(stack.getItem() == ItemInit.INFINITY_GAUNTLET.get().asItem()) {
            if(stack.getTag().getInt("gem") == 4 && stack.getTag().get("EntityData") == null && target.getType() != EntityType.ENDER_DRAGON && target.getType() != EntityType.WITHER) {
                stack.getOrCreateTag().get("EntityData");
                stack.getOrCreateTag().get("EntityName");
                stack.getTag().put("EntityData", target.serializeNBT());
                stack.getTag().putString("EntityName", target.getName().getString());
                target.remove();
                p.getCooldowns().addCooldown(stack.getItem(), 10);

            }
            if(stack.getTag().getInt("gem") == 5 && target.getType() != EntityType.ENDER_DRAGON && target.getType() != EntityType.WITHER) {
                target.kill();
                p.getCooldowns().addCooldown(stack.getItem(), 10);

            }
        }



    }


}
