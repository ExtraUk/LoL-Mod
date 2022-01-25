package com.extra.leaguecraft.tileentity;

import com.extra.leaguecraft.block.custom.HextechChargerBlock;
import com.extra.leaguecraft.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class HextechChargerTile extends TileEntity implements ITickableTileEntity {

    private ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public HextechChargerTile(TileEntityType<?> type) {
        super(type);
    }

    public HextechChargerTile() {
        this(ModTileEntities.HEXTECH_CHARGER_TILE.get());
    }

    private ItemStackHandler createHandler(){
        return new ItemStackHandler(2){
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch (slot){
                    case 0:
                        return stack.getItem() == ModItems.HEXTECH_CRYSTAL.get();
                    case 1:
                        return stack.getItem() == ModItems.HEXTECH_CRYSTAL.get() ||
                               stack.getItem() == ModItems.HEXTECH_SWORD.get() ||
                               stack.getItem() == ModItems.HEXTECH_SWORD_BROKEN.get();
                    default:
                        return false;
                }
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(!isItemValid(slot, stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }

        return super.getCapability(cap, side);
    }


    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt.put("inv", itemHandler.serializeNBT());
        return super.write(nbt);
    }

    @Override
    public void tick() {
        ItemStack fuel = this.itemHandler.getStackInSlot(0);
        ItemStack chargingItem = this.itemHandler.getStackInSlot(1);
        if(!fuel.isEmpty() && !chargingItem.isEmpty()){
            if(chargingItem.getItem() == ModItems.HEXTECH_SWORD_BROKEN.get() && fuel.getDamage() != fuel.getMaxDamage()){
                if(!this.world.getBlockState(pos).get(HextechChargerBlock.ACTIVE)) {
                    this.world.setBlockState(pos, this.world.getBlockState(this.pos).cycleValue(HextechChargerBlock.ACTIVE));
                }
                ItemStack stack = ModItems.HEXTECH_SWORD.get().getDefaultInstance();
                stack.setDamage(stack.getItem().getMaxDamage(stack));
                this.itemHandler.setStackInSlot(1, stack);
                return;
            }
            if(fuel.getDamage() != fuel.getMaxDamage() && chargingItem.getDamage() != 0) {
                if(!this.world.getBlockState(pos).get(HextechChargerBlock.ACTIVE)) {
                    this.world.setBlockState(pos, this.world.getBlockState(this.pos).cycleValue(HextechChargerBlock.ACTIVE));
                }
                this.itemHandler.getStackInSlot(0).setDamage(fuel.getDamage() + 1);
                this.itemHandler.getStackInSlot(1).setDamage(chargingItem.getDamage()-1);
                return;
            }
        }
        if(this.world.getBlockState(pos).get(HextechChargerBlock.ACTIVE)) {
            this.world.setBlockState(pos, this.world.getBlockState(this.pos).cycleValue(HextechChargerBlock.ACTIVE));
        }
    }
}
