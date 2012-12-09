package net.minecraft.server;

public class GenLayerSwampRivers extends GenLayer
{
    public GenLayerSwampRivers(long par1, GenLayer par3GenLayer)
    {
        super(par1);
        this.a = par3GenLayer;
    }

    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    public int[] a(int par1, int par2, int par3, int par4)
    {
        int[] var5 = this.a.a(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
        int[] var6 = IntCache.a(par3 * par4);

        for (int var7 = 0; var7 < par4; ++var7)
        {
            for (int var8 = 0; var8 < par3; ++var8)
            {
                this.a((long) (var8 + par1), (long) (var7 + par2));
                int var9 = var5[var8 + 1 + (var7 + 1) * (par3 + 2)];

                if ((var9 != BiomeBase.SWAMPLAND.id || this.a(6) != 0) && (var9 != BiomeBase.JUNGLE.id && var9 != BiomeBase.JUNGLE_HILLS.id || this.a(8) != 0))
                {
                    var6[var8 + var7 * par3] = var9;
                }
                else
                {
                    var6[var8 + var7 * par3] = BiomeBase.RIVER.id;
                }
            }
        }

        return var6;
    }
}
