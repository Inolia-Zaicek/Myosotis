package com.inolia_zaicek.myosotis.Item;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.myosotis.Config.MyConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"all", "removal"})
public class Myosotis1Item extends Item implements ICurioItem {
    public Myosotis1Item() {
        super((new Properties()).stacksTo(1).fireResistant());
    }
    @NotNull
    public ICurio.@NotNull DropRule getDropRule(SlotContext slotContext, DamageSource source, int lootingLevel, boolean recentlyHit, ItemStack stack) {
        return ICurio.DropRule.ALWAYS_KEEP;
    }
    //获取物品id以自动更新文本
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.myosotis.myosotis_1.shift_text"
                    ,(float)(MyConfig.myosotis_1_night.get()*100),(float)(MyConfig.myosotis_1_debuff.get()*100)
                    ,(float)(MyConfig.myosotis_1_heal.get()*100),(float)(MyConfig.myosotis_1_hp.get()*100)
                    ,(int)(MyConfig.myosotis_1_max_buff.get()*1),(float)(MyConfig.myosotis_1_buff.get()*100)
                    ,(float)(MyConfig.myosotis_1_armor.get()*100)
                    ,(float)(MyConfig.myosotis_1_source_damage.get()*100),(float)(MyConfig.myosotis_1_hp_damage.get()*100)
            ).withStyle(style -> style.withColor(ChatFormatting.YELLOW)));
        }else if(Screen.hasAltDown()) {
                pTooltipComponents.add(Component.translatable("tooltip.myosotis.myosotis_1.alt_text"
                ).withStyle(style -> style.withColor(TextColor.fromRgb(0x7BEBFC))));
            }
        else{
            pTooltipComponents.add(Component.translatable("tooltip.myosotis.myosotis_1.text"
            ).withStyle(style -> style.withColor(TextColor.fromRgb(0x7BEBFC))));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, this.getTooltipItemName(), -MyConfig.myosotis_1_hp.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, this.getTooltipItemName(), -MyConfig.myosotis_1_armor.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, this.getTooltipItemName(), -MyConfig.myosotis_1_armor_toughness.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        CuriosApi.getCuriosHelper().addSlotModifier(atts, "world_will", uuid, 3, AttributeModifier.Operation.ADDITION);
        return atts;
    }
    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        return Collections.emptyList();
    }
    public boolean canUnequip(SlotContext context, ItemStack stack) {
        LivingEntity var4 = context.entity();
        if (var4 instanceof Player player) {
            if (player.isCreative()) {
                return ICurioItem.super.canUnequip(context, stack);
            }
        }

        return false;
    }
}
