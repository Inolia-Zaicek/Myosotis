package com.inolia_zaicek.myosotis.Register;

import com.inolia_zaicek.myosotis.Register.buff.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.inolia_zaicek.myosotis.Myosotis.MODID;

public class MyEffectsRegister {
    public static final DeferredRegister<MobEffect> INOEFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,MODID);
    //决斗
    public static final RegistryObject<MobEffect> CrepuscularStagnation = INOEFFECT.register("crepuscular_stagnation", CrepuscularStagnationBuff::new);
    public static final RegistryObject<MobEffect> SelfImmolation = INOEFFECT.register("self_immolation", SelfImmolationBuff::new);
    public static final RegistryObject<MobEffect> BrightFlame = INOEFFECT.register("bright_flame", BrightFlameBuff::new);
    public static final RegistryObject<MobEffect> ShieldRed = INOEFFECT.register("shield_red", ShieldRedBuff::new);
    public static final RegistryObject<MobEffect> ShieldYellow = INOEFFECT.register("shield_yellow", ShieldYellowBuff::new);
    public static final RegistryObject<MobEffect> ShieldGreen = INOEFFECT.register("shield_green", ShieldGreenBuff::new);
}
