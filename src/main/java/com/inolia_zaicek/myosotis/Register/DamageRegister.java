package com.inolia_zaicek.myosotis.Register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

import static com.inolia_zaicek.myosotis.Myosotis.MODID;


//source的create方法
public class DamageRegister {
    private static ResourceKey<DamageType> create(String name){
        return ResourceKey.create(Registries.DAMAGE_TYPE,new ResourceLocation(MODID,name));
    }
    public DamageRegister(){}
    public static final ResourceKey<DamageType> TrueDamage = create("true_damage");
    public static final ResourceKey<DamageType> DotDamage = create("dot_damage");
    public static final ResourceKey<DamageType> PhysicsDamage = create("physics_damage");
    public static final ResourceKey<DamageType> MagicDamage = create("magic_damage");
    public static final ResourceKey<DamageType> ElementDamage = create("element_damage");
    }
