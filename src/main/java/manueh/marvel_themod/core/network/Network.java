package manueh.marvel_themod.core.network;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.core.network.message.InputMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Network {

    public static final ResourceLocation channelName = new ResourceLocation(Main.MODID, "network");
    public static final String networkVersion = new ResourceLocation(Main.MODID, "1").toString();

    public static SimpleChannel register() {
        final SimpleChannel network = NetworkRegistry.ChannelBuilder.named(channelName)
                .clientAcceptedVersions(version -> true)
                .serverAcceptedVersions(version -> true)
                .networkProtocolVersion(() -> networkVersion)
                .simpleChannel();

        network.registerMessage(1, InputMessage.class, InputMessage::encode, InputMessage::decode, InputMessage::handle);


        return network;
    }


}
