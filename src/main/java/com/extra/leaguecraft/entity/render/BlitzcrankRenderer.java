package com.extra.leaguecraft.entity.render;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.entity.custom.BlitzcrankEntity;
import com.extra.leaguecraft.entity.model.BlitzcrankModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BlitzcrankRenderer extends GeoEntityRenderer<BlitzcrankEntity> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(LeagueCraft.MOD_ID, "textures/entity/blitzcrank.png");

    public BlitzcrankRenderer(EntityRendererManager renderManager) {
        super(renderManager, new BlitzcrankModel());
        this.shadowSize = 1.5f;
    }

    @Override
    public ResourceLocation getEntityTexture(BlitzcrankEntity entity) {
        return TEXTURE;
    }

    @Override
    public RenderType getRenderType(BlitzcrankEntity animatable, float partialTicks, MatrixStack stack,
                                    IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
                                    ResourceLocation textrueLocation){
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textrueLocation);
    }

    /*public BlitzcrankRenderer(EntityRendererManager rendererManagerIn){
        super(rendererManagerIn, new BlitzcrankModel<>(), 1.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(BlitzcrankEntity entity) {
        return TEXTURE;
    }*/
}
