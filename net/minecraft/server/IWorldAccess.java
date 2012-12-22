package net.minecraft.server;

public interface IWorldAccess
{
    /**
     * On the client, re-renders the block. On the server, sends the block to the client (which will re-render it),
     * including the tile entity description packet if applicable. Args: x, y, z
     */
    void a(int var1, int var2, int var3);

    /**
     * On the client, re-renders this block. On the server, does nothing. Used for lighting updates.
     */
    void b(int var1, int var2, int var3);

    /**
     * On the client, re-renders all blocks in this range, inclusive. On the server, does nothing. Args: min x, min y,
     * min z, max x, max y, max z
     */
    void a(int var1, int var2, int var3, int var4, int var5, int var6);

    /**
     * Plays the specified sound. Arg: soundName, x, y, z, volume, pitch
     */
    void a(String var1, double var2, double var4, double var6, float var8, float var9);

    /**
     * Plays sound to all near players except the player reference given
     */
    void a(EntityHuman var1, String var2, double var3, double var5, double var7, float var9, float var10);

    /**
     * Spawns a particle. Arg: particleType, x, y, z, velX, velY, velZ
     */
    void a(String var1, double var2, double var4, double var6, double var8, double var10, double var12);

    /**
     * Start the skin for this entity downloading, if necessary, and increment its reference counter
     */
    void a(Entity var1);

    /**
     * Decrement the reference counter for this entity's skin image data
     */
    void b(Entity var1);

    /**
     * Plays the specified record. Arg: recordName, x, y, z
     */
    void a(String var1, int var2, int var3, int var4);

    void a(int var1, int var2, int var3, int var4, int var5);

    /**
     * Plays a pre-canned sound effect along with potentially auxiliary data-driven one-shot behaviour (particles, etc).
     */
    void a(EntityHuman var1, int var2, int var3, int var4, int var5, int var6);

    /**
     * Starts (or continues) destroying a block with given ID at the given coordinates for the given partially destroyed
     * value
     */
    void b(int var1, int var2, int var3, int var4, int var5);
}
