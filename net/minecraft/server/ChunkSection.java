package net.minecraft.server;

public class ChunkSection
{
    /**
     * Contains the bottom-most Y block represented by this ExtendedBlockStorage. Typically a multiple of 16.
     */
    private int yPos;

    /**
     * A total count of the number of non-air blocks in this block storage's Chunk.
     */
    private int nonEmptyBlockCount;

    /**
     * Contains the number of blocks in this block storage's parent chunk that require random ticking. Used to cull the
     * Chunk from random tick updates for performance reasons.
     */
    private int tickingBlockCount;

    /**
     * Contains the least significant 8 bits of each block ID belonging to this block storage's parent Chunk.
     */
    private byte[] blockIds;

    /**
     * Contains the most significant 4 bits of each block ID belonging to this block storage's parent Chunk.
     */
    private NibbleArray extBlockIds;

    /**
     * Stores the metadata associated with blocks in this ExtendedBlockStorage.
     */
    private NibbleArray blockData;

    /** The NibbleArray containing a block of Block-light data. */
    private NibbleArray blockLight;

    /** The NibbleArray containing a block of Sky-light data. */
    private NibbleArray skyLight;

    public ChunkSection(int par1)
    {
        this.yPos = par1;
        this.blockIds = new byte[4096];
        this.blockData = new NibbleArray(this.blockIds.length, 4);
        this.skyLight = new NibbleArray(this.blockIds.length, 4);
        this.blockLight = new NibbleArray(this.blockIds.length, 4);
    }

    /**
     * Returns the extended block ID for a location in a chunk, merged from a byte array and a NibbleArray to form a
     * full 12-bit block ID.
     */
    public int a(int par1, int par2, int par3)
    {
        int var4 = this.blockIds[par2 << 8 | par3 << 4 | par1] & 255;
        return this.extBlockIds != null ? this.extBlockIds.a(par1, par2, par3) << 8 | var4 : var4;
    }

    /**
     * Sets the extended block ID for a location in a chunk, splitting bits 11..8 into a NibbleArray and bits 7..0 into
     * a byte array. Also performs reference counting to determine whether or not to broadly cull this Chunk from the
     * random-update tick list.
     */
    public void a(int par1, int par2, int par3, int par4)
    {
        int var5 = this.blockIds[par2 << 8 | par3 << 4 | par1] & 255;

        if (this.extBlockIds != null)
        {
            var5 |= this.extBlockIds.a(par1, par2, par3) << 8;
        }

        if (var5 == 0 && par4 != 0)
        {
            ++this.nonEmptyBlockCount;

            if (Block.byId[par4] != null && Block.byId[par4].isTicking())
            {
                ++this.tickingBlockCount;
            }
        }
        else if (var5 != 0 && par4 == 0)
        {
            --this.nonEmptyBlockCount;

            if (Block.byId[var5] != null && Block.byId[var5].isTicking())
            {
                --this.tickingBlockCount;
            }
        }
        else if (Block.byId[var5] != null && Block.byId[var5].isTicking() && (Block.byId[par4] == null || !Block.byId[par4].isTicking()))
        {
            --this.tickingBlockCount;
        }
        else if ((Block.byId[var5] == null || !Block.byId[var5].isTicking()) && Block.byId[par4] != null && Block.byId[par4].isTicking())
        {
            ++this.tickingBlockCount;
        }

        this.blockIds[par2 << 8 | par3 << 4 | par1] = (byte)(par4 & 255);

        if (par4 > 255)
        {
            if (this.extBlockIds == null)
            {
                this.extBlockIds = new NibbleArray(this.blockIds.length, 4);
            }

            this.extBlockIds.a(par1, par2, par3, (par4 & 3840) >> 8);
        }
        else if (this.extBlockIds != null)
        {
            this.extBlockIds.a(par1, par2, par3, 0);
        }
    }

    /**
     * Returns the metadata associated with the block at the given coordinates in this ExtendedBlockStorage.
     */
    public int b(int par1, int par2, int par3)
    {
        return this.blockData.a(par1, par2, par3);
    }

