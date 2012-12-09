package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenMineshaftPieces
{
    /** List of contents that can generate in Mineshafts. */
    private static final StructurePieceTreasure[] a = new StructurePieceTreasure[] {new StructurePieceTreasure(Item.IRON_INGOT.id, 0, 1, 5, 10), new StructurePieceTreasure(Item.GOLD_INGOT.id, 0, 1, 3, 5), new StructurePieceTreasure(Item.REDSTONE.id, 0, 4, 9, 5), new StructurePieceTreasure(Item.INK_SACK.id, 4, 4, 9, 5), new StructurePieceTreasure(Item.DIAMOND.id, 0, 1, 2, 3), new StructurePieceTreasure(Item.COAL.id, 0, 3, 8, 10), new StructurePieceTreasure(Item.BREAD.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.IRON_PICKAXE.id, 0, 1, 1, 1), new StructurePieceTreasure(Block.RAILS.id, 0, 4, 8, 1), new StructurePieceTreasure(Item.MELON_SEEDS.id, 0, 2, 4, 10), new StructurePieceTreasure(Item.PUMPKIN_SEEDS.id, 0, 2, 4, 10)};

    private static StructurePiece a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        int var7 = par1Random.nextInt(100);
        StructureBoundingBox var8;

        if (var7 >= 80)
        {
            var8 = WorldGenMineshaftCross.a(par0List, par1Random, par2, par3, par4, par5);

            if (var8 != null)
            {
                return new WorldGenMineshaftCross(par6, par1Random, var8, par5);
            }
        }
        else if (var7 >= 70)
        {
            var8 = WorldGenMineshaftStairs.a(par0List, par1Random, par2, par3, par4, par5);

            if (var8 != null)
            {
                return new WorldGenMineshaftStairs(par6, par1Random, var8, par5);
            }
        }
        else
        {
            var8 = WorldGenMineshaftCorridor.a(par0List, par1Random, par2, par3, par4, par5);

            if (var8 != null)
            {
                return new WorldGenMineshaftCorridor(par6, par1Random, var8, par5);
            }
        }

        return null;
    }

    private static StructurePiece b(StructurePiece par0StructureComponent, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        if (par7 > 8)
        {
            return null;
        }
        else if (Math.abs(par3 - par0StructureComponent.b().a) <= 80 && Math.abs(par5 - par0StructureComponent.b().c) <= 80)
        {
            StructurePiece var8 = a(par1List, par2Random, par3, par4, par5, par6, par7 + 1);

            if (var8 != null)
            {
                par1List.add(var8);
                var8.a(par0StructureComponent, par1List, par2Random);
            }

            return var8;
        }
        else
        {
            return null;
        }
    }

    static StructurePiece a(StructurePiece par0StructureComponent, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        return b(par0StructureComponent, par1List, par2Random, par3, par4, par5, par6, par7);
    }

    static StructurePieceTreasure[] a()
    {
        return a;
    }
}
