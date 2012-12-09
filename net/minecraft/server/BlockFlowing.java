package net.minecraft.server;

import java.util.Random;

public class BlockFlowing extends BlockFluids
{
    /**
     * Number of horizontally adjacent liquid source blocks. Diagonal doesn't count. Only source blocks of the same
     * liquid as the block using the field are counted.
     */
    int a = 0;

    /**
     * Indicates whether the flow direction is optimal. Each array index corresponds to one of the four cardinal
     * directions.
     */
    boolean[] b = new boolean[4];

    /**
     * The estimated cost to flow in a given direction from the current point. Each array index corresponds to one of
     * the four cardinal directions.
     */
    int[] c = new int[4];

    protected BlockFlowing(int par1, Material par2Material)
    {
        super(par1, par2Material);
    }

    /**
     * Updates the flow for the BlockFlowing object.
     */
    private void l(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getData(par2, par3, par4);
        par1World.setRawTypeIdAndData(par2, par3, par4, this.id + 1, var5);
        par1World.e(par2, par3, par4, par2, par3, par4);
    }

    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return this.material != Material.LAVA;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int var6 = this.f_(par1World, par2, par3, par4);
        byte var7 = 1;

        if (this.material == Material.LAVA && !par1World.worldProvider.e)
        {
            var7 = 2;
        }

        boolean var8 = true;
        int var10;

        if (var6 > 0)
        {
            byte var9 = -100;
            this.a = 0;
            int var12 = this.d(par1World, par2 - 1, par3, par4, var9);
            var12 = this.d(par1World, par2 + 1, par3, par4, var12);
            var12 = this.d(par1World, par2, par3, par4 - 1, var12);
            var12 = this.d(par1World, par2, par3, par4 + 1, var12);
            var10 = var12 + var7;

            if (var10 >= 8 || var12 < 0)
            {
                var10 = -1;
            }

            if (this.f_(par1World, par2, par3 + 1, par4) >= 0)
            {
                int var11 = this.f_(par1World, par2, par3 + 1, par4);

                if (var11 >= 8)
                {
                    var10 = var11;
                }
                else
                {
                    var10 = var11 + 8;
                }
            }

            if (this.a >= 2 && this.material == Material.WATER)
            {
                if (par1World.getMaterial(par2, par3 - 1, par4).isBuildable())
                {
                    var10 = 0;
                }
                else if (par1World.getMaterial(par2, par3 - 1, par4) == this.material && par1World.getData(par2, par3, par4) == 0)
                {
                    var10 = 0;
                }
            }

            if (this.material == Material.LAVA && var6 < 8 && var10 < 8 && var10 > var6 && par5Random.nextInt(4) != 0)
            {
                var10 = var6;
                var8 = false;
            }

            if (var10 == var6)
            {
                if (var8)
                {
                    this.l(par1World, par2, par3, par4);
                }
            }
            else
            {
                var6 = var10;

                if (var10 < 0)
                {
                    par1World.setTypeId(par2, par3, par4, 0);
                }
                else
                {
                    par1World.setData(par2, par3, par4, var10);
                    par1World.a(par2, par3, par4, this.id, this.r_());
                    par1World.applyPhysics(par2, par3, par4, this.id);
                }
            }
        }
        else
        {
            this.l(par1World, par2, par3, par4);
        }

