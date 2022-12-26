package com.extra.leaguecraft.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;

import java.util.UUID;

public class UltraShimmerEffect extends Effect {
    public UltraShimmerEffect() {
        super(EffectType.HARMFUL, 15018206);
        this.addAttributesModifier(Attributes.MAX_HEALTH, "30cac552-c761-4a7a-aec7-bc9857f13b97", 1.0D, AttributeModifier.Operation.ADDITION);
        this.addAttributesModifier(Attributes.ATTACK_DAMAGE, "51be17f7-98f6-4af3-8f51-cbb4f5363079", 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributesModifier(Attributes.KNOCKBACK_RESISTANCE, "58d0de39-d93f-4fb5-b906-75a05cf5642a", 1.0D, AttributeModifier.Operation.ADDITION);
    }


    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        entity.removeActivePotionEffect(Effects.NAUSEA.getEffect());
        entity.removeActivePotionEffect(Effects.WEAKNESS.getEffect());
        entity.removeActivePotionEffect(Effects.MINING_FATIGUE.getEffect());
        entity.removeActivePotionEffect(ModEffects.SHIMMER.get());

        entity.heal(amplifier + 1f);

        if(entity.getActivePotionEffect(ModEffects.ULTRA_SHIMMER.get()).getDuration() == 1) {
            entity.addPotionEffect(new EffectInstance(Effects.NAUSEA.getEffect(), 1200));
            entity.addPotionEffect(new EffectInstance(Effects.WEAKNESS.getEffect(), 2400));
            entity.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE.getEffect(), 2400));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        if(duration == 1) return true;
        return duration % 3 == 0;
    }

    @Override
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        if(modifier.getID().equals(UUID.fromString("30cac552-c761-4a7a-aec7-bc9857f13b97"))){
            return (amplifier * 20) + 20;
        }
        else if(modifier.getID().equals(UUID.fromString("51be17f7-98f6-4af3-8f51-cbb4f5363079"))){
            return 3D;
        }
        else{
            return 10;
        }
    }
}
