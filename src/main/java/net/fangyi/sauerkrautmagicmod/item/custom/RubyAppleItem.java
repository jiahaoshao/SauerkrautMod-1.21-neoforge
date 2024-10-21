package net.fangyi.sauerkrautmagicmod.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class RubyAppleItem extends Item {
    public RubyAppleItem() {
        super(new Properties().food(FOOD_PROPERTIES));
    }

    private static final FoodProperties FOOD_PROPERTIES = new FoodProperties.Builder()
            .saturationModifier(10)
            .nutrition(20)
            .alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 3 * 20, 255), 0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 30 * 20, 255), 0.75f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 30 * 20, 255), 0.75f)
            .build();
}