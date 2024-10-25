package net.fangyi.sauerkrautmagicmod.enchatment;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.enchatment.effect.entity.ApplyFrostedEffect;
import net.fangyi.sauerkrautmagicmod.enchatment.effect.entity.SecKillEffect;
import net.fangyi.sauerkrautmagicmod.enchatment.effect.value.PowerfulEffect;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.EnchantmentLevelProvider;

public class ModEnchantments{
    public static final ResourceKey<Enchantment> FIRE_REACT = registerKey("fire_react");
    public static final ResourceKey<Enchantment> CHILL_AURA = registerKey("chill_aura");
    public static final ResourceKey<Enchantment> RAPID_SHOOT = registerKey("rapid_shoot");
    public static final ResourceKey<Enchantment> POWERFUL = registerKey("powerful");
    public static final ResourceKey<Enchantment> ONE_TAP = registerKey("one_tap");

    private static ResourceKey<Enchantment> registerKey(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, SauerkrautMagicMod.prefix(name));
    }

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<DamageType> damageTypes = context.lookup(Registries.DAMAGE_TYPE);
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

        register(context, FIRE_REACT, new Enchantment.Builder(Enchantment.definition(
                items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                1,
                3,
                Enchantment.dynamicCost(5, 9),
                Enchantment.dynamicCost(20, 9),
                8,
                EquipmentSlotGroup.ARMOR)
        ).exclusiveWith(HolderSet.direct(enchantments.getOrThrow(Enchantments.THORNS), enchantments.getOrThrow(CHILL_AURA)))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK,
                        EnchantmentTarget.VICTIM,
                        EnchantmentTarget.ATTACKER,
                        AllOf.entityEffects(
                                new Ignite(LevelBasedValue.perLevel(2.0F, 3.0F)),
                                new DamageItem(LevelBasedValue.constant(2.0F))),
                        LootItemRandomChanceCondition.randomChance(EnchantmentLevelProvider.forEnchantmentLevel(LevelBasedValue.perLevel(0.15F)))));

        register(context, CHILL_AURA, new Enchantment.Builder(Enchantment.definition(
                items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                1,
                3,
                Enchantment.dynamicCost(5, 9),
                Enchantment.dynamicCost(20, 9),
                8,
                EquipmentSlotGroup.ARMOR)
        ).exclusiveWith(HolderSet.direct(enchantments.getOrThrow(Enchantments.THORNS), enchantments.getOrThrow(FIRE_REACT)))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK,
                        EnchantmentTarget.VICTIM,
                        EnchantmentTarget.ATTACKER,
                        AllOf.entityEffects(
                                new ApplyFrostedEffect(LevelBasedValue.constant(200), LevelBasedValue.perLevel(0.0F, 1.0F)),
                                new DamageItem(LevelBasedValue.constant(2.0F))),
                        LootItemRandomChanceCondition.randomChance(EnchantmentLevelProvider.forEnchantmentLevel(LevelBasedValue.perLevel(0.15F)))));

        register(context, RAPID_SHOOT, new Enchantment.Builder(Enchantment.definition(
                items.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                items.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE),
                1,
                1,
                Enchantment.constantCost(20),
                Enchantment.constantCost(50),
                8,
                EquipmentSlotGroup.MAINHAND)
        ));

        register(
                context,
                POWERFUL,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                                        2,
                                        1,
                                        Enchantment.constantCost(20),
                                        Enchantment.constantCost(50),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new SecKillEffect(LevelBasedValue.perLevel(0.1F)),
                                DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(true))
                        )
        );

        register(
                context,
                ONE_TAP,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                                        items.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE),
                                        2,
                                        1,
                                        Enchantment.constantCost(20),
                                        Enchantment.constantCost(50),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.DAMAGING_ENTITY,
                                new SecKillEffect(LevelBasedValue.perLevel(0.5F)),
                                DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(true))
                        )
                        .withEffect(EnchantmentEffectComponents.PROJECTILE_COUNT, new AddValue(LevelBasedValue.perLevel(2.0F)))
                        .withEffect(EnchantmentEffectComponents.PROJECTILE_SPREAD, new AddValue(LevelBasedValue.perLevel(10.0F)))
        );

    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }
}
