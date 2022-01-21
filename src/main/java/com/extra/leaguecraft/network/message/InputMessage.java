package com.extra.leaguecraft.network.message;

import com.extra.leaguecraft.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;


public class InputMessage {

    public int key;

    public InputMessage(){

    }

    public InputMessage(int key){
        this.key = key;
    }

    public static void encode(InputMessage message, PacketBuffer buffer){
        buffer.writeInt(message.key);
    }

    public static InputMessage decode(PacketBuffer buffer){
        return new InputMessage(buffer.readInt());
    }

    public static void handle(InputMessage message, Supplier<NetworkEvent.Context> contextSupplier){
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayerEntity player = context.getSender();

            ItemStack stack = player.inventory.armorItemInSlot(2).getStack();
            if(!stack.isEmpty()){
                if(stack.getItem().equals(ModItems.TURBO_CHEMTANK_CHEST.get())) {
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
}
