package com.extra.leaguecraft.block.custom;

import com.extra.leaguecraft.container.HextechChargerContainer;
import com.extra.leaguecraft.container.HextechSynthesizerContainer;
import com.extra.leaguecraft.tileentity.HextechChargerTile;
import com.extra.leaguecraft.tileentity.HextechSynthesizerTile;
import com.extra.leaguecraft.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class HextechSynthesizerBlock extends Block {

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public HextechSynthesizerBlock(Properties properties) {
        super(properties);
        //this.setDefaultState(this.getDefaultState().with(ACTIVE, false));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if(!world.isRemote){
            TileEntity tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof HextechSynthesizerTile){
                INamedContainerProvider containerProvider = createContainerProvider(world, pos);

                NetworkHooks.openGui(((ServerPlayerEntity) player), containerProvider, tileEntity.getPos());
            }
            else{
                throw new IllegalStateException("Container provider missing");
            }
        }

        return ActionResultType.SUCCESS;
    }

    public INamedContainerProvider createContainerProvider(World world, BlockPos pos){
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.leaguecraft.hextech_synthesizer");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity player) {
                return new HextechSynthesizerContainer(i, world, pos, playerInventory, player);
            }
        };
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.HEXTECH_SYNTHESIZER_TILE.get().create();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

}
