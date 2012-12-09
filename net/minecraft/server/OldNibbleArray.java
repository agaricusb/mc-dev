package net.minecraft.server;

public class OldNibbleArray
{
    public final byte[] a;
    private final int b;
    private final int c;

    public OldNibbleArray(byte[] par1ArrayOfByte, int par2)
    {
        this.a = par1ArrayOfByte;
        this.b = par2;
        this.c = par2 + 4;
    }

    public int a(int par1, int par2, int par3)
    {
        int var4 = par1 << this.c | par3 << this.b | par2;
        int var5 = var4 >> 1;
        int var6 = var4 & 1;
        return var6 == 0 ? this.a[var5] & 15 : this.a[var5] >> 4 & 15;
    }
}
