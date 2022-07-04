package manueh.marvel_themod.core.init;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.entity.MjolnirEntity;
import manueh.marvel_themod.common.entity.PowerGenEntity;
import manueh.marvel_themod.common.entity.TimeGemBlockEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypesInit {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MODID);

    public static final RegistryObject<EntityType<PowerGenEntity>> POWER_GEM = ENTITY_TYPES.register("power_gem_entity", () -> EntityType.Builder.<PowerGenEntity>of(PowerGenEntity::new, EntityClassification.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build("power_gem_entity"));





    //pub
    //
    // lic static final RegistryObject<EntityType<MjolnirEntity>> MJOLNIR_ENTITY = ENTITY_TYPES.register("mjolnir", () -> EntityType.Builder.of(MjolnirEntity::new, EntityClassification.MISC).sized(1.0f,1.0f));


}
