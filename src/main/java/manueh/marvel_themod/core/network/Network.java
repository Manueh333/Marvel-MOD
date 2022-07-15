package manueh.marvel_themod.core.network;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.core.network.message.InputMessage;
import manueh.marvel_themod.core.network.message.PacketChangeArmorEntity;
import manueh.marvel_themod.core.network.message.PacketDamageEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Network {

    public static final ResourceLocation channelName = new ResourceLocation(Main.MODID, "network");
    public static final String networkVersion = new ResourceLocation(Main.MODID, "1.4").toString();

    public static SimpleChannel register() {
        final SimpleChannel network = NetworkRegistry.ChannelBuilder.named(channelName)
                .clientAcceptedVersions(version -> true)
                .serverAcceptedVersions(version -> true)
                .networkProtocolVersion(() -> networkVersion)
                .simpleChannel();

        network.registerMessage(1, InputMessage.class, InputMessage::encode, InputMessage::decode, InputMessage::handle);
        network.registerMessage(2, PacketDamageEntity.class, PacketDamageEntity::encode, PacketDamageEntity::decode, PacketDamageEntity::handle);
        network.registerMessage(3, PacketChangeArmorEntity.class, PacketChangeArmorEntity::encode, PacketChangeArmorEntity::decode, PacketChangeArmorEntity::handle);

        return network;
    }


}
