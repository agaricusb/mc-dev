package net.minecraft.server;

public class BlockTrapdoor extends Block
{
    protected BlockTrapdoor(int par1, Material par2Material)
    {
        super(par1, par2Material);
        this.textureId = 84;

        if (par2Material == Material.ORE)
        {
            ++this.textureId;
        }

        float var3 = 0.5F;
        float var4 = 1.0F;
        this.a(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4, 0.5F + var3);
        this.a(CreativeModeTab.d);
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
        return !g(par1IBlockAccess.getData(par2, par3, par4));
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 0;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        this.updateShape(par1World, par2, par3, par4);
        return super.e(par1World, par2, par3, par4);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        this.e(par1IBlockAccess.getData(par2, par3, par4));
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void f()
    {
        float var1 = 0.1875F;
        this.a(0.0F, 0.5F - var1 / 2.0F, 0.0F, 1.0F, 0.5F + var1 / 2.0F, 1.0F);
    }

    public void e(int par1)
    {
        float var2 = 0.1875F;

        if ((par1 & 8) != 0)
        {
            this.a(0.0F, 1.0F - var2, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, var2, 1.0F);
        }

        if (g(par1))
        {
            if ((par1 & 3) == 0)
            {
                this.a(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            }

            if ((par1 & 3) == 1)
            {
                this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            }

            if ((par1 & 3) == 2)
            {
                this.a(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }

            if ((par1 & 3) == 3)
            {
                this.a(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            }
        }
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void attack(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer) {}

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (this.material == Material.ORE)
        {
            return true;
        }
        else
        {
            int var10 = par1World.getData(par2, par3, par4);
            par1World.setData(par2, par3, par4, var10 ^ 4);
            par1World.a(par5EntityPlayer, 1003, par2, par3, par4, 0);
            return true;
        }
    }

    public void setOpen(World par1World, int par2, int par3, int par4, boolean par5)
    {
        int var6 = par1World.getData(par2, par3, par4);
        boolean var7 = (var6 & 4) > 0;

        if (var7 != par5)
        {
            par1World.setData(par2, par3, par4, var6 ^ 4);
            par1World.a((EntityHuman) null, 1003, par2, par3, par4, 0);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isStatic)
        {
            int var6 = par1World.getData(par2, par3, par4);
            int var7 = par2;
            int var8 = par4;

            if ((var6 & 3) == 0)
            {
                var8 = par4 + 1;
            }

            if ((var6 & 3) == 1)
            {
                --var8;
            }

            if ((var6 & 3) == 2)
            {
                var7 = par2 + 1;
            }

            if ((var6 & 3) == 3)
            {
                --var7;
            }

            if (!j(par1World.getTypeId(var7, par3, var8)))
            {
                par1World.setTypeId(par2, par3, par4, 0);
                this.c(par1World, par2, par3, par4, var6, 0);
            }

            boolean var9 = par1World.isBlockIndirectlyPowered(par2, par3, par4);

            if (var9 || par5 > 0 && Block.byId[par5].isPowerSource())
            {
                this.setOpen(par1World, par2, par3, par4, var9);
            }
        }
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition a(World par1World, int par2, int par3, int par4, Vec3D par5Vec3, Vec3D par6Vec3)
    {
        this.updateShape(par1World, par2, par3, par4);
        return super.a(par1World, par2, par3, par4, par5Vec3, par6Vec3);
    }

    public int getPlacedData(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int var10 = 0;

        if (par5 == 2)
        {
            var10 = 0;
        }

        if (par5 == 3)
        {
            var10 = 1;
        }

        if (par5 == 4)
        {
            var10 = 2;
        }

        if (par5 == 5)
        {
            var10 = 3;
        }

        if (par5 != 1 && par5 != 0 && par7 > 0.5F)
        {
            var10 |= 8;
        }

        return var10;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par5 == 0)
        {
            return false;
        }
        else if (par5 == 1)
        {
            return false;
        }
        else
        {
            if (par5 == 2)
            {
                ++par4;
            }

            if (par5 == 3)
            {
                --par4;
            }

            if (par5 == 4)
            {
                ++par2;
            }

            if (par5 == 5)
            {
                --par2;
            }

            return j(par1World.getTypeId(par2, par3, par4));
        }
    }

    public static boolean g(int par0)
    {
        return (par0 & 4) != 0;
    }

    /**
     * Checks if the block ID is a valid support block for the trap door to connect with. If it is not the trapdoor is
     * dropped into the world.
     */
    private static boolean j(int par0)
    {
        if (par0 <= 0)
        {
            return false;
        }
        else
        {
            Block var1 = Block.byId[par0];
            return var1 != null && var1.material.k() && var1.b() || var1 == Block.GLOWSTONE || var1 instanceof BlockStepAbstract || var1 instanceof BlockStairs;
        }
    }
}
