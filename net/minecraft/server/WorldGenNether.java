package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

public class WorldGenNether extends StructureGenerator
{
    private List e = new ArrayList();

    public WorldGenNether()
    {
        this.e.add(new BiomeMeta(EntityBlaze.class, 10, 2, 3));
        this.e.add(new BiomeMeta(EntityPigZombie.class, 5, 4, 4));
        this.e.add(new BiomeMeta(EntitySkeleton.class, 10, 4, 4));
        this.e.add(new BiomeMeta(EntityMagmaCube.class, 3, 4, 4));
    }

    public List a()
    {
        return this.e;
    }

    protected boolean a(int par1, int par2)
    {
        int var3 = par1 >> 4;
        int var4 = par2 >> 4;
        this.b.setSeed((long)(var3 ^ var4 << 4) ^ this.c.getSeed());
        this.b.nextInt();
        return this.b.nextInt(3) != 0 ? false : (par1 != (var3 << 4) + 4 + this.b.nextInt(8) ? false : par2 == (var4 << 4) + 4 + this.b.nextInt(8));
    }

    protected StructureStart b(int par1, int par2)
    {
        return new WorldGenNetherStart(this.c, this.b, par1, par2);
    }
}
