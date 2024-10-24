package net.fangyi.sauerkrautmagicmod.enchatment.effect.value;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;

public record PowerfulEffect(LevelBasedValue value) implements EnchantmentValueEffect {
    public static final MapCodec<PowerfulEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                LevelBasedValue.CODEC.fieldOf("powerful").forGetter(PowerfulEffect::value))
            .apply(instance, PowerfulEffect::new));

    public float process(int enchantmentLevel, RandomSource random, float value) {
        return value + this.value.calculate(enchantmentLevel);
    }

    @Override
    public MapCodec<? extends EnchantmentValueEffect> codec() {
        return CODEC;
    }

    public LevelBasedValue value() {
        return this.value;
    }
}
