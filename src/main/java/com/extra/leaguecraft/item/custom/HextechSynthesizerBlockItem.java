package com.extra.leaguecraft.item.custom;

import com.extra.leaguecraft.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HextechSynthesizerBlockItem extends BlockItem {
    public HextechSynthesizerBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        playerIn.inventory.addItemStackToInventory(ModItems.SYNTHESIZER_RECIPE_BOOK.get().getDefaultInstance());
    }
}
