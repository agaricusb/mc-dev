package net.minecraft.server;

public class OldChunkLoader
{
    public static OldChunk a(NBTTagCompound par0NBTTagCompound)
    {
        int var1 = par0NBTTagCompound.getInt("xPos");
        int var2 = par0NBTTagCompound.getInt("zPos");
        OldChunk var3 = new OldChunk(var1, var2);
        var3.g = par0NBTTagCompound.getByteArray("Blocks");
        var3.f = new OldNibbleArray(par0NBTTagCompound.getByteArray("Data"), 7);
        var3.e = new OldNibbleArray(par0NBTTagCompound.getByteArray("SkyLight"), 7);
        var3.d = new OldNibbleArray(par0NBTTagCompound.getByteArray("BlockLight"), 7);
        var3.c = par0NBTTagCompound.getByteArray("HeightMap");
        var3.b = par0NBTTagCompound.getBoolean("TerrainPopulated");
        var3.h = par0NBTTagCompound.getList("Entities");
        var3.i = par0NBTTagCompound.getList("TileEntities");
        var3.j = par0NBTTagCompound.getList("TileTicks");

        try
        {
            var3.a = par0NBTTagCompound.getLong("LastUpdate");
        }
        catch (ClassCastException var5)
        {
            var3.a = (long)par0NBTTagCompound.getInt("LastUpdate");
        }

        return var3;
    }

    public static void a(OldChunk par0AnvilConverterData, NBTTagCompound par1NBTTagCompound, WorldChunkManager par2WorldChunkManager)
    {
        par1NBTTagCompound.setInt("xPos", par0AnvilConverterData.k);
        par1NBTTagCompound.setInt("zPos", par0AnvilConverterData.l);
        par1NBTTagCompound.setLong("LastUpdate", par0AnvilConverterData.a);
        int[] var3 = new int[par0AnvilConverterData.c.length];

        for (int var4 = 0; var4 < par0AnvilConverterData.c.length; ++var4)
        {
            var3[var4] = par0AnvilConverterData.c[var4];
        }

        par1NBTTagCompound.setIntArray("HeightMap", var3);
        par1NBTTagCompound.setBoolean("TerrainPopulated", par0AnvilConverterData.b);
        NBTTagList var16 = new NBTTagList("Sections");
        int var7;

        for (int var5 = 0; var5 < 8; ++var5)
        {
            boolean var6 = true;

            for (var7 = 0; var7 < 16 && var6; ++var7)
            {
                int var8 = 0;

                while (var8 < 16 && var6)
                {
                    int var9 = 0;

                    while (true)
                    {
                        if (var9 < 16)
                        {
                            int var10 = var7 << 11 | var9 << 7 | var8 + (var5 << 4);
                            byte var11 = par0AnvilConverterData.g[var10];

                            if (var11 == 0)
                            {
                                ++var9;
                                continue;
                            }

                            var6 = false;
                        }

                        ++var8;
                        break;
                    }
                }
            }

            if (!var6)
            {
                byte[] var19 = new byte[4096];
                NibbleArray var20 = new NibbleArray(var19.length, 4);
                NibbleArray var21 = new NibbleArray(var19.length, 4);
                NibbleArray var23 = new NibbleArray(var19.length, 4);

                for (int var22 = 0; var22 < 16; ++var22)
                {
                    for (int var12 = 0; var12 < 16; ++var12)
                    {
                        for (int var13 = 0; var13 < 16; ++var13)
                        {
                            int var14 = var22 << 11 | var13 << 7 | var12 + (var5 << 4);
                            byte var15 = par0AnvilConverterData.g[var14];
                            var19[var12 << 8 | var13 << 4 | var22] = (byte)(var15 & 255);
                            var20.a(var22, var12, var13, par0AnvilConverterData.f.a(var22, var12 + (var5 << 4), var13));
                            var21.a(var22, var12, var13, par0AnvilConverterData.e.a(var22, var12 + (var5 << 4), var13));
                            var23.a(var22, var12, var13, par0AnvilConverterData.d.a(var22, var12 + (var5 << 4), var13));
                        }
                    }
                }

                NBTTagCompound var24 = new NBTTagCompound();
                var24.setByte("Y", (byte)(var5 & 255));
                var24.setByteArray("Blocks", var19);
                var24.setByteArray("Data", var20.a);
                var24.setByteArray("SkyLight", var21.a);
                var24.setByteArray("BlockLight", var23.a);
                var16.add(var24);
            }
        }

        par1NBTTagCompound.set("Sections", var16);
        byte[] var17 = new byte[256];

        for (int var18 = 0; var18 < 16; ++var18)
        {
            for (var7 = 0; var7 < 16; ++var7)
            {
                var17[var7 << 4 | var18] = (byte)(par2WorldChunkManager.getBiome(par0AnvilConverterData.k << 4 | var18, par0AnvilConverterData.l << 4 | var7).id & 255);
            }
        }

        par1NBTTagCompound.setByteArray("Biomes", var17);
        par1NBTTagCompound.set("Entities", par0AnvilConverterData.h);
        par1NBTTagCompound.set("TileEntities", par0AnvilConverterData.i);

        if (par0AnvilConverterData.j != null)
        {
            par1NBTTagCompound.set("TileTicks", par0AnvilConverterData.j);
        }
    }
}
