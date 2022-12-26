package com.extra.leaguecraft.entity.custom;

import com.extra.leaguecraft.entity.goal.HookAttackGoal;
import com.extra.leaguecraft.network.ModNetwork;
import com.extra.leaguecraft.network.message.HookMessage;
import com.extra.leaguecraft.network.message.InputMessage;
import com.extra.leaguecraft.util.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class BlitzcrankEntity extends IronGolemEntity implements IAnimatable, IRangedAttackMob {
    private static final DataParameter<Byte> IS_HOOKING = EntityDataManager.createKey(BlitzcrankEntity.class, DataSerializers.BYTE);
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static int maxCooldown = 200;
    private int cooldown = maxCooldown;
    private int ticksSinceHook = 0;
    private int hookAnimTicks = 0;
    public boolean queueHook = false;

    public BlitzcrankEntity(EntityType<? extends IronGolemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 150.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 10.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 50.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new HookAttackGoal(this,1.0d,20,20));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5D, true));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.addGoal(2, new ReturnToVillageGoal(this, 0.6D, false));
        this.goalSelector.addGoal(4, new PatrolVillageGoal(this, 0.6D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new DefendVillageTargetGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MobEntity.class, 5, false, false, (p_234199_0_) -> {
            return p_234199_0_ instanceof IMob && !(p_234199_0_ instanceof CreeperEntity);
        }));
        this.targetSelector.addGoal(4, new ResetAngerGoal<>(this, false));
    }

    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        this.hookAnimTicks = 0;
        BlitzcrankFistEntity blitzcrankFist = new BlitzcrankFistEntity(this.world);
        blitzcrankFist.teleportKeepLoaded(this.getPosX(),this.getPosY()+2,this.getPosZ());
        blitzcrankFist.setBlitzShooter(this);
        blitzcrankFist.setShooter(this);
        double d0 = target.getPosYEye() - (double)1.1F;
        double d1 = target.getPosX() - this.getPosX();
        double d2 = d0 - blitzcrankFist.getPosY();
        double d3 = target.getPosZ() - this.getPosZ();
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        blitzcrankFist.shoot(d1, d2 + (double)f, d3, 1.6F, 0.0F);
        this.playSound(ModSoundEvents.BLITZCRANK_HOOK_LAUNCH.get(), 0.7F, 1.0f);
        this.world.addEntity(blitzcrankFist);
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && this.ticksSinceLastSwing >= 10 && this.ticksSinceHook > 15) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.blitzcrank.walk", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.blitzcrank.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
        data.addAnimationController(new AnimationController(this, "attackController", 0, this::attackPredicate));
        data.addAnimationController(new AnimationController(this, "rocketGrabController", 0, this::rocketGrabPredicate));
    }

    private PlayState attackPredicate(AnimationEvent event){
        if(this.isSwingInProgress && event.getController().getAnimationState().equals(AnimationState.Stopped) && this.ticksSinceHook > 15){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.blitzcrank.attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            this.isSwingInProgress = false;
            this.ticksSinceLastSwing = 0;
        }
        return PlayState.CONTINUE;
    }

    private PlayState rocketGrabPredicate(AnimationEvent event){
        if(((BlitzcrankEntity)event.getAnimatable()).getIsHooking() && event.getController().getAnimationState().equals(AnimationState.Stopped)){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.blitzcrank.rocket_grab", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
            ModNetwork.CHANNEL.sendToServer(new HookMessage(false, this.getEntityId()));
            ((BlitzcrankEntity)event.getAnimatable()).setIsHooking(false);
            ((BlitzcrankEntity)event.getAnimatable()).setTicksSinceHook(0);
        }
        return PlayState.CONTINUE;
    }


    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void tick() {
        this.ticksSinceLastSwing++;
        this.ticksSinceHook++;
        if(this.cooldown > 0){
            this.cooldown--;
        }
        if(!this.world.isRemote) {
            if (this.queueHook && hookAnimTicks <= 14) {
                this.hookAnimTicks++;
            } else if(this.queueHook){
                this.goalSelector.getRunningGoals().anyMatch(goal -> {
                    if(goal.getGoal() instanceof HookAttackGoal){
                        HookAttackGoal g = (HookAttackGoal)goal.getGoal();
                        g.fire();
                        return true;
                    }
                    return false;
                });
                this.hookAnimTicks = 0;
                this.queueHook = false;
            }
        }
        super.tick();
    }

    @Override
    public void registerData(){
        super.registerData();
        this.dataManager.register(IS_HOOKING, (byte)0);
    }


    public int getCooldown(){
        return this.cooldown;
    }

    public void resetCooldown(){
        this.cooldown = maxCooldown;
    }

    public boolean getIsHooking(){
        return (this.dataManager.get(IS_HOOKING) & 1) != 0;
    }
    //setIsHooking(false) is always from client not from server, make custom message
    public void setIsHooking(boolean hooking) {
        byte b0 = this.dataManager.get(IS_HOOKING);
        if (hooking) {
            this.dataManager.set(IS_HOOKING, (byte)(b0 | 1));
        } else {
            this.dataManager.set(IS_HOOKING, (byte)(b0 & -2));
            this.dataManager.setClean();
        }

    }

    public void setTicksSinceHook(int ticks){
        this.ticksSinceHook = ticks;
    }

    public void incrementHookAnimTicks(){
        this.hookAnimTicks++;
    }

    public int getHookAnimTicks(){
        return hookAnimTicks;
    }
}
