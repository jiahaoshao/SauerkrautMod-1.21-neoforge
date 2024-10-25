package net.fangyi.sauerkrautmagicmod.enchatment.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record SecKillEffect(LevelBasedValue duration) implements EnchantmentEntityEffect {
    public static final MapCodec<SecKillEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LevelBasedValue.CODEC.fieldOf("duration").forGetter(SecKillEffect::duration))
            .apply(instance, SecKillEffect::new));

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        entity.kill();
        SauerkrautMagicMod.LOGGER.info(entity.toString());
        SauerkrautMagicMod.LOGGER.info(origin.toString());
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
