package com.extra.leaguecraft.event;

import com.extra.leaguecraft.LeagueCraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.event.KeyEvent;

public class KeyInit {

    public static KeyBinding activateKey;
    public static KeyBinding levelUpMenuKey;

    public static void register(final FMLClientSetupEvent event){
        activateKey = create("activate_key", KeyEvent.VK_G);
        levelUpMenuKey = create("level_up_menu_key", KeyEvent.VK_L);

        ClientRegistry.registerKeyBinding(activateKey);
        ClientRegistry.registerKeyBinding(levelUpMenuKey);
    }

    private static KeyBinding create(String name, int key){
        return new KeyBinding("key." + LeagueCraft.MOD_ID + "." + name, key, "key.category." + LeagueCraft.MOD_ID);
    }
}
