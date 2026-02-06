    package com.inolia_zaicek.myosotis.ModelProvider;

    import com.inolia_zaicek.myosotis.Myosotis;
    import com.inolia_zaicek.myosotis.Register.FeatureRegistry;
    import net.minecraft.core.HolderLookup;
    import net.minecraft.core.RegistryAccess;
    import net.minecraft.core.RegistrySetBuilder;
    import net.minecraft.core.registries.BuiltInRegistries;
    import net.minecraft.core.registries.Registries;
    import net.minecraft.data.DataGenerator;
    import net.minecraft.data.PackOutput;
    import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
    import net.minecraftforge.common.data.ExistingFileHelper;
    import net.minecraftforge.registries.ForgeRegistries;

    import java.util.Set;
    import java.util.concurrent.CompletableFuture;

    public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {
        private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
                .add(ForgeRegistries.Keys.BIOME_MODIFIERS, FeatureRegistry::bootstrapBiomeModifier)
                .add(Registries.CONFIGURED_FEATURE, FeatureRegistry::bootstrapConfiguredFeature)
                .add(Registries.PLACED_FEATURE, FeatureRegistry::bootstrapPlacedFeature)
                ;


        // Use addProviders() instead
        private RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
            super(output, provider, BUILDER, Set.of("minecraft", Myosotis.MODID));
        }

        public static void addProviders(boolean isServer, DataGenerator generator, PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
            generator.addProvider(isServer, new RegistryDataGenerator(output, provider));

        }

        private static HolderLookup.Provider append(HolderLookup.Provider original, RegistrySetBuilder builder) {
            return builder.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), original);
        }
    }
