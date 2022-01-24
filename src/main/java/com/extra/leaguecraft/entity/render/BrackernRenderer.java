package com.extra.leaguecraft.entity.render;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.entity.custom.BrackernEntity;
import com.extra.leaguecraft.entity.model.BrackernModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BrackernRenderer extends MobRenderer<BrackernEntity, BrackernModel<BrackernEntity>> {

    protected static final ResourceLocation TEXTURE =
            new ResourceLocation(LeagueCraft.MOD_ID, "textures/entity/brackern.png");

    public BrackernRenderer(EntityRendererManager rendererManagerIn){
        super(rendererManagerIn, new BrackernModel<>(), 2.0f);
    }

    public ResourceLocation getEntityTexture(BrackernEntity entity) {
        return TEXTURE;
    }
}
