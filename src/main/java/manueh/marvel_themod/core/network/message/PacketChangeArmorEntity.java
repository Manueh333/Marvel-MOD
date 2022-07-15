package manueh.marvel_themod.core.network.message;

import manueh.marvel_themod.client.ClientEvents;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketChangeArmorEntity
{


	public static void handle(PacketChangeArmorEntity message, Supplier<NetworkEvent.Context> contextSupplier) {

		Entity entity = contextSupplier.get().getSender().getLevel().getEntity(message.ID);
		if(entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entity;
			if(fullIronManArmor(player)) {
				player.setItemSlot(EquipmentSlotType.FEET, ItemStack.EMPTY);
				player.setItemSlot(EquipmentSlotType.LEGS, ItemStack.EMPTY);
				player.setItemSlot(EquipmentSlotType.CHEST, ItemInit.REACTOR.get().getDefaultInstance());
				player.setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
			}else {
				player.setItemSlot(EquipmentSlotType.FEET, ItemInit.IRONMAN_BOOTS.get().getDefaultInstance());
				player.setItemSlot(EquipmentSlotType.LEGS, ItemInit.IRONMAN_LEGGINS.get().getDefaultInstance());
				player.setItemSlot(EquipmentSlotType.CHEST, ItemInit.IRONMAN_CHESTPLATE.get().getDefaultInstance());
				player.setItemSlot(EquipmentSlotType.HEAD, ItemInit.IRONMAN_HELMET.get().getDefaultInstance());
			}
			ClientEvents.changingArmor = false;
		}
	}
	private static boolean fullIronManArmor(PlayerEntity player) {
		return player.getItemBySlot(EquipmentSlotType.FEET).getItem() == (ItemInit.IRONMAN_BOOTS.get()) && player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == ItemInit.IRONMAN_LEGGINS.get() && player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == ItemInit.IRONMAN_CHESTPLATE.get() && player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ItemInit.IRONMAN_HELMET.get();

	}

	public static int ID;


	public PacketChangeArmorEntity(int ID) {
		this.ID = ID;

	}
	

	public static PacketChangeArmorEntity decode(final PacketBuffer buffer) {

		ID = buffer.readInt();
		return new PacketChangeArmorEntity(ID);
	}
	public static void encode(final PacketChangeArmorEntity message, final PacketBuffer buffer) {
		buffer.writeInt(message.ID);


	}

}