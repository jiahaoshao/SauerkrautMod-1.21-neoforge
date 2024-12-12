package net.fangyi.sauerkrautmagicmod.entity;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.entity.custom.FlyingSwordEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, SauerkrautMagicMod.MODID);
    public static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(Registries.ITEM, SauerkrautMagicMod.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<FlyingSwordEntity>> FLYING_SWORD = make(ModEntityNames.FLYING_SWORD, FlyingSwordEntity::new, MobCategory.MISC, 1.414F, 0.2F, false, 0x082520,  0xa4fdf0);






    //Same as below, but with riding offset set to 0.0F;
    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> make(ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, int primary, int secondary) {
        return make(id, factory, classification, width, height, primary, secondary, 0.0F);
    }

    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> make(ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, int primary, int secondary, float ridingOffset) {
        return make(id, factory, classification, width, height, false, primary, secondary, ridingOffset);
    }

    //Same as below, but with riding offset set to 0.0F;
    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> make(ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, boolean fireproof, int primary, int secondary) {
        return make(id, factory, classification, width, height, fireproof, primary, secondary, 0.0F);
    }

    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> make(ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, boolean fireproof, int primary, int secondary, float ridingOffset) {
        return build(id, makeBuilder(factory, classification, width, height, 80, 3, ridingOffset), fireproof, primary, secondary);
    }

    //Same as below, but with riding offset set to 0.0F;
    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> make(ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, float eyeHeight, boolean fireproof, int primary, int secondary) {
        return make(id, factory, classification, width, height, eyeHeight, fireproof, primary, secondary, 0.0F);
    }

    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> make(ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, float eyeHeight, boolean fireproof, int primary, int secondary, float ridingOffset) {
        return build(id, makeBuilder(factory, classification, width, height, 80, 3, ridingOffset).eyeHeight(eyeHeight), fireproof, primary, secondary);
    }

    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> buildNoEgg(ResourceLocation id, EntityType.Builder<E> builder, boolean fireproof) {
        if (fireproof) builder.fireImmune();
        return ENTITY_TYPES.register(id.getPath(), () -> builder.build(id.toString()));
    }

    @SuppressWarnings("unchecked")
    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> build(ResourceLocation id, EntityType.Builder<E> builder, boolean fireproof, int primary, int secondary) {
        if (fireproof) builder.fireImmune();
        DeferredHolder<EntityType<?>, EntityType<E>> ret = ENTITY_TYPES.register(id.getPath(), () -> builder.build(id.toString()));
        if (primary != 0 && secondary != 0) {
            SPAWN_EGGS.register(id.getPath() + "_spawn_egg", () -> new DeferredSpawnEggItem(() -> (EntityType<? extends Mob>) ret.get(), primary, secondary, new Item.Properties()));
        }
        return ret;
    }

    private static <E extends Entity> EntityType.Builder<E> makeCastedBuilder(@SuppressWarnings("unused") Class<E> cast, EntityType.EntityFactory<E> factory, float width, float height, int range, int interval) {
        return makeBuilder(factory, MobCategory.MISC, width, height, range, interval, 0.0F);
    }

    private static <E extends Entity> EntityType.Builder<E> makeBuilder(EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, int range, int interval, float ridingOffset) {
        return EntityType.Builder.of(factory, classification)
                .sized(width, height)
                .setTrackingRange(range)
                .setUpdateInterval(interval)
                .setShouldReceiveVelocityUpdates(true)
                .ridingOffset(ridingOffset);
    }

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
        SPAWN_EGGS.register(eventBus);
    }
}
