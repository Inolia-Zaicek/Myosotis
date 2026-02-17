package com.inolia_zaicek.myosotis.ModelProvider;

import com.inolia_zaicek.myosotis.Register.MyItemRegister;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.List;
import java.util.function.Consumer;

public class ZeroingModRecipesGen extends RecipeProvider {

    public ZeroingModRecipesGen(PackOutput pOutput) {
        super(pOutput);
    }
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        //添加熔炉的合成表
        oreSmelting(pWriter,
                List.of(MyItemRegister.MithrilShard.get()),//原物品
                RecipeCategory.MISC,//合成表分类
                MyItemRegister.MithrilIngot.get(),//获得的物品
                40f,//获得经验
                200,//燃烧时间
                "zeroing_story");//属于哪个mod
        //高炉
        oreBlasting(pWriter,
                List.of(MyItemRegister.MithrilShard.get()),//原物品
                RecipeCategory.MISC,//合成表分类
                MyItemRegister.MithrilIngot.get(),//获得的物品
                40f,//获得经验
                100,//燃烧时间
                "zeroing_story");//属于哪个mod
    }
}
