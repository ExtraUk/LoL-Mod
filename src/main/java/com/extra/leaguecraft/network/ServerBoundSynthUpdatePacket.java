package com.extra.leaguecraft.network;

import com.extra.leaguecraft.tileentity.HextechSynthesizerTile;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ServerBoundSynthUpdatePacket {

    public final BlockPos pos;

    public ServerBoundSynthUpdatePacket(BlockPos pos){
        this.pos = pos;
    }

    public void encode(PacketBuffer buffer){
        buffer.writeBlockPos(this.pos);
    }

    public static ServerBoundSynthUpdatePacket decode(PacketBuffer buffer){
        return new ServerBoundSynthUpdatePacket(buffer.readBlockPos());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() ->{
            TileEntity tile = ctx.get().getSender().world.getTileEntity(this.pos);
            if(tile instanceof HextechSynthesizerTile){
                ((HextechSynthesizerTile) tile).disposeContents();
            }
        });
        ctx.get().setPacketHandled(true);
        return true;
    }

}
