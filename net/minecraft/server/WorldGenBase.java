package net.minecraft.server;

import java.util.Random;

public class WorldGenBase
{
    /** The number of Chunks to gen-check in any given direction. */
    protected int a = 8;

    /** The RNG used by the MapGen classes. */
    protected Random b = new Random();

    /** This world object. */
    protected World c;

    public void a(IChunkProvider par1IChunkProvider, World par2World, int par3, int par4, byte[] par5ArrayOfByte)
    {
        int var6 = this.a;
        this.c = par2World;
        this.b.setSeed(par2World.getSeed());
        long var7 = this.b.nextLong();
        long var9 = this.b.nextLong();

        for (int var11 = par3 - var6; var11 <= par3 + var6; ++var11)
        {
            for (int var12 = par4 - var6; var12 <= par4 + var6; ++var12)
            {
                long var13 = (long)var11 * var7;
                long var15 = (long)var12 * var9;
                this.b.setSeed(var13 ^ var15 ^ par2World.getSeed());
                this.a(par2World, var11, var12, par3, par4, par5ArrayOfByte);
            }
        }
    }

    /**
     * Recursively called by generate() (generate) and optionally by itself.
     */
    protected void a(World par1World, int par2, int par3, int par4, int par5, byte[] par6ArrayOfByte) {}
}
