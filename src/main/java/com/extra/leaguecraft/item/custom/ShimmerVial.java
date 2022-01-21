package com.extra.leaguecraft.item.custom;

import com.extra.leaguecraft.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ShimmerVial extends Item {

    public ShimmerVial(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume(itemstack);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        if(entity.getActivePotionEffect(ModEffects.SHIMMER.get()) == null){
            entity.addPotionEffect(new EffectInstance(ModEffects.SHIMMER.get(), 3600));
        }
        else{
            int amp = entity.getActivePotionEffect(ModEffects.SHIMMER.get()).getAmplifier();
            amp = Math.min(amp, 1);
            int dur = 3600;
            if(amp >= 2){
                dur += entity.getActivePotionEffect(ModEffects.SHIMMER.get()).getDuration();
            }
            entity.addPotionEffect(new EffectInstance(ModEffects.SHIMMER.get(), dur, amp+1));
        }
        return super.onItemUseFinish(stack, world, entity);
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.ENTITY_GENERIC_DRINK;
    }
}
