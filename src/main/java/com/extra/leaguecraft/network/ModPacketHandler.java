package com.extra.leaguecraft.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.lwjgl.system.windows.MSG;

import java.util.function.BiConsumer;

public final class ModPacketHandler {

    private ModPacketHandler(){}

    private static final String PROTOCOL_VERSION = "1";
    private static int id = 0;
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("leaguecraft", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init(){
        int index = 0;
        INSTANCE.registerMessage(index++,
                ServerBoundSynthUpdatePacket.class,
                ServerBoundSynthUpdatePacket::encode,
                ServerBoundSynthUpdatePacket::decode,
                ServerBoundSynthUpdatePacket::handle);

        /*INSTANCE.messageBuilder(ServerBoundSynthUpdatePacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ServerBoundSynthUpdatePacket::encode)
                .decoder(ServerBoundSynthUpdatePacket::decode)
                .consumer(ServerBoundSynthUpdatePacket::handle).add();*/
    }


}
