package net.minecraft.server;

import java.io.File;

public class ServerNBTManager extends WorldNBTStorage
{
    public ServerNBTManager(File par1File, String par2Str, boolean par3)
    {
        super(par1File, par2Str, par3);
    }

    /**
     * initializes and returns the chunk loader for the specified world provider
     */
    public IChunkLoader createChunkLoader(WorldProvider par1WorldProvider)
    {
        File var2 = this.getDirectory();
        File var3;

        if (par1WorldProvider instanceof WorldProviderHell)
        {
            var3 = new File(var2, "DIM-1");
            var3.mkdirs();
            return new ChunkRegionLoader(var3);
        }
        else if (par1WorldProvider instanceof WorldProviderTheEnd)
        {
            var3 = new File(var2, "DIM1");
            var3.mkdirs();
            return new ChunkRegionLoader(var3);
        }
        else
        {
            return new ChunkRegionLoader(var2);
        }
    }

    /**
     * Saves the given World Info with the given NBTTagCompound as the Player.
     */
    public void saveWorldData(WorldData par1WorldInfo, NBTTagCompound par2NBTTagCompound)
    {
        par1WorldInfo.e(19133);
        super.saveWorldData(par1WorldInfo, par2NBTTagCompound);
    }

    /**
     * Called to flush all changes to disk, waiting for them to complete.
     */
    public void a()
    {
        try
        {
            FileIOThread.a.a();
        }
        catch (InterruptedException var2)
        {
            var2.printStackTrace();
        }

        RegionFileCache.a();
    }
}
