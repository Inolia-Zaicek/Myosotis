package com.inolia_zaicek.myosotis.Event;

import com.inolia_zaicek.myosotis.Myosotis;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = Myosotis.MODID)
public class RightEntityEvent {
    @SubscribeEvent
    public static void event(PlayerInteractEvent.EntityInteractSpecific event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        //末地水晶
        if (target instanceof EndCrystal) {
        }
    }
}