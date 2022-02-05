package com.extra.leaguecraft.item.custom;

import com.extra.leaguecraft.item.ModItems;
import com.extra.leaguecraft.render.curio.CurioRenderers;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class IronGauntletItem extends Item implements ICurioItem {

    public IronGauntletItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        PlayerEntity player = ((PlayerEntity) livingEntity);

        if(stack.getDamage() >= stack.getMaxDamage()) return;

        ICuriosHelper helper = CuriosApi.getCuriosHelper();
        LazyOptional<IItemHandlerModifiable> equippedCurios = helper.getEquippedCurios(player);
        final AtomicInteger gauntletsEquipped = new AtomicInteger(0);
        equippedCurios.ifPresent(h -> {
            for(int i = 0; i < h.getSlots(); i++){
                if(h.getStackInSlot(i).getItem() == ModItems.IRON_GAUNTLET.get()){
                    gauntletsEquipped.incrementAndGet();
                }
            }
        });



        final AttributeModifier punchStrength = new AttributeModifier(UUID.fromString("ebc26481-3efc-4683-a5ae-68c96d146e02"), "ironGauntletsPunch", 2D * gauntletsEquipped.get(), AttributeModifier.Operation.ADDITION);
        final AttributeModifier punchKnockBack = new AttributeModifier(UUID.fromString("c51bffab-a917-4b91-8302-9bbfb940c34a"), "ironGauntletKnockBack", 1D, AttributeModifier.Operation.ADDITION);

        if(!player.world.isRemote()) {
            if (player.getHeldItem(Hand.MAIN_HAND) == ItemStack.EMPTY && player.getHeldItem(Hand.OFF_HAND) == ItemStack.EMPTY) {

                Multimap<Attribute, AttributeModifier> map = ArrayListMultimap.create();
                map.put(Attributes.ATTACK_DAMAGE, punchStrength);
                map.put(Attributes.ATTACK_KNOCKBACK, punchKnockBack);
                player.getAttributeManager().removeModifiers(map);

                player.getAttributeManager().createInstanceIfAbsent(Attributes.ATTACK_DAMAGE).applyPersistentModifier(punchStrength);

                if(player.getAttributeValue(Attributes.ATTACK_KNOCKBACK) < 1) {
                    player.getAttributeManager().createInstanceIfAbsent(Attributes.ATTACK_KNOCKBACK).applyPersistentModifier(punchKnockBack);
                }

            }
            else{
                Multimap<Attribute, AttributeModifier> map = ArrayListMultimap.create();
                map.put(Attributes.ATTACK_DAMAGE, punchStrength);
                map.put(Attributes.ATTACK_KNOCKBACK, punchKnockBack);
                player.getAttributeManager().removeModifiers(map);
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        PlayerEntity player = ((PlayerEntity) slotContext.getWearer());

        if(!player.world.isRemote()){

            final AttributeModifier punchStrength = new AttributeModifier(UUID.fromString("ebc26481-3efc-4683-a5ae-68c96d146e02"), "ironGauntletsPunch", 2D, AttributeModifier.Operation.ADDITION);
            final AttributeModifier punchKnockBack = new AttributeModifier(UUID.fromString("c51bffab-a917-4b91-8302-9bbfb940c34a"), "ironGauntletKnockBack", 1D, AttributeModifier.Operation.ADDITION);

            Multimap<Attribute, AttributeModifier> map = ArrayListMultimap.create();
            map.put(Attributes.ATTACK_DAMAGE, punchStrength);
            map.put(Attributes.ATTACK_KNOCKBACK, punchKnockBack);
            player.getAttributeManager().removeModifiers(map);
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        return CurioRenderers.getRenderer(this) != null;
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ticks, float headYaw, float headPitch, ItemStack stack) {
        CurioRenderers.getRenderer(this).render(identifier, index, matrixStack, buffer, light, entity, limbSwing, limbSwingAmount, partialTicks, ticks, headYaw, headPitch, stack);
    }

}
