package com.inolia_zaicek.myosotis.Register;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.inolia_zaicek.myosotis.Myosotis.MODID;

public class BlockRegister {
    public static final DeferredRegister<Block> ZeroingBLOCK=DeferredRegister.create(ForgeRegistries.BLOCKS,MODID);
    //给创造物品栏注册的 (这个 CommonBlock 列表是用来存储 Block 对象的，而不是 BlockItem 对象)
    public static List<RegistryObject<Block>> CommonBlock=new ArrayList<>(List.of());
    //把方块对应的物品注册的Item注册表上
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        RegistryObject<Item> blockItem = MyItemRegister.ZeroingITEM.register(name,()->new BlockItem(block.get(),new Item.Properties()));
        // **** 新增的代码行 ****
        //（但是生成模型的时候得把下面那行代码去了）将注册的 BlockItem 添加到 ZeroingStoryItem 的 CommonItem 列表中，这样它就会被 CreativeModeTab 遍历到
        MyItemRegister.BlockItem.add(blockItem);
        // **** 结束新增的代码行 ****
        return  blockItem;
    }
    // 这个方法不直接用于向CommonBlock添加方块，而是通过下面的 registryBlock 方法来添加
    public static RegistryObject<Block> registryBlock(DeferredRegister<Block> register,String name, Supplier<? extends Block> sup){
        RegistryObject<Block> object = register.register(name,sup);
        CommonBlock.add(object); // 这里将 Block 对象添加到 CommonBlock 列表
        return object;
    }
    //第一个参数为方块名字，第二个参数为Block对象
    //添加矿石
    public static final RegistryObject<Block> MithrilOre = registryBlock("mithril_ore",
            ()->new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)//例子效果
                    .instrument(NoteBlockInstrument.BASEDRUM)//挖掘音效
                    .requiresCorrectToolForDrops()//需要使用工具挖掘【需要手动给方块加tag】
                    .strength(5.0F, 6.0F),
                    UniformInt.of(15, 20)));
    public static final RegistryObject<Block> DeepslateMithrilOre = registryBlock("deepslate_mithril_ore",
            ()->new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)//例子效果
                    .instrument(NoteBlockInstrument.BASEDRUM)//挖掘音效
                    .requiresCorrectToolForDrops()//需要使用工具挖掘【需要手动给方块加tag】
                    .strength(5.0F, 6.0F),
                    UniformInt.of(15, 20)));


    public static final RegistryObject<Block> MagicOre = registryBlock("magic_ore",
            ()->new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)//例子效果
                    .instrument(NoteBlockInstrument.BASEDRUM)//挖掘音效
                    .requiresCorrectToolForDrops()//需要使用工具挖掘【需要手动给方块加tag】
                    .strength(3.0F, 3.0F),
                    UniformInt.of(7, 10)));

    public static final RegistryObject<Block> DeepslateMagicOre = registryBlock("deepslate_magic_ore",
            ()->new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)//例子效果
                    .instrument(NoteBlockInstrument.BASEDRUM)//挖掘音效
                    .requiresCorrectToolForDrops()//需要使用工具挖掘【需要手动给方块加tag】
                    .strength(4.5F, 3.0F),
                    UniformInt.of(7, 10)));
    //Items.Iron
    //自定义注册方法方法，因为要同时注册方块和方块对应的物品，就写在一起了
    private static <T extends Block> RegistryObject<T> registryBlock(String name, Supplier<T>block){
        RegistryObject<T>toReturn= ZeroingBLOCK.register(name,block);//注册方块
        registerBlockItem(name,toReturn);//注册对应物品
        return toReturn;
    }
    public static void register(IEventBus bus){
        ZeroingBLOCK.register(bus);
    }
}