        if (this.p(par1World, par2, par3 - 1, par4))
        {
            if (this.material == Material.LAVA && par1World.getMaterial(par2, par3 - 1, par4) == Material.WATER)
            {
                par1World.setTypeId(par2, par3 - 1, par4, Block.STONE.id);
                this.fizz(par1World, par2, par3 - 1, par4);
                return;
            }

            if (var6 >= 8)
            {
                this.flow(par1World, par2, par3 - 1, par4, var6);
            }
            else
            {
                this.flow(par1World, par2, par3 - 1, par4, var6 + 8);
            }
        }
        else if (var6 >= 0 && (var6 == 0 || this.o(par1World, par2, par3 - 1, par4)))
        {
            boolean[] var13 = this.n(par1World, par2, par3, par4);
            var10 = var6 + var7;

            if (var6 >= 8)
            {
                var10 = 1;
            }

            if (var10 >= 8)
            {
                return;
            }

            if (var13[0])
            {
                this.flow(par1World, par2 - 1, par3, par4, var10);
            }

            if (var13[1])
            {
                this.flow(par1World, par2 + 1, par3, par4, var10);
            }

            if (var13[2])
            {
                this.flow(par1World, par2, par3, par4 - 1, var10);
            }

            if (var13[3])
            {
                this.flow(par1World, par2, par3, par4 + 1, var10);
            }
        }
    }

    /**
     * flowIntoBlock(World world, int x, int y, int z, int newFlowDecay) - Flows into the block at the coordinates and
     * changes the block type to the liquid.
     */
    private void flow(World par1World, int par2, int par3, int par4, int par5)
    {
        if (this.p(par1World, par2, par3, par4))
        {
            int var6 = par1World.getTypeId(par2, par3, par4);

            if (var6 > 0)
            {
                if (this.material == Material.LAVA)
                {
                    this.fizz(par1World, par2, par3, par4);
                }
                else
                {
                    Block.byId[var6].c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
                }
            }

            par1World.setTypeIdAndData(par2, par3, par4, this.id, par5);
        }
    }

    /**
     * calculateFlowCost(World world, int x, int y, int z, int accumulatedCost, int previousDirectionOfFlow) - Used to
     * determine the path of least resistance, this method returns the lowest possible flow cost for the direction of
     * flow indicated. Each necessary horizontal flow adds to the flow cost.
     */
    private int d(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        int var7 = 1000;

        for (int var8 = 0; var8 < 4; ++var8)
        {
            if ((var8 != 0 || par6 != 1) && (var8 != 1 || par6 != 0) && (var8 != 2 || par6 != 3) && (var8 != 3 || par6 != 2))
            {
                int var9 = par2;
                int var11 = par4;

                if (var8 == 0)
                {
                    var9 = par2 - 1;
                }

                if (var8 == 1)
                {
                    ++var9;
                }

                if (var8 == 2)
                {
                    var11 = par4 - 1;
                }

                if (var8 == 3)
                {
                    ++var11;
                }

                if (!this.o(par1World, var9, par3, var11) && (par1World.getMaterial(var9, par3, var11) != this.material || par1World.getData(var9, par3, var11) != 0))
                {
                    if (!this.o(par1World, var9, par3 - 1, var11))
                    {
                        return par5;
                    }

                    if (par5 < 4)
                    {
                        int var12 = this.d(par1World, var9, par3, var11, par5 + 1, var8);

                        if (var12 < var7)
                        {
                            var7 = var12;
                        }
                    }
                }
            }
        }

        return var7;
    }

    /**
     * Returns a boolean array indicating which flow directions are optimal based on each direction's calculated flow
     * cost. Each array index corresponds to one of the four cardinal directions. A value of true indicates the
     * direction is optimal.
     */
    private boolean[] n(World par1World, int par2, int par3, int par4)
    {
        int var5;
        int var6;

        for (var5 = 0; var5 < 4; ++var5)
        {
            this.c[var5] = 1000;
            var6 = par2;
            int var8 = par4;

            if (var5 == 0)
            {
                var6 = par2 - 1;
            }

            if (var5 == 1)
            {
                ++var6;
            }

            if (var5 == 2)
            {
                var8 = par4 - 1;
            }

            if (var5 == 3)
            {
                ++var8;
            }

            if (!this.o(par1World, var6, par3, var8) && (par1World.getMaterial(var6, par3, var8) != this.material || par1World.getData(var6, par3, var8) != 0))
            {
                if (this.o(par1World, var6, par3 - 1, var8))
                {
                    this.c[var5] = this.d(par1World, var6, par3, var8, 1, var5);
                }
                else
                {
                    this.c[var5] = 0;
                }
            }
        }

        var5 = this.c[0];

        for (var6 = 1; var6 < 4; ++var6)
        {
            if (this.c[var6] < var5)
            {
                var5 = this.c[var6];
            }
        }

        for (var6 = 0; var6 < 4; ++var6)
        {
            this.b[var6] = this.c[var6] == var5;
        }

        return this.b;
    }

    /**
     * Returns true if block at coords blocks fluids
     */
    private boolean o(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getTypeId(par2, par3, par4);

        if (var5 != Block.WOODEN_DOOR.id && var5 != Block.IRON_DOOR_BLOCK.id && var5 != Block.SIGN_POST.id && var5 != Block.LADDER.id && var5 != Block.SUGAR_CANE_BLOCK.id)
        {
            if (var5 == 0)
            {
                return false;
            }
            else
            {
                Material var6 = Block.byId[var5].material;
                return var6 == Material.PORTAL ? true : var6.isSolid();
            }
        }
        else
        {
            return true;
        }
    }

    /**
     * getSmallestFlowDecay(World world, intx, int y, int z, int currentSmallestFlowDecay) - Looks up the flow decay at
     * the coordinates given and returns the smaller of this value or the provided currentSmallestFlowDecay. If one
     * value is valid and the other isn't, the valid value will be returned. Valid values are >= 0. Flow decay is the
     * amount that a liquid has dissipated. 0 indicates a source block.
     */
    protected int d(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = this.f_(par1World, par2, par3, par4);

        if (var6 < 0)
        {
            return par5;
        }
        else
        {
            if (var6 == 0)
            {
                ++this.a;
            }

            if (var6 >= 8)
            {
                var6 = 0;
            }

            return par5 >= 0 && var6 >= par5 ? par5 : var6;
        }
    }

    /**
     * Returns true if the block at the coordinates can be displaced by the liquid.
     */
    private boolean p(World par1World, int par2, int par3, int par4)
    {
        Material var5 = par1World.getMaterial(par2, par3, par4);
        return var5 == this.material ? false : (var5 == Material.LAVA ? false : !this.o(par1World, par2, par3, par4));
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        super.onPlace(par1World, par2, par3, par4);

        if (par1World.getTypeId(par2, par3, par4) == this.id)
        {
            par1World.a(par2, par3, par4, this.id, this.r_());
        }
    }

    public boolean l()
    {
        return false;
    }
}
