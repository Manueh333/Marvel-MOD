package manueh.marvel_themod.common.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class SpecialGemItem extends Item {

    int gem;

    public SpecialGemItem(Properties p_i48487_1_, int gem) {
        super(p_i48487_1_);
        this.gem = gem;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, world, tooltip, flagIn);
        Minecraft mc = Minecraft.getInstance();

        if (world == null || mc.player == null) {
            return;
        }

        tooltip.add(new TranslationTextComponent("tooltip.marvel_themod.gem_" + gem));


    }
}
