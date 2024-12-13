package net.fangyi.sauerkrautmagicmod.datagen.lang;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.block.ModBlocks;
import net.fangyi.sauerkrautmagicmod.entity.ModEntityTypes;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class ModChineseLangProvider extends LanguageProvider {
    public ModChineseLangProvider(PackOutput output, String locale) {
        super(output, SauerkrautMagicMod.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup.sauerkrautmagicmod.mod_tab", "酸菜的魔法世界");

        //items
        this.add(ModItems.RUBY.get(),"鲁比");
        this.add(ModItems.MAGIC_INGOT.get(), "魔术锭");
        this.add(ModItems.RUBY_APPLE.get(), "鲁比苹果");
        this.add(ModItems.RUBY_SWORD.get(), "鲁比🗡");
        this.add(ModItems.RUBY_PICKAXE.get(), "鲁比⛏");
        this.add(ModItems.RUBY_HELMET.get(), "鲁比头盔");
        this.add(ModItems.RUBY_CHESTPLATE.get(), "鲁比胸甲");
        this.add(ModItems.RUBY_LEGGINGS.get(), "鲁比护腿");
        this.add(ModItems.RUBY_BOOTS.get(), "鲁比靴子");
        this.add(ModItems.RUBY_WAND.get(), "鲁比棒");
        this.add(ModItems.ICE_BOW.get(), "冰弓");

        //blocks
        this.add(ModBlocks.RUBY_BLOCK.get(), "鲁比方块");
        this.add(ModBlocks.LAMP_BLOCK.get(), "锆石灯笼");
        this.add(ModBlocks.RUBY_FRAME.get(), "鲁比框架");
        this.add(ModBlocks.GLASS_JAR.get(), "玻璃罐子");
        this.add(ModBlocks.RUBY_ORE.get(), "鲁比矿石");
        this.add(ModBlocks.OBSIDIAN_OBJ.get(), "黑曜石塔");
        this.add(ModBlocks.DATA_SAVE_BLOCK.get(), "数据保存器");
        this.add(ModBlocks.SWORD_BLOCK.get(), "钻石剑");

        //enchantments
        this.addEnchantment("chill_aura", "凛寒气场", "当敌人攻击你的时候会受到类似雪怪首领盔甲的冰冻效果");
        this.addEnchantment("fire_react", "炽焰反制", "当敌人攻击你的时候会受到类似炽铁盔甲的着火效果");
        this.addEnchantment("rapid_shoot", "速射", "快速射击");
        this.addEnchantment("powerful", "一刀", "一刀");
        this.addEnchantment("one_tap", "颗秒", "颗秒");
        this.addEnchantment("can_tp", "末影刀", "末影刀");
        this.addEnchantment("auto_can_tp", "自动装填", "自动装填");

        //effects
        this.add("effect.sauerkrautmagicmod.frosted", "冻结");

        //entities
        this.addEntityAndEgg(ModEntityTypes.FLYING_SWORD, "飞行剑");
    }

    public void addEnchantment(String key, String title, String desc) {
        this.add("enchantment.sauerkrautmagicmod." + key, title);
        this.add("enchantment.sauerkrautmagicmod." + key + ".desc", desc);
    }

    public void addEntityType(Supplier<? extends EntityType<?>> key, String name) {
        this.add((EntityType)key.get(), name);
    }

    public void addEntityAndEgg(DeferredHolder<EntityType<?>, ? extends EntityType<?>> entity, String name) {
        this.addEntityType(entity, name);
        this.add("item.sauerkrautmagicmod." + entity.getId().getPath() + "_spawn_egg", name + "刷怪蛋");
    }
}
