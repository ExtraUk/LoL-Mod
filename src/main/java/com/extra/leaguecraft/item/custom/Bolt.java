package com.extra.leaguecraft.item.custom;

import com.extra.leaguecraft.item.ModItems;
import net.minecraft.command.arguments.NBTTagArgument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class Bolt extends ArrowItem {

    public Bolt(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        for(int j=0; j< player.inventory.getSizeInventory(); j++) {
            if (player.inventory.getStackInSlot(j).getItem().equals(ModItems.MAGAZINE.get().getItem())) {
                for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                    if (player.inventory.getStackInSlot(i).getItem().equals(ModItems.MAGAZINE.get().getItem()) && player.inventory.getStackInSlot(i).isDamaged()) {
                        player.inventory.decrStackSize(player.inventory.getSlotFor(player.getHeldItem(hand)), 1);
                        player.inventory.getStackInSlot(i).setDamage(player.inventory.getStackInSlot(i).getDamage()-1);
                        loadBolt(player, i);
                        break;
                    }
                }
                break;
            }
        }
        return ActionResult.resultSuccess(player.getHeldItem(hand));
    }


    public void loadBolt(PlayerEntity player, int i){

        CompoundNBT nbt;
        ItemStack stack = player.inventory.getStackInSlot(i);
        if(stack.hasTag()){
            nbt = stack.getTag();
        }
        else{
            nbt = new CompoundNBT();
        }

        if (nbt.contains("Normal"))
        {
            nbt.putInt("Normal", nbt.getInt("Normal")+1);
        }
        else
        {
            nbt.putInt("Normal", 1);
        }
        player.inventory.getStackInSlot(i).setTag(nbt);

    }
}
