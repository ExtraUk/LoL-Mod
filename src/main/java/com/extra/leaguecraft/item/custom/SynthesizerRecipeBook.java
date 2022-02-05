package com.extra.leaguecraft.item.custom;

import com.extra.leaguecraft.screen.SynthesizerRecipeBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class SynthesizerRecipeBook extends Item {

    public SynthesizerRecipeBook(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(worldIn.isRemote) {
            Minecraft.getInstance().displayGuiScreen(new SynthesizerRecipeBookScreen(new StringTextComponent("")));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
