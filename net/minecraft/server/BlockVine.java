package net.minecraft.server;

import java.util.Random;

public class BlockVine extends Block
{
    public BlockVine(int par1)
    {
        super(par1, 143, Material.REPLACEABLE_PLANT);
        this.b(true);
        this.a(CreativeModeTab.c);
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void f()
    {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 20;
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
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var6 = par1IBlockAccess.getData(par2, par3, par4);
        float var7 = 1.0F;
        float var8 = 1.0F;
        float var9 = 1.0F;
        float var10 = 0.0F;
        float var11 = 0.0F;
        float var12 = 0.0F;
        boolean var13 = var6 > 0;

        if ((var6 & 2) != 0)
        {
            var10 = Math.max(var10, 0.0625F);
            var7 = 0.0F;
            var8 = 0.0F;
            var11 = 1.0F;
            var9 = 0.0F;
            var12 = 1.0F;
            var13 = true;
        }

        if ((var6 & 8) != 0)
        {
            var7 = Math.min(var7, 0.9375F);
            var10 = 1.0F;
            var8 = 0.0F;
            var11 = 1.0F;
            var9 = 0.0F;
            var12 = 1.0F;
            var13 = true;
        }

        if ((var6 & 4) != 0)
        {
            var12 = Math.max(var12, 0.0625F);
            var9 = 0.0F;
            var7 = 0.0F;
            var10 = 1.0F;
            var8 = 0.0F;
            var11 = 1.0F;
            var13 = true;
        }

        if ((var6 & 1) != 0)
        {
            var9 = Math.min(var9, 0.9375F);
            var12 = 1.0F;
            var7 = 0.0F;
            var10 = 1.0F;
            var8 = 0.0F;
            var11 = 1.0F;
            var13 = true;
        }

        if (!var13 && this.e(par1IBlockAccess.getTypeId(par2, par3 + 1, par4)))
        {
            var8 = Math.min(var8, 0.9375F);
            var11 = 1.0F;
            var7 = 0.0F;
            var10 = 1.0F;
            var9 = 0.0F;
            var12 = 1.0F;
        }

        this.a(var7, var8, var9, var10, var11, var12);
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
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4, int par5)
    {
        switch (par5)
        {
            case 1:
                return this.e(par1World.getTypeId(par2, par3 + 1, par4));

            case 2:
                return this.e(par1World.getTypeId(par2, par3, par4 + 1));

            case 3:
                return this.e(par1World.getTypeId(par2, par3, par4 - 1));

            case 4:
                return this.e(par1World.getTypeId(par2 + 1, par3, par4));

            case 5:
                return this.e(par1World.getTypeId(par2 - 1, par3, par4));

            default:
                return false;
        }
    }

    /**
     * returns true if a vine can be placed on that block (checks for render as normal block and if it is solid)
     */
    private boolean e(int par1)
    {
        if (par1 == 0)
        {
            return false;
        }
        else
        {
            Block var2 = Block.byId[par1];
            return var2.b() && var2.material.isSolid();
        }
    }

    /**
     * Returns if the vine can stay in the world. It also changes the metadata according to neighboring blocks.
     */
    private boolean l(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getData(par2, par3, par4);
        int var6 = var5;

        if (var5 > 0)
        {
            for (int var7 = 0; var7 <= 3; ++var7)
            {
                int var8 = 1 << var7;

                if ((var5 & var8) != 0 && !this.e(par1World.getTypeId(par2 + Direction.a[var7], par3, par4 + Direction.b[var7])) && (par1World.getTypeId(par2, par3 + 1, par4) != this.id || (par1World.getData(par2, par3 + 1, par4) & var8) == 0))
                {
                    var6 &= ~var8;
                }
            }
        }

        if (var6 == 0 && !this.e(par1World.getTypeId(par2, par3 + 1, par4)))
        {
            return false;
        }
        else
        {
            if (var6 != var5)
            {
                par1World.setData(par2, par3, par4, var6);
            }

            return true;
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isStatic && !this.l(par1World, par2, par3, par4))
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
        if (!par1World.isStatic && par1World.random.nextInt(4) == 0)
        {
            byte var6 = 4;
            int var7 = 5;
            boolean var8 = false;
            int var9;
            int var10;
            int var11;
            label138:

            for (var9 = par2 - var6; var9 <= par2 + var6; ++var9)
            {
                for (var10 = par4 - var6; var10 <= par4 + var6; ++var10)
                {
                    for (var11 = par3 - 1; var11 <= par3 + 1; ++var11)
                    {
                        if (par1World.getTypeId(var9, var11, var10) == this.id)
                        {
                            --var7;

                            if (var7 <= 0)
                            {
                                var8 = true;
                                break label138;
                            }
                        }
                    }
                }
            }

            var9 = par1World.getData(par2, par3, par4);
            var10 = par1World.random.nextInt(6);
            var11 = Direction.e[var10];
            int var12;
            int var13;

            if (var10 == 1 && par3 < 255 && par1World.isEmpty(par2, par3 + 1, par4))
            {
                if (var8)
                {
                    return;
                }

                var12 = par1World.random.nextInt(16) & var9;

                if (var12 > 0)
                {
                    for (var13 = 0; var13 <= 3; ++var13)
                    {
                        if (!this.e(par1World.getTypeId(par2 + Direction.a[var13], par3 + 1, par4 + Direction.b[var13])))
                        {
                            var12 &= ~(1 << var13);
                        }
                    }

                    if (var12 > 0)
                    {
                        par1World.setTypeIdAndData(par2, par3 + 1, par4, this.id, var12);
                    }
                }
            }
            else
            {
                int var14;

                if (var10 >= 2 && var10 <= 5 && (var9 & 1 << var11) == 0)
                {
                    if (var8)
                    {
                        return;
                    }

                    var12 = par1World.getTypeId(par2 + Direction.a[var11], par3, par4 + Direction.b[var11]);

                    if (var12 != 0 && Block.byId[var12] != null)
                    {
                        if (Block.byId[var12].material.k() && Block.byId[var12].b())
                        {
                            par1World.setData(par2, par3, par4, var9 | 1 << var11);
                        }
                    }
                    else
                    {
                        var13 = var11 + 1 & 3;
                        var14 = var11 + 3 & 3;

                        if ((var9 & 1 << var13) != 0 && this.e(par1World.getTypeId(par2 + Direction.a[var11] + Direction.a[var13], par3, par4 + Direction.b[var11] + Direction.b[var13])))
                        {
                            par1World.setTypeIdAndData(par2 + Direction.a[var11], par3, par4 + Direction.b[var11], this.id, 1 << var13);
                        }
                        else if ((var9 & 1 << var14) != 0 && this.e(par1World.getTypeId(par2 + Direction.a[var11] + Direction.a[var14], par3, par4 + Direction.b[var11] + Direction.b[var14])))
                        {
                            par1World.setTypeIdAndData(par2 + Direction.a[var11], par3, par4 + Direction.b[var11], this.id, 1 << var14);
                        }
                        else if ((var9 & 1 << var13) != 0 && par1World.isEmpty(par2 + Direction.a[var11] + Direction.a[var13], par3, par4 + Direction.b[var11] + Direction.b[var13]) && this.e(par1World.getTypeId(par2 + Direction.a[var13], par3, par4 + Direction.b[var13])))
                        {
                            par1World.setTypeIdAndData(par2 + Direction.a[var11] + Direction.a[var13], par3, par4 + Direction.b[var11] + Direction.b[var13], this.id, 1 << (var11 + 2 & 3));
                        }
                        else if ((var9 & 1 << var14) != 0 && par1World.isEmpty(par2 + Direction.a[var11] + Direction.a[var14], par3, par4 + Direction.b[var11] + Direction.b[var14]) && this.e(par1World.getTypeId(par2 + Direction.a[var14], par3, par4 + Direction.b[var14])))
                        {
                            par1World.setTypeIdAndData(par2 + Direction.a[var11] + Direction.a[var14], par3, par4 + Direction.b[var11] + Direction.b[var14], this.id, 1 << (var11 + 2 & 3));
                        }
                        else if (this.e(par1World.getTypeId(par2 + Direction.a[var11], par3 + 1, par4 + Direction.b[var11])))
                        {
                            par1World.setTypeIdAndData(par2 + Direction.a[var11], par3, par4 + Direction.b[var11], this.id, 0);
                        }
                    }
                }
                else if (par3 > 1)
                {
                    var12 = par1World.getTypeId(par2, par3 - 1, par4);

                    if (var12 == 0)
                    {
                        var13 = par1World.random.nextInt(16) & var9;

                        if (var13 > 0)
                        {
                            par1World.setTypeIdAndData(par2, par3 - 1, par4, this.id, var13);
                        }
                    }
                    else if (var12 == this.id)
                    {
                        var13 = par1World.random.nextInt(16) & var9;
                        var14 = par1World.getData(par2, par3 - 1, par4);

                        if (var14 != (var14 | var13))
                        {
                            par1World.setData(par2, par3 - 1, par4, var14 | var13);
                        }
                    }
                }
            }
        }
    }

    public int getPlacedData(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        byte var10 = 0;

        switch (par5)
        {
            case 2:
                var10 = 1;
                break;

            case 3:
                var10 = 4;
                break;

            case 4:
                var10 = 8;
                break;

            case 5:
                var10 = 2;
        }

        return var10 != 0 ? var10 : par9;
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
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void a(World par1World, EntityHuman par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        if (!par1World.isStatic && par2EntityPlayer.bT() != null && par2EntityPlayer.bT().id == Item.SHEARS.id)
        {
            par2EntityPlayer.a(StatisticList.C[this.id], 1);
            this.b(par1World, par3, par4, par5, new ItemStack(Block.VINE, 1, 0));
        }
        else
        {
            super.a(par1World, par2EntityPlayer, par3, par4, par5, par6);
        }
    }
}
