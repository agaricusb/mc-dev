package net.minecraft.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class StructureGenerator extends WorldGenBase
{
    /**
     * Used to store a list of all structures that have been recursively generated. Used so that during recursive
     * generation, the structure generator can avoid generating structures that intersect ones that have already been
     * placed.
     */
    protected Map d = new HashMap();

    /**
     * Recursively called by generate() (generate) and optionally by itself.
     */
    protected void a(World par1World, int par2, int par3, int par4, int par5, byte[] par6ArrayOfByte)
    {
        if (!this.d.containsKey(Long.valueOf(ChunkCoordIntPair.a(par2, par3))))
        {
            this.b.nextInt();

            try
            {
                if (this.a(par2, par3))
                {
                    StructureStart var7 = this.b(par2, par3);
                    this.d.put(Long.valueOf(ChunkCoordIntPair.a(par2, par3)), var7);
                }
            }
            catch (Throwable var10)
            {
                CrashReport var8 = CrashReport.a(var10, "Exception preparing structure feature");
                CrashReportSystemDetails var9 = var8.a("Feature being prepared");
                var9.a("Is feature chunk", new CrashReportIsFeatureChunk(this, par2, par3));
                var9.a("Chunk location", String.format("%d,%d", new Object[]{Integer.valueOf(par2), Integer.valueOf(par3)}));
                var9.a("Chunk pos hash", new CrashReportChunkPosHash(this, par2, par3));
                var9.a("Structure type", new CrashReportStructureType(this));
                throw new ReportedException(var8);
            }
        }
    }

    /**
     * Generates structures in specified chunk next to existing structures. Does *not* generate StructureStarts.
     */
    public boolean a(World par1World, Random par2Random, int par3, int par4)
    {
        int var5 = (par3 << 4) + 8;
        int var6 = (par4 << 4) + 8;
        boolean var7 = false;
        Iterator var8 = this.d.values().iterator();

        while (var8.hasNext())
        {
            StructureStart var9 = (StructureStart)var8.next();

            if (var9.d() && var9.a().a(var5, var6, var5 + 15, var6 + 15))
            {
                var9.a(par1World, par2Random, new StructureBoundingBox(var5, var6, var5 + 15, var6 + 15));
                var7 = true;
            }
        }

        return var7;
    }

    /**
     * Returns true if the structure generator has generated a structure located at the given position tuple.
     */
    public boolean a(int par1, int par2, int par3)
    {
        Iterator var4 = this.d.values().iterator();

        while (var4.hasNext())
        {
            StructureStart var5 = (StructureStart)var4.next();

            if (var5.d() && var5.a().a(par1, par3, par1, par3))
            {
                Iterator var6 = var5.b().iterator();

                while (var6.hasNext())
                {
                    StructurePiece var7 = (StructurePiece)var6.next();

                    if (var7.b().b(par1, par2, par3))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public ChunkPosition getNearestGeneratedFeature(World par1World, int par2, int par3, int par4)
    {
        this.c = par1World;
        this.b.setSeed(par1World.getSeed());
        long var5 = this.b.nextLong();
        long var7 = this.b.nextLong();
        long var9 = (long)(par2 >> 4) * var5;
        long var11 = (long)(par4 >> 4) * var7;
        this.b.setSeed(var9 ^ var11 ^ par1World.getSeed());
        this.a(par1World, par2 >> 4, par4 >> 4, 0, 0, (byte[]) null);
        double var13 = Double.MAX_VALUE;
        ChunkPosition var15 = null;
        Iterator var16 = this.d.values().iterator();
        ChunkPosition var19;
        int var21;
        int var20;
        double var23;
        int var22;

        while (var16.hasNext())
        {
            StructureStart var17 = (StructureStart)var16.next();

            if (var17.d())
            {
                StructurePiece var18 = (StructurePiece)var17.b().get(0);
                var19 = var18.a();
                var20 = var19.x - par2;
                var21 = var19.y - par3;
                var22 = var19.z - par4;
                var23 = (double)(var20 + var20 * var21 * var21 + var22 * var22);

                if (var23 < var13)
                {
                    var13 = var23;
                    var15 = var19;
                }
            }
        }

        if (var15 != null)
        {
            return var15;
        }
        else
        {
            List var25 = this.p_();

            if (var25 != null)
            {
                ChunkPosition var26 = null;
                Iterator var27 = var25.iterator();

                while (var27.hasNext())
                {
                    var19 = (ChunkPosition)var27.next();
                    var20 = var19.x - par2;
                    var21 = var19.y - par3;
                    var22 = var19.z - par4;
                    var23 = (double)(var20 + var20 * var21 * var21 + var22 * var22);

                    if (var23 < var13)
                    {
                        var13 = var23;
                        var26 = var19;
                    }
                }

                return var26;
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * Returns a list of other locations at which the structure generation has been run, or null if not relevant to this
     * structure generator.
     */
    protected List p_()
    {
        return null;
    }

    protected abstract boolean a(int var1, int var2);

    protected abstract StructureStart b(int var1, int var2);
}
