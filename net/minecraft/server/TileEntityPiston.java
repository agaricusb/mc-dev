package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TileEntityPiston extends TileEntity
{
    private int a;
    private int b;

    /** the side the front of the piston is on */
    private int c;

    /** if this piston is extending or not */
    private boolean d;
    private boolean e;
    private float f;

    /** the progress in (de)extending */
    private float g;
    private List h = new ArrayList();

    public TileEntityPiston() {}

    public TileEntityPiston(int par1, int par2, int par3, boolean par4, boolean par5)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3;
        this.d = par4;
        this.e = par5;
    }

    public int a()
    {
        return this.a;
    }

    /**
     * Returns block data at the location of this entity (client-only).
     */
    public int p()
    {
        return this.b;
    }

    /**
     * Returns true if a piston is extending
     */
    public boolean b()
    {
        return this.d;
    }

    /**
     * Returns the orientation of the piston as an int
     */
    public int c()
    {
        return this.c;
    }

    /**
     * Get interpolated progress value (between lastProgress and progress) given the fractional time between ticks as an
     * argument.
     */
    public float a(float par1)
    {
        if (par1 > 1.0F)
        {
            par1 = 1.0F;
        }

        return this.g + (this.f - this.g) * par1;
    }

    private void a(float par1, float par2)
    {
        if (this.d)
        {
            par1 = 1.0F - par1;
        }
        else
        {
            --par1;
        }

        AxisAlignedBB var3 = Block.PISTON_MOVING.b(this.world, this.x, this.y, this.z, this.a, par1, this.c);

        if (var3 != null)
        {
            List var4 = this.world.getEntities((Entity) null, var3);

            if (!var4.isEmpty())
            {
                this.h.addAll(var4);
                Iterator var5 = this.h.iterator();

                while (var5.hasNext())
                {
                    Entity var6 = (Entity)var5.next();
                    var6.move((double) (par2 * (float) Facing.b[this.c]), (double) (par2 * (float) Facing.c[this.c]), (double) (par2 * (float) Facing.d[this.c]));
                }

                this.h.clear();
            }
        }
    }

    /**
     * removes a pistons tile entity (and if the piston is moving, stops it)
     */
    public void f()
    {
        if (this.g < 1.0F && this.world != null)
        {
            this.g = this.f = 1.0F;
            this.world.r(this.x, this.y, this.z);
            this.w_();

            if (this.world.getTypeId(this.x, this.y, this.z) == Block.PISTON_MOVING.id)
            {
                this.world.setTypeIdAndData(this.x, this.y, this.z, this.a, this.b);
            }
        }
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void g()
    {
        this.g = this.f;

        if (this.g >= 1.0F)
        {
            this.a(1.0F, 0.25F);
            this.world.r(this.x, this.y, this.z);
            this.w_();

            if (this.world.getTypeId(this.x, this.y, this.z) == Block.PISTON_MOVING.id)
            {
                this.world.setTypeIdAndData(this.x, this.y, this.z, this.a, this.b);
            }
        }
        else
        {
            this.f += 0.5F;

            if (this.f >= 1.0F)
            {
                this.f = 1.0F;
            }

            if (this.d)
            {
                this.a(this.f, this.f - this.g + 0.0625F);
            }
        }
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.a = par1NBTTagCompound.getInt("blockId");
        this.b = par1NBTTagCompound.getInt("blockData");
        this.c = par1NBTTagCompound.getInt("facing");
        this.g = this.f = par1NBTTagCompound.getFloat("progress");
        this.d = par1NBTTagCompound.getBoolean("extending");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setInt("blockId", this.a);
        par1NBTTagCompound.setInt("blockData", this.b);
        par1NBTTagCompound.setInt("facing", this.c);
        par1NBTTagCompound.setFloat("progress", this.g);
        par1NBTTagCompound.setBoolean("extending", this.d);
    }
}
