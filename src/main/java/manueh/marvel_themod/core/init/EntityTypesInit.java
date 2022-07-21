package manueh.marvel_themod.core.init;

import manueh.marvel_themod.Main;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypesInit {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MODID);






    //pub
    //
    // lic static final RegistryObject<EntityType<MjolnirEntity>> MJOLNIR_ENTITY = ENTITY_TYPES.register("mjolnir", () -> EntityType.Builder.of(MjolnirEntity::new, EntityClassification.MISC).sized(1.0f,1.0f));


}
