package com.extra.leaguecraft.item.custom;

import com.extra.leaguecraft.effect.ModEffects;
import com.extra.leaguecraft.entity.custom.ModArrowEntity;
import com.extra.leaguecraft.item.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Repeater extends CrossbowItem {

    public Repeater(Properties properties) {
        super(properties);
    }

    @Override
    public Predicate<ItemStack> getAmmoPredicate() {
        return (stack) -> {
            return stack.getItem().equals(ModItems.MAGAZINE.get().getItem());
        };
    }

    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return (stack) -> {
            return stack.getItem().equals(ModItems.MAGAZINE.get().getItem());
        };
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (isCharged(itemstack)) {
            String type = getBolt(itemstack);
            fireProjectiles(worldIn, playerIn, handIn, itemstack, 3.0f, 0F, type);

            return ActionResult.resultConsume(itemstack);
        } else if (!playerIn.findAmmo(itemstack).isEmpty()) {
            if (!isCharged(itemstack)) {

                playerIn.setActiveHand(handIn);
            }
            return ActionResult.resultFail(itemstack);
        } else {
            return ActionResult.resultFail(itemstack);
        }
    }

    public static void fireProjectiles(World worldIn, LivingEntity shooter, Hand handIn, ItemStack stack, float velocityIn, float inaccuracyIn, String type) {
        System.out.println("Dispara");
        List<ItemStack> list = getChargedProjectiles(stack, type);
        float[] afloat = getRandomSoundPitches(shooter.getRNG());

        for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = list.get(i);
            boolean flag = shooter instanceof PlayerEntity && ((PlayerEntity)shooter).abilities.isCreativeMode;
            if (!itemstack.isEmpty()) {
                if (i == 0) {
                    fireProjectile(worldIn, shooter, handIn, stack, itemstack, afloat[i], flag, velocityIn, inaccuracyIn, 0.0F);
                } else if (i == 1) {
                    fireProjectile(worldIn, shooter, handIn, stack, itemstack, afloat[i], flag, velocityIn, inaccuracyIn, -10.0F);
                } else if (i == 2) {
                    fireProjectile(worldIn, shooter, handIn, stack, itemstack, afloat[i], flag, velocityIn, inaccuracyIn, 10.0F);
                }
            }
        }

        fireProjectilesAfter(worldIn, shooter, stack, type);
    }

    private static void fireProjectilesAfter(World worldIn, LivingEntity shooter, ItemStack stack, String type) {
        if (shooter instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)shooter;
            if (!worldIn.isRemote) {
                CriteriaTriggers.SHOT_CROSSBOW.test(serverplayerentity, stack);
            }

            serverplayerentity.addStat(Stats.ITEM_USED.get(stack.getItem()));

            CompoundNBT nbt = stack.getTag();
            if(type.equals("N")){
                nbt.putInt("Normal", nbt.getInt("Normal")-1);
            }
            else{
                nbt.putInt("Silver", nbt.getInt("Silver")-1);
            }

            if(getLoadedNormal(stack) <= 0 && getLoadedSilver(stack) <= 0){
                clearProjectiles(stack);
                setCharged(stack, false);
                ItemStack mag = ModItems.MAGAZINE.get().getDefaultInstance();
                mag.setDamage(mag.getMaxDamage());
                ((ServerPlayerEntity) shooter).inventory.add(1, mag);
            }

        }
    }


    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        int i = this.getUseDuration(stack) - timeLeft;
        float f = getCharge(i, stack);

        ItemStack ammo = hasAmmo(entityLiving, stack);
        if(ammo.getItem().equals(ModItems.MAGAZINE.get().getItem())){
            assignAmmo(stack, ammo);
        }
        if (f >= 1.0F && !isCharged(stack) && ammo != null) {
            setCharged(stack, true);
            setAmmo(stack);
            SoundCategory soundcategory = entityLiving instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
            worldIn.playSound((PlayerEntity)null, entityLiving.getPosX(), entityLiving.getPosY(), entityLiving.getPosZ(), SoundEvents.ITEM_CROSSBOW_LOADING_END, soundcategory, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    public static void assignAmmo(ItemStack stack, ItemStack ammo) {

        int n = 0;
        int s = 0;
        if (ammo.hasTag()) {
            if (ammo.getTag().contains("Normal")) {
                n = ammo.getTag().getInt("Normal");
            }
            if (ammo.getTag().contains("Silver")) {
                s = ammo.getTag().getInt("Silver");
            }
        }

        CompoundNBT compoundnbt = stack.getOrCreateTag();
        compoundnbt.putInt("NormalTemp", n);
        compoundnbt.putInt("SilverTemp", s);
        stack.setTag(compoundnbt);
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        int n = 0;
        int s = 0;
       if(tooltip.size() == 1){
           tooltip.add(new StringTextComponent("Normal: " + 0));
           tooltip.add(new StringTextComponent("Silver: " + 0));
       }
        if(stack.hasTag()) {
            if(stack.getTag().contains("Normal")){
                n = stack.getTag().getInt("Normal");
            }
            if(stack.getTag().contains("Silver")){
                s = stack.getTag().getInt("Silver");
            }
            tooltip.set(1, new StringTextComponent("Normal: " + n));
            tooltip.set(2, new StringTextComponent("Silver: " + s));
        }
        else{
            tooltip.set(1,new StringTextComponent("Normal: " + 0));
            tooltip.set(2,new StringTextComponent("Silver: " + 0));
        }
    }

    public static void setCharged(ItemStack stack, boolean chargedIn) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        compoundnbt.putBoolean("Charged", chargedIn);
    }

    public static void setAmmo(ItemStack stack){
        int n = 0;
        int s = 0;
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        if(compoundnbt.contains("NormalTemp")){
            n = compoundnbt.getInt("NormalTemp");
        }
        if(compoundnbt.contains("SilverTemp")){
            s = compoundnbt.getInt("SilverTemp");
        }
        compoundnbt.putInt("Normal", n);
        compoundnbt.putInt("Silver", s);
        stack.setTag(compoundnbt);
    }

    public static int getLoadedNormal(ItemStack stack){
        CompoundNBT nbt = stack.getTag();
        if (nbt.contains("Normal"))
        {
            return nbt.getInt("Normal");
        }
        else
        {
            return 0;
        }
    }

    public static int getLoadedSilver(ItemStack stack){
        CompoundNBT nbt = stack.getTag();
        if (nbt.contains("Silver"))
        {
            return nbt.getInt("Silver");
        }
        else
        {
            return 0;
        }
    }

    public String getBolt(ItemStack stack){
        if(getLoadedNormal(stack) > 0 && getLoadedSilver(stack) > 0){
            if(Math.round(Math.random()) == 1){
                return "N";
            }
            else{
                return "S";
            }
        }
        else if(getLoadedNormal(stack) <= 0){
            return "S";
        }
        else{
            return "N";
        }
    }




    private static List<ItemStack> getChargedProjectiles(ItemStack stack, String type) {
        List<ItemStack> list = Lists.newArrayList();
        CompoundNBT compoundnbt = stack.getTag();
        if (compoundnbt != null){
            int s = 0;
            int n = 0;
            if(compoundnbt.contains("Normal")){
                n = compoundnbt.getInt("Normal");
            }
            if(compoundnbt.contains("Silver")){
                s = compoundnbt.getInt("Silver");
            }
            ListNBT listnbt = compoundnbt.getList("ChargedProjectiles", 10);
            if (listnbt != null) {
                if(n>0 && s>0){
                    if(type.equals("N")) {
                        list.add(new ItemStack(ModItems.BOLT.get().getItem()));
                    }
                    else {
                        list.add(new ItemStack(ModItems.SILVER_BOLT.get().getItem()));
                    }
                }
                else if(type.equals("N")){
                    list.add(new ItemStack(ModItems.BOLT.get().getItem()));
                }
                else{
                    list.add(new ItemStack(ModItems.SILVER_BOLT.get().getItem()));
                }
            }
        }

        return list;
    }

    private static float[] getRandomSoundPitches(Random rand) {
        boolean flag = rand.nextBoolean();
        return new float[]{1.0F, getRandomSoundPitch(flag), getRandomSoundPitch(!flag)};
    }

    private static float getRandomSoundPitch(boolean flagIn) {
        float f = flagIn ? 0.63F : 0.43F;
        return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
    }

    private static void fireProjectile(World worldIn, LivingEntity shooter, Hand handIn, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean isCreativeMode, float velocity, float inaccuracy, float projectileAngle) {
        if (!worldIn.isRemote) {
            boolean flag = projectile.getItem() == Items.FIREWORK_ROCKET;
            ProjectileEntity projectileentity;
            if (flag) {
                projectileentity = new FireworkRocketEntity(worldIn, projectile, shooter, shooter.getPosX(), shooter.getPosYEye() - (double)0.15F, shooter.getPosZ(), true);
            } else {
                projectileentity = createArrow(worldIn, shooter, crossbow, projectile);
                ((AbstractArrowEntity)projectileentity).pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
            }

            if (shooter instanceof ICrossbowUser) {
                ICrossbowUser icrossbowuser = (ICrossbowUser)shooter;
                icrossbowuser.fireProjectile(icrossbowuser.getAttackTarget(), crossbow, projectileentity, projectileAngle);
            } else {
                Vector3d vector3d1 = shooter.getUpVector(1.0F);
                Quaternion quaternion = new Quaternion(new Vector3f(vector3d1), projectileAngle, true);
                Vector3d vector3d = shooter.getLook(1.0F);
                Vector3f vector3f = new Vector3f(vector3d);
                vector3f.transform(quaternion);
                projectileentity.shoot((double)vector3f.getX(), (double)vector3f.getY(), (double)vector3f.getZ(), velocity, inaccuracy);
            }

            crossbow.damageItem(flag ? 3 : 1, shooter, (p_220017_1_) -> {
                p_220017_1_.sendBreakAnimation(handIn);
            });
            worldIn.addEntity(projectileentity);
            worldIn.playSound((PlayerEntity)null, shooter.getPosX(), shooter.getPosY(), shooter.getPosZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);
        }
    }

    public static AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter, boolean flag){
        if(flag) {
            ModArrowEntity modArrowentity = new ModArrowEntity(worldIn, shooter);
            modArrowentity.addEffect(new EffectInstance(ModEffects.DAMAGE_UNDEAD.get()));
            modArrowentity.setPotionEffectDamageUndead();
            return modArrowentity;
        }
        else{
            ArrowEntity arrowEntity = new ArrowEntity(worldIn, shooter);
            return  arrowEntity;
        }

    }

    private static AbstractArrowEntity createArrow(World worldIn, LivingEntity shooter, ItemStack crossbow, ItemStack ammo) {
        ArrowItem arrowitem = (ArrowItem)ModItems.BOLT.get().getItem();
        System.out.println(ammo.getItem());
        boolean flag = ammo.getItem().equals(ModItems.SILVER_BOLT.get().getItem());
        System.out.println(flag);
        AbstractArrowEntity abstractarrowentity = createArrow(worldIn, ammo, shooter, flag);
        abstractarrowentity.setDamage(1.0D);
        if (shooter instanceof PlayerEntity) {
            abstractarrowentity.setIsCritical(true);
        }

        abstractarrowentity.setHitSound(SoundEvents.ITEM_CROSSBOW_HIT);
        abstractarrowentity.setShotFromCrossbow(true);
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.PIERCING, crossbow);
        if (i > 0) {
            abstractarrowentity.setPierceLevel((byte)i);
        }

        return abstractarrowentity;
    }

    private static void clearProjectiles(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        if (compoundnbt != null) {
            ListNBT listnbt = compoundnbt.getList("ChargedProjectiles", 9);
            listnbt.clear();
            compoundnbt.put("ChargedProjectiles", listnbt);
        }

    }

    private static float getCharge(int useTime, ItemStack stack) {
        float f = (float)useTime / (float)getChargeTime(stack);
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    private static ItemStack hasAmmo(LivingEntity entityIn, ItemStack stack) {
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.MULTISHOT, stack);
        int j = i == 0 ? 1 : 3;
        boolean flag = entityIn instanceof PlayerEntity && ((PlayerEntity)entityIn).abilities.isCreativeMode;
        ItemStack itemstack = entityIn.findAmmo(stack);
        System.out.println(itemstack);
        ItemStack itemstack1 = itemstack.copy();

        for(int k = 0; k < j; ++k) {
            if (k > 0) {
                itemstack = itemstack1.copy();
            }

            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(ModItems.MAGAZINE.get().getItem());
                itemstack1 = itemstack.copy();
            }

            if (!func_220023_a(entityIn, stack, itemstack, k > 0, flag)) {
                System.out.println("coack");
                return null;
            }
        }
        
        return itemstack1;
    }

    private static boolean func_220023_a(LivingEntity p_220023_0_, ItemStack stack, ItemStack p_220023_2_, boolean p_220023_3_, boolean p_220023_4_) {
        if (p_220023_2_.isEmpty()) {
            return false;
        } else {
            boolean flag = p_220023_4_ && p_220023_2_.getItem() instanceof ArrowItem;
            ItemStack itemstack;
            if (!flag && !p_220023_4_ && !p_220023_3_) {
                itemstack = p_220023_2_.split(1);
                if (p_220023_2_.isEmpty() && p_220023_0_ instanceof PlayerEntity) {
                    ((PlayerEntity)p_220023_0_).inventory.deleteStack(p_220023_2_);
                }
            } else {
                itemstack = p_220023_2_.copy();
            }

            addChargedProjectile(stack, itemstack);
            return true;
        }
    }

    private static void addChargedProjectile(ItemStack crossbow, ItemStack projectile) {
        CompoundNBT compoundnbt = crossbow.getOrCreateTag();
        ListNBT listnbt;
        if (compoundnbt.contains("ChargedProjectiles", 9)) {
            listnbt = compoundnbt.getList("ChargedProjectiles", 10);
        } else {
            listnbt = new ListNBT();
        }

        CompoundNBT compoundnbt1 = new CompoundNBT();
        projectile.write(compoundnbt1);
        listnbt.add(compoundnbt1);
        compoundnbt.put("ChargedProjectiles", listnbt);
    }

}
