package net.fangyi.sauerkrautmagicmod.enchatment;

import com.mojang.serialization.MapCodec;
import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.enchatment.effect.ApplyFrostedEffect;
import net.fangyi.sauerkrautmagicmod.enchatment.effect.SmashBlocksEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_EFFECTS = DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, SauerkrautMagicMod.MODID);

    public static final DeferredHolder<MapCodec<? extends EnchantmentEntityEffect>, MapCodec<ApplyFrostedEffect>> APPLY_FROSTED = ENTITY_EFFECTS.register("apply_frosted", () -> ApplyFrostedEffect.CODEC);
    public static final DeferredHolder<MapCodec<? extends EnchantmentEntityEffect>, MapCodec<SmashBlocksEffect>> SMASH_BLOCKS = ENTITY_EFFECTS.register("smash_blocks", () -> SmashBlocksEffect.CODEC);

    public static void register(IEventBus bus) {
        ENTITY_EFFECTS.register(bus);
    }
}
