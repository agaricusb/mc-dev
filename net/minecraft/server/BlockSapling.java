package net.minecraft.server;

import java.util.Random;

public class BlockSapling extends BlockFlower
{
    public static final String[] a = new String[] {"oak", "spruce", "birch", "jungle"};

    protected BlockSapling(int par1, int par2)
    {
        super(par1, par2);
        float var3 = 0.4F;
        this.a(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
        this.a(CreativeModeTab.c);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isStatic)
        {
            super.b(par1World, par2, par3, par4, par5Random);

            if (par1World.getLightLevel(par2, par3 + 1, par4) >= 9 && par5Random.nextInt(7) == 0)
            {
                int var6 = par1World.getData(par2, par3, par4);

                if ((var6 & 8) == 0)
                {
                    par1World.setData(par2, par3, par4, var6 | 8);
                }
                else
                {
                    this.grow(par1World, par2, par3, par4, par5Random);
                }
            }
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        par2 &= 3;
        return par2 == 1 ? 63 : (par2 == 2 ? 79 : (par2 == 3 ? 30 : super.a(par1, par2)));
    }

    /**
     * Attempts to grow a sapling into a tree
     */
    public void grow(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int var6 = par1World.getData(par2, par3, par4) & 3;
        Object var7 = null;
        int var8 = 0;
        int var9 = 0;
        boolean var10 = false;

        if (var6 == 1)
        {
            var7 = new WorldGenTaiga2(true);
        }
        else if (var6 == 2)
        {
            var7 = new WorldGenForest(true);
        }
        else if (var6 == 3)
        {
            for (var8 = 0; var8 >= -1; --var8)
            {
                for (var9 = 0; var9 >= -1; --var9)
                {
                    if (this.d(par1World, par2 + var8, par3, par4 + var9, 3) && this.d(par1World, par2 + var8 + 1, par3, par4 + var9, 3) && this.d(par1World, par2 + var8, par3, par4 + var9 + 1, 3) && this.d(par1World, par2 + var8 + 1, par3, par4 + var9 + 1, 3))
                    {
                        var7 = new WorldGenMegaTree(true, 10 + par5Random.nextInt(20), 3, 3);
                        var10 = true;
                        break;
                    }
                }

                if (var7 != null)
                {
                    break;
                }
            }

            if (var7 == null)
            {
                var9 = 0;
                var8 = 0;
                var7 = new WorldGenTrees(true, 4 + par5Random.nextInt(7), 3, 3, false);
            }
        }
        else
        {
            var7 = new WorldGenTrees(true);

            if (par5Random.nextInt(10) == 0)
            {
                var7 = new WorldGenBigTree(true);
            }
        }

        if (var10)
        {
            par1World.setRawTypeId(par2 + var8, par3, par4 + var9, 0);
            par1World.setRawTypeId(par2 + var8 + 1, par3, par4 + var9, 0);
            par1World.setRawTypeId(par2 + var8, par3, par4 + var9 + 1, 0);
            par1World.setRawTypeId(par2 + var8 + 1, par3, par4 + var9 + 1, 0);
        }
        else
        {
            par1World.setRawTypeId(par2, par3, par4, 0);
        }

        if (!((WorldGenerator)var7).a(par1World, par5Random, par2 + var8, par3, par4 + var9))
        {
            if (var10)
            {
                par1World.setRawTypeIdAndData(par2 + var8, par3, par4 + var9, this.id, var6);
                par1World.setRawTypeIdAndData(par2 + var8 + 1, par3, par4 + var9, this.id, var6);
                par1World.setRawTypeIdAndData(par2 + var8, par3, par4 + var9 + 1, this.id, var6);
                par1World.setRawTypeIdAndData(par2 + var8 + 1, par3, par4 + var9 + 1, this.id, var6);
            }
            else
            {
                par1World.setRawTypeIdAndData(par2, par3, par4, this.id, var6);
            }
        }
    }

    /**
     * Determines if the same sapling is present at the given location.
     */
    public boolean d(World par1World, int par2, int par3, int par4, int par5)
    {
        return par1World.getTypeId(par2, par3, par4) == this.id && (par1World.getData(par2, par3, par4) & 3) == par5;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int getDropData(int par1)
    {
        return par1 & 3;
    }
}
