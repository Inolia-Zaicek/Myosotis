package com.inolia_zaicek.myosotis.Event;

import com.inolia_zaicek.myosotis.Config.MyConfig;
import com.inolia_zaicek.myosotis.Myosotis;
import com.inolia_zaicek.myosotis.Network.DamageHungerChannel;
import com.inolia_zaicek.myosotis.Network.parket.GetServerManaToClientPacket;
import com.inolia_zaicek.myosotis.Register.MyAttributesRegister;
import com.inolia_zaicek.myosotis.Register.MyItemRegister;
import com.inolia_zaicek.myosotis.Register.MyEffectsRegister;
import com.inolia_zaicek.myosotis.Util.MyUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;
import java.util.Optional;

import static com.inolia_zaicek.myosotis.Event.HurtEvent.shield_number;

@SuppressWarnings({"all", "removal"})
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = Myosotis.MODID)
public class TickEvent {
    public static final String embers_egnir_nbt = Myosotis.MODID + ":embers_egnir";
    public static final String bright_flame_nbt = Myosotis.MODID + ":bright_flame";
    public static final String manaNumber = Myosotis.MODID + ":mana_number";
    public static final String manaReganTime = Myosotis.MODID + ":mana_regan_time";
    public static final String KEY_ENTITY = "entity";

