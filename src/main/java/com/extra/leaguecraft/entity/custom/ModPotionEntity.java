package com.extra.leaguecraft.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModPotionEntity extends PotionEntity {

    private EffectInstance ef1 = null;
    private EffectInstance ef2 = null;
    private EffectInstance ef3 = null;

    public ModPotionEntity(EntityType<? extends PotionEntity> p_i50149_1_, World p_i50149_2_) {
        super(p_i50149_1_, p_i50149_2_);
    }

    public ModPotionEntity(World p_i50150_1_, LivingEntity p_i50150_2_, EffectInstance effect1, EffectInstance effect2, EffectInstance effect3) {
        super(p_i50150_1_, p_i50150_2_);
        this.ef1 = effect1;
        this.ef2 = effect2;
        this.ef3 = effect3;
    }

    public ModPotionEntity(World p_i50151_1_, double p_i50151_2_, double p_i50151_4_, double p_i50151_6_) {
        super(p_i50151_1_, p_i50151_2_, p_i50151_4_, p_i50151_6_);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        onImpactSuper(result);
        if (!this.world.isRemote) {
            ItemStack itemstack = this.getItem();
            Potion potion = PotionUtils.getPotionFromItem(itemstack);

            ArrayList<EffectInstance> effects = new ArrayList<EffectInstance>();
            if(ef1 != null) effects.add(ef1);
            if(ef2 != null) effects.add(ef2);
            if(ef3 != null) effects.add(ef3);
            PotionUtils.appendEffects(itemstack, effects);

            List<EffectInstance> list = PotionUtils.getEffectsFromStack(itemstack);

            this.func_213888_a(list, result.getType() == RayTraceResult.Type.ENTITY ? ((EntityRayTraceResult)result).getEntity() : null);

            int i = potion.hasInstantEffect() ? 2007 : 2002;
            this.world.playEvent(i, this.getPosition(), PotionUtils.getColor(itemstack));
            this.remove();
        }
    }

    private void func_213888_a(List<EffectInstance> p_213888_1_, @Nullable Entity p_213888_2_) {
        AxisAlignedBB axisalignedbb = this.getBoundingBox().grow(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, axisalignedbb);
        if (!list.isEmpty()) {
            for(LivingEntity livingentity : list) {
                if (livingentity.canBeHitWithPotion()) {
                    double d0 = this.getDistanceSq(livingentity);
                    if (d0 < 16.0D) {
                        double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
                        if (livingentity == p_213888_2_) {
                            d1 = 1.0D;
                        }

                        for(EffectInstance effectinstance : p_213888_1_) {
                            Effect effect = effectinstance.getPotion();
                            if (effect.isInstant()) {
                                effect.affectEntity(this, this.getShooter(), livingentity, effectinstance.getAmplifier(), d1);
                            } else {
                                int i = (int)(d1 * (double)effectinstance.getDuration() + 0.5D);
                                if (i > 20) {
                                    livingentity.addPotionEffect(new EffectInstance(effect, i, effectinstance.getAmplifier(), effectinstance.isAmbient(), effectinstance.doesShowParticles()));
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    protected void onImpactSuper(RayTraceResult result) {
        RayTraceResult.Type raytraceresult$type = result.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
            this.onEntityHit((EntityRayTraceResult)result);
        } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
            this.func_230299_a_((BlockRayTraceResult)result);
        }
    }

    protected void func_230299_a_(BlockRayTraceResult result) {
        BlockState blockstate = this.world.getBlockState(result.getPos());
        blockstate.onProjectileCollision(this.world, blockstate, result, this);
    }
}
