package manueh.marvel_themod.common.entity;

import manueh.marvel_themod.core.init.EntityTypesInit;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PowerGenEntity extends ProjectileItemEntity {

    public PowerGenEntity(World p_i1780_1_, LivingEntity p_i1780_2_) {
        super(EntityTypesInit.POWER_GEM.get(), p_i1780_2_, p_i1780_1_);
    }

    public PowerGenEntity(EntityType<? extends PowerGenEntity> p_37432_, World world) {
        super(p_37432_, world);
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
        entityRayTraceResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 15.0F);
    }

    protected void onHit(RayTraceResult rayTraceResult) {
        super.onHit(rayTraceResult);
        if (!this.level.isClientSide && rayTraceResult.getType() != RayTraceResult.Type.ENTITY) {
            level.explode(this, new DamageSource("damage.marvel_themod.infinity_gauntlet"), (ExplosionContext) null,(double) this.getX(), (double)this.getY(0.0625D), (double) this.getZ(), 2.0F, false, Explosion.Mode.DESTROY);
            this.level.broadcastEntityEvent(this, (byte)3);
            this.remove();

        }

    }

    @Override
    protected Item getDefaultItem() {
        return ItemInit.POWER_GEM.get();
    }
}
