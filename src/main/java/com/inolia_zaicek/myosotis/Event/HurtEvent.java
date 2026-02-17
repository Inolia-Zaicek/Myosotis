package com.inolia_zaicek.myosotis.Event;

import com.inolia_zaicek.myosotis.Config.MyConfig;
import com.inolia_zaicek.myosotis.Myosotis;
import com.inolia_zaicek.myosotis.Register.MyAttributesRegister;
import com.inolia_zaicek.myosotis.Register.DamageRegister;
import com.inolia_zaicek.myosotis.Register.MyItemRegister;
import com.inolia_zaicek.myosotis.Register.MyEffectsRegister;
import com.inolia_zaicek.myosotis.Util.MyUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

import static net.minecraft.tags.DamageTypeTags.IS_PROJECTILE;
import static net.minecraft.tags.DamageTypeTags.WITCH_RESISTANT_TO;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = Myosotis.MODID)
public class HurtEvent {
    public static final String embers_egnir_nbt = Myosotis.MODID + ":embers_egnir";
    public static final String bright_flame_nbt = Myosotis.MODID + ":bright_flame";
    //护盾值默认为血量*100，计算时/100
    public static final String shield_number = Myosotis.MODID + ":shield";
    public static final String KEY_ENTITY = "entity";
    //积累进度
    public static final String FireAccumulationNumber = Myosotis.MODID + ":fire_accumulation";
    public static final String WaterAccumulationNumber = Myosotis.MODID + ":water_accumulation";
    public static final String LightningAccumulationNumber = Myosotis.MODID + ":lightning_accumulation";
    public static final String IceAccumulationNumber = Myosotis.MODID + ":ice_accumulation";
    public static final String NatureAccumulationNumber = Myosotis.MODID + ":nature_accumulation";
    public static final String HolyAccumulationNumber = Myosotis.MODID + ":holy_accumulation";
    public static final String BloodAccumulationNumber = Myosotis.MODID + ":blood_accumulation";
    public static final String ShadowAccumulationNumber = Myosotis.MODID + ":shadow_accumulation";
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        Random random = new Random();
        LivingEntity attacked = event.getEntity();
        if(attacked!=null){
            CompoundTag compoundTag = attacked.getPersistentData();
            float shield = compoundTag.getFloat(shield_number);
            double number = 1;
            double overNumber = 1;
            double fixedNumber = 0;
            double baseDamage = event.getAmount();
            double armor = attacked.getAttributeValue(Attributes.ARMOR);
            double armorToughness = attacked.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
            //护甲减伤后的伤害【护甲减伤：1-4%*(护甲值-伤害*4/(8+盔甲韧性))——若为0.3减伤，则是A*(1-0.3)=X，要回复则为X/(1-0.3)=A
            //【100*0.7=70，70/(0.7)
            float armorDamage = (float) (baseDamage / (1 - 0.04 * (armor - baseDamage * 4 / (8 + armorToughness))) );
            /// 护盾开始计算
            if(shield>0){
                //护盾可以抵挡的伤害
                float shieldToughness = 1;
                if(attacked.getAttributes().hasAttribute( MyAttributesRegister.ShieldToughness.get() )) {
                    shieldToughness = (float) attacked.getAttributeValue(MyAttributesRegister.ShieldToughness.get());
                }
                //护盾大于减伤前伤害——完全抵消
                if(shield * shieldToughness>= armorDamage){
                    baseDamage = 0;
                    MyUtil.reduceShield(attacked,armorDamage/shieldToughness);
                }
                //小于
                else{
                    baseDamage -= shield * shieldToughness;
                    MyUtil.setShield(attacked,0);
                }
            }
            //伤害减免
            if(attacked.getAttributes().hasAttribute( MyAttributesRegister.DamageReduction.get() )) {
                overNumber *= Math.max(0,2 - (float) attacked.getAttributeValue(MyAttributesRegister.DamageReduction.get()));
            }
            //「尘躯」
            if(MyUtil.isCurioEquipped(attacked, MyItemRegister.Myosotis1.get()) ){
                if(event.getSource().getEntity()!=null||event.getSource().getDirectEntity()!=null){
                    number += MyConfig.myosotis_1_source_damage.get();
                }
            }
            if(MyUtil.isCurioEquipped(attacked, MyItemRegister.Myosotis2.get()) ){
                if(event.getSource().getEntity()!=null||event.getSource().getDirectEntity()!=null){
                    number += MyConfig.myosotis_2_source_damage.get();
                }
            }
            if(MyUtil.hasItemInEitherHand(attacked, MyItemRegister.SablePlume.get())){
                number += 0.5F;
            }
            //最终伤害
            double damage = (baseDamage*number+fixedNumber)*overNumber;
            //「重创」
            if (MyUtil.isCurioEquipped(attacked, MyItemRegister.Myosotis1.get()) && damage<attacked.getMaxHealth()*MyConfig.myosotis_1_hp_damage.get()) {
               damage = attacked.getMaxHealth()*MyConfig.myosotis_1_hp_damage.get();
            }
            if (MyUtil.isCurioEquipped(attacked, MyItemRegister.Myosotis2.get()) && damage<attacked.getMaxHealth()*MyConfig.myosotis_2_hp_damage.get()) {
                damage = attacked.getMaxHealth()*MyConfig.myosotis_2_hp_damage.get();
            }
            if (MyUtil.isCurioEquipped(attacked, MyItemRegister.Myosotis3.get()) && damage<attacked.getMaxHealth()*0.05F) {
                damage = attacked.getMaxHealth()*0.05F;
            }
            if(damage>0) {
                event.setAmount((float) damage);
            }
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked!=null) {
            CompoundTag attackedTag = attacked.getPersistentData();
            CompoundTag attackerTag = attacker.getPersistentData();
            double number = 1;
            double overNumber = 1;
            double fixedNumber = 0;
            long gameTime = attacker.level().getDayTime();
            //积累：
            float fireAccumulation = attackedTag.getFloat(FireAccumulationNumber);
            float waterAccumulation = attackedTag.getFloat(WaterAccumulationNumber);
            float lightningAccumulation = attackedTag.getFloat(LightningAccumulationNumber);
            float iceAccumulation = attackedTag.getFloat(IceAccumulationNumber);
            float natureAccumulation = attackedTag.getFloat(NatureAccumulationNumber);
            float holyAccumulation = attackedTag.getFloat(HolyAccumulationNumber);
            float bloodAccumulation = attackedTag.getFloat(BloodAccumulationNumber);
            float shadowAccumulation = attackedTag.getFloat(ShadowAccumulationNumber);
            //基础元素积累值
            float baseEle = 100;
            if(MyUtil.isElementLvlUp25(event.getEntity().getType())){
                baseEle+=baseEle*0.25F;
            }
            if(MyUtil.isElementLvlUp50(event.getEntity().getType())){
                baseEle+=baseEle*0.5F;
            }
            if(MyUtil.isElementLvlUp75(event.getEntity().getType())){
                baseEle+=baseEle*0.75F;
            }
            if(MyUtil.isElementLvlUp100(event.getEntity().getType())){
                baseEle+=baseEle;
            }
            var PhysicsDamageType = MyUtil.hasSource(attacker.level(), DamageRegister.PhysicsDamage,attacker);
            var MagicDamageType = MyUtil.hasSource(attacker.level(), DamageRegister.MagicDamage,attacker);
            var ElementDamageType = MyUtil.hasSource(attacker.level(), DamageRegister.ElementDamage,attacker);
            var DotDamageType = MyUtil.hasSource(attacker.level(), DamageRegister.DotDamage,attacker);
            var TrueDamageType = MyUtil.hasSource(attacker.level(), DamageRegister.TrueDamage,attacker);
            if(MyUtil.hasItemInEitherHand(attacker, MyItemRegister.EmbersEgnir.get()) ){
                //叠火
                int embers_egnir = attackedTag.getInt(embers_egnir_nbt);
                attackedTag.putInt(embers_egnir_nbt, Math.min(100,embers_egnir+10) );
                if (attacked.hasEffect(MyEffectsRegister.BrightFlame.get())) {
                    var map = attacked.getActiveEffectsMap();
                    attacked.addEffect(new MobEffectInstance(MyEffectsRegister.BrightFlame.get(), 100, 0));
                    if (!attacked.hasEffect(MyEffectsRegister.BrightFlame.get())) {
                        attackedTag.put(KEY_ENTITY, attacker.serializeNBT());
                        map.put(MyEffectsRegister.BrightFlame.get(), new MobEffectInstance(MyEffectsRegister.BrightFlame.get(), 101, 0));
                    }
                }
            }
            if(MyUtil.hasItemInEitherHand(attacker, MyItemRegister.SablePlume.get()) ){
                int attackedNeutralAndHarmfulCount = 0;
                for (MobEffectInstance effect : attacked.getActiveEffects()) {
                    // 判断是否为NEUTRAL或Harmful
                    boolean isNEUTRAL = effect.getEffect().getCategory() == MobEffectCategory.NEUTRAL;
                    boolean isHarmful = effect.getEffect().getCategory() == MobEffectCategory.HARMFUL;
                    // 统计非NEUTRAL且非Harmful的效果
                    if (isNEUTRAL || isHarmful) {
                        attackedNeutralAndHarmfulCount++;
                    }
                }
                int attackerNeutralAndHarmfulCount = 0;
                for (MobEffectInstance effect : attacker.getActiveEffects()) {
                    // 判断是否为NEUTRAL或Harmful
                    boolean isNEUTRAL = effect.getEffect().getCategory() == MobEffectCategory.NEUTRAL;
                    boolean isHarmful = effect.getEffect().getCategory() == MobEffectCategory.HARMFUL;
                    // 统计非NEUTRAL且非Harmful的效果
                    if (isNEUTRAL || isHarmful) {
                        attackerNeutralAndHarmfulCount++;
                    }
                }
                number *= 1+0.1F*attackedNeutralAndHarmfulCount+0.15F*attackerNeutralAndHarmfulCount;
                if (MyUtil.isMeleeAttack(event.getSource())) {
                    attacker.heal(attacker.getMaxHealth()*0.03F);
                    attacked.addEffect(new MobEffectInstance(MobEffects.WITHER,100,0));
                }
            }
            //暮滞
            if (MyUtil.isCurioEquipped(attacker, MyItemRegister.Myosotis1.get()) && MyConfig.myosotis_1_max_buff.get() > 0){
                if(attacker.hasEffect(MyEffectsRegister.CrepuscularStagnation.get())){
                    int level = attacker.getEffect(MyEffectsRegister.CrepuscularStagnation.get()).getAmplifier();
                attacker.addEffect(new MobEffectInstance(MyEffectsRegister.CrepuscularStagnation.get(),100, (int) Math.min(MyConfig.myosotis_1_max_buff.get()-1,level+1)));
                }else{
                    attacker.addEffect(new MobEffectInstance(MyEffectsRegister.CrepuscularStagnation.get(),100,0));
                }
                if(gameTime>=13801&&gameTime<=22200) {
                    number+=MyConfig.myosotis_1_night.get();
                }
            }
            if (MyUtil.isCurioEquipped(attacker, MyItemRegister.Myosotis2.get()) && MyConfig.myosotis_2_max_buff.get() > 0 ) {
                if (attacker.hasEffect(MyEffectsRegister.CrepuscularStagnation.get())) {
                    int level = attacker.getEffect(MyEffectsRegister.CrepuscularStagnation.get()).getAmplifier();
                    attacker.addEffect(new MobEffectInstance(MyEffectsRegister.CrepuscularStagnation.get(), 100,(int) Math.min(MyConfig.myosotis_2_max_buff.get()-1, level + 1)));
                } else {
                    attacker.addEffect(new MobEffectInstance(MyEffectsRegister.CrepuscularStagnation.get(), 100, 0));
                }
                if (gameTime >= 13801 && gameTime <= 22200) {
                    number+=MyConfig.myosotis_2_night.get();
                }
            }
            if(attacker.getAttributes().hasAttribute( MyAttributesRegister.DamageAmplifier.get() )) {
                overNumber *= attacker.getAttributeValue(MyAttributesRegister.DamageAmplifier.get());
            }
            //近战增幅
            if(attacker.getAttributes().hasAttribute( MyAttributesRegister.MeleeAmplifier.get() )) {
                if (MyUtil.isMeleeAttack(event.getSource())) {
                    overNumber *= attacker.getAttributeValue(MyAttributesRegister.MeleeAmplifier.get());
                }
            }
            //魔法增幅
            if(attacker.getAttributes().hasAttribute( MyAttributesRegister.MagicAmplifier.get() )) {
                if (event.getSource().is(WITCH_RESISTANT_TO)||event.getSource().is(DamageRegister.MagicDamage) ) {
                    overNumber *= attacker.getAttributeValue(MyAttributesRegister.MagicAmplifier.get());
                }else if ((ModList.get().isLoaded("alshanex_familiars")
                        && event.getSource().type().msgId().equals(new ResourceLocation("alshanex_familiars", "sound_magic"))
                )|| (ModList.get().isLoaded("traveloptics")
                        && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                        && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                ) || (ModList.get().isLoaded("fantasy_ending")
                        && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                ) || (ModList.get().isLoaded("fantasy_ending")
                        && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                )) {
                    overNumber *= attacker.getAttributeValue(MyAttributesRegister.MagicAmplifier.get());
                }else if ((ModList.get().isLoaded("ars_nouveau")
                        && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "spell"))
                )|| (ModList.get().isLoaded("ars_nouveau")
                        && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "windshear"))
                )        || (ModList.get().isLoaded("ars_nouveau")
                        && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "frost"))
                )        || (ModList.get().isLoaded("ars_nouveau")
                        && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "flare"))
                )        || (ModList.get().isLoaded("ars_nouveau")
                        && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "crush"))
                )){
                    overNumber *= attacker.getAttributeValue(MyAttributesRegister.MagicAmplifier.get());
                }
                //弹射物
                if(attacker.getAttributes().hasAttribute( MyAttributesRegister.RangeAmplifier.get() )) {
                    if (event.getSource().is(IS_PROJECTILE)) {
                        overNumber *= attacker.getAttributeValue(MyAttributesRegister.RangeAmplifier.get());
                    } else if ((ModList.get().isLoaded("tacz")
                            && event.getSource().type().msgId().equals(new ResourceLocation("tacz", "bullet"))
                    )|| (ModList.get().isLoaded("tacz")
                            && event.getSource().type().msgId().equals(new ResourceLocation("tacz", "bullet_ignore_armor"))
                    ) || (ModList.get().isLoaded("tacz")
                            && event.getSource().type().msgId().equals(new ResourceLocation("tacz", "bullet_void"))
                    ) || (ModList.get().isLoaded("tacz")
                            && event.getSource().type().msgId().equals(new ResourceLocation("tacz", "bullet_void_ignore_armor"))
                    ) || (ModList.get().isLoaded("tacz")
                            && event.getSource().type().msgId().equals(new ResourceLocation("tacz", "bullets"))
                    )
                    ) {
                        overNumber *= attacker.getAttributeValue(MyAttributesRegister.RangeAmplifier.get());
                    } else if ((ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "gunfire"))
                    )|| (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "gunfire_absolute"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "gunfire_headshot"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "gunfire_headshot_absolute"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "laser"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "laser_headshot"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "projectile_hit"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "projectile_explosion"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "custom_explosion"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "drone_hit"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "laser_static"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "grapeshot_hit"))
                    )) {
                        overNumber *= attacker.getAttributeValue(MyAttributesRegister.RangeAmplifier.get());
                    }
                }
            }
            //暴击
            if(attacked.getAttributes().hasAttribute( MyAttributesRegister.CriChance.get())
                    && attacked.getAttributes().hasAttribute( MyAttributesRegister.CriDamage.get()) ) {
                if (attacked.getAttributeValue(MyAttributesRegister.CriChance.get())>0
                        &&random.nextInt(100) <= attacked.getAttributeValue(MyAttributesRegister.CriChance.get()) * 100) {
                    overNumber*= attacker.getAttributeValue(MyAttributesRegister.CriDamage.get());
                }
            }
            double damage = (event.getAmount()*number+fixedNumber)*overNumber;
            //伤害数额出来后计算元素积累
            if(attacker.getAttributes().hasAttribute(MyAttributesRegister.FireAccumulationAbility.get())){
                float accumulation = (float) attacker.getAttributeValue(MyAttributesRegister.FireAccumulationAbility.get());
                //应添加元素积累值+当前元素积累值＞上限
                if(accumulation+fireAccumulation>=baseEle){
                    attackedTag.putFloat(FireAccumulationNumber,0);
                    //共鸣强度——————————基础倍率——————*属性
                    float resonance = 0.5F*(float) attacker.getAttributeValue(MyAttributesRegister.FireResonanceAbility.get());
                    var mobList = MyUtil.mobList(4, attacked);
                    for (Mob mobs : mobList) {
                        //范围内，生物并非攻击者的随从，受伤
                        if (!(mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null && ownableEntity.getOwner() == attacker)) {
                            mobs.invulnerableTime = 0;
                            mobs.hurt(ElementDamageType, (float) (damage * resonance));
                        }
                    }
                }else{
                    attackedTag.putFloat(FireAccumulationNumber,accumulation+fireAccumulation);
                }
            }
            //焚身数额
            attackedTag.putInt(bright_flame_nbt, (int) (damage*100));
            event.setAmount((float) damage);
        }
    }
}
