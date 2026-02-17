package com.inolia_zaicek.myosotis.Event;

import com.inolia_zaicek.myosotis.Register.MyAttributesRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityAttributeGiveEvent {
    @SubscribeEvent
    public static void addCustomAttributes(EntityAttributeModificationEvent event) {
        for (EntityType<? extends LivingEntity> entityType : event.getTypes()) {
            //有最大生命值时，为其新增
            if (event.has(entityType, Attributes.MAX_HEALTH)) {
                event.add(entityType, MyAttributesRegister.MaxMama.get());
                event.add(entityType, MyAttributesRegister.MamaRegan.get());
                event.add(entityType, MyAttributesRegister.DamageAmplifier.get());
                event.add(entityType, MyAttributesRegister.DamageReduction.get());
                event.add(entityType, MyAttributesRegister.MeleeAmplifier.get());
                event.add(entityType, MyAttributesRegister.MagicAmplifier.get());
                event.add(entityType, MyAttributesRegister.RangeAmplifier.get());
                event.add(entityType, MyAttributesRegister.MaxShield.get());
                event.add(entityType, MyAttributesRegister.ShieldToughness.get());
                event.add(entityType, MyAttributesRegister.MagicPower.get());
                event.add(entityType, MyAttributesRegister.CriDamage.get());
                event.add(entityType, MyAttributesRegister.CriChance.get());

                event.add(entityType, MyAttributesRegister.FireAccumulationAbility.get());
                event.add(entityType, MyAttributesRegister.WaterAccumulationAbility.get());
                event.add(entityType, MyAttributesRegister.LightningAccumulationAbility.get());
                event.add(entityType, MyAttributesRegister.IceAccumulationAbility.get());
                event.add(entityType, MyAttributesRegister.NatureAccumulationAbility.get());
                event.add(entityType, MyAttributesRegister.HolyAccumulationAbility.get());
                event.add(entityType, MyAttributesRegister.BloodAccumulationAbility.get());
                event.add(entityType, MyAttributesRegister.ShadowAccumulationAbility.get());

                event.add(entityType, MyAttributesRegister.FireResonanceAbility.get());
                event.add(entityType, MyAttributesRegister.WaterResonanceAbility.get());
                event.add(entityType, MyAttributesRegister.LightningResonanceAbility.get());
                event.add(entityType, MyAttributesRegister.IceResonanceAbility.get());
                event.add(entityType, MyAttributesRegister.NatureResonanceAbility.get());
                event.add(entityType, MyAttributesRegister.HolyResonanceAbility.get());
                event.add(entityType, MyAttributesRegister.BloodResonanceAbility.get());
                event.add(entityType, MyAttributesRegister.ShadowResonanceAbility.get());
            }
        }
    }

}