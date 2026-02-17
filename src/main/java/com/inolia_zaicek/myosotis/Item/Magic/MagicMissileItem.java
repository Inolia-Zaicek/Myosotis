package com.inolia_zaicek.myosotis.Item.Magic;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.myosotis.Item.CasterWeapon;
import com.inolia_zaicek.myosotis.Register.DamageRegister;
import com.inolia_zaicek.myosotis.Util.MyUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;


public class MagicMissileItem extends CasterWeapon {
    public MagicMissileItem(Properties properties) {
        super(properties);
    }
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
        attributeBuilder.putAll(super.getDefaultAttributeModifiers(slot));
        attributeBuilder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("28A8831E-6FB0-03D0-95A7-28D2D54297B1"),
                getTooltipItemName(), 1, AttributeModifier.Operation.ADDITION));
        //限定主手
        return slot == EquipmentSlot.MAINHAND ? attributeBuilder.build() : super.getDefaultAttributeModifiers(slot);
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();{
            pTooltipComponents.add(Component.translatable("tooltip." + "wizard_terra_cuiros" + "." + itemName + ".text",
                    1 ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        }super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (MyUtil.getNearestNonFollowerOnPath(livingEntity, 10) != null) {
            LivingEntity mob = MyUtil.getNearestNonFollowerOnPath(livingEntity, 10);
            mob.hurt(MyUtil.hasSource(livingEntity.level(), DamageRegister.MagicDamage, livingEntity), 5);
            MyUtil.reduceMana(livingEntity, 5);
            if (livingEntity instanceof Player player) {
                livingEntity.swing(player.getUsedItemHand());
                livingEntity.startUsingItem(player.getUsedItemHand());
                MyUtil.spawnLinkParticles(level,livingEntity,mob,ParticleTypes.SCULK_CHARGE_POP);
                //player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
            }
        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        ItemStack item = player.getItemInHand(useHand);
        InteractionHand otherhand = useHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack otheritem = player.getItemInHand(otherhand);
        //副手盾且主手冷却+潜行or魔力不足
        if (
                (otheritem.canPerformAction(net.minecraftforge.common.ToolActions.SHIELD_BLOCK) && !player.getCooldowns().isOnCooldown(otheritem.getItem())
                && player.isShiftKeyDown())
                || !MyUtil.hasMana(player,5)
        ) {
            return InteractionResultHolder.fail(item);
        }else{
            player.startUsingItem(useHand);
            return InteractionResultHolder.consume(item);
        }
    }

    public int getUseDuration(ItemStack stack) {
        return 20;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}
