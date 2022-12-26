package com.extra.leaguecraft.event;

import com.extra.leaguecraft.LeagueCraft;
import com.extra.leaguecraft.entity.ModEntityTypes;
import com.extra.leaguecraft.network.ClientBoundXpUpdatePacket;
import com.extra.leaguecraft.network.ModPacketHandler;
import com.extra.leaguecraft.util.ModAttributesUtil;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = LeagueCraft.MOD_ID)
public class ModEvents  {

    @SubscribeEvent
    public static void onEntitySpawn(LivingSpawnEvent.CheckSpawn event){
        if(event.getEntity().getType().equals(ModEntityTypes.BRACKERN.get())){
            if(event.getY() > 50){
                event.getEntity().remove();
            }
        }
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event){
        if(event.getSource().getTrueSource() instanceof PlayerEntity){
            PlayerEntity player = ((PlayerEntity) event.getSource().getTrueSource());
            Entity entity = event.getEntity();

            int xp = entity instanceof MonsterEntity || entity instanceof GhastEntity || entity instanceof SlimeEntity ? 5 :
                     entity instanceof EnderDragonEntity ? 100 :
                     entity instanceof MobEntity ? 1 :
                     entity instanceof PlayerEntity ? 7 : 0;

            int currentXp = player.getPersistentData().getInt("xp");
            int level = player.getPersistentData().getInt("playerLevel");
            int points = player.getPersistentData().getInt("points");

            if(player.getPersistentData().contains("xp")){

                if(currentXp + xp >= ModAttributesUtil.getLevelXp(level)){
                    player.getPersistentData().putInt("xp", 0);
                    xp = currentXp + xp - ModAttributesUtil.getLevelXp(level);
                    currentXp = 0;
                    player.getPersistentData().putInt("playerLevel", ++level);
                    player.getPersistentData().putInt("points", player.getPersistentData().getInt("points") + 1);
                    points = player.getPersistentData().getInt("points");
                }

                player.getPersistentData().putInt("xp", currentXp + xp);
                ModPacketHandler.INSTANCE.send(PacketDistributor.DIMENSION.with(()-> player.world.getDimensionKey()), new ClientBoundXpUpdatePacket(currentXp + xp, level, points));
            }
            else{
                player.getPersistentData().putInt("xp", xp);
                ModPacketHandler.INSTANCE.send(PacketDistributor.DIMENSION.with(()-> player.world.getDimensionKey()), new ClientBoundXpUpdatePacket(xp, level, points));
            }
            ModAttributesUtil.applyAllAttributes(player);
        }
    }

    @SubscribeEvent
    public static void onLogIn(PlayerEvent.PlayerLoggedInEvent event){
        ModPacketHandler.INSTANCE.send(PacketDistributor.DIMENSION.with(()->
                event.getPlayer().world.getDimensionKey()),
                new ClientBoundXpUpdatePacket(event.getPlayer().getPersistentData().getInt("xp"),
                        event.getPlayer().getPersistentData().getInt("level"),
                        event.getPlayer().getPersistentData().getInt("points")));
    }

}
