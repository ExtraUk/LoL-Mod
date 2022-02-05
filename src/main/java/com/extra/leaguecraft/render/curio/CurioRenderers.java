package com.extra.leaguecraft.render.curio;

import com.extra.leaguecraft.item.ModItems;
import com.extra.leaguecraft.render.curio.model.HandsModel;
import com.extra.leaguecraft.render.curio.renderer.CurioRenderer;
import com.extra.leaguecraft.render.curio.renderer.GloveCurioRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class CurioRenderers {

    private static final Map<Item, CurioRenderer> renderers = new HashMap<>();

    public static CurioRenderer getRenderer(Item curio) {
        return renderers.get(curio);
    }

    @Nullable
    public static GloveCurioRenderer getGloveRenderer(ItemStack stack) {
        if (!stack.isEmpty()) {
            CurioRenderer renderer = getRenderer(stack.getItem());
            if (renderer instanceof GloveCurioRenderer) {
                return ((GloveCurioRenderer) renderer);
            }
        }
        return null;
    }

    public static void setupCurioRenderers() {

        renderers.put(ModItems.HEXTECH_GAUNTLET.get(), new GloveCurioRenderer("hextech_gauntlet"));
        renderers.put(ModItems.IRON_GAUNTLET.get(), new GloveCurioRenderer("iron_gauntlet"));
    }
}
