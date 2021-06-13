package manueh.marvel.client;

import manueh.marvel.Main;
import manueh.marvel.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void ChangeArmor(final TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if(player != null) {
            if(player.getItemBySlot(EquipmentSlotType.CHEST).getStack().sameItemStackIgnoreDurability(ItemInit.IRONMAN_CHESTPLATE.get().getDefaultInstance())) {
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
}
