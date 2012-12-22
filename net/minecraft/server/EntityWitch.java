package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class EntityWitch extends EntityMonster implements IRangedEntity
{
    /** List of items a witch should drop on death. */
    private static final int[] d = new int[] {Item.GLOWSTONE_DUST.id, Item.SUGAR.id, Item.REDSTONE.id, Item.SPIDER_EYE.id, Item.GLASS_BOTTLE.id, Item.SULPHUR.id, Item.STICK.id, Item.STICK.id};

    /**
     * Timer used as interval for a witch's attack, decremented every tick if aggressive and when reaches zero the witch
     * will throw a potion at the target entity.
     */
    private int e = 0;

    public EntityWitch(World par1World)
    {
        super(par1World);
        this.texture = "/mob/villager/witch.png";
        this.bH = 0.25F;
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, this.bH, 60, 10.0F));
        this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, this.bH));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0F, 0, true));
    }

    protected void a()
    {
        super.a();
        this.getDataWatcher().a(21, Byte.valueOf((byte) 0));
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "mob.witch.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.witch.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.witch.death";
    }

    /**
     * Set whether this witch is aggressive at an entity.
     */
    public void f(boolean par1)
    {
        this.getDataWatcher().watch(21, Byte.valueOf((byte) (par1 ? 1 : 0)));
    }

    /**
     * Return whether this witch is aggressive at an entity.
     */
    public boolean m()
    {
        return this.getDataWatcher().getByte(21) == 1;
    }

    public int getMaxHealth()
    {
        return 26;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean be()
    {
        return true;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        if (!this.world.isStatic)
        {
            if (this.m())
            {
                if (this.e-- <= 0)
                {
                    this.f(false);
                    ItemStack var1 = this.bD();
                    this.setEquipment(0, (ItemStack) null);

                    if (var1 != null && var1.id == Item.POTION.id)
                    {
                        List var2 = Item.POTION.g(var1);

                        if (var2 != null)
                        {
                            Iterator var3 = var2.iterator();

                            while (var3.hasNext())
                            {
                                MobEffect var4 = (MobEffect)var3.next();
                                this.addEffect(new MobEffect(var4));
                            }
                        }
                    }
                }
            }
            else
            {
                short var5 = -1;

                if (this.random.nextFloat() < 0.15F && this.isBurning() && !this.hasEffect(MobEffectList.FIRE_RESISTANCE))
                {
                    var5 = 16307;
                }
                else if (this.random.nextFloat() < 0.05F && this.health < this.getMaxHealth())
                {
                    var5 = 16341;
                }
                else if (this.random.nextFloat() < 0.25F && this.aG() != null && !this.hasEffect(MobEffectList.FASTER_MOVEMENT) && this.aG().e(this) > 121.0D)
                {
                    var5 = 16274;
                }
                else if (this.random.nextFloat() < 0.25F && this.aG() != null && !this.hasEffect(MobEffectList.FASTER_MOVEMENT) && this.aG().e(this) > 121.0D)
                {
                    var5 = 16274;
                }

                if (var5 > -1)
                {
                    this.setEquipment(0, new ItemStack(Item.POTION, 1, var5));
                    this.e = this.bD().m();
                    this.f(true);
                }
            }

            if (this.random.nextFloat() < 7.5E-4F)
            {
                this.world.broadcastEntityEffect(this, (byte) 15);
            }
        }

        super.c();
    }

    /**
     * Reduces damage, depending on potions
     */
    protected int c(DamageSource par1DamageSource, int par2)
    {
        par2 = super.c(par1DamageSource, par2);

        if (par1DamageSource.getEntity() == this)
        {
            par2 = 0;
        }

        if (par1DamageSource.o())
        {
            par2 = (int)((double)par2 * 0.15D);
        }

        return par2;
    }

    /**
     * This method returns a value to be applied directly to entity speed, this factor is less than 1 when a slowdown
     * potion effect is applied, more than 1 when a haste potion effect is applied and 2 for fleeing entities.
     */
    public float bB()
    {
        float var1 = super.bB();

        if (this.m())
        {
            var1 *= 0.75F;
        }

        return var1;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        int var3 = this.random.nextInt(3) + 1;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            int var5 = this.random.nextInt(3);
            int var6 = d[this.random.nextInt(d.length)];

            if (par2 > 0)
            {
                var5 += this.random.nextInt(par2 + 1);
            }

            for (int var7 = 0; var7 < var5; ++var7)
            {
                this.b(var6, 1);
            }
        }
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void d(EntityLiving par1EntityLiving)
    {
        if (!this.m())
        {
            EntityPotion var2 = new EntityPotion(this.world, this, 32732);
            var2.pitch -= -20.0F;
            double var3 = par1EntityLiving.locX + par1EntityLiving.motX - this.locX;
            double var5 = par1EntityLiving.locY + (double)par1EntityLiving.getHeadHeight() - 1.100000023841858D - this.locY;
            double var7 = par1EntityLiving.locZ + par1EntityLiving.motZ - this.locZ;
            float var9 = MathHelper.sqrt(var3 * var3 + var7 * var7);

            if (var9 >= 8.0F && !par1EntityLiving.hasEffect(MobEffectList.SLOWER_MOVEMENT))
            {
                var2.setPotionValue(32698);
            }
            else if (par1EntityLiving.getHealth() >= 8 && !par1EntityLiving.hasEffect(MobEffectList.POISON))
            {
                var2.setPotionValue(32660);
            }
            else if (var9 <= 3.0F && !par1EntityLiving.hasEffect(MobEffectList.WEAKNESS) && this.random.nextFloat() < 0.25F)
            {
                var2.setPotionValue(32696);
            }

            var2.shoot(var3, var5 + (double) (var9 * 0.2F), var7, 0.75F, 8.0F);
            this.world.addEntity(var2);
        }
    }
}
