package manueh.marvel_themod.server;

import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class ServerEvents {


    public static void entityFall(LivingFallEvent event)
    {
        Entity attackedEnt = event.getEntity();
        if(attackedEnt instanceof Player)
        {
            Player player = ((Player)attackedEnt);
            if(player.getItemBySlot(EquipmentSlot.HEAD).sameItemStackIgnoreDurability(ItemInit.IRONMAN_HELMET.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlot.CHEST).sameItemStackIgnoreDurability(ItemInit.IRONMAN_CHESTPLATE.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlot.LEGS).sameItemStackIgnoreDurability(ItemInit.IRONMAN_LEGGINS.get().getDefaultInstance()) && player.getItemBySlot(EquipmentSlot.FEET).sameItemStackIgnoreDurability(ItemInit.IRONMAN_BOOTS.get().getDefaultInstance())) {
                event.setDistance(0);
            }
        }
    }
}