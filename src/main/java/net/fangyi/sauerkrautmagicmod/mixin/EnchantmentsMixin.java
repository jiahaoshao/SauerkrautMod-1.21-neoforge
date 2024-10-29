package net.fangyi.sauerkrautmagicmod.mixin;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.AllOf;
import net.minecraft.world.item.enchantment.effects.PlaySoundEffect;
import net.minecraft.world.item.enchantment.effects.SummonEntityEffect;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Enchantments.class)
public class EnchantmentsMixin {
    @Shadow @Final public static ResourceKey<Enchantment> MULTISHOT;

    @Shadow @Final public static ResourceKey<Enchantment> CHANNELING;

    @Inject(method = "register", at = @At("HEAD"))
    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder, CallbackInfo ci) {
        HolderGetter<Enchantment> holdergetter1 = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> holdergetter2 = context.lookup(Registries.ITEM);

        //让弓也有多重射击
        if(key == MULTISHOT) {
           builder = Enchantment.enchantment(
                            Enchantment.definition(
                                    holdergetter2.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE),
                                    holdergetter2.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                                    2,
                                    1,
                                    Enchantment.constantCost(20),
                                    Enchantment.constantCost(50),
                                    4,
                                    EquipmentSlotGroup.MAINHAND
                            )
                    )
                    .withEffect(EnchantmentEffectComponents.PROJECTILE_COUNT, new AddValue(LevelBasedValue.perLevel(2.0F)))
                    .withEffect(EnchantmentEffectComponents.PROJECTILE_SPREAD, new AddValue(LevelBasedValue.perLevel(10.0F)));
        }
        //让弓箭可以引雷（未实现）
        if(key == CHANNELING){
            builder = Enchantment.enchantment(
                            Enchantment.definition(
                                    holdergetter2.getOrThrow(ItemTags.TRIDENT_ENCHANTABLE),
                                    holdergetter2.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                                    1,
                                    1,
                                    Enchantment.constantCost(25),
                                    Enchantment.constantCost(50),
                                    8,
                                    EquipmentSlotGroup.MAINHAND
                            )
                    )
                    .withEffect(
                            EnchantmentEffectComponents.POST_ATTACK,
                            EnchantmentTarget.ATTACKER,
                            EnchantmentTarget.VICTIM,
                            AllOf.entityEffects(
                                    new SummonEntityEffect(HolderSet.direct(EntityType.LIGHTNING_BOLT.builtInRegistryHolder()), false),
                                    new PlaySoundEffect(SoundEvents.TRIDENT_THUNDER, ConstantFloat.of(5.0F), ConstantFloat.of(1.0F))
                            ),
                            AllOfCondition.allOf(
                                    WeatherCheck.weather().setThundering(true),
                                    LootItemEntityPropertyCondition.hasProperties(
                                            LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().located(LocationPredicate.Builder.location().setCanSeeSky(true))
                                    ),
                                    LootItemEntityPropertyCondition.hasProperties(
                                            LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity().of(EntityType.TRIDENT)
                                    )
                            )
                    )
                    .withEffect(
                            EnchantmentEffectComponents.HIT_BLOCK,
                            AllOf.entityEffects(
                                    new SummonEntityEffect(HolderSet.direct(EntityType.LIGHTNING_BOLT.builtInRegistryHolder()), false),
                                    new PlaySoundEffect(SoundEvents.TRIDENT_THUNDER, ConstantFloat.of(5.0F), ConstantFloat.of(1.0F))
                            ),
                            AllOfCondition.allOf(
                                    WeatherCheck.weather().setThundering(true),
                                    LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(EntityType.TRIDENT)),
                                    LocationCheck.checkLocation(LocationPredicate.Builder.location().setCanSeeSky(true)),
                                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.LIGHTNING_ROD)
                            )
                    )
                    .withEffect(
                            EnchantmentEffectComponents.PROJECTILE_SPAWNED,
                            AllOf.entityEffects(
                                    new SummonEntityEffect(HolderSet.direct(EntityType.LIGHTNING_BOLT.builtInRegistryHolder()), false),
                                    new PlaySoundEffect(SoundEvents.TRIDENT_THUNDER, ConstantFloat.of(5.0F), ConstantFloat.of(1.0F))
                            ),
                            AllOfCondition.allOf(
                                    LootItemEntityPropertyCondition.hasProperties(
                                            LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().located(LocationPredicate.Builder.location().setCanSeeSky(true))
                                    ),
                                    LootItemEntityPropertyCondition.hasProperties(
                                            LootContext.EntityTarget.DIRECT_ATTACKER, EntityPredicate.Builder.entity().of(EntityType.ARROW)
                                    )
                            )
                    )
                    .withEffect(
                            EnchantmentEffectComponents.HIT_BLOCK,
                            AllOf.entityEffects(
                                    new SummonEntityEffect(HolderSet.direct(EntityType.LIGHTNING_BOLT.builtInRegistryHolder()), false),
                                    new PlaySoundEffect(SoundEvents.TRIDENT_THUNDER, ConstantFloat.of(5.0F), ConstantFloat.of(1.0F))
                            ),
                            AllOfCondition.allOf(
                                    WeatherCheck.weather().setThundering(true),
                                    LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(EntityType.ARROW)),
                                    LocationCheck.checkLocation(LocationPredicate.Builder.location().setCanSeeSky(true)),
                                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.LIGHTNING_ROD)
                            )
                    );
        }
    }
}
