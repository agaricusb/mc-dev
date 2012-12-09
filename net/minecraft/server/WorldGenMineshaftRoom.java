package net.minecraft.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WorldGenMineshaftRoom extends StructurePiece
{
    /** List of other Mineshaft components linked to this room. */
    private List a = new LinkedList();

    public WorldGenMineshaftRoom(int par1, Random par2Random, int par3, int par4)
    {
        super(par1);
        this.e = new StructureBoundingBox(par3, 50, par4, par3 + 7 + par2Random.nextInt(6), 54 + par2Random.nextInt(6), par4 + 7 + par2Random.nextInt(6));
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece par1StructureComponent, List par2List, Random par3Random)
    {
        int var4 = this.c();
        int var6 = this.e.c() - 3 - 1;

        if (var6 <= 0)
        {
            var6 = 1;
        }

        int var5;
        StructurePiece var7;
        StructureBoundingBox var8;

        for (var5 = 0; var5 < this.e.b(); var5 += 4)
        {
            var5 += par3Random.nextInt(this.e.b());

            if (var5 + 3 > this.e.b())
            {
                break;
            }

            var7 = WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a + var5, this.e.b + par3Random.nextInt(var6) + 1, this.e.c - 1, 2, var4);

            if (var7 != null)
            {
                var8 = var7.b();
                this.a.add(new StructureBoundingBox(var8.a, var8.b, this.e.c, var8.d, var8.e, this.e.c + 1));
            }
        }

        for (var5 = 0; var5 < this.e.b(); var5 += 4)
        {
            var5 += par3Random.nextInt(this.e.b());

            if (var5 + 3 > this.e.b())
            {
                break;
            }

            var7 = WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a + var5, this.e.b + par3Random.nextInt(var6) + 1, this.e.f + 1, 0, var4);

            if (var7 != null)
            {
                var8 = var7.b();
                this.a.add(new StructureBoundingBox(var8.a, var8.b, this.e.f - 1, var8.d, var8.e, this.e.f));
            }
        }

        for (var5 = 0; var5 < this.e.d(); var5 += 4)
        {
            var5 += par3Random.nextInt(this.e.d());

            if (var5 + 3 > this.e.d())
            {
                break;
            }

            var7 = WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a - 1, this.e.b + par3Random.nextInt(var6) + 1, this.e.c + var5, 1, var4);

            if (var7 != null)
            {
                var8 = var7.b();
                this.a.add(new StructureBoundingBox(this.e.a, var8.b, var8.c, this.e.a + 1, var8.e, var8.f));
            }
        }

        for (var5 = 0; var5 < this.e.d(); var5 += 4)
        {
            var5 += par3Random.nextInt(this.e.d());

            if (var5 + 3 > this.e.d())
            {
                break;
            }

            var7 = WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.d + 1, this.e.b + par3Random.nextInt(var6) + 1, this.e.c + var5, 3, var4);

            if (var7 != null)
            {
                var8 = var7.b();
                this.a.add(new StructureBoundingBox(this.e.d - 1, var8.b, var8.c, this.e.d, var8.e, var8.f));
            }
        }
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        if (this.a(par1World, par3StructureBoundingBox))
        {
            return false;
        }
        else
        {
            this.a(par1World, par3StructureBoundingBox, this.e.a, this.e.b, this.e.c, this.e.d, this.e.b, this.e.f, Block.DIRT.id, 0, true);
            this.a(par1World, par3StructureBoundingBox, this.e.a, this.e.b + 1, this.e.c, this.e.d, Math.min(this.e.b + 3, this.e.e), this.e.f, 0, 0, false);
            Iterator var4 = this.a.iterator();

            while (var4.hasNext())
            {
                StructureBoundingBox var5 = (StructureBoundingBox)var4.next();
                this.a(par1World, par3StructureBoundingBox, var5.a, var5.e - 2, var5.c, var5.d, var5.e, var5.f, 0, 0, false);
            }

            this.a(par1World, par3StructureBoundingBox, this.e.a, this.e.b + 4, this.e.c, this.e.d, this.e.e, this.e.f, 0, false);
            return true;
        }
    }
}
