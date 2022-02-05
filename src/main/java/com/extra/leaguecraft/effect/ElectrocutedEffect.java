package com.extra.leaguecraft.effect;

import com.extra.leaguecraft.util.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;

public class ElectrocutedEffect extends Effect {
    public ElectrocutedEffect() {
        super(EffectType.HARMFUL, 0x2acaea);
        this.addAttributesModifier(Attributes.MOVEMENT_SPEED, "f83e0b29-2170-4a8b-b5c2-7307498da36f", Integer.MIN_VALUE, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void performEffect(LivingEntity entity, int amp) {
        entity.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 0.5F*(amp+1));
        if(entity.getActivePotionEffect(this).getDuration() % 20 == 0 && entity.getActivePotionEffect(this).getDuration() != 0){
            entity.world.playMovingSound(null, entity, ModSoundEvents.ELECTROCUTED_EFFECT.get(), SoundCategory.PLAYERS, 1F, ((float) Math.random()));
        }
    }

    @Override
    public boolean isReady(int duration, int amp) {
        return duration % 10 == 0;
    }
}
