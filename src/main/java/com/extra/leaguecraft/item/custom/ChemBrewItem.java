package com.extra.leaguecraft.item.custom;

import com.extra.leaguecraft.entity.custom.ModPotionEntity;
import com.extra.leaguecraft.tileentity.HextechSynthesizerTile;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ChemBrewItem extends SplashPotionItem {

    public ChemBrewItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            CompoundNBT nbt = itemstack.getOrCreateTag();
            Effect ef1 = HextechSynthesizerTile.stringToEffect(nbt.getString("effect1"));
            int amp1 = nbt.getInt("amp1");
            Effect ef2 = HextechSynthesizerTile.stringToEffect(nbt.getString("effect2"));
            int amp2 = nbt.getInt("amp2");
            Effect ef3 = HextechSynthesizerTile.stringToEffect(nbt.getString("effect3"));
            int amp3 = nbt.getInt("amp3");

            EffectInstance effect1 = null;
            EffectInstance effect2 = null;
            EffectInstance effect3 = null;

            if(ef1 != null){
                effect1 = new EffectInstance(ef1, 1200, amp1);
                PotionUtils.addPotionToItemStack(itemstack, effectToPotion(ef1));
            }
            if(ef2 != null){
                effect2 = new EffectInstance(ef2, 1200, amp2);
                PotionUtils.addPotionToItemStack(itemstack, effectToPotion(ef2));
            }
            if(ef3 != null){
                effect3 = new EffectInstance(ef3, 1200, amp3);
                PotionUtils.addPotionToItemStack(itemstack, effectToPotion(ef2));
            }

            ModPotionEntity potionentity = new ModPotionEntity(worldIn, playerIn, effect1, effect2, effect3);
            potionentity.setItem(itemstack);
            potionentity.setDirectionAndMovement(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 0.5F, 1.0F);
            worldIn.addEntity(potionentity);
            return ActionResult.resultConsume(itemstack);
        }
        return ActionResult.resultSuccess(itemstack);
    }

    public Potion effectToPotion(Effect effect){
        String ef = HextechSynthesizerTile.effectToString(effect);

        if(ef.equals("Poison")) return Potions.POISON;
        if(ef.equals("Weakness")) return Potions.WEAKNESS;
        if(ef.equals("Slowness")) return Potions.SLOWNESS;
        if(ef.equals("MiningFatigue")) return new Potion("mining_fatigue", new EffectInstance(Effects.MINING_FATIGUE));
        if(ef.equals("Wither")) return new Potion("wither", new EffectInstance(Effects.WITHER));
        if(ef.equals("Blindness")) return new Potion("blindness", new EffectInstance(Effects.BLINDNESS));
        if(ef.equals("Hunger")) return new Potion("hunger", new EffectInstance(Effects.HUNGER));;
        if(ef.equals("Nausea")) return new Potion("nausea", new EffectInstance(Effects.NAUSEA));
        if(ef.equals("Glowing")) return new Potion("glowing", new EffectInstance(Effects.GLOWING));
        if(ef.equals("Regeneration")) return Potions.REGENERATION;
        if(ef.equals("Speed")) return Potions.SWIFTNESS;
        if(ef.equals("Haste")) return new Potion("haste", new EffectInstance(Effects.HASTE));
        if(ef.equals("Strength")) return Potions.STRENGTH;
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {

        CompoundNBT nbt = stack.getOrCreateTag();
        Effect ef1 = HextechSynthesizerTile.stringToEffect(nbt.getString("effect1"));
        Effect ef2 = HextechSynthesizerTile.stringToEffect(nbt.getString("effect2"));
        Effect ef3 = HextechSynthesizerTile.stringToEffect(nbt.getString("effect3"));
        int amp1 = nbt.getInt("amp1");
        int amp2 = nbt.getInt("amp2");
        int amp3 = nbt.getInt("amp3");

        if(ef1 != null){
            IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(new EffectInstance(ef1, 1200, amp1).getEffectName());
            tooltip.add(iformattabletextcomponent);
        }
        if(ef2 != null){
            IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(new EffectInstance(ef2, 1200, amp2).getEffectName());
            tooltip.add(iformattabletextcomponent);
        }
        if(ef3 != null){
            IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(new EffectInstance(ef2, 1200, amp3).getEffectName());
            tooltip.add(iformattabletextcomponent);
        }
    }
}
