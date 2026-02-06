package com.inolia_zaicek.myosotis.ModelProvider;

import com.inolia_zaicek.myosotis.Myosotis;
import com.inolia_zaicek.myosotis.Register.BlockRegister;
import com.inolia_zaicek.myosotis.Register.ItemRegister;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.stream.Collectors;

public class ZeroingModLootTableGen extends VanillaBlockLoot {
    @Override
    protected void generate() {
        //这个方块破坏后掉落自己
        //dropSelf(ZeroingStoryBlock.AZURE_STEEL_BLOCK.get());
        //这个方块破坏后掉落对应的矿石（自己写好掉落物）
        add(BlockRegister.MithrilOre.get(), this::createMithrilOre);
        add(BlockRegister.DeepslateMithrilOre.get(), this::createMithrilOre);
        add(BlockRegister.MagicOre.get(), this::createMagicOre);
        add(BlockRegister.DeepslateMagicOre.get(), this::createMagicOre);
    }

    // add方法的第二个参数的返回值，大部分照原版的写就行，只需要修改注释地方的参数即可
    // 这个方法依然可以使用 createSilkTouchDispatchTable 和 applyExplosionDecay，
    // 因为它处理的是单一的 LootItem.lootTableItem。
    protected LootTable.Builder createMithrilOre(Block pBlock) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(ItemRegister.MithrilShard.get())//设置我们要破坏方块后掉落的物品
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))//设置物品的掉落数量范围
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));//受时运效果影响
    }

    protected LootTable.Builder createMagicOre(Block pBlock) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(ItemRegister.MagicDust.get())//设置我们要破坏方块后掉落的物品
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))//设置物品的掉落数量范围
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));//受时运效果影响
    }

    //照这样写就行
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(e -> e.getKey().location().getNamespace().equals(Myosotis.MODID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
