package com.extra.leaguecraft.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WorldRuneFire extends Item{

    public WorldRuneFire(Item.Properties properties) {
        super(properties.isImmuneToFire());
    }

    private RayTraceResult rayTrace(World world, PlayerEntity player)
    {
        float rotationPitch = player.rotationPitch;
        float rotationYaw = player.rotationYaw;
        Vector3d eyePosition = player.getEyePosition(1.0F);

        // look vector
        float f2 = MathHelper.cos(-rotationYaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-rotationYaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-rotationPitch * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-rotationPitch * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double rayTraceDistance = 256;

        // from start pos, add look vector multiplied by rayTraceDistance
        Vector3d endPosition = eyePosition.add((double)f6 * rayTraceDistance, (double)f5 * rayTraceDistance, (double)f7 * rayTraceDistance);
        return world.rayTraceBlocks(new RayTraceContext(eyePosition, endPosition, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote()) {
            RayTraceResult result = rayTrace(worldIn, playerIn);
            if (result instanceof BlockRayTraceResult) {
                BlockPos pos = ((BlockRayTraceResult) result).getPos();
                Block block = worldIn.getBlockState(pos).getBlock();

                if (block != Blocks.AIR) {
                    if (worldIn instanceof ServerWorld) {
                        worldIn.createExplosion(null, new DamageSource("FireWorldRune"), null, pos.getX(), pos.getY(), pos.getZ(), 20, true, Explosion.Mode.NONE);
                    }
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
