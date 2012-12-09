package net.minecraft.server;

import java.util.Random;

public class BiomeSwamp extends BiomeBase
{
    protected BiomeSwamp(int par1)
    {
        super(par1);
        this.I.z = 2;
        this.I.A = -999;
        this.I.C = 1;
        this.I.D = 8;
        this.I.E = 10;
        this.I.I = 1;
        this.I.y = 4;
        this.H = 14745518;
        this.J.add(new BiomeMeta(EntitySlime.class, 1, 1, 1));
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenerator a(Random par1Random)
    {
        return this.R;
    }
}
