package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenNetherPieces
{
    private static final WorldGenNetherPieceWeight[] a = new WorldGenNetherPieceWeight[] {new WorldGenNetherPieceWeight(WorldGenNetherPiece3.class, 30, 0, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece1.class, 10, 4), new WorldGenNetherPieceWeight(WorldGenNetherPiece13.class, 10, 4), new WorldGenNetherPieceWeight(WorldGenNetherPiece16.class, 10, 3), new WorldGenNetherPieceWeight(WorldGenNetherPiece12.class, 5, 2), new WorldGenNetherPieceWeight(WorldGenNetherPiece6.class, 5, 1)};
    private static final WorldGenNetherPieceWeight[] b = new WorldGenNetherPieceWeight[] {new WorldGenNetherPieceWeight(WorldGenNetherPiece9.class, 25, 0, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece7.class, 15, 5), new WorldGenNetherPieceWeight(WorldGenNetherPiece10.class, 5, 10), new WorldGenNetherPieceWeight(WorldGenNetherPiece8.class, 5, 10), new WorldGenNetherPieceWeight(WorldGenNetherPiece4.class, 10, 3, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece5.class, 7, 2), new WorldGenNetherPieceWeight(WorldGenNetherPiece11.class, 5, 2)};

    private static WorldGenNetherPiece b(WorldGenNetherPieceWeight par0StructureNetherBridgePieceWeight, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        Class var8 = par0StructureNetherBridgePieceWeight.a;
        Object var9 = null;

        if (var8 == WorldGenNetherPiece3.class)
        {
            var9 = WorldGenNetherPiece3.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == WorldGenNetherPiece1.class)
        {
            var9 = WorldGenNetherPiece1.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == WorldGenNetherPiece13.class)
        {
            var9 = WorldGenNetherPiece13.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == WorldGenNetherPiece16.class)
        {
            var9 = WorldGenNetherPiece16.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == WorldGenNetherPiece12.class)
        {
            var9 = WorldGenNetherPiece12.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == WorldGenNetherPiece6.class)
        {
            var9 = WorldGenNetherPiece6.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == WorldGenNetherPiece9.class)
        {
            var9 = WorldGenNetherPiece9.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == WorldGenNetherPiece10.class)
        {
            var9 = WorldGenNetherPiece10.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == WorldGenNetherPiece8.class)
        {
            var9 = WorldGenNetherPiece8.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == WorldGenNetherPiece4.class)
        {
            var9 = WorldGenNetherPiece4.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == WorldGenNetherPiece5.class)
        {
            var9 = WorldGenNetherPiece5.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == WorldGenNetherPiece7.class)
        {
            var9 = WorldGenNetherPiece7.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == WorldGenNetherPiece11.class)
        {
            var9 = WorldGenNetherPiece11.a(par1List, par2Random, par3, par4, par5, par6, par7);
        }

        return (WorldGenNetherPiece)var9;
    }

    static WorldGenNetherPiece a(WorldGenNetherPieceWeight par0StructureNetherBridgePieceWeight, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        return b(par0StructureNetherBridgePieceWeight, par1List, par2Random, par3, par4, par5, par6, par7);
    }

    static WorldGenNetherPieceWeight[] a()
    {
        return a;
    }

    static WorldGenNetherPieceWeight[] b()
    {
        return b;
    }
}
