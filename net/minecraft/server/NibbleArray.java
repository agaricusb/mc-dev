package net.minecraft.server;

public class NibbleArray
{
    /**
     * Byte array of data stored in this holder. Possibly a light map or some chunk data. Data is accessed in 4-bit
     * pieces.
     */
    public final byte[] a;

    /**
     * Log base 2 of the chunk height (128); applied as a shift on Z coordinate
     */
    private final int b;

    /**
     * Log base 2 of the chunk height (128) * width (16); applied as a shift on X coordinate
     */
    private final int c;

    public NibbleArray(int par1, int par2)
    {
        this.a = new byte[par1 >> 1];
        this.b = par2;
        this.c = par2 + 4;
    }

    public NibbleArray(byte[] par1ArrayOfByte, int par2)
    {
        this.a = par1ArrayOfByte;
        this.b = par2;
        this.c = par2 + 4;
    }

    /**
     * Returns the nibble of data corresponding to the passed in x, y, z. y is at most 6 bits, z is at most 4.
     */
    public int a(int par1, int par2, int par3)
    {
        int var4 = par2 << this.c | par3 << this.b | par1;
        int var5 = var4 >> 1;
        int var6 = var4 & 1;
        return var6 == 0 ? this.a[var5] & 15 : this.a[var5] >> 4 & 15;
    }

    /**
     * Arguments are x, y, z, val. Sets the nibble of data at x << 11 | z << 7 | y to val.
     */
    public void a(int par1, int par2, int par3, int par4)
    {
        int var5 = par2 << this.c | par3 << this.b | par1;
        int var6 = var5 >> 1;
        int var7 = var5 & 1;

        if (var7 == 0)
        {
            this.a[var6] = (byte)(this.a[var6] & 240 | par4 & 15);
        }
        else
        {
            this.a[var6] = (byte)(this.a[var6] & 15 | (par4 & 15) << 4);
        }
    }
}