    /**
     * Sets the metadata of the Block at the given coordinates in this ExtendedBlockStorage to the given metadata.
     */
    public void b(int par1, int par2, int par3, int par4)
    {
        this.blockData.a(par1, par2, par3, par4);
    }

    /**
     * Returns whether or not this block storage's Chunk is fully empty, based on its internal reference count.
     */
    public boolean a()
    {
        return this.nonEmptyBlockCount == 0;
    }

    /**
     * Returns whether or not this block storage's Chunk will require random ticking, used to avoid looping through
     * random block ticks when there are no blocks that would randomly tick.
     */
    public boolean b()
    {
        return this.tickingBlockCount > 0;
    }

    /**
     * Returns the Y location of this ExtendedBlockStorage.
     */
    public int d()
    {
        return this.yPos;
    }

    /**
     * Sets the saved Sky-light value in the extended block storage structure.
     */
    public void c(int par1, int par2, int par3, int par4)
    {
        this.skyLight.a(par1, par2, par3, par4);
    }

    /**
     * Gets the saved Sky-light value in the extended block storage structure.
     */
    public int c(int par1, int par2, int par3)
    {
        return this.skyLight.a(par1, par2, par3);
    }

    /**
     * Sets the saved Block-light value in the extended block storage structure.
     */
    public void d(int par1, int par2, int par3, int par4)
    {
        this.blockLight.a(par1, par2, par3, par4);
    }

    /**
     * Gets the saved Block-light value in the extended block storage structure.
     */
    public int d(int par1, int par2, int par3)
    {
        return this.blockLight.a(par1, par2, par3);
    }

    public void recalcBlockCounts()
    {
        this.nonEmptyBlockCount = 0;
        this.tickingBlockCount = 0;

        for (int var1 = 0; var1 < 16; ++var1)
        {
            for (int var2 = 0; var2 < 16; ++var2)
            {
                for (int var3 = 0; var3 < 16; ++var3)
                {
                    int var4 = this.a(var1, var2, var3);

                    if (var4 > 0)
                    {
                        if (Block.byId[var4] == null)
                        {
                            this.blockIds[var2 << 8 | var3 << 4 | var1] = 0;

                            if (this.extBlockIds != null)
                            {
                                this.extBlockIds.a(var1, var2, var3, 0);
                            }
                        }
                        else
                        {
                            ++this.nonEmptyBlockCount;

                            if (Block.byId[var4].isTicking())
                            {
                                ++this.tickingBlockCount;
                            }
                        }
                    }
                }
            }
        }
    }

    public byte[] g()
    {
        return this.blockIds;
    }

    /**
     * Returns the block ID MSB (bits 11..8) array for this storage array's Chunk.
     */
    public NibbleArray i()
    {
        return this.extBlockIds;
    }

    public NibbleArray j()
    {
        return this.blockData;
    }

    /**
     * Returns the NibbleArray instance containing Block-light data.
     */
    public NibbleArray k()
    {
        return this.blockLight;
    }

    /**
     * Returns the NibbleArray instance containing Sky-light data.
     */
    public NibbleArray l()
    {
        return this.skyLight;
    }

    /**
     * Sets the array of block ID least significant bits for this ExtendedBlockStorage.
     */
    public void a(byte[] par1ArrayOfByte)
    {
        this.blockIds = par1ArrayOfByte;
    }

    /**
     * Sets the array of blockID most significant bits (blockMSBArray) for this ExtendedBlockStorage.
     */
    public void a(NibbleArray par1NibbleArray)
    {
        this.extBlockIds = par1NibbleArray;
    }

    /**
     * Sets the NibbleArray of block metadata (blockMetadataArray) for this ExtendedBlockStorage.
     */
    public void b(NibbleArray par1NibbleArray)
    {
        this.blockData = par1NibbleArray;
    }

    /**
     * Sets the NibbleArray instance used for Block-light values in this particular storage block.
     */
    public void c(NibbleArray par1NibbleArray)
    {
        this.blockLight = par1NibbleArray;
    }

    /**
     * Sets the NibbleArray instance used for Sky-light values in this particular storage block.
     */
    public void d(NibbleArray par1NibbleArray)
    {
        this.skyLight = par1NibbleArray;
    }
}
