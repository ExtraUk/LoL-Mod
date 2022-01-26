package com.extra.leaguecraft.item.custom;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Magazine extends Item {

    public Magazine(Properties properties) {
        super(properties);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public void onCreated(ItemStack stack, World world, PlayerEntity player) {
        stack.setDamage(64);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        int n = 0;
        int s = 0;
        if(tooltip.size() == 1){
            tooltip.add(new StringTextComponent("Normal: " + 0));
            tooltip.add(new StringTextComponent("Silver: " + 0));
        }
        if(stack.hasTag()) {
            if(stack.getTag().contains("Normal")){
                n = stack.getTag().getInt("Normal");
            }
            if(stack.getTag().contains("Silver")){
                s = stack.getTag().getInt("Silver");
            }
            tooltip.set(1, new StringTextComponent("Normal: " + n));
            tooltip.set(2, new StringTextComponent("Silver: " + s));
        }
        else{
            tooltip.set(1,new StringTextComponent("Normal: " + 0));
            tooltip.set(2,new StringTextComponent("Silver: " + 0));
        }
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack toReturn = new ItemStack(this);
        toReturn.setDamage(this.getMaxDamage(new ItemStack(this)));
        return toReturn;
    }
}
