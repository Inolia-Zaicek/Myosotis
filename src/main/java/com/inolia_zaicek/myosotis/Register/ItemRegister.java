package com.inolia_zaicek.myosotis.Register;

import com.inolia_zaicek.myosotis.Item.*;
import com.inolia_zaicek.myosotis.Register.TooltipItem.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.inolia_zaicek.myosotis.Myosotis.MODID;

public class ItemRegister {
    public static final DeferredRegister<Item> ZeroingITEM = DeferredRegister.create(Registries.ITEM, MODID);
    public static List<RegistryObject<Item>> CommonItem = new ArrayList<>(List.of());
    public static List<RegistryObject<Item>> NostalgicItem = new ArrayList<>(List.of());
    public static List<RegistryObject<Item>> CraftItem = new ArrayList<>(List.of());
    public static List<RegistryObject<Item>> BlockItem = new ArrayList<>(List.of());

    public static RegistryObject<Item> registerCommonMaterials(DeferredRegister<Item> register, String name, Supplier<? extends Item> sup){
        RegistryObject<Item> object = register.register(name,sup);
        CommonItem.add(object);
        return object;
    }
    public static RegistryObject<Item> registerNostalgicItem(DeferredRegister<Item> register, String name, Supplier<? extends Item> sup){
        RegistryObject<Item> object = register.register(name,sup);
        NostalgicItem.add(object);
        return object;
    }
    public static RegistryObject<Item> registerCraftItem(DeferredRegister<Item> register, String name, Supplier<? extends Item> sup){
        RegistryObject<Item> object = register.register(name,sup);
        CraftItem.add(object);
        return object;
    }
    //伤害：剑A的情况下，斧头A+2，镐A-2，锹A-1.5，锄固定为1
    // 攻击速度（镐统一-2.8，斧头-3.1，剑-2.4，锹-3，锄0
    ///铜
    //tag
    public static final TagKey<Item> copper = TagKey.create(Registries.ITEM,new ResourceLocation("forge","ingots/copper"));
    public static final Supplier<Item> Myosotis1=registerCommonMaterials(ZeroingITEM,"myosotis_1", Myosotis1Item::new);
    public static final Supplier<Item> Myosotis2=registerCommonMaterials(ZeroingITEM,"myosotis_2", Myosotis2Item::new);
    public static final Supplier<Item> Myosotis3=registerCommonMaterials(ZeroingITEM,"myosotis_3", Myosotis3Item::new);
    public static final RegistryObject<Item>SablePlume=registerNostalgicItem(ZeroingITEM,
            "sable_plume", ()->new SablePlumeItem(
                    Tiers.NETHERITE, //材质（此处是自定义
                    30,             // 攻击伤害
                    -2.4F,         // 攻击速度(-2为2攻击速度，比钻剑高0.4
                    new Item.Properties().stacksTo(1) // 剑默认堆叠数为1，这里保留是为了明确，但对于剑来说是冗余的}
            )   );
    public static final RegistryObject<Item>EmbersEgnir=registerNostalgicItem(ZeroingITEM,
            "embers_egnir", ()->new EmbersEgnirItem(
                    Tiers.NETHERITE, //材质（此处是自定义
                    25,             // 攻击伤害
                    -2.4F,         // 攻击速度(-2为2攻击速度，比钻剑高0.4
                    new Item.Properties().stacksTo(1) // 剑默认堆叠数为1，这里保留是为了明确，但对于剑来说是冗余的}
            )   );
    public static final RegistryObject<Item> MithrilShard = registerCraftItem(ZeroingITEM,"mithril_shard", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> MithrilIngot = registerCraftItem(ZeroingITEM,"mithril_ingot", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));

    public static final RegistryObject<Item> BlessedMithrilIngot = registerCraftItem(ZeroingITEM,"blessed_mithril_ingot", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> MagicLeather = registerCraftItem(ZeroingITEM,"magic_leather", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> CelestialSteelIngot = registerCraftItem(ZeroingITEM,"celestial_steel_ingot", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> CelestialCrystal = registerCraftItem(ZeroingITEM,"celestial_crystal", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> MeleeWeaponTemplate = registerCraftItem(ZeroingITEM,"melee_weapon_template", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> RangedWeaponTemplate = registerCraftItem(ZeroingITEM,"ranged_weapon_template", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> CasterWeaponTemplate = registerCraftItem(ZeroingITEM,"caster_weapon_template", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));

    public static final RegistryObject<Item> PureDarkMagicalCore = registerCraftItem(ZeroingITEM,"pure_dark_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> ThunderousMagicalCore = registerCraftItem(ZeroingITEM,"thunderous_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> EmberMagicalCore = registerCraftItem(ZeroingITEM,"ember_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> PureWaterMagicalCore = registerCraftItem(ZeroingITEM,"pure_water_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> NaturalMagicalCore = registerCraftItem(ZeroingITEM,"natural_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> FrostMagicalCore = registerCraftItem(ZeroingITEM,"frost_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> SacredSpiritMagicalCore = registerCraftItem(ZeroingITEM,"sacred_spirit_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> CrimsonMagicalCore = registerCraftItem(ZeroingITEM,"crimson_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> MagicStone = registerCraftItem(ZeroingITEM,"magic_stone", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> MagicDust = registerCraftItem(ZeroingITEM,"magic_dust", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> MagicController = registerCraftItem(ZeroingITEM,"magic_controller", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> DenseManaStone = registerCraftItem(ZeroingITEM,"dense_mana_stone", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> ForgedSteel = registerCraftItem(ZeroingITEM,"forged_steel", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> PureDarkWood = registerCraftItem(ZeroingITEM,"pure_dark_wood", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> RefinedDarkAlloy = registerCraftItem(ZeroingITEM,"refined_dark_alloy", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> RefinedDarkGrip = registerCraftItem(ZeroingITEM,"refined_dark_grip", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> FusedMana = registerCraftItem(ZeroingITEM,"fused_mana", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> Goodwill = registerCraftItem(ZeroingITEM,"good_will", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> Malice = registerCraftItem(ZeroingITEM,"malice", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> Memory = registerCraftItem(ZeroingITEM,"memory", () -> new TooltipItem4(new Item.Properties().stacksTo(64).fireResistant() ));

    public static void register(IEventBus bus){
        ZeroingITEM.register(bus);
    }
}