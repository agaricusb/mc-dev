package net.minecraft.server;

public class BiomeBeach extends BiomeBase
{
    public BiomeBeach(int par1)
    {
        super(par1);
        this.K.clear();
        this.A = (byte) Block.SAND.id;
        this.B = (byte) Block.SAND.id;
        this.I.z = -999;
        this.I.C = 0;
        this.I.E = 0;
        this.I.F = 0;
    }
}
