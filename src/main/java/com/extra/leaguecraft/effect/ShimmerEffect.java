package com.extra.leaguecraft.effect;

import com.google.common.collect.Maps;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ShimmerEffect extends Effect {
    public ShimmerEffect() {
        super(EffectType.HARMFUL, 15018206);
    }

    private final Map<Attribute, AttributeModifier> attributeModifierMap = Maps.newHashMap();

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        if(amplifier == 0){
            entity.heal(1.0f);
        }
        else if(amplifier == 1){
            entity.heal(2.0f);
        }
        else if(amplifier >= 2){
            entity.heal(6.0f);

            entity.removeActivePotionEffect(Effects.NAUSEA.getEffect());
            entity.removeActivePotionEffect(Effects.WEAKNESS.getEffect());
            entity.removeActivePotionEffect(Effects.MINING_FATIGUE.getEffect());
        }
    }

    public Effect addAttributesModifier(Attribute attributeIn, String uuid, double amount, AttributeModifier.Operation operation, boolean flag) {
        if(flag){
            this.attributeModifierMap.clear();
        }
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uuid), this::getName, amount, operation);
        this.attributeModifierMap.put(attributeIn, attributemodifier);
        return this;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {

        if(amplifier == 0){
            this.addAttributesModifier(Attributes.MAX_HEALTH, "30cac552-c761-4a7a-aec7-bc9857f13b97", 4.0D, AttributeModifier.Operation.ADDITION, true);
            this.addAttributesModifier(Attributes.ATTACK_DAMAGE, "51be17f7-98f6-4af3-8f51-cbb4f5363079", 0.0D, AttributeModifier.Operation.ADDITION, false);
            this.addAttributesModifier(Attributes.MOVEMENT_SPEED, "6742176a-73e1-451c-9877-75985ed2bc28", 0.0D, AttributeModifier.Operation.ADDITION, false);
            this.addAttributesModifier(Attributes.KNOCKBACK_RESISTANCE, "58d0de39-d93f-4fb5-b906-75a05cf5642a", 0.0D, AttributeModifier.Operation.ADDITION, false);
        }
        else if(amplifier == 1){
            this.addAttributesModifier(Attributes.MAX_HEALTH, "30cac552-c761-4a7a-aec7-bc9857f13b97", 8.0D, AttributeModifier.Operation.ADDITION, true);
            this.addAttributesModifier(Attributes.ATTACK_DAMAGE, "51be17f7-98f6-4af3-8f51-cbb4f5363079", (double)1.5F, AttributeModifier.Operation.MULTIPLY_TOTAL, false);
            this.addAttributesModifier(Attributes.MOVEMENT_SPEED, "6742176a-73e1-451c-9877-75985ed2bc28", (double)0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL, false);
            this.addAttributesModifier(Attributes.KNOCKBACK_RESISTANCE, "58d0de39-d93f-4fb5-b906-75a05cf5642a", 0.0D, AttributeModifier.Operation.ADDITION, false);
        }
        else if(amplifier >= 2){
            this.addAttributesModifier(Attributes.MAX_HEALTH, "30cac552-c761-4a7a-aec7-bc9857f13b97", 20.0D, AttributeModifier.Operation.ADDITION, true);
            this.addAttributesModifier(Attributes.ATTACK_DAMAGE, "51be17f7-98f6-4af3-8f51-cbb4f5363079", 4.0D, AttributeModifier.Operation.MULTIPLY_TOTAL, false);
            this.addAttributesModifier(Attributes.MOVEMENT_SPEED, "6742176a-73e1-451c-9877-75985ed2bc28", 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL, false);
            this.addAttributesModifier(Attributes.KNOCKBACK_RESISTANCE, "58d0de39-d93f-4fb5-b906-75a05cf5642a", 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL, false);
            return duration % 20 == 0;
        }

        return duration % 50 == 0;
    }

    @Override
    public void removeAttributesModifiersFromEntity(LivingEntity entityLivingBaseIn, AttributeModifierManager attributeMapIn, int amplifier) {
        if(entityLivingBaseIn.getActivePotionEffect(ModEffects.SHIMMER.get()) == null) {

            ArrayList<Map.Entry<Attribute, AttributeModifier>> list = new ArrayList<>();
            for (Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
                ModifiableAttributeInstance modifiableattributeinstance = attributeMapIn.createInstanceIfAbsent(entry.getKey());
                System.out.println(entry.getValue());
                if (modifiableattributeinstance != null) {
                    list.add(entry);
                }
            }
            for (Map.Entry<Attribute, AttributeModifier> entry : list) {
                ModifiableAttributeInstance modifiableattributeinstance = attributeMapIn.createInstanceIfAbsent(entry.getKey());
                modifiableattributeinstance.removeModifier(entry.getValue());
            }

            if (amplifier == 1) {
                if (Math.round(Math.random()) == 1) {
                    System.out.println(1);
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.NAUSEA.getEffect(), 1200));
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.WEAKNESS.getEffect(), 2400));
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE.getEffect(), 2400));
                }
            }
            if (amplifier == 2) {
                System.out.println(2);
                entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.NAUSEA.getEffect(), 1200));
                entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.WEAKNESS.getEffect(), 2400));
                entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE.getEffect(), 2400));
                if (Math.round(Math.random() * 10) == 1) {
                    System.out.println(3);
                    entityLivingBaseIn.getAttribute(Attributes.MAX_HEALTH).applyNonPersistentModifier(new AttributeModifier("MaxHealth", entityLivingBaseIn.getMaxHealth() - (entityLivingBaseIn.getMaxHealth() + 2), AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    @Override
    public void applyAttributesModifiersToEntity(LivingEntity entityLivingBaseIn, AttributeModifierManager attributeMapIn, int amplifier) {
        entityLivingBaseIn.removeActivePotionEffect(Effects.NAUSEA.getEffect());
        entityLivingBaseIn.removeActivePotionEffect(Effects.WEAKNESS.getEffect());
        entityLivingBaseIn.removeActivePotionEffect(Effects.MINING_FATIGUE.getEffect());
        for(Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
            ModifiableAttributeInstance modifiableattributeinstance = attributeMapIn.createInstanceIfAbsent(entry.getKey());
            if (modifiableattributeinstance != null) {
                AttributeModifier attributemodifier = entry.getValue();
                modifiableattributeinstance.removeModifier(attributemodifier);
                modifiableattributeinstance.applyPersistentModifier(new AttributeModifier(attributemodifier.getID(), this.getName() + " " + amplifier, this.getAttributeModifierAmount(amplifier, attributemodifier), attributemodifier.getOperation()));
            }
        }
        

    }

    @Override
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        //return modifier.getAmount() * (double)(amplifier + 1);
        if(amplifier == 0){
            if(modifier.getID().equals(UUID.fromString("30cac552-c761-4a7a-aec7-bc9857f13b97"))){
                return 4;
            }
            else{
                return 0;
            }
        }
        else if(amplifier == 1){
            if(modifier.getID().equals(UUID.fromString("30cac552-c761-4a7a-aec7-bc9857f13b97"))){
                return 8;
            }
            else if(modifier.getID().equals(UUID.fromString("51be17f7-98f6-4af3-8f51-cbb4f5363079"))){
                return 1.5;
            }
            else if(modifier.getID().equals(UUID.fromString("6742176a-73e1-451c-9877-75985ed2bc28"))){
                return 0.5*amplifier;
            }
            else{
                return 0;
            }
        }
        else if(amplifier >= 2){
            if(modifier.getID().equals(UUID.fromString("30cac552-c761-4a7a-aec7-bc9857f13b97"))){
                return 10*amplifier;
            }
            else if(modifier.getID().equals(UUID.fromString("51be17f7-98f6-4af3-8f51-cbb4f5363079"))){
                return 2*amplifier;
            }
            else if(modifier.getID().equals(UUID.fromString("6742176a-73e1-451c-9877-75985ed2bc28"))){
                return 0.5*amplifier;
            }
            else{
                return 0.5*amplifier;
            }
        }
        else{
            return 0;
        }
    }

    @Override
    public Map<Attribute, AttributeModifier> getAttributeModifierMap() {
        return this.attributeModifierMap;
    }
}
