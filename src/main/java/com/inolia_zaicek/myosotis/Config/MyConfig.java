package com.inolia_zaicek.myosotis.Config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MyConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> myosotis_1_sleep;
    public static final ForgeConfigSpec.DoubleValue myosotis_1_night;
    public static final ForgeConfigSpec.DoubleValue myosotis_1_debuff;
    public static final ForgeConfigSpec.DoubleValue myosotis_1_heal;
    public static final ForgeConfigSpec.DoubleValue myosotis_1_hp;
    public static final ForgeConfigSpec.DoubleValue myosotis_1_max_buff;
    public static final ForgeConfigSpec.ConfigValue<Boolean> myosotis_1_mob;
    public static final ForgeConfigSpec.DoubleValue myosotis_1_buff;
    public static final ForgeConfigSpec.DoubleValue myosotis_1_armor;
    public static final ForgeConfigSpec.DoubleValue myosotis_1_armor_toughness;
    public static final ForgeConfigSpec.ConfigValue<Boolean> myosotis_1_fire;
    public static final ForgeConfigSpec.DoubleValue myosotis_1_source_damage;
    public static final ForgeConfigSpec.DoubleValue myosotis_1_hp_damage;

    public static final ForgeConfigSpec.ConfigValue<Boolean> myosotis_2_sleep;
    public static final ForgeConfigSpec.DoubleValue myosotis_2_night;
    public static final ForgeConfigSpec.DoubleValue myosotis_2_debuff;
    public static final ForgeConfigSpec.DoubleValue myosotis_2_heal;
    public static final ForgeConfigSpec.DoubleValue myosotis_2_hp;
    public static final ForgeConfigSpec.DoubleValue myosotis_2_max_buff;
    public static final ForgeConfigSpec.ConfigValue<Boolean> myosotis_2_mob;
    public static final ForgeConfigSpec.DoubleValue myosotis_2_buff;
    public static final ForgeConfigSpec.DoubleValue myosotis_2_armor;
    public static final ForgeConfigSpec.DoubleValue myosotis_2_armor_toughness;
    public static final ForgeConfigSpec.DoubleValue myosotis_2_source_damage;
    public static final ForgeConfigSpec.DoubleValue myosotis_2_hp_damage;
    static {
    //祝福
    BUILDER.push("base");
        myosotis_1_sleep = BUILDER.comment("勿忘我1是否禁止睡觉").define("myosotis_1_sleep",true);
        myosotis_1_night = BUILDER.comment("勿忘我1伤减").defineInRange("myosotis_1_night",0.5,0,2147483647);
        myosotis_1_debuff = BUILDER.comment("勿忘我1负面延时").defineInRange("myosotis_1_debuff",1.0,0,2147483647);
        myosotis_1_heal = BUILDER.comment("勿忘我1治疗降低").defineInRange("myosotis_1_heal",0.7,0,2147483647);
        myosotis_1_hp = BUILDER.comment("勿忘我1生命降低").defineInRange("myosotis_1_hp",0.3,0,2147483647);
        myosotis_1_max_buff = BUILDER.comment("勿忘我1暮滞层数上限").defineInRange("myosotis_1_max_buff",5.0,0,2147483647);
        myosotis_1_mob = BUILDER.comment("勿忘我1是否吸引怪物仇恨").define("myosotis_1_mob",true);
        myosotis_1_buff = BUILDER.comment("勿忘我1正面减时").defineInRange("myosotis_1_buff",0.5,0,2147483647);
        myosotis_1_armor = BUILDER.comment("勿忘我1护甲降低").defineInRange("myosotis_1_armor",0.5,0,2147483647);
        myosotis_1_armor_toughness = BUILDER.comment("勿忘我1护甲韧性降低").defineInRange("myosotis_1_armor_toughness",0.5,0,2147483647);
        myosotis_1_fire = BUILDER.comment("勿忘我1是否持续着火").define("myosotis_1_fire",true);
        myosotis_1_source_damage = BUILDER.comment("勿忘我1有源伤害增幅").defineInRange("myosotis_1_source_damage",0.3,0,2147483647);
        myosotis_1_hp_damage = BUILDER.comment("勿忘我1最低伤害").defineInRange("myosotis_1_hp_damage",0.2,0,2147483647);

        myosotis_2_sleep = BUILDER.comment("勿忘我2是否禁止睡觉").define("myosotis_2_sleep",true);
        myosotis_2_night = BUILDER.comment("勿忘我2伤减").defineInRange("myosotis_2_night",0.5,0,2147483647);
        myosotis_2_debuff = BUILDER.comment("勿忘我2负面延时").defineInRange("myosotis_2_debuff",0.5,0,2147483647);
        myosotis_2_heal = BUILDER.comment("勿忘我2治疗降低").defineInRange("myosotis_2_heal",0.3,0,2147483647);
        myosotis_2_hp = BUILDER.comment("勿忘我2生命降低").defineInRange("myosotis_2_hp",0.2,0,2147483647);
        myosotis_2_max_buff = BUILDER.comment("勿忘我2暮滞层数上限").defineInRange("myosotis_2_max_buff",3.0,0,2147483647);
        myosotis_2_mob = BUILDER.comment("勿忘我2是否吸引怪物仇恨").define("myosotis_2_mob",true);
        myosotis_2_buff = BUILDER.comment("勿忘我2正面减时").defineInRange("myosotis_2_buff",0.5,0,2147483647);
        myosotis_2_armor = BUILDER.comment("勿忘我2护甲降低").defineInRange("myosotis_2_armor",0.3,0,2147483647);
        myosotis_2_armor_toughness = BUILDER.comment("勿忘我2护甲韧性降低").defineInRange("myosotis_2_armor_toughness",0.3,0,2147483647);
        myosotis_2_source_damage = BUILDER.comment("勿忘我2有源伤害增幅").defineInRange("myosotis_2_source_damage",0.2,0,2147483647);
        myosotis_2_hp_damage = BUILDER.comment("勿忘我2最低伤害").defineInRange("myosotis_2_hp_damage",0.1,0,2147483647);


    BUILDER.pop();
    SPEC = BUILDER.build();
    }
}
