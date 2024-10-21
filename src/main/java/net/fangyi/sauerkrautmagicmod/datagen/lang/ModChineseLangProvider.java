package net.fangyi.sauerkrautmagicmod.datagen.lang;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.block.ModBlocks;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModChineseLangProvider extends LanguageProvider {
    public ModChineseLangProvider(PackOutput output, String locale) {
        super(output, SauerkrautMagicMod.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        this.add("itemgroup.sauerkrautmagicmod.sauerkraut_magic_mod_tab", "酸菜的魔法世界");

        this.add(ModItems.RUBY.get(),"鲁比");
        this.add(ModItems.MAGIC_INGOT.get(), "魔术锭");
        this.add(ModItems.RUBY_APPLE.get(), "鲁比苹果");
        this.add(ModItems.RUBY_SWORD.get(), "鲁比🗡");
        this.add(ModItems.RUBY_PICKAXE.get(), "鲁比⛏");
        this.add(ModItems.RUBY_HELMET.get(), "鲁比头盔");
        this.add(ModItems.RUBY_CHESTPLATE.get(), "鲁比胸甲");
        this.add(ModItems.RUBY_LEGGINGS.get(), "鲁比护腿");
        this.add(ModItems.RUBY_BOOTS.get(), "鲁比靴子");

        this.add(ModBlocks.RUBY_BLOCK.get(), "鲁比方块");
        this.add(ModBlocks.LAMP_BLOCK.get(), "锆石灯笼");
    }
}
