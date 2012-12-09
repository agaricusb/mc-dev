package net.minecraft.server;

import java.io.File;

public interface IDataManager
{
    /**
     * Loads and returns the world info
     */
    WorldData getWorldData();

    /**
     * Checks the session lock to prevent save collisions
     */
    void checkSession() throws ExceptionWorldConflict;

    /**
     * initializes and returns the chunk loader for the specified world provider
     */
    IChunkLoader createChunkLoader(WorldProvider var1);

    /**
     * Saves the given World Info with the given NBTTagCompound as the Player.
     */
    void saveWorldData(WorldData var1, NBTTagCompound var2);

    /**
     * used to update level.dat from old format to MCRegion format
     */
    void saveWorldData(WorldData var1);

    PlayerFileData getPlayerFileData();

    /**
     * Called to flush all changes to disk, waiting for them to complete.
     */
    void a();

    /**
     * Gets the file location of the given map
     */
    File getDataFile(String var1);

    /**
     * Returns the name of the directory where world information is saved.
     */
    String g();
}
