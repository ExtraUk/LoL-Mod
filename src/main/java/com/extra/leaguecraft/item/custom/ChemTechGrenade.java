package com.extra.leaguecraft.item.custom;

import com.extra.leaguecraft.entity.custom.ChemTechGrenadeEntity;
import com.extra.leaguecraft.util.ModSoundEvents;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SnowballItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ChemTechGrenade extends SnowballItem {
    public ChemTechGrenade(Properties properties) {
        super(properties);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (itemstack.getOrCreateTag().getBoolean("charged")) {
            worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), ModSoundEvents.CHEMTECH_GRENADE.get(), SoundCategory.NEUTRAL, 0.5F, 1.0F);
            if (!worldIn.isRemote) {
                ChemTechGrenadeEntity grenadeEntity = new ChemTechGrenadeEntity(worldIn, playerIn);
                grenadeEntity.setItem(itemstack);
                grenadeEntity.setDirectionAndMovement(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.0F, 1.0F);
                worldIn.addEntity(grenadeEntity);
                playerIn.inventory.decrStackSize(playerIn.inventory.getSlotFor(itemstack), 1);
            }
        }

        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(stack.getOrCreateTag().getBoolean("charged")){
            tooltip.add(new StringTextComponent("[CHARGED]"));
        }
        else{
            tooltip.add(new StringTextComponent("[EMPTY]"));
        }
    }
}
