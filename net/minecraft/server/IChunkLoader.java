package net.minecraft.server;

import java.io.IOException;

public interface IChunkLoader
{
    /**
     * Loads the specified(XZ) chunk into the specified world.
     */
    Chunk a(World var1, int var2, int var3) throws IOException;

    void a(World var1, Chunk var2) throws ExceptionWorldConflict, IOException;

    /**
     * Save extra data associated with this Chunk not normally saved during autosave, only during chunk unload.
     * Currently unused.
     */
    void b(World var1, Chunk var2);

    /**
     * Called every World.tick()
     */
    void a();

    /**
     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
     * unused.
     */
    void b();
}
