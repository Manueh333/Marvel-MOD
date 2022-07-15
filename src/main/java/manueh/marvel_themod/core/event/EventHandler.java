package manueh.marvel_themod.core.event;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.items.IronManArmor;
import manueh.marvel_themod.core.init.DimensionInit;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.Dimension;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.ExplosionEvent;
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
        if(player.level.dimension().equals(Dimension.NETHER)) {
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
        PlayerEntity player = null;
        if (event.getEntity() instanceof PlayerEntity) {
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
    public static void getTimeStone(PlayerInteractEvent.RightClickBlock event) {
        for(int i = 0; i < BlockTags.BEDS.getValues().size(); i++) {
            PlayerEntity player = event.getPlayer();
            World level = player.level;
            Block clickedBlock = level.getBlockState(event.getPos()).getBlock();
            if(clickedBlock.equals( BlockTags.BEDS.getValues().get(i))) {

                if(level.dimension().equals(DimensionInit.REALITY_DIMENSION)) {
                    player.inventory.add(ItemInit.TIME_GEM.get().getDefaultInstance());
                }
            }
        }
    }


    @SubscribeEvent
    public static void rightClickEntity(PlayerInteractEvent.EntityInteract event) {
        PlayerEntity p = event.getPlayer();
        World level = event.getWorld();
        Entity target = event.getTarget();
        ItemStack stack = event.getItemStack();
        if(stack.getItem() == ItemInit.INFINITY_GAUNTLET.get().asItem()) {
            if(stack.getTag().getInt("gem") == 4 && stack.getTag().get("EntityData") == null && target.getType() != EntityType.ENDER_DRAGON && target.getType() != EntityType.WITHER && target.getType() != EntityType.PLAYER) {
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
