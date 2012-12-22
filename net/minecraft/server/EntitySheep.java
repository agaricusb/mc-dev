package net.minecraft.server;

import java.util.Random;

public class EntitySheep extends EntityAnimal
{
    private final InventoryCrafting e = new InventoryCrafting(new ContainerSheepBreed(this), 2, 1);

    /**
     * Holds the RGB table of the sheep colors - in OpenGL glColor3f values - used to render the sheep colored fleece.
     */
    public static final float[][] d = new float[][] {{1.0F, 1.0F, 1.0F}, {0.85F, 0.5F, 0.2F}, {0.7F, 0.3F, 0.85F}, {0.4F, 0.6F, 0.85F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.5F, 0.65F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.5F, 0.6F}, {0.5F, 0.25F, 0.7F}, {0.2F, 0.3F, 0.7F}, {0.4F, 0.3F, 0.2F}, {0.4F, 0.5F, 0.2F}, {0.6F, 0.2F, 0.2F}, {0.1F, 0.1F, 0.1F}};

    /**
     * Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each
     * tick.
     */
    private int f;

    /** The eat grass AI task for this mob. */
    private PathfinderGoalEatTile g = new PathfinderGoalEatTile(this);

    public EntitySheep(World par1World)
    {
        super(par1World);
        this.texture = "/mob/sheep.png";
        this.a(0.9F, 1.3F);
        float var2 = 0.23F;
        this.getNavigation().a(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 0.38F));
        this.goalSelector.a(2, new PathfinderGoalBreed(this, var2));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 0.25F, Item.WHEAT.id, false));
        this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 0.25F));
        this.goalSelector.a(5, this.g);
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, var2));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.e.setItem(0, new ItemStack(Item.INK_SACK, 1, 0));
        this.e.setItem(1, new ItemStack(Item.INK_SACK, 1, 0));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean be()
    {
        return true;
    }

    protected void bl()
    {
        this.f = this.g.f();
        super.bl();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        if (this.world.isStatic)
        {
            this.f = Math.max(0, this.f - 1);
        }

        super.c();
    }

    public int getMaxHealth()
    {
        return 8;
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(16, new Byte((byte) 0));
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        if (!this.isSheared())
        {
            this.a(new ItemStack(Block.WOOL.id, 1, this.getColor()), 0.0F);
        }
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return Block.WOOL.id;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean a(EntityHuman par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.getItemInHand();

        if (var2 != null && var2.id == Item.SHEARS.id && !this.isSheared() && !this.isBaby())
        {
            if (!this.world.isStatic)
            {
                this.setSheared(true);
                int var3 = 1 + this.random.nextInt(3);

                for (int var4 = 0; var4 < var3; ++var4)
                {
                    EntityItem var5 = this.a(new ItemStack(Block.WOOL.id, 1, this.getColor()), 1.0F);
                    var5.motY += (double)(this.random.nextFloat() * 0.05F);
                    var5.motX += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                    var5.motZ += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                }
            }

            var2.damage(1, par1EntityPlayer);
            this.makeSound("mob.sheep.shear", 1.0F, 1.0F);
        }

        return super.a(par1EntityPlayer);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Sheared", this.isSheared());
        par1NBTTagCompound.setByte("Color", (byte) this.getColor());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.setSheared(par1NBTTagCompound.getBoolean("Sheared"));
        this.setColor(par1NBTTagCompound.getByte("Color"));
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "mob.sheep.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.sheep.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.sheep.say";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void a(int par1, int par2, int par3, int par4)
    {
        this.makeSound("mob.sheep.step", 0.15F, 1.0F);
    }

    public int getColor()
    {
        return this.datawatcher.getByte(16) & 15;
    }

    public void setColor(int par1)
    {
        byte var2 = this.datawatcher.getByte(16);
        this.datawatcher.watch(16, Byte.valueOf((byte) (var2 & 240 | par1 & 15)));
    }

    /**
     * returns true if a sheeps wool has been sheared
     */
    public boolean isSheared()
    {
        return (this.datawatcher.getByte(16) & 16) != 0;
    }

    /**
     * make a sheep sheared if set to true
     */
    public void setSheared(boolean par1)
    {
        byte var2 = this.datawatcher.getByte(16);

        if (par1)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) (var2 | 16)));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) (var2 & -17)));
        }
    }

    /**
     * This method is called when a sheep spawns in the world to select the color of sheep fleece.
     */
    public static int a(Random par0Random)
    {
        int var1 = par0Random.nextInt(100);
        return var1 < 5 ? 15 : (var1 < 10 ? 7 : (var1 < 15 ? 8 : (var1 < 18 ? 12 : (par0Random.nextInt(500) == 0 ? 6 : 0))));
    }

    public EntitySheep b(EntityAgeable par1EntityAgeable)
    {
        EntitySheep var2 = (EntitySheep)par1EntityAgeable;
        EntitySheep var3 = new EntitySheep(this.world);
        int var4 = this.a(this, var2);
        var3.setColor(15 - var4);
        return var3;
    }

    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This
     * function is used in the AIEatGrass)
     */
    public void aH()
    {
        this.setSheared(false);

        if (this.isBaby())
        {
            int var1 = this.getAge() + 1200;

            if (var1 > 0)
            {
                var1 = 0;
            }

            this.setAge(var1);
        }
    }

    /**
     * Initialize this creature.
     */
    public void bG()
    {
        this.setColor(a(this.world.random));
    }

    private int a(EntityAnimal par1EntityAnimal, EntityAnimal par2EntityAnimal)
    {
        int var3 = this.b(par1EntityAnimal);
        int var4 = this.b(par2EntityAnimal);
        this.e.getItem(0).setData(var3);
        this.e.getItem(1).setData(var4);
        ItemStack var5 = CraftingManager.getInstance().craft(this.e, ((EntitySheep) par1EntityAnimal).world);
        int var6;

        if (var5 != null && var5.getItem().id == Item.INK_SACK.id)
        {
            var6 = var5.getData();
        }
        else
        {
            var6 = this.world.random.nextBoolean() ? var3 : var4;
        }

        return var6;
    }

    private int b(EntityAnimal par1EntityAnimal)
    {
        return 15 - ((EntitySheep)par1EntityAnimal).getColor();
    }

    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        return this.b(par1EntityAgeable);
    }
}
