package manueh.marvel_themod.common.items;



import com.mojang.math.Vector3d;

import manueh.marvel_themod.common.entity.PowerGemEntity;
import manueh.marvel_themod.core.init.BlockInit;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;

public class InfinityGauntlet extends Item {
    public InfinityGauntlet(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player p, InteractionHand hand) {
        ItemStack stack = p.getItemInHand(hand);
        if(p.isShiftKeyDown()) {
            int gem = stack.getOrCreateTag().getInt("gem");
            gem++;
            if(gem > 5) {
                gem = 0;
            }
            stack.getTag().putInt("gem", gem);
            if(level.isClientSide) {
                p.sendMessage(new TranslatableComponent("item.marvel_themod.infinity_gauntlet.gem_message", getGemName(stack)), Util.NIL_UUID);
            }
        }
        else if(getGem(stack) == 1) {
            ItemStack itemstack = p.getItemInHand(hand);
            PowerGemEntity entity = new PowerGemEntity(level, p);
            entity.setItem(new ItemStack(ItemInit.POWER_GEM.get()));
            entity.shootFromRotation(p, p.getXRot(), p.getYRot(), 0.0F, 3F, 1.0F);
            level.addFreshEntity(entity);
            p.getCooldowns().addCooldown(this, 10);

        }else if(getGem(stack) == 2) {
            if(p != null) {
                    //RAY END POINT - TO WHERE IT WILL TRAVEL TO
                    Double rayLength = new Double(100);
                    Vec3 playerRotation = p.getViewVector(0);
                    Vec3 rayPath = playerRotation.scale(rayLength);

                    //RAY START AND END POINTS
                    Vec3 from = p.getEyePosition(0);
                    Vec3 to = from.add(rayPath);

                    //CREATE THE RAY
                     ClipContext rayCtx = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, null);
                    //CAST THE RAY
                    HitResult rayHit = level.clip(rayCtx);

                    //CHECK THE RESULTS
                    if (rayHit.getType() == HitResult.Type.MISS){
                        //IF RAY MISSED
                    }
                    else {
                        //IF RAY HIT SOMETHING
                        Vec3 hitLocation = rayHit.getLocation();
                        p.teleportTo(hitLocation.x, hitLocation.y,hitLocation.z);
                        p.getCooldowns().addCooldown(this, 10);
                        // ItemStack stack = player.getItemInHand(hand);
                        // stack.setDamageValue(stack.getDamageValue() + 1);

                        // break if durability gets to 0
                        // if (stack.getDamageValue() == 0) stack.setCount(0);
                    }
                }

        }
        return InteractionResultHolder.success(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        ItemStack stack = context.getItemInHand();
        if(context.getPlayer().isShiftKeyDown()) {
            int gem = stack.getOrCreateTag().getInt("gem");
            gem++;
            if(gem > 5) {
                gem = 0;
            }
            stack.getTag().putInt("gem", gem);
            if(world.isClientSide) {
                context.getPlayer().sendMessage(new TranslatableComponent("item.marvel_themod.infinity_gauntlet.gem_message", getGemName(stack)), Util.NIL_UUID);
            }
        }else if(getGem(context.getItemInHand()) == 0) {
            BlockPos blockpos = context.getClickedPos();
            BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
            if (applyBonemeal(context.getItemInHand(), world, blockpos, context.getPlayer())) {
                if (!world.isClientSide) {
                    world.levelEvent(2005, blockpos, 0);
                }

                return InteractionResult.sidedSuccess(world.isClientSide);
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
                    if(world.getBlockState(context.getClickedPos().subtract(new Vec3i(x, 0, 0) )) != Blocks.AIR.defaultBlockState() && world.getBlockState(context.getClickedPos().subtract(new Vec3i(x, 0, 0) )) != Blocks.CAVE_AIR.defaultBlockState() && world.getBlockState(context.getClickedPos().subtract(new Vec3i(x, 0, 0) )) != Blocks.VOID_AIR.defaultBlockState()) {
                        world.setBlockAndUpdate(context.getClickedPos().subtract(new Vec3i(x, 0, 0)), BlockInit.CORRUPTED_BLOCK.get().defaultBlockState());
                    }
                    for(int y = -8; y<8; y++) {
                        if(world.getBlockState(context.getClickedPos().subtract(new Vec3i(x, y, 0) )) != Blocks.AIR.defaultBlockState() && world.getBlockState(context.getClickedPos().subtract(new Vec3i(x, y, 0) )) != Blocks.CAVE_AIR.defaultBlockState() && world.getBlockState(context.getClickedPos().subtract(new Vec3i(x, y, 0) )) != Blocks.VOID_AIR.defaultBlockState()) {
                            world.setBlockAndUpdate(context.getClickedPos().subtract(new Vec3i(x, y, 0)), BlockInit.CORRUPTED_BLOCK.get().defaultBlockState());
                        }
                        for(int z = -8; z<8; z++) {
                            if(world.getBlockState(context.getClickedPos().subtract(new Vec3i(x, y, z) )) != Blocks.AIR.defaultBlockState() && world.getBlockState(context.getClickedPos().subtract(new Vec3i(x, y, z) )) != Blocks.CAVE_AIR.defaultBlockState() && world.getBlockState(context.getClickedPos().subtract(new Vec3i(x, y, z) )) != Blocks.VOID_AIR.defaultBlockState()) {
                                world.setBlockAndUpdate(context.getClickedPos().subtract(new Vec3i(x, y, z)), BlockInit.CORRUPTED_BLOCK.get().defaultBlockState());
                            }
                        }
                    }
                }
            }

        }
        else if(getGem(stack) == 4) {
            CompoundTag entityData = (CompoundTag) stack.getTag().get("EntityData");
            if(entityData != null) {
                Entity entity = EntityType.create(entityData, world).get();
                entity.setPos(context.getClickedPos().getX() + 0.5f, context.getClickedPos().getY() + 1f, context.getClickedPos().getZ() + 0.5f);
                world.addFreshEntity(entity);
                stack.getTag().remove("EntityData");
                stack.getTag().remove("EntityName");
                context.getPlayer().getCooldowns().addCooldown(this, 10);

            }


        }
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    public static boolean applyBonemeal(ItemStack p_40628_, Level p_40629_, BlockPos p_40630_, net.minecraft.world.entity.player.Player player) {
        BlockState blockstate = p_40629_.getBlockState(p_40630_);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, p_40629_, p_40630_, blockstate, p_40628_);
        if (hook != 0) return hook > 0;
        if (blockstate.getBlock() instanceof BonemealableBlock) {
            BonemealableBlock bonemealableblock = (BonemealableBlock)blockstate.getBlock();
            if (bonemealableblock.isValidBonemealTarget(p_40629_, p_40630_, blockstate, p_40629_.isClientSide)) {
                if (p_40629_ instanceof ServerLevel) {
                    if (bonemealableblock.isBonemealSuccess(p_40629_, p_40629_.random, p_40630_, blockstate)) {
                        bonemealableblock.performBonemeal((ServerLevel)p_40629_, p_40629_.random, p_40630_, blockstate);
                    }
                    
                }

                return true;
            }
        }

