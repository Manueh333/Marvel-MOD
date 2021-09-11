package manueh.marvel_themod.common.entity;

import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MjolnirEntity extends ProjectileItemEntity {

    public MjolnirEntity(World p_i1780_1_, LivingEntity p_i1780_2_) {
        super(EntityType.EGG, p_i1780_2_, p_i1780_1_);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte p_70103_1_) {
        if (p_70103_1_ == 3) {
            double d0 = 0.08D;

            for(int i = 0; i < 8; ++i) {
                this.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    protected void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
        super.onHitEntity(entityRayTraceResult);
        entityRayTraceResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 5.0F);
        if(this.getOwner().getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) this.getOwner();
            player.addEffect(new EffectInstance(Effects.GLOWING, 40, 5));
        }
        Entity entity = entityRayTraceResult.getEntity();
        World world = entity.getCommandSenderWorld();
        LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
        lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
        world.addFreshEntity(lightning);
    }

    protected void onHit(RayTraceResult rayTraceResult) {
        super.onHit(rayTraceResult);
        if (!this.level.isClientSide) {
            this.getOwner();

            this.level.broadcastEntityEvent(this, (byte)3);
            this.remove();
            if(this.getOwner().getEntity() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) this.getOwner();
                player.addEffect(new EffectInstance(Effects.GLOWING, 40, 5));
            }
            LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, this.getCommandSenderWorld());
            lightning.setPos(rayTraceResult.getLocation().x, rayTraceResult.getLocation().y, rayTraceResult.getLocation().z);
            this.getCommandSenderWorld().addFreshEntity(lightning);
        }

    }

    @Override
    protected Item getDefaultItem() {
        return ItemInit.MJOLNIR.get();
    }
}
