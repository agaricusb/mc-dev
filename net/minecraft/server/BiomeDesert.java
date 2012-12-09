package net.minecraft.server;

import java.util.Random;

public class BiomeDesert extends BiomeBase
{
    public BiomeDesert(int par1)
    {
        super(par1);
        this.K.clear();
        this.A = (byte) Block.SAND.id;
        this.B = (byte) Block.SAND.id;
        this.I.z = -999;
        this.I.C = 2;
        this.I.E = 50;
        this.I.F = 10;
    }

    public void a(World par1World, Random par2Random, int par3, int par4)
    {
        super.a(par1World, par2Random, par3, par4);

        if (par2Random.nextInt(1000) == 0)
        {
            int var5 = par3 + par2Random.nextInt(16) + 8;
            int var6 = par4 + par2Random.nextInt(16) + 8;
            WorldGenDesertWell var7 = new WorldGenDesertWell();
            var7.a(par1World, par2Random, var5, par1World.getHighestBlockYAt(var5, var6) + 1, var6);
        }
    }
}
