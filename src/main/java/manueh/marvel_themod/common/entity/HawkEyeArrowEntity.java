package manueh.marvel_themod.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class HawkEyeArrowEntity extends ArrowEntity {

    public HawkEyeArrowEntity(EntityType<? extends ArrowEntity> p_i50172_1_, World p_i50172_2_) {
        super(p_i50172_1_, p_i50172_2_);
    }

    public HawkEyeArrowEntity(World p_i46757_1_, double p_i46757_2_, double p_i46757_4_, double p_i46757_6_) {
        super(p_i46757_1_, p_i46757_2_, p_i46757_4_, p_i46757_6_);
    }

    public HawkEyeArrowEntity(World p_i46758_1_, LivingEntity p_i46758_2_) {
        super(p_i46758_1_, p_i46758_2_);
    }

    public void fillItemCategory(ItemGroup p_150895_1_, NonNullList<ItemStack> p_150895_2_) {
        for(Potion potion : Registry.POTION) {
            if (!potion.getEffects().isEmpty()) {
              //  p_150895_2_.add(PotionUtils.setPotion(new ItemStack(this), potion));
            }
        }

    }
}
