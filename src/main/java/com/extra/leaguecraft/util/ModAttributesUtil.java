package com.extra.leaguecraft.util;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class ModAttributesUtil {

    public static void increaseAttrLevel(String attr, PlayerEntity player, int amount){
        assignAttributeLevel(attr, player.getPersistentData().getInt(attr) + amount, player);
    }

    public static void decreaseAttrLevel(String attr, PlayerEntity player, int amount){
        assignAttributeLevel(attr, player.getPersistentData().getInt(attr) - amount, player);
    }

    public static void assignAttributeLevel(String attr, int level, PlayerEntity player){
        player.getPersistentData().putInt(attr, level);
    }

    public static void applyAttributes(String attr, PlayerEntity player){
        if(attr.equals("strength")){
            int i = player.getPersistentData().contains(attr) ? player.getPersistentData().getInt(attr) : 0;
            final AttributeModifier strength = new AttributeModifier(UUID.fromString("456d4189-261e-4261-bb24-204ac4f92326"), "strengthAttribute", i, AttributeModifier.Operation.ADDITION);

            Multimap<Attribute, AttributeModifier> map = ArrayListMultimap.create();
            map.put(Attributes.ATTACK_DAMAGE, strength);
            player.getAttributeManager().removeModifiers(map);

            player.getAttributeManager().createInstanceIfAbsent(Attributes.ATTACK_DAMAGE).applyPersistentModifier(strength);
        }
        else if(attr.equals("health")){
            int i = player.getPersistentData().contains(attr) ? player.getPersistentData().getInt(attr) : 0;
            final AttributeModifier health = new AttributeModifier(UUID.fromString("3162845d-fdc3-4a09-910a-3120235e89cc"), "healthAttribute", i, AttributeModifier.Operation.ADDITION);

            Multimap<Attribute, AttributeModifier> map = ArrayListMultimap.create();
            map.put(Attributes.MAX_HEALTH, health);
            player.getAttributeManager().removeModifiers(map);

            player.getAttributeManager().createInstanceIfAbsent(Attributes.MAX_HEALTH).applyPersistentModifier(health);
        }
    }

    public static void applyAllAttributes(PlayerEntity player){
        applyAttributes("strength", player);
        applyAttributes("health", player);
    }

    public static int getLevelXp(int x){
        return 10*x + 100;
    }

    public static void assignXpToClient(PlayerEntity player, int xp){
        player.getPersistentData().putInt("xp", xp);
    }

    public static void assignLevelToClient(PlayerEntity player, int level){
        player.getPersistentData().putInt("playerLevel", level);
    }

    public static void assignPointsToClient(PlayerEntity player, int points){
        player.getPersistentData().putInt("points", points);
    }
}
