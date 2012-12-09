package net.minecraft.server;

public abstract class WorldMapBase
{
    /** The name of the map data nbt */
    public final String id;

    /** Whether this MapDataBase needs saving to disk. */
    private boolean a;

    public WorldMapBase(String par1Str)
    {
        this.id = par1Str;
    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    public abstract void a(NBTTagCompound var1);

    /**
     * write data to NBTTagCompound from this MapDataBase, similar to Entities and TileEntities
     */
    public abstract void b(NBTTagCompound var1);

    /**
     * Marks this MapDataBase dirty, to be saved to disk when the level next saves.
     */
    public void c()
    {
        this.a(true);
    }

    /**
     * Sets the dirty state of this MapDataBase, whether it needs saving to disk.
     */
    public void a(boolean par1)
    {
        this.a = par1;
    }

    /**
     * Whether this MapDataBase needs saving to disk.
     */
    public boolean d()
    {
        return this.a;
    }
}
