package net.minecraft.server;

import java.util.Comparator;

public class PlayerDistanceComparator implements Comparator
{
    private final ChunkCoordinates a;

    public PlayerDistanceComparator(ChunkCoordinates par1ChunkCoordinates)
    {
        this.a = par1ChunkCoordinates;
    }

    /**
     * Compare the position of two players.
     */
    public int a(EntityPlayer par1EntityPlayerMP, EntityPlayer par2EntityPlayerMP)
    {
        double var3 = par1EntityPlayerMP.e((double) this.a.x, (double) this.a.y, (double) this.a.z);
        double var5 = par2EntityPlayerMP.e((double) this.a.x, (double) this.a.y, (double) this.a.z);
        return var3 < var5 ? -1 : (var3 > var5 ? 1 : 0);
    }

    public int compare(Object par1Obj, Object par2Obj)
    {
        return this.a((EntityPlayer) par1Obj, (EntityPlayer) par2Obj);
    }
}
