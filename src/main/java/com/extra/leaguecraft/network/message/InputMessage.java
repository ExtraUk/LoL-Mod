package com.extra.leaguecraft.network.message;

import com.extra.leaguecraft.item.ModItems;
import com.extra.leaguecraft.screen.SynthesizerRecipeBookScreen;
import com.extra.leaguecraft.util.ModSoundEvents;
import net.java.games.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.logging.log4j.core.jmx.Server;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

import java.awt.event.KeyEvent;
import java.util.function.Supplier;


public class InputMessage {

    public int key;

    public InputMessage(){

    }

    public InputMessage(int key){
        this.key = key;
    }

    public static void encode(InputMessage message, PacketBuffer buffer){
        buffer.writeInt(message.key);
    }

    public static InputMessage decode(PacketBuffer buffer){
        return new InputMessage(buffer.readInt());
    }

    public static void handle(InputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (message.key == KeyEvent.VK_G) {
            activateChemtank(context);
        }
        else if(message.key == 1){
            hextechGauntletDash(context);
        }

    }

    public static void activateChemtank(NetworkEvent.Context context){
        context.enqueueWork(() -> {
            ServerPlayerEntity player = context.getSender();

            ItemStack stack = player.inventory.armorItemInSlot(2).getStack();
            if (!stack.isEmpty()) {
                if (stack.getItem().equals(ModItems.TURBO_CHEMTANK_CHEST.get())) {
                    if (stack.getOrCreateTag().contains("active")) {
                        if (stack.getTag().getBoolean("active")) {
                            player.inventory.armorItemInSlot(2).getStack().getTag().putBoolean("active", false);
                        } else {
                            player.inventory.armorItemInSlot(2).getStack().getTag().putBoolean("active", true);
                        }
                    } else {
                        player.inventory.armorItemInSlot(2).getStack().getTag().putBoolean("active", true);
                    }
                }
            }

        });
        context.setPacketHandled(true);
    }

    public static void hextechGauntletDash(NetworkEvent.Context context){
        ICuriosHelper helper = CuriosApi.getCuriosHelper();
        ServerPlayerEntity player = context.getSender();

        LazyOptional<IItemHandlerModifiable> equippedCurios = helper.getEquippedCurios(player);

        player.world.playSound(player, new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ()), ModSoundEvents.HEXTECH_GAUNTLET_DASH.get(), SoundCategory.PLAYERS, 1F, 1F);
        equippedCurios.ifPresent(h -> {
            for(int i = 0; i < h.getSlots(); i++){
                if(h.getStackInSlot(i).getItem() == ModItems.HEXTECH_GAUNTLET.get()){
                    ItemStack gauntlet = ModItems.HEXTECH_GAUNTLET.get().getDefaultInstance();
                    if(h.getStackInSlot(i).getDamage() < gauntlet.getMaxDamage()-10) {
                        gauntlet.setDamage(h.getStackInSlot(i).getDamage() + 10);
                        h.setStackInSlot(i, gauntlet);
                    }
                    else{
                        gauntlet.setDamage(gauntlet.getMaxDamage());
                        h.setStackInSlot(i, gauntlet);
                    }

                    h.getStackInSlot(i).getTag().putInt("Cooldown", 100);
                }
            }
        });
    }
}
