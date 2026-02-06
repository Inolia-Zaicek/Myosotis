package com.inolia_zaicek.myosotis.Event;

import com.inolia_zaicek.myosotis.Myosotis;
import com.inolia_zaicek.myosotis.Register.ItemRegister;
import com.inolia_zaicek.myosotis.Util.MyUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = Myosotis.MODID)
public class HealEvent {
    @SubscribeEvent
    public static void hurt(LivingHealEvent event) {
        LivingEntity livingEntity = event.getEntity();
        float number = 1;
        if (MyUtil.isCurioEquipped(livingEntity, ItemRegister.Myosotis1.get())) {
            number *=0.3F;
        }
        if (MyUtil.isCurioEquipped(livingEntity, ItemRegister.Myosotis2.get())) {
            number *=0.6F;
        }
        if (MyUtil.isCurioEquipped(livingEntity, ItemRegister.Myosotis2.get())) {
            number *=0.8F;
        }
        if(MyUtil.hasItemInEitherHand(livingEntity,ItemRegister.SablePlume.get())){
            number=0;
        }
        event.setAmount(event.getAmount()*number);
    }
}
