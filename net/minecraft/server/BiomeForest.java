package net.minecraft.server;

import java.util.Random;

public class BiomeForest extends BiomeBase
{
    public BiomeForest(int par1)
    {
        super(par1);
        this.K.add(new BiomeMeta(EntityWolf.class, 5, 4, 4));
        this.I.z = 10;
        this.I.B = 2;
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenerator a(Random par1Random)
    {
        return (WorldGenerator)(par1Random.nextInt(5) == 0 ? this.Q : (par1Random.nextInt(10) == 0 ? this.P : this.O));
    }
}
