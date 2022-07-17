package manueh.marvel_themod.common.entity;

import manueh.marvel_themod.core.enums.TierSupplier;
import manueh.marvel_themod.core.enums.TimeGemAPI;
import manueh.marvel_themod.core.init.TileEntityTypeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TimeGemBlockEntity extends BlockEntity implements Nameable, TierSupplier {
  public static int randomTicks;
  
  private Component customName;
  
  private int xRange = 5;
  
  private int yRange = 3;
  
  private int zRange = 5;
  
  private int speed = 10;
  private boolean inTick = false;
  
  private int redstoneMode;
  
  private Iterable<BlockPos> area;

  
  private ResourceLocation tierID;
  
  private String uuid = "";

  public TimeGemBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
    super(TileEntityTypeInit.TIME_GEM_TILE_ENTITY.get(), p_155229_, p_155230_);
  }


  public boolean hasCustomName() {
    return (this.customName != null);
  }

  @Override
  public Component getDisplayName() {
    return hasCustomName() ? this.customName : (Component)new TranslatableComponent(getBlockState().getBlock().getDescriptionId());

  }

  public Component getCustomName() {
    return this.customName;
  }

  public void setCustomName(Component name) {
    this.customName = name;
  }
  

  @Override
  public Component getName() {
    return hasCustomName() ? customName : new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
  }

  @Override
  public void setLevel(Level level) {
    super.setLevel(level);
    if (!level.isClientSide()) {
      level.getServer().tell(new TickTask(level.getServer().getTickCount(), () -> getBlockState().neighborChanged(level, worldPosition, null, null, false)));
    }
  }



  @Override
  public void onLoad() {

    if (level.isClientSide)
      return;

    this.area = BlockPos.betweenClosed(this.worldPosition.getX() - this.xRange, this.getBlockPos().getY() - this.yRange, this.getBlockPos().getZ() - this.zRange, this.getBlockPos()
        .getX() + this.xRange, this.getBlockPos().getY() + this.yRange, this.getBlockPos().getZ() + this.zRange);

    this.level.getServer().addTickable((Runnable)new TickTask(this.level.getServer().getTickCount(), () -> getBlockState().neighborChanged(this.level, this.getBlockPos(), null, null, false)));
  }

  @Override
  public AABB getRenderBoundingBox() {
    return super.getRenderBoundingBox();
  }

  @Override
  public void requestModelDataUpdate() {
    super.requestModelDataUpdate();
  }

  @Nonnull
  @Override
  public IModelData getModelData() {
    return super.getModelData();
  }



  public static void tick(Level level, BlockPos pos, BlockState state, TimeGemBlockEntity entity) {
    if (entity.inTick) return;
    entity.inTick = true;
    if ( entity.speed == 0 || (entity.xRange == 0 && entity.yRange == 0 && entity.zRange == 0)) {
      return;
    }
    if(!level.isClientSide) {
    randomTicks = level.getGameRules().getInt(GameRules.RULE_RANDOMTICKING);
      entity.area.forEach(entity::tickBlock);
      entity.inTick = false;
    }}

  private void tickBlock(BlockPos pos) {
    if (level.isClientSide)
      return;
    TickingBlockEntity tickableBlockEntity;
    BlockState blockState = this.level.getBlockState(pos);
    Block block = blockState.getBlock();
    if (TimeGemAPI.INSTANCE.isBlockBlacklisted(block))
      return;

    if (this.level instanceof ServerLevel && block.isRandomlyTicking(blockState) && this.level.getRandom().nextInt(Mth.clamp(4096 / this.speed * 4, 1, 4096)) < randomTicks)
      for (int i = 0; i < this.speed &&
              !blockState.isAir(); i++)
        blockState.randomTick((ServerLevel)this.level, pos, this.level.getRandom());


    BlockEntity blockEntity = this.level.getBlockEntity(pos);
    if (blockEntity != null && !blockEntity.isRemoved() && !(blockEntity instanceof TimeGemBlockEntity)) {
      BlockEntity tileEntity = blockEntity;
      if (tileEntity instanceof TickingBlockEntity) {
        tickableBlockEntity = (TickingBlockEntity)tileEntity;
      } else {
        return;
      } 
    } else {
      return;
    }
    if(!level.isClientSide) {

    for (int i = 0; i < this.speed && 
      !blockEntity.isRemoved(); i++)
      tickableBlockEntity.tick();

    }
    }
  
 /* public boolean readClientData(int xRange, int zRange, int yRange, int speed, int redstoneMode) {
    Tier tier = (Tier) TimeGemAPI.INSTANCE.getTiers().get(getTier());
    if (valueInRange(xRange, 0, tier.getXZRange()) &&
      valueInRange(zRange, 0, tier.getXZRange()) &&
      valueInRange(yRange, 0, tier.getYRange()) &&
      valueInRange(speed, 0, tier.getMaxSpeed()) &&
      valueInRange(redstoneMode, 0, 3)) {
      this.xRange = xRange;
      this.zRange = zRange;
      this.yRange = yRange;
      this.speed = speed;
      this.redstoneMode = redstoneMode;
      this.area = BlockPos.betweenClosed(this.getBlockPos().getX() - xRange, this.getBlockPos().getY() - yRange, this.getBlockPos().getZ() - zRange, this.getBlockPos()
          .getX() + xRange, this.getBlockPos().getY() + yRange, this.getBlockPos().getZ() + zRange);

      return true;
    }
    return false;
  }*/

  private boolean valueInRange(int value, int min, int max) {
    return (value >= min && value <= max);
  }

  public ResourceLocation getTier() {
    if (this.tierID == null) {
      Block block = getBlockState().getBlock();
      Block block1 = block;
      if (block1 instanceof TierSupplier) {
        TierSupplier supplier = (TierSupplier)block1;
        this.tierID = supplier.getTier();
      }
    }
    return this.tierID;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockGetter p_196283_1_) {
    return null;
  }

  @Override
  public CompoundTag save(CompoundTag tag) {
    super.save(tag);
    if (this.hasCustomName()) {
      tag.putString("CustomName", Component.Serializer.toJson(getCustomName()));
    }
    tag.putInt("XRange", xRange);
    tag.putInt("ZRange", zRange);
    tag.putInt("YRange", yRange);
    tag.putInt("Speed", speed);

    return tag;
  }

  @Override
  public void load(CompoundTag tag) {
    super.load(tag);
    if (tag.contains("CustomName", 8)) {
      this.setCustomName(Component.Serializer.fromJson(tag.getString("CustomName")));
    }
    xRange = tag.getInt("XRange");
    zRange = tag.getInt("ZRange");
    yRange = tag.getInt("YRange");
    speed = tag.getInt("Speed");


    area = BlockPos.betweenClosed(worldPosition.getX() - xRange, worldPosition.getY() - yRange, worldPosition.getZ() - zRange,
            worldPosition.getX() + xRange, worldPosition.getY() + yRange, worldPosition.getZ() + zRange);
  }


  @Override
  public void deserializeNBT(CompoundTag nbt) {
    super.deserializeNBT(nbt);
  }

  @Override
  public CompoundTag serializeNBT() {
    return super.serializeNBT();
  }

  @Override
  public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
    super.onDataPacket(net, pkt);
  }

  @Override
  public void handleUpdateTag(CompoundTag tag) {
    super.handleUpdateTag(tag);
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
    return super.getCapability(cap);
  }



  public BlockEntityType<?> getType() {
    return TileEntityTypeInit.TIME_GEM_TILE_ENTITY.get();
  }
}
