package com.extra.leaguecraft.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
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


}
