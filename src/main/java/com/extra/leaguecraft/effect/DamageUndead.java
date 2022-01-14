package com.extra.leaguecraft.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;

import javax.annotation.Nullable;

public class DamageUndead extends Effect {

    public DamageUndead() {
        super(EffectType.NEUTRAL, 16777215);
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        if(entity.isEntityUndead()){
            entity.attackEntityFrom(DamageSource.GENERIC, 200);
        }
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
        if (entityLivingBaseIn.isEntityUndead()) {
            int j = 100;
            if (source == null) {
                entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, (float)j);
            } else {
                entityLivingBaseIn.attackEntityFrom(DamageSource.causeIndirectMagicDamage(source, indirectSource), (float)j);
            }
        }
    }

    @Override
    public boolean isInstant() {
        return true;
    }
}
