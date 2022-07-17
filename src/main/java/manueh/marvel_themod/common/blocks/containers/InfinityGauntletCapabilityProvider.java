package manueh.marvel_themod.common.blocks.containers;

import javax.annotation.Nonnull;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.function.Consumer;

public class InfinityGauntletCapabilityProvider implements ICapabilitySerializable<Tag> {
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


  @Override
  public Tag serializeNBT() {
    return null;
  }

  @Override
  public void deserializeNBT(Tag nbt) {

  }
}
