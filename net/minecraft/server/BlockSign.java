package net.minecraft.server;

import java.util.Random;

public class BlockSign extends BlockContainer
{
    private Class a;

    /** Whether this is a freestanding sign or a wall-mounted sign */
    private boolean b;

    protected BlockSign(int par1, Class par2Class, boolean par3)
    {
        super(par1, Material.WOOD);
        this.b = par3;
        this.textureId = 4;
        this.a = par2Class;
        float var4 = 0.25F;
        float var5 = 1.0F;
        this.a(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var5, 0.5F + var4);
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
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        if (!this.b)
        {
            int var5 = par1IBlockAccess.getData(par2, par3, par4);
            float var6 = 0.28125F;
            float var7 = 0.78125F;
            float var8 = 0.0F;
            float var9 = 1.0F;
            float var10 = 0.125F;
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

            if (var5 == 2)
            {
                this.a(var8, var6, 1.0F - var10, var9, var7, 1.0F);
            }

            if (var5 == 3)
            {
                this.a(var8, var6, 0.0F, var9, var7, var10);
            }

            if (var5 == 4)
            {
                this.a(1.0F - var10, var6, var8, 1.0F, var7, var9);
            }

            if (var5 == 5)
            {
                this.a(0.0F, var6, var8, var10, var7, var9);
            }
        }
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return -1;
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
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean c()
    {
        return false;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity a(World par1World)
    {
        try
        {
            return (TileEntity)this.a.newInstance();
        }
        catch (Exception var3)
        {
            throw new RuntimeException(var3);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Item.SIGN.id;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        boolean var6 = false;

        if (this.b)
        {
            if (!par1World.getMaterial(par2, par3 - 1, par4).isBuildable())
            {
                var6 = true;
            }
        }
        else
        {
            int var7 = par1World.getData(par2, par3, par4);
            var6 = true;

            if (var7 == 2 && par1World.getMaterial(par2, par3, par4 + 1).isBuildable())
            {
                var6 = false;
            }

            if (var7 == 3 && par1World.getMaterial(par2, par3, par4 - 1).isBuildable())
            {
                var6 = false;
            }

            if (var7 == 4 && par1World.getMaterial(par2 + 1, par3, par4).isBuildable())
            {
                var6 = false;
            }

            if (var7 == 5 && par1World.getMaterial(par2 - 1, par3, par4).isBuildable())
            {
                var6 = false;
            }
        }

        if (var6)
        {
            this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
            par1World.setTypeId(par2, par3, par4, 0);
        }

        super.doPhysics(par1World, par2, par3, par4, par5);
    }
}
