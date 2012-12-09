package net.minecraft.server;

import java.util.Random;

public class BiomeTaiga extends BiomeBase
{
    public BiomeTaiga(int par1)
    {
        super(par1);
        this.K.add(new BiomeMeta(EntityWolf.class, 8, 4, 4));
        this.I.z = 10;
        this.I.B = 1;
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenerator a(Random par1Random)
    {
        return (WorldGenerator)(par1Random.nextInt(3) == 0 ? new WorldGenTaiga1() : new WorldGenTaiga2(false));
    }
}
