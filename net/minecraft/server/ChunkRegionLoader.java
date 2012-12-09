package net.minecraft.server;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChunkRegionLoader implements IAsyncChunkSaver, IChunkLoader
{
    private List a = new ArrayList();
    private Set b = new HashSet();
    private Object c = new Object();

    /** Save directory for chunks using the Anvil format */
    private final File d;

    public ChunkRegionLoader(File par1File)
    {
        this.d = par1File;
    }

    /**
     * Loads the specified(XZ) chunk into the specified world.
     */
    public Chunk a(World par1World, int par2, int par3) throws IOException
    {
        NBTTagCompound var4 = null;
        ChunkCoordIntPair var5 = new ChunkCoordIntPair(par2, par3);
        Object var6 = this.c;

        synchronized (this.c)
        {
            if (this.b.contains(var5))
            {
                for (int var7 = 0; var7 < this.a.size(); ++var7)
                {
                    if (((PendingChunkToSave)this.a.get(var7)).a.equals(var5))
                    {
                        var4 = ((PendingChunkToSave)this.a.get(var7)).b;
                        break;
                    }
                }
            }
        }

        if (var4 == null)
        {
            DataInputStream var10 = RegionFileCache.c(this.d, par2, par3);

            if (var10 == null)
            {
                return null;
            }

            var4 = NBTCompressedStreamTools.a((DataInput) var10); // read
        }

        return this.a(par1World, par2, par3, var4);
    }

    /**
     * Wraps readChunkFromNBT. Checks the coordinates and several NBT tags.
     */
    protected Chunk a(World par1World, int par2, int par3, NBTTagCompound par4NBTTagCompound)
    {
        if (!par4NBTTagCompound.hasKey("Level"))
        {
            System.out.println("Chunk file at " + par2 + "," + par3 + " is missing level data, skipping");
            return null;
        }
        else if (!par4NBTTagCompound.getCompound("Level").hasKey("Sections"))
        {
            System.out.println("Chunk file at " + par2 + "," + par3 + " is missing block data, skipping");
            return null;
        }
        else
        {
            Chunk var5 = this.a(par1World, par4NBTTagCompound.getCompound("Level"));

            if (!var5.a(par2, par3))
            {
                System.out.println("Chunk file at " + par2 + "," + par3 + " is in the wrong location; relocating. (Expected " + par2 + ", " + par3 + ", got " + var5.x + ", " + var5.z + ")");
                par4NBTTagCompound.setInt("xPos", par2);
                par4NBTTagCompound.setInt("zPos", par3);
                var5 = this.a(par1World, par4NBTTagCompound.getCompound("Level"));
            }

            return var5;
        }
    }

    public void a(World par1World, Chunk par2Chunk) throws ExceptionWorldConflict, IOException
    {
        par1World.D();

        try
        {
            NBTTagCompound var3 = new NBTTagCompound();
            NBTTagCompound var4 = new NBTTagCompound();
            var3.set("Level", var4);
            this.a(par2Chunk, par1World, var4);
            this.a(par2Chunk.l(), var3);
        }
        catch (Exception var5)
        {
            var5.printStackTrace();
        }
    }

    protected void a(ChunkCoordIntPair par1ChunkCoordIntPair, NBTTagCompound par2NBTTagCompound)
    {
        Object var3 = this.c;

        synchronized (this.c)
        {
            if (this.b.contains(par1ChunkCoordIntPair))
            {
                for (int var4 = 0; var4 < this.a.size(); ++var4)
                {
                    if (((PendingChunkToSave)this.a.get(var4)).a.equals(par1ChunkCoordIntPair))
                    {
                        this.a.set(var4, new PendingChunkToSave(par1ChunkCoordIntPair, par2NBTTagCompound));
                        return;
                    }
                }
            }

            this.a.add(new PendingChunkToSave(par1ChunkCoordIntPair, par2NBTTagCompound));
            this.b.add(par1ChunkCoordIntPair);
            FileIOThread.a.a(this);
        }
    }

    /**
     * Returns a boolean stating if the write was unsuccessful.
     */
    public boolean c()
    {
        PendingChunkToSave var1 = null;
        Object var2 = this.c;

        synchronized (this.c)
        {
            if (this.a.isEmpty())
            {
                return false;
            }

            var1 = (PendingChunkToSave)this.a.remove(0);
            this.b.remove(var1.a);
        }

        if (var1 != null)
        {
            try
            {
                this.a(var1);
            }
            catch (Exception var4)
            {
                var4.printStackTrace();
            }
        }

        return true;
    }

    private void a(PendingChunkToSave par1AnvilChunkLoaderPending) throws IOException
    {
        DataOutputStream var2 = RegionFileCache.d(this.d, par1AnvilChunkLoaderPending.a.x, par1AnvilChunkLoaderPending.a.z);
        NBTCompressedStreamTools.a(par1AnvilChunkLoaderPending.b, (DataOutput) var2); // write
        var2.close();
    }

    /**
     * Save extra data associated with this Chunk not normally saved during autosave, only during chunk unload.
     * Currently unused.
     */
    public void b(World par1World, Chunk par2Chunk) {}

    /**
     * Called every World.tick()
     */
    public void a() {}

    /**
     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
     * unused.
     */
    public void b() {}

    /**
     * Writes the Chunk passed as an argument to the NBTTagCompound also passed, using the World argument to retrieve
     * the Chunk's last update time.
     */
    private void a(Chunk par1Chunk, World par2World, NBTTagCompound par3NBTTagCompound)
    {
        par3NBTTagCompound.setInt("xPos", par1Chunk.x);
        par3NBTTagCompound.setInt("zPos", par1Chunk.z);
        par3NBTTagCompound.setLong("LastUpdate", par2World.getTime());
        par3NBTTagCompound.setIntArray("HeightMap", par1Chunk.heightMap);
        par3NBTTagCompound.setBoolean("TerrainPopulated", par1Chunk.done);
        ChunkSection[] var4 = par1Chunk.i();
        NBTTagList var5 = new NBTTagList("Sections");
        ChunkSection[] var6 = var4;
        int var7 = var4.length;
        NBTTagCompound var10;

        for (int var8 = 0; var8 < var7; ++var8)
        {
            ChunkSection var9 = var6[var8];

            if (var9 != null)
            {
                var10 = new NBTTagCompound();
                var10.setByte("Y", (byte)(var9.d() >> 4 & 255));
                var10.setByteArray("Blocks", var9.g());

                if (var9.i() != null)
                {
                    var10.setByteArray("Add", var9.i().a);
                }

                var10.setByteArray("Data", var9.j().a);
                var10.setByteArray("SkyLight", var9.l().a);
                var10.setByteArray("BlockLight", var9.k().a);
                var5.add(var10);
            }
        }

        par3NBTTagCompound.set("Sections", var5);
        par3NBTTagCompound.setByteArray("Biomes", par1Chunk.m());
        par1Chunk.m = false;
        NBTTagList var15 = new NBTTagList();
        Iterator var17;

        for (var7 = 0; var7 < par1Chunk.entitySlices.length; ++var7)
        {
            var17 = par1Chunk.entitySlices[var7].iterator();

            while (var17.hasNext())
            {
                Entity var19 = (Entity)var17.next();
                par1Chunk.m = true;
                var10 = new NBTTagCompound();

                if (var19.c(var10))
                {
                    var15.add(var10);
                }
            }
        }

        par3NBTTagCompound.set("Entities", var15);
        NBTTagList var16 = new NBTTagList();
        var17 = par1Chunk.tileEntities.values().iterator();

        while (var17.hasNext())
        {
            TileEntity var21 = (TileEntity)var17.next();
            var10 = new NBTTagCompound();
            var21.b(var10);
            var16.add(var10);
        }

        par3NBTTagCompound.set("TileEntities", var16);
        List var18 = par2World.a(par1Chunk, false);

        if (var18 != null)
        {
            long var20 = par2World.getTime();
            NBTTagList var11 = new NBTTagList();
            Iterator var12 = var18.iterator();

            while (var12.hasNext())
            {
                NextTickListEntry var13 = (NextTickListEntry)var12.next();
                NBTTagCompound var14 = new NBTTagCompound();
                var14.setInt("i", var13.d);
                var14.setInt("x", var13.a);
                var14.setInt("y", var13.b);
                var14.setInt("z", var13.c);
                var14.setInt("t", (int) (var13.e - var20));
                var11.add(var14);
            }

            par3NBTTagCompound.set("TileTicks", var11);
        }
    }

    /**
     * Reads the data stored in the passed NBTTagCompound and creates a Chunk with that data in the passed World.
     * Returns the created Chunk.
     */
    private Chunk a(World par1World, NBTTagCompound par2NBTTagCompound)
    {
        int var3 = par2NBTTagCompound.getInt("xPos");
        int var4 = par2NBTTagCompound.getInt("zPos");
        Chunk var5 = new Chunk(par1World, var3, var4);
        var5.heightMap = par2NBTTagCompound.getIntArray("HeightMap");
        var5.done = par2NBTTagCompound.getBoolean("TerrainPopulated");
        NBTTagList var6 = par2NBTTagCompound.getList("Sections");
        byte var7 = 16;
        ChunkSection[] var8 = new ChunkSection[var7];

        for (int var9 = 0; var9 < var6.size(); ++var9)
        {
            NBTTagCompound var10 = (NBTTagCompound)var6.get(var9);
            byte var11 = var10.getByte("Y");
            ChunkSection var12 = new ChunkSection(var11 << 4);
            var12.a(var10.getByteArray("Blocks"));

            if (var10.hasKey("Add"))
            {
                var12.a(new NibbleArray(var10.getByteArray("Add"), 4));
            }

            var12.b(new NibbleArray(var10.getByteArray("Data"), 4));
            var12.d(new NibbleArray(var10.getByteArray("SkyLight"), 4));
            var12.c(new NibbleArray(var10.getByteArray("BlockLight"), 4));
            var12.recalcBlockCounts();
            var8[var11] = var12;
        }

        var5.a(var8);

        if (par2NBTTagCompound.hasKey("Biomes"))
        {
            var5.a(par2NBTTagCompound.getByteArray("Biomes"));
        }

        NBTTagList var14 = par2NBTTagCompound.getList("Entities");

        if (var14 != null)
        {
            for (int var17 = 0; var17 < var14.size(); ++var17)
            {
                NBTTagCompound var16 = (NBTTagCompound)var14.get(var17);
                Entity var18 = EntityTypes.a(var16, par1World);
                var5.m = true;

                if (var18 != null)
                {
                    var5.a(var18);
                }
            }
        }

        NBTTagList var15 = par2NBTTagCompound.getList("TileEntities");

        if (var15 != null)
        {
            for (int var21 = 0; var21 < var15.size(); ++var21)
            {
                NBTTagCompound var20 = (NBTTagCompound)var15.get(var21);
                TileEntity var13 = TileEntity.c(var20);

                if (var13 != null)
                {
                    var5.a(var13);
                }
            }
        }

        if (par2NBTTagCompound.hasKey("TileTicks"))
        {
            NBTTagList var19 = par2NBTTagCompound.getList("TileTicks");

            if (var19 != null)
            {
                for (int var22 = 0; var22 < var19.size(); ++var22)
                {
                    NBTTagCompound var23 = (NBTTagCompound)var19.get(var22);
                    par1World.b(var23.getInt("x"), var23.getInt("y"), var23.getInt("z"), var23.getInt("i"), var23.getInt("t"));
                }
            }
        }

        return var5;
    }
}
