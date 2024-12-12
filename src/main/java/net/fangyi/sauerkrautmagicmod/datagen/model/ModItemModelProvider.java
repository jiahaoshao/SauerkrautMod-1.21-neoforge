package net.fangyi.sauerkrautmagicmod.datagen.model;

import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.block.ModBlocks;
import net.fangyi.sauerkrautmagicmod.entity.ModEntityTypes;
import net.fangyi.sauerkrautmagicmod.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import twilightforest.init.TFEntities;

import java.util.*;

import static net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod.prefix;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SauerkrautMagicMod.MODID, existingFileHelper);
    }

    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    @Override
    protected void registerModels() {
        for (DeferredHolder<Item, ?> item : ModEntityTypes.SPAWN_EGGS.getEntries()) {
            if (item.get() instanceof SpawnEggItem) {
                getBuilder(item.getId().getPath()).parent(getExistingFile(ResourceLocation.withDefaultNamespace("item/template_spawn_egg")));
            }
        }

        this.basicItem(ModItems.RUBY.get());
        this.basicItem(ModItems.RUBY_APPLE.get());
        //this.handheldItem(ModItems.RUBY_SWORD.get());
        this.handheldItem(ModItems.RUBY_PICKAXE.get());
        this.trimmedArmorItem(ModItems.RUBY_HELMET);
        this.trimmedArmorItem(ModItems.RUBY_CHESTPLATE);
        this.trimmedArmorItem(ModItems.RUBY_LEGGINGS);
        this.trimmedArmorItem(ModItems.RUBY_BOOTS);
        this.magicIngotModel(getResourceLocation(ModItems.MAGIC_INGOT.get()));

        ModelFile icePulling0 = bowItem("ice_bow_pulling_0", prefix("item/ice_bow_solid_pulling_0"), prefix("item/ice_bow_clear_pulling_0"));
        ModelFile icePulling1 = bowItem("ice_bow_pulling_1", prefix("item/ice_bow_solid_pulling_1"), prefix("item/ice_bow_clear_pulling_1"));
        ModelFile icePulling2 = bowItem("ice_bow_pulling_2", prefix("item/ice_bow_solid_pulling_2"), prefix("item/ice_bow_clear_pulling_2"));
        iceBowTex(icePulling0, icePulling1, icePulling2);

        List<Item> TP_SWORD = new ArrayList<>();
        TP_SWORD.add(ModItems.RUBY_SWORD.get());
        TP_SWORD.add(Items.WOODEN_SWORD.asItem());
        TP_SWORD.add(Items.STONE_SWORD.asItem());
        TP_SWORD.add(Items.IRON_SWORD.asItem());
        TP_SWORD.add(Items.GOLDEN_SWORD.asItem());
        TP_SWORD.add(Items.DIAMOND_SWORD.asItem());
        TP_SWORD.add(Items.NETHERITE_SWORD.asItem());

        TP_SWORD.forEach(this::TPSwordItem);

    }

    private void TPSwordItem(Item item){
        String name = getResourceLocation(item).toString();
        ResourceLocation layer = ResourceLocation.fromNamespaceAndPath(getResourceLocation(item).getNamespace(), "item/" + getResourceLocation(item).getPath());
        ItemModelBuilder builder = withExistingParent(name + "_can_tp", "item/handheld");
        builder = builder.texture("layer0", layer);
        builder = builder.texture("layer1", prefix("item/sword_can_tp"));

        this.getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", layer)
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(SauerkrautMagicMod.MODID, "can_tp"), 1)
                .model(builder)
                .end();
    }

    public ResourceLocation getResourceLocation(Item item) {
        return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item));
    }

    public void magicIngotModel(ResourceLocation item) {
        this.getBuilder(item.getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.withDefaultNamespace("item/iron_ingot"))
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(SauerkrautMagicMod.MODID, "size"), 16)
                .model(new ModelFile.UncheckedModelFile("item/gold_ingot"))
                .end();
    }



    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(DeferredItem<ArmorItem> itemDeferredItem) {
        final String MOD_ID = SauerkrautMagicMod.MODID; // Change this to your mod id

        if(itemDeferredItem.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemDeferredItem.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace()  + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(MOD_ID,
                                        "item/" + itemDeferredItem.getId().getPath()));
            });
        }
    }




    private ItemModelBuilder bowItem(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/bow");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private void iceBowTex(ModelFile pull0, ModelFile pull1, ModelFile pull2) {
        bowItem(ModItems.ICE_BOW.getId().getPath(), prefix("item/ice_bow_solid"), prefix("item/ice_bow_clear"))
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).model(pull0).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).predicate(ResourceLocation.withDefaultNamespace("pull"), (float) 0.65).model(pull1).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).predicate(ResourceLocation.withDefaultNamespace("pull"), (float) 0.9).model(pull2).end();
    }
}
