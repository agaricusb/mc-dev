package net.minecraft.server;

import java.util.Random;

public class WorldGenDeadBush extends WorldGenerator
{
    /** stores the ID for WorldGenDeadBush */
    private int a;

    public WorldGenDeadBush(int par1)
    {
        this.a = par1;
    }

    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var11;

        for (boolean var6 = false; ((var11 = par1World.getTypeId(par3, par4, par5)) == 0 || var11 == Block.LEAVES.id) && par4 > 0; --par4)
        {
            ;
        }

        for (int var7 = 0; var7 < 4; ++var7)
        {
            int var8 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var9 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var10 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isEmpty(var8, var9, var10) && Block.byId[this.a].d(par1World, var8, var9, var10))
            {
                par1World.setRawTypeId(var8, var9, var10, this.a);
            }
        }

        return true;
    }
}
