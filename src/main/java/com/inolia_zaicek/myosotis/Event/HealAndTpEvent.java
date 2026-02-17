package com.inolia_zaicek.myosotis.Event;

import com.inolia_zaicek.myosotis.Config.MyConfig;
import com.inolia_zaicek.myosotis.Myosotis;
import com.inolia_zaicek.myosotis.Register.MyEffectsRegister;
import com.inolia_zaicek.myosotis.Register.MyItemRegister;
import com.inolia_zaicek.myosotis.Util.MyUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = Myosotis.MODID)
public class HealAndTpEvent {
    @SubscribeEvent
    public static void hurt(LivingHealEvent event) {
        LivingEntity livingEntity = event.getEntity();
        double number = 1;
        if (MyUtil.isCurioEquipped(livingEntity, MyItemRegister.Myosotis1.get())) {
            number *=1 - MyConfig.myosotis_1_heal.get();
        }
        if (MyUtil.isCurioEquipped(livingEntity, MyItemRegister.Myosotis2.get())) {
            number *=1 - MyConfig.myosotis_2_heal.get();
        }
        if (MyUtil.isCurioEquipped(livingEntity, MyItemRegister.Myosotis2.get())) {
            number *=0.8F;
        }
        if(MyUtil.hasItemInEitherHand(livingEntity, MyItemRegister.SablePlume.get())){
            number=0;
        }
        if (livingEntity.hasEffect(MyEffectsRegister.GrievousWounds.get())) {
            int buffLevel = livingEntity.getEffect(MyEffectsRegister.GrievousWounds.get()).getAmplifier();
            number *= 1 - 0.1F*buffLevel;
        }
        if(number>0) {
            event.setAmount((float) (event.getAmount() * number));
        }else{
            event.setAmount(0);
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void event(EntityTeleportEvent event) {
        //有抑影，阻止传送
        if (event.getEntity() instanceof LivingEntity livingEntity&& livingEntity.hasEffect(MyEffectsRegister.EnderFerence.get())) {
            event.setCanceled(true);
        }
    }
}
