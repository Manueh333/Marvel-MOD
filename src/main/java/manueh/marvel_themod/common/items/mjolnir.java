package manueh.marvel_themod.common.items;

import manueh.marvel_themod.common.entity.MjolnirEntity;
import manueh.marvel_themod.core.init.BlockInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;

public class mjolnir extends SwordItem {

    public mjolnir(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
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
            tooltip.add(new TranslatableComponent("tooltip.marvel_themod.mjolnir"));
        }else {
            tooltip.add(new TranslatableComponent("tooltip.marvel_themod.hold_shift"));
        }

    }


    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {


        item.setCount(0);
        if(player.getCommandSenderWorld().getBlockState(new BlockPos(player.position())) == BlockInit.MJOLNIR_BLOCK.get().defaultBlockState()) {
            int rand = (int)(Math.random() * ((4 - 1) + 1)) + 1;
            if(rand == 1) {
                player.getCommandSenderWorld().setBlock(new BlockPos(player.position().add(1,1,0)), BlockInit.MJOLNIR_BLOCK.get().defaultBlockState(), Constants.BlockFlags.DEFAULT);

            }
            if(rand == 2) {
                player.getCommandSenderWorld().setBlock(new BlockPos(player.position().add(-1,1,0)), BlockInit.MJOLNIR_BLOCK.get().defaultBlockState(), Constants.BlockFlags.DEFAULT);

            }
            if(rand == 3) {
                player.getCommandSenderWorld().setBlock(new BlockPos(player.position().add(0,1,1)), BlockInit.MJOLNIR_BLOCK.get().defaultBlockState(), Constants.BlockFlags.DEFAULT);

            }
            if(rand == 4) {
                player.getCommandSenderWorld().setBlock(new BlockPos(player.position().add(0,1,-1)), BlockInit.MJOLNIR_BLOCK.get().defaultBlockState(), Constants.BlockFlags.DEFAULT);

            }
      }else {
            player.getCommandSenderWorld().setBlock(new BlockPos(player.position().add(0,1,0)), BlockInit.MJOLNIR_BLOCK.get().defaultBlockState(), Constants.BlockFlags.DEFAULT);

        }

        return super.onDroppedByPlayer(item, player);
    }

   /* @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        player.addEffect(new EffectInstance(Effects.GLOWING, 40, 5));
        World world = entity.getCommandSenderWorld();
        LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
        lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
        world.addFreshEntity(lightning);

       // stack.setDamageValue(stack.getDamageValue() + 1);

        // break if durability gets to 0
       // if (stack.getDamageValue() == 0) stack.setCount(0);

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
                   // ItemStack stack = player.getItemInHand(hand);
                   // stack.setDamageValue(stack.getDamageValue() + 1);

                    // break if durability gets to 0
                   // if (stack.getDamageValue() == 0) stack.setCount(0);
                }
            }
        }

        return super.use(world, player, hand);
    }
*/

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {     ItemStack itemstack = player.getItemInHand(hand);
        MjolnirEntity entity = new MjolnirEntity(level, player);
        entity.setItem(itemstack);
        entity.shootFromRotation(player, player.getViewXRot(0), player.getViewYRot(0), 0.0F, 1.5F, 1.0F);
        level.addFreshEntity(entity);
        player.getCooldowns().addCooldown(this, 10);
        return super.use(level, player, hand);
    }

}
