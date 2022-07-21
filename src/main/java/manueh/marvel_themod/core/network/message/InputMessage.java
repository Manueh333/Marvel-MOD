package manueh.marvel_themod.core.network.message;

import manueh.marvel_themod.common.items.InfinityGauntlet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;


public class InputMessage {


    public InputMessage() {

    }

    public static InputMessage decode(final FriendlyByteBuf buffer) {

        return new InputMessage();
    }
    public static void encode(final InputMessage message, final FriendlyByteBuf buffer) {
        buffer.writeByte(0);

    }

    public static void handle(InputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        ItemStack gauntlet = player.getMainHandItem();
        if(gauntlet.getItem() instanceof InfinityGauntlet) {
            MenuProvider ncp = new InfinityGauntlet.InfinityGauntletContainerProvider((InfinityGauntlet) gauntlet.getItem(), gauntlet);
            NetworkHooks.openGui(player, ncp);
            context.setPacketHandled(false);
        }


    }


}
