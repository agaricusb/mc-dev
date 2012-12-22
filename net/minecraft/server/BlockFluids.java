package net.minecraft.server;

import java.util.Random;

public abstract class BlockFluids extends Block
{
    protected BlockFluids(int par1, Material par2Material)
    {
        super(par1, (par2Material == Material.LAVA ? 14 : 12) * 16 + 13, par2Material);
        float var3 = 0.0F;
        float var4 = 0.0F;
        this.a(0.0F + var4, 0.0F + var3, 0.0F + var4, 1.0F + var4, 1.0F + var3, 1.0F + var4);
        this.b(true);
    }

    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return this.material != Material.LAVA;
    }

    /**
     * Returns the percentage of the fluid block that is air, based on the given flow decay of the fluid.
     */
    public static float e(int par0)
    {
        if (par0 >= 8)
        {
            par0 = 0;
        }

        return (float)(par0 + 1) / 9.0F;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return par1 != 0 && par1 != 1 ? this.textureId + 1 : this.textureId;
    }

    /**
     * Returns the amount of fluid decay at the coordinates, or -1 if the block at the coordinates is not the same
     * material as the fluid.
     */
    protected int f_(World par1World, int par2, int par3, int par4)
    {
        return par1World.getMaterial(par2, par3, par4) == this.material ? par1World.getData(par2, par3, par4) : -1;
    }

    /**
     * Returns the flow decay but converts values indicating falling liquid (values >=8) to their effective source block
     * value of zero.
     */
    protected int d(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        if (par1IBlockAccess.getMaterial(par2, par3, par4) != this.material)
        {
            return -1;
        }
        else
        {
            int var5 = par1IBlockAccess.getData(par2, par3, par4);

            if (var5 >= 8)
            {
                var5 = 0;
            }

            return var5;
        }
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
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
     * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
     */
    public boolean a(int par1, boolean par2)
    {
        return par2 && par1 == 0;
    }

    /**
     * Returns Returns true if the given side of this block type should be rendered (if it's solid or not), if the
     * adjacent block is at the given coordinates. Args: blockAccess, x, y, z, side
     */
    public boolean a_(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        Material var6 = par1IBlockAccess.getMaterial(par2, par3, par4);
        return var6 == this.material ? false : (par5 == 1 ? true : (var6 == Material.ICE ? false : super.a_(par1IBlockAccess, par2, par3, par4, par5)));
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
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 4;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return 0;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 0;
    }

    /**
     * Returns a vector indicating the direction and intensity of fluid flow.
     */
    private Vec3D g(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        Vec3D var5 = par1IBlockAccess.getVec3DPool().create(0.0D, 0.0D, 0.0D);
        int var6 = this.d(par1IBlockAccess, par2, par3, par4);

        for (int var7 = 0; var7 < 4; ++var7)
        {
            int var8 = par2;
            int var10 = par4;

            if (var7 == 0)
            {
                var8 = par2 - 1;
            }

            if (var7 == 1)
            {
                var10 = par4 - 1;
            }

            if (var7 == 2)
            {
                ++var8;
            }

            if (var7 == 3)
            {
                ++var10;
            }

            int var11 = this.d(par1IBlockAccess, var8, par3, var10);
            int var12;

            if (var11 < 0)
            {
                if (!par1IBlockAccess.getMaterial(var8, par3, var10).isSolid())
                {
                    var11 = this.d(par1IBlockAccess, var8, par3 - 1, var10);

                    if (var11 >= 0)
                    {
                        var12 = var11 - (var6 - 8);
                        var5 = var5.add((double) ((var8 - par2) * var12), (double) ((par3 - par3) * var12), (double) ((var10 - par4) * var12));
                    }
                }
            }
            else if (var11 >= 0)
            {
                var12 = var11 - var6;
                var5 = var5.add((double) ((var8 - par2) * var12), (double) ((par3 - par3) * var12), (double) ((var10 - par4) * var12));
            }
        }

        if (par1IBlockAccess.getData(par2, par3, par4) >= 8)
        {
            boolean var13 = false;

            if (var13 || this.a_(par1IBlockAccess, par2, par3, par4 - 1, 2))
            {
                var13 = true;
            }

            if (var13 || this.a_(par1IBlockAccess, par2, par3, par4 + 1, 3))
            {
                var13 = true;
            }

            if (var13 || this.a_(par1IBlockAccess, par2 - 1, par3, par4, 4))
            {
                var13 = true;
            }

            if (var13 || this.a_(par1IBlockAccess, par2 + 1, par3, par4, 5))
            {
                var13 = true;
            }

            if (var13 || this.a_(par1IBlockAccess, par2, par3 + 1, par4 - 1, 2))
            {
                var13 = true;
            }

            if (var13 || this.a_(par1IBlockAccess, par2, par3 + 1, par4 + 1, 3))
            {
                var13 = true;
            }

            if (var13 || this.a_(par1IBlockAccess, par2 - 1, par3 + 1, par4, 4))
            {
                var13 = true;
            }

            if (var13 || this.a_(par1IBlockAccess, par2 + 1, par3 + 1, par4, 5))
            {
                var13 = true;
            }

            if (var13)
            {
                var5 = var5.a().add(0.0D, -6.0D, 0.0D);
            }
        }

        var5 = var5.a();
        return var5;
    }

    /**
     * Can add to the passed in vector for a movement vector to be applied to the entity. Args: x, y, z, entity, vec3d
     */
    public void a(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3D par6Vec3)
    {
        Vec3D var7 = this.g(par1World, par2, par3, par4);
        par6Vec3.c += var7.c;
        par6Vec3.d += var7.d;
        par6Vec3.e += var7.e;
    }

    /**
     * How many world ticks before ticking
     */
    public int r_()
    {
        return this.material == Material.WATER ? 5 : (this.material == Material.LAVA ? 30 : 0);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        this.l(par1World, par2, par3, par4);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        this.l(par1World, par2, par3, par4);
    }

    /**
     * Forces lava to check to see if it is colliding with water, and then decide what it should harden to.
     */
    private void l(World par1World, int par2, int par3, int par4)
    {
        if (par1World.getTypeId(par2, par3, par4) == this.id)
        {
            if (this.material == Material.LAVA)
            {
                boolean var5 = false;

                if (var5 || par1World.getMaterial(par2, par3, par4 - 1) == Material.WATER)
                {
                    var5 = true;
                }

                if (var5 || par1World.getMaterial(par2, par3, par4 + 1) == Material.WATER)
                {
                    var5 = true;
                }

                if (var5 || par1World.getMaterial(par2 - 1, par3, par4) == Material.WATER)
                {
                    var5 = true;
                }

                if (var5 || par1World.getMaterial(par2 + 1, par3, par4) == Material.WATER)
                {
                    var5 = true;
                }

                if (var5 || par1World.getMaterial(par2, par3 + 1, par4) == Material.WATER)
                {
                    var5 = true;
                }

                if (var5)
                {
                    int var6 = par1World.getData(par2, par3, par4);

                    if (var6 == 0)
                    {
                        par1World.setTypeId(par2, par3, par4, Block.OBSIDIAN.id);
                    }
                    else if (var6 <= 4)
                    {
                        par1World.setTypeId(par2, par3, par4, Block.COBBLESTONE.id);
                    }

                    this.fizz(par1World, par2, par3, par4);
                }
            }
        }
    }

    /**
     * Creates fizzing sound and smoke. Used when lava flows over block or mixes with water.
     */
    protected void fizz(World par1World, int par2, int par3, int par4)
    {
        par1World.makeSound((double) ((float) par2 + 0.5F), (double) ((float) par3 + 0.5F), (double) ((float) par4 + 0.5F), "random.fizz", 0.5F, 2.6F + (par1World.random.nextFloat() - par1World.random.nextFloat()) * 0.8F);

        for (int var5 = 0; var5 < 8; ++var5)
        {
            par1World.addParticle("largesmoke", (double) par2 + Math.random(), (double) par3 + 1.2D, (double) par4 + Math.random(), 0.0D, 0.0D, 0.0D);
        }
    }
}