    @SubscribeEvent
    public static void playerTick(net.minecraftforge.event.TickEvent.PlayerTickEvent event) {
        //客户端同步数据【服务端发客户端格式，
        if(event.player instanceof ServerPlayer serverPlayer&& !serverPlayer.level().isClientSide()) {
            float nowMana = serverPlayer.getPersistentData().getFloat(manaNumber);
            DamageHungerChannel.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) serverPlayer), new GetServerManaToClientPacket(nowMana));
            //护盾值同步
            float nowShield = serverPlayer.getPersistentData().getFloat(shield_number);
            DamageHungerChannel.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) serverPlayer), new GetServerManaToClientPacket(nowShield));
        }
    }
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        if (!event.getEntity().isAlive())
            return;
        if(event.getEntity().getHealth()>event.getEntity().getMaxHealth()){
            event.getEntity().setHealth(event.getEntity().getMaxHealth());
        }
        LivingEntity livingEntity = event.getEntity();
        CompoundTag compoundTag = livingEntity.getPersistentData();
        //魔力回复部分
        float maxMana = (float) livingEntity.getAttributeValue((Attribute) MyAttributesRegister.MaxMama.get());
        float manaRegan = (float) livingEntity.getAttributeValue((Attribute) MyAttributesRegister.MamaRegan.get());
        if (!livingEntity.level().isClientSide()) {
            float nowMana = livingEntity.getPersistentData().getFloat(manaNumber);
            //用来保证回蓝是1s间隔
            if (nowMana < maxMana) {
                livingEntity.getPersistentData().putFloat(manaReganTime, livingEntity.getPersistentData().getFloat(manaReganTime) + 1);
            } else {
                livingEntity.getPersistentData().putFloat(manaReganTime, 0);
            }
            if (livingEntity.getPersistentData().getFloat(manaReganTime) == 20) {
                if (!livingEntity.level().isClientSide()) {
                    if (nowMana < maxMana) {
                        MyUtil.addMana(livingEntity, manaRegan);
                        livingEntity.getPersistentData().putFloat(manaReganTime, 0);
                    }
                }
            }
        }

        //焚身
        if(compoundTag.getInt(embers_egnir_nbt)>=100){
            var map = livingEntity.getActiveEffectsMap();
            livingEntity.addEffect(new MobEffectInstance(MyEffectsRegister.SelfImmolation.get(), 100, 0));
            if (!livingEntity.hasEffect(MyEffectsRegister.SelfImmolation.get())) {
                map.put(MyEffectsRegister.SelfImmolation.get(), new MobEffectInstance(MyEffectsRegister.SelfImmolation.get(), 100, 0));
            }
        }
        //耀炎
        if(livingEntity.hasEffect(MyEffectsRegister.BrightFlame.get()) &&compoundTag.getInt(bright_flame_nbt)>0
                &&livingEntity.level().getGameTime() % 20L == 0 && !compoundTag.getCompound(KEY_ENTITY).isEmpty()){
            Optional<Entity> entityOpt = EntityType.create(compoundTag.getCompound(KEY_ENTITY), livingEntity.level());
            var DamageType = MyUtil.hasSource(livingEntity.level(), DamageTypes.MAGIC, entityOpt.get() );
            var mobList = MyUtil.mobList(3, livingEntity);
            for (Mob mobs : mobList) {
                //非随从or非是自己为主的随从
                if (!(mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null && ownableEntity==livingEntity) && mobs!=livingEntity) {
                    mobs.hurt(DamageType, compoundTag.getInt(bright_flame_nbt)/100*0.3F );
                }
            }

        }
        if (!event.getEntity().level().isClientSide && livingEntity instanceof Player player) {
            //lvl1
            if(MyUtil.isCurioEquipped(player, MyItemRegister.Myosotis1.get())&&MyConfig.myosotis_1_fire.get()) {
                //火
                if (player.getRemainingFireTicks() < 200 && player.getRemainingFireTicks() > 0) {
                    player.setRemainingFireTicks(200);
                }
                //替换物品(1--2
                if (player.level().dimension().equals(Level.NETHER)){
                    if (player instanceof ServerPlayer) {
                        player.sendSystemMessage(Component.translatable("message.myosotis.level_up")
                                .withStyle(ChatFormatting.BLUE));
                    }
                    MyUtil.replaceFirstHatredInundate(player, MyItemRegister.Myosotis1.get(), MyItemRegister.Myosotis2.get());
                }
            }
            //level2---3
            if(MyUtil.isCurioEquipped(player, MyItemRegister.Myosotis2.get())) {
                //替换物品
                if (player.level().dimension().equals(Level.END)){
                    if (player instanceof ServerPlayer) {
                        player.sendSystemMessage(Component.translatable("message.myosotis.level_up")
                                .withStyle(ChatFormatting.BLUE));
                    }
                    MyUtil.replaceFirstHatredInundate(player, MyItemRegister.Myosotis2.get(), MyItemRegister.Myosotis3.get());
                }
            }
            //勿忘我
            if ( (MyUtil.isCurioEquipped(player, MyItemRegister.Myosotis1.get())&& MyConfig.myosotis_1_sleep.get())
                    ||(MyUtil.isCurioEquipped(player, MyItemRegister.Myosotis2.get())&& MyConfig.myosotis_2_sleep.get())
                    ||MyUtil.isCurioEquipped(player, MyItemRegister.Myosotis3.get())) {
                if (player.isSleeping()) {
                    //不许睡觉
                    if (player.getSleepTimer() == 5) {
                        if (player instanceof ServerPlayer) {
                            player.sendSystemMessage(Component.translatable("message.myosotis.can_not_sleep")
                                    .withStyle(ChatFormatting.RED));
                        }
                    } else if (player.getSleepTimer() > 90) {
                        player.sleepCounter = 90;
                    }
                }
            }
            if ( (MyUtil.isCurioEquipped(player, MyItemRegister.Myosotis1.get())&& MyConfig.myosotis_1_mob.get())
                    ||(MyUtil.isCurioEquipped(player, MyItemRegister.Myosotis2.get())&& MyConfig.myosotis_2_mob.get())
                    ||MyUtil.isCurioEquipped(player, MyItemRegister.Myosotis3.get())) {
// 获取玩家周围半径为16的范围内所有的LivingEntity实体，存入列表
                //是玩家，且为创造or观测的情况下，不拉仇恨
                if( !(livingEntity instanceof Player player1&&!player1.isCreative()&&!player1.isSpectator()  ) ) {
                    List<LivingEntity> genericMobs = player.level().getEntitiesOfClass(LivingEntity.class, MyUtil.getBoundingBoxAroundEntity(player, 16));
// 遍历所有检测到的实体，逐个判断处理
                    for (LivingEntity checkedEntity : genericMobs) {
                        // 计算玩家对当前实体的可见百分比（0-1）
                        double visibility = player.getVisibilityPercent(checkedEntity);
                        // 根据可见性调整“激怒距离”，取最大值：16*可见度 和 12
                        double angerDistance = Math.max(16 * visibility, 12);
                        // 如果实体距离玩家的平方距离大于激怒距离的平方，则跳过（不考虑激怒）
                        if (checkedEntity.distanceToSqr(player.getX(), player.getY(), player.getZ()) > angerDistance * angerDistance) {
                            continue;
                        }
                        // 如果实体是Piglin类型
                        if (checkedEntity instanceof Piglin) {
                            Piglin piglin = (Piglin) checkedEntity;
                            // 如果Piglin没有目标或目标已死亡
                            if (piglin.getTarget() == null || !piglin.getTarget().isAlive()) {
                                // 且玩家能看到该Piglin或距离在16以内，则触发Piglin被玩家攻击的反应
                                if (player.hasLineOfSight(checkedEntity) || player.distanceTo(checkedEntity) <= 16) {
                                    PiglinAi.wasHurtBy(piglin, player);
                                } else {
                                    // 否则跳过
                                    continue;
                                }
                            }
                            // 如果实体是中立生物（NeutralMob）
                        } else if (checkedEntity instanceof NeutralMob) {
                            NeutralMob neutral = (NeutralMob) checkedEntity;
                            // 如果该实体类型在“黑名单”中，则跳过
                            //neutralAngerBlacklist.contains(ForgeRegistries.ENTITY_TYPES.getKey(checkedEntity.getType()))
                            if (1 < 0) {
                                continue;
                            }
                            // 如果是可驯服动物且已驯服，则跳过（不激怒）
                            if (neutral instanceof TamableAnimal tamable && tamable.isTame()) {
                                continue;
                                // 如果玩家持有“动物指南书”且实体是可驯养动物，则跳过
                            } else if (1 < 0) {
                                continue;
                                // 如果是由玩家创建的铁巨人，则跳过（不激怒）
                            } else if (neutral instanceof IronGolem golem && golem.isPlayerCreated()) {
                                continue;
                                // 如果是蜜蜂
                            } else if (neutral instanceof Bee) {
                                // 如果启用“Save the Bees”或玩家持有“动物指南书”，则跳过
                                if (1 < 0) {
                                    continue;
                                }
                            }

                            // 如果该中立实体没有目标或目标已死
                            if (neutral.getTarget() == null || !neutral.getTarget().isAlive()) {
                                // 且玩家能看到该实体或距离在12以内，将玩家设为其目标
                                if (player.hasLineOfSight(checkedEntity) || player.distanceTo(checkedEntity) <= 12) {
                                    neutral.setTarget(player);
                                } else {
                                    // 否则跳过
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}