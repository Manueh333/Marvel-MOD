package manueh.marvel_themod.common.entity;

import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MjolnirEntity extends ThrowableItemProjectile {

    public MjolnirEntity(Level p_i1780_1_, LivingEntity p_i1780_2_) {
        super(EntityType.EGG, p_i1780_2_, p_i1780_1_);
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
        entityRayTraceResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 5.0F);
        if(this.getOwner() instanceof Player) {
            Player player = (Player) this.getOwner();
            player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 5));
        }
        Entity entity = entityRayTraceResult.getEntity();
        Level world = entity.getCommandSenderWorld();
        LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
        lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
        world.addFreshEntity(lightning);
    }

    protected void onHit(HitResult rayTraceResult) {
        super.onHit(rayTraceResult);
        if (!this.level.isClientSide) {
            this.getOwner();

            this.level.broadcastEntityEvent(this, (byte)3);
            this.remove(RemovalReason.KILLED);
            if(this.getOwner() instanceof Player) {
                Player player = (Player) this.getOwner();
                player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 5));
            }
            LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, this.getCommandSenderWorld());
            lightning.setPos(rayTraceResult.getLocation().x, rayTraceResult.getLocation().y, rayTraceResult.getLocation().z);
            this.getCommandSenderWorld().addFreshEntity(lightning);
        }

    }

    @Override
    protected Item getDefaultItem() {
        return ItemInit.MJOLNIR.get();
    }
}
