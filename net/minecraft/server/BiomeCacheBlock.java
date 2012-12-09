package net.minecraft.server;

public class BiomeCacheBlock
{
    /** An array of chunk temperatures saved by this cache. */
    public float[] a;

    /** An array of chunk rainfall values saved by this cache. */
    public float[] b;

    /** The array of biome types stored in this BiomeCacheBlock. */
    public BiomeBase[] c;

    /** The x coordinate of the BiomeCacheBlock. */
    public int d;

    /** The z coordinate of the BiomeCacheBlock. */
    public int e;

    /** The last time this BiomeCacheBlock was accessed, in milliseconds. */
    public long f;

    /** The BiomeCache object that contains this BiomeCacheBlock */
    final BiomeCache g;

    public BiomeCacheBlock(BiomeCache par1BiomeCache, int par2, int par3)
    {
        this.g = par1BiomeCache;
        this.a = new float[256];
        this.b = new float[256];
        this.c = new BiomeBase[256];
        this.d = par2;
        this.e = par3;
        BiomeCache.a(par1BiomeCache).getTemperatures(this.a, par2 << 4, par3 << 4, 16, 16);
        BiomeCache.a(par1BiomeCache).getWetness(this.b, par2 << 4, par3 << 4, 16, 16);
        BiomeCache.a(par1BiomeCache).a(this.c, par2 << 4, par3 << 4, 16, 16, false);
    }

    /**
     * Returns the BiomeGenBase related to the x, z position from the cache block.
     */
    public BiomeBase a(int par1, int par2)
    {
        return this.c[par1 & 15 | (par2 & 15) << 4];
    }
}
