package com.inolia_zaicek.myosotis.Register;

import com.inolia_zaicek.myosotis.Myosotis;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MyAttributesRegister {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Myosotis.MODID);
    //法力上限
    public static final RegistryObject<Attribute> MaxMama = ATTRIBUTES.register("max_mana",
            () -> new RangedAttribute(Myosotis.MODID + ".max_mana",
                    100,//基础值
                    0,//最小值
                    Float.MAX_VALUE//最大值
            ).setSyncable(true));
    public static final RegistryObject<Attribute> MamaRegan = ATTRIBUTES.register("mana_regan",
            () -> new RangedAttribute(Myosotis.MODID + ".mana_regan",
                    10,//基础值
                    0,//最小值
                    Float.MAX_VALUE//最大值
            ).setSyncable(true));
    public static final RegistryObject<Attribute> DamageAmplifier = ATTRIBUTES.register("damage_amplifier",
            () -> new RangedAttribute(Myosotis.MODID + ".damage_amplifier",
                    1,//基础值
                    -Float.MAX_VALUE,//最小值
                    Float.MAX_VALUE//最大值
            ).setSyncable(true));
    public static final RegistryObject<Attribute> DamageReduction = ATTRIBUTES.register("damage_reduction",
            () -> new RangedAttribute(Myosotis.MODID + ".damage_reduction",
                    1,//基础值
                    -Float.MAX_VALUE,//最小值
                    Float.MAX_VALUE//最大值
            ).setSyncable(true));
    public static final RegistryObject<Attribute> MeleeAmplifier = ATTRIBUTES.register("melee_amplifier",
            () -> new RangedAttribute(Myosotis.MODID + ".melee_amplifier",
                    1,//基础值
                    -Float.MAX_VALUE,//最小值
                    Float.MAX_VALUE//最大值
            ).setSyncable(true));
    public static final RegistryObject<Attribute> MagicAmplifier = ATTRIBUTES.register("magic_amplifier",//魔法增幅不需要写进纯伤害的代码里面，因为在hurt事件已经增幅了
            () -> new RangedAttribute(Myosotis.MODID + ".magic_amplifier",
                    1,//基础值
                    -Float.MAX_VALUE,//最小值
                    Float.MAX_VALUE//最大值
            ).setSyncable(true));
    public static final RegistryObject<Attribute> RangeAmplifier = ATTRIBUTES.register("range_amplifier",
            () -> new RangedAttribute(Myosotis.MODID + ".range_amplifier",
                    1,//基础值
                    -Float.MAX_VALUE,//最小值
                    Float.MAX_VALUE//最大值
            ).setSyncable(true));
    public static final RegistryObject<Attribute> MaxShield = ATTRIBUTES.register("max_shield",
            () -> new RangedAttribute(Myosotis.MODID + ".max_shield",
                    1,//基础值
                    0,
                    Float.MAX_VALUE//最大值
            ).setSyncable(true));
    public static final RegistryObject<Attribute> ShieldToughness = ATTRIBUTES.register("shield_toughness",
            () -> new RangedAttribute(Myosotis.MODID + ".shield_toughness",
                    1,//基础值
                    0,//最小值
                    Float.MAX_VALUE//最大值
            ).setSyncable(true));
    public static final RegistryObject<Attribute> MagicPower = ATTRIBUTES.register("magic_power",
            () -> new RangedAttribute(Myosotis.MODID + ".magic_power",
                    0,//基础值
                    -Float.MAX_VALUE,//最小值
                    Float.MAX_VALUE//最大值
            ).setSyncable(true));
    public static final RegistryObject<Attribute> CriDamage = ATTRIBUTES.register("cri_damage",
            () -> new RangedAttribute(Myosotis.MODID + ".cri_damage",
                    1.5,//基础值
                    1,//最小值
                    Float.MAX_VALUE//最大值
            ).setSyncable(true));
    public static final RegistryObject<Attribute> CriChance = ATTRIBUTES.register("cri_chance",
            () -> new RangedAttribute(Myosotis.MODID + ".cri_chance",
                    0,//基础值
                    0,//最小值
                    Float.MAX_VALUE//最大值
            ).setSyncable(true));
    //积累
    public static final RegistryObject<Attribute> FireAccumulationAbility = ATTRIBUTES.register("fire_accumulation_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".fire_accumulation_ability", 0, 0, Float.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> WaterAccumulationAbility = ATTRIBUTES.register("water_accumulation_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".water_accumulation_ability", 0, 0, Float.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> LightningAccumulationAbility = ATTRIBUTES.register("lightning_accumulation_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".lightning_accumulation_ability", 0, 0, Float.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> IceAccumulationAbility = ATTRIBUTES.register("ice_accumulation_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".ice_accumulation_ability", 0, 0, Float.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> NatureAccumulationAbility = ATTRIBUTES.register("nature_accumulation_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".nature_accumulation_ability", 0, 0, Float.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> HolyAccumulationAbility = ATTRIBUTES.register("holy_accumulation_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".holy_accumulation_ability", 0, 0, Float.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> BloodAccumulationAbility = ATTRIBUTES.register("blood_accumulation_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".blood_accumulation_ability", 0, 0, Float.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> ShadowAccumulationAbility = ATTRIBUTES.register("shadow_accumulation_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".shadow_accumulation_ability", 0, 0, Float.MAX_VALUE).setSyncable(true));
    //共鸣
    public static final RegistryObject<Attribute> FireResonanceAbility = ATTRIBUTES.register("fire_resonance_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".fire_resonance_ability", 1, 0, Float.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> WaterResonanceAbility = ATTRIBUTES.register("water_resonance_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".water_resonance_ability", 1, 0, Float.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> LightningResonanceAbility = ATTRIBUTES.register("lightning_resonance_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".lightning_resonance_ability", 1, 0, Float.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> IceResonanceAbility = ATTRIBUTES.register("ice_resonance_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".ice_resonance_ability", 1, 0, Float.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> NatureResonanceAbility = ATTRIBUTES.register("nature_resonance_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".nature_resonance_ability", 1, 0, Float.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> HolyResonanceAbility = ATTRIBUTES.register("holy_resonance_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".holy_resonance_ability", 1, 0, Float.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> BloodResonanceAbility = ATTRIBUTES.register("blood_resonance_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".blood_resonance_ability", 1, 0, Float.MAX_VALUE).setSyncable(true));
    public static final RegistryObject<Attribute> ShadowResonanceAbility = ATTRIBUTES.register("shadow_resonance_ability",
            () -> new RangedAttribute(Myosotis.MODID + ".shadow_resonance_ability", 1, 0, Float.MAX_VALUE).setSyncable(true));
}