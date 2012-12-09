package net.minecraft.server;

public class WorldMapDecoration
{
    public byte type;
    public byte locX;
    public byte locY;
    public byte rotation;

    final WorldMap e;

    public WorldMapDecoration(WorldMap par1MapData, byte par2, byte par3, byte par4, byte par5)
    {
        this.e = par1MapData;
        this.type = par2;
        this.locX = par3;
        this.locY = par4;
        this.rotation = par5;
    }
}
