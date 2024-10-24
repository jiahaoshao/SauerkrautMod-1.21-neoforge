package net.fangyi.sauerkrautmagicmod.enchatment;

import com.mojang.serialization.MapCodec;
import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.enchatment.effect.entity.ApplyFrostedEffect;
import net.fangyi.sauerkrautmagicmod.enchatment.effect.entity.SmashBlocksEffect;
import net.fangyi.sauerkrautmagicmod.enchatment.effect.value.PowerfulEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEnchantmentValueEffect {
    public static final DeferredRegister<MapCodec<? extends EnchantmentValueEffect>> VALUE_EFFECTS = DeferredRegister.create(Registries.ENCHANTMENT_VALUE_EFFECT_TYPE, SauerkrautMagicMod.MODID);

    public static final DeferredHolder<MapCodec<? extends EnchantmentValueEffect>, MapCodec<PowerfulEffect>> POWERFUL = VALUE_EFFECTS.register("powerful", () -> PowerfulEffect.CODEC);

    public static void register(IEventBus bus) {
        VALUE_EFFECTS.register(bus);
    }
}
