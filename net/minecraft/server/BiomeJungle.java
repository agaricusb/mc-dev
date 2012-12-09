package net.minecraft.server;

import java.util.Random;

public class BiomeJungle extends BiomeBase
{
    public BiomeJungle(int par1)
    {
        super(par1);
        this.I.z = 50;
        this.I.B = 25;
        this.I.A = 4;
        this.J.add(new BiomeMeta(EntityOcelot.class, 2, 1, 1));
        this.K.add(new BiomeMeta(EntityChicken.class, 10, 4, 4));
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenerator a(Random par1Random)
    {
        return (WorldGenerator)(par1Random.nextInt(10) == 0 ? this.P : (par1Random.nextInt(2) == 0 ? new WorldGenGroundBush(3, 0) : (par1Random.nextInt(3) == 0 ? new WorldGenMegaTree(false, 10 + par1Random.nextInt(20), 3, 3) : new WorldGenTrees(false, 4 + par1Random.nextInt(7), 3, 3, true))));
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenerator b(Random par1Random)
    {
        return par1Random.nextInt(4) == 0 ? new WorldGenGrass(Block.LONG_GRASS.id, 2) : new WorldGenGrass(Block.LONG_GRASS.id, 1);
    }

    public void a(World par1World, Random par2Random, int par3, int par4)
    {
        super.a(par1World, par2Random, par3, par4);
        WorldGenVines var5 = new WorldGenVines();

        for (int var6 = 0; var6 < 50; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(16) + 8;
            byte var8 = 64;
            int var9 = par4 + par2Random.nextInt(16) + 8;
            var5.a(par1World, par2Random, var7, var8, var9);
        }
    }
}
