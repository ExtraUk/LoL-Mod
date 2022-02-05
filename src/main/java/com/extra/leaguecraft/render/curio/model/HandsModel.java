package com.extra.leaguecraft.render.curio.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;

public class HandsModel extends BipedModel<LivingEntity> {

    protected HandsModel(int textureWidth, int textureHeight) {
        super(0, 0, textureWidth, textureHeight);
        setVisible(false);

        bipedLeftArm = new ModelRenderer(this);
        bipedRightArm = new ModelRenderer(this);
    }

    public void renderHand(HandSide handSide, MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bipedLeftArm.showModel = handSide == HandSide.LEFT;
        bipedRightArm.showModel = !bipedLeftArm.showModel;
        render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    private static HandsModel hands(int textureWidth, int textureHeight) {
        return new HandsModel(textureWidth, textureHeight);
    }


    public static HandsModel glove(boolean smallArms) {
        return glove(smallArms, 32, 32);
    }

    public static HandsModel glove(boolean smallArms, int textureWidth, int textureHeight) {
        HandsModel model = hands(textureWidth, textureHeight);

        model.bipedLeftArm.setRotationPoint(5, smallArms ? 2.5F : 2, 0);
        model.bipedRightArm.setRotationPoint(-5, smallArms ? 2.5F : 2, 0);

        // arms
        model.bipedLeftArm.setTextureOffset(0, 0);
        model.bipedLeftArm.addBox(-1, -2, -2, smallArms ? 3 : 4, 12, 4, 0.5F);
        model.bipedRightArm.setTextureOffset(16, 0);
        model.bipedRightArm.addBox(smallArms ? -2 : -3, -2, -2, smallArms ? 3 : 4, 12, 4, 0.5F);

        // sleeves
        model.bipedLeftArm.setTextureOffset(0, 16);
        model.bipedLeftArm.addBox(-1, -2, -2, smallArms ? 3 : 4, 12, 4, 0.5F + 0.25F);
        model.bipedRightArm.setTextureOffset(16, 16);
        model.bipedRightArm.addBox(smallArms ? -2 : -3, -2, -2, smallArms ? 3 : 4, 12, 4, 0.5F + 0.25F);

        return model;
    }
}