package manueh.marvel_themod.common.items;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.containers.InfinityGauntletCapabilityProvider;
import manueh.marvel_themod.common.containers.InfinityGauntletContainer;
import manueh.marvel_themod.common.containers.InfinityGauntletItemStackHandler;
import manueh.marvel_themod.common.entity.PowerGenEntity;
import manueh.marvel_themod.core.init.BlockInit;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.List;

public class InfinityGauntlet extends Item {
    public InfinityGauntlet(Properties properties) {
        super(properties);

    }

    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity p, Hand hand) {
        ItemStack stack = p.getItemInHand(hand);
        if (p.isShiftKeyDown()) {

            int gem = stack.getOrCreateTag().getInt("gem");

            boolean tried = false;
            boolean triggered = false;

            hasGemInInventory(stack);

            Main.LOGGER.debug("Stack in 0 is: " + getBackpackItemStackHandler(stack).getStackInSlot(0).getItem());
            if (gem == 0) {
                if (hasGem(stack, 1)) {
                    gem = 1;
                } else if (hasGem(stack, 2)) {
                    gem = 2;
                } else if (hasGem(stack, 3)) {
                    gem = 3;
                } else if (hasGem(stack, 4)) {
                    gem = 4;
                } else if (hasGem(stack, 5)) {
                    gem = 5;
                }

            } else if (gem == 1) {

                if (hasGem(stack, 2)) {
                    gem = 2;
                } else if (hasGem(stack, 3)) {
                    gem = 3;
                } else if (hasGem(stack, 4)) {
                    gem = 4;
                } else if (hasGem(stack, 5)) {
                    gem = 5;
                } else if (hasGem(stack, 0)) {
                    gem = 0;
                }
            }
         else if (gem == 2) {

                if (hasGem(stack, 3)) {
                    gem = 3;
                } else if (hasGem(stack, 4)) {
                    gem = 4;
                } else if (hasGem(stack, 5)) {
                    gem = 5;
                } else if (hasGem(stack, 0)) {
                    gem = 0;
                }else if (hasGem(stack, 1)) {
                    gem = 1;
                }
            }else if (gem == 3) {

                if (hasGem(stack, 4)) {
                    gem = 4;
                } else if (hasGem(stack, 5)) {
                    gem = 5;
                } else if (hasGem(stack, 0)) {
                    gem = 0;
                }else if (hasGem(stack, 1)) {
                    gem = 1;
                }else if (hasGem(stack, 2)) {
                    gem = 2;
                }
            }else if (gem == 4) {

                if (hasGem(stack, 5)) {
                    gem = 5;
                } else if (hasGem(stack, 0)) {
                    gem = 0;
                }else if (hasGem(stack, 1)) {
                    gem = 1;
                }else if (hasGem(stack, 2)) {
                    gem = 2;
                }else if (hasGem(stack, 3)) {
                    gem = 3;
                }
            }else if (gem == 5) {

                if (hasGem(stack, 0)) {
                    gem = 0;
                }else if (hasGem(stack, 1)) {
                    gem = 1;
                }else if (hasGem(stack, 2)) {
                    gem = 2;
                }else if (hasGem(stack, 3)) {
                    gem = 3;
                }else if (hasGem(stack, 4)) {
                    gem = 4;
                }
            }
            else if (gem == 6) {

                if (hasGem(stack, 0)) {
                    gem = 0;
                }else if (hasGem(stack, 1)) {
                    gem = 1;
                }else if (hasGem(stack, 2)) {
                    gem = 2;
                }else if (hasGem(stack, 3)) {
                    gem = 3;
                }else if (hasGem(stack, 4)) {
                    gem = 4;
                }else if (hasGem(stack, 5)) {
                    gem = 5;
                }
            }
            if(!hasGem(stack, gem)) {
                gem = 6;
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



    public void hasGemInInventory(ItemStack stack) {
        if(getBackpackItemStackHandler(stack).getStackInSlot(0).getItem().equals(ItemInit.TIME_GEM.get()) ) {
            onGem(stack, 0);
        }else {
            offGem(stack, 0);
        }
        if(getBackpackItemStackHandler(stack).getStackInSlot(1).getItem().equals(ItemInit.POWER_GEM.get()) ) {
            onGem(stack, 1);
        }else {
            offGem(stack, 1);
        }
        if(getBackpackItemStackHandler(stack).getStackInSlot(2).getItem().equals(ItemInit.SPACE_GEM.get()) ) {
            onGem(stack, 2);
        }else {
            offGem(stack, 2);
        }
        if(getBackpackItemStackHandler(stack).getStackInSlot(3).getItem().equals(ItemInit.REALITY_GEM.get()) ) {
            onGem(stack, 3);
        }else {
            offGem(stack, 3);
        }
        if(getBackpackItemStackHandler(stack).getStackInSlot(4).getItem().equals(ItemInit.SOUL_GEM.get()) ) {
            onGem(stack, 4);
        }else {
            offGem(stack, 4);
        }
        if(getBackpackItemStackHandler(stack).getStackInSlot(5).getItem().equals(ItemInit.MIND_GEM.get()) ) {
            onGem(stack, 5);
        }else {
            offGem(stack, 5);
        }
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        ItemStack stack = context.getItemInHand();
        PlayerEntity player = context.getPlayer();
        if(context.getPlayer().isShiftKeyDown()) {
            if(!world.isClientSide) {
                    INamedContainerProvider ncp = new InfinityGauntletContainerProvider(this, stack);
                    NetworkHooks.openGui((ServerPlayerEntity)player, ncp);


                }

            }
        else if(getGem(context.getItemInHand()) == 0) {
            BlockPos blockpos = context.getClickedPos();
            BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
            world.setBlockAndUpdate(blockpos1, BlockInit.TIME_GEM_BLOCK.get().defaultBlockState());
            getBackpackItemStackHandler(stack).extractItem(0, 1, false);
            stack.getTag().putInt("gem", 6);
                return ActionResultType.sidedSuccess(world.isClientSide);


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

    @Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new InfinityGauntletCapabilityProvider();
    }

    private InfinityGauntletItemStackHandler getBackpackItemStackHandler(ItemStack stack) {
        IItemHandler backpack = (IItemHandler)stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        if (backpack == null || !(backpack instanceof InfinityGauntletItemStackHandler)) {
            Main.LOGGER.error("Backpack did not have the expected ITEM_HANDLER_CAPABILITY");
            return new InfinityGauntletItemStackHandler();
        }
        return (InfinityGauntletItemStackHandler)backpack;
    }

    @Nullable
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT baseTag = stack.getTag();
        InfinityGauntletItemStackHandler backpackItemStackHandler = getBackpackItemStackHandler(stack);
        CompoundNBT capTag = backpackItemStackHandler.serializeNBT();
        CompoundNBT combinedTag = new CompoundNBT();
        if (baseTag != null)
            combinedTag.put("base", (INBT)baseTag);
        if (capTag != null)
            combinedTag.put("cap", (INBT)capTag);
        return combinedTag;
    }

    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (nbt == null) {
            stack.setTag(null);
            return;
        }
        CompoundNBT baseTag = nbt.getCompound("base");
        stack.setTag(baseTag);
        CompoundNBT capTag = nbt.getCompound("cap");
        InfinityGauntletItemStackHandler backpackItemStackHandler = getBackpackItemStackHandler(stack);
        backpackItemStackHandler.deserializeNBT(capTag);
    }

    public void openGUI(InfinityGauntlet gauntlet, ItemStack stack, PlayerEntity player) {
        INamedContainerProvider ncp = new InfinityGauntletContainerProvider(this, stack);
        NetworkHooks.openGui((ServerPlayerEntity)player, ncp);
    }

    public static class InfinityGauntletContainerProvider implements INamedContainerProvider {
        private final InfinityGauntlet backpackItem;

        private final ItemStack backpackStack;

        public InfinityGauntletContainerProvider(Item item, ItemStack stack) {

            this.backpackItem = (InfinityGauntlet) item;

            this.backpackStack = stack;
        }

        @Nullable
        public Container createMenu(int windowId, PlayerInventory playerInv, PlayerEntity player) {
            return new InfinityGauntletContainer(windowId, playerInv, this.backpackItem

                    .getBackpackItemStackHandler(this.backpackStack), this.backpackStack);
        }

        @Override
        public ITextComponent getDisplayName() {
            return (ITextComponent) new TranslationTextComponent("container.marvel_themod.infinity_gauntlet");
        }
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
            if(getGem(stack) == 6)
            tooltip.add(new TranslationTextComponent("tooltip.marvel_themod.infinity_gauntlet_second_" + getGem(stack)));
        }else {
            tooltip.add(new TranslationTextComponent("tooltip.marvel_themod.hold_shift"));
        }
    }

    public int getGem(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt("gem") : 6;
    }

    public boolean hasGem(ItemStack stack, int gem) {
        if(gem == 6) {
            return true;
        }
        return stack.hasTag() ? stack.getTag().getBoolean("hasGem." + gem) : false;
    }

    public void onGem(ItemStack stack, int gem) {
        stack.getOrCreateTag().putBoolean("hasGem." + gem, true);
    }

    public void offGem(ItemStack stack, int gem) {
        stack.getOrCreateTag().putBoolean("hasGem." + gem, false);
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


    public void setSlot(ItemStack gauntlet, int slot, ItemStack item) {
        getBackpackItemStackHandler(gauntlet).insertItem(slot, item, false);
    }


    public void inventoryTick(ItemStack stack, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        hasGemInInventory(stack);
        if(!hasGem(stack, stack.getTag().getInt("gem"))) {
            stack.getTag().putInt("gem", 6);
        }


    }

}
