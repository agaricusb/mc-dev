package net.minecraft.server;

import java.util.Random;

public class BiomeBigHills extends BiomeBase
{
    private WorldGenerator S;

    protected BiomeBigHills(int par1)
    {
        super(par1);
        this.S = new WorldGenMinable(Block.MONSTER_EGGS.id, 8);
    }

    public void a(World par1World, Random par2Random, int par3, int par4)
    {
        super.a(par1World, par2Random, par3, par4);
        int var5 = 3 + par2Random.nextInt(6);
        int var6;
        int var7;
        int var8;

        for (var6 = 0; var6 < var5; ++var6)
        {
            var7 = par3 + par2Random.nextInt(16);
            var8 = par2Random.nextInt(28) + 4;
            int var9 = par4 + par2Random.nextInt(16);
            int var10 = par1World.getTypeId(var7, var8, var9);

            if (var10 == Block.STONE.id)
            {
                par1World.setRawTypeId(var7, var8, var9, Block.EMERALD_ORE.id);
            }
        }

        for (var5 = 0; var5 < 7; ++var5)
        {
            var6 = par3 + par2Random.nextInt(16);
            var7 = par2Random.nextInt(64);
            var8 = par4 + par2Random.nextInt(16);
            this.S.a(par1World, par2Random, var6, var7, var8);
        }
    }
}
