package net.minecraft.server;

import java.util.Random;

public class WorldGenGrass extends WorldGenerator
{
    /** Stores ID for WorldGenTallGrass */
    private int a;
    private int b;

    public WorldGenGrass(int par1, int par2)
    {
        this.a = par1;
        this.b = par2;
    }

    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var11;

        for (boolean var6 = false; ((var11 = par1World.getTypeId(par3, par4, par5)) == 0 || var11 == Block.LEAVES.id) && par4 > 0; --par4)
        {
            ;
        }

        for (int var7 = 0; var7 < 128; ++var7)
        {
            int var8 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var9 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var10 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isEmpty(var8, var9, var10) && Block.byId[this.a].d(par1World, var8, var9, var10))
            {
                par1World.setRawTypeIdAndData(var8, var9, var10, this.a, this.b);
            }
        }

        return true;
    }
}
