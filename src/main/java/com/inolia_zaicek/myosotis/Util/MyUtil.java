package com.inolia_zaicek.myosotis.Util;

import com.inolia_zaicek.myosotis.Register.MyAttributesRegister;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.inolia_zaicek.myosotis.Event.HurtEvent.shield_number;
import static com.inolia_zaicek.myosotis.Event.TickEvent.manaNumber;

@SuppressWarnings({"all", "removal"})
public class MyUtil {
    public static MyUtil INSTANCE;
    public static MyUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MyUtil();
        }
        return INSTANCE;
    }
    //获取周围敌人列表
    public static List<Mob> mobList(double range, LivingEntity entity){
        double x =entity.getX();
        double y =entity.getY();
        double z =entity.getZ();
        return entity.getCommandSenderWorld().getEntitiesOfClass(Mob.class,new AABB(x+range,y+range,z+range,x-range,y-range,z-range));
    }
    //获取周围玩家列表
    public static List<Player> PlayerList(double range, LivingEntity entity){
        double x =entity.getX();
        double y =entity.getY();
        double z =entity.getZ();
        return entity.getCommandSenderWorld().getEntitiesOfClass(Player.class,new AABB(x+range,y+range,z+range,x-range,y-range,z-range));
    }
    //检测饰品是否装备
    public static boolean isCurioEquipped(LivingEntity entity, Item itemStackSupplier) {
        Optional<SlotResult> slotResult = CuriosApi.getCuriosHelper().findFirstCurio(entity,itemStackSupplier);
        return slotResult.isPresent();
    }
    //生成source
    public static DamageSource source(Level level, ResourceKey<DamageType> type, @Nullable Entity direct, @Nullable Entity causing){
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type), direct,causing);
    }
    //有源
    public static DamageSource hasSource(Level level, ResourceKey<DamageType> type, @Nullable Entity entity){
        return source(level,type,entity, entity);
    }
    public static AABB getBoundingBoxAroundEntity(final Entity entity, final double radius) {
        return new AABB(entity.getX() - radius, entity.getY() - radius, entity.getZ() - radius, entity.getX() + radius, entity.getY() + radius, entity.getZ() + radius);
    }
    //替换饰品
    public static void replaceFirstHatredInundate(Player player, Item oldStack, Item newStack) {
        LazyOptional<ICuriosItemHandler> curiosOpt = CuriosApi.getCuriosInventory(player);
        curiosOpt.ifPresent(curios -> {
            Map<String, ICurioStacksHandler> curiosMap = curios.getCurios();
            for (Map.Entry<String, ICurioStacksHandler> entry : curiosMap.entrySet()) {
                ICurioStacksHandler handler = entry.getValue();
                IDynamicStackHandler stacksHandler = handler.getStacks();
                int slotCount = stacksHandler.getSlots();
                for (int i = 0; i < slotCount; i++) {
                    ItemStack stack = stacksHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && stack.getItem() == oldStack) {
                        // 替换该槽的Item
                        stacksHandler.setStackInSlot(i, newStack.getDefaultInstance());
                        return; // 只替换第一个符合条件的饰品
                    }
                }
            }
        });
    }
    public static boolean isMeleeAttack(DamageSource source) {
        return !source.isIndirect() && (source.is(DamageTypes.MOB_ATTACK) || source.is(DamageTypes.PLAYER_ATTACK) || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO));
    }
    public static boolean hasItemInEitherHand(LivingEntity livingEntity, Item item) {
        if (livingEntity == null || item == null) {
            return false; // 避免空指针异常
        }

        ItemStack mainHandStack = livingEntity.getMainHandItem();
        ItemStack offHandStack = livingEntity.getOffhandItem();

        // 检查主手
        if (!mainHandStack.isEmpty() && mainHandStack.getItem() == item) {
            return true;
        }

        // 检查副手
        if (!offHandStack.isEmpty() && offHandStack.getItem() == item) {
            return true;
        }

        return false; // 任意一只手都没有找到指定的 Item
    }


    public static boolean hasCuriosImplements(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); ++i) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                        hasItem.set(true);
                        return;
                    }
                }
            }
        });
        return hasItem.get();
    }
    public static boolean hasHandImplements(LivingEntity living, Class<?> targetClass) {
        if (!living.getMainHandItem().isEmpty() && targetClass.isAssignableFrom(living.getMainHandItem().getItem().getClass())){
            return true;
        }else if (!living.getOffhandItem().isEmpty() && targetClass.isAssignableFrom(living.getOffhandItem().getItem().getClass())){
            return true;
        }else {
            return false;
        }
    }
    //增加mana
    public static void addMana(LivingEntity livingEntity, float reganMana) {
        //限定服务端
        if(!livingEntity.level().isClientSide()) {
            float maxMana = (float) livingEntity.getAttributeValue((Attribute) MyAttributesRegister.MaxMama.get());
            float nowMana = MyUtil.getMana(livingEntity);
            livingEntity.getPersistentData().putFloat(manaNumber, Math.min(nowMana + reganMana, maxMana));
        }
        //发包版本
        /*
        if(livingEntity instanceof ServerPlayer player) {
            SetManaPacket pkt = new SetManaPacket(reganMana);
            DamageHungerChannel.INSTANCE.sendToServer(pkt);
        }else{
            float maxMana = (float) livingEntity.getAttributeValue((Attribute) AttributesRegister.MaxMama.get());
            float nowMana = MyUtil.getMana(livingEntity);
            livingEntity.getPersistentData().putFloat(manaNumber, Math.min(nowMana + reganMana, maxMana));
        }
         */
    }
    //减少mana
    public static void reduceMana(LivingEntity livingEntity, float reduceMana) {
        if(!livingEntity.level().isClientSide()) {
        float maxMana = (float) livingEntity.getAttributeValue((Attribute) MyAttributesRegister.MaxMama.get());
        float nowMana = MyUtil.getMana(livingEntity);
        livingEntity.getPersistentData().putFloat(manaNumber,Math.max(nowMana-reduceMana,0));
        }
    }
    public static void setMana(LivingEntity livingEntity, float setMana) {
        if(!livingEntity.level().isClientSide()) {
            float maxMana = (float) livingEntity.getAttributeValue((Attribute) MyAttributesRegister.MaxMama.get());
            livingEntity.getPersistentData().putFloat(manaNumber,Math.min(maxMana,Math.max(setMana,0)) );
        }
    }
    //获取当前mana
    public static float getMana(LivingEntity livingEntity) {
        return livingEntity.getPersistentData().getFloat(manaNumber);
    }
    //有无足够mana
    public static boolean hasMana(LivingEntity livingEntity,float mana) {
        if(MyUtil.getMana(livingEntity)>mana){
            return true;
        }else {
            return false;
        }
    }
    //增加Shield
    public static void addShield(LivingEntity livingEntity, float reganShield) {
        if(!livingEntity.level().isClientSide()) {
            float maxShield = (float) (livingEntity.getAttributeValue((Attribute) MyAttributesRegister.MaxShield.get())
                    * livingEntity.getAttributeValue((Attribute) Attributes.MAX_HEALTH));
            float nowShield = MyUtil.getShield(livingEntity);
            livingEntity.getPersistentData().putFloat(shield_number, Math.min(nowShield + reganShield, maxShield));
        }
    }
    //减少Shield
    public static void reduceShield(LivingEntity livingEntity, float reduceShield) {
        if (!livingEntity.level().isClientSide()) {
            float maxShield = (float) (livingEntity.getAttributeValue((Attribute) MyAttributesRegister.MaxShield.get())
                    * livingEntity.getAttributeValue((Attribute) Attributes.MAX_HEALTH));
            float nowShield = MyUtil.getShield(livingEntity);
            livingEntity.getPersistentData().putFloat(shield_number, Math.max(nowShield - reduceShield, 0));
        }
    }
    public static void setShield(LivingEntity livingEntity, float finalShield) {
        if (!livingEntity.level().isClientSide()) {
            float maxShield = (float) (livingEntity.getAttributeValue((Attribute) MyAttributesRegister.MaxShield.get())
                    * livingEntity.getAttributeValue((Attribute) Attributes.MAX_HEALTH));
            livingEntity.getPersistentData().putFloat(shield_number, Math.min(maxShield, Math.max(finalShield, 0)));
        }
    }
    //获取当前Shield
    public static float getShield(LivingEntity livingEntity) {
        return livingEntity.getPersistentData().getFloat(shield_number);
    }
    //锁定一定距离内最近的【非自身随从】目标
    public static LivingEntity getNearestNonFollowerOnPath(LivingEntity livingEntity, double range) {
        Vec3 srcVec = livingEntity.getEyePosition();
        Vec3 lookVec = livingEntity.getViewVector(1.0F);
        Vec3 destVec = srcVec.add(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range);
        float borderSize = 0.5F;
        float expandAmount = 1.0F;
        // 获取路径范围内所有实体
        List<Entity> possibleList = livingEntity.level.getEntities(
                livingEntity,
                livingEntity.getBoundingBox()
                        .expandTowards(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range)
                        .inflate(expandAmount, expandAmount, expandAmount)
        );
        LivingEntity nearestEntity = null;
        double nearestDist = Double.MAX_VALUE;
        for (Entity entity : possibleList) {
            // 只锁定LivingEntity且排除玩家自己
            if (!(entity instanceof LivingEntity)) continue;
            if (entity == livingEntity) continue;
            // 过滤玩家自己的随从
            if (entity instanceof OwnableEntity ownable && ownable.getOwner() == livingEntity) {
                continue;
            }
            AABB collisionBB = entity.getBoundingBox().inflate(borderSize, borderSize, borderSize);
            Optional<Vec3> interceptPos = collisionBB.clip(srcVec, destVec);
            boolean isOnPath = false;
            if (collisionBB.contains(srcVec)) {
                isOnPath = true;
            } else if (interceptPos.isPresent()) {
                isOnPath = true;
            }
            if (isOnPath) {
                double dist = entity.distanceTo(livingEntity);
                if (dist <= range && dist < nearestDist) {
                    nearestDist = dist;
                    nearestEntity = (LivingEntity) entity;
                }
            }
        }
        return nearestEntity; // 没找到会返回null
    }
    //在二者之间生成粒子效果——————————————————————维度——————————————————发射者——————————————————终点————————————————————————粒子特效，如ParticleTypes.LAVA
    public static void spawnLinkParticles(Level level, LivingEntity entityA, LivingEntity entityB, ParticleOptions particleType) {
        //level.isClientSide &&
        if (entityA != null && entityB != null) {
            //爆发的熔岩粒子ParticleTypes.LAVA;
            //灰色ParticleTypes.ASH;
            //向外扩散的紫色粒子ParticleTypes.WITCH;
            //樱花叶ParticleTypes.CHERRY_LEAVES;
            //堆肥桶绿色粒子ParticleTypes.COMPOSTER;
            //水花ParticleTypes.DOLPHIN;
            //集中的紫色粒子ParticleTypes.DRAGON_BREATH;
            //深黄色粒子ParticleTypes.DRIPPING_HONEY;
            //末地烛白色粒子ParticleTypes.END_ROD;
            //爆炸（就像是音爆一样）ParticleTypes.EXPLOSION;
            //末影龙爆炸（激烈一点）ParticleTypes.EXPLOSION_EMITTER;
            //音爆ParticleTypes.SONIC_BOOM;
            //幽匿粒子（小型音爆）ParticleTypes.SCULK_CHARGE_POP;
            //雪花粒子ParticleTypes.SNOWFLAKE;
            // 1. 获取粒子起点（实体A眼睛位置 + 朝向 * 0.25）
            Vec3 origin = entityA.getEyePosition(1.0F);
            Vec3 lookVec = entityA.getViewVector(1.0F);
            Vec3 startPos = origin.add(lookVec.scale(0.25));

            // 2. 获取终点（实体B位置上方0.5格）
            Vec3 endPos = new Vec3(entityB.getX(), entityB.getY() + 1, entityB.getZ());

            // 3. 计算两点距离
            double distance = startPos.distanceTo(endPos);

            // 4. 根据距离决定粒子数量(例如：距离越远粒子越多，每格10个，最少5个，最多100个)
            int particleCount = (int) Math.min(Math.max(distance * 10, 5), 100);

            // 5. 在起点和终点之间生成粒子
            // 颗粒均匀分布，或者加点随机扰动

            for (int i = 0; i < particleCount; i++) {
                double t = (double) i / particleCount; // 0 ~ 1 线性插值位置
                // 线性插值位置
                double x = lerp(t, startPos.x(), endPos.x());
                double y = lerp(t, startPos.y(), endPos.y());
                double z = lerp(t, startPos.z(), endPos.z());

                // 粒子运动速度(可以加一点随机扰动，让粒子不完全静止)
                double offsetX = (level.random.nextDouble() - 0.5) * 0.02;
                double offsetY = (level.random.nextDouble() - 0.5) * 0.02;
                double offsetZ = (level.random.nextDouble() - 0.5) * 0.02;

                // 生成粒子
                level.addParticle(
                        particleType,
                        x, y, z,
                        offsetX, offsetY, offsetZ
                );
            }
        }
    }
    //BOSS判断
    public static boolean isBossEntity(EntityType<?> entity) {
        // 检查 "more_tetra_tools:bosses" tag
        boolean isMoreTetraBoss = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getTag(
                TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation("myosotis", "bosses"))
        ).contains(entity);
        // 检查 "forge:bosses" tag
        boolean isForgeBoss = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getTag(
                TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation("forge", "bosses"))
        ).contains(entity);
        // 只要满足其中一个 tag 即可
        return isMoreTetraBoss || isForgeBoss;
    }
    //元素积累阈值提升
    public static boolean isElementLvlUp25(EntityType<?> entity) {
        boolean itIs = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getTag(
                TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation("myosotis", "element_level_25"))
        ).contains(entity);
        return itIs;
    }
    public static boolean isElementLvlUp50(EntityType<?> entity) {
        boolean itIs = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getTag(
                TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation("myosotis", "element_level_50"))
        ).contains(entity);
        return itIs;
    }
    public static boolean isElementLvlUp75(EntityType<?> entity) {
        boolean itIs = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getTag(
                TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation("myosotis", "element_level_75"))
        ).contains(entity);
        return itIs;
    }
    public static boolean isElementLvlUp100(EntityType<?> entity) {
        boolean itIs = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getTag(
                TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation("myosotis", "element_level_100"))
        ).contains(entity);
        return itIs;
    }
    private static double lerp(double t, double start, double end) {
        return start + t * (end - start);
    }
}
