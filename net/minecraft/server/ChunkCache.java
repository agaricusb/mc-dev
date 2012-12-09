package net.minecraft.server;

public class ChunkCache implements IBlockAccess
{
    private int a;
    private int b;
    private Chunk[][] c;

    /** set by !chunk.getAreLevelsEmpty */
    private boolean d;

    /** Reference to the World object. */
    private World e;

    public ChunkCache(World par1World, int par2, int par3, int par4, int par5, int par6, int par7)
    {
        this.e = par1World;
        this.a = par2 >> 4;
        this.b = par4 >> 4;
        int var8 = par5 >> 4;
        int var9 = par7 >> 4;
        this.c = new Chunk[var8 - this.a + 1][var9 - this.b + 1];
        this.d = true;

        for (int var10 = this.a; var10 <= var8; ++var10)
        {
            for (int var11 = this.b; var11 <= var9; ++var11)
            {
                Chunk var12 = par1World.getChunkAt(var10, var11);

                if (var12 != null)
                {
                    this.c[var10 - this.a][var11 - this.b] = var12;

                    if (!var12.c(par3, par6))
                    {
                        this.d = false;
                    }
                }
            }
        }
    }

    /**
     * Returns the block ID at coords x,y,z
     */
    public int getTypeId(int par1, int par2, int par3)
    {
        if (par2 < 0)
        {
            return 0;
        }
        else if (par2 >= 256)
        {
            return 0;
        }
        else
        {
            int var4 = (par1 >> 4) - this.a;
            int var5 = (par3 >> 4) - this.b;

            if (var4 >= 0 && var4 < this.c.length && var5 >= 0 && var5 < this.c[var4].length)
            {
                Chunk var6 = this.c[var4][var5];
                return var6 == null ? 0 : var6.getTypeId(par1 & 15, par2, par3 & 15);
            }
            else
            {
                return 0;
            }
        }
    }

    /**
     * Returns the TileEntity associated with a given block in X,Y,Z coordinates, or null if no TileEntity exists
     */
    public TileEntity getTileEntity(int par1, int par2, int par3)
    {
        int var4 = (par1 >> 4) - this.a;
        int var5 = (par3 >> 4) - this.b;
        return this.c[var4][var5].e(par1 & 15, par2, par3 & 15);
    }

    /**
     * Returns the block metadata at coords x,y,z
     */
    public int getData(int par1, int par2, int par3)
    {
        if (par2 < 0)
        {
            return 0;
        }
        else if (par2 >= 256)
        {
            return 0;
        }
        else
        {
            int var4 = (par1 >> 4) - this.a;
            int var5 = (par3 >> 4) - this.b;
            return this.c[var4][var5].getData(par1 & 15, par2, par3 & 15);
        }
    }

    /**
     * Returns the block's material.
     */
    public Material getMaterial(int par1, int par2, int par3)
    {
        int var4 = this.getTypeId(par1, par2, par3);
        return var4 == 0 ? Material.AIR : Block.byId[var4].material;
    }

    /**
     * Returns true if the block at the specified coordinates is an opaque cube. Args: x, y, z
     */
    public boolean t(int par1, int par2, int par3)
    {
        Block var4 = Block.byId[this.getTypeId(par1, par2, par3)];
        return var4 == null ? false : var4.material.isSolid() && var4.b();
    }

    /**
     * Return the Vec3Pool object for this world.
     */
    public Vec3DPool getVec3DPool()
    {
        return this.e.getVec3DPool();
    }

    /**
     * Is this block powering in the specified direction Args: x, y, z, direction
     */
    public boolean isBlockFacePowered(int par1, int par2, int par3, int par4)
    {
        int var5 = this.getTypeId(par1, par2, par3);
        return var5 == 0 ? false : Block.byId[var5].c(this, par1, par2, par3, par4);
    }
}
