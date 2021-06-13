package manueh.marvel.common.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class mjolnir extends SwordItem {

    public mjolnir(IItemTier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, world, tooltip, flagIn);
        Minecraft mc = Minecraft.getInstance();

        if (world == null || mc.player == null) {
            return;
        }
        boolean sneakPressed = Screen.hasShiftDown();

        if(sneakPressed) {
            tooltip.add(new TranslationTextComponent("tooltip.marvel.mjolnir"));
        }else {
            tooltip.add(new TranslationTextComponent("tooltip.marvel.hold_shift"));
        }

    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        player.addEffect(new EffectInstance(Effects.GLOWING, 40, 5));
        World world = entity.getCommandSenderWorld();
        LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
        lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
        world.addFreshEntity(lightning);

        stack.setDamageValue(stack.getDamageValue() + 1);

        // break if durability gets to 0
        if (stack.getDamageValue() == 0) stack.setCount(0);

        return super.interactLivingEntity(stack, player, entity, hand);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if(player != null) {
            boolean sneakPressed = player.isCrouching();
            if(sneakPressed) {
                //RAY END POINT - TO WHERE IT WILL TRAVEL TO
                Double rayLength = new Double(100);
                Vector3d playerRotation = player.getViewVector(0);
                Vector3d rayPath = playerRotation.scale(rayLength);

                //RAY START AND END POINTS
                Vector3d from = player.getEyePosition(0);
                Vector3d to = from.add(rayPath);

                //CREATE THE RAY
                RayTraceContext rayCtx = new RayTraceContext(from, to, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, null);
                //CAST THE RAY
                BlockRayTraceResult rayHit = world.clip(rayCtx);

                //CHECK THE RESULTS
                if (rayHit.getType() == RayTraceResult.Type.MISS){
                    //IF RAY MISSED
                }
                else {
                    //IF RAY HIT SOMETHING
                    Vector3d hitLocation = rayHit.getLocation();
                    player.addEffect(new EffectInstance(Effects.GLOWING, 40, 5));
                    LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
                    lightning.setPos(hitLocation.x, hitLocation.y, hitLocation.z);
                    world.addFreshEntity(lightning);
                    ItemStack stack = player.getItemInHand(hand);
                    stack.setDamageValue(stack.getDamageValue() + 1);

                    // break if durability gets to 0
                    if (stack.getDamageValue() == 0) stack.setCount(0);
                }
            }
        }

        return super.use(world, player, hand);
    }

}
