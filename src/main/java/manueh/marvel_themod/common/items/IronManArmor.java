package manueh.marvel_themod.common.items;

import manueh.marvel_themod.client.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class IronManArmor extends ArmorItem {
    public IronManArmor(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Item.Properties p_40388_) {
        super(p_40386_, p_40387_, p_40388_);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        Minecraft mc = Minecraft.getInstance();

        if (level == null || mc.player == null) {
            return;
        }
        boolean sneakPressed = Screen.hasShiftDown();


        if(sneakPressed) {
            tooltip.add(new TranslatableComponent("tooltip.marvel_themod.ironman_armor",  ClientEvents.keyFly.getKey().getDisplayName()));
        }else {
            tooltip.add(new TranslatableComponent("tooltip.marvel_themod.hold_shift"));
        }


}


}
