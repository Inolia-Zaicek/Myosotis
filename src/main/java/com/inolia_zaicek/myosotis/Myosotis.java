package com.inolia_zaicek.myosotis;

import com.inolia_zaicek.myosotis.Event.*;
import com.inolia_zaicek.myosotis.ModelProvider.ZeroingModLootTableGen;
import com.inolia_zaicek.myosotis.ModelProvider.ZeroingModRecipesGen;
import com.inolia_zaicek.myosotis.Register.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.*;


@Mod.EventBusSubscriber(modid = Myosotis.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod(Myosotis.MODID)
public class Myosotis {

    public static final String MODID = "myosotis";
    public Myosotis() {
        init();
    }

    public void init(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        FeatureRegistry.register(bus);
        // 注册 Item、Tab、Entity 类型
        Tab.register(bus);
        TabNostalgic.register(bus);
        TabCraft.register(bus);
        TabBlock.register(bus);
        ItemRegister.register(bus);
        BlockRegister.register(bus);
        MyEffectsRegister.INOEFFECT.register(bus);
        // 注册 CommonSetup 事件
        bus.addListener(this::commonSetup);
        // !!! 注册 ClientSetup 事件 !!!
        bus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(BuffEvent.class);
        MinecraftForge.EVENT_BUS.register(HealEvent.class);
        MinecraftForge.EVENT_BUS.register(HurtEvent.class);
        MinecraftForge.EVENT_BUS.register(TickEvent.class);
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
        });
    }

    // 客户端设置事件，用于注册渲染器和GUI屏幕
    // 加上 @SubscribeEvent，使其成为 Mod 事件总线上的监听器
    @SubscribeEvent
    public void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }

    //注册掉落物
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        if (event.includeServer()) {
            generator.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(),
                    List.of(new LootTableProvider.SubProviderEntry(ZeroingModLootTableGen::new, LootContextParamSets.BLOCK))));
            //generator.addProvider(event.includeServer(), new ZeroingModRecipesGen(output));
        }
    }

    public static ResourceLocation prefix(String name){
        return new ResourceLocation(MODID,name.toLowerCase(Locale.ROOT));
    }
    public static ResourceLocation getResource(String id) {
        return new ResourceLocation("myosotis", id);
    }
}