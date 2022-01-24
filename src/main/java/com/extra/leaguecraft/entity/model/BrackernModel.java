package com.extra.leaguecraft.entity.model;

import com.extra.leaguecraft.entity.custom.BrackernEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BrackernModel <T extends BrackernEntity> extends EntityModel<T> {
    private final ModelRenderer Legs;
    private final ModelRenderer RightLegs;
    private final ModelRenderer R3_r1;
    private final ModelRenderer LeftLegs;
    private final ModelRenderer L3_r1;
    private final ModelRenderer Claws;
    private final ModelRenderer LeftClaw;
    private final ModelRenderer Joint_r1;
    private final ModelRenderer RightClaw;
    private final ModelRenderer Joint_r2;
    private final ModelRenderer Tail;
    private final ModelRenderer TailPoint_r1;
    private final ModelRenderer Tail3_r1;
    private final ModelRenderer Tail2_r1;
    private final ModelRenderer Tail1_r1;
    private final ModelRenderer bb_main;

    public BrackernModel() {
        textureWidth = 128;
        textureHeight = 128;

        Legs = new ModelRenderer(this);
        Legs.setRotationPoint(0.0F, 24.0F, 0.0F);


        RightLegs = new ModelRenderer(this);
        RightLegs.setRotationPoint(0.0F, 0.0F, 0.0F);
        Legs.addChild(RightLegs);


        R3_r1 = new ModelRenderer(this);
        R3_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        RightLegs.addChild(R3_r1);
        setRotationAngle(R3_r1, 0.0F, 0.0F, 0.5236F);
        R3_r1.setTextureOffset(0, 21).addBox(-8.0F, -1.0F, -9.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
        R3_r1.setTextureOffset(20, 0).addBox(-8.0F, -1.0F, -13.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
        R3_r1.setTextureOffset(0, 0).addBox(-8.0F, -1.0F, -17.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

        LeftLegs = new ModelRenderer(this);
        LeftLegs.setRotationPoint(0.0F, 0.0F, 0.0F);
        Legs.addChild(LeftLegs);


        L3_r1 = new ModelRenderer(this);
        L3_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        LeftLegs.addChild(L3_r1);
        setRotationAngle(L3_r1, 0.0F, 0.0F, -0.5236F);
        L3_r1.setTextureOffset(53, 0).addBox(6.0F, -1.0F, -9.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
        L3_r1.setTextureOffset(53, 8).addBox(6.0F, -1.0F, -13.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
        L3_r1.setTextureOffset(61, 0).addBox(6.0F, -1.0F, -17.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

        Claws = new ModelRenderer(this);
        Claws.setRotationPoint(0.0F, 24.0F, 0.0F);


        LeftClaw = new ModelRenderer(this);
        LeftClaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        Claws.addChild(LeftClaw);
        LeftClaw.setTextureOffset(33, 46).addBox(13.0F, -13.0F, -24.0F, 8.0F, 6.0F, 17.0F, 0.0F, false);
        LeftClaw.setTextureOffset(33, 40).addBox(18.0F, -13.0F, -29.0F, 3.0F, 6.0F, 5.0F, 0.0F, false);
        LeftClaw.setTextureOffset(0, 40).addBox(13.0F, -13.0F, -29.0F, 3.0F, 6.0F, 5.0F, 0.0F, false);

        Joint_r1 = new ModelRenderer(this);
        Joint_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        LeftClaw.addChild(Joint_r1);
        setRotationAngle(Joint_r1, 0.0F, -0.9163F, 0.0F);
        Joint_r1.setTextureOffset(0, 63).addBox(4.0F, -11.0F, -21.0F, 3.0F, 3.0F, 19.0F, 0.0F, false);

        RightClaw = new ModelRenderer(this);
        RightClaw.setRotationPoint(0.0F, -19.0F, 0.0F);
        Claws.addChild(RightClaw);
        setRotationAngle(RightClaw, 0.0F, 0.0F, -3.1416F);
        RightClaw.setTextureOffset(0, 40).addBox(13.0F, -12.0F, -24.0F, 8.0F, 6.0F, 17.0F, 0.0F, false);
        RightClaw.setTextureOffset(11, 16).addBox(18.0F, -12.0F, -29.0F, 3.0F, 6.0F, 5.0F, 0.0F, false);
        RightClaw.setTextureOffset(0, 10).addBox(13.0F, -12.0F, -29.0F, 3.0F, 6.0F, 5.0F, 0.0F, false);

        Joint_r2 = new ModelRenderer(this);
        Joint_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
        RightClaw.addChild(Joint_r2);
        setRotationAngle(Joint_r2, 0.0F, -0.9163F, 0.0F);
        Joint_r2.setTextureOffset(53, 0).addBox(4.0F, -11.0F, -21.0F, 3.0F, 3.0F, 19.0F, 0.0F, false);

        Tail = new ModelRenderer(this);
        Tail.setRotationPoint(0.0F, 24.0F, 0.0F);


        TailPoint_r1 = new ModelRenderer(this);
        TailPoint_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        Tail.addChild(TailPoint_r1);
        setRotationAngle(TailPoint_r1, -2.4435F, 0.0F, 0.0F);
        TailPoint_r1.setTextureOffset(11, 10).addBox(-1.0F, 22.0F, -21.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
        TailPoint_r1.setTextureOffset(0, 0).addBox(-3.0F, 22.0F, -29.0F, 6.0F, 2.0F, 8.0F, 0.0F, false);

        Tail3_r1 = new ModelRenderer(this);
        Tail3_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        Tail.addChild(Tail3_r1);
        setRotationAngle(Tail3_r1, 2.5744F, 0.0F, 0.0F);
        Tail3_r1.setTextureOffset(73, 60).addBox(-3.0F, 33.0F, 4.0F, 6.0F, 2.0F, 10.0F, 0.0F, false);

        Tail2_r1 = new ModelRenderer(this);
        Tail2_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        Tail.addChild(Tail2_r1);
        setRotationAngle(Tail2_r1, 1.7453F, 0.0F, 0.0F);
        Tail2_r1.setTextureOffset(66, 42).addBox(-3.0F, 18.0F, 13.0F, 6.0F, 2.0F, 16.0F, 0.0F, false);

        Tail1_r1 = new ModelRenderer(this);
        Tail1_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        Tail.addChild(Tail1_r1);
        setRotationAngle(Tail1_r1, 0.7418F, 0.0F, 0.0F);
        Tail1_r1.setTextureOffset(66, 24).addBox(-3.0F, -2.0F, 8.0F, 6.0F, 2.0F, 16.0F, 0.0F, false);

        bb_main = new ModelRenderer(this);
        bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
        bb_main.setTextureOffset(0, 0).addBox(-6.0F, -15.0F, -23.0F, 12.0F, 11.0F, 29.0F, 0.0F, false);
        bb_main.setTextureOffset(44, 69).addBox(-4.0F, -13.0F, -31.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Legs.render(matrixStack, buffer, packedLight, packedOverlay);
        Claws.render(matrixStack, buffer, packedLight, packedOverlay);
        Tail.render(matrixStack, buffer, packedLight, packedOverlay);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
