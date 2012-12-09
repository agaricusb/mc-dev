package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdCorridor extends WorldGenStrongholdPiece
{
    private final int a;

    public WorldGenStrongholdCorridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.f = par4;
        this.e = par3StructureBoundingBox;
        this.a = par4 != 2 && par4 != 0 ? par3StructureBoundingBox.b() : par3StructureBoundingBox.d();
    }

    public static StructureBoundingBox a(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -1, -1, 0, 5, 5, 4, par5);
        StructurePiece var8 = StructurePiece.a(par0List, var7);

        if (var8 == null)
        {
            return null;
        }
        else
        {
            if (var8.b().b == var7.b)
            {
                for (int var9 = 3; var9 >= 1; --var9)
                {
                    var7 = StructureBoundingBox.a(par2, par3, par4, -1, -1, 0, 5, 5, var9 - 1, par5);

                    if (!var8.b().a(var7))
                    {
                        return StructureBoundingBox.a(par2, par3, par4, -1, -1, 0, 5, 5, var9, par5);
                    }
                }
            }

            return null;
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
            for (int var4 = 0; var4 < this.a; ++var4)
            {
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, 0, 0, var4, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, 1, 0, var4, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, 2, 0, var4, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, 3, 0, var4, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, 4, 0, var4, par3StructureBoundingBox);

                for (int var5 = 1; var5 <= 3; ++var5)
                {
                    this.a(par1World, Block.SMOOTH_BRICK.id, 0, 0, var5, var4, par3StructureBoundingBox);
                    this.a(par1World, 0, 0, 1, var5, var4, par3StructureBoundingBox);
                    this.a(par1World, 0, 0, 2, var5, var4, par3StructureBoundingBox);
                    this.a(par1World, 0, 0, 3, var5, var4, par3StructureBoundingBox);
                    this.a(par1World, Block.SMOOTH_BRICK.id, 0, 4, var5, var4, par3StructureBoundingBox);
                }

                this.a(par1World, Block.SMOOTH_BRICK.id, 0, 0, 4, var4, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, 1, 4, var4, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, 2, 4, var4, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, 3, 4, var4, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, 4, 4, var4, par3StructureBoundingBox);
            }

            return true;
        }
    }
}
