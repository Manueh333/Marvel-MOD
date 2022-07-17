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

    public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
        if (!p_77659_1_.isClientSide) {
            CaptainAmericaShieldEntity entity = new CaptainAmericaShieldEntity(p_77659_1_, p_77659_2_);
            entity.setItem(itemstack);
            entity.shootFromRotation(p_77659_2_, p_77659_2_.getViewXRot(0), p_77659_2_.getViewYRot(0), 0.0F, 1.5F, 1.0F);
            p_77659_1_.addFreshEntity(entity);
            p_77659_2_.getCooldowns().addCooldown(this, 10);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, p_77659_1_.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, world, tooltip, flagIn);
        Minecraft mc = Minecraft.getInstance();

        if (world == null || mc.player == null) {
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