package manueh.marvel_themod.common.items;

import manueh.marvel_themod.common.entity.CaptainAmericaShieldEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CaptainAmericaShield extends SwordItem {
    public CaptainAmericaShield(Tier tier, int damage, float speed, Item.Properties properties) {
        super(tier, damage, speed, properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            CaptainAmericaShieldEntity entity = new CaptainAmericaShieldEntity(level, player);
            entity.setItem(itemstack);
            entity.shootFromRotation(player, player.getViewXRot(0), player.getViewYRot(0), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(entity);
            player.getCooldowns().addCooldown(this, 10);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
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
            tooltip.add(new TranslatableComponent("tooltip.marvel_themod.captain_america_shield"));
        }else {
            tooltip.add(new TranslatableComponent("tooltip.marvel_themod.hold_shift"));
        }

    }


}