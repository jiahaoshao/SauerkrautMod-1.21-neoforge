package net.fangyi.sauerkrautmagicmod.item;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.item.custom.*;
import net.fangyi.sauerkrautmagicmod.item.custom.tool.ModArmorMaterials;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModItems {
    public static final List<DeferredItem<Item>> ITEMS_SUPPLIER = new ArrayList<>();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SauerkrautMagicMod.MODID);

    public static final DeferredItem<Item> RUBY = register("ruby", RubyItem::new);
    public static final DeferredItem<Item> MAGIC_INGOT = register("magic_ingot", MagicIngotItem::new);
    public static final DeferredItem<Item> RUBY_APPLE = register("ruby_apple", RubyAppleItem::new);
    public static final DeferredItem<RubySwordItem> RUBY_SWORD = register("ruby_sword", RubySwordItem::new);
    public static final DeferredItem<RubyPickaxeItem> RUBY_PICKAXE = register("ruby_pickaxe", RubyPickaxeItem::new);
    public static final DeferredItem<ArmorItem> RUBY_HELMET = register("ruby_helmet", () -> new ArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.HELMET, (new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(40)))));
    public static final DeferredItem<ArmorItem> RUBY_CHESTPLATE = register("ruby_chestplate", () -> new ArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.CHESTPLATE, (new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(40)))));
    public static final DeferredItem<ArmorItem> RUBY_LEGGINGS = register("ruby_leggings", () -> new ArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.LEGGINGS, (new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(40)))));
    public static final DeferredItem<ArmorItem> RUBY_BOOTS = register("ruby_boots", () -> new ArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.BOOTS, (new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(40)))));

    public static  <T extends Item> DeferredItem<T> register(String name, Supplier<T> Item){
        DeferredItem<T> item =  ITEMS.register(name,Item);
        ITEMS_SUPPLIER.add((DeferredItem<Item>) item);
        return item;
    }


    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
