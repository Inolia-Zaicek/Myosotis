package com.inolia_zaicek.myosotis.Event;

import com.inolia_zaicek.myosotis.Myosotis;
import com.inolia_zaicek.myosotis.Register.ItemRegister;
import com.inolia_zaicek.myosotis.Register.MyEffectsRegister;
import com.inolia_zaicek.myosotis.Util.MyUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = Myosotis.MODID)
public class HurtEvent {
    public static final String embers_egnir_nbt = Myosotis.MODID + ":embers_egnir";
    public static final String bright_flame_nbt = Myosotis.MODID + ":bright_flame";
    //护盾值默认为血量*100，计算时/100
    public static final String shield_number = Myosotis.MODID + ":shield";
    public static final String KEY_ENTITY = "entity";
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        if(attacked!=null){
            CompoundTag compoundTag = attacked.getPersistentData();
            int shield = compoundTag.getInt(shield_number);
            float number = 1;
            float overNumber = 1;
            float fixedNumber = 0;
            float baseDamage = event.getAmount();
            double armor = attacked.getAttributeValue(Attributes.ARMOR);
            double armorToughness = attacked.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
            //护甲减伤后的伤害【护甲减伤：1-4%*(护甲值-伤害*4/(8+盔甲韧性))——若为0.3减伤，则是A*(1-0.3)=X，要回复则为X/(1-0.3)=A
            //【100*0.7=70，70/(0.7)
            float armorDamage = (float) (baseDamage / (1 - 0.04 * (armor - baseDamage * 4 / (8 + armorToughness))) );
            //如果护盾抵消了，减少基础伤害数额baseDamage
            if(shield>0){
                //护盾大于减伤前伤害——完全抵消
                if((float) shield /100 >= armorDamage){
                    baseDamage = 0;
                    compoundTag.putInt(shield_number, (int) Math.max(0,(shield-armorDamage*100) ) );
                }
                //小于————减免护盾值的数额/100
                else{
                    baseDamage -= (float) shield /100;
                    compoundTag.putInt(shield_number, 0 );
                }
            }
            //「尘躯」
            if(MyUtil.isCurioEquipped(attacked, ItemRegister.Myosotis1.get()) ){
                if(event.getSource().getEntity()!=null||event.getSource().getDirectEntity()!=null){
                    number *= 1.3F;
                }
            }
            if(MyUtil.isCurioEquipped(attacked, ItemRegister.Myosotis2.get()) ){
                if(event.getSource().getEntity()!=null||event.getSource().getDirectEntity()!=null){
                    number *= 1.2F;
                }
            }
            if(MyUtil.hasItemInEitherHand(attacked,ItemRegister.SablePlume.get())){
                number *= 1.5F;
            }
            //最终伤害
            float damage = (baseDamage*number+fixedNumber)*overNumber;
            //「重创」
            if (MyUtil.isCurioEquipped(attacked, ItemRegister.Myosotis1.get()) && damage<attacked.getMaxHealth()*0.2F) {
               damage = attacked.getMaxHealth()*0.2F;
            }
            if (MyUtil.isCurioEquipped(attacked, ItemRegister.Myosotis2.get()) && damage<attacked.getMaxHealth()*0.1F) {
                damage = attacked.getMaxHealth()*0.1F;
            }
            if (MyUtil.isCurioEquipped(attacked, ItemRegister.Myosotis3.get()) && damage<attacked.getMaxHealth()*0.05F) {
                damage = attacked.getMaxHealth()*0.05F;
            }
            event.setAmount(damage);
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked!=null) {
            CompoundTag attackedTag = attacked.getPersistentData();
            CompoundTag attackerTag = attacker.getPersistentData();
            float number = 1;
            float overNumber = 1;
            float fixedNumber = 0;
            long gameTime = attacker.level().getDayTime();
            if(MyUtil.hasItemInEitherHand(attacker,ItemRegister.EmbersEgnir.get()) ){
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
            if(MyUtil.hasItemInEitherHand(attacker,ItemRegister.SablePlume.get()) ){
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
            if (MyUtil.isCurioEquipped(attacker, ItemRegister.Myosotis1.get()) ){
                if(attacker.hasEffect(MyEffectsRegister.CrepuscularStagnation.get())){
                    int level = attacker.getEffect(MyEffectsRegister.CrepuscularStagnation.get()).getAmplifier();
                attacker.addEffect(new MobEffectInstance(MyEffectsRegister.CrepuscularStagnation.get(),100,Math.min(4,level+1) ));
                }else{
                    attacker.addEffect(new MobEffectInstance(MyEffectsRegister.CrepuscularStagnation.get(),100,0));
                }
                if(gameTime>=13801&&gameTime<=22200) {
                    number*=0.5F;
                }
            }
            if (MyUtil.isCurioEquipped(attacker, ItemRegister.Myosotis2.get()) ){
                if(attacker.hasEffect(MyEffectsRegister.CrepuscularStagnation.get())){
                    int level = attacker.getEffect(MyEffectsRegister.CrepuscularStagnation.get()).getAmplifier();
                    attacker.addEffect(new MobEffectInstance(MyEffectsRegister.CrepuscularStagnation.get(),100,Math.min(2,level+1) ));
                }else{
                    attacker.addEffect(new MobEffectInstance(MyEffectsRegister.CrepuscularStagnation.get(),100,0));
                }
                if(gameTime>=13801&&gameTime<=22200) {
                    number*=0.5F;
                }
            }
            float damage = (event.getAmount()*number+fixedNumber)*overNumber;
            //焚身数额
            attackedTag.putInt(bright_flame_nbt, (int) (damage*100));
            event.setAmount(damage);
        }
    }
}
