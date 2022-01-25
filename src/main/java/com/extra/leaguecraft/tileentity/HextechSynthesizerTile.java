package com.extra.leaguecraft.tileentity;

import com.extra.leaguecraft.block.ModBlocks;
import com.extra.leaguecraft.block.custom.HextechChargerBlock;
import com.extra.leaguecraft.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HextechSynthesizerTile extends TileEntity implements ITickableTileEntity {

    private ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private int progress = 0;
    private int shimmerAmount = 0;

    private int totalBrewAmount = 0;
    private int effect1Amp = 0;
    private int effect2Amp = 0;
    private int effect3Amp = 0;
    private Effect effect1 = null;
    private Effect effect2 = null;
    private Effect effect3 = null;
    private int ingredientAmount = 0;

    public int getProgress() {
        return progress;
    }

    public int getShimmerAmount() {
        return this.shimmerAmount;
    }

    public int getTotalBrewAmount() {
        return totalBrewAmount;
    }

    public HextechSynthesizerTile(TileEntityType<?> type) {
        super(type);
    }

    public HextechSynthesizerTile() {
        this(ModTileEntities.HEXTECH_SYNTHESIZER_TILE.get());
    }

    private ItemStackHandler createHandler(){
        return new ItemStackHandler(4){
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
                        return stack.getItem() == ModItems.VIAL.get() ||
                               stack.getItem() == Items.GLASS_BOTTLE;
                    case 2:
                        return (HextechSynthesizerTile.getEffect(stack) != null || stack.getItem() == ModBlocks.SHIMMER_FLOWER.get().asItem());
                    case 3:
                        return stack.getItem() == ModItems.SHIMMER_VIAL.get() || stack.getItem() == ModItems.CHEM_BREW.get();
                    default:
                        return false;
                }
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
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
        super.read(state, nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        this.shimmerAmount = nbt.getInt("shimmer");
        this.totalBrewAmount = nbt.getInt("brew");
        this.effect1Amp = nbt.getInt("effect1Amp");
        this.effect2Amp = nbt.getInt("effect2Amp");
        this.effect3Amp = nbt.getInt("effect3Amp");
        this.ingredientAmount = nbt.getInt("ingAmount");

        String ef1 = nbt.contains("effect1") ? nbt.getString("effect1") : "";
        String ef2 = nbt.contains("effect2") ? nbt.getString("effect2") : "";
        String ef3 = nbt.contains("effect3") ? nbt.getString("effect3") : "";

        this.effect1 = stringToEffect(ef1);
        this.effect2 = stringToEffect(ef2);
        this.effect3 = stringToEffect(ef3);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {

        nbt.putInt("shimmer", shimmerAmount);

        nbt.putInt("brew", totalBrewAmount);
        nbt.putInt("effect1Amp", effect1Amp);
        nbt.putInt("effect2Amp", effect2Amp);
        nbt.putInt("effect3Amp", effect3Amp);
        String ef1 = effectToString(effect1);
        String ef2 = effectToString(effect2);
        String ef3 = effectToString(effect3);
        nbt.putString("effect1", ef1);
        nbt.putString("effect2", ef2);
        nbt.putString("effect3", ef3);
        nbt.putInt("ingAmount", ingredientAmount);

        nbt.put("inv", itemHandler.serializeNBT());
        return super.write(nbt);
    }

    @Override
    public void tick() {
        ItemStack fuel = this.itemHandler.getStackInSlot(0);
        ItemStack container = this.itemHandler.getStackInSlot(1);
        ItemStack ingredient = this.itemHandler.getStackInSlot(2);
        ItemStack result = this.itemHandler.getStackInSlot(3);

        if(!fuel.isEmpty() && !ingredient.isEmpty()){
            if(fuel.getDamage() != fuel.getMaxDamage()) {
                doAction(ingredient);
            }
        }
        if(this.shimmerAmount >= 100){
            extractShimmer(container, result);
        }
        else if(this.totalBrewAmount >= 100){
            extractBrew(container, result);
        }
        if(this.shimmerAmount >= 100 && container.getItem() == ModItems.VIAL.get() && result.getCount() < result.getMaxStackSize()){

        }
    }

    public void doAction(ItemStack ingredient){
        if (ingredient.getItem() == ModBlocks.SHIMMER_FLOWER.get().asItem()) {
            if(totalBrewAmount == 0) {
                makeShimmer(ingredient);
            }
        }
        else if(getEffect(ingredient) != null && shimmerAmount == 0){
            makeChemBrew(ingredient);
        }
    }

    public void makeShimmer(ItemStack ingredient){
        if (shimmerAmount < 1000) {
            if (progress < 140) {
                if (progress % 20 == 0) {
                    this.itemHandler.getStackInSlot(0).setDamage(this.itemHandler.getStackInSlot(0).getDamage() + 1);
                }
                progress++;
            } else {
                progress = 0;
                this.itemHandler.extractItem(2, 1, false);
                this.shimmerAmount += 10;
            }
        }
    }

    public void makeChemBrew(ItemStack ingredient) {
        if (totalBrewAmount < 1000) {
            if(progress < 140) {
                Effect effect = getEffect(ingredient);
                if(ingredientAmount == 3 && (effect != effect1 && effect != effect2 && effect != effect3)){
                    return;
                }

                if (progress % 20 == 0) {
                    this.itemHandler.getStackInSlot(0).setDamage(this.itemHandler.getStackInSlot(0).getDamage() + 1);
                }
                progress++;
            }
            else{
                Effect effect = getEffect(ingredient);
                if(ingredientAmount < 3) {
                    if (effect == effect1 || effect == effect2 || effect == effect3) {
                        if (effect == effect1 && effect1Amp < 2) {
                            effect1Amp++;
                            ingredientAmount++;
                        }
                        if (effect == effect2 && effect2Amp < 2) {
                            effect2Amp++;
                            ingredientAmount++;
                        }
                        if (effect == effect3 && effect3Amp < 2) {
                            effect3Amp++;
                            ingredientAmount++;
                        }
                    }
                    else if (effect1 == null) {
                        effect1 = effect;
                        ingredientAmount++;
                    }
                    else if (effect2 == null) {
                        effect2 = effect;
                        ingredientAmount++;
                    }
                    else if (effect3 == null) {
                        effect3 = effect;
                        ingredientAmount++;
                    }
                }
                progress = 0;
                this.itemHandler.extractItem(2, 1, false);
                totalBrewAmount += 100;
            }
        }
    }

    public void extractShimmer(ItemStack container, ItemStack result){
        if(container.getItem() == ModItems.VIAL.get() && result.getCount() < result.getMaxStackSize()){
            this.itemHandler.extractItem(1, 1, false);
            this.itemHandler.insertItem(3, ModItems.SHIMMER_VIAL.get().getDefaultInstance(), false);
            shimmerAmount -= 100;
        }
    }

    public void extractBrew(ItemStack container, ItemStack result){
        if(container.getItem() == Items.GLASS_BOTTLE && result.getCount() < result.getMaxStackSize()){
            this.itemHandler.extractItem(1, 1, false);
            ItemStack chemBrew = ModItems.CHEM_BREW.get().getDefaultInstance();
            chemBrew.getOrCreateTag().putString("effect1", effectToString(this.effect1));
            chemBrew.getOrCreateTag().putString("effect2", effectToString(this.effect2));
            chemBrew.getOrCreateTag().putString("effect3", effectToString(this.effect3));
            chemBrew.getOrCreateTag().putInt("amp1", this.effect1Amp);
            chemBrew.getOrCreateTag().putInt("amp2", this.effect2Amp);
            chemBrew.getOrCreateTag().putInt("amp3", this.effect3Amp);
            this.itemHandler.insertItem(3, chemBrew, false);
            totalBrewAmount -= 100;
            if(totalBrewAmount == 0) {
                this.effect1 = null;
                this.effect2 = null;
                this.effect3 = null;
                this.effect1Amp = 0;
                this.effect2Amp = 0;
                this.effect3Amp = 0;
                ingredientAmount = 0;
            }
        }
    }

    public static Effect getEffect(ItemStack stack){
        if(stack.getItem() == Items.SPIDER_EYE) return Effects.POISON;
        if(stack.getItem() == Items.FERMENTED_SPIDER_EYE) return Effects.WEAKNESS;
        if(stack.getItem() == Items.STRING) return Effects.SLOWNESS;
        if(stack.getItem() == Items.WOODEN_PICKAXE) return Effects.MINING_FATIGUE;
        if(stack.getItem() == Items.BONE) return Effects.WITHER;
        if(stack.getItem() == Items.INK_SAC) return Effects.BLINDNESS;
        if(stack.getItem() == Items.ROTTEN_FLESH) return Effects.HUNGER;
        if(stack.getItem() == Items.SLIME_BALL) return Effects.NAUSEA;
        if(stack.getItem() == Items.GLOWSTONE_DUST) return Effects.GLOWING;
        if(stack.getItem() == Items.GHAST_TEAR) return Effects.REGENERATION;
        if(stack.getItem() == Items.SUGAR) return Effects.SPEED;
        if(stack.getItem() == Items.IRON_PICKAXE) return Effects.HASTE;
        if(stack.getItem() == Items.BLAZE_POWDER) return Effects.STRENGTH;
        return null;
    }

    public static Effect stringToEffect(String effect){
        if(effect.equals("Poison")) return Effects.POISON;
        if(effect.equals("Weakness")) return Effects.WEAKNESS;
        if(effect.equals("Slowness")) return Effects.SLOWNESS;
        if(effect.equals("MiningFatigue")) return Effects.MINING_FATIGUE;
        if(effect.equals("Wither")) return Effects.WITHER;
        if(effect.equals("Blindness")) return Effects.BLINDNESS;
        if(effect.equals("Hunger")) return Effects.HUNGER;
        if(effect.equals("Nausea")) return Effects.NAUSEA;
        if(effect.equals("Glowing")) return Effects.GLOWING;
        if(effect.equals("Regeneration")) return Effects.REGENERATION;
        if(effect.equals("Speed")) return Effects.SPEED;
        if(effect.equals("Haste")) return Effects.HASTE;
        if(effect.equals("Strength")) return Effects.STRENGTH;
        return null;
    }

    public static String effectToString(Effect effect){
        if(effect == Effects.POISON) return "Poison";
        if(effect == Effects.WEAKNESS) return "Weakness";
        if(effect == Effects.SLOWNESS) return "Slowness";
        if(effect == Effects.MINING_FATIGUE) return "MiningFatigue";
        if(effect == Effects.WITHER) return "Wither";
        if(effect == Effects.BLINDNESS) return "Blindness";
        if(effect == Effects.HUNGER) return "Hunger";
        if(effect == Effects.NAUSEA) return "Nausea";
        if(effect == Effects.GLOWING) return "Glowing";
        if(effect == Effects.REGENERATION) return "Regeneration";
        if(effect == Effects.SPEED) return "Speed";
        if(effect == Effects.HASTE) return "Haste";
        if(effect == Effects.STRENGTH) return "Strength";
        return "";
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);

        // the number here is generally ignored for non-vanilla TileEntities, 0 is safest
        return new SUpdateTileEntityPacket(this.getPos(), 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
    {
        this.read(this.getBlockState(), packet.getNbtCompound());
    }
}
