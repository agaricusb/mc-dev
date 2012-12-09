package net.minecraft.server;

public class ChunkCoordinates implements Comparable
{
    public int x;

    /** the y coordinate */
    public int y;

    /** the z coordinate */
    public int z;

    public ChunkCoordinates() {}

    public ChunkCoordinates(int par1, int par2, int par3)
    {
        this.x = par1;
        this.y = par2;
        this.z = par3;
    }

    public ChunkCoordinates(ChunkCoordinates par1ChunkCoordinates)
    {
        this.x = par1ChunkCoordinates.x;
        this.y = par1ChunkCoordinates.y;
        this.z = par1ChunkCoordinates.z;
    }

    public boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof ChunkCoordinates))
        {
            return false;
        }
        else
        {
            ChunkCoordinates var2 = (ChunkCoordinates)par1Obj;
            return this.x == var2.x && this.y == var2.y && this.z == var2.z;
        }
    }

    public int hashCode()
    {
        return this.x + this.z << 8 + this.y << 16;
    }

    /**
     * Compare the coordinate with another coordinate
     */
    public int compareTo(ChunkCoordinates par1ChunkCoordinates)
    {
        return this.y == par1ChunkCoordinates.y ? (this.z == par1ChunkCoordinates.z ? this.x - par1ChunkCoordinates.x : this.z - par1ChunkCoordinates.z) : this.y - par1ChunkCoordinates.y;
    }

    public void b(int par1, int par2, int par3)
    {
        this.x = par1;
        this.y = par2;
        this.z = par3;
    }

    /**
     * Returns the squared distance between this coordinates and the coordinates given as argument.
     */
    public float e(int par1, int par2, int par3)
    {
        int var4 = this.x - par1;
        int var5 = this.y - par2;
        int var6 = this.z - par3;
        return (float)(var4 * var4 + var5 * var5 + var6 * var6);
    }

    /**
     * Return the squared distance between this coordinates and the ChunkCoordinates given as argument.
     */
    public float e(ChunkCoordinates par1ChunkCoordinates)
    {
        return this.e(par1ChunkCoordinates.x, par1ChunkCoordinates.y, par1ChunkCoordinates.z);
    }

    public int compareTo(Object par1Obj)
    {
        return this.compareTo((ChunkCoordinates) par1Obj);
    }
}
