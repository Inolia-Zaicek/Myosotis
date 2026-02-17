package com.inolia_zaicek.myosotis.Register;

import com.inolia_zaicek.myosotis.Item.*;
import com.inolia_zaicek.myosotis.Item.Magic.MagicMissileItem;
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

public class MyItemRegister {
    public static final DeferredRegister<Item> ZeroingITEM = DeferredRegister.create(Registries.ITEM, MODID);
    public static List<RegistryObject<Item>> CommonItem = new ArrayList<>(List.of());
    public static List<RegistryObject<Item>> MeleeItem = new ArrayList<>(List.of());
    public static List<RegistryObject<Item>> MagicItem = new ArrayList<>(List.of());
    public static List<RegistryObject<Item>> RangeItem = new ArrayList<>(List.of());
    public static List<RegistryObject<Item>> CraftItem = new ArrayList<>(List.of());
    public static List<RegistryObject<Item>> BlockItem = new ArrayList<>(List.of());

    public static RegistryObject<Item> registerCommonMaterials(DeferredRegister<Item> register, String name, Supplier<? extends Item> sup){
        RegistryObject<Item> object = register.register(name,sup);
        CommonItem.add(object);
        return object;
    }
    public static RegistryObject<Item> registerMeleeItem(DeferredRegister<Item> register, String name, Supplier<? extends Item> sup){
        RegistryObject<Item> object = register.register(name,sup);
        MeleeItem.add(object);
        return object;
    }
    public static RegistryObject<Item> registerCraftItem(DeferredRegister<Item> register, String name, Supplier<? extends Item> sup){
        RegistryObject<Item> object = register.register(name,sup);
        CraftItem.add(object);
        return object;
    }
    public static RegistryObject<Item> registerMagicItem(DeferredRegister<Item> register, String name, Supplier<? extends Item> sup){
        RegistryObject<Item> object = register.register(name,sup);
        MagicItem.add(object);
        return object;
    }
    public static RegistryObject<Item> registerRangeItem(DeferredRegister<Item> register, String name, Supplier<? extends Item> sup){
        RegistryObject<Item> object = register.register(name,sup);
        RangeItem.add(object);
        return object;
    }
    //public static final RegistryObject<Item> FragmentOfMemoryFleeting = registerCommonMaterials(ZeroingITEM,"fragment_of_memory_fleeting", FragmentOfMemoryFleetingItem::new);
    //tag
    public static final TagKey<Item> copper = TagKey.create(Registries.ITEM,new ResourceLocation("forge","ingots/copper"));
    public static final Supplier<Item> Myosotis1=registerCommonMaterials(ZeroingITEM,"myosotis_1", Myosotis1Item::new);
    public static final Supplier<Item> Myosotis2=registerCommonMaterials(ZeroingITEM,"myosotis_2", Myosotis2Item::new);
    public static final Supplier<Item> Myosotis3=registerCommonMaterials(ZeroingITEM,"myosotis_3", Myosotis3Item::new);
    public static final RegistryObject<Item>SablePlume= registerMeleeItem(ZeroingITEM,
            "sable_plume", ()->new SablePlumeItem(
                    Tiers.NETHERITE, //材质（此处是自定义
                    30,             // 攻击伤害
                    -2.4F,         // 攻击速度(-2为2攻击速度，比钻剑高0.4
                    new Item.Properties().stacksTo(1) // 剑默认堆叠数为1，这里保留是为了明确，但对于剑来说是冗余的}
            )   );
    public static final RegistryObject<Item>EmbersEgnir= registerMeleeItem(ZeroingITEM,
            "embers_egnir", ()->new EmbersEgnirItem(
                    Tiers.NETHERITE, //材质（此处是自定义
                    25,             // 攻击伤害
                    -2.4F,         // 攻击速度(-2为2攻击速度，比钻剑高0.4
                    new Item.Properties().stacksTo(1) // 剑默认堆叠数为1，这里保留是为了明确，但对于剑来说是冗余的}
            )   );
    public static final RegistryObject<Item> BlankCore = registerCraftItem(ZeroingITEM,"blank_core", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    //元素核心
    public static final RegistryObject<Item> PureDarkMagicalCore = registerCraftItem(ZeroingITEM,"pure_dark_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> ThunderousMagicalCore = registerCraftItem(ZeroingITEM,"thunderous_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> EmberMagicalCore = registerCraftItem(ZeroingITEM,"ember_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> PureWaterMagicalCore = registerCraftItem(ZeroingITEM,"pure_water_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> NaturalMagicalCore = registerCraftItem(ZeroingITEM,"natural_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> FrostMagicalCore = registerCraftItem(ZeroingITEM,"frost_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> SacredSpiritMagicalCore = registerCraftItem(ZeroingITEM,"sacred_spirit_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> CrimsonMagicalCore = registerCraftItem(ZeroingITEM,"crimson_magical_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    //高阶元素素材
    public static final RegistryObject<Item> EvilEyeball = registerCraftItem(ZeroingITEM,"evil_eyeball", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> CobaltThunderGather = registerCraftItem(ZeroingITEM,"cobalt_thunder_gather", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> BlazingMagma = registerCraftItem(ZeroingITEM,"blazing_magma", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> EssenceofClarity = registerCraftItem(ZeroingITEM,"essence_of_clarity", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> WhisperofNature = registerCraftItem(ZeroingITEM,"whisper_of_nature", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> FrostBreath = registerCraftItem(ZeroingITEM,"frost_breath", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> HolyRadianceCore = registerCraftItem(ZeroingITEM,"holy_radiance_core", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    //武器素体
    public static final RegistryObject<Item> MeleeWeaponTemplate = registerCraftItem(ZeroingITEM,"melee_weapon_template", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> RangedWeaponTemplate = registerCraftItem(ZeroingITEM,"ranged_weapon_template", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> CasterWeaponTemplate = registerCraftItem(ZeroingITEM,"caster_weapon_template", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    //忆
    public static final RegistryObject<Item> Memory = registerCraftItem(ZeroingITEM,"memory", () -> new TooltipItem4(new Item.Properties().stacksTo(64).fireResistant() ));
    //普通素材
    public static final RegistryObject<Item> ForgedSteel = registerCraftItem(ZeroingITEM,"forged_steel", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> Hardwood = registerCraftItem(ZeroingITEM,"hard_wood", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    //秘银
    public static final RegistryObject<Item> MithrilShard = registerCraftItem(ZeroingITEM,"mithril_shard", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> MithrilIngot = registerCraftItem(ZeroingITEM,"mithril_ingot", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    //神圣（金色
    public static final RegistryObject<Item> BlessedMithrilIngot = registerCraftItem(ZeroingITEM,"blessed_mithril_ingot", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> DivineRadianceDiamond = registerCraftItem(ZeroingITEM,"divine_radiance_diamond", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    //魔力材料
    public static final RegistryObject<Item> MagicDust = registerCraftItem(ZeroingITEM,"magic_dust", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> MagicStone = registerCraftItem(ZeroingITEM,"magic_stone", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> DenseManaStone = registerCraftItem(ZeroingITEM,"dense_mana_stone", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> FusedMana = registerCraftItem(ZeroingITEM,"fused_mana", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> MagicLeather = registerCraftItem(ZeroingITEM,"magic_leather", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> MagicController = registerCraftItem(ZeroingITEM,"magic_controller", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> MagicSteelIngot = registerCraftItem(ZeroingITEM,"magic_steel_ingot", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    //圣灵（粉色
    public static final RegistryObject<Item> CelestialSteelIngot = registerCraftItem(ZeroingITEM,"celestial_steel_ingot", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> CelestialCrystal = registerCraftItem(ZeroingITEM,"celestial_crystal", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    //血
    public static final RegistryObject<Item> WeepingBloodHilt = registerCraftItem(ZeroingITEM,"weeping_blood_hilt", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> WeepingBloodDiamond = registerCraftItem(ZeroingITEM,"weeping_blood_diamond", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> CrimsonAlloy = registerCraftItem(ZeroingITEM,"crimson_alloy", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> HeartofBlood = registerCraftItem(ZeroingITEM,"heart_of_blood", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    //暗
    public static final RegistryObject<Item> PureDarkWood = registerCraftItem(ZeroingITEM,"pure_dark_wood", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> RefinedDarkAlloy = registerCraftItem(ZeroingITEM,"refined_dark_alloy", () -> new TooltipItem2(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> RefinedDarkGrip = registerCraftItem(ZeroingITEM,"refined_dark_grip", () -> new TooltipItem3(new Item.Properties().stacksTo(64).fireResistant() ));

    public static final RegistryObject<Item> MagicMissile = registerMagicItem(ZeroingITEM,"magic_missile", () -> new MagicMissileItem(new Item.Properties().stacksTo(1).fireResistant() ));

    public static void register(IEventBus bus){
        ZeroingITEM.register(bus);
    }
}