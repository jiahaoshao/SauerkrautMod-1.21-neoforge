package net.fangyi.sauerkrautmagicmod.entity.model;// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.entity.custom.FlyingSwordEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class FlyingSwordModel extends EntityModel<FlyingSwordEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(SauerkrautMagicMod.prefix("flying_sword"), "main");
	private final ModelPart group;

	public FlyingSwordModel(ModelPart root) {
		this.group = root.getChild("group");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition group = partdefinition.addOrReplaceChild("group", CubeListBuilder.create().texOffs(0, 4).addBox(6.0F, 6.0F, 7.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(5.0F, 6.0F, 6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(6, 0).addBox(7.0F, 6.0F, 5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 3).addBox(6.0F, 6.0F, 6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 6).addBox(6.0F, 6.0F, 5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 7).addBox(4.0F, 6.0F, 5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 9).addBox(4.0F, 6.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 9).addBox(3.0F, 6.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(4.0F, 6.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 0).addBox(3.0F, 6.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 3).addBox(3.0F, 6.0F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 6).addBox(2.0F, 6.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 12).addBox(1.0F, 6.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 12).addBox(-2.0F, 6.0F, 5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 9).addBox(-1.0F, 6.0F, 5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 12).addBox(-3.0F, 6.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(-4.0F, 6.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 0).addBox(-4.0F, 6.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 3).addBox(-5.0F, 6.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 6).addBox(-4.0F, 6.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 15).addBox(-6.0F, 6.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 15).addBox(-5.0F, 6.0F, -6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 15).addBox(-6.0F, 6.0F, -6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-7.0F, 6.0F, -6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 9).addBox(-5.0F, 6.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 12).addBox(-6.0F, 6.0F, -7.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 15).addBox(-7.0F, 6.0F, -7.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 0).addBox(-3.0F, 6.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 3).addBox(-3.0F, 6.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 18).addBox(-2.0F, 6.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 6).addBox(-2.0F, 6.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 18).addBox(-1.0F, 6.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 18).addBox(-2.0F, 6.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 18).addBox(-1.0F, 6.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 19).addBox(0.0F, 6.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 9).addBox(-1.0F, 6.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 12).addBox(1.0F, 6.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 15).addBox(0.0F, 6.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 18).addBox(2.0F, 6.0F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 21).addBox(1.0F, 6.0F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 21).addBox(2.0F, 6.0F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 21).addBox(1.0F, 6.0F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 21).addBox(0.0F, 6.0F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 21).addBox(-1.0F, 6.0F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(0.0F, 6.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 0).addBox(1.0F, 6.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 3).addBox(2.0F, 6.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 6).addBox(3.0F, 6.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 24).addBox(5.0F, 6.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 24).addBox(-2.0F, 6.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 9).addBox(-5.0F, 6.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 24).addBox(-6.0F, 6.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 12).addBox(-6.0F, 6.0F, -8.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 15).addBox(-3.0F, 6.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 24).addBox(-2.0F, 6.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 18).addBox(0.0F, 6.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 24).addBox(4.0F, 6.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 21).addBox(5.0F, 6.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 24).addBox(-1.0F, 6.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 25).addBox(-5.0F, 6.0F, -7.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 0).addBox(-4.0F, 6.0F, -6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 3).addBox(-8.0F, 6.0F, -8.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 6).addBox(-7.0F, 6.0F, -8.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 27).addBox(-8.0F, 6.0F, -6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 27).addBox(-8.0F, 6.0F, -7.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 27).addBox(-3.0F, 6.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 27).addBox(-7.0F, 6.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 27).addBox(-4.0F, 6.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 27).addBox(1.0F, 6.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(0.0F, 6.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 9).addBox(-1.0F, 6.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 12).addBox(4.0F, 6.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 15).addBox(3.0F, 6.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 18).addBox(4.0F, 6.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 21).addBox(-1.0F, 6.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 24).addBox(4.0F, 6.0F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 27).addBox(3.0F, 6.0F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 0).addBox(0.0F, 6.0F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 3).addBox(-2.0F, 6.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 30).addBox(5.0F, 6.0F, 5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 6).addBox(5.0F, 6.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 30).addBox(7.0F, 6.0F, 6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		group.render(poseStack, buffer, packedLight, packedOverlay);
	}

	@Override
	public void setupAnim(FlyingSwordEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}
}