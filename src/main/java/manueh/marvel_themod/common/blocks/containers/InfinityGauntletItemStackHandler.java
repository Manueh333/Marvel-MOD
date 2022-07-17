package manueh.marvel_themod.common.blocks.containers;

import manueh.marvel_themod.common.items.InfinityGauntlet;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;


public class InfinityGauntletItemStackHandler extends ItemStackHandler {
  public static final int NUMBER_SLOTS = 6;
  
  public InfinityGauntletItemStackHandler() {
    super(6);
  }
  
  public boolean isItemValid(int slot, ItemStack stack) {
    if (slot < 0 || slot >= 6)
      throw new IllegalArgumentException("Invalid slot number: " + slot); 
    if (stack.isEmpty())
      return false;
    if(slot == 0) {
      if(stack.getItem() == ItemInit.TIME_GEM.get()) {

        return true;
      }
      else {
        return false;
      }
    }else if(slot == 1) {
      if(stack.getItem() == ItemInit.POWER_GEM.get()) {

        return true;
      }
      else {
        return false;
      }
    }else if(slot == 2) {
      if(stack.getItem() == ItemInit.SPACE_GEM.get()) {

        return true;
      }
      else {
        return false;
      }
    }
    else if(slot == 3) {
      if(stack.getItem() == ItemInit.REALITY_GEM.get()) {

        return true;
      }
      else {
        return false;
      }
    }
    else if(slot == 4) {
      if(stack.getItem() == ItemInit.SOUL_GEM.get()) {

        return true;
      }
      else {
        return false;
      }
    }else if(slot == 5) {
      if(stack.getItem() == ItemInit.MIND_GEM.get()) {

        return true;
      }
      else {
        return false;
      }
    }
    return !(stack.getItem() instanceof InfinityGauntlet);
  }


}
