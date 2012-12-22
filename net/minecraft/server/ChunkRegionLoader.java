package net.minecraft.server;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChunkRegionLoader implements IChunkLoader, IAsyncChunkSaver
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

            var4 = NBTCompressedStreamTools.a((DataInput) var10);
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
        NBTCompressedStreamTools.a(par1AnvilChunkLoaderPending.b, (DataOutput) var2);
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
        boolean var6 = !par2World.worldProvider.f;
        ChunkSection[] var7 = var4;
        int var8 = var4.length;
        NBTTagCompound var11;

        for (int var9 = 0; var9 < var8; ++var9)
        {
            ChunkSection var10 = var7[var9];

            if (var10 != null)
            {
                var11 = new NBTTagCompound();
                var11.setByte("Y", (byte) (var10.d() >> 4 & 255));
                var11.setByteArray("Blocks", var10.g());

                if (var10.i() != null)
                {
                    var11.setByteArray("Add", var10.i().a);
                }

                var11.setByteArray("Data", var10.j().a);
                var11.setByteArray("BlockLight", var10.k().a);

                if (var6)
                {
                    var11.setByteArray("SkyLight", var10.l().a);
                }
                else
                {
                    var11.setByteArray("SkyLight", new byte[var10.k().a.length]);
                }

                var5.add(var11);
            }
        }

        par3NBTTagCompound.set("Sections", var5);
        par3NBTTagCompound.setByteArray("Biomes", par1Chunk.m());
        par1Chunk.m = false;
        NBTTagList var16 = new NBTTagList();
        Iterator var18;

        for (var8 = 0; var8 < par1Chunk.entitySlices.length; ++var8)
        {
            var18 = par1Chunk.entitySlices[var8].iterator();

            while (var18.hasNext())
            {
                Entity var21 = (Entity)var18.next();
                par1Chunk.m = true;
                var11 = new NBTTagCompound();

                if (var21.c(var11))
                {
                    var16.add(var11);
                }
            }
        }

        par3NBTTagCompound.set("Entities", var16);
        NBTTagList var17 = new NBTTagList();
        var18 = par1Chunk.tileEntities.values().iterator();

        while (var18.hasNext())
        {
            TileEntity var22 = (TileEntity)var18.next();
            var11 = new NBTTagCompound();
            var22.b(var11);
            var17.add(var11);
        }

        par3NBTTagCompound.set("TileEntities", var17);
        List var20 = par2World.a(par1Chunk, false);

        if (var20 != null)
        {
            long var19 = par2World.getTime();
            NBTTagList var12 = new NBTTagList();
            Iterator var13 = var20.iterator();

            while (var13.hasNext())
            {
                NextTickListEntry var14 = (NextTickListEntry)var13.next();
                NBTTagCompound var15 = new NBTTagCompound();
                var15.setInt("i", var14.d);
                var15.setInt("x", var14.a);
                var15.setInt("y", var14.b);
                var15.setInt("z", var14.c);
                var15.setInt("t", (int) (var14.e - var19));
                var12.add(var15);
            }

            par3NBTTagCompound.set("TileTicks", var12);
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
        boolean var9 = !par1World.worldProvider.f;

        for (int var10 = 0; var10 < var6.size(); ++var10)
        {
            NBTTagCompound var11 = (NBTTagCompound)var6.get(var10);
            byte var12 = var11.getByte("Y");
            ChunkSection var13 = new ChunkSection(var12 << 4, var9);
            var13.a(var11.getByteArray("Blocks"));

            if (var11.hasKey("Add"))
            {
                var13.a(new NibbleArray(var11.getByteArray("Add"), 4));
            }

            var13.b(new NibbleArray(var11.getByteArray("Data"), 4));
            var13.c(new NibbleArray(var11.getByteArray("BlockLight"), 4));

            if (var9)
            {
                var13.d(new NibbleArray(var11.getByteArray("SkyLight"), 4));
            }

            var13.recalcBlockCounts();
            var8[var12] = var13;
        }

        var5.a(var8);

        if (par2NBTTagCompound.hasKey("Biomes"))
        {
            var5.a(par2NBTTagCompound.getByteArray("Biomes"));
        }

        NBTTagList var16 = par2NBTTagCompound.getList("Entities");

        if (var16 != null)
        {
            for (int var15 = 0; var15 < var16.size(); ++var15)
            {
                NBTTagCompound var17 = (NBTTagCompound)var16.get(var15);
                Entity var22 = EntityTypes.a(var17, par1World);
                var5.m = true;

                if (var22 != null)
                {
                    var5.a(var22);
                }
            }
        }

        NBTTagList var19 = par2NBTTagCompound.getList("TileEntities");

        if (var19 != null)
        {
            for (int var18 = 0; var18 < var19.size(); ++var18)
            {
                NBTTagCompound var20 = (NBTTagCompound)var19.get(var18);
                TileEntity var14 = TileEntity.c(var20);

                if (var14 != null)
                {
                    var5.a(var14);
                }
            }
        }

        if (par2NBTTagCompound.hasKey("TileTicks"))
        {
            NBTTagList var23 = par2NBTTagCompound.getList("TileTicks");

            if (var23 != null)
            {
                for (int var21 = 0; var21 < var23.size(); ++var21)
                {
                    NBTTagCompound var24 = (NBTTagCompound)var23.get(var21);
                    par1World.b(var24.getInt("x"), var24.getInt("y"), var24.getInt("z"), var24.getInt("i"), var24.getInt("t"));
                }
            }
        }

        return var5;
    }
}
