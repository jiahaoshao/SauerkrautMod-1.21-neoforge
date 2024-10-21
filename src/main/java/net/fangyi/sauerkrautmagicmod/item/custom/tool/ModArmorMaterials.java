package net.fangyi.sauerkrautmagicmod.item.custom.tool;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials  {

    public static final Holder<ArmorMaterial> RUBY = register(
            "ruby",
            Util.make(new EnumMap<>(ArmorItem.Type.class), typeObjectEnumMap -> {
                typeObjectEnumMap.put(ArmorItem.Type.BOOTS,4);
                typeObjectEnumMap.put(ArmorItem.Type.CHESTPLATE,7);
                typeObjectEnumMap.put(ArmorItem.Type.LEGGINGS,9);
                typeObjectEnumMap.put(ArmorItem.Type.HELMET,4);
                typeObjectEnumMap.put(ArmorItem.Type.BODY,3);
            }),
            16,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            4F,
            0.2F,
            ()-> Ingredient.of(ModItems.RUBY.get()),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(SauerkrautMagicMod.MODID,"ruby"),"",true),
                    new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(SauerkrautMagicMod.MODID,"ruby"),"_overlay",false))
    );

    private static Holder<ArmorMaterial> register(
            String name,
            EnumMap<ArmorItem.Type, Integer> defense,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngredient
    ) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(SauerkrautMagicMod.MODID, name)));
        return register(name, defense, enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient, list);
    }

    private static Holder<ArmorMaterial> register(
            String name,
            EnumMap<ArmorItem.Type, Integer> defense,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngridient,
            List<ArmorMaterial.Layer> layers
    ) {
        EnumMap<ArmorItem.Type, Integer> enummap = new EnumMap<>(ArmorItem.Type.class);

        for (ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
            enummap.put(armoritem$type, defense.get(armoritem$type));
        }

        return Registry.registerForHolder(
                BuiltInRegistries.ARMOR_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(SauerkrautMagicMod.MODID, name),
                new ArmorMaterial(enummap, enchantmentValue, equipSound, repairIngridient, layers, toughness, knockbackResistance)
        );
    }
}
