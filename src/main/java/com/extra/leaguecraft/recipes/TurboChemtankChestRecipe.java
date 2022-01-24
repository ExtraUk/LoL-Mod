package com.extra.leaguecraft.recipes;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.item.ModItems;
import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class TurboChemtankChestRecipe implements ICraftingRecipe {

    private final ResourceLocation id;

    public TurboChemtankChestRecipe(ResourceLocation idIn){
        id = idIn;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ModItems.TURBO_CHEMTANK_CHEST.get());
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        ItemStack chest = ItemStack.EMPTY;
        ItemStack vial = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.isEmpty()){
                continue;
            }
            Item item = stack.getItem();
            if (item instanceof AirItem){
                continue;
            }
            if (item.equals(ModItems.SHIMMER_VIAL.get())) {
                vial = stack;
            }
            else if (item.equals(ModItems.TURBO_CHEMTANK_CHEST.get())) {
                chest = stack;
            }
            else{
                return false;
            }
        }
        return !(vial.isEmpty() || chest.isEmpty());
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        int vialCount = 0;
        ItemStack chest = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            Item item = stack.getItem();
            if (item.equals(ModItems.SHIMMER_VIAL.get())) {
                vialCount++;
            }
            else if (item.equals(ModItems.TURBO_CHEMTANK_CHEST.get()))
                chest = stack;
        }

        if (chest.isEmpty() || vialCount == 0) { // Should only happen if the result from matches() gets ignored
            return ItemStack.EMPTY;
        }

        int load = 0;
        if(chest.getOrCreateTag().contains("shimmer_load")){
            load = chest.getTag().getInt("shimmer_load");
        }

        ItemStack chestplateToRetrun = chest.copy();
        if(load >= 16800){
            chestplateToRetrun.getOrCreateTag().putInt("shimmer_load", 19200);
        }
        else {
            chestplateToRetrun.getOrCreateTag().putInt("shimmer_load", load + vialCount * 2400);
        }
        return chestplateToRetrun;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return LeagueCraft.TURBO_CHEMTANK_CHEST_RECIPE.get();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TurboChemtankChestRecipe> {
        @Override
        public TurboChemtankChestRecipe read(ResourceLocation recipeId, JsonObject json) {
            return new TurboChemtankChestRecipe(recipeId);
        }

        @Nullable
        @Override
        public TurboChemtankChestRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            return new TurboChemtankChestRecipe(recipeId);
        }

        @Override
        public void write(PacketBuffer buffer, TurboChemtankChestRecipe recipe) {
            // TODO Auto-generated method stub
        }
    }
}
