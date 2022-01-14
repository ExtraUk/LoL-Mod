package com.extra.leaguecraft.item.custom;

import com.extra.leaguecraft.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SilverBolt extends ArrowItem {

    public SilverBolt(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        for(int j=0; j< player.inventory.getSizeInventory(); j++) {
            if (player.inventory.getStackInSlot(j).getItem().equals(ModItems.MAGAZINE.get().getItem())) {
                for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                    if (player.inventory.getStackInSlot(i).getItem().equals(ModItems.MAGAZINE.get().getItem()) && player.inventory.getStackInSlot(i).isDamaged()) {
                        System.out.println("Cuack");
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

        if (nbt.contains("Silver"))
        {
            nbt.putInt("Silver", nbt.getInt("Silver")+1);
        }
        else
        {
            nbt.putInt("Silver", 1);
        }
        player.inventory.getStackInSlot(i).setTag(nbt);

    }
}
