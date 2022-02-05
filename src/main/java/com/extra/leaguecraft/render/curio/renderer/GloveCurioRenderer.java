package com.extra.leaguecraft.render.curio.renderer;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.render.curio.model.HandsModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.function.Function;

public class GloveCurioRenderer implements CurioRenderer {

    private final ResourceLocation defaultTexture;
    private final ResourceLocation slimTexture;
    private final HandsModel defaultModel;
    private final HandsModel slimModel;

    public GloveCurioRenderer(String name) {
        this(String.format("glove/%s/%s_default", name, name), String.format("glove/%s/%s_slim", name, name), HandsModel::glove);
    }

    public GloveCurioRenderer(String name, Function<Boolean, HandsModel> modelFactory) {
        this(String.format("%s/%s_default", name, name), String.format("%s/%s_slim", name, name), modelFactory);
    }

    public GloveCurioRenderer(String defaultTexturePath, String slimTexturePath, Function<Boolean, HandsModel> modelFactory) {
        this.defaultTexture = new ResourceLocation(LeagueCraft.MOD_ID, String.format("textures/entity/curio/%s.png", defaultTexturePath));
        this.slimTexture = new ResourceLocation(LeagueCraft.MOD_ID, String.format("textures/entity/curio/%s.png", slimTexturePath));
        this.defaultModel = modelFactory.apply(false);
        this.slimModel = modelFactory.apply(true);
    }

    protected ResourceLocation getTexture(boolean hasSlimArms) {
        return hasSlimArms ? slimTexture : defaultTexture;
    }

    protected HandsModel getModel(boolean hasSlimArms) {
        return hasSlimArms ? slimModel : defaultModel;
    }

    protected static boolean hasSlimArms(Entity entity) {
        return entity instanceof AbstractClientPlayerEntity && ((AbstractClientPlayerEntity) entity).getSkinType().equals("slim");
    }

    @Override
    public final void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ticks, float headYaw, float headPitch, ItemStack stack) {
        boolean hasSlimArms = hasSlimArms(entity);
        HandsModel model = getModel(hasSlimArms);
        Hand hand = index % 2 == 0 ? Hand.MAIN_HAND : Hand.OFF_HAND;
        HandSide handSide = hand == Hand.MAIN_HAND ? entity.getPrimaryHand() : entity.getPrimaryHand().opposite();

        model.setRotationAngles(entity, limbSwing, limbSwingAmount, ticks, headYaw, headPitch);
        model.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        ICurio.RenderHelper.followBodyRotations(entity, model);

        renderArm(model, matrixStack, buffer, handSide, light, hasSlimArms, stack.hasEffect());
    }

    protected void renderArm(HandsModel model, MatrixStack matrixStack, IRenderTypeBuffer buffer, HandSide handSide, int light, boolean hasSlimArms, boolean hasFoil) {
        RenderType renderType = model.getRenderType(getTexture(hasSlimArms));
        IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(buffer, renderType, false, hasFoil);
        model.renderHand(handSide, matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    public final void renderFirstPersonArm(MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, AbstractClientPlayerEntity player, HandSide side, boolean hasFoil) {
        if (!player.isSpectator()) {
            boolean hasSlimArms = hasSlimArms(player);
            HandsModel model = getModel(hasSlimArms);

            ModelRenderer arm = side == HandSide.LEFT ? model.bipedLeftArm : model.bipedRightArm;

            model.setVisible(false);
            arm.showModel = true;

            model.isSneak = false;
            model.swingProgress = model.swimAnimation = 0;
            model.setRotationAngles(player, 0, 0, 0, 0, 0);
            arm.rotateAngleX = 0;

            renderFirstPersonArm(model, arm, matrixStack, buffer, light, hasSlimArms, hasFoil);
        }
    }

    protected void renderFirstPersonArm(HandsModel model, ModelRenderer arm, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, boolean hasSlimArms, boolean hasFoil) {
        RenderType renderType = model.getRenderType(getTexture(hasSlimArms));
        IVertexBuilder builder = ItemRenderer.getBuffer(buffer, renderType, false, hasFoil);
        arm.render(matrixStack, builder, light, OverlayTexture.NO_OVERLAY);
    }
}
