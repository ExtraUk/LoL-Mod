package com.extra.leaguecraft.util;

import com.extra.leaguecraft.LeagueCraft;
import net.minecraft.client.audio.Sound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LeagueCraft.MOD_ID);

    public static final RegistryObject<SoundEvent> HEXTECH_GAUNTLET_HIT = registerSoundEvent("hextech_gauntlet_hit");
    public static final RegistryObject<SoundEvent> HEXTECH_GAUNTLET_DASH = registerSoundEvent("hextech_gauntlet_dash");
    public static final RegistryObject<SoundEvent> HEXTECH_SWORD_HIT = registerSoundEvent("hextech_sword_hit");
    public static final RegistryObject<SoundEvent> ELECTROCUTED_EFFECT = registerSoundEvent("electrocuted_effect");
    public static final RegistryObject<SoundEvent> CHEMTECH_GRENADE = registerSoundEvent("chemtech_grenade");
    public static final RegistryObject<SoundEvent> BLITZCRANK_HOOK_LAUNCH = registerSoundEvent("blitzcrank_hook_launch");
    public static final RegistryObject<SoundEvent> BLITZCRANK_HOOK_HIT = registerSoundEvent("blitzcrank_hook_hit");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name){
        return SOUND_EVENT.register(name, () -> new SoundEvent(new ResourceLocation(LeagueCraft.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENT.register(eventBus);
    }
}
