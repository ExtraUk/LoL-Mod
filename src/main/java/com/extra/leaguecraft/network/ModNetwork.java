package com.extra.leaguecraft.network;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.network.message.HookMessage;
import com.extra.leaguecraft.network.message.InputMessage;
import com.extra.leaguecraft.network.message.LevelUpMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ModNetwork {

    public static final String NETWORK_VERSION = "0.1.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry
            .newSimpleChannel(new ResourceLocation(LeagueCraft.MOD_ID, "network"), () -> NETWORK_VERSION,
            version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION));

    public static void init(){
        CHANNEL.registerMessage(0, InputMessage.class, InputMessage::encode, InputMessage::decode, InputMessage::handle);
        CHANNEL.registerMessage(1, LevelUpMessage.class, LevelUpMessage::encode, LevelUpMessage::decode, LevelUpMessage::handle);
        CHANNEL.registerMessage(2, HookMessage.class, HookMessage::encode, HookMessage::decode, HookMessage::handle);
    }
}
