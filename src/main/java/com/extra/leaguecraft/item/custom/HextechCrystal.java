package com.extra.leaguecraft.item.custom;

import com.extra.leaguecraft.block.ModBlocks;
import com.extra.leaguecraft.entity.ModEntityTypes;
import com.extra.leaguecraft.entity.custom.BlitzcrankEntity;
import com.extra.leaguecraft.entity.custom.BlitzcrankFistEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HextechCrystal extends Item {

    public HextechCrystal(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity entity, Hand hand) {
        if (entity.world.isRemote) return ActionResultType.PASS;
        if(entity instanceof BlitzcrankEntity){
            if(!playerIn.isCreative()) {
                float healAmount = Math.min((entity.getMaxHealth() - entity.getHealth()), (stack.getMaxDamage() - stack.getDamage()));
                entity.heal(healAmount);
                stack.setDamage(stack.getDamage() + (int)healAmount);
                return ActionResultType.SUCCESS;
            }
            else{
                entity.heal(entity.getMaxHealth() - entity.getHealth());
                return ActionResultType.SUCCESS;
            }
        }
        else{
            return ActionResultType.PASS;
        }
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState blockstate = world.getBlockState(blockpos);
        BlockState downBS = world.getBlockState(blockpos.down());
        BlockState upBS = world.getBlockState(blockpos.up());
        BlockState northBS = world.getBlockState(blockpos.north());
        BlockState southBS = world.getBlockState(blockpos.south());
        BlockState eastBS = world.getBlockState(blockpos.east());
        BlockState westBS = world.getBlockState(blockpos.west());
        if(blockstate.getBlock() == ModBlocks.BRONZE_BLOCK.get() && downBS.getBlock() == ModBlocks.BRONZE_BLOCK.get() && upBS.getBlock() == ModBlocks.BRONZE_BLOCK.get()){
            if(northBS.getBlock() == ModBlocks.BRONZE_BLOCK.get() && southBS.getBlock() == ModBlocks.BRONZE_BLOCK.get()){
                world.destroyBlock(blockpos, false);
                world.destroyBlock(blockpos.down(), false);
                world.destroyBlock(blockpos.up(), false);
                world.destroyBlock(blockpos.north(), false);
                world.destroyBlock(blockpos.south(), false);
                BlitzcrankEntity blitzcrank = new BlitzcrankEntity(ModEntityTypes.BLITZCRANK.get(), world);
                blitzcrank.teleportKeepLoaded(blockpos.getX(),blockpos.getY()-1,blockpos.getZ());
                world.addEntity(blitzcrank);
                context.getItem().setCount(0);
                return ActionResultType.SUCCESS;
            }
            else if(eastBS.getBlock() == ModBlocks.BRONZE_BLOCK.get() && westBS.getBlock() == ModBlocks.BRONZE_BLOCK.get()){
                world.destroyBlock(blockpos, false);
                world.destroyBlock(blockpos.down(), false);
                world.destroyBlock(blockpos.up(), false);
                world.destroyBlock(blockpos.east(), false);
                world.destroyBlock(blockpos.west(), false);
                BlitzcrankEntity blitzcrank = new BlitzcrankEntity(ModEntityTypes.BLITZCRANK.get(), world);
                blitzcrank.teleportKeepLoaded(blockpos.getX(),blockpos.getY()-1,blockpos.getZ());
                world.addEntity(blitzcrank);
                context.getItem().setCount(0);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
}
