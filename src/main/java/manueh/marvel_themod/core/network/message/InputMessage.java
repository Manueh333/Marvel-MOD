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


    public InputMessage() {

    }

    public static InputMessage decode(final PacketBuffer buffer) {

        return new InputMessage();
    }
    public static void encode(final InputMessage message, final PacketBuffer buffer) {
        buffer.writeByte(0);

    }

    public static void handle(InputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayerEntity player = context.getSender();
        ItemStack gauntlet = player.getMainHandItem();
        if(gauntlet.getItem() instanceof InfinityGauntlet) {
            INamedContainerProvider ncp = new InfinityGauntlet.InfinityGauntletContainerProvider((InfinityGauntlet) gauntlet.getItem(), gauntlet);
            NetworkHooks.openGui(player, ncp);
            context.setPacketHandled(false);
        }


    }


}
