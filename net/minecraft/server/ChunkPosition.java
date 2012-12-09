package net.minecraft.server;

public class ChunkPosition
{
    /** The x coordinate of this ChunkPosition */
    public final int x;

    /** The y coordinate of this ChunkPosition */
    public final int y;

    /** The z coordinate of this ChunkPosition */
    public final int z;

    public ChunkPosition(int par1, int par2, int par3)
    {
        this.x = par1;
        this.y = par2;
        this.z = par3;
    }

    public ChunkPosition(Vec3D par1Vec3)
    {
        this(MathHelper.floor(par1Vec3.c), MathHelper.floor(par1Vec3.d), MathHelper.floor(par1Vec3.e));
    }

    public boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof ChunkPosition))
        {
            return false;
        }
        else
        {
            ChunkPosition var2 = (ChunkPosition)par1Obj;
            return var2.x == this.x && var2.y == this.y && var2.z == this.z;
        }
    }

    public int hashCode()
    {
        return this.x * 8976890 + this.y * 981131 + this.z;
    }
}
