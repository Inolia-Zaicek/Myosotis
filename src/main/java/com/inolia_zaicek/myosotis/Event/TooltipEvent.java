package com.inolia_zaicek.myosotis.Event;

import com.inolia_zaicek.myosotis.Myosotis;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Myosotis.MODID)
public class TooltipEvent {
    @SubscribeEvent
    public static void tooltip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        // 检测物品为 patchouli:guide_book 且 NBT 包含指定书籍标识
        if (itemStack.getItem() == Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("patchouli", "guide_book")))
                && itemStack.getTag() != null
                && itemStack.hasTag()
                && itemStack.getTag().contains("patchouli:book")
                && "myosotis:fragment_of_memory_fleeting".equals(itemStack.getTag().getString("patchouli:book"))) {
            event.getToolTip().add(Component.translatable("tooltip.myosotis.fragment_of_memory_fleeting.text").withStyle(ChatFormatting.WHITE));
            event.getToolTip().add(Component.translatable("tooltip.myosotis.fragment_of_memory_fleeting.text1").withStyle(ChatFormatting.WHITE));
            event.getToolTip().add(Component.translatable("tooltip.myosotis.fragment_of_memory_fleeting.text2").withStyle(ChatFormatting.WHITE));
        }
    }
}
