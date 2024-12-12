package net.fangyi.sauerkrautmagicmod.entity.ai.behavior;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.fangyi.sauerkrautmagicmod.entity.ai.behavior.SonicBoomHandler.SonicBoom;

public class SonicBoomHandler {

    public static LivingEntity getTarget(ServerLevel level, LivingEntity player) {
        Vec3 vec3 = player.position().add(player.getAttachments().get(EntityAttachment.WARDEN_CHEST, 0, player.getYRot()));
        Vec3 vec31 = player.getLookAngle().normalize().scale(32f).add(vec3);

            BlockHitResult blockHitResult = level.clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            vec31 = blockHitResult.getLocation();
            // 创建一个以玩家为中心，向玩家视线方向扩展的轴对齐包围盒（AABB）
            AABB range = player.getBoundingBox().expandTowards(vec31.subtract(vec3));
            List<HitResult> hits = new ArrayList<>();
            List<? extends Entity> entities = level.getEntities(player, range, entity -> entity.isPickable() && entity.isAlive());
            for (var e : entities) {
                Vec3 vec34 = e.getBoundingBox().clip(vec3, vec31).orElse(null);
                if (vec34 != null) {
                    EntityHitResult entityHitResult = new EntityHitResult(e, vec34);
                    hits.add(entityHitResult);
                }
            }

            if (!hits.isEmpty()) {
                hits.sort((o1, o2) -> o1.getLocation().distanceToSqr(vec3) < o2.getLocation().distanceToSqr(vec3) ? -1 : 1);
                HitResult hitResult = hits.getFirst();
                if (hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
                    return livingEntity;
                }
            }
            return null;
    }

    public static void SonicBoom(ServerLevel level, LivingEntity attacker, @Nullable LivingEntity target){
        Vec3 vec3 = attacker.position().add(attacker.getAttachments().get(EntityAttachment.WARDEN_CHEST, 0, attacker.getYRot()));
        if (target != null) {

            Vec3 vec31 = target.getEyePosition().subtract(vec3);
            Vec3 vec32 = vec31.normalize();
            int i = Mth.floor(vec31.length()) + 7;

            for (int j = 1; j < i; j++) {
                Vec3 vec33 = vec3.add(vec32.scale((double)j));
                level.sendParticles(ParticleTypes.SONIC_BOOM, vec33.x, vec33.y, vec33.z, 1, 0.0, 0.0, 0.0, 0.0);
            }

            attacker.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
            if (target.hurt(level.damageSources().sonicBoom(attacker), 10.0F)) {
                double d1 = 0.5 * (1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double d0 = 2.5 * (1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                target.push(vec32.x() * d0, vec32.y() * d1, vec32.z() * d0);
            }
        }
        else {
            int i = 32 + 7;
            for(int j = 1; j < i; j ++) {
                Vec3 vec32 = attacker.getLookAngle();
                Vec3 vec33 = vec3.add(vec32.scale((double)j));
                level.sendParticles(ParticleTypes.SONIC_BOOM, vec33.x, vec33.y, vec33.z, 1, 0.0, 0.0, 0.0, 0.0);
            }
            attacker.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
        }
    }
}
