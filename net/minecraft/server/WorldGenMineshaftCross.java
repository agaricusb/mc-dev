package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenMineshaftCross extends StructurePiece
{
    private final int a;
    private final boolean b;

    public WorldGenMineshaftCross(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.a = par4;
        this.e = par3StructureBoundingBox;
        this.b = par3StructureBoundingBox.c() > 3;
    }

    public static StructureBoundingBox a(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
    {
        StructureBoundingBox var6 = new StructureBoundingBox(par2, par3, par4, par2, par3 + 2, par4);

        if (par1Random.nextInt(4) == 0)
        {
            var6.e += 4;
        }

        switch (par5)
        {
            case 0:
                var6.a = par2 - 1;
                var6.d = par2 + 3;
                var6.f = par4 + 4;
                break;

            case 1:
                var6.a = par2 - 4;
                var6.c = par4 - 1;
                var6.f = par4 + 3;
                break;

            case 2:
                var6.a = par2 - 1;
                var6.d = par2 + 3;
                var6.c = par4 - 4;
                break;

            case 3:
                var6.d = par2 + 4;
                var6.c = par4 - 1;
                var6.f = par4 + 3;
        }

        return StructurePiece.a(par0List, var6) != null ? null : var6;
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece par1StructureComponent, List par2List, Random par3Random)
    {
        int var4 = this.c();

        switch (this.a)
        {
            case 0:
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a + 1, this.e.b, this.e.f + 1, 0, var4);
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a - 1, this.e.b, this.e.c + 1, 1, var4);
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.d + 1, this.e.b, this.e.c + 1, 3, var4);
                break;

            case 1:
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a + 1, this.e.b, this.e.c - 1, 2, var4);
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a + 1, this.e.b, this.e.f + 1, 0, var4);
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a - 1, this.e.b, this.e.c + 1, 1, var4);
                break;

            case 2:
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a + 1, this.e.b, this.e.c - 1, 2, var4);
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a - 1, this.e.b, this.e.c + 1, 1, var4);
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.d + 1, this.e.b, this.e.c + 1, 3, var4);
                break;

            case 3:
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a + 1, this.e.b, this.e.c - 1, 2, var4);
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a + 1, this.e.b, this.e.f + 1, 0, var4);
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.d + 1, this.e.b, this.e.c + 1, 3, var4);
        }

        if (this.b)
        {
            if (par3Random.nextBoolean())
            {
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a + 1, this.e.b + 3 + 1, this.e.c - 1, 2, var4);
            }

            if (par3Random.nextBoolean())
            {
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a - 1, this.e.b + 3 + 1, this.e.c + 1, 1, var4);
            }

            if (par3Random.nextBoolean())
            {
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.d + 1, this.e.b + 3 + 1, this.e.c + 1, 3, var4);
            }

            if (par3Random.nextBoolean())
            {
                WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a + 1, this.e.b + 3 + 1, this.e.f + 1, 0, var4);
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
            if (this.b)
            {
                this.a(par1World, par3StructureBoundingBox, this.e.a + 1, this.e.b, this.e.c, this.e.d - 1, this.e.b + 3 - 1, this.e.f, 0, 0, false);
                this.a(par1World, par3StructureBoundingBox, this.e.a, this.e.b, this.e.c + 1, this.e.d, this.e.b + 3 - 1, this.e.f - 1, 0, 0, false);
                this.a(par1World, par3StructureBoundingBox, this.e.a + 1, this.e.e - 2, this.e.c, this.e.d - 1, this.e.e, this.e.f, 0, 0, false);
                this.a(par1World, par3StructureBoundingBox, this.e.a, this.e.e - 2, this.e.c + 1, this.e.d, this.e.e, this.e.f - 1, 0, 0, false);
                this.a(par1World, par3StructureBoundingBox, this.e.a + 1, this.e.b + 3, this.e.c + 1, this.e.d - 1, this.e.b + 3, this.e.f - 1, 0, 0, false);
            }
            else
            {
                this.a(par1World, par3StructureBoundingBox, this.e.a + 1, this.e.b, this.e.c, this.e.d - 1, this.e.e, this.e.f, 0, 0, false);
                this.a(par1World, par3StructureBoundingBox, this.e.a, this.e.b, this.e.c + 1, this.e.d, this.e.e, this.e.f - 1, 0, 0, false);
            }

            this.a(par1World, par3StructureBoundingBox, this.e.a + 1, this.e.b, this.e.c + 1, this.e.a + 1, this.e.e, this.e.c + 1, Block.WOOD.id, 0, false);
            this.a(par1World, par3StructureBoundingBox, this.e.a + 1, this.e.b, this.e.f - 1, this.e.a + 1, this.e.e, this.e.f - 1, Block.WOOD.id, 0, false);
            this.a(par1World, par3StructureBoundingBox, this.e.d - 1, this.e.b, this.e.c + 1, this.e.d - 1, this.e.e, this.e.c + 1, Block.WOOD.id, 0, false);
            this.a(par1World, par3StructureBoundingBox, this.e.d - 1, this.e.b, this.e.f - 1, this.e.d - 1, this.e.e, this.e.f - 1, Block.WOOD.id, 0, false);

            for (int var4 = this.e.a; var4 <= this.e.d; ++var4)
            {
                for (int var5 = this.e.c; var5 <= this.e.f; ++var5)
                {
                    int var6 = this.a(par1World, var4, this.e.b - 1, var5, par3StructureBoundingBox);

                    if (var6 == 0)
                    {
                        this.a(par1World, Block.WOOD.id, 0, var4, this.e.b - 1, var5, par3StructureBoundingBox);
                    }
                }
            }

            return true;
        }
    }
}
