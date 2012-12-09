package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class WorldGenVillagePieces
{
    public static ArrayList a(Random par0Random, int par1)
    {
        ArrayList var2 = new ArrayList();
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageHouse.class, 4, MathHelper.nextInt(par0Random, 2 + par1, 4 + par1 * 2)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageTemple.class, 20, MathHelper.nextInt(par0Random, 0 + par1, 1 + par1)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageLibrary.class, 20, MathHelper.nextInt(par0Random, 0 + par1, 2 + par1)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageHut.class, 3, MathHelper.nextInt(par0Random, 2 + par1, 5 + par1 * 3)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageButcher.class, 15, MathHelper.nextInt(par0Random, 0 + par1, 2 + par1)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageFarm2.class, 3, MathHelper.nextInt(par0Random, 1 + par1, 4 + par1)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageFarm.class, 3, MathHelper.nextInt(par0Random, 2 + par1, 4 + par1 * 2)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageBlacksmith.class, 15, MathHelper.nextInt(par0Random, 0, 1 + par1)));
        var2.add(new WorldGenVillagePieceWeight(WorldGenVillageHouse2.class, 8, MathHelper.nextInt(par0Random, 0 + par1, 3 + par1 * 2)));
        Iterator var3 = var2.iterator();

        while (var3.hasNext())
        {
            if (((WorldGenVillagePieceWeight)var3.next()).d == 0)
            {
                var3.remove();
            }
        }

        return var2;
    }

    private static int a(List par0List)
    {
        boolean var1 = false;
        int var2 = 0;
        WorldGenVillagePieceWeight var4;

        for (Iterator var3 = par0List.iterator(); var3.hasNext(); var2 += var4.b)
        {
            var4 = (WorldGenVillagePieceWeight)var3.next();

            if (var4.d > 0 && var4.c < var4.d)
            {
                var1 = true;
            }
        }

        return var1 ? var2 : -1;
    }

    private static WorldGenVillagePiece a(WorldGenVillageStartPiece par0ComponentVillageStartPiece, WorldGenVillagePieceWeight par1StructureVillagePieceWeight, List par2List, Random par3Random, int par4, int par5, int par6, int par7, int par8)
    {
        Class var9 = par1StructureVillagePieceWeight.a;
        Object var10 = null;

        if (var9 == WorldGenVillageHouse.class)
        {
            var10 = WorldGenVillageHouse.a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == WorldGenVillageTemple.class)
        {
            var10 = WorldGenVillageTemple.a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == WorldGenVillageLibrary.class)
        {
            var10 = WorldGenVillageLibrary.a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == WorldGenVillageHut.class)
        {
            var10 = WorldGenVillageHut.a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == WorldGenVillageButcher.class)
        {
            var10 = WorldGenVillageButcher.a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == WorldGenVillageFarm2.class)
        {
            var10 = WorldGenVillageFarm2.a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == WorldGenVillageFarm.class)
        {
            var10 = WorldGenVillageFarm.a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == WorldGenVillageBlacksmith.class)
        {
            var10 = WorldGenVillageBlacksmith.a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }
        else if (var9 == WorldGenVillageHouse2.class)
        {
            var10 = WorldGenVillageHouse2.a(par0ComponentVillageStartPiece, par2List, par3Random, par4, par5, par6, par7, par8);
        }

        return (WorldGenVillagePiece)var10;
    }

    /**
     * attempts to find a next Village Component to be spawned
     */
    private static WorldGenVillagePiece c(WorldGenVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        int var8 = a(par0ComponentVillageStartPiece.h);

        if (var8 <= 0)
        {
            return null;
        }
        else
        {
            int var9 = 0;

            while (var9 < 5)
            {
                ++var9;
                int var10 = par2Random.nextInt(var8);
                Iterator var11 = par0ComponentVillageStartPiece.h.iterator();

                while (var11.hasNext())
                {
                    WorldGenVillagePieceWeight var12 = (WorldGenVillagePieceWeight)var11.next();
                    var10 -= var12.b;

                    if (var10 < 0)
                    {
                        if (!var12.a(par7) || var12 == par0ComponentVillageStartPiece.d && par0ComponentVillageStartPiece.h.size() > 1)
                        {
                            break;
                        }

                        WorldGenVillagePiece var13 = a(par0ComponentVillageStartPiece, var12, par1List, par2Random, par3, par4, par5, par6, par7);

                        if (var13 != null)
                        {
                            ++var12.c;
                            par0ComponentVillageStartPiece.d = var12;

                            if (!var12.a())
                            {
                                par0ComponentVillageStartPiece.h.remove(var12);
                            }

                            return var13;
                        }
                    }
                }
            }

            StructureBoundingBox var14 = WorldGenVillageLight.a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);

            if (var14 != null)
            {
                return new WorldGenVillageLight(par0ComponentVillageStartPiece, par7, par2Random, var14, par6);
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * attempts to find a next Structure Component to be spawned, private Village function
     */
    private static StructurePiece d(WorldGenVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        if (par7 > 50)
        {
            return null;
        }
        else if (Math.abs(par3 - par0ComponentVillageStartPiece.b().a) <= 112 && Math.abs(par5 - par0ComponentVillageStartPiece.b().c) <= 112)
        {
            WorldGenVillagePiece var8 = c(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7 + 1);

            if (var8 != null)
            {
                int var9 = (var8.e.a + var8.e.d) / 2;
                int var10 = (var8.e.c + var8.e.f) / 2;
                int var11 = var8.e.d - var8.e.a;
                int var12 = var8.e.f - var8.e.c;
                int var13 = var11 > var12 ? var11 : var12;

                if (par0ComponentVillageStartPiece.d().a(var9, var10, var13 / 2 + 4, WorldGenVillage.e))
                {
                    par1List.add(var8);
                    par0ComponentVillageStartPiece.i.add(var8);
                    return var8;
                }
            }

            return null;
        }
        else
        {
            return null;
        }
    }

    private static StructurePiece e(WorldGenVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        if (par7 > 3 + par0ComponentVillageStartPiece.c)
        {
            return null;
        }
        else if (Math.abs(par3 - par0ComponentVillageStartPiece.b().a) <= 112 && Math.abs(par5 - par0ComponentVillageStartPiece.b().c) <= 112)
        {
            StructureBoundingBox var8 = WorldGenVillageRoad.a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);

            if (var8 != null && var8.b > 10)
            {
                WorldGenVillageRoad var9 = new WorldGenVillageRoad(par0ComponentVillageStartPiece, par7, par2Random, var8, par6);
                int var10 = (var9.e.a + var9.e.d) / 2;
                int var11 = (var9.e.c + var9.e.f) / 2;
                int var12 = var9.e.d - var9.e.a;
                int var13 = var9.e.f - var9.e.c;
                int var14 = var12 > var13 ? var12 : var13;

                if (par0ComponentVillageStartPiece.d().a(var10, var11, var14 / 2 + 4, WorldGenVillage.e))
                {
                    par1List.add(var9);
                    par0ComponentVillageStartPiece.j.add(var9);
                    return var9;
                }
            }

            return null;
        }
        else
        {
            return null;
        }
    }

    /**
     * attempts to find a next Structure Component to be spawned
     */
    static StructurePiece a(WorldGenVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        return d(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7);
    }

    static StructurePiece b(WorldGenVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        return e(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7);
    }
}
