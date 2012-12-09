package net.minecraft.server;

public class EntitySnowman extends EntityGolem implements IRangedEntity
{
    public EntitySnowman(World par1World)
    {
        super(par1World);
        this.texture = "/mob/snowman.png";
        this.a(0.4F, 1.8F);
        this.getNavigation().a(true);
        this.goalSelector.a(1, new PathfinderGoalArrowAttack(this, 0.25F, 20, 10.0F));
        this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 0.2F));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityLiving.class, 16.0F, 0, true, false, IMonster.a));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean be()
    {
        return true;
    }

    public int getMaxHealth()
    {
        return 4;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        super.c();

        if (this.G())
        {
            this.damageEntity(DamageSource.DROWN, 1);
        }

        int var1 = MathHelper.floor(this.locX);
        int var2 = MathHelper.floor(this.locZ);

        if (this.world.getBiome(var1, var2).j() > 1.0F)
        {
            this.damageEntity(DamageSource.BURN, 1);
        }

        for (var1 = 0; var1 < 4; ++var1)
        {
            var2 = MathHelper.floor(this.locX + (double) ((float) (var1 % 2 * 2 - 1) * 0.25F));
            int var3 = MathHelper.floor(this.locY);
            int var4 = MathHelper.floor(this.locZ + (double) ((float) (var1 / 2 % 2 * 2 - 1) * 0.25F));

            if (this.world.getTypeId(var2, var3, var4) == 0 && this.world.getBiome(var2, var4).j() < 0.8F && Block.SNOW.canPlace(this.world, var2, var3, var4))
            {
                this.world.setTypeId(var2, var3, var4, Block.SNOW.id);
            }
        }
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return Item.SNOW_BALL.id;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        int var3 = this.random.nextInt(16);

        for (int var4 = 0; var4 < var3; ++var4)
        {
            this.b(Item.SNOW_BALL.id, 1);
        }
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void d(EntityLiving par1EntityLiving)
    {
        EntitySnowball var2 = new EntitySnowball(this.world, this);
        double var3 = par1EntityLiving.locX - this.locX;
        double var5 = par1EntityLiving.locY + (double)par1EntityLiving.getHeadHeight() - 1.100000023841858D - var2.locY;
        double var7 = par1EntityLiving.locZ - this.locZ;
        float var9 = MathHelper.sqrt(var3 * var3 + var7 * var7) * 0.2F;
        var2.shoot(var3, var5 + (double) var9, var7, 1.6F, 12.0F);
        this.makeSound("random.bow", 1.0F, 1.0F / (this.aB().nextFloat() * 0.4F + 0.8F));
        this.world.addEntity(var2);
    }
}
