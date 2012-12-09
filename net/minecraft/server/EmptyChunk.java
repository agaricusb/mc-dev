package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class EmptyChunk extends Chunk
{
    public EmptyChunk(World par1World, int par2, int par3)
    {
        super(par1World, par2, par3);
    }

    /**
     * Checks whether the chunk is at the X/Z location specified
     */
    public boolean a(int par1, int par2)
    {
        return par1 == this.x && par2 == this.z;
    }

    /**
     * Returns the value in the height map at this x, z coordinate in the chunk
     */
    public int b(int par1, int par2)
    {
        return 0;
    }

    /**
     * Generates the initial skylight map for the chunk upon generation or load.
     */
    public void initLighting() {}

    /**
     * Return the ID of a block in the chunk.
     */
    public int getTypeId(int par1, int par2, int par3)
    {
        return 0;
    }

    public int b(int par1, int par2, int par3)
    {
        return 255;
    }

    /**
     * Sets a blockID of a position within a chunk with metadata. Args: x, y, z, blockID, metadata
     */
    public boolean a(int par1, int par2, int par3, int par4, int par5)
    {
        return true;
    }

    /**
     * Sets a blockID for a position in the chunk. Args: x, y, z, blockID
     */
    public boolean a(int par1, int par2, int par3, int par4)
    {
        return true;
    }

    /**
     * Return the metadata corresponding to the given coordinates inside a chunk.
     */
    public int getData(int par1, int par2, int par3)
    {
        return 0;
    }

    /**
     * Set the metadata of a block in the chunk
     */
    public boolean b(int par1, int par2, int par3, int par4)
    {
        return false;
    }

    /**
     * Gets the amount of light saved in this block (doesn't adjust for daylight)
     */
    public int getBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
    {
        return 0;
    }

    /**
     * Sets the light value at the coordinate. If enumskyblock is set to sky it sets it in the skylightmap and if its a
     * block then into the blocklightmap. Args enumSkyBlock, x, y, z, lightValue
     */
    public void a(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4, int par5) {}

    /**
     * Gets the amount of light on a block taking into account sunlight
     */
    public int c(int par1, int par2, int par3, int par4)
    {
        return 0;
    }

    /**
     * Adds an entity to the chunk. Args: entity
     */
    public void a(Entity par1Entity) {}

    /**
     * removes entity using its y chunk coordinate as its index
     */
    public void b(Entity par1Entity) {}

    /**
     * Removes entity at the specified index from the entity array.
     */
    public void a(Entity par1Entity, int par2) {}

    /**
     * Returns whether is not a block above this one blocking sight to the sky (done via checking against the heightmap)
     */
    public boolean d(int par1, int par2, int par3)
    {
        return false;
    }

    /**
     * Gets the TileEntity for a given block in this chunk
     */
    public TileEntity e(int par1, int par2, int par3)
    {
        return null;
    }

    /**
     * Adds a TileEntity to a chunk
     */
    public void a(TileEntity par1TileEntity) {}

    /**
     * Sets the TileEntity for a given block in this chunk
     */
    public void a(int par1, int par2, int par3, TileEntity par4TileEntity) {}

    /**
     * Removes the TileEntity for a given block in this chunk
     */
    public void f(int par1, int par2, int par3) {}

    /**
     * Called when this Chunk is loaded by the ChunkProvider
     */
    public void addEntities() {}

    /**
     * Called when this Chunk is unloaded by the ChunkProvider
     */
    public void removeEntities() {}

    /**
     * Sets the isModified flag for this Chunk
     */
    public void e() {}

    /**
     * Fills the given list of all entities that intersect within the given bounding box that aren't the passed entity
     * Args: entity, aabb, listToFill
     */
    public void a(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB, List par3List) {}

    /**
     * Gets all entities that can be assigned to the specified class. Args: entityClass, aabb, listToFill
     */
    public void a(Class par1Class, AxisAlignedBB par2AxisAlignedBB, List par3List, IEntitySelector par4IEntitySelector) {}

    /**
     * Returns true if this Chunk needs to be saved
     */
    public boolean a(boolean par1)
    {
        return false;
    }

    public Random a(long par1)
    {
        return new Random(this.world.getSeed() + (long)(this.x * this.x * 4987142) + (long)(this.x * 5947611) + (long)(this.z * this.z) * 4392871L + (long)(this.z * 389711) ^ par1);
    }

    public boolean isEmpty()
    {
        return true;
    }

    /**
     * Returns whether the ExtendedBlockStorages containing levels (in blocks) from arg 1 to arg 2 are fully empty
     * (true) or not (false).
     */
    public boolean c(int par1, int par2)
    {
        return true;
    }
}
