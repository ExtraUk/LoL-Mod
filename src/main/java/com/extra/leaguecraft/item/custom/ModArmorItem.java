package com.extra.leaguecraft.item.custom;

import com.extra.leaguecraft.effect.ModEffects;
import com.extra.leaguecraft.item.ModArmorMaterial;
import com.extra.leaguecraft.item.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;

public class ModArmorItem extends ArmorItem {

    public ModArmorItem(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
        super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
    }


    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if(!world.isRemote()){
            if(hasFullSuitOfArmorOn(player, this.material)){
                if(this.material.equals(ModArmorMaterial.TURBO_CHEMTANK) && stack.getItem().equals(ModItems.TURBO_CHEMTANK_CHEST.get())){
                    turboChemtankLogic(player);
                }
            }
        }
    }


    public static boolean hasFullSuitOfArmorOn(PlayerEntity player, IArmorMaterial material){
        int count = 0;
        for(ItemStack stack : player.getArmorInventoryList()){
            if(stack.getItem() instanceof ArmorItem){
                if(((ArmorItem) stack.getItem()).getArmorMaterial().equals(material)){
                    count++;
                }
            }
        }

        if(count == 4){
            return true;
        }
        else{
            return false;
        }
    }


    public void turboChemtankLogic(PlayerEntity player){

        if(!player.inventory.armorItemInSlot(2).getOrCreateTag().contains("active")){
            player.inventory.armorItemInSlot(2).getTag().putBoolean("active", true);
        }
        if(!player.inventory.armorItemInSlot(2).getTag().contains("shimmer_load")){
            player.inventory.armorItemInSlot(2).getTag().putInt("shimmer_load", 0);
        }

        boolean flag = true;

        if(player.getActivePotionEffect(ModEffects.SHIMMER.get()) != null){
            if(!(player.getActivePotionEffect(ModEffects.SHIMMER.get()).getDuration() % 10 == 0)){
                flag = false;
            }
        }

        if(player.inventory.armorItemInSlot(2).hasTag()){
            if(player.inventory.armorItemInSlot(2).getTag().getBoolean("active")){
                if(player.inventory.armorItemInSlot(2).getTag().getInt("shimmer_load") > 0){
                    if(flag) {
                        player.addPotionEffect(new EffectInstance(ModEffects.SHIMMER.get(), 60, 3));
                    }
                    player.inventory.armorItemInSlot(2).getTag().putInt("shimmer_load", player.inventory.armorItemInSlot(2).getTag().getInt("shimmer_load") -1);
                }
            }
        }
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if(stack.getItem().equals(ModItems.TURBO_CHEMTANK_CHEST.get())) {
            if(stack.getOrCreateTag().contains("shimmer_load")) {
                if (tooltip.size() == 1) {
                    tooltip.add(new StringTextComponent("Shimmer Load Time: " + stack.getTag().getInt("shimmer_load")/20));
                }
            }
        }
    }
}
