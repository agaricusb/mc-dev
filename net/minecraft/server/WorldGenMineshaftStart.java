package net.minecraft.server;

import java.util.Random;

public class WorldGenMineshaftStart extends StructureStart
{
    public WorldGenMineshaftStart(World par1World, Random par2Random, int par3, int par4)
    {
        WorldGenMineshaftRoom var5 = new WorldGenMineshaftRoom(0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2);
        this.a.add(var5);
        var5.a(var5, this.a, par2Random);
        this.c();
        this.a(par1World, par2Random, 10);
    }
}
