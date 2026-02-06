package com.inolia_zaicek.myosotis.Event;

import com.inolia_zaicek.myosotis.Myosotis;
import com.inolia_zaicek.myosotis.Register.ItemRegister;
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
        if (MyUtil.isCurioEquipped(livingEntity, ItemRegister.Myosotis1.get())) {
            MobEffectInstance effect = event.getEffectInstance();
            MobEffect type = effect.getEffect();
            //正面
            if (type.getCategory().equals(MobEffectCategory.BENEFICIAL) ) {
                MyAEntityHelper.shortenEffect(effect, livingEntity, effect.getDuration() / 2);
            }
            //非正面
            if (!type.getCategory().equals(MobEffectCategory.BENEFICIAL)) {
                float number = (float) (2.0F * 1);
                int finish = (int) (effect.getDuration() * (number-1) );
                MyAEntityHelper.extendEffect(effect, livingEntity, finish);
            }
        }
        if (MyUtil.isCurioEquipped(livingEntity, ItemRegister.Myosotis2.get())) {
            MobEffectInstance effect = event.getEffectInstance();
            MobEffect type = effect.getEffect();
            //正面
            if (type.getCategory().equals(MobEffectCategory.BENEFICIAL) ) {
                float number = (float) ((float) 1 / (0.5F*1));
                int finish = (int) (effect.getDuration() / (1-number) );
                MyAEntityHelper.shortenEffect(effect, livingEntity, finish);
            }
            //非正面
            if (!type.getCategory().equals(MobEffectCategory.BENEFICIAL)) {
                float number = (float) (1.5F * 1);
                int finish = (int) (effect.getDuration() * (number-1) );
                MyAEntityHelper.extendEffect(effect, livingEntity, finish);
            }
        }
    }
}
