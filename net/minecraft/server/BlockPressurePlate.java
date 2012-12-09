package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BlockPressurePlate extends Block
{
    /** The mob type that can trigger this pressure plate. */
    private EnumMobType a;

    protected BlockPressurePlate(int par1, int par2, EnumMobType par3EnumMobType, Material par4Material)
    {
        super(par1, par2, par4Material);
        this.a = par3EnumMobType;
        this.a(CreativeModeTab.d);
        this.b(true);
        float var5 = 0.0625F;
        this.a(var5, 0.0F, var5, 1.0F - var5, 0.03125F, 1.0F - var5);
    }

    /**
     * How many world ticks before ticking
     */
    public int r_()
    {
        return 20;
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

    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return true;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return par1World.v(par2, par3 - 1, par4) || BlockFence.c(par1World.getTypeId(par2, par3 - 1, par4));
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        boolean var6 = false;

        if (!par1World.v(par2, par3 - 1, par4) && !BlockFence.c(par1World.getTypeId(par2, par3 - 1, par4)))
        {
            var6 = true;
        }

        if (var6)
        {
            this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
            par1World.setTypeId(par2, par3, par4, 0);
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isStatic)
        {
            if (par1World.getData(par2, par3, par4) != 0)
            {
                this.l(par1World, par2, par3, par4);
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
            if (par1World.getData(par2, par3, par4) != 1)
            {
                this.l(par1World, par2, par3, par4);
            }
        }
    }

    /**
     * Checks if there are mobs on the plate. If a mob is on the plate and it is off, it turns it on, and vice versa.
     */
    private void l(World par1World, int par2, int par3, int par4)
    {
        boolean var5 = par1World.getData(par2, par3, par4) == 1;
        boolean var6 = false;
        float var7 = 0.125F;
        List var8 = null;

        if (this.a == EnumMobType.EVERYTHING)
        {
            var8 = par1World.getEntities((Entity) null, AxisAlignedBB.a().a((double) ((float) par2 + var7), (double) par3, (double) ((float) par4 + var7), (double) ((float) (par2 + 1) - var7), (double) par3 + 0.25D, (double) ((float) (par4 + 1) - var7)));
        }

        if (this.a == EnumMobType.MOBS)
        {
            var8 = par1World.a(EntityLiving.class, AxisAlignedBB.a().a((double) ((float) par2 + var7), (double) par3, (double) ((float) par4 + var7), (double) ((float) (par2 + 1) - var7), (double) par3 + 0.25D, (double) ((float) (par4 + 1) - var7)));
        }

        if (this.a == EnumMobType.PLAYERS)
        {
            var8 = par1World.a(EntityHuman.class, AxisAlignedBB.a().a((double) ((float) par2 + var7), (double) par3, (double) ((float) par4 + var7), (double) ((float) (par2 + 1) - var7), (double) par3 + 0.25D, (double) ((float) (par4 + 1) - var7)));
        }

        if (!var8.isEmpty())
        {
            Iterator var9 = var8.iterator();

            while (var9.hasNext())
            {
                Entity var10 = (Entity)var9.next();

                if (!var10.au())
                {
                    var6 = true;
                    break;
                }
            }
        }

        if (var6 && !var5)
        {
            par1World.setData(par2, par3, par4, 1);
            par1World.applyPhysics(par2, par3, par4, this.id);
            par1World.applyPhysics(par2, par3 - 1, par4, this.id);
            par1World.e(par2, par3, par4, par2, par3, par4);
            par1World.makeSound((double) par2 + 0.5D, (double) par3 + 0.1D, (double) par4 + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (!var6 && var5)
        {
            par1World.setData(par2, par3, par4, 0);
            par1World.applyPhysics(par2, par3, par4, this.id);
            par1World.applyPhysics(par2, par3 - 1, par4, this.id);
            par1World.e(par2, par3, par4, par2, par3, par4);
            par1World.makeSound((double) par2 + 0.5D, (double) par3 + 0.1D, (double) par4 + 0.5D, "random.click", 0.3F, 0.5F);
        }

        if (var6)
        {
            par1World.a(par2, par3, par4, this.id, this.r_());
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (par6 > 0)
        {
            par1World.applyPhysics(par2, par3, par4, this.id);
            par1World.applyPhysics(par2, par3 - 1, par4, this.id);
        }

        super.remove(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        boolean var5 = par1IBlockAccess.getData(par2, par3, par4) == 1;
        float var6 = 0.0625F;

        if (var5)
        {
            this.a(var6, 0.0F, var6, 1.0F - var6, 0.03125F, 1.0F - var6);
        }
        else
        {
            this.a(var6, 0.0F, var6, 1.0F - var6, 0.0625F, 1.0F - var6);
        }
    }

    /**
     * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
     * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
     * Y, Z, side
     */
    public boolean b(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return par1IBlockAccess.getData(par2, par3, par4) > 0;
    }

    /**
     * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
     * side
     */
    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return par1IBlockAccess.getData(par2, par3, par4) == 0 ? false : par5 == 1;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean isPowerSource()
    {
        return true;
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void f()
    {
        float var1 = 0.5F;
        float var2 = 0.125F;
        float var3 = 0.5F;
        this.a(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int q_()
    {
        return 1;
    }
}
