package com.inolia_zaicek.myosotis.Event;

import com.inolia_zaicek.myosotis.Myosotis;
import com.inolia_zaicek.myosotis.Register.MyItemRegister;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.patchouli.api.PatchouliAPI;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = Myosotis.MODID)
public class LoggedAndCloneEvent {
    //开局or返还
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity().level().isClientSide) return;
        var player = event.getEntity();
        var data = player.getPersistentData();
        var key1 = "myosotis_1_give";  //名字随意
        if (!data.getBoolean(key1)) {
            player.getInventory().add(MyItemRegister.Myosotis1.get().getDefaultInstance());
            data.putBoolean(key1, true);
        }
        var keyBook = "myosotis_book_give";  //名字随意
        if (!data.getBoolean(keyBook)) {
            //player.getInventory().add(ItemRegister.FragmentOfMemoryFleeting.get().getDefaultInstance());
            //ResourceLocation第一个参数传你的modid，第二个传你的手册名字
            ItemStack book = PatchouliAPI.get().getBookStack(new ResourceLocation(Myosotis.MODID, "fragment_of_memory_fleeting"));
            player.getInventory().add(book);
            data.putBoolean(keyBook, true);
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
                "myosotis_1_give",
                "myosotis_book_give",
                "mana_number",
                "shield",
                "myosotis:mana_number",
                "myosotis:shield"
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
