package net.fangyi.sauerkrautmagicmod.entity.custom;

import com.google.common.collect.Lists;
import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.event.client.KeyBoardInput;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class FlyingSwordEntity extends VehicleEntity implements Leashable {
    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYRot;
    private double lerpXRot;
    private boolean inputLeft;
    private boolean inputRight;
    private boolean inputUp;
    private boolean inputDown;
    private boolean inputJumping;
    private boolean inputShiftKeyDown;
    private float deltaRotation;
    private float invFriction;
    private FlyingSwordEntity.Status status;
    private FlyingSwordEntity.Status oldStatus;
    private double waterLevel;
    private float bubbleAngle;
    private float bubbleAngleO;
    @javax.annotation.Nullable
    private Leashable.LeashData leashData;
    private float playerSpeed =  0.9f;
    private static final EntityDataAccessor<ItemStack> SWORD_ITEM = SynchedEntityData.defineId(FlyingSwordEntity.class, EntityDataSerializers.ITEM_STACK);

    public FlyingSwordEntity(EntityType<? extends FlyingSwordEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.blocksBuilding = true;
    }

    @Override
    public void tick() {
        if(this.level().isClientSide){
            this.updateControl();
        }

        if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
        }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 0.5F);
        }

        //控制移动逻辑
        if (this.isControlledByLocalInstance()) {
            this.floatFlyingSword();
            if (this.level().isClientSide) {
                this.controlSword();
            }
            this.move(MoverType.SELF, this.getDeltaMovement());
        } else {
            this.setDeltaMovement(Vec3.ZERO);
        }

        super.tick();
        this.tickLerp();
        this.checkInsideBlocks();
    }


    private void controlSword() {
        if (this.isVehicle()) {
            float f = 0.0F;
            float g = 0.0F;
            if (this.inputLeft) {
                this.deltaRotation--;
            }

            if (this.inputRight) {
                this.deltaRotation++;
            }

            if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
                f += 0.005F;
            }

            this.setYRot(this.getYRot() + this.deltaRotation);

            if (this.inputUp) {
                f += 0.04F * playerSpeed * 2;
            }

            if (this.inputDown) {
                f -= 0.01F * playerSpeed * 4;
            }

            Vec3 vec3 = this.getDeltaMovement()
                    .add(
                            (double)(Mth.sin(-this.getYRot() * (float) (Math.PI / 180.0)) * f),
                            0,
                            (double)(Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)) * f)
                    );

            float currentY = (float) vec3.y;
            float maxYspeed = 0.3f*playerSpeed*2;
            float yacc = 0.05f*playerSpeed*1.5f;
            float ydec = 0.02f;

            if (currentY >0){
                if (inputJumping&&inputShiftKeyDown){
                }else if(inputJumping){
                    if(currentY+yacc>maxYspeed){
                        currentY = maxYspeed;
                    }else {
                        currentY += yacc;
                    }
                }else if (inputShiftKeyDown){
                    currentY -= yacc;
                }else {
                    currentY -= ydec;
                }
            }else {
                if (inputJumping&&inputShiftKeyDown){
                }else if(inputShiftKeyDown){
                    if(currentY-yacc<-maxYspeed){
                        currentY = -maxYspeed;
                    }else {
                        currentY -= yacc;
                    }
                }else if (inputJumping){
                    currentY += yacc;
                }else {
                    currentY += ydec;
                }
            }

            if(Math.abs(currentY)<=0.03){
                currentY = 0;
            }
            this.setDeltaMovement(vec3.x,currentY,vec3.z);
        }
    }

    public void setInput(boolean inputLeft, boolean inputRight, boolean inputUp, boolean inputDown, boolean inputJumping, boolean inputShiftKeyDown) {
//        this.inputLeft = inputLeft;
//        this.inputRight = inputRight;
//        this.inputUp = inputUp;
//        this.inputDown = inputDown;
//        this.inputJumping = inputJumping;
//        this.inputShiftKeyDown = inputShiftKeyDown;
    }

    @OnlyIn(Dist.CLIENT)
    private void updateControl(){
        if(!getPassengers().isEmpty()){

            assert Minecraft.getInstance().player != null;
            if (getPassengers().get(0).getUUID() == Minecraft.getInstance().player.getUUID()){
                inputUp = Minecraft.getInstance().options.keyUp.isDown();
                inputDown = Minecraft.getInstance().options.keyDown.isDown();
                inputLeft = Minecraft.getInstance().options.keyLeft.isDown();
                inputRight = Minecraft.getInstance().options.keyRight.isDown();
                inputJumping = KeyBoardInput.up;
                inputShiftKeyDown = KeyBoardInput.down;
                if (KeyBoardInput.DOWN_KEY.getKey().getValue() == GLFW.GLFW_KEY_LEFT_CONTROL && Minecraft.getInstance().options.keySprint.getKey().getValue() == GLFW.GLFW_KEY_LEFT_CONTROL){
                    inputShiftKeyDown = Minecraft.getInstance().options.keySprint.isDown();
                }

            }else {
                inputUp = inputDown = inputLeft = inputRight = inputJumping = inputShiftKeyDown =false;
            }
        }else {
            inputUp = inputDown = inputLeft = inputRight = inputJumping = inputShiftKeyDown =false;
        }
    }



    @Override
    protected void positionRider(Entity passenger, Entity.MoveFunction callback) {
        super.positionRider(passenger, callback);
        if (!passenger.getType().is(EntityTypeTags.CAN_TURN_IN_BOATS)) {
            passenger.setYRot(passenger.getYRot() + this.deltaRotation);
            passenger.setYHeadRot(passenger.getYHeadRot() + this.deltaRotation);
            this.clampRotation(passenger);
            if (passenger instanceof Animal && this.getPassengers().size() == this.getMaxPassengers()) {
                int i = passenger.getId() % 2 == 0 ? 90 : 270;
                passenger.setYBodyRot(((Animal)passenger).yBodyRot + (float)i);
                passenger.setYHeadRot(passenger.getYHeadRot() + (float)i);
            }
        }
    }

    protected void clampRotation(Entity entityToUpdate) {
        entityToUpdate.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(entityToUpdate.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -105.0F, 105.0F);
        entityToUpdate.yRotO += f1 - f;
        entityToUpdate.setYRot(entityToUpdate.getYRot() + f1 - f);
        entityToUpdate.setYHeadRot(entityToUpdate.getYRot());
    }


    @Override
    public void onPassengerTurned(Entity entityToUpdate) {
        this.clampRotation(entityToUpdate);
    }

    protected void checkInsideBlocks() {
        AABB aabb = this.getBoundingBox();
        BlockPos blockpos = BlockPos.containing(aabb.minX + 1.0E-7, aabb.minY + 1.0E-7, aabb.minZ + 1.0E-7);
        BlockPos blockpos1 = BlockPos.containing(aabb.maxX - 1.0E-7, aabb.maxY - 1.0E-7, aabb.maxZ - 1.0E-7);
        if (this.level().hasChunksAt(blockpos, blockpos1)) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int i = blockpos.getX(); i <= blockpos1.getX(); i++) {
                for (int j = blockpos.getY(); j <= blockpos1.getY(); j++) {
                    for (int k = blockpos.getZ(); k <= blockpos1.getZ(); k++) {
                        if (!this.isAlive()) {
                            return;
                        }

                        blockpos$mutableblockpos.set(i, j, k);
                        BlockState blockstate = this.level().getBlockState(blockpos$mutableblockpos);

                        try {
                            blockstate.entityInside(this.level(), blockpos$mutableblockpos, this);
                            this.onInsideBlock(blockstate);
                        } catch (Throwable throwable) {
                            CrashReport crashreport = CrashReport.forThrowable(throwable, "Colliding entity with block");
                            CrashReportCategory crashreportcategory = crashreport.addCategory("Block being collided with");
                            CrashReportCategory.populateBlockDetails(crashreportcategory, this.level(), blockpos$mutableblockpos, blockstate);
                            throw new ReportedException(crashreport);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        InteractionResult interactionresult = super.interact(player, hand);
        if (interactionresult != InteractionResult.PASS) {
            return interactionresult;
        } else if (player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        } else {
            if (!this.level().isClientSide) {
                return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
            } else {
                return InteractionResult.SUCCESS;
            }
        }
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(this.getDropItem());
    }


    @Override
    public Item getDropItem() {
        return Items.DIAMOND_SWORD;
    }

    public void setItemStack(ItemStack itemstack) {
        this.getEntityData().set(SWORD_ITEM, itemstack);
        //SauerkrautMagicMod.LOGGER.info("setItem:" + this.getItem().toString());
    }

    public ItemStack getItemStack() {
        return this.getEntityData().get(SWORD_ITEM);
    }

    private ItemStack getDefualtItemStack() {
        return Items.DIAMOND_SWORD.getDefaultInstance();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(SWORD_ITEM, getDefualtItemStack());
        super.defineSynchedData(pBuilder);
    }

    //从NBT中读取数据
    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        ItemStack itemstack;
        if (pCompound.contains("SwordItem", 10)) {
            CompoundTag compoundtag = pCompound.getCompound("SwordItem");
            itemstack = ItemStack.parse(this.registryAccess(), compoundtag).orElse(getDefualtItemStack());
        } else {
            itemstack = getDefualtItemStack();
        }
        //SauerkrautMagicMod.LOGGER.info("readAdditionalSaveData1:" + this.getItem().toString());
        this.setItemStack(itemstack);
        //SauerkrautMagicMod.LOGGER.info("readAdditionalSaveData2:" + this.getItem().toString());
    }

    //将数据写入NBT
    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        //SauerkrautMagicMod.LOGGER.info("addAdditionalSaveData1:" + this.getItem().toString());
        if (!this.getItemStack().isEmpty()) {
            pCompound.put("SwordItem", this.getItemStack().save(this.registryAccess()));
            //SauerkrautMagicMod.LOGGER.info("addAdditionalSaveData2:" + this.getItem().toString());
        }
    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYRot = (double)yRot;
        this.lerpXRot = (double)xRot;
        this.lerpSteps = 10;
    }

    @Override
    public double lerpTargetX() {
        return this.lerpSteps > 0 ? this.lerpX : this.getX();
    }

    @Override
    public double lerpTargetY() {
        return this.lerpSteps > 0 ? this.lerpY : this.getY();
    }

    @Override
    public double lerpTargetZ() {
        return this.lerpSteps > 0 ? this.lerpZ : this.getZ();
    }

    @Override
    public float lerpTargetXRot() {
        return this.lerpSteps > 0 ? (float)this.lerpXRot : this.getXRot();
    }

    @Override
    public float lerpTargetYRot() {
        return this.lerpSteps > 0 ? (float)this.lerpYRot : this.getYRot();
    }

    private void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }

        if (this.lerpSteps > 0) {
            this.lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.lerpXRot);
            this.lerpSteps--;
        }
    }

    public float getBubbleAngle(float partialTicks) {
        return Mth.lerp(partialTicks, this.bubbleAngleO, this.bubbleAngle);
    }

    @Override
    public Direction getMotionDirection() {
        return this.getDirection().getClockWise();
    }

    @Override
    public void animateHurt(float yaw) {
        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() * 11.0F);
    }

    @Override
    public @Nullable LeashData getLeashData() {
        return this.leashData;
    }

    @Override
    public void setLeashData(@Nullable Leashable.LeashData leashData) {
        this.leashData = leashData;
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, (double)(0.88F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.64F));
    }

    @Override
    public void elasticRangeLeashBehaviour(Entity leashHolder, float distance) {
        Vec3 vec3 = leashHolder.position().subtract(this.position()).normalize().scale((double)distance - 6.0);
        Vec3 vec31 = this.getDeltaMovement();
        boolean flag = vec31.dot(vec3) > 0.0;
        this.setDeltaMovement(vec31.add(vec3.scale(flag ? 0.15F : 0.2F)));
    }



    protected int getMaxPassengers() {
        return 2;
    }

    public boolean hasEnoughSpaceFor(Entity entity) {
        return entity.getBbWidth() < this.getBbWidth();
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return this.getFirstPassenger() instanceof LivingEntity livingentity ? livingentity : super.getControllingPassenger();
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        if (!this.level().isClientSide && reason.shouldDestroy() && this.isLeashed()) {
            this.dropLeash(true, true);
        }

        super.remove(reason);
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
        Vec3 vec3 = getCollisionHorizontalEscapeVector((double)(this.getBbWidth() * Mth.SQRT_OF_TWO), (double)livingEntity.getBbWidth(), livingEntity.getYRot());
        double d0 = this.getX() + vec3.x;
        double d1 = this.getZ() + vec3.z;
        BlockPos blockpos = BlockPos.containing(d0, this.getBoundingBox().maxY, d1);
        BlockPos blockpos1 = blockpos.below();
        if (!this.level().isWaterAt(blockpos1)) {
            List<Vec3> list = Lists.newArrayList();
            double d2 = this.level().getBlockFloorHeight(blockpos);
            if (DismountHelper.isBlockFloorValid(d2)) {
                list.add(new Vec3(d0, (double)blockpos.getY() + d2, d1));
            }

            double d3 = this.level().getBlockFloorHeight(blockpos1);
            if (DismountHelper.isBlockFloorValid(d3)) {
                list.add(new Vec3(d0, (double)blockpos1.getY() + d3, d1));
            }

            for (Pose pose : livingEntity.getDismountPoses()) {
                for (Vec3 vec31 : list) {
                    if (DismountHelper.canDismountTo(this.level(), vec31, livingEntity, pose)) {
                        livingEntity.setPose(pose);
                        return vec31;
                    }
                }
            }
        }

        return super.getDismountLocationForPassenger(livingEntity);
    }

    public static enum Status {
        IN_WATER,
        UNDER_WATER,
        UNDER_FLOWING_WATER,
        ON_LAND,
        IN_AIR;
    }

    private void floatFlyingSword() {
        double d0 = -this.getGravity();//重力
            this.invFriction = 0.95F;//摩檫力
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(vec3.x * (double)this.invFriction, vec3.y + d0, vec3.z * (double)this.invFriction);
            this.deltaRotation = this.deltaRotation * 0.9f;
    }
}
