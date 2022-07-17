package manueh.marvel_themod.common.blocks.containers;

import manueh.marvel_themod.common.items.InfinityGauntlet;
import manueh.marvel_themod.core.init.ContainerInit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class InfinityGauntletContainer extends AbstractContainerMenu {
    private final InfinityGauntletItemStackHandler backpackItemStackHandler;

    private final ItemStack heldItem;

    public InfinityGauntletContainer(int windowId, Inventory playerInv, FriendlyByteBuf data) {
        this(windowId, playerInv, new InfinityGauntletItemStackHandler(), ItemStack.EMPTY);
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        if (stack.getItem() instanceof InfinityGauntlet) return false;
        return true;
    }

    public InfinityGauntletContainer(int windowId, Inventory playerInv, InfinityGauntletItemStackHandler backpackItemStackHandler, ItemStack stack) {
        super((MenuType) ContainerInit.INFINITY_GAUNTLET_CONTAINER.get(), windowId);
        this.backpackItemStackHandler = backpackItemStackHandler;
        this.heldItem = stack;

        for (int i = 0; i < 6; i++)
            addSlot((new SlotItemHandler((IItemHandler) backpackItemStackHandler, i, 35 + i * 18, 33)));
        int row;
        for (row = 0; row < 3; ) {
            for (int i = 0; i < 9; i++)
                addSlot(new Slot((Container) playerInv, 9 + row * 9 + i, 8 + i * 18, 84 + row * 18));
            row++;
        }
        for (int column = 0; column < 9; column++)
            addSlot(new Slot((Container) playerInv, column, 8 + column * 18, 142));
    }

    public ItemStack quickMoveStack(Player player, int index) {
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
    public boolean stillValid(Player player) {
        return (player.getMainHandItem().equals(heldItem, true));

    }
}