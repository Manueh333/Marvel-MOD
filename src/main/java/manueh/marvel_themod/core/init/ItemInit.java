package manueh.marvel_themod.core.init;

import manueh.marvel_themod.Main;
import manueh.marvel_themod.common.items.CaptainAmericaShield;
import manueh.marvel_themod.common.items.InfinityGauntlet;
import manueh.marvel_themod.common.items.IronManArmor;
import manueh.marvel_themod.common.items.mjolnir;
import manueh.marvel_themod.core.enums.IronManMaterial;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    //THOR
    //ORE
    public static final RegistryObject<BlockItem> URU_ORE = ITEMS.register("uru_ore",
            () -> new BlockItem(BlockInit.URU_ORE.get(), new Item.Properties().tab(Main.GROUP)));
    //NUGGET
    public static final RegistryObject<Item> URU_NUGGET = ITEMS.register("uru_nugget",
            () -> new Item(new Item.Properties().tab(Main.GROUP)));
    //INGOT
    public static final RegistryObject<Item> URU_INGOT = ITEMS.register("uru_ingot",
            () -> new Item(new Item.Properties().tab(Main.GROUP)));
    //BLOCK
    public static final RegistryObject<BlockItem> URU_BLOCK = ITEMS.register("uru_block",
            () -> new BlockItem(BlockInit.URU_BLOCK.get(), new Item.Properties().tab(Main.GROUP)));
    //STAR
    public static final RegistryObject<Item> STAR = ITEMS.register("star",
            () -> new Item(new Item.Properties().tab(Main.GROUP)));
    public static final RegistryObject<Item> STAR_CORE = ITEMS.register("star_core",
            () -> new Item(new Item.Properties().tab(Main.GROUP)));
    //HANDLE
    public static final RegistryObject<Item> MJOLNIR_HANDLE = ITEMS.register("mjolnir_handle",
            () -> new Item(new Item.Properties().tab(Main.GROUP)));
    //MJOLNIR
    public static final RegistryObject<Item> MJOLNIR = ITEMS.register("mjolnir",
            () -> new mjolnir(Tiers.DIAMOND,8,-1.5f, new Item.Properties().tab(Main.GROUP).durability(800).setNoRepair().fireResistant()));


    //IRON_MAN
    //MATERIAL
    public static final RegistryObject<Item> NANOBOT = ITEMS.register("nanobot",
            () -> new Item(new Item.Properties().tab(Main.GROUP)));
    //INGOT
    public static final RegistryObject<Item> REACTOR = ITEMS.register("reactor_arc",
            () -> new Item(new Item.Properties().tab(Main.GROUP)));
    //ARMOR
    public static final RegistryObject<Item> IRONMAN_HELMET = ITEMS.register("ironman_helmet", () -> new IronManArmor(IronManMaterial.IRONMAN_ARMOR, EquipmentSlot.HEAD, new Item.Properties().tab(Main.GROUP)));
    public static final RegistryObject<Item> IRONMAN_CHESTPLATE = ITEMS.register("ironman_chestplate", () -> new IronManArmor(IronManMaterial.IRONMAN_ARMOR, EquipmentSlot.CHEST, new Item.Properties().tab(Main.GROUP)));
    public static final RegistryObject<Item> IRONMAN_LEGGINS = ITEMS.register("ironman_leggins", () -> new IronManArmor(IronManMaterial.IRONMAN_ARMOR, EquipmentSlot.LEGS, new Item.Properties().tab(Main.GROUP)));
    public static final RegistryObject<Item> IRONMAN_BOOTS = ITEMS.register("ironman_boots", () -> new IronManArmor(IronManMaterial.IRONMAN_ARMOR, EquipmentSlot.FEET, new Item.Properties().tab(Main.GROUP)));


    //CAPTAIN AMERICA
    public static final RegistryObject<Item> CAPTAIN_AMERICA_SHIELD = ITEMS.register("captain_america_shield", () -> new CaptainAmericaShield(Tiers.DIAMOND,8,-1.5f, new Item.Properties().tab(Main.GROUP).durability(800).setNoRepair().fireResistant()));


    //INFINITY GAUNTLET
    public static final RegistryObject<InfinityGauntlet> INFINITY_GAUNTLET = ITEMS.register("infinity_gauntlet", () -> new InfinityGauntlet(new Item.Properties().stacksTo(1).tab(Main.GROUP).fireResistant()));
    public static final RegistryObject<Item> POWER_GEM = ITEMS.register("power_gem", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<BlockItem> CORRUPTED_BLOCK = ITEMS.register("corrupted_block",
            () -> new BlockItem(BlockInit.CORRUPTED_BLOCK.get(), new Item.Properties().tab(Main.GROUP)));
}
