package net.minecraft.server;

import java.util.Random;

public class WorldGenEnder extends WorldGenerator
{
    /**
     * The Block ID that the generator is allowed to replace while generating the terrain.
     */
    private int a;

    public WorldGenEnder(int par1)
    {
        this.a = par1;
    }

    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        if (par1World.isEmpty(par3, par4, par5) && par1World.getTypeId(par3, par4 - 1, par5) == this.a)
        {
            int var6 = par2Random.nextInt(32) + 6;
            int var7 = par2Random.nextInt(4) + 1;
            int var8;
            int var9;
            int var10;
            int var11;

            for (var8 = par3 - var7; var8 <= par3 + var7; ++var8)
            {
                for (var9 = par5 - var7; var9 <= par5 + var7; ++var9)
                {
                    var10 = var8 - par3;
                    var11 = var9 - par5;

                    if (var10 * var10 + var11 * var11 <= var7 * var7 + 1 && par1World.getTypeId(var8, par4 - 1, var9) != this.a)
                    {
                        return false;
                    }
                }
            }

            for (var8 = par4; var8 < par4 + var6 && var8 < 128; ++var8)
            {
                for (var9 = par3 - var7; var9 <= par3 + var7; ++var9)
                {
                    for (var10 = par5 - var7; var10 <= par5 + var7; ++var10)
                    {
                        var11 = var9 - par3;
                        int var12 = var10 - par5;

                        if (var11 * var11 + var12 * var12 <= var7 * var7 + 1)
                        {
                            par1World.setTypeId(var9, var8, var10, Block.OBSIDIAN.id);
                        }
                    }
                }
            }

            EntityEnderCrystal var13 = new EntityEnderCrystal(par1World);
            var13.setPositionRotation((double) ((float) par3 + 0.5F), (double) (par4 + var6), (double) ((float) par5 + 0.5F), par2Random.nextFloat() * 360.0F, 0.0F);
            par1World.addEntity(var13);
            par1World.setTypeId(par3, par4 + var6, par5, Block.BEDROCK.id);
            return true;
        }
        else
        {
            return false;
        }
    }
}
