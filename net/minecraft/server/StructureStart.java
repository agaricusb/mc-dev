package net.minecraft.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public abstract class StructureStart
{
    /** List of all StructureComponents that are part of this structure */
    protected LinkedList a = new LinkedList();
    protected StructureBoundingBox b;

    public StructureBoundingBox a()
    {
        return this.b;
    }

    public LinkedList b()
    {
        return this.a;
    }

    /**
     * Keeps iterating Structure Pieces and spawning them until the checks tell it to stop
     */
    public void a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        Iterator var4 = this.a.iterator();

        while (var4.hasNext())
        {
            StructurePiece var5 = (StructurePiece)var4.next();

            if (var5.b().a(par3StructureBoundingBox) && !var5.a(par1World, par2Random, par3StructureBoundingBox))
            {
                var4.remove();
            }
        }
    }

    /**
     * Calculates total bounding box based on components' bounding boxes and saves it to boundingBox
     */
    protected void c()
    {
        this.b = StructureBoundingBox.a();
        Iterator var1 = this.a.iterator();

        while (var1.hasNext())
        {
            StructurePiece var2 = (StructurePiece)var1.next();
            this.b.b(var2.b());
        }
    }

    /**
     * offsets the structure Bounding Boxes up to a certain height, typically 63 - 10
     */
    protected void a(World par1World, Random par2Random, int par3)
    {
        int var4 = 63 - par3;
        int var5 = this.b.c() + 1;

        if (var5 < var4)
        {
            var5 += par2Random.nextInt(var4 - var5);
        }

        int var6 = var5 - this.b.e;
        this.b.a(0, var6, 0);
        Iterator var7 = this.a.iterator();

        while (var7.hasNext())
        {
            StructurePiece var8 = (StructurePiece)var7.next();
            var8.b().a(0, var6, 0);
        }
    }

    protected void a(World par1World, Random par2Random, int par3, int par4)
    {
        int var5 = par4 - par3 + 1 - this.b.c();
        boolean var6 = true;
        int var10;

        if (var5 > 1)
        {
            var10 = par3 + par2Random.nextInt(var5);
        }
        else
        {
            var10 = par3;
        }

        int var7 = var10 - this.b.b;
        this.b.a(0, var7, 0);
        Iterator var8 = this.a.iterator();

        while (var8.hasNext())
        {
            StructurePiece var9 = (StructurePiece)var8.next();
            var9.b().a(0, var7, 0);
        }
    }

    /**
     * currently only defined for Villages, returns true if Village has more than 2 non-road components
     */
    public boolean d()
    {
        return true;
    }
}
