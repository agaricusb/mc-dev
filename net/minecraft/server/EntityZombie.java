package net.minecraft.server;

import java.util.Calendar;

public class EntityZombie extends EntityMonster
{
    /**
     * Ticker used to determine the time remaining for this zombie to convert into a villager when cured.
     */
    private int d = 0;

    public EntityZombie(World par1World)
    {
        super(par1World);
        this.texture = "/mob/zombie.png";
        this.bG = 0.23F;
        this.getNavigation().b(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalBreakDoor(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, this.bG, false));
        this.goalSelector.a(3, new PathfinderGoalMeleeAttack(this, EntityVillager.class, this.bG, true));
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, this.bG));
        this.goalSelector.a(5, new PathfinderGoalMoveThroughVillage(this, this.bG, false));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, this.bG));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0F, 0, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, 16.0F, 0, false));
    }

    /**
     * This method returns a value to be applied directly to entity speed, this factor is less than 1 when a slowdown
     * potion effect is applied, more than 1 when a haste potion effect is applied and 2 for fleeing entities.
     */
    public float bB()
    {
        return super.bB() * (this.isBaby() ? 1.5F : 1.0F);
    }

    protected void a()
    {
        super.a();
        this.getDataWatcher().a(12, Byte.valueOf((byte) 0));
        this.getDataWatcher().a(13, Byte.valueOf((byte) 0));
        this.getDataWatcher().a(14, Byte.valueOf((byte) 0));
    }

    public int getMaxHealth()
    {
        return 20;
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    public int aW()
    {
        int var1 = super.aW() + 2;

        if (var1 > 20)
        {
            var1 = 20;
        }

        return var1;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean be()
    {
        return true;
    }

    /**
     * If Animal, checks if the age timer is negative
     */
    public boolean isBaby()
    {
        return this.getDataWatcher().getByte(12) == 1;
    }

    /**
     * Set whether this zombie is a child.
     */
    public void setBaby(boolean par1)
    {
        this.getDataWatcher().watch(12, Byte.valueOf((byte) 1));
    }

    /**
     * Return whether this zombie is a villager.
     */
    public boolean isVillager()
    {
        return this.getDataWatcher().getByte(13) == 1;
    }

    /**
     * Set whether this zombie is a villager.
     */
    public void setVillager(boolean par1)
    {
        this.getDataWatcher().watch(13, Byte.valueOf((byte) (par1 ? 1 : 0)));
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        if (this.world.u() && !this.world.isStatic && !this.isBaby())
        {
            float var1 = this.c(1.0F);

            if (var1 > 0.5F && this.random.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F && this.world.k(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)))
            {
                boolean var2 = true;
                ItemStack var3 = this.getEquipment(4);

                if (var3 != null)
                {
                    if (var3.f())
                    {
                        var3.setData(var3.i() + this.random.nextInt(2));

                        if (var3.i() >= var3.k())
                        {
                            this.a(var3);
                            this.setEquipment(4, (ItemStack) null);
                        }
                    }

                    var2 = false;
                }

                if (var2)
                {
                    this.setOnFire(8);
                }
            }
        }

        super.c();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        if (!this.world.isStatic && this.o())
        {
            int var1 = this.q();
            this.d -= var1;

            if (this.d <= 0)
            {
                this.p();
            }
        }

        super.j_();
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int c(Entity par1Entity)
    {
        ItemStack var2 = this.bD();
        int var3 = 4;

        if (var2 != null)
        {
            var3 += var2.a((Entity)this); // getDamageVsEntity
        }

        return var3;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "mob.zombie.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.zombie.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.zombie.death";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void a(int par1, int par2, int par3, int par4)
    {
        this.makeSound("mob.zombie.step", 0.15F, 1.0F);
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return Item.ROTTEN_FLESH.id;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumMonsterType getMonsterType()
    {
        return EnumMonsterType.UNDEAD;
    }

    protected void l(int par1)
    {
        switch (this.random.nextInt(3))
        {
            case 0:
                this.b(Item.IRON_INGOT.id, 1);
                break;

            case 1:
                this.b(Item.CARROT.id, 1);
                break;

            case 2:
                this.b(Item.POTATO.id, 1);
        }
    }

    protected void bE()
    {
        super.bE();

        if (this.random.nextFloat() < (this.world.difficulty == 3 ? 0.05F : 0.01F))
        {
            int var1 = this.random.nextInt(3);

            if (var1 == 0)
            {
                this.setEquipment(0, new ItemStack(Item.IRON_SWORD));
            }
            else
            {
                this.setEquipment(0, new ItemStack(Item.IRON_SPADE));
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);

        if (this.isBaby())
        {
            par1NBTTagCompound.setBoolean("IsBaby", true);
        }

        if (this.isVillager())
        {
            par1NBTTagCompound.setBoolean("IsVillager", true);
        }

        par1NBTTagCompound.setInt("ConversionTime", this.o() ? this.d : -1);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);

        if (par1NBTTagCompound.getBoolean("IsBaby"))
        {
            this.setBaby(true);
        }

        if (par1NBTTagCompound.getBoolean("IsVillager"))
        {
            this.setVillager(true);
        }

        if (par1NBTTagCompound.hasKey("ConversionTime") && par1NBTTagCompound.getInt("ConversionTime") > -1)
        {
            this.a(par1NBTTagCompound.getInt("ConversionTime"));
        }
    }

    /**
     * This method gets called when the entity kills another one.
     */
    public void a(EntityLiving par1EntityLiving)
    {
        super.a(par1EntityLiving);

        if (this.world.difficulty >= 2 && par1EntityLiving instanceof EntityVillager)
        {
            if (this.world.difficulty == 2 && this.random.nextBoolean())
            {
                return;
            }

            EntityZombie var2 = new EntityZombie(this.world);
            var2.k(par1EntityLiving);
            this.world.kill(par1EntityLiving);
            var2.bG();
            var2.setVillager(true);

            if (par1EntityLiving.isBaby())
            {
                var2.setBaby(true);
            }

            this.world.addEntity(var2);
            this.world.a((EntityHuman) null, 1016, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
        }
    }

    /**
     * Initialize this creature.
     */
    public void bG()
    {
        this.canPickUpLoot = this.random.nextFloat() < as[this.world.difficulty];

        if (this.world.random.nextFloat() < 0.05F)
        {
            this.setVillager(true);
        }

        this.bE();
        this.bF();

        if (this.getEquipment(4) == null)
        {
            Calendar var1 = this.world.T();

            if (var1.get(2) + 1 == 10 && var1.get(5) == 31 && this.random.nextFloat() < 0.25F)
            {
                this.setEquipment(4, new ItemStack(this.random.nextFloat() < 0.1F ? Block.JACK_O_LANTERN : Block.PUMPKIN));
                this.dropChances[4] = 0.0F;
            }
        }
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean a(EntityHuman par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.bT();

        if (var2 != null && var2.getItem() == Item.GOLDEN_APPLE && var2.getData() == 0 && this.isVillager() && this.hasEffect(MobEffectList.WEAKNESS))
        {
            if (!par1EntityPlayer.abilities.canInstantlyBuild)
            {
                --var2.count;
            }

            if (var2.count <= 0)
            {
                par1EntityPlayer.inventory.setItem(par1EntityPlayer.inventory.itemInHandIndex, (ItemStack) null);
            }

            if (!this.world.isStatic)
            {
                this.a(this.random.nextInt(2401) + 3600);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Starts converting this zombie into a villager. The zombie converts into a villager after the specified time in
     * ticks.
     */
    protected void a(int par1)
    {
        this.d = par1;
        this.getDataWatcher().watch(14, Byte.valueOf((byte) 1));
        this.o(MobEffectList.WEAKNESS.id);
        this.addEffect(new MobEffect(MobEffectList.INCREASE_DAMAGE.id, par1, Math.min(this.world.difficulty - 1, 0)));
        this.world.broadcastEntityEffect(this, (byte) 16);
    }

    public boolean o()
    {
        return this.getDataWatcher().getByte(14) == 1;
    }

    /**
     * Convert this zombie into a villager.
     */
    protected void p()
    {
        EntityVillager var1 = new EntityVillager(this.world);
        var1.k(this);
        var1.bG();
        var1.q();

        if (this.isBaby())
        {
            var1.setAge(-24000);
        }

        this.world.kill(this);
        this.world.addEntity(var1);
        var1.addEffect(new MobEffect(MobEffectList.CONFUSION.id, 200, 0));
        this.world.a((EntityHuman) null, 1017, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
    }

    /**
     * Return the amount of time decremented from conversionTime every tick.
     */
    protected int q()
    {
        int var1 = 1;

        if (this.random.nextFloat() < 0.01F)
        {
            int var2 = 0;

            for (int var3 = (int)this.locX - 4; var3 < (int)this.locX + 4 && var2 < 14; ++var3)
            {
                for (int var4 = (int)this.locY - 4; var4 < (int)this.locY + 4 && var2 < 14; ++var4)
                {
                    for (int var5 = (int)this.locZ - 4; var5 < (int)this.locZ + 4 && var2 < 14; ++var5)
                    {
                        int var6 = this.world.getTypeId(var3, var4, var5);

                        if (var6 == Block.IRON_FENCE.id || var6 == Block.BED.id)
                        {
                            if (this.random.nextFloat() < 0.3F)
                            {
                                ++var1;
                            }

                            ++var2;
                        }
                    }
                }
            }
        }

        return var1;
    }
}
