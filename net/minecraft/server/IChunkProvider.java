package net.minecraft.server;

import java.util.List;

public interface IChunkProvider
{
    /**
     * Checks to see if a chunk exists at x, y
     */
    boolean isChunkLoaded(int var1, int var2);

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    Chunk getOrCreateChunk(int var1, int var2);

    /**
     * loads or generates the chunk at the chunk location specified
     */
    Chunk getChunkAt(int var1, int var2);

    /**
     * Populates chunk with ores etc etc
     */
    void getChunkAt(IChunkProvider var1, int var2, int var3);

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    boolean saveChunks(boolean var1, IProgressUpdate var2);

    /**
     * Unloads the 100 oldest chunks from memory, due to a bug with chunkSet.add() never being called it thinks the list
     * is always empty and will not remove any chunks.
     */
    boolean unloadChunks();

    /**
     * Returns if the IChunkProvider supports saving.
     */
    boolean canSave();

    /**
     * Converts the instance data to a readable string.
     */
    String getName();

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    List getMobsFor(EnumCreatureType var1, int var2, int var3, int var4);

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    ChunkPosition findNearestMapFeature(World var1, String var2, int var3, int var4, int var5);

    int getLoadedChunks();

    void recreateStructures(int var1, int var2);
}
