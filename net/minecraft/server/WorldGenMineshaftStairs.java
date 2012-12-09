package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenMineshaftStairs extends StructurePiece
{
    public WorldGenMineshaftStairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.f = par4;
        this.e = par3StructureBoundingBox;
    }

    /**
     * Trys to find a valid place to put this component.
     */
    public static StructureBoundingBox a(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
    {
        StructureBoundingBox var6 = new StructureBoundingBox(par2, par3 - 5, par4, par2, par3 + 2, par4);

        switch (par5)
        {
            case 0:
                var6.d = par2 + 2;
                var6.f = par4 + 8;
                break;

            case 1:
                var6.a = par2 - 8;
                var6.f = par4 + 2;
                break;

            case 2:
                var6.d = par2 + 2;
                var6.c = par4 - 8;
                break;

            case 3:
                var6.d = par2 + 8;
                var6.f = par4 + 2;
        }

        return StructurePiece.a(par0List, var6) != null ? null : var6;
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece par1StructureComponent, List par2List, Random par3Random)
    {
        int var4 = this.c();

        switch (this.f)
        {
            case 0:
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a, this.e.b, this.e.f + 1, 0, var4);
                break;

            case 1:
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a - 1, this.e.b, this.e.c, 1, var4);
                break;

            case 2:
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a, this.e.b, this.e.c - 1, 2, var4);
                break;

            case 3:
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.d + 1, this.e.b, this.e.c, 3, var4);
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
            this.a(par1World, par3StructureBoundingBox, 0, 5, 0, 2, 7, 1, 0, 0, false);
            this.a(par1World, par3StructureBoundingBox, 0, 0, 7, 2, 2, 8, 0, 0, false);

            for (int var4 = 0; var4 < 5; ++var4)
            {
                this.a(par1World, par3StructureBoundingBox, 0, 5 - var4 - (var4 < 4 ? 1 : 0), 2 + var4, 2, 7 - var4, 2 + var4, 0, 0, false);
            }

            return true;
        }
    }
}
