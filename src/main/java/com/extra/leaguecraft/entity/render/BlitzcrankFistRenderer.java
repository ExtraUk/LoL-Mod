package com.extra.leaguecraft.entity.render;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.entity.custom.BlitzcrankFistEntity;
import com.extra.leaguecraft.entity.custom.BrackernEntity;
import com.extra.leaguecraft.entity.model.BlitzcrankFistModel;
import com.extra.leaguecraft.entity.model.BrackernModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class BlitzcrankFistRenderer extends EntityRenderer<BlitzcrankFistEntity> {
    protected static final ResourceLocation TEXTURE =
            new ResourceLocation(LeagueCraft.MOD_ID, "textures/entity/blitzcrank_fist.png");

    public BlitzcrankFistRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(BlitzcrankFistEntity entity) {
        return TEXTURE;
    }

    public void render(BlitzcrankFistEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        BlitzcrankFistModel blitzFist = new BlitzcrankFistModel();
        matrixStackIn.push();
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        float f = MathHelper.rotLerp(entityIn.prevRotationYaw, entityIn.rotationYaw, partialTicks);
        float f1 = MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(blitzFist.getRenderType(getEntityTexture(entityIn)));
        blitzFist.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
