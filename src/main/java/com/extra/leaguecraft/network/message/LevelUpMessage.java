package com.extra.leaguecraft.network.message;

import com.extra.leaguecraft.item.ModItems;
import com.extra.leaguecraft.util.ModAttributesUtil;
import com.extra.leaguecraft.util.ModSoundEvents;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

import java.awt.event.KeyEvent;
import java.util.function.Supplier;


public class LevelUpMessage {

    public String attribute;

    public LevelUpMessage(){

    }

    public LevelUpMessage(String attribute){
        this.attribute = attribute;
    }

    public static void encode(LevelUpMessage message, PacketBuffer buffer){
        buffer.writeString(message.attribute);
    }

    public static LevelUpMessage decode(PacketBuffer buffer){
        return new LevelUpMessage(buffer.readString());
    }

    public static void handle(LevelUpMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (message.attribute.equals("strength")) {
            context.enqueueWork(() -> {
                ServerPlayerEntity player = context.getSender();
                player.getPersistentData().putInt("points", player.getPersistentData().getInt("points") - 1);
                player.getPersistentData().putInt("strength", player.getPersistentData().getInt("strength") + 1);
            });
            context.setPacketHandled(true);
        }
        else if(message.attribute.equals("health")){
            context.enqueueWork(() -> {
                ServerPlayerEntity player = context.getSender();
                player.getPersistentData().putInt("points", player.getPersistentData().getInt("points") - 1);
                player.getPersistentData().putInt("health", player.getPersistentData().getInt("health") + 1);
            });
            context.setPacketHandled(true);
        }
        else if(message.attribute.equals("magic")){
            context.enqueueWork(() -> {
                ServerPlayerEntity player = context.getSender();
                player.getPersistentData().putInt("points", player.getPersistentData().getInt("points") - 1);
                player.getPersistentData().putInt("magic", player.getPersistentData().getInt("magic") + 1);
            });
            context.setPacketHandled(true);
        }

        ModAttributesUtil.applyAllAttributes(context.getSender());

    }

    public static void activateChemtank(NetworkEvent.Context context){
        context.enqueueWork(() -> {
            ServerPlayerEntity player = context.getSender();
            player.getPersistentData().putInt("points", player.getPersistentData().getInt("points") - 1);
            player.getPersistentData().putInt("strength", player.getPersistentData().getInt("strength") + 1);

            ItemStack stack = player.inventory.armorItemInSlot(2).getStack();
            if (!stack.isEmpty()) {
                if (stack.getItem().equals(ModItems.TURBO_CHEMTANK_CHEST.get())) {
                    if (stack.getOrCreateTag().contains("active")) {
                        if (stack.getTag().getBoolean("active")) {
                            player.inventory.armorItemInSlot(2).getStack().getTag().putBoolean("active", false);
                        } else {
                            player.inventory.armorItemInSlot(2).getStack().getTag().putBoolean("active", true);
                        }
                    } else {
                        player.inventory.armorItemInSlot(2).getStack().getTag().putBoolean("active", true);
                    }
                }
            }

        });
        context.setPacketHandled(true);
    }

    public static void hextechGauntletDash(NetworkEvent.Context context){
        ICuriosHelper helper = CuriosApi.getCuriosHelper();
        ServerPlayerEntity player = context.getSender();

        LazyOptional<IItemHandlerModifiable> equippedCurios = helper.getEquippedCurios(player);

        player.world.playSound(player, new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ()), ModSoundEvents.HEXTECH_GAUNTLET_DASH.get(), SoundCategory.PLAYERS, 1F, 1F);
        equippedCurios.ifPresent(h -> {
            for(int i = 0; i < h.getSlots(); i++){
                if(h.getStackInSlot(i).getItem() == ModItems.HEXTECH_GAUNTLET.get()){
                    ItemStack gauntlet = ModItems.HEXTECH_GAUNTLET.get().getDefaultInstance();
                    if(h.getStackInSlot(i).getDamage() < gauntlet.getMaxDamage()-10) {
                        gauntlet.setDamage(h.getStackInSlot(i).getDamage() + 10);
                        h.setStackInSlot(i, gauntlet);
                    }
                    else{
                        gauntlet.setDamage(gauntlet.getMaxDamage());
                        h.setStackInSlot(i, gauntlet);
                    }

                    h.getStackInSlot(i).getTag().putInt("Cooldown", 100);
                }
            }
        });
    }
}
