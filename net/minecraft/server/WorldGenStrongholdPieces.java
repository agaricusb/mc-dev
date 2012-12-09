package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class WorldGenStrongholdPieces
{
    private static final WorldGenStrongholdPieceWeight[] b = new WorldGenStrongholdPieceWeight[] {new WorldGenStrongholdPieceWeight(WorldGenStrongholdStairs.class, 40, 0), new WorldGenStrongholdPieceWeight(WorldGenStrongholdPrison.class, 5, 5), new WorldGenStrongholdPieceWeight(WorldGenStrongholdLeftTurn.class, 20, 0), new WorldGenStrongholdPieceWeight(WorldGenStrongholdRightTurn.class, 20, 0), new WorldGenStrongholdPieceWeight(WorldGenStrongholdRoomCrossing.class, 10, 6), new WorldGenStrongholdPieceWeight(WorldGenStrongholdStairsStraight.class, 5, 5), new WorldGenStrongholdPieceWeight(WorldGenStrongholdStairs2.class, 5, 5), new WorldGenStrongholdPieceWeight(WorldGenStrongholdCrossing.class, 5, 4), new WorldGenStrongholdPieceWeight(WorldGenStrongholdChestCorridor.class, 5, 4), new WorldGenStrongholdUnknown(WorldGenStrongholdLibrary.class, 10, 2), new WorldGenStrongholdPiece2(WorldGenStrongholdPortalRoom.class, 20, 1)};
    private static List c;
    private static Class d;
    static int a = 0;
    private static final WorldGenStrongholdStones e = new WorldGenStrongholdStones((WorldGenStrongholdUnknown)null);

    /**
     * sets up Arrays with the Structure pieces and their weights
     */
    public static void a()
    {
        c = new ArrayList();
        WorldGenStrongholdPieceWeight[] var0 = b;
        int var1 = var0.length;

        for (int var2 = 0; var2 < var1; ++var2)
        {
            WorldGenStrongholdPieceWeight var3 = var0[var2];
            var3.c = 0;
            c.add(var3);
        }

        d = null;
    }

    private static boolean c()
    {
        boolean var0 = false;
        a = 0;
        WorldGenStrongholdPieceWeight var2;

        for (Iterator var1 = c.iterator(); var1.hasNext(); a += var2.b)
        {
            var2 = (WorldGenStrongholdPieceWeight)var1.next();

            if (var2.d > 0 && var2.c < var2.d)
            {
                var0 = true;
            }
        }

        return var0;
    }

    /**
     * translates the PieceWeight class to the Component class
     */
    private static WorldGenStrongholdPiece a(Class par0Class, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        Object var8 = null;

        if (par0Class == WorldGenStrongholdStairs.class)
        {
            var8 = WorldGenStrongholdStairs.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == WorldGenStrongholdPrison.class)
        {
            var8 = WorldGenStrongholdPrison.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == WorldGenStrongholdLeftTurn.class)
        {
            var8 = WorldGenStrongholdLeftTurn.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == WorldGenStrongholdRightTurn.class)
        {
            var8 = WorldGenStrongholdRightTurn.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == WorldGenStrongholdRoomCrossing.class)
        {
            var8 = WorldGenStrongholdRoomCrossing.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == WorldGenStrongholdStairsStraight.class)
        {
            var8 = WorldGenStrongholdStairsStraight.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == WorldGenStrongholdStairs2.class)
        {
            var8 = WorldGenStrongholdStairs2.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == WorldGenStrongholdCrossing.class)
        {
            var8 = WorldGenStrongholdCrossing.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == WorldGenStrongholdChestCorridor.class)
        {
            var8 = WorldGenStrongholdChestCorridor.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == WorldGenStrongholdLibrary.class)
        {
            var8 = WorldGenStrongholdLibrary.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (par0Class == WorldGenStrongholdPortalRoom.class)
        {
            var8 = WorldGenStrongholdPortalRoom.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }

        return (WorldGenStrongholdPiece)var8;
    }

    private static WorldGenStrongholdPiece b(WorldGenStrongholdStart par0ComponentStrongholdStairs2, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        if (!c())
        {
            return null;
        }
        else
        {
            if (d != null)
            {
                WorldGenStrongholdPiece var8 = a(d, par1List, par2Random, par3, par4, par5, par6, par7);
                d = null;

                if (var8 != null)
                {
                    return var8;
                }
            }

            int var13 = 0;

            while (var13 < 5)
            {
                ++var13;
                int var9 = par2Random.nextInt(a);
                Iterator var10 = c.iterator();

                while (var10.hasNext())
                {
                    WorldGenStrongholdPieceWeight var11 = (WorldGenStrongholdPieceWeight)var10.next();
                    var9 -= var11.b;

                    if (var9 < 0)
                    {
                        if (!var11.a(par7) || var11 == par0ComponentStrongholdStairs2.a)
                        {
                            break;
                        }

                        WorldGenStrongholdPiece var12 = a(var11.a, par1List, par2Random, par3, par4, par5, par6, par7);

                        if (var12 != null)
                        {
                            ++var11.c;
                            par0ComponentStrongholdStairs2.a = var11;

                            if (!var11.a())
                            {
                                c.remove(var11);
                            }

                            return var12;
                        }
                    }
                }
            }

            StructureBoundingBox var14 = WorldGenStrongholdCorridor.a(par1List, par2Random, par3, par4, par5, par6);

            if (var14 != null && var14.b > 1)
            {
                return new WorldGenStrongholdCorridor(par7, par2Random, var14, par6);
            }
            else
            {
                return null;
            }
        }
    }

    private static StructurePiece c(WorldGenStrongholdStart par0ComponentStrongholdStairs2, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        if (par7 > 50)
        {
            return null;
        }
        else if (Math.abs(par3 - par0ComponentStrongholdStairs2.b().a) <= 112 && Math.abs(par5 - par0ComponentStrongholdStairs2.b().c) <= 112)
        {
            WorldGenStrongholdPiece var8 = b(par0ComponentStrongholdStairs2, par1List, par2Random, par3, par4, par5, par6, par7 + 1);

            if (var8 != null)
            {
                par1List.add(var8);
                par0ComponentStrongholdStairs2.c.add(var8);
            }

            return var8;
        }
        else
        {
            return null;
        }
    }

    static StructurePiece a(WorldGenStrongholdStart par0ComponentStrongholdStairs2, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        return c(par0ComponentStrongholdStairs2, par1List, par2Random, par3, par4, par5, par6, par7);
    }

    static Class a(Class par0Class)
    {
        d = par0Class;
        return par0Class;
    }

    static WorldGenStrongholdStones b()
    {
        return e;
    }
}
