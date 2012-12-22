package net.minecraft.server;

class TileEntityMobSpawnerData extends WeightedRandomChoice
{
    public final NBTTagCompound b;
    public final String c;

    final TileEntityMobSpawner d;

    public TileEntityMobSpawnerData(TileEntityMobSpawner par1TileEntityMobSpawner, NBTTagCompound par2NBTTagCompound)
    {
        super(par2NBTTagCompound.getInt("Weight"));
        this.d = par1TileEntityMobSpawner;
        this.b = par2NBTTagCompound.getCompound("Properties");
        this.c = par2NBTTagCompound.getString("Type");
    }

    public TileEntityMobSpawnerData(TileEntityMobSpawner par1TileEntityMobSpawner, NBTTagCompound par2NBTTagCompound, String par3Str)
    {
        super(1);
        this.d = par1TileEntityMobSpawner;
        this.b = par2NBTTagCompound;
        this.c = par3Str;
    }

    public NBTTagCompound a()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        var1.setCompound("Properties", this.b);
        var1.setString("Type", this.c);
        var1.setInt("Weight", this.a);
        return var1;
    }
}
