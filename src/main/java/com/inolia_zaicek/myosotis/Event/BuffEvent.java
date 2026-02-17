package com.inolia_zaicek.myosotis.Event;

import com.inolia_zaicek.myosotis.Config.MyConfig;
import com.inolia_zaicek.myosotis.Myosotis;
import com.inolia_zaicek.myosotis.Register.MyItemRegister;
import com.inolia_zaicek.myosotis.Util.MyAEntityHelper;
import com.inolia_zaicek.myosotis.Util.MyUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = Myosotis.MODID)
public class BuffEvent {
    @SubscribeEvent
    public static void buff(MobEffectEvent.Added event) {
        LivingEntity livingEntity = event.getEntity();
        if (MyUtil.isCurioEquipped(livingEntity, MyItemRegister.Myosotis1.get())) {
            MobEffectInstance effect = event.getEffectInstance();
            MobEffect type = effect.getEffect();
            //正面
            if (type.getCategory().equals(MobEffectCategory.BENEFICIAL) ) {
                MyAEntityHelper.shortenEffect(effect, livingEntity, (int) (effect.getDuration()*MyConfig.myosotis_1_debuff.get()));
            }
            //非正面
            if (!type.getCategory().equals(MobEffectCategory.BENEFICIAL)) {
                float number = (float) (1+ MyConfig.myosotis_1_debuff.get() );
                int finish = (int) (effect.getDuration() * (number-1) );
                MyAEntityHelper.extendEffect(effect, livingEntity, finish);
            }
        }
        if (MyUtil.isCurioEquipped(livingEntity, MyItemRegister.Myosotis2.get())) {
            MobEffectInstance effect = event.getEffectInstance();
            MobEffect type = effect.getEffect();
            //正面
            if (type.getCategory().equals(MobEffectCategory.BENEFICIAL) ) {
                MyAEntityHelper.shortenEffect(effect, livingEntity, (int) (effect.getDuration()*MyConfig.myosotis_2_debuff.get()));
            }
            //非正面
            if (!type.getCategory().equals(MobEffectCategory.BENEFICIAL)) {
                float number = (float) (1+ MyConfig.myosotis_2_debuff.get() );
                int finish = (int) (effect.getDuration() * (number-1) );
                MyAEntityHelper.extendEffect(effect, livingEntity, finish);
            }
        }
    }
}