        return false;
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(new TranslatableComponent("item.marvel_themod.infinity_gauntlet.current.gem", getGemName(stack)));
        if(getGem(stack) == 4 && stack.getTag().getString("EntityName") != null) {
            tooltip.add(new TranslatableComponent("item.marvel_themod.infinity_gauntlet.current.entity", stack.getTag().getString("EntityName")));

        }


        Minecraft mc = Minecraft.getInstance();

        if (level == null || mc.player == null) {
            return;
        }
        boolean sneakPressed = Screen.hasShiftDown();

        if(sneakPressed) {
            tooltip.add(new TranslatableComponent("tooltip.marvel_themod.infinity_gauntlet_1"));
            tooltip.add(new TranslatableComponent("tooltip.marvel_themod.infinity_gauntlet_second_" + getGem(stack)));
        }else {
            tooltip.add(new TranslatableComponent("tooltip.marvel_themod.hold_shift"));
        }
    }

    public int getGem(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt("gem") : 0;
    }

    public TranslatableComponent getGemName(ItemStack stack) {

        int gem = (int) getGem(stack);
        if(gem == 0) {
            return new TranslatableComponent("marvel_themod.time.gem");
        }
        else if(gem == 1) {
            return new TranslatableComponent("marvel_themod.power.gem");
        }else if(gem == 2) {
            return new TranslatableComponent("marvel_themod.space.gem");
        }else if(gem == 3) {
            return new TranslatableComponent("marvel_themod.reality.gem");
        }else if(gem == 4) {
            return new TranslatableComponent("marvel_themod.soul.gem");
        }else if(gem == 5) {
            return new TranslatableComponent("marvel_themod.mind.gem");
        }

        return new TranslatableComponent("marvel_themod.any.gem");
    }


    public void setTag(String name, CompoundTag compoundNBT, ItemStack stack) {
        stack.getTag().put("EntityData", compoundNBT);
    }
    public Tag getTag(String name, ItemStack stack) {
        return stack.getOrCreateTag().get(name);
    }

}
