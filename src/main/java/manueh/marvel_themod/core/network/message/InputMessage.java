package manueh.marvel_themod.core.network.message;

import manueh.marvel_themod.common.items.InfinityGauntlet;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import org.lwjgl.system.CallbackI;

import java.util.function.Supplier;

public class InputMessage {

    public ItemStack stack;

    public InputMessage(ItemStack stack) {
        this.stack = stack;
    }

    public static InputMessage decode(final PacketBuffer buffer) {
        buffer.readByte();
        return new InputMessage(buffer.readItem());
    }
    public static void encode(final InputMessage message, final PacketBuffer buffer) {
        buffer.writeByte(0);
        buffer.writeItemStack(message.stack, true);
    }

    public static void handle(InputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork( () ->
                {
                    ServerPlayerEntity player = context.getSender();
                    INamedContainerProvider ncp = new InfinityGauntlet.InfinityGauntletContainerProvider((InfinityGauntlet)player.getMainHandItem().getItem(), message.stack);
                    NetworkHooks.openGui((ServerPlayerEntity)player, ncp);
                }
                );
        context.setPacketHandled(true);

    }


}
