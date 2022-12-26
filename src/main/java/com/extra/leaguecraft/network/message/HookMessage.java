package com.extra.leaguecraft.network.message;

import com.extra.leaguecraft.entity.custom.BlitzcrankEntity;
import com.extra.leaguecraft.item.ModItems;
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

public class HookMessage {
    public boolean isHooking;
    public int blitzId;

    public HookMessage(){

    }

    public HookMessage(boolean isHooking, int blitzId){
        this.isHooking = isHooking;
        this.blitzId = blitzId;
    }

    public static void encode(HookMessage message, PacketBuffer buffer){
        buffer.writeBoolean(message.isHooking);
        buffer.writeInt(message.blitzId);
    }

    public static HookMessage decode(PacketBuffer buffer){
        return new HookMessage(buffer.readBoolean(), buffer.readInt());
    }

    public static void handle(HookMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        setBlitzcrankEntityIsHooking(context, message.isHooking, message.blitzId);

    }

    public static void setBlitzcrankEntityIsHooking(NetworkEvent.Context context, boolean isHooking, int blitzId){
        context.enqueueWork(() -> {
            ((BlitzcrankEntity)context.getSender().world.getEntityByID(blitzId)).setIsHooking(isHooking);
            if(!isHooking){
                ((BlitzcrankEntity)context.getSender().world.getEntityByID(blitzId)).setTicksSinceHook(0);
            }
        });
        context.setPacketHandled(true);
    }
}
