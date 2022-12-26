package com.extra.leaguecraft.entity.custom;

import com.extra.leaguecraft.entity.ModEntityTypes;
import com.extra.leaguecraft.util.ModSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;

public class BlitzcrankFistEntity extends ArrowEntity{
    private BlitzcrankEntity blitzShooter;

    public BlitzcrankFistEntity(EntityType<? extends ArrowEntity> type, World world) {
        super(type,world);
        this.setHitSound(ModSoundEvents.BLITZCRANK_HOOK_HIT.get());
    }
    public BlitzcrankFistEntity(World worldIn, LivingEntity throwerIn) {
        super(worldIn, throwerIn);
    }

    public BlitzcrankFistEntity(World world) {
        this(ModEntityTypes.BLITZCRANK_FIST.get(), world);
        //this.setHitSound(ModSoundEvents.BLITZCRANK_HOOK_HIT.get());
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        if(!world.isRemote()) {
            Entity entity = result.getEntity();
            BlitzcrankEntity blitz;
            if (this.blitzShooter == null) {
                blitz = (BlitzcrankEntity) this.getShooter();
            } else {
                blitz = this.blitzShooter;
            }
            if(blitz != null) {
                blitz.playSound(ModSoundEvents.BLITZCRANK_HOOK_HIT.get(), 1.0f, 1.0f);
                entity.teleportKeepLoaded(blitz.getPosX(), blitz.getPosY(), blitz.getPosZ());
            }
        }
        this.remove();
    }

    @Override
    protected void registerData() {
        super.registerData();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setBlitzShooter(BlitzcrankEntity blitz){
        this.blitzShooter = blitz;
    }
}
