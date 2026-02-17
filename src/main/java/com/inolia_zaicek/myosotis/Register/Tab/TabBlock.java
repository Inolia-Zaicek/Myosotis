package com.inolia_zaicek.myosotis.Register.Tab;

import com.inolia_zaicek.myosotis.Register.BlockRegister;
import com.inolia_zaicek.myosotis.Register.MyItemRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.inolia_zaicek.myosotis.Myosotis.MODID;


public class TabBlock {
    public static final DeferredRegister<CreativeModeTab> creative_mode_tab= DeferredRegister.create(Registries.CREATIVE_MODE_TAB,MODID);
    public static final String MoreModTetraTab="item_group.myosotis.block";
    public static final Supplier<CreativeModeTab> materials=creative_mode_tab.register("item4",()-> CreativeModeTab.builder()
            //槽位位置
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            //物品栏名称
            .title(Component.translatable(MoreModTetraTab))
            //图标
            .icon(BlockRegister.MithrilOre.get().asItem()::getDefaultInstance)
            .displayItems((itemDisplayParameters, output) -> {
                // 遍历 CommonItem 列表中的物品
                for(RegistryObject<Item> curios: MyItemRegister.BlockItem){
                    if(curios.isPresent()){
                        output.accept(curios.get());
                    }
                }
                //这个物品栏当中包含的物品
            })
            .build()
    );
    public static void register(IEventBus bus){
        creative_mode_tab.register(bus);
    }

}
