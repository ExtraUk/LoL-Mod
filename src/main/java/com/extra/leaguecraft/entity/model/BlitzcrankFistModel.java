package com.extra.leaguecraft.entity.model;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.extra.leaguecraft.entity.custom.BlitzcrankFistEntity;
import com.extra.leaguecraft.entity.custom.BrackernEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BlitzcrankFistModel <T extends BlitzcrankFistEntity> extends EntityModel<T> {
	private final ModelRenderer main;
	private final ModelRenderer rFist;
	private final ModelRenderer cube_r14_r1;
	private final ModelRenderer cube_r14_r2;
	private final ModelRenderer cube_r14_r3;
	private final ModelRenderer cube_r14_r4;
	private final ModelRenderer cube_r14;

	public BlitzcrankFistModel() {
		textureWidth = 64;
		textureHeight = 64;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		rFist = new ModelRenderer(this);
		rFist.setRotationPoint(-26.0F, -8.0F, 30.0F);
		main.addChild(rFist);
		setRotationAngle(rFist, 0.0F, 0.0F, 1.5708F);
		rFist.setTextureOffset(0, 8).addBox(-4.0001F, -32.0F, -38.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		rFist.setTextureOffset(0, 4).addBox(-4.0001F, -27.0F, -38.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		rFist.setTextureOffset(0, 0).addBox(-4.0001F, -22.0F, -38.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);

		cube_r14_r1 = new ModelRenderer(this);
		cube_r14_r1.setRotationPoint(1.0F, -26.0F, -37.0F);
		rFist.addChild(cube_r14_r1);
		setRotationAngle(cube_r14_r1, -3.1416F, 1.2654F, 3.0107F);
		cube_r14_r1.setTextureOffset(24, 32).addBox(-0.5829F, 4.0F, -1.2111F, 9.0F, 3.0F, 3.0F, 0.0F, false);

		cube_r14_r2 = new ModelRenderer(this);
		cube_r14_r2.setRotationPoint(1.0F, -26.0F, -37.0F);
		rFist.addChild(cube_r14_r2);
		setRotationAngle(cube_r14_r2, -3.1416F, 1.2654F, -3.1416F);
		cube_r14_r2.setTextureOffset(0, 38).addBox(-0.5829F, -1.0F, -1.2111F, 9.0F, 3.0F, 3.0F, 0.0F, false);

		cube_r14_r3 = new ModelRenderer(this);
		cube_r14_r3.setRotationPoint(4.0F, -36.0F, -30.0F);
		rFist.addChild(cube_r14_r3);
		setRotationAngle(cube_r14_r3, -1.5708F, 0.4363F, -1.3963F);
		cube_r14_r3.setTextureOffset(0, 32).addBox(-0.9131F, -0.0388F, -1.0F, 9.0F, 3.0F, 3.0F, 0.0F, false);

		cube_r14_r4 = new ModelRenderer(this);
		cube_r14_r4.setRotationPoint(1.0F, -26.0F, -37.0F);
		rFist.addChild(cube_r14_r4);
		setRotationAngle(cube_r14_r4, -3.1416F, 1.2654F, -3.0107F);
		cube_r14_r4.setTextureOffset(24, 38).addBox(-0.5829F, -6.0F, -1.2111F, 9.0F, 3.0F, 3.0F, 0.0F, false);

		cube_r14 = new ModelRenderer(this);
		cube_r14.setRotationPoint(-18.0F, -24.0F, -12.0F);
		rFist.addChild(cube_r14);
		setRotationAngle(cube_r14, 0.0F, -1.5708F, 0.0F);
		cube_r14.setTextureOffset(0, 0).addBox(-25.0001F, -11.0F, -26.0F, 14.0F, 18.0F, 14.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}