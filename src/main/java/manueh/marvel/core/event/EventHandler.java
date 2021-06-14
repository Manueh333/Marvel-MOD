package manueh.marvel.core.event;

import manueh.marvel.Main;
import manueh.marvel.core.init.BlockInit;
import manueh.marvel.core.init.ItemInit;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockEventData;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FaceDirection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
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
    public static void rightClickMjolnir(final PlayerInteractEvent.RightClickBlock event) {

        if(event.getWorld().getBlockState(event.getPos()).equals(BlockInit.MJOLNIR_BLOCK.get())) {
            event.getWorld().destroyBlock(event.getPos(), true);
            event.getPlayer().addItem(new ItemStack(ItemInit.MJOLNIR.get()));
        }
    }






}
