package net.minecraft.server;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class WorldLoaderServer extends WorldLoader
{
    public WorldLoaderServer(File par1File)
    {
        super(par1File);
    }

    protected int c()
    {
        return 19133;
    }

    public void d()
    {
        RegionFileCache.a();
    }

    /**
     * Returns back a loader for the specified save directory
     */
    public IDataManager a(String par1Str, boolean par2)
    {
        return new ServerNBTManager(this.a, par1Str, par2);
    }

    /**
     * gets if the map is old chunk saving (true) or McRegion (false)
     */
    public boolean isConvertable(String par1Str)
    {
        WorldData var2 = this.c(par1Str);
        return var2 != null && var2.l() != this.c();
    }

    /**
     * converts the map to mcRegion
     */
    public boolean convert(String par1Str, IProgressUpdate par2IProgressUpdate)
    {
        par2IProgressUpdate.a(0);
        ArrayList var3 = new ArrayList();
        ArrayList var4 = new ArrayList();
        ArrayList var5 = new ArrayList();
        File var6 = new File(this.a, par1Str);
        File var7 = new File(var6, "DIM-1");
        File var8 = new File(var6, "DIM1");
        System.out.println("Scanning folders...");
        this.a(var6, var3);

        if (var7.exists())
        {
            this.a(var7, var4);
        }

        if (var8.exists())
        {
            this.a(var8, var5);
        }

        int var9 = var3.size() + var4.size() + var5.size();
        System.out.println("Total conversion count is " + var9);
        WorldData var10 = this.c(par1Str);
        Object var11 = null;

        if (var10.getType() == WorldType.FLAT)
        {
            var11 = new WorldChunkManagerHell(BiomeBase.PLAINS, 0.5F, 0.5F);
        }
        else
        {
            var11 = new WorldChunkManager(var10.getSeed(), var10.getType());
        }

        this.a(new File(var6, "region"), var3, (WorldChunkManager) var11, 0, var9, par2IProgressUpdate);
        this.a(new File(var7, "region"), var4, new WorldChunkManagerHell(BiomeBase.HELL, 1.0F, 0.0F), var3.size(), var9, par2IProgressUpdate);
        this.a(new File(var8, "region"), var5, new WorldChunkManagerHell(BiomeBase.SKY, 0.5F, 0.0F), var3.size() + var4.size(), var9, par2IProgressUpdate);
        var10.e(19133);

        if (var10.getType() == WorldType.NORMAL_1_1)
        {
            var10.setType(WorldType.NORMAL);
        }

        this.g(par1Str);
        IDataManager var12 = this.a(par1Str, false);
        var12.saveWorldData(var10);
        return true;
    }

    /**
     * par: filename for the level.dat_mcr backup
     */
    private void g(String par1Str)
    {
        File var2 = new File(this.a, par1Str);

        if (!var2.exists())
        {
            System.out.println("Warning: Unable to create level.dat_mcr backup");
        }
        else
        {
            File var3 = new File(var2, "level.dat");

            if (!var3.exists())
            {
                System.out.println("Warning: Unable to create level.dat_mcr backup");
            }
            else
            {
                File var4 = new File(var2, "level.dat_mcr");

                if (!var3.renameTo(var4))
                {
                    System.out.println("Warning: Unable to create level.dat_mcr backup");
                }
            }
        }
    }

    private void a(File par1File, Iterable par2Iterable, WorldChunkManager par3WorldChunkManager, int par4, int par5, IProgressUpdate par6IProgressUpdate)
    {
        Iterator var7 = par2Iterable.iterator();

        while (var7.hasNext())
        {
            File var8 = (File)var7.next();
            this.a(par1File, var8, par3WorldChunkManager, par4, par5, par6IProgressUpdate);
            ++par4;
            int var9 = (int)Math.round(100.0D * (double)par4 / (double)par5);
            par6IProgressUpdate.a(var9);
        }
    }

    /**
     * copies a 32x32 chunk set from par2File to par1File, via AnvilConverterData
     */
    private void a(File par1File, File par2File, WorldChunkManager par3WorldChunkManager, int par4, int par5, IProgressUpdate par6IProgressUpdate)
    {
        try
        {
            String var7 = par2File.getName();
            RegionFile var8 = new RegionFile(par2File);
            RegionFile var9 = new RegionFile(new File(par1File, var7.substring(0, var7.length() - ".mcr".length()) + ".mca"));

            for (int var10 = 0; var10 < 32; ++var10)
            {
                int var11;

                for (var11 = 0; var11 < 32; ++var11)
                {
                    if (var8.c(var10, var11) && !var9.c(var10, var11))
                    {
                        DataInputStream var12 = var8.a(var10, var11);

                        if (var12 == null)
                        {
                            System.out.println("Failed to fetch input stream");
                        }
                        else
                        {
                            NBTTagCompound var13 = NBTCompressedStreamTools.a((DataInput) var12); // read
                            var12.close();
                            NBTTagCompound var14 = var13.getCompound("Level");
                            OldChunk var15 = OldChunkLoader.a(var14);
                            NBTTagCompound var16 = new NBTTagCompound();
                            NBTTagCompound var17 = new NBTTagCompound();
                            var16.set("Level", var17);
                            OldChunkLoader.a(var15, var17, par3WorldChunkManager);
                            DataOutputStream var18 = var9.b(var10, var11);
                            NBTCompressedStreamTools.a(var16, (DataOutput) var18); // write
                            var18.close();
                        }
                    }
                }

                var11 = (int)Math.round(100.0D * (double)(par4 * 1024) / (double)(par5 * 1024));
                int var20 = (int)Math.round(100.0D * (double)((var10 + 1) * 32 + par4 * 1024) / (double)(par5 * 1024));

                if (var20 > var11)
                {
                    par6IProgressUpdate.a(var20);
                }
            }

            var8.c();
            var9.c();
        }
        catch (IOException var19)
        {
            var19.printStackTrace();
        }
    }

    /**
     * filters the files in the par1 directory, and adds them to the par2 collections
     */
    private void a(File par1File, Collection par2Collection)
    {
        File var3 = new File(par1File, "region");
        File[] var4 = var3.listFiles(new ChunkFilenameFilter(this));

        if (var4 != null)
        {
            Collections.addAll(par2Collection, var4);
        }
    }
}
