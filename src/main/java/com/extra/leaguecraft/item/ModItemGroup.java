package com.extra.leaguecraft.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {

    public static final ItemGroup LEAGUECRAFT_GROUP = new ItemGroup("leaguecraftTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.LEAGUE_ICON.get());
        }
    };
}
