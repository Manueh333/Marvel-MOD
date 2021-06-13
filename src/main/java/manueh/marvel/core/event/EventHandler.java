package manueh.marvel.core.event;

import manueh.marvel.Main;
import manueh.marvel.core.init.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {

    @SubscribeEvent
    public static void ChangeArmor(final LivingEquipmentChangeEvent event) {
        ServerPlayerEntity player = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(event.getEntity().getUUID());
        if(player.getItemBySlot(EquipmentSlotType.CHEST).getStack().sameItemStackIgnoreDurability(ItemInit.IRONMAN_CHESTPLATE.get().getDefaultInstance())) {
            player.abilities.mayfly = true;
            player.abilities.setFlyingSpeed(0.05001f);
        }else {
            if(player.abilities.getFlyingSpeed() == 0.05001f) {
                if(!player.isCreative()) {
                    player.abilities.mayfly = false;
                    player.abilities.flying = false;
                }
                player.abilities.setFlyingSpeed(0.05f);
            }
        }


    }


}
