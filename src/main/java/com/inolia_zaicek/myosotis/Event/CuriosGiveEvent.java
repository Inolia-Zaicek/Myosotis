package com.inolia_zaicek.myosotis.Event;

import com.inolia_zaicek.myosotis.Myosotis;
import com.inolia_zaicek.myosotis.Register.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = Myosotis.MODID)
public class CuriosGiveEvent {
    //开局or返还
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity().level().isClientSide) return;
        var player = event.getEntity();
        var data = player.getPersistentData();
        var key1 = "myosotis_1_give";  //名字随意
        if (!data.getBoolean(key1)) {
            player.getInventory().add(ItemRegister.Myosotis1.get().getDefaultInstance());
            data.putBoolean(key1, true);
        }
    }
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.getEntity().level().isClientSide) return;

        Player original = event.getOriginal();
        Player newPlayer = event.getEntity();

        CompoundTag originalData = original.getPersistentData();
        CompoundTag newData = newPlayer.getPersistentData();

        String[] keys = {
                "myosotis_1_give"
        };

        for (String key : keys) {
            if (originalData.contains(key)) {
                // 根据存储类型选择对应的get方法
                Tag tag = originalData.get(key);
                if (tag instanceof ByteTag) {
                    boolean value = originalData.getBoolean(key);
                    newData.putBoolean(key, value);
                } else if (tag instanceof IntTag) {
                    int value = originalData.getInt(key);
                    newData.putInt(key, value);
                } else if (tag instanceof StringTag) {
                    String value = originalData.getString(key);
                    newData.putString(key, value);
                }
                // 可以根据需要添加其他类型
            }
        }
    }
}
