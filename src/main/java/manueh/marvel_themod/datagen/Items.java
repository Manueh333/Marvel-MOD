package manueh.marvel_themod.datagen;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.core.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Items extends ItemModelProvider {


    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Main.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        /*singleTexture(Registration.MAGIC_POWDER.get().getRegistryName().getPath(),
                new ResourceLocation("item/handheld"), "layer0",
                new ResourceLocation(Main.MODID, "item/magic_powder"));
*/
        getBuilder(ItemInit.INFINITY_GAUNTLET.get().getRegistryName().getPath())
                .parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", "items/infinity_gauntlet_0")
                .override().predicate(Main.INFINITY_GAUNTLET_GEM_PROPERTY, 0).model(createTestModel(0)).end()
                .override().predicate(Main.INFINITY_GAUNTLET_GEM_PROPERTY, 1).model(createTestModel(1)).end()
                .override().predicate(Main.INFINITY_GAUNTLET_GEM_PROPERTY, 2).model(createTestModel(2)).end()
                .override().predicate(Main.INFINITY_GAUNTLET_GEM_PROPERTY, 3).model(createTestModel(3)).end()
                .override().predicate(Main.INFINITY_GAUNTLET_GEM_PROPERTY, 4).model(createTestModel(4)).end()
                .override().predicate(Main.INFINITY_GAUNTLET_GEM_PROPERTY, 5).model(createTestModel(5)).end();

        // withExistingParent(Registration.MAGIC_ORE_ITEM.get().getRegistryName().getPath(), new ResourceLocation(Main.MODID, "block/magic_ore"));
    }



    private ItemModelBuilder createTestModel(int suffix) {
        return getBuilder("infinity_gauntlet_" + suffix).parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", "items/infinity_gauntlet_" + suffix);
    }

}