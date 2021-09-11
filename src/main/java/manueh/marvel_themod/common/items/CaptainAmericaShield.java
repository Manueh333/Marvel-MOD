package manueh.marvel_themod.common.items;

import manueh.marvel_themod.common.entity.CaptainAmericaShieldEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CaptainAmericaShield extends SwordItem {
    public CaptainAmericaShield(IItemTier tier, int damage, float speed, Item.Properties properties) {
        super(tier, damage, speed, properties);
    }

    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
        if (!p_77659_1_.isClientSide) {
            CaptainAmericaShieldEntity entity = new CaptainAmericaShieldEntity(p_77659_1_, p_77659_2_);
            entity.setItem(itemstack);
            entity.shootFromRotation(p_77659_2_, p_77659_2_.getViewXRot(0), p_77659_2_.getViewYRot(0), 0.0F, 1.5F, 1.0F);
            p_77659_1_.addFreshEntity(entity);
            p_77659_2_.getCooldowns().addCooldown(this, 10);
        }

        return ActionResult.sidedSuccess(itemstack, p_77659_1_.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, world, tooltip, flagIn);
        Minecraft mc = Minecraft.getInstance();

        if (world == null || mc.player == null) {
            return;
        }
        boolean sneakPressed = Screen.hasShiftDown();

        if(sneakPressed) {
            tooltip.add(new TranslationTextComponent("tooltip.marvel_themod.captain_america_shield"));
        }else {
            tooltip.add(new TranslationTextComponent("tooltip.marvel_themod.hold_shift"));
        }

    }


}