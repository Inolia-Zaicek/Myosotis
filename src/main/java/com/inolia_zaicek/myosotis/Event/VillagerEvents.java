package com.inolia_zaicek.myosotis.Event;

import com.inolia_zaicek.myosotis.Myosotis;
import com.inolia_zaicek.myosotis.Register.MyItemRegister;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

// 通过该注解，类会注册成为事件总线的监听者（modid是自定义mod的ID）
@Mod.EventBusSubscriber(modid = Myosotis.MODID)
@ParametersAreNonnullByDefault
public class VillagerEvents
{
    // 监听MC自带的村民交易事件，通过该事件可以获取和修改村民的职业交易列表
    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        // 获取当前村民职业对应的交易列表，返回的是一个Int2ObjectMap，key是交易等级（1-5）
        // value是一个List<VillagerTrades.ItemListing>，即该等级可用的所有交易
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        // 获取当前触发事件的村民职业（农夫、渔夫等）
        VillagerProfession profession = event.getType();
        // 获取该职业在注册表中的资源位置(ResourceLocation)，用于判断当前职业是不是我们关心的职业
        ResourceLocation professionKey = ForgeRegistries.VILLAGER_PROFESSIONS.getKey(profession);
        if (professionKey == null) return; // 防御性判断，职业不存在则不处理
        // 如果是"farmer"（农夫）职业，给它增加自定义的“额外交易”
        if (professionKey.getPath().equals("cleric")) {
            // trades.get(1) 返回农夫等级1对应的交易列表，add()方法添加新的交易项—————————————————————————————价格————————次数——————EXP
            trades.get(1).add(itemForEmeraldTrade(MyItemRegister.CelestialCrystal.get(), 8,4, 10));
        }
    }

    // 该方法是监听流浪商人的交易事件，和上述村民交易逻辑类似，这里不展开详细解析
    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> trades = event.getGenericTrades();
        trades.add(itemForEmeraldTrade(MyItemRegister.CelestialCrystal.get(), 6,8, 10));
    }

    // 这是一个工具方法，用于创建“绿宝石换指定物品”的交易
    // 参数说明：// item：要换出的物品// count：数量// maxTrades：最大交易次数// xp：交易者经验奖励// 返回一个BasicItemListing对象，定义了该交易的完整信息
    public static BasicItemListing emeraldForItemsTrade(ItemLike item, int count, int maxTrades, int xp) {
        // 构造一个交易，玩家给村民指定数量的物品，村民给玩家1个绿宝石【这个数额是固定的？
        // 0.05F 为交易经验修正值 (影响价格变化等)
        //如：增加1级交易，用收集26个洋葱换1个绿宝石，最大交易次数16次，完成交易村民获得2经验值
        return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.EMERALD), maxTrades, xp, 0.05F);
    }

    // 另一个工具方法，定义“用绿宝石换物品”的交易
    public static BasicItemListing itemForEmeraldTrade(ItemLike item,int emeraldNumber, int maxTrades, int xp) {
        // 售卖物品给玩家，每次需要1颗绿宝石进行购买
        return new BasicItemListing(emeraldNumber, new ItemStack(item), maxTrades, xp, 0.05F);
    }
}