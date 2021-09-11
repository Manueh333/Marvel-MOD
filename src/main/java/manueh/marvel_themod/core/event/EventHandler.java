package manueh.marvel_themod.core.event;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {



    @SubscribeEvent
    public static void rightClickEntity(PlayerInteractEvent.EntityInteract event) {
        Player p = event.getPlayer();
        Level level = event.getWorld();
        Entity target = event.getTarget();
        ItemStack stack = event.getItemStack();
        if(stack.getItem() == ItemInit.INFINITY_GAUNTLET.get().asItem()) {
            if(stack.getTag().getInt("gem") == 4 && stack.getTag().get("EntityData") == null && target.getType() != EntityType.ENDER_DRAGON && target.getType() != EntityType.WITHER) {
                stack.getOrCreateTag().get("EntityData");
                stack.getOrCreateTag().get("EntityName");
                stack.getTag().put("EntityData", target.serializeNBT());
                stack.getTag().putString("EntityName", target.getName().getString());
                target.discard();
                p.getCooldowns().addCooldown(stack.getItem(), 10);

            }
            if(stack.getTag().getInt("gem") == 5 && target.getType() != EntityType.ENDER_DRAGON && target.getType() != EntityType.WITHER) {
                target.kill();
                p.getCooldowns().addCooldown(stack.getItem(), 10);

            }
        }



    }


}
