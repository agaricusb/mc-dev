package net.minecraft.server;

public class ChunkCoordinatesPortal extends ChunkCoordinates
{
    public long d;

    final PortalTravelAgent e;

    public ChunkCoordinatesPortal(PortalTravelAgent par1Teleporter, int par2, int par3, int par4, long par5)
    {
        super(par2, par3, par4);
        this.e = par1Teleporter;
        this.d = par5;
    }
}
