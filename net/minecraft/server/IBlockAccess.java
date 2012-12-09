package net.minecraft.server;

public interface IBlockAccess
{
    /**
     * Returns the block ID at coords x,y,z
     */
    int getTypeId(int var1, int var2, int var3);

    /**
     * Returns the TileEntity associated with a given block in X,Y,Z coordinates, or null if no TileEntity exists
     */
    TileEntity getTileEntity(int var1, int var2, int var3);

    /**
     * Returns the block metadata at coords x,y,z
     */
    int getData(int var1, int var2, int var3);

    /**
     * Returns the block's material.
     */
    Material getMaterial(int var1, int var2, int var3);

    /**
     * Returns true if the block at the specified coordinates is an opaque cube. Args: x, y, z
     */
    boolean t(int var1, int var2, int var3);

    /**
     * Return the Vec3Pool object for this world.
     */
    Vec3DPool getVec3DPool();

    /**
     * Is this block powering in the specified direction Args: x, y, z, direction
     */
    boolean isBlockFacePowered(int var1, int var2, int var3, int var4);
}
