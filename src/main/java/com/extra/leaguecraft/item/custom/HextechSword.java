package com.extra.leaguecraft.item.custom;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.item.ModItemGroup;
import com.extra.leaguecraft.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.Hand;

public class HextechSword extends SwordItem{
    public HextechSword() {
        super(new IItemTier() {
            @Override
            public int getMaxUses() {
                return 512;
            }

            @Override
            public float getEfficiency() {
                return 0;
            }

            @Override
            public float getAttackDamage() {
                return 0;
            }

            @Override
            public int getHarvestLevel() {
                return 0;
            }

            @Override
            public int getEnchantability() {
                return 0;
            }

            @Override
            public Ingredient getRepairMaterial() {
                return null;
            }
        }, 5, -2.3f, new Properties().group(ModItemGroup.LEAGUECRAFT_GROUP));
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean flag = false;
        if(stack.getDamage() == this.getMaxDamage(stack) - 1){
            if(attacker instanceof PlayerEntity){
                int slot = ((PlayerEntity) attacker).inventory.getSlotFor(stack);
                ItemStack brokenSword = ModItems.HEXTECH_SWORD_BROKEN.get().getDefaultInstance();
                ((PlayerEntity) attacker).inventory.setInventorySlotContents(slot, brokenSword);
            }
        }
        else{
            stack.damageItem(1, attacker, (entity) -> {
                entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }
        return true;
    }
}
