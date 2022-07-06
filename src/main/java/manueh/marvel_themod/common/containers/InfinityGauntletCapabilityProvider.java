package manueh.marvel_themod.common.containers;

import javax.annotation.Nonnull;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class InfinityGauntletCapabilityProvider implements ICapabilitySerializable<INBT> {
  private InfinityGauntletItemStackHandler backpackItemStackHandler;
  
  @Nonnull
  private InfinityGauntletItemStackHandler getCachedInventory() {
    if (this.backpackItemStackHandler == null)
      this.backpackItemStackHandler = new InfinityGauntletItemStackHandler();
    return this.backpackItemStackHandler;
  }
  
  private final LazyOptional<InfinityGauntletItemStackHandler> lazyInitialisationSupplier = LazyOptional.of(this::getCachedInventory);
  

  public <T> LazyOptional<T> getCapability( Capability<T> cap, Direction side) {
    if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
      return (LazyOptional)this.lazyInitialisationSupplier; 
    return LazyOptional.empty();
  }
  
  public INBT serializeNBT() {
    return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT( getCachedInventory(), null);
  }
  
  public void deserializeNBT(INBT nbt) {
    CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT( getCachedInventory(), null, nbt);
  }
}
