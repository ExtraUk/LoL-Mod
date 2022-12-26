package com.extra.leaguecraft.network;

import com.extra.leaguecraft.tileentity.HextechSynthesizerTile;
import com.extra.leaguecraft.util.ModAttributesUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ClientBoundXpUpdatePacket {

    public final int xp;
    public final int level;
    public final int points;

    public ClientBoundXpUpdatePacket(int xp, int level, int points){
        this.xp = xp;
        this.level = level;
        this.points = points;
    }

    public void encode(PacketBuffer buffer){
        buffer.writeInt(this.xp);
        buffer.writeInt(this.level);
        buffer.writeInt(this.points);
    }

    public static ClientBoundXpUpdatePacket decode(PacketBuffer buffer){
        return new ClientBoundXpUpdatePacket(buffer.readInt(), buffer.readInt(), buffer.readInt());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx){


        PlayerEntity player = Minecraft.getInstance().player;
        ctx.get().enqueueWork(() ->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    ModAttributesUtil.assignXpToClient(player, this.xp));
                    ModAttributesUtil.assignLevelToClient(player, this.level);
                    ModAttributesUtil.assignPointsToClient(player, this.points);
        });
        ctx.get().setPacketHandled(true);
        return true;
    }

}
