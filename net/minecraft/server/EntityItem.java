package net.minecraft.server;

import java.util.Iterator;

public class EntityItem extends Entity
{
    /**
     * The age of this EntityItem (used to animate it up and down as well as expire it)
     */
    public int age;
    public int pickupDelay;

    /** The health of this EntityItem. (For example, damage for tools) */
    private int d;

    /** The EntityItem's random initial float height. */
    public float c;

    public EntityItem(World par1World, double par2, double par4, double par6)
    {
        super(par1World);
        this.age = 0;
        this.d = 5;
        this.c = (float)(Math.random() * Math.PI * 2.0D);
        this.a(0.25F, 0.25F);
        this.height = this.length / 2.0F;
        this.setPosition(par2, par4, par6);
        this.yaw = (float)(Math.random() * 360.0D);
        this.motX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
        this.motY = 0.20000000298023224D;
        this.motZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
    }

    public EntityItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
    {
        this(par1World, par2, par4, par6);
        this.setItemStack(par8ItemStack);
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean f_()
    {
        return false;
    }

    public EntityItem(World par1World)
    {
        super(par1World);
        this.age = 0;
        this.d = 5;
        this.c = (float)(Math.random() * Math.PI * 2.0D);
        this.a(0.25F, 0.25F);
        this.height = this.length / 2.0F;
    }

    protected void a()
    {
        this.getDataWatcher().a(10, 5);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        super.j_();

        if (this.pickupDelay > 0)
        {
            --this.pickupDelay;
        }

        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.motY -= 0.03999999910593033D;
        this.Y = this.i(this.locX, (this.boundingBox.b + this.boundingBox.e) / 2.0D, this.locZ);
        this.move(this.motX, this.motY, this.motZ);
        boolean var1 = (int)this.lastX != (int)this.locX || (int)this.lastY != (int)this.locY || (int)this.lastZ != (int)this.locZ;

        if (var1 || this.ticksLived % 25 == 0)
        {
            if (this.world.getMaterial(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) == Material.LAVA)
            {
                this.motY = 0.20000000298023224D;
                this.motX = (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                this.motZ = (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                this.makeSound("random.fizz", 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
            }

            if (!this.world.isStatic)
            {
                this.g();
            }
        }

        float var2 = 0.98F;

        if (this.onGround)
        {
            var2 = 0.58800006F;
            int var3 = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));

            if (var3 > 0)
            {
                var2 = Block.byId[var3].frictionFactor * 0.98F;
            }
        }

        this.motX *= (double)var2;
        this.motY *= 0.9800000190734863D;
        this.motZ *= (double)var2;

        if (this.onGround)
        {
            this.motY *= -0.5D;
        }

        ++this.age;

        if (!this.world.isStatic && this.age >= 6000)
        {
            this.die();
        }
    }

    private void g()
    {
        Iterator var1 = this.world.a(EntityItem.class, this.boundingBox.grow(0.5D, 0.0D, 0.5D)).iterator();

        while (var1.hasNext())
        {
            EntityItem var2 = (EntityItem)var1.next();
            this.a(var2);
        }
    }

    /**
     * Tries to merge this item with the item passed as the parameter. Returns true if successful. Either this item or
     * the other item will  be removed from the world.
     */
    public boolean a(EntityItem par1EntityItem)
    {
        if (par1EntityItem == this)
        {
            return false;
        }
        else if (par1EntityItem.isAlive() && this.isAlive())
        {
            ItemStack var2 = this.getItemStack();
            ItemStack var3 = par1EntityItem.getItemStack();

            if (var3.getItem() != var2.getItem())
            {
                return false;
            }
            else if (var3.hasTag() ^ var2.hasTag())
            {
                return false;
            }
            else if (var3.hasTag() && !var3.getTag().equals(var2.getTag()))
            {
                return false;
            }
            else if (var3.getItem().l() && var3.getData() != var2.getData())
            {
                return false;
            }
            else if (var3.count < var2.count)
            {
                return par1EntityItem.a(this);
            }
            else if (var3.count + var2.count > var3.getMaxStackSize())
            {
                return false;
            }
            else
            {
                var3.count += var2.count;
                par1EntityItem.pickupDelay = Math.max(par1EntityItem.pickupDelay, this.pickupDelay);
                par1EntityItem.age = Math.min(par1EntityItem.age, this.age);
                par1EntityItem.setItemStack(var3);
                this.die();
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    public void c()
    {
        this.age = 4800;
    }

    /**
     * Returns if this entity is in water and will end up adding the waters velocity to the entity
     */
    public boolean I()
    {
        return this.world.a(this.boundingBox, Material.WATER, this);
    }

    /**
     * Will deal the specified amount of damage to the entity if the entity isn't immune to fire damage. Args:
     * amountDamage
     */
    protected void burn(int par1)
    {
        this.damageEntity(DamageSource.FIRE, par1);
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean damageEntity(DamageSource par1DamageSource, int par2)
    {
        if (this.isInvulnerable())
        {
            return false;
        }
        else if (this.getItemStack() != null && this.getItemStack().id == Item.NETHER_STAR.id && par1DamageSource == DamageSource.EXPLOSION)
        {
            return false;
        }
        else
        {
            this.K();
            this.d -= par2;

            if (this.d <= 0)
            {
                this.die();
            }

            return false;
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("Health", (short) ((byte) this.d));
        par1NBTTagCompound.setShort("Age", (short) this.age);

        if (this.getItemStack() != null)
        {
            par1NBTTagCompound.setCompound("Item", this.getItemStack().save(new NBTTagCompound()));
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        this.d = par1NBTTagCompound.getShort("Health") & 255;
        this.age = par1NBTTagCompound.getShort("Age");
        NBTTagCompound var2 = par1NBTTagCompound.getCompound("Item");
        this.setItemStack(ItemStack.a(var2));

        if (this.getItemStack() == null)
        {
            this.die();
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void c_(EntityHuman par1EntityPlayer)
    {
        if (!this.world.isStatic)
        {
            ItemStack var2 = this.getItemStack();
            int var3 = var2.count;

            if (this.pickupDelay == 0 && par1EntityPlayer.inventory.pickup(var2))
            {
                if (var2.id == Block.LOG.id)
                {
                    par1EntityPlayer.a(AchievementList.g);
                }

                if (var2.id == Item.LEATHER.id)
                {
                    par1EntityPlayer.a(AchievementList.t);
                }

                if (var2.id == Item.DIAMOND.id)
                {
                    par1EntityPlayer.a(AchievementList.w);
                }

                if (var2.id == Item.BLAZE_ROD.id)
                {
                    par1EntityPlayer.a(AchievementList.z);
                }

                this.makeSound("random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                par1EntityPlayer.receive(this, var3);

                if (var2.count <= 0)
                {
                    this.die();
                }
            }
        }
    }

    /**
     * Gets the username of the entity.
     */
    public String getLocalizedName()
    {
        return LocaleI18n.get("item." + this.getItemStack().a());
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean aq()
    {
        return false;
    }

    public void b(int par1)
    {
        super.b(par1);

        if (!this.world.isStatic)
        {
            this.g();
        }
    }

    public ItemStack getItemStack()
    {
        ItemStack var1 = this.getDataWatcher().f(10);

        if (var1 == null)
        {
            System.out.println("Item entity " + this.id + " has no item?!");
            return new ItemStack(Block.STONE);
        }
        else
        {
            return var1;
        }
    }

    public void setItemStack(ItemStack par1ItemStack)
    {
        this.getDataWatcher().watch(10, par1ItemStack);
        this.getDataWatcher().h(10);
    }
}
