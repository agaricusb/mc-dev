package net.minecraft.server;

public class ChunkCoordIntPair
{
    /** The X position of this Chunk Coordinate Pair */
    public final int x;

    /** The Z position of this Chunk Coordinate Pair */
    public final int z;

    public ChunkCoordIntPair(int par1, int par2)
    {
        this.x = par1;
        this.z = par2;
    }

    /**
     * converts a chunk coordinate pair to an integer (suitable for hashing)
     */
    public static long a(int par0, int par1)
    {
        return (long)par0 & 4294967295L | ((long)par1 & 4294967295L) << 32;
    }

    public int hashCode()
    {
        long var1 = a(this.x, this.z);
        int var3 = (int)var1;
        int var4 = (int)(var1 >> 32);
        return var3 ^ var4;
    }

    public boolean equals(Object par1Obj)
    {
        ChunkCoordIntPair var2 = (ChunkCoordIntPair)par1Obj;
        return var2.x == this.x && var2.z == this.z;
    }

    public int a()
    {
        return (this.x << 4) + 8;
    }

    public int b()
    {
        return (this.z << 4) + 8;
    }

    public ChunkPosition a(int par1)
    {
        return new ChunkPosition(this.a(), par1, this.b());
    }

    public String toString()
    {
        return "[" + this.x + ", " + this.z + "]";
    }
}
