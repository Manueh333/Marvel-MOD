package manueh.marvel_themod.common.items;

import com.mojang.math.Vector3f;
import manueh.marvel_themod.Main;
import manueh.marvel_themod.client.ClientEvents;
import manueh.marvel_themod.common.blocks.containers.InfinityGauntletCapabilityProvider;
import manueh.marvel_themod.common.blocks.containers.InfinityGauntletContainer;
import manueh.marvel_themod.common.blocks.containers.InfinityGauntletItemStackHandler;
import manueh.marvel_themod.core.init.BlockInit;
import manueh.marvel_themod.core.init.DimensionInit;
import manueh.marvel_themod.core.init.ItemInit;
import manueh.marvel_themod.world.RealityTeleporter;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public class InfinityGauntlet extends Item {
    public InfinityGauntlet(Properties properties) {
        super(properties);

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player p, InteractionHand hand) {
        ItemStack stack = p.getItemInHand(hand);
        if (p.isShiftKeyDown()) {

            int gem = stack.getOrCreateTag().getInt("gem");

            boolean tried = false;
            boolean triggered = false;

            hasGemInInventory(stack);

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
                p.sendMessage(new TranslatableComponent("item.marvel_themod.infinity_gauntlet.gem_message", getGemName(stack)), Util.NIL_UUID);
            }
        }
        else if(getGem(stack) == 1) {
            Double rayLength = new Double(100);
            Vec3 playerRotation = p.getViewVector(0);
            Vec3 rayPath = playerRotation.scale(rayLength);

            //RAY START AND END POINTS
            Vec3 from = p.getEyePosition(0);
            Vec3 to = from.add(rayPath);

            //CREATE THE RAY
            ClipContext rayCtx = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, null);
            //CAST THE RAY
            BlockHitResult rayHit = level.clip(rayCtx);

            //CHECK THE RESULTS
            if (rayHit.getType() == HitResult.Type.MISS){
                //IF RAY MISSED
            }
            else {
                //IF RAY HIT SOMETHING
                Vec3 hitLocation = rayHit.getLocation();
                level.explode(null,new DamageSource("damage.marvel_themod.infinity_gauntlet"), (ExplosionDamageCalculator) null,(double) hitLocation.x, (double)hitLocation.y, (double) hitLocation.z, 2.0F, false, Explosion.BlockInteraction.DESTROY);


                p.getCooldowns().addCooldown(this, 10);
                // ItemStack stack = player.getItemInHand(hand);
                // stack.setDamageValue(stack.getDamageValue() + 1);

                // break if durability gets to 0
                // if (stack.getDamageValue() == 0) stack.setCount(0);



            }
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
                    BlockHitResult rayHit = level.clip(rayCtx);

                    //CHECK THE RESULTS
                    if (rayHit.getType() == HitResult.Type.MISS){
                        //IF RAY MISSED
                    }
                    else {
                        //IF RAY HIT SOMETHING
                        Vec3 hitLocation = rayHit.getLocation();
                        p.teleportTo(hitLocation.x, hitLocation.y,hitLocation.z);
                        animateTeleport(level, p);


                        p.getCooldowns().addCooldown(this, 10);


                    }
                }

        }else if(getGem(stack) == 3) {

            if (!level.isClientSide()) {
                MinecraftServer server = level.getServer();

                if (server != null) {
                    if (level.dimension() == DimensionInit.REALITY_DIMENSION) {
                        ServerLevel overWorld = server.getLevel(Level.OVERWORLD);
                        if (overWorld != null) {
                            p.changeDimension(overWorld, new RealityTeleporter(new BlockPos(p.position()), false));
                        }
                    } else {
                        ServerLevel realityDim = server.getLevel(DimensionInit.REALITY_DIMENSION);
                        if (realityDim != null) {
                            p.changeDimension(realityDim, new RealityTeleporter(new BlockPos(p.position()), true));
                        }
                    }

                }

            }
        }

            return InteractionResultHolder.success(stack);
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



    public void animateTeleport(Level level, Player p) {
        Main.timer = 0;
        Main.makeTeleportAnimation = true;
    }



    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        if(getGem(context.getItemInHand()) == 0) {
            BlockPos blockpos = context.getClickedPos();
            BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
            world.setBlockAndUpdate(blockpos1, BlockInit.TIME_GEM_BLOCK.get().defaultBlockState());
            getBackpackItemStackHandler(stack).extractItem(0, 1, false);
            stack.getTag().putInt("gem", 6);
                return InteractionResult.sidedSuccess(world.isClientSide);


        }else if(getGem(stack) == 3) {

            if (!world.isClientSide()) {
                    MinecraftServer server = world.getServer();

                    if (server != null) {
                        if (world.dimension() == DimensionInit.REALITY_DIMENSION) {
                            ServerLevel overWorld = server.getLevel(Level.OVERWORLD);
                            if (overWorld != null) {
                                player.changeDimension(overWorld, new RealityTeleporter(new BlockPos(player.position()), false));
                            }
                        } else {
                            ServerLevel realityDim = server.getLevel(DimensionInit.REALITY_DIMENSION);
                            if (realityDim != null) {
                                player.changeDimension(realityDim, new RealityTeleporter(new BlockPos(player.position()), true));
                            }
                        }
                        return InteractionResult.SUCCESS;
                    }

            }


        }
        else if(getGem(stack) == 4) {
            CompoundTag entityData = (CompoundTag) stack.getTag().get("EntityData");
            if(entityData != null) {
                Entity entity = EntityType.create(entityData, world).get();
                if(entity instanceof Player) {
                    return InteractionResult.FAIL;
                }
                entity.setPos(context.getClickedPos().getX() + 0.5f, context.getClickedPos().getY() + 1f, context.getClickedPos().getZ() + 0.5f);
                world.addFreshEntity(entity);
                stack.getTag().remove("EntityData");
                stack.getTag().remove("EntityName");
                context.getPlayer().getCooldowns().addCooldown(this, 10);

            }


        }
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    @Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
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
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag baseTag = stack.getTag();
        InfinityGauntletItemStackHandler backpackItemStackHandler = getBackpackItemStackHandler(stack);
        CompoundTag capTag = backpackItemStackHandler.serializeNBT();
        CompoundTag combinedTag = new CompoundTag();
        if (baseTag != null)
            combinedTag.put("base", (Tag)baseTag);
        if (capTag != null)
            combinedTag.put("cap", (Tag)capTag);
        return combinedTag;
    }

    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        if (nbt == null) {
            stack.setTag(null);
            return;
        }
        CompoundTag baseTag = nbt.getCompound("base");
        stack.setTag(baseTag);
        CompoundTag capTag = nbt.getCompound("cap");
        InfinityGauntletItemStackHandler backpackItemStackHandler = getBackpackItemStackHandler(stack);
        backpackItemStackHandler.deserializeNBT(capTag);
    }

    public void openGUI(InfinityGauntlet gauntlet, ItemStack stack, Player player) {
        MenuProvider ncp = new InfinityGauntletContainerProvider(this, stack);
        NetworkHooks.openGui((ServerPlayer)player, ncp);
    }

    public static class InfinityGauntletContainerProvider implements MenuProvider {
        private final InfinityGauntlet backpackItem;

        private final ItemStack backpackStack;

        public InfinityGauntletContainerProvider(InfinityGauntlet item, ItemStack stack) {

            this.backpackItem = item;

            this.backpackStack = stack;
        }

        @Nullable
        public AbstractContainerMenu createMenu(int windowId, Inventory playerInv, Player player) {
            return new InfinityGauntletContainer(windowId, playerInv, this.backpackItem

                    .getBackpackItemStackHandler(this.backpackStack), this.backpackStack);
        }

        @Override
        public Component getDisplayName() {
            return (Component) new TranslatableComponent("container.marvel_themod.infinity_gauntlet");
        }
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

            tooltip.add(new TranslatableComponent("tooltip.marvel_themod.infinity_gauntlet_second_" + getGem(stack), ClientEvents.keyOpenGauntlet.getKey().getDisplayName()));
        }else {
            tooltip.add(new TranslatableComponent("tooltip.marvel_themod.hold_shift"));
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


    public void particleAnimation(Level level, Player player) {
        level.addParticle(new DustParticleOptions( new Vector3f(255, 255, 255), 1), player.getX(), player.getY(), player.getZ(), 0, 0, 0);

    }


    public void setTag(String name, CompoundTag compoundNBT, ItemStack stack) {
        stack.getTag().put("EntityData", compoundNBT);

    }


    public Tag getTag(String name, ItemStack stack) {
        return stack.getOrCreateTag().get(name);
    }


    public void setSlot(ItemStack gauntlet, int slot, ItemStack item) {
        getBackpackItemStackHandler(gauntlet).insertItem(slot, item, false);
    }


    public void inventoryTick(ItemStack stack, Level p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        hasGemInInventory(stack);
        if(!hasGem(stack, stack.getTag().getInt("gem"))) {
            stack.getTag().putInt("gem", 6);
        }


    }

}
