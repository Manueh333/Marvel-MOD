package manueh.marvel_themod.common.items;

import com.google.common.collect.ImmutableMultimap;
import manueh.marvel_themod.client.ClientEvents;
import manueh.marvel_themod.common.entity.MjolnirEntity;
import manueh.marvel_themod.common.entity.PowerGenEntity;
import manueh.marvel_themod.core.init.BlockInit;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InfinityGauntlet extends Item {
    public InfinityGauntlet(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity p, Hand hand) {
        ItemStack stack = p.getItemInHand(hand);
        if(p.isShiftKeyDown()) {
            int gem = stack.getOrCreateTag().getInt("gem");
            gem++;
            if(gem > 5) {
                gem = 0;
            }
            stack.getTag().putInt("gem", gem);
            if(level.isClientSide) {
                p.sendMessage(new TranslationTextComponent("item.marvel_themod.infinity_gauntlet.gem_message", getGemName(stack)), Util.NIL_UUID);
            }
        }
        else if(getGem(stack) == 1) {
            ItemStack itemstack = p.getItemInHand(hand);
            PowerGenEntity entity = new PowerGenEntity(level, p);
            entity.setItem(new ItemStack(ItemInit.POWER_GEM.get()));
            entity.shootFromRotation(p, p.xRot, p.yRot, 0.0F, 3F, 1.0F);
            level.addFreshEntity(entity);
            p.getCooldowns().addCooldown(this, 10);

        }else if(getGem(stack) == 2) {
            if(p != null) {
                    //RAY END POINT - TO WHERE IT WILL TRAVEL TO
                    Double rayLength = new Double(100);
                    Vector3d playerRotation = p.getViewVector(0);
                    Vector3d rayPath = playerRotation.scale(rayLength);

                    //RAY START AND END POINTS
                    Vector3d from = p.getEyePosition(0);
                    Vector3d to = from.add(rayPath);

                    //CREATE THE RAY
                    RayTraceContext rayCtx = new RayTraceContext(from, to, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, null);
                    //CAST THE RAY
                    BlockRayTraceResult rayHit = level.clip(rayCtx);

                    //CHECK THE RESULTS
                    if (rayHit.getType() == RayTraceResult.Type.MISS){
                        //IF RAY MISSED
                    }
                    else {
                        //IF RAY HIT SOMETHING
                        Vector3d hitLocation = rayHit.getLocation();
                        p.teleportTo(hitLocation.x, hitLocation.y,hitLocation.z);
                        p.getCooldowns().addCooldown(this, 10);
                        // ItemStack stack = player.getItemInHand(hand);
                        // stack.setDamageValue(stack.getDamageValue() + 1);

                        // break if durability gets to 0
                        // if (stack.getDamageValue() == 0) stack.setCount(0);
                    }
                }

        }
        return ActionResult.success(stack);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        ItemStack stack = context.getItemInHand();
        if(context.getPlayer().isShiftKeyDown()) {
            int gem = stack.getOrCreateTag().getInt("gem");
            gem++;
            if(gem > 5) {
                gem = 0;
            }
            stack.getTag().putInt("gem", gem);
            if(world.isClientSide) {
               context.getPlayer().sendMessage(new TranslationTextComponent("item.marvel_themod.infinity_gauntlet.gem_message", getGemName(stack)), Util.NIL_UUID);
            }
        }else if(getGem(context.getItemInHand()) == 0) {
            BlockPos blockpos = context.getClickedPos();
            BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
            if (applyBonemeal(context.getItemInHand(), world, blockpos, context.getPlayer())) {
                if (!world.isClientSide) {
                    world.levelEvent(2005, blockpos, 0);
                }

                return ActionResultType.sidedSuccess(world.isClientSide);
            }

        }else if(getGem(stack) == 3) {
          /*  if(world.isRaining()) {
                world.setRainLevel(0);
            }else {
                world.setRainLevel(2);

            }*/
            if(!context.getLevel().isClientSide()) {
                context.getPlayer().getCooldowns().addCooldown(this, 10);
                world.setBlockAndUpdate(context.getClickedPos(), BlockInit.CORRUPTED_BLOCK.get().defaultBlockState());

                for(int x = -8; x<8; x++) {
                    if(world.getBlockState(context.getClickedPos().subtract(new Vector3i(x, 0, 0) )) != Blocks.AIR.defaultBlockState() && world.getBlockState(context.getClickedPos().subtract(new Vector3i(x, 0, 0) )) != Blocks.CAVE_AIR.defaultBlockState() && world.getBlockState(context.getClickedPos().subtract(new Vector3i(x, 0, 0) )) != Blocks.VOID_AIR.defaultBlockState()) {
                        world.setBlockAndUpdate(context.getClickedPos().subtract(new Vector3i(x, 0, 0)), BlockInit.CORRUPTED_BLOCK.get().defaultBlockState());
                    }
                    for(int y = -8; y<8; y++) {
                        if(world.getBlockState(context.getClickedPos().subtract(new Vector3i(x, y, 0) )) != Blocks.AIR.defaultBlockState() && world.getBlockState(context.getClickedPos().subtract(new Vector3i(x, y, 0) )) != Blocks.CAVE_AIR.defaultBlockState() && world.getBlockState(context.getClickedPos().subtract(new Vector3i(x, y, 0) )) != Blocks.VOID_AIR.defaultBlockState()) {
                            world.setBlockAndUpdate(context.getClickedPos().subtract(new Vector3i(x, y, 0)), BlockInit.CORRUPTED_BLOCK.get().defaultBlockState());
                        }
                        for(int z = -8; z<8; z++) {
                            if(world.getBlockState(context.getClickedPos().subtract(new Vector3i(x, y, z) )) != Blocks.AIR.defaultBlockState() && world.getBlockState(context.getClickedPos().subtract(new Vector3i(x, y, z) )) != Blocks.CAVE_AIR.defaultBlockState() && world.getBlockState(context.getClickedPos().subtract(new Vector3i(x, y, z) )) != Blocks.VOID_AIR.defaultBlockState()) {
                                world.setBlockAndUpdate(context.getClickedPos().subtract(new Vector3i(x, y, z)), BlockInit.CORRUPTED_BLOCK.get().defaultBlockState());
                            }
                        }
                    }
                }
            }

        }
        else if(getGem(stack) == 4) {
            CompoundNBT entityData = (CompoundNBT) stack.getTag().get("EntityData");
            if(entityData != null) {
                Entity entity = EntityType.create(entityData, world).get();
                entity.setPos(context.getClickedPos().getX() + 0.5f, context.getClickedPos().getY() + 1f, context.getClickedPos().getZ() + 0.5f);
                world.addFreshEntity(entity);
                stack.getTag().remove("EntityData");
                stack.getTag().remove("EntityName");
                context.getPlayer().getCooldowns().addCooldown(this, 10);

            }


        }
        return ActionResultType.sidedSuccess(world.isClientSide);
    }
    public static boolean applyBonemeal(ItemStack p_195966_0_, World p_195966_1_, BlockPos p_195966_2_, net.minecraft.entity.player.PlayerEntity player) {
        BlockState blockstate = p_195966_1_.getBlockState(p_195966_2_);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, p_195966_1_, p_195966_2_, blockstate, p_195966_0_);
        if (hook != 0) return hook > 0;
        if (blockstate.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable)blockstate.getBlock();
            if (igrowable.isValidBonemealTarget(p_195966_1_, p_195966_2_, blockstate, p_195966_1_.isClientSide)) {
                if (p_195966_1_ instanceof ServerWorld) {
                    if (igrowable.isBonemealSuccess(p_195966_1_, p_195966_1_.random, p_195966_2_, blockstate)) {
                        igrowable.performBonemeal((ServerWorld)p_195966_1_, p_195966_1_.random, p_195966_2_, blockstate);
                    }

                }

                return true;
            }
        }

        return false;
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World level, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(new TranslationTextComponent("item.marvel_themod.infinity_gauntlet.current.gem", getGemName(stack)));
        if(getGem(stack) == 4 && stack.getTag().getString("EntityName") != null) {
            tooltip.add(new TranslationTextComponent("item.marvel_themod.infinity_gauntlet.current.entity", stack.getTag().getString("EntityName")));

        }


        Minecraft mc = Minecraft.getInstance();

        if (level == null || mc.player == null) {
            return;
        }
        boolean sneakPressed = Screen.hasShiftDown();

        if(sneakPressed) {
            tooltip.add(new TranslationTextComponent("tooltip.marvel_themod.infinity_gauntlet_1"));
            tooltip.add(new TranslationTextComponent("tooltip.marvel_themod.infinity_gauntlet_second_" + getGem(stack)));
        }else {
            tooltip.add(new TranslationTextComponent("tooltip.marvel_themod.hold_shift"));
        }
    }

    public int getGem(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt("gem") : 0;
    }

    public TranslationTextComponent getGemName(ItemStack stack) {

        int gem = (int) getGem(stack);
        if(gem == 0) {
            return new TranslationTextComponent("marvel_themod.time.gem");
        }
        else if(gem == 1) {
            return new TranslationTextComponent("marvel_themod.power.gem");
        }else if(gem == 2) {
            return new TranslationTextComponent("marvel_themod.space.gem");
        }else if(gem == 3) {
            return new TranslationTextComponent("marvel_themod.reality.gem");
        }else if(gem == 4) {
            return new TranslationTextComponent("marvel_themod.soul.gem");
        }else if(gem == 5) {
            return new TranslationTextComponent("marvel_themod.mind.gem");
        }

        return new TranslationTextComponent("marvel_themod.any.gem");
    }


    public void particleAnimation(World level, PlayerEntity player) {
        level.addParticle(new RedstoneParticleData( 255, 255, 255,1), player.getX(), player.getY(), player.getZ(), 0, 0, 0);

    }


    public void setTag(String name, CompoundNBT compoundNBT, ItemStack stack) {
        stack.getTag().put("EntityData", compoundNBT);
    }
    public INBT getTag(String name, ItemStack stack) {
        return stack.getOrCreateTag().get(name);
    }

}
