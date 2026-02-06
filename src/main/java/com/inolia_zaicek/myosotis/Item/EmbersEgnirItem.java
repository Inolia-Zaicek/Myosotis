package com.inolia_zaicek.myosotis.Item;

import com.inolia_zaicek.myosotis.Myosotis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class EmbersEgnirItem extends SwordItem {
    public static final String embers_egnir_nbt = Myosotis.MODID + ":embers_egnir";
    //护盾值默认为血量*100，计算时/100
    public static final String shield_number = Myosotis.MODID + ":shield";
    public EmbersEgnirItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
    //获取物品id以自动更新文本
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Screen.hasAltDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.myosotis.embers_egnir.alt_text"
            ).withStyle(style -> style.withColor(TextColor.fromRgb(0x7BEBFC))));
        }
        else{
            pTooltipComponents.add(Component.translatable("tooltip.myosotis.embers_egnir.text"
            ).withStyle(style -> style.withColor(TextColor.fromRgb(0x7BEBFC))));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    //物品右键使用
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        CompoundTag compoundTag = pPlayer.getPersistentData();
        int shield = compoundTag.getInt(shield_number);
        if(!pLevel.isClientSide()&&pUsedHand==InteractionHand.MAIN_HAND){
            pPlayer.getCooldowns().addCooldown(this,20*60);//设置冷却时间
            compoundTag.putInt(shield_number, (int) (shield+(pPlayer.getMaxHealth()*0.3F*100) ) );
            pPlayer.setRemainingFireTicks(pPlayer.getRemainingFireTicks()+200);
        }

        if(!pLevel.isClientSide()&&pUsedHand==InteractionHand.OFF_HAND){
            pPlayer.getCooldowns().addCooldown(this,20*60);//设置冷却时间
            compoundTag.putInt(shield_number, (int) (shield+(pPlayer.getMaxHealth()*0.3F*100) ) );
            pPlayer.setRemainingFireTicks(pPlayer.getRemainingFireTicks()+200);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}