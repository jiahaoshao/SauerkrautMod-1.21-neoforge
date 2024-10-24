package net.fangyi.sauerkrautmagicmod.painting;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.decoration.PaintingVariants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class ModPaintingVariants extends PaintingVariants {
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS =
            DeferredRegister.create(Registries.PAINTING_VARIANT, SauerkrautMagicMod.MODID);


//    public static final ResourceKey<PaintingVariant> XI_GUA = create("xi_gua");
//    //public static final TagKey<PaintingVariant> XI_GUA_1 = PAINTING_VARIANTS.createTagKey("xi_gua");
//    //public static final TagKey<PaintingVariant> JIU_1 = PAINTING_VARIANTS.createTagKey("jiu");
//    public static final ResourceKey<PaintingVariant> JIU = create("jiu");
//
//    public static final Supplier<PaintingVariant> XI_GUA_1 = PAINTING_VARIANTS.register("xi_gua",() -> new PaintingVariant(16,32, XI_GUA.location()));
//    public static final Supplier<PaintingVariant> JIU_1 = PAINTING_VARIANTS.register("jiu",() -> new PaintingVariant(16,16, JIU.location()));
//
//
//    private static ResourceKey<PaintingVariant> create(String name) {
//        return ResourceKey.create(Registries.PAINTING_VARIANT, ResourceLocation.fromNamespaceAndPath(SauerkrautMagicMod.MODID, name));
//    }

    public static void register(IEventBus eventBus){
        PAINTING_VARIANTS.register(eventBus);
    }

}
