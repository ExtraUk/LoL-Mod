package com.extra.leaguecraft.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ChemTechGrenadeEntity extends SnowballEntity {

    public ChemTechGrenadeEntity(World worldIn, LivingEntity throwerIn) {
        super(worldIn, throwerIn);
    }


    @Override
    protected void onImpact(RayTraceResult result) {

        if(!world.isRemote) {
            if (result.getType() == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult res = ((BlockRayTraceResult) result);
                double x = res.getPos().getX();
                double y = res.getPos().getY();
                double z = res.getPos().getZ();
                this.world.createExplosion(null, new DamageSource("ChemTechGrenade"), null, x, y, z, 3F, false, Explosion.Mode.BREAK);
            } else if (result.getType() == RayTraceResult.Type.ENTITY) {
                EntityRayTraceResult res = ((EntityRayTraceResult) result);
                double x = res.getEntity().getPosX();
                double y = res.getEntity().getPosY();
                double z = res.getEntity().getPosZ();
                this.world.createExplosion(null, new DamageSource("ChemTechGrenade"), null, x, y, z, 3F, false, Explosion.Mode.BREAK);
            }
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }

    }

    @Override
    protected float getGravityVelocity() {
        return 0.03F;
    }
}
