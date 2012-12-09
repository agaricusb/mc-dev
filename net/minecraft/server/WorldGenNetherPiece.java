package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

abstract class WorldGenNetherPiece extends StructurePiece
{
    protected WorldGenNetherPiece(int par1)
    {
        super(par1);
    }

    private int a(List par1List)
    {
        boolean var2 = false;
        int var3 = 0;
        WorldGenNetherPieceWeight var5;

        for (Iterator var4 = par1List.iterator(); var4.hasNext(); var3 += var5.b)
        {
            var5 = (WorldGenNetherPieceWeight)var4.next();

            if (var5.d > 0 && var5.c < var5.d)
            {
                var2 = true;
            }
        }

        return var2 ? var3 : -1;
    }

    private WorldGenNetherPiece a(WorldGenNetherPiece15 par1ComponentNetherBridgeStartPiece, List par2List, List par3List, Random par4Random, int par5, int par6, int par7, int par8, int par9)
    {
        int var10 = this.a(par2List);
        boolean var11 = var10 > 0 && par9 <= 30;
        int var12 = 0;

        while (var12 < 5 && var11)
        {
            ++var12;
            int var13 = par4Random.nextInt(var10);
            Iterator var14 = par2List.iterator();

            while (var14.hasNext())
            {
                WorldGenNetherPieceWeight var15 = (WorldGenNetherPieceWeight)var14.next();
                var13 -= var15.b;

                if (var13 < 0)
                {
                    if (!var15.a(par9) || var15 == par1ComponentNetherBridgeStartPiece.a && !var15.e)
                    {
                        break;
                    }

                    WorldGenNetherPiece var16 = WorldGenNetherPieces.a(var15, par3List, par4Random, par5, par6, par7, par8, par9);

                    if (var16 != null)
                    {
                        ++var15.c;
                        par1ComponentNetherBridgeStartPiece.a = var15;

                        if (!var15.a())
                        {
                            par2List.remove(var15);
                        }

                        return var16;
                    }
                }
            }
        }

        return WorldGenNetherPiece2.a(par3List, par4Random, par5, par6, par7, par8, par9);
    }

    /**
     * Finds a random component to tack on to the bridge. Or builds the end.
     */
    private StructurePiece a(WorldGenNetherPiece15 par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, int par6, int par7, int par8, boolean par9)
    {
        if (Math.abs(par4 - par1ComponentNetherBridgeStartPiece.b().a) <= 112 && Math.abs(par6 - par1ComponentNetherBridgeStartPiece.b().c) <= 112)
        {
            List var10 = par1ComponentNetherBridgeStartPiece.b;

            if (par9)
            {
                var10 = par1ComponentNetherBridgeStartPiece.c;
            }

            WorldGenNetherPiece var11 = this.a(par1ComponentNetherBridgeStartPiece, var10, par2List, par3Random, par4, par5, par6, par7, par8 + 1);

            if (var11 != null)
            {
                par2List.add(var11);
                par1ComponentNetherBridgeStartPiece.d.add(var11);
            }

            return var11;
        }
        else
        {
            return WorldGenNetherPiece2.a(par2List, par3Random, par4, par5, par6, par7, par8);
        }
    }

    /**
     * Gets the next component in any cardinal direction
     */
    protected StructurePiece a(WorldGenNetherPiece15 par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6)
    {
        switch (this.f)
        {
            case 0:
                return this.a(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.e.a + par4, this.e.b + par5, this.e.f + 1, this.f, this.c(), par6);

            case 1:
                return this.a(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.e.a - 1, this.e.b + par5, this.e.c + par4, this.f, this.c(), par6);

            case 2:
                return this.a(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.e.a + par4, this.e.b + par5, this.e.c - 1, this.f, this.c(), par6);

            case 3:
                return this.a(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.e.d + 1, this.e.b + par5, this.e.c + par4, this.f, this.c(), par6);

            default:
                return null;
        }
    }

    /**
     * Gets the next component in the +/- X direction
     */
    protected StructurePiece b(WorldGenNetherPiece15 par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6)
    {
        switch (this.f)
        {
            case 0:
                return this.a(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.e.a - 1, this.e.b + par4, this.e.c + par5, 1, this.c(), par6);

            case 1:
                return this.a(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.e.a + par5, this.e.b + par4, this.e.c - 1, 2, this.c(), par6);

            case 2:
                return this.a(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.e.a - 1, this.e.b + par4, this.e.c + par5, 1, this.c(), par6);

            case 3:
                return this.a(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.e.a + par5, this.e.b + par4, this.e.c - 1, 2, this.c(), par6);

            default:
                return null;
        }
    }

    /**
     * Gets the next component in the +/- Z direction
     */
    protected StructurePiece c(WorldGenNetherPiece15 par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6)
    {
        switch (this.f)
        {
            case 0:
                return this.a(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.e.d + 1, this.e.b + par4, this.e.c + par5, 3, this.c(), par6);

            case 1:
                return this.a(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.e.a + par5, this.e.b + par4, this.e.f + 1, 0, this.c(), par6);

            case 2:
                return this.a(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.e.d + 1, this.e.b + par4, this.e.c + par5, 3, this.c(), par6);

            case 3:
                return this.a(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.e.a + par5, this.e.b + par4, this.e.f + 1, 0, this.c(), par6);

            default:
                return null;
        }
    }

    /**
     * Checks if the bounding box's minY is > 10
     */
    protected static boolean a(StructureBoundingBox par0StructureBoundingBox)
    {
        return par0StructureBoundingBox != null && par0StructureBoundingBox.b > 10;
    }
}
