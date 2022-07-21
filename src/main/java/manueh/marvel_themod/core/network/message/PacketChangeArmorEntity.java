package manueh.marvel_themod.core.network.message;

import manueh.marvel_themod.client.ClientEvents;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;


import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketChangeArmorEntity
{


	public static void handle(PacketChangeArmorEntity message, Supplier<NetworkEvent.Context> contextSupplier) {

		Entity entity = contextSupplier.get().getSender().getLevel().getEntity(message.ID);
		if(entity instanceof Player) {
			Player player = (Player) entity;
			if(fullIronManArmor(player)) {
				player.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
				player.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
				player.setItemSlot(EquipmentSlot.CHEST, ItemInit.REACTOR.get().getDefaultInstance());
				player.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
			}else {
				player.setItemSlot(EquipmentSlot.FEET, ItemInit.IRONMAN_BOOTS.get().getDefaultInstance());
				player.setItemSlot(EquipmentSlot.LEGS, ItemInit.IRONMAN_LEGGINS.get().getDefaultInstance());
				player.setItemSlot(EquipmentSlot.CHEST, ItemInit.IRONMAN_CHESTPLATE.get().getDefaultInstance());
				player.setItemSlot(EquipmentSlot.HEAD, ItemInit.IRONMAN_HELMET.get().getDefaultInstance());
			}
			ClientEvents.changingArmor = false;
		}
	}
	private static boolean fullIronManArmor(Player player) {
		return player.getItemBySlot(EquipmentSlot.FEET).getItem() == (ItemInit.IRONMAN_BOOTS.get()) && player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.IRONMAN_LEGGINS.get() && player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.IRONMAN_CHESTPLATE.get() && player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.IRONMAN_HELMET.get();

	}

	public static int ID;


	public PacketChangeArmorEntity(int ID) {
		this.ID = ID;

	}
	

	public static PacketChangeArmorEntity decode(final FriendlyByteBuf buffer) {

		ID = buffer.readInt();
		return new PacketChangeArmorEntity(ID);
	}
	public static void encode(final PacketChangeArmorEntity message, final FriendlyByteBuf buffer) {
		buffer.writeInt(message.ID);


	}

}