package manueh.marvel_themod.common.entity;


import manueh.marvel_themod.core.init.EntityTypesInit;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PowerGemEntity extends ThrowableItemProjectile {

    public PowerGemEntity(Level p_i1780_1_, LivingEntity p_i1780_2_) {
        super(EntityType.EGG, p_i1780_2_, p_i1780_1_);
    }

    public PowerGemEntity(EntityType<? extends PowerGemEntity> p_37432_, Level world) {
        super(p_37432_, world);
    }


    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte p_70103_1_) {
        if (p_70103_1_ == 3) {
            double d0 = 0.08D;

            for(int i = 0; i < 8; ++i) {
                this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }


    protected void onHitEntity(EntityHitResult entityRayTraceResult) {
        super.onHitEntity(entityRayTraceResult);
        entityRayTraceResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 15.0F);
    }

    protected void onHit(HitResult rayTraceResult) {
        super.onHit(rayTraceResult);
        if (!this.level.isClientSide && rayTraceResult.getType() != HitResult.Type.ENTITY) {
            level.explode(this, new DamageSource("damage.marvel_themod.infinity_gauntlet"), (ExplosionDamageCalculator) null,(double) this.getX(), (double)this.getY(0.0625D), (double) this.getZ(), 2.0F, false, Explosion.BlockInteraction.DESTROY);
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();

        }

    }

    @Override
    protected Item getDefaultItem() {
        return ItemInit.POWER_GEM.get();
    }
}
