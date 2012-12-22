package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;

public class EntityFallingBlock extends Entity
{
    public int id;
    public int data;

    /** How long the block has been falling for. */
    public int c;
    public boolean dropItem;
    private boolean e;
    private boolean hurtEntities;

    /** Maximum amount of damage dealt to entities hit by falling block */
    private int fallHurtMax;

    /** Actual damage dealt to entities hit by falling block */
    private float fallHurtAmount;

    public EntityFallingBlock(World par1World)
    {
        super(par1World);
        this.c = 0;
        this.dropItem = true;
        this.e = false;
        this.hurtEntities = false;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0F;
    }

    public EntityFallingBlock(World par1World, double par2, double par4, double par6, int par8)
    {
        this(par1World, par2, par4, par6, par8, 0);
    }

    public EntityFallingBlock(World par1World, double par2, double par4, double par6, int par8, int par9)
    {
        super(par1World);
        this.c = 0;
        this.dropItem = true;
        this.e = false;
        this.hurtEntities = false;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0F;
        this.id = par8;
        this.data = par9;
        this.m = true;
        this.a(0.98F, 0.98F);
        this.height = this.length / 2.0F;
        this.setPosition(par2, par4, par6);
        this.motX = 0.0D;
        this.motY = 0.0D;
        this.motZ = 0.0D;
        this.lastX = par2;
        this.lastY = par4;
        this.lastZ = par6;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean f_()
    {
        return false;
    }

    protected void a() {}

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean L()
    {
        return !this.dead;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        if (this.id == 0)
        {
            this.die();
        }
        else
        {
            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;
            ++this.c;
            this.motY -= 0.03999999910593033D;
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.9800000190734863D;
            this.motY *= 0.9800000190734863D;
            this.motZ *= 0.9800000190734863D;

            if (!this.world.isStatic)
            {
                int var1 = MathHelper.floor(this.locX);
                int var2 = MathHelper.floor(this.locY);
                int var3 = MathHelper.floor(this.locZ);

                if (this.c == 1)
                {
                    if (this.c != 1 || this.world.getTypeId(var1, var2, var3) != this.id)
                    {
                        this.die();
                        return;
                    }

                    this.world.setTypeId(var1, var2, var3, 0);
                }

                if (this.onGround)
                {
                    this.motX *= 0.699999988079071D;
                    this.motZ *= 0.699999988079071D;
                    this.motY *= -0.5D;

                    if (this.world.getTypeId(var1, var2, var3) != Block.PISTON_MOVING.id)
                    {
                        this.die();

                        if (!this.e && this.world.mayPlace(this.id, var1, var2, var3, true, 1, (Entity) null) && !BlockSand.canFall(this.world, var1, var2 - 1, var3) && this.world.setTypeIdAndData(var1, var2, var3, this.id, this.data))
                        {
                            if (Block.byId[this.id] instanceof BlockSand)
                            {
                                ((BlockSand) Block.byId[this.id]).a_(this.world, var1, var2, var3, this.data);
                            }
                        }
                        else if (this.dropItem && !this.e)
                        {
                            this.a(new ItemStack(this.id, 1, Block.byId[this.id].getDropData(this.data)), 0.0F);
                        }
                    }
                }
                else if (this.c > 100 && !this.world.isStatic && (var2 < 1 || var2 > 256) || this.c > 600)
                {
                    if (this.dropItem)
                    {
                        this.a(new ItemStack(this.id, 1, Block.byId[this.id].getDropData(this.data)), 0.0F);
                    }

                    this.die();
                }
            }
        }
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void a(float par1)
    {
        if (this.hurtEntities)
        {
            int var2 = MathHelper.f(par1 - 1.0F);

            if (var2 > 0)
            {
                ArrayList var3 = new ArrayList(this.world.getEntities(this, this.boundingBox));
                DamageSource var4 = this.id == Block.ANVIL.id ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;
                Iterator var5 = var3.iterator();

                while (var5.hasNext())
                {
                    Entity var6 = (Entity)var5.next();
                    var6.damageEntity(var4, Math.min(MathHelper.d((float) var2 * this.fallHurtAmount), this.fallHurtMax));
                }

                if (this.id == Block.ANVIL.id && (double)this.random.nextFloat() < 0.05000000074505806D + (double)var2 * 0.05D)
                {
                    int var7 = this.data >> 2;
                    int var8 = this.data & 3;
                    ++var7;

                    if (var7 > 2)
                    {
                        this.e = true;
                    }
                    else
                    {
                        this.data = var8 | var7 << 2;
                    }
                }
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("Tile", (byte) this.id);
        par1NBTTagCompound.setByte("Data", (byte) this.data);
        par1NBTTagCompound.setByte("Time", (byte) this.c);
        par1NBTTagCompound.setBoolean("DropItem", this.dropItem);
        par1NBTTagCompound.setBoolean("HurtEntities", this.hurtEntities);
        par1NBTTagCompound.setFloat("FallHurtAmount", this.fallHurtAmount);
        par1NBTTagCompound.setInt("FallHurtMax", this.fallHurtMax);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void a(NBTTagCompound par1NBTTagCompound)
    {
        this.id = par1NBTTagCompound.getByte("Tile") & 255;
        this.data = par1NBTTagCompound.getByte("Data") & 255;
        this.c = par1NBTTagCompound.getByte("Time") & 255;

        if (par1NBTTagCompound.hasKey("HurtEntities"))
        {
            this.hurtEntities = par1NBTTagCompound.getBoolean("HurtEntities");
            this.fallHurtAmount = par1NBTTagCompound.getFloat("FallHurtAmount");
            this.fallHurtMax = par1NBTTagCompound.getInt("FallHurtMax");
        }
        else if (this.id == Block.ANVIL.id)
        {
            this.hurtEntities = true;
        }

        if (par1NBTTagCompound.hasKey("DropItem"))
        {
            this.dropItem = par1NBTTagCompound.getBoolean("DropItem");
        }

        if (this.id == 0)
        {
            this.id = Block.SAND.id;
        }
    }

    public void e(boolean par1)
    {
        this.hurtEntities = par1;
    }

    public void a(CrashReportSystemDetails par1CrashReportCategory)
    {
        super.a(par1CrashReportCategory);
        par1CrashReportCategory.a("Immitating block ID", Integer.valueOf(this.id));
        par1CrashReportCategory.a("Immitating block data", Integer.valueOf(this.data));
    }
}
