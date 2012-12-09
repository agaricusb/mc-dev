package net.minecraft.server;

import java.util.Random;

abstract class WorldGenScatteredPiece extends StructurePiece
{
    /** The size of the bounding box for this feature in the X axis */
    protected final int a;

    /** The size of the bounding box for this feature in the Y axis */
    protected final int b;

    /** The size of the bounding box for this feature in the Z axis */
    protected final int c;
    protected int d = -1;

    protected WorldGenScatteredPiece(Random par1Random, int par2, int par3, int par4, int par5, int par6, int par7)
    {
        super(0);
        this.a = par5;
        this.b = par6;
        this.c = par7;
        this.f = par1Random.nextInt(4);

        switch (this.f)
        {
            case 0:
            case 2:
                this.e = new StructureBoundingBox(par2, par3, par4, par2 + par5 - 1, par3 + par6 - 1, par4 + par7 - 1);
                break;

            default:
                this.e = new StructureBoundingBox(par2, par3, par4, par2 + par7 - 1, par3 + par6 - 1, par4 + par5 - 1);
        }
    }

    protected boolean a(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3)
    {
        if (this.d >= 0)
        {
            return true;
        }
        else
        {
            int var4 = 0;
            int var5 = 0;

            for (int var6 = this.e.c; var6 <= this.e.f; ++var6)
            {
                for (int var7 = this.e.a; var7 <= this.e.d; ++var7)
                {
                    if (par2StructureBoundingBox.b(var7, 64, var6))
                    {
                        var4 += Math.max(par1World.i(var7, var6), par1World.worldProvider.getSeaLevel());
                        ++var5;
                    }
                }
            }

            if (var5 == 0)
            {
                return false;
            }
            else
            {
                this.d = var4 / var5;
                this.e.a(0, this.d - this.e.b + par3, 0);
                return true;
            }
        }
    }
}
