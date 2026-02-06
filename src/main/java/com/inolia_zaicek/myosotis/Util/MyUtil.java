package com.inolia_zaicek.myosotis.Util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
}
