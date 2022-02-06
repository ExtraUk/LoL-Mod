package com.extra.leaguecraft.block.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class ShimmerFlowerBlock extends FlowerBlock {

    public ShimmerFlowerBlock(Effect p_i49984_1_, int p_i49984_2_, Properties p_i49984_3_) {
        super(p_i49984_1_, p_i49984_2_, p_i49984_3_);
    }


    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.matchesBlock(Blocks.GRASS_BLOCK) || state.matchesBlock(Blocks.DIRT) || state.matchesBlock(Blocks.COARSE_DIRT) || state.matchesBlock(Blocks.PODZOL) || state.matchesBlock(Blocks.FARMLAND) || state.matchesBlock(Blocks.STONE) || state.matchesBlock(Blocks.COBBLESTONE);
    }


   /* public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextInt(25) == 0) {

            while (true) {
                int rand = random.nextInt(4);
                if (rand == 0 && worldIn.getBlockState(pos.north().east()).getBlock() == Blocks.AIR && worldIn.getBlockState(pos.north().east().down()).getBlock() == Blocks.STONE) {
                    worldIn.setBlockState(pos.north().east(), this.getDefaultState());
                    break;
                }
                if (rand == 1 && worldIn.getBlockState(pos.north().west()).getBlock() == Blocks.AIR && worldIn.getBlockState(pos.north().west().down()).getBlock() == Blocks.STONE) {
                    worldIn.setBlockState(pos.north().west(), this.getDefaultState());
                    break;
                }
                if (rand == 2 && worldIn.getBlockState(pos.south().east()).getBlock() == Blocks.AIR && worldIn.getBlockState(pos.south().east().down()).getBlock() == Blocks.STONE) {
                    worldIn.setBlockState(pos.south().east(), this.getDefaultState());
                    break;
                }
                if (rand == 3 && worldIn.getBlockState(pos.south().west()).getBlock() == Blocks.AIR && worldIn.getBlockState(pos.south().west().down()).getBlock() == Blocks.STONE) {
                    worldIn.setBlockState(pos.south().west(), this.getDefaultState());
                    break;
                }

            }
        }
    }*/

}
