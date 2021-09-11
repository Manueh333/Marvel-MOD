package manueh.marvel_themod.core.init;

import manueh.marvel_themod.Main;

import manueh.marvel_themod.common.entity.PowerGemEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypesInit {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MODID);

    //public static final RegistryObject<EntityType<MjolnirEntity>> MJOLNIR_ENTITY = ENTITY_TYPES.register("mjolnir", () -> EntityType.Builder.of(MjolnirEntity::new, EntityClassification.MISC).sized(1.0f,1.0f));
    public static final RegistryObject<EntityType<PowerGemEntity>> POWER_GEM = ENTITY_TYPES.register("power_gem_entity", () -> EntityType.Builder.<PowerGemEntity>of(PowerGemEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build("power_gem_entity"));


}
