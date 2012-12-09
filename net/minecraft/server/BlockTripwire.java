package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BlockTripwire extends Block
{
    public BlockTripwire(int par1)
    {
        super(par1, 173, Material.ORIENTABLE);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
        this.b(true);
    }

    /**
     * How many world ticks before ticking
     */
    public int r_()
    {
        return 10;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean c()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 30;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Item.STRING.id;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = par1World.getData(par2, par3, par4);
        boolean var7 = (var6 & 2) == 2;
        boolean var8 = !par1World.v(par2, par3 - 1, par4);

        if (var7 != var8)
        {
            this.c(par1World, par2, par3, par4, var6, 0);
            par1World.setTypeId(par2, par3, par4, 0);
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getData(par2, par3, par4);
        boolean var6 = (var5 & 4) == 4;
        boolean var7 = (var5 & 2) == 2;

        if (!var7)
        {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
        }
        else if (!var6)
        {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
        else
        {
            this.a(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.v(par2, par3 - 1, par4) ? 0 : 2;
        par1World.setData(par2, par3, par4, var5);
        this.d(par1World, par2, par3, par4, var5);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        this.d(par1World, par2, par3, par4, par6 | 1);
    }

    /**
     * Called when the block is attempted to be harvested
     */
    public void a(World par1World, int par2, int par3, int par4, int par5, EntityHuman par6EntityPlayer)
    {
        if (!par1World.isStatic)
        {
            if (par6EntityPlayer.bT() != null && par6EntityPlayer.bT().id == Item.SHEARS.id)
            {
                par1World.setData(par2, par3, par4, par5 | 8);
            }
        }
    }

    private void d(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = 0;

        while (var6 < 2)
        {
            int var7 = 1;

            while (true)
            {
                if (var7 < 42)
                {
                    int var8 = par2 + Direction.a[var6] * var7;
                    int var9 = par4 + Direction.b[var6] * var7;
                    int var10 = par1World.getTypeId(var8, par3, var9);

                    if (var10 == Block.TRIPWIRE_SOURCE.id)
                    {
                        int var11 = par1World.getData(var8, par3, var9) & 3;

                        if (var11 == Direction.f[var6])
                        {
                            Block.TRIPWIRE_SOURCE.a(par1World, var8, par3, var9, var10, par1World.getData(var8, par3, var9), true, var7, par5);
                        }
                    }
                    else if (var10 == Block.TRIPWIRE.id)
                    {
                        ++var7;
                        continue;
                    }
                }

                ++var6;
                break;
            }
        }
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void a(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (!par1World.isStatic)
        {
            if ((par1World.getData(par2, par3, par4) & 1) != 1)
            {
                this.l(par1World, par2, par3, par4);
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isStatic)
        {
            if ((par1World.getData(par2, par3, par4) & 1) == 1)
            {
                this.l(par1World, par2, par3, par4);
            }
        }
    }

    private void l(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getData(par2, par3, par4);
        boolean var6 = (var5 & 1) == 1;
        boolean var7 = false;
        List var8 = par1World.getEntities((Entity) null, AxisAlignedBB.a().a((double) par2 + this.minX, (double) par3 + this.minY, (double) par4 + this.minZ, (double) par2 + this.maxX, (double) par3 + this.maxY, (double) par4 + this.maxZ));

        if (!var8.isEmpty())
        {
            Iterator var9 = var8.iterator();

            while (var9.hasNext())
            {
                Entity var10 = (Entity)var9.next();

                if (!var10.au())
                {
                    var7 = true;
                    break;
                }
            }
        }

        if (var7 && !var6)
        {
            var5 |= 1;
        }

        if (!var7 && var6)
        {
            var5 &= -2;
        }

        if (var7 != var6)
        {
            par1World.setData(par2, par3, par4, var5);
            this.d(par1World, par2, par3, par4, var5);
        }

        if (var7)
        {
            par1World.a(par2, par3, par4, this.id, this.r_());
        }
    }
}
