package net.fangyi.sauerkrautmagicmod.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fangyi.sauerkrautmagicmod.SauerkrautMagicMod;
import net.fangyi.sauerkrautmagicmod.entity.custom.FlyingSwordEntity;
import net.fangyi.sauerkrautmagicmod.entity.model.FlyingSwordModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;

public class FlyingSwordEntityRenderer extends EntityRenderer<FlyingSwordEntity> {
    private EntityModel<FlyingSwordEntity> flyingSwordModel;

    public FlyingSwordEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        flyingSwordModel = new FlyingSwordModel(pContext.bakeLayer(FlyingSwordModel.LAYER_LOCATION));
    }


    @Override
    public ResourceLocation getTextureLocation(FlyingSwordEntity pEntity) {
        return SauerkrautMagicMod.prefix( "textures/entity/flying_sword.png");
    }

    @Override
    public void render(FlyingSwordEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
        //渲染应该在push和pop之间，避免污染其他的渲染
        pPoseStack.pushPose();
        //绕y轴旋转45度
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pEntityYaw - 45.0F));
        //向下移动一格
        pPoseStack.translate(0,-1.29,0);
        //构建顶点
        VertexConsumer buffer = pBuffer.getBuffer(this.flyingSwordModel.renderType(this.getTextureLocation(pEntity)));
        //调用模型的render方法进行渲染，这里的OverlayTexture下有很多类型，自己选用
        this.flyingSwordModel.renderToBuffer(pPoseStack,buffer,pPackedLight, OverlayTexture.NO_OVERLAY);
        pPoseStack.popPose();

    }
}
