package com.extra.leaguecraft.item.custom;

import com.extra.leaguecraft.effect.ModEffects;
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
import net.minecraft.potion.EffectInstance;
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
import java.util.concurrent.atomic.AtomicInteger;

public class ChemtechCanisterItem extends Item implements ICurioItem {

    public ChemtechCanisterItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        PlayerEntity player = ((PlayerEntity) livingEntity);

        if(stack.getDamage() >= stack.getMaxDamage()) return;

        ICuriosHelper helper = CuriosApi.getCuriosHelper();
        LazyOptional<IItemHandlerModifiable> equippedCurios = helper.getEquippedCurios(player);
        final AtomicInteger canistersEquipped = new AtomicInteger();
        canistersEquipped.set(0);
        equippedCurios.ifPresent(h -> {
            for(int i = 0; i < h.getSlots(); i++){
                if(h.getStackInSlot(i).getItem() == ModItems.CHEMTECH_CANISTER.get()){
                    canistersEquipped.set(canistersEquipped.get() + 1);
                }
            }
        });
        stack.setDamage(stack.getDamage()+1);
        if((player.getActivePotionEffect(ModEffects.ULTRA_SHIMMER.get()) != null && !(player.getActivePotionEffect(ModEffects.ULTRA_SHIMMER.get()).getDuration() % 3 == 0)) || player.getActivePotionEffect(ModEffects.ULTRA_SHIMMER.get()) == null){
            player.addPotionEffect(new EffectInstance(ModEffects.ULTRA_SHIMMER.get(),20,canistersEquipped.get()-1));
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
        CurioRenderers.getRenderer(this).render(identifier, index, matrixStack, buffer, light, entity, limbSwing, limbSwingAmount, partialTicks, ticks, headYaw, headPitch, stack);    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Charge with shimmer to use"));
    }
}
