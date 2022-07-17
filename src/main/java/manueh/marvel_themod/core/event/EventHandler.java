package manueh.marvel_themod.core.event;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.items.IronManArmor;
import manueh.marvel_themod.core.init.DimensionInit;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {




    @SubscribeEvent
    public static void drop(ItemTossEvent event) {
        ItemEntity drops = event.getEntityItem();
        if(drops.getItem().getItem() instanceof IronManArmor) {
            if (event.isCancelable() )
            {
                event.setCanceled(true);
            }

        }
        Entity player = event.getEntity();
        if(player.level.dimension().equals(LevelStem.NETHER)) {
            int count = drops.getItem().getCount();
            if(drops.getItem().getItem().equals(Items.EMERALD)) {
                ItemStack dropStack = ItemInit.REALITY_GEM.get().getDefaultInstance();
                dropStack.setCount(count);
                drops.setItem(dropStack);
            }

        }
    }

    @SubscribeEvent
    public static void fallDamage(LivingDamageEvent event) {
        Player player = null;
        if (event.getEntity() instanceof Player) {
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
    public static void getTimeStone(PlayerInteractEvent.RightClickBlock event) {
        for(int i = 0; i < BlockTags.BEDS.getValues().size(); i++) {
            Player player = event.getPlayer();
            Level level = player.level;
            Block clickedBlock = level.getBlockState(event.getPos()).getBlock();
            if(clickedBlock.equals( BlockTags.BEDS.getValues().get(i))) {

                if(level.dimension().equals(DimensionInit.REALITY_DIMENSION)) {
                    player.getInventory().add(ItemInit.TIME_GEM.get().getDefaultInstance());
                }
            }
        }
    }


    @SubscribeEvent
    public static void rightClickEntity(PlayerInteractEvent.EntityInteract event) {
        Player p = event.getPlayer();
        Level level = event.getWorld();
        Entity target = event.getTarget();
        ItemStack stack = event.getItemStack();
        if(stack.getItem() == ItemInit.INFINITY_GAUNTLET.get().asItem()) {
            if(stack.getTag().getInt("gem") == 4 && stack.getTag().get("EntityData") == null && target.getType() != EntityType.ENDER_DRAGON && target.getType() != EntityType.WITHER && target.getType() != EntityType.PLAYER) {
                stack.getOrCreateTag().get("EntityData");
                stack.getOrCreateTag().get("EntityName");
                stack.getTag().put("EntityData", target.serializeNBT());
                stack.getTag().putString("EntityName", target.getName().getString());
                target.remove(Entity.RemovalReason.valueOf("removal_reason.marvel_themod.saved_on_soul_gem"));
                p.getCooldowns().addCooldown(stack.getItem(), 10);

            }
            if(stack.getTag().getInt("gem") == 5 && target.getType() != EntityType.ENDER_DRAGON && target.getType() != EntityType.WITHER) {
                target.kill();
                p.getCooldowns().addCooldown(stack.getItem(), 10);

            }
        }
    }
}
