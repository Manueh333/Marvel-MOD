package manueh.marvel_themod.common.containers;

import manueh.marvel_themod.common.items.InfinityGauntlet;
import manueh.marvel_themod.core.init.ContainerInit;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class InfinityGauntletContainer extends Container {
    private final InfinityGauntletItemStackHandler backpackItemStackHandler;

    private final ItemStack heldItem;

    public InfinityGauntletContainer(int windowId, PlayerInventory playerInv, PacketBuffer data) {
        this(windowId, playerInv, new InfinityGauntletItemStackHandler(), ItemStack.EMPTY);
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        if (stack.getItem() instanceof InfinityGauntlet) return false;
        return true;
    }

    public InfinityGauntletContainer(int windowId, PlayerInventory playerInv, InfinityGauntletItemStackHandler backpackItemStackHandler, ItemStack stack) {
        super((ContainerType) ContainerInit.INFINITY_GAUNTLET_CONTAINER.get(), windowId);
        this.backpackItemStackHandler = backpackItemStackHandler;
        this.heldItem = stack;

        for (int i = 0; i < 6; i++)
            addSlot((new SlotItemHandler((IItemHandler) backpackItemStackHandler, i, 35 + i * 18, 33)));
        int row;
        for (row = 0; row < 3; ) {
            for (int i = 0; i < 9; i++)
                addSlot(new Slot((IInventory) playerInv, 9 + row * 9 + i, 8 + i * 18, 84 + row * 18));
            row++;
        }
        for (int column = 0; column < 9; column++)
            addSlot(new Slot((IInventory) playerInv, column, 8 + column * 18, 142));
    }

    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasItem())
            return stack;
        ItemStack slotStack = slot.getItem();
        stack = slotStack.copy();
        if (index >= this.backpackItemStackHandler.getSlots()) {
            if (!moveItemStackTo(slotStack, 0, this.backpackItemStackHandler.getSlots(), false))
                return ItemStack.EMPTY;
        } else if (!moveItemStackTo(slotStack, this.backpackItemStackHandler.getSlots(), this.slots.size(), false)) {
            return ItemStack.EMPTY;
        }
        if (slotStack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        return stack;
    }


    @Override
    public boolean stillValid(PlayerEntity player) {
        return (player.getMainHandItem().equals(heldItem));

    }
}