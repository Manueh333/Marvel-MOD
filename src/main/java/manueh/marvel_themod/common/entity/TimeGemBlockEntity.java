package manueh.marvel_themod.common.entity;

import manueh.marvel_themod.Config;
import manueh.marvel_themod.core.enums.Tier;
import manueh.marvel_themod.core.enums.TierSupplier;
import manueh.marvel_themod.core.enums.TimeGemAPI;
import manueh.marvel_themod.core.init.TileEntityTypeInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.INameable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;

import java.util.Objects;

public class TimeGemBlockEntity extends TileEntity implements INameable, ITickableTileEntity, TierSupplier {
  public static int randomTicks;
  
  private ITextComponent customName;
  
  private int xRange = 5;
  
  private int yRange = 3;
  
  private int zRange = 5;
  
  private int speed = 4;
  
  private int redstoneMode;
  
  private Iterable<BlockPos> area;

  
  private ResourceLocation tierID;
  
  private String uuid = "";
  
  public TimeGemBlockEntity() {
    super(TileEntityTypeInit.TIME_GEM_TILE_ENTITY.get());
  }
  
  public boolean hasCustomName() {
    return (this.customName != null);
  }
  
  public ITextComponent getCustomName() {
    return this.customName;
  }

  public void setCustomName(ITextComponent name) {
    this.customName = name;
  }
  
  private String getOwner() {
    return this.uuid;
  }
  
  public void setOwner(String s) {
    this.uuid = s;
  }
  
  public ITextComponent getName() {
    return hasCustomName() ? this.customName : (ITextComponent)new TranslationTextComponent(getBlockState().getBlock().getDescriptionId());
  }
  @Override
  public void onLoad() {

    if (this.getLevel().isClientSide())
      return;

    this.area = BlockPos.betweenClosed(this.worldPosition.getX() - this.xRange, this.getBlockPos().getY() - this.yRange, this.getBlockPos().getZ() - this.zRange, this.getBlockPos()
        .getX() + this.xRange, this.getBlockPos().getY() + this.yRange, this.getBlockPos().getZ() + this.zRange);
    this.level.getServer().addTickable((Runnable)new TickDelayedTask(this.level.getServer().getTickCount(), () -> getBlockState().neighborChanged(this.level, this.getBlockPos(), null, null, false)));
  }

  @Override
  public void tick() {
    if (this.speed == 0 || (this.xRange == 0 && this.yRange == 0 && this.zRange == 0))
      return;
    randomTicks = this.level.getGameRules().getInt(GameRules.RULE_RANDOMTICKING);
    this.area.forEach(this::tickBlock);
  }
  
  private void tickBlock(BlockPos pos) {
    ITickableTileEntity tickableBlockEntity;
    BlockState blockState = this.level.getBlockState(pos);
    Block block = blockState.getBlock();
    if (TimeGemAPI.INSTANCE.isBlockBlacklisted(block))
      return;
    Objects.requireNonNull(Config.INSTANCE);
    if (this.level instanceof ServerWorld && block.isRandomlyTicking(blockState) && this.level.getRandom().nextInt(MathHelper.clamp(4096 / this.speed * 4, 1, 4096)) < randomTicks)
      blockState.randomTick((ServerWorld)this.level, pos, this.level.getRandom());
    TileEntity blockEntity = this.level.getBlockEntity(pos);
    if (blockEntity != null && !blockEntity.isRemoved() && !TimeGemAPI.INSTANCE.isBlockEntityBlacklisted(blockEntity.getType())) {
      TileEntity tileEntity = blockEntity;
      if (tileEntity instanceof ITickableTileEntity) {
        tickableBlockEntity = (ITickableTileEntity)tileEntity;
      } else {
        return;
      } 
    } else {
      return;
    } 
    for (int i = 0; i < this.speed && 
      !blockEntity.isRemoved(); i++)
      tickableBlockEntity.tick();
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

  public CompoundNBT func_189515_b(CompoundNBT tag) {
    tag.putInt("XRange", this.xRange);
    tag.putInt("ZRange", this.zRange);
    tag.putInt("YRange", this.yRange);
    tag.putInt("Speed", this.speed);
    return tag;
  }
  
  public void func_230337_a_(BlockState state, CompoundNBT tag) {

    this.xRange = tag.getInt("XRange");
    this.zRange = tag.getInt("ZRange");
    this.yRange = tag.getInt("YRange");
    this.speed = tag.getInt("Speed");

  }
}
