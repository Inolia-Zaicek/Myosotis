package com.inolia_zaicek.myosotis.ModelProvider;

import com.inolia_zaicek.myosotis.Myosotis;
import com.inolia_zaicek.myosotis.Register.BlockRegister;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ZeroingModBlockModelGen extends BlockStateProvider {

    public ZeroingModBlockModelGen(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Myosotis.MODID, existingFileHelper);
    }
    //simpleBlockWithItem方法可以把方块和对应的物品一起生成json文件，
    // cubeAll方法是设置该方块的六面都使用一个贴图，如果每个面不同贴图的方块以后再介绍。
    // 直接在重写的simpleBlockWithItem中调用registerBlockModelAndItem方法传入对应的方块即可。最后把该类添加到数据生成器中即可。
    //把自己需要生成模型的方块放这里
    @Override
    protected void registerStatesAndModels() {
        this.registerBlockModelAndItem(BlockRegister.MithrilOre.get());
        this.registerBlockModelAndItem(BlockRegister.DeepslateMithrilOre.get());
        this.registerBlockModelAndItem(BlockRegister.MagicOre.get());
        this.registerBlockModelAndItem(BlockRegister.DeepslateMagicOre.get());
        //遍历方块，自动注册
        //for(RegistryObject<Block> blocksDeferredRegister: ZeroingStoryBlock.CommonBlock){
        //    if(blocksDeferredRegister.isPresent()){
        //        this.registerBlockModelAndItem(blocksDeferredRegister.get());
        //    }
        //}
    }

    //在registerStatesAndModels方法里调用该函数直接添加对应的方块即可
    public void registerBlockModelAndItem(Block block) {
        this.simpleBlockWithItem(block, this.cubeAll(block));
    }
}