package manueh.marvel_themod.core.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;

import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketDamageEntity
{


	public static void handle(PacketDamageEntity message, Supplier<NetworkEvent.Context> contextSupplier) {
		contextSupplier.get().getSender().getLevel().getEntity(message.ID).hurt(DamageSource.playerAttack(contextSupplier.get().getSender()), message.damage);

	}

	public static int ID;
	public static float damage;

	public PacketDamageEntity(int ID, float damage) {
		this.ID = ID;
		this.damage = damage;
	}
	

	public static PacketDamageEntity decode(final PacketBuffer buffer) {

		ID = buffer.readInt();
		damage = buffer.readFloat();
		return new PacketDamageEntity(ID, damage);
	}
	public static void encode(final PacketDamageEntity message, final PacketBuffer buffer) {
		buffer.writeInt(message.ID);
		buffer.writeFloat(message.damage);

	}

}