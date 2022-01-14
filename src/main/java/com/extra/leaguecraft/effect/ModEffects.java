package com.extra.leaguecraft.effect;

import com.extra.leaguecraft.LeagueCraft;
import net.minecraft.item.Item;
import net.minecraft.potion.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects{

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, LeagueCraft.MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, LeagueCraft.MOD_ID);

    public static final RegistryObject<Effect> DAMAGE_UNDEAD = EFFECTS.register("damage_undead",
            () -> new DamageUndead());

    public static final RegistryObject<Potion> DAMAGE_UNDEAD_POTION = POTIONS.register("damage_undead",
            () -> new Potion(new EffectInstance(DAMAGE_UNDEAD.get(), 0, 0)));
}
