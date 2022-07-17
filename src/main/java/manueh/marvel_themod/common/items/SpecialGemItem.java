package manueh.marvel_themod.common.items;

import net.minecraft.client.Minecraft;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class SpecialGemItem extends Item {

    int gem;

    public SpecialGemItem(Properties p_i48487_1_, int gem) {
        super(p_i48487_1_);
        this.gem = gem;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, world, tooltip, flagIn);
        Minecraft mc = Minecraft.getInstance();

        if (world == null || mc.player == null) {
            return;
        }

        tooltip.add(new TranslatableComponent("tooltip.marvel_themod.gem_" + gem));


    }
}
