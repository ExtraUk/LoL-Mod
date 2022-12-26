package com.extra.leaguecraft.entity.model;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.entity.custom.BlitzcrankEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlitzcrankModel extends AnimatedGeoModel<BlitzcrankEntity> {

	@Override
	public ResourceLocation getModelLocation(BlitzcrankEntity object) {
		return new ResourceLocation(LeagueCraft.MOD_ID, "geo/blitzcrank.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BlitzcrankEntity object) {
		return new ResourceLocation(LeagueCraft.MOD_ID, "textures/entity/blitzcrank.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(BlitzcrankEntity animatable) {
		return new ResourceLocation(LeagueCraft.MOD_ID, "animations/blitzcrank.animation.json");
	}
}