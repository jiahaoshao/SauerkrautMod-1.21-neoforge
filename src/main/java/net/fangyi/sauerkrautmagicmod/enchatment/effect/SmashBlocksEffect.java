package net.fangyi.sauerkrautmagicmod.enchatment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockEvent;


import java.util.Optional;

public record SmashBlocksEffect(LevelBasedValue maxSmash, LevelBasedValue radius, Optional<HolderSet<Block>> immuneBlocks, Optional<HolderSet<Block>> vulnerableBlocks, Optional<Holder<SoundEvent>> smashSound) implements EnchantmentEntityEffect {

    public static final MapCodec<SmashBlocksEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    LevelBasedValue.CODEC.fieldOf("max_smash").forGetter(SmashBlocksEffect::maxSmash),
                    LevelBasedValue.CODEC.fieldOf("radius").forGetter(SmashBlocksEffect::radius),
                    RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("immune_blocks").forGetter(SmashBlocksEffect::immuneBlocks),
                    RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("vulnerable_blocks").forGetter(SmashBlocksEffect::vulnerableBlocks),
                    SoundEvent.CODEC.optionalFieldOf("smash_sound").forGetter(SmashBlocksEffect::smashSound))
            .apply(instance, SmashBlocksEffect::new));

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 position) {
//        if (item.owner() instanceof ServerPlayer player) {
//            int blocksSmashed = entity.getData(TFDataAttachments.SMASH_BLOCKS).getBlocksSmashed();
//            int maxSmash = Math.round(this.maxSmash.calculate(enchantmentLevel));
//            if (blocksSmashed >= maxSmash) return;
//            BlockPos start = BlockPos.containing(position);
//            int radius = Math.round(this.radius.calculate(enchantmentLevel));
//
//            for (BlockPos pos : BlockPos.betweenClosed(start.offset(-radius, 0, -radius), start.offset(radius, 0, radius))) {
//                if (blocksSmashed >= maxSmash) break;
//                BlockState state = level.getBlockState(pos);
//                if (!state.isAir()) {
//                    if (this.immuneBlocks().isPresent() && this.immuneBlocks().get().contains(state.getBlockHolder())) continue;
//                    if (ChainBlock.canBreakBlockAt(level, pos, state, item.itemStack(), player.gameMode.getGameModeForPlayer().isBlockPlacingRestricted()) && state.canEntityDestroy(level, pos, player)) {
//                        if (!NeoForge.EVENT_BUS.post(new BlockEvent.BreakEvent(level, pos, state, player)).isCanceled()) {
//                            level.destroyBlock(pos, true);
//                            if (!player.isCreative()) state.getBlock().playerDestroy(level, player, pos, state, level.getBlockEntity(pos), item.itemStack());
//                            if (this.smashSound().isPresent()) {
//                                level.playSound(null, pos, this.smashSound().get().value(), SoundSource.BLOCKS, 1.0F, 1.0F);
//                            }
//                            blocksSmashed++;
//                        }
//                    }
//                }
//            }
//
//            entity.getData(TFDataAttachments.SMASH_BLOCKS).setBlocksSmashed(blocksSmashed);
//        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}