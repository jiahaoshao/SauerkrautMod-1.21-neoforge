package net.fangyi.sauerkrautmagicmod.level;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.fml.common.Mod;

import java.util.Stack;

public class ModLevelSaveData extends SavedData {
    public static final String NAME = "sauerkrautmagicmod_level_save_data";
    private final Stack<ItemStack> itemStacks = new Stack<>();

    public static ModLevelSaveData create() {
        return new ModLevelSaveData();
    }

    public void putItemStack(ItemStack itemstack) {
        itemStacks.push(itemstack);
        setDirty();
    }

    public ItemStack getItemStack() {
        if (itemStacks.isEmpty()) {
            return ItemStack.EMPTY;
        }
        setDirty();
        return itemStacks.pop();
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag listTag = new ListTag();
        itemStacks.forEach((itemstack) -> {
            CompoundTag compoundTag = new CompoundTag();
           listTag.add(itemstack.save(registries, compoundTag));
        });
        tag.put("list", listTag);
        return tag;
    }

    public ModLevelSaveData load(CompoundTag nbt, HolderLookup.Provider registries) {
        ListTag listNBT = (ListTag) nbt.get("list");
        if (listNBT != null) {
            for (Tag value : listNBT) {
                CompoundTag tag = (CompoundTag) value;
                ItemStack itemStack = ItemStack.parse(registries,tag).orElse(ItemStack.EMPTY);
                if (itemStack.equals(ItemStack.EMPTY)){
                    continue;
                }
                itemStacks.push(itemStack);
            }
        }
        // Load saved data
        return this;
    }

    private static ModLevelSaveData decode(CompoundTag tag,HolderLookup.Provider pRegistries){
        return ModLevelSaveData.create().load(tag,pRegistries);
    }

    public static ModLevelSaveData get(Level worldIn) {
        if (!(worldIn instanceof ServerLevel)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }
        ServerLevel world = worldIn.getServer().getLevel(ServerLevel.OVERWORLD);
        DimensionDataStorage dataStorage = world.getDataStorage();
        return dataStorage.computeIfAbsent(new Factory<ModLevelSaveData>(ModLevelSaveData::create, ModLevelSaveData::decode), ModLevelSaveData.NAME);
    }
}
