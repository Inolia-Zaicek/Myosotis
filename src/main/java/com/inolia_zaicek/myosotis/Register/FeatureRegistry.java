//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.inolia_zaicek.myosotis.Register;

import java.util.List;

import com.inolia_zaicek.myosotis.Register.BlockRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistries.Keys;

public class FeatureRegistry {
    // 延迟注册配置特征，方便在特定生命周期阶段注册世界生成配置
    private static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registries.CONFIGURED_FEATURE, "myosotis");

    // 延迟注册放置特征，定义如何将配置特征放进世界
    private static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registries.PLACED_FEATURE, "myosotis");

    // 资源键，标识秘银矿石生成的配置特征
    public static final ResourceKey<ConfiguredFeature<?, ?>> deepslate_mithril_FEATURE = configuredFeatureResourceKey("ore_deepslate_mithril");

    // 资源键，标识秘银矿石的放置特征（定义具体放置规则）
    public static final ResourceKey<PlacedFeature> deepslate_mithril_PLACEMENT = placedFeatureResourceKey("ore_deepslate_mithril");

    // 生物群系修饰器资源键，用于把秘银矿石生成添加到目标生物群系
    public static final ResourceKey<BiomeModifier> ADD_deepslate_mithril_ORE = biomeModifierResourceKey("add_deepslate_mithril_ore");
    //秘银
    public static final ResourceKey<ConfiguredFeature<?, ?>> mithril_FEATURE = configuredFeatureResourceKey("ore_mithril");
    public static final ResourceKey<PlacedFeature> mithril_PLACEMENT = placedFeatureResourceKey("ore_mithril");;
    public static final ResourceKey<BiomeModifier> ADD_mithril_ORE = biomeModifierResourceKey("add_mithril_ore");
    // 注册方法，注册所有配置和放置特征到事件总线中
    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
        PLACED_FEATURES.register(eventBus);
    }

    // 定义秘银矿石的配置特征（生成规则）
    public static void bootstrapConfiguredFeature(BootstapContext<ConfiguredFeature<?, ?>> context) {
        // 【替换的方块】：目标替换方块为可被深板岩矿石替代的方块，即秘银矿石只能生成在这些方块上替代生成
        RuleTest ruleTestArcaneDebris = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        // 定义秘银矿石的生成状态（默认块状态）
        List<OreConfiguration.TargetBlockState> arcaneDebrisList =
                List.of(OreConfiguration.target(ruleTestArcaneDebris, (BlockRegister.DeepslateMithrilOre.get()).defaultBlockState()));
        // 【生成的具体含义】：
        // 注册配置特征，定义每次生成矿脉最大方块数为3，生成概率参数为1.0F————0.0表示【方块暴露在空气中时也可生成】、1.0表示————【只能被埋起来，周围不能有空气】
        // 意味着每个矿脉最多3个矿石块，权重因子为1（标准生成概率，未降低）
        FeatureUtils.register(context, deepslate_mithril_FEATURE, Feature.ORE, new OreConfiguration(arcaneDebrisList, 3, 0.0F));
    }

    // 定义秘银矿石在世界中的放置规则
    public static void bootstrapPlacedFeature(BootstapContext<PlacedFeature> context) {
        // 获取秘银矿石配置特征的引用
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(CONFIGURED_FEATURES.getRegistryKey());
        Holder<ConfiguredFeature<?, ?>> holderArcaneDebris = holdergetter.getOrThrow(deepslate_mithril_FEATURE);

        // 【y轴范围】：
        // 指定生成范围在地下Y坐标 -63 到 -38 层之间
        // 该高度范围限制秘银矿石只能在这一区域生成，代表地下较深层次
        List<PlacementModifier> list = List.of(
                CountPlacement.of(4), // 每区块7次生成尝试
                InSquarePlacement.spread(), // 在方块区域内均匀分布（XZ轴）
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-32)), // 【y轴范围】
                BiomeFilter.biome() // 限制只能在允许生物群系生成
        );

        // 注册放置特征，将配置特征与上述放置规则绑定
        PlacementUtils.register(context, deepslate_mithril_PLACEMENT, holderArcaneDebris, list);
    }
    // 定义目标生物群系并添加矿石生成特征
    public static void bootstrapBiomeModifier(BootstapContext<BiomeModifier> context) {
        // 获取所有主世界生物群系的引用
        HolderGetter<Biome> biomes = context.lookup(ForgeRegistries.BIOMES.getRegistryKey());

        // 获取放置特征的引用
        HolderGetter<PlacedFeature> features = context.lookup(PLACED_FEATURES.getRegistryKey());

        // 将秘银矿石生成放置特征添加到主世界所有地下矿石生成阶段
        context.register(ADD_deepslate_mithril_ORE,
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        tag(biomes, BiomeTags.IS_OVERWORLD), // 作用于主世界生物群系
                        feature(features, deepslate_mithril_PLACEMENT), // 绑定放置特征
                        Decoration.UNDERGROUND_ORES // 生成阶段为地下矿石生成
                ));
    }

    //普通秘银
    public static void bootstrapConfiguredFeature1(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest ruleTestArcaneDebris = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        List<OreConfiguration.TargetBlockState> arcaneDebrisList =
                List.of(OreConfiguration.target(ruleTestArcaneDebris, (BlockRegister.MithrilOre.get()).defaultBlockState()));
        FeatureUtils.register(context, mithril_FEATURE, Feature.ORE, new OreConfiguration(arcaneDebrisList, 6, 0.0F));
    }

    public static void bootstrapPlacedFeature1(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(CONFIGURED_FEATURES.getRegistryKey());
        Holder<ConfiguredFeature<?, ?>> holderArcaneDebris = holdergetter.getOrThrow(mithril_FEATURE);
        List<PlacementModifier> list = List.of(
                CountPlacement.of(4), // 每区块7次生成尝试
                InSquarePlacement.spread(), // 在方块区域内均匀分布（XZ轴）
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-16)), // 【y轴范围】
                BiomeFilter.biome() // 限制只能在允许生物群系生成
        );PlacementUtils.register(context, mithril_PLACEMENT, holderArcaneDebris, list);
    }
    public static void bootstrapBiomeModifier1(BootstapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(ForgeRegistries.BIOMES.getRegistryKey());// 获取所有主世界生物群系的引用
        HolderGetter<PlacedFeature> features = context.lookup(PLACED_FEATURES.getRegistryKey());
        context.register(ADD_mithril_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, BiomeTags.IS_OVERWORLD), // 作用于主世界生物群系
                feature(features, mithril_PLACEMENT), // 绑定放置特征
                Decoration.UNDERGROUND_ORES // 生成阶段为地下矿石生成
        ));
    }

    /*
    //普通魔力
    public static final ResourceKey<ConfiguredFeature<?, ?>> magic_FEATURE = configuredFeatureResourceKey("ore_magic");
    public static final ResourceKey<PlacedFeature> magic_PLACEMENT = placedFeatureResourceKey("ore_magic");;
    public static final ResourceKey<BiomeModifier> ADD_magic_ORE = biomeModifierResourceKey("add_magic_ore");
    public static void bootstrapConfiguredFeature2(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest ruleTestArcaneDebris = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        List<OreConfiguration.TargetBlockState> arcaneDebrisList =
                List.of(OreConfiguration.target(ruleTestArcaneDebris, (BlockRegister.MagicOre.get()).defaultBlockState()));
        FeatureUtils.register(context, magic_FEATURE, Feature.ORE, new OreConfiguration(arcaneDebrisList, 6, 0.0F));
    }

    public static void bootstrapPlacedFeature2(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(CONFIGURED_FEATURES.getRegistryKey());
        Holder<ConfiguredFeature<?, ?>> holderArcaneDebris = holdergetter.getOrThrow(magic_FEATURE);
        List<PlacementModifier> list = List.of(
                CountPlacement.of(10), // 每区块7次生成尝试
                InSquarePlacement.spread(), // 在方块区域内均匀分布（XZ轴）
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-16)), // 【y轴范围】
                BiomeFilter.biome() // 限制只能在允许生物群系生成
        );PlacementUtils.register(context, magic_PLACEMENT, holderArcaneDebris, list);
    }
    public static void bootstrapBiomeModifier2(BootstapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(ForgeRegistries.BIOMES.getRegistryKey());// 获取所有主世界生物群系的引用
        HolderGetter<PlacedFeature> features = context.lookup(PLACED_FEATURES.getRegistryKey());
        context.register(ADD_magic_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, BiomeTags.IS_OVERWORLD), // 作用于主世界生物群系
                feature(features, magic_PLACEMENT), // 绑定放置特征
                Decoration.UNDERGROUND_ORES // 生成阶段为地下矿石生成
        ));
    }
    //复制到这就行


    //深层魔力
    public static final ResourceKey<ConfiguredFeature<?, ?>> deepslate_magic_FEATURE = configuredFeatureResourceKey("ore_deepslate_magic");
    public static final ResourceKey<PlacedFeature> deepslate_magic_PLACEMENT = placedFeatureResourceKey("ore_deepslate_magic");;
    public static final ResourceKey<BiomeModifier> ADD_deepslate_magic_ORE = biomeModifierResourceKey("add_deepslate_magic_ore");
    public static void bootstrapConfiguredFeature3(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest ruleTestArcaneDebris = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        List<OreConfiguration.TargetBlockState> arcaneDebrisList =
                List.of(OreConfiguration.target(ruleTestArcaneDebris, (BlockRegister.DeepslateMagicOre.get()).defaultBlockState()));
        FeatureUtils.register(context, deepslate_magic_FEATURE, Feature.ORE, new OreConfiguration(arcaneDebrisList, 6, 0.0F));
    }

    public static void bootstrapPlacedFeature3(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(CONFIGURED_FEATURES.getRegistryKey());
        Holder<ConfiguredFeature<?, ?>> holderArcaneDebris = holdergetter.getOrThrow(deepslate_magic_FEATURE);
        List<PlacementModifier> list = List.of(
                CountPlacement.of(10), // 每区块7次生成尝试
                InSquarePlacement.spread(), // 在方块区域内均匀分布（XZ轴）
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-32)), // 【y轴范围】
                BiomeFilter.biome() // 限制只能在允许生物群系生成
        );PlacementUtils.register(context, deepslate_magic_PLACEMENT, holderArcaneDebris, list);
    }
    public static void bootstrapBiomeModifier3(BootstapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(ForgeRegistries.BIOMES.getRegistryKey());// 获取所有主世界生物群系的引用
        HolderGetter<PlacedFeature> features = context.lookup(PLACED_FEATURES.getRegistryKey());
        context.register(ADD_deepslate_magic_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                tag(biomes, BiomeTags.IS_OVERWORLD), // 作用于主世界生物群系
                feature(features, deepslate_magic_PLACEMENT), // 绑定放置特征
                Decoration.UNDERGROUND_ORES // 生成阶段为地下矿石生成
        ));
    }

     */

    //下面的不要动了
    // 工具方法，创建配置特征的资源键
    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureResourceKey(String name) {
        return ResourceKey.create(CONFIGURED_FEATURES.getRegistryKey(), new ResourceLocation("myosotis", name));
    }

    // 工具方法，创建放置特征的资源键
    private static ResourceKey<PlacedFeature> placedFeatureResourceKey(String name) {
        return ResourceKey.create(PLACED_FEATURES.getRegistryKey(), new ResourceLocation("myosotis", name));
    }

    // 工具方法，创建生物群系修饰器的资源键
    private static ResourceKey<BiomeModifier> biomeModifierResourceKey(String name) {
        return ResourceKey.create(Keys.BIOME_MODIFIERS, new ResourceLocation("myosotis", name));
    }

    // 通过生物群系标记获取HolderSet，便于筛选特定生物群系
    private static HolderSet<Biome> tag(HolderGetter<Biome> holderGetter, TagKey<Biome> key) {
        return holderGetter.getOrThrow(key);
    }

    // 通过资源键获取放置特征的HolderSet，方便后续操作
    private static HolderSet<PlacedFeature> feature(HolderGetter<PlacedFeature> holderGetter, ResourceKey<PlacedFeature> feature) {
        return HolderSet.direct(new Holder[]{holderGetter.getOrThrow(feature)});
    }
}