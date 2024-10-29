//package net.fangyi.sauerkrautmagicmod.entity.projectile;
//
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.projectile.AbstractArrow;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.Level;
//import org.jetbrains.annotations.Nullable;
//
//public class ModArrow extends AbstractArrow {
//    protected ModArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
//        super(entityType, level);
//    }
//
//    public ModArrow(EntityType<? extends ModArrow> type, Level level, @Nullable LivingEntity shooter, ItemStack stack, ItemStack weapon) {
//        super(type, shooter, level, stack, weapon);
//        this.setOwner(shooter);
//        if (shooter != null) {
//            this.setPos(shooter.getX(), shooter.getEyeY() - 0.1D, shooter.getZ());
//        }
//    }
//
//    @Override
//    protected ItemStack getDefaultPickupItem() {
//        return new ItemStack(Items.ARROW);
//    }
//}
