package net.minecraft.server;

import java.util.Random;

public class WorldGenLargeFeatureStart extends StructureStart
{
    public WorldGenLargeFeatureStart(World par1World, Random par2Random, int par3, int par4)
    {
        BiomeBase var5 = par1World.getBiome(par3 * 16 + 8, par4 * 16 + 8);

        if (var5 != BiomeBase.JUNGLE && var5 != BiomeBase.JUNGLE_HILLS)
        {
            if (var5 == BiomeBase.SWAMPLAND)
            {
                WorldGenWitchHut var8 = new WorldGenWitchHut(par2Random, par3 * 16, par4 * 16);
                this.a.add(var8);
            }
            else
            {
                WorldGenPyramidPiece var7 = new WorldGenPyramidPiece(par2Random, par3 * 16, par4 * 16);
                this.a.add(var7);
            }
        }
        else
        {
            WorldGenJungleTemple var6 = new WorldGenJungleTemple(par2Random, par3 * 16, par4 * 16);
            this.a.add(var6);
        }

        this.c();
    }
}
