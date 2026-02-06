package com.inolia_zaicek.myosotis.Register.TooltipItem;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class TooltipItem3 extends Item {
    public TooltipItem3(Properties properties) {
        super(properties);
    }
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName(); // 获取物品 ID
        {
            pTooltipComponents.add(Component.translatable("tooltip." + "myosotis" + "." + itemName + ".text")
                    .withStyle(style -> style.withColor(TextColor.fromRgb(0xA0A0A0))));
        }
        {
            pTooltipComponents.add(Component.translatable("tooltip." + "myosotis" + "." + itemName + ".text1")
                    .withStyle(style -> style.withColor(TextColor.fromRgb(0xA0A0A0))));
        }
        {
            pTooltipComponents.add(Component.translatable("tooltip." + "myosotis" + "." + itemName + ".text2")
                    .withStyle(style -> style.withColor(TextColor.fromRgb(0xA0A0A0))));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}