package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdLibrary extends WorldGenStrongholdPiece
{
    /** List of items that Stronghold Library chests can contain. */
    private static final StructurePieceTreasure[] b = new StructurePieceTreasure[] {new StructurePieceTreasure(Item.BOOK.id, 0, 1, 3, 20), new StructurePieceTreasure(Item.PAPER.id, 0, 2, 7, 20), new StructurePieceTreasure(Item.MAP_EMPTY.id, 0, 1, 1, 1), new StructurePieceTreasure(Item.COMPASS.id, 0, 1, 1, 1)};
    protected final WorldGenStrongholdDoorType a;
    private final boolean c;

    public WorldGenStrongholdLibrary(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.f = par4;
        this.a = this.a(par2Random);
        this.e = par3StructureBoundingBox;
        this.c = par3StructureBoundingBox.c() > 6;
    }

    public static WorldGenStrongholdLibrary a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -4, -1, 0, 14, 11, 15, par5);

        if (!a(var7) || StructurePiece.a(par0List, var7) != null)
        {
            var7 = StructureBoundingBox.a(par2, par3, par4, -4, -1, 0, 14, 6, 15, par5);

            if (!a(var7) || StructurePiece.a(par0List, var7) != null)
            {
                return null;
            }
        }

        return new WorldGenStrongholdLibrary(par6, par1Random, var7, par5);
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
            byte var4 = 11;

            if (!this.c)
            {
                var4 = 6;
            }

            this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 13, var4 - 1, 14, true, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par2Random, par3StructureBoundingBox, this.a, 4, 1, 0);
            this.a(par1World, par3StructureBoundingBox, par2Random, 0.07F, 2, 1, 1, 11, 4, 13, Block.WEB.id, Block.WEB.id, false);
            int var7;

            for (var7 = 1; var7 <= 13; ++var7)
            {
                if ((var7 - 1) % 4 == 0)
                {
                    this.a(par1World, par3StructureBoundingBox, 1, 1, var7, 1, 4, var7, Block.WOOD.id, Block.WOOD.id, false);
                    this.a(par1World, par3StructureBoundingBox, 12, 1, var7, 12, 4, var7, Block.WOOD.id, Block.WOOD.id, false);
                    this.a(par1World, Block.TORCH.id, 0, 2, 3, var7, par3StructureBoundingBox);
                    this.a(par1World, Block.TORCH.id, 0, 11, 3, var7, par3StructureBoundingBox);

                    if (this.c)
                    {
                        this.a(par1World, par3StructureBoundingBox, 1, 6, var7, 1, 9, var7, Block.WOOD.id, Block.WOOD.id, false);
                        this.a(par1World, par3StructureBoundingBox, 12, 6, var7, 12, 9, var7, Block.WOOD.id, Block.WOOD.id, false);
                    }
                }
                else
                {
                    this.a(par1World, par3StructureBoundingBox, 1, 1, var7, 1, 4, var7, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
                    this.a(par1World, par3StructureBoundingBox, 12, 1, var7, 12, 4, var7, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);

                    if (this.c)
                    {
                        this.a(par1World, par3StructureBoundingBox, 1, 6, var7, 1, 9, var7, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
                        this.a(par1World, par3StructureBoundingBox, 12, 6, var7, 12, 9, var7, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
                    }
                }
            }

            for (var7 = 3; var7 < 12; var7 += 2)
            {
                this.a(par1World, par3StructureBoundingBox, 3, 1, var7, 4, 3, var7, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
                this.a(par1World, par3StructureBoundingBox, 6, 1, var7, 7, 3, var7, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
                this.a(par1World, par3StructureBoundingBox, 9, 1, var7, 10, 3, var7, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
            }

            if (this.c)
            {
                this.a(par1World, par3StructureBoundingBox, 1, 5, 1, 3, 5, 13, Block.WOOD.id, Block.WOOD.id, false);
                this.a(par1World, par3StructureBoundingBox, 10, 5, 1, 12, 5, 13, Block.WOOD.id, Block.WOOD.id, false);
                this.a(par1World, par3StructureBoundingBox, 4, 5, 1, 9, 5, 2, Block.WOOD.id, Block.WOOD.id, false);
                this.a(par1World, par3StructureBoundingBox, 4, 5, 12, 9, 5, 13, Block.WOOD.id, Block.WOOD.id, false);
                this.a(par1World, Block.WOOD.id, 0, 9, 5, 11, par3StructureBoundingBox);
                this.a(par1World, Block.WOOD.id, 0, 8, 5, 11, par3StructureBoundingBox);
                this.a(par1World, Block.WOOD.id, 0, 9, 5, 10, par3StructureBoundingBox);
                this.a(par1World, par3StructureBoundingBox, 3, 6, 2, 3, 6, 12, Block.FENCE.id, Block.FENCE.id, false);
                this.a(par1World, par3StructureBoundingBox, 10, 6, 2, 10, 6, 10, Block.FENCE.id, Block.FENCE.id, false);
                this.a(par1World, par3StructureBoundingBox, 4, 6, 2, 9, 6, 2, Block.FENCE.id, Block.FENCE.id, false);
                this.a(par1World, par3StructureBoundingBox, 4, 6, 12, 8, 6, 12, Block.FENCE.id, Block.FENCE.id, false);
                this.a(par1World, Block.FENCE.id, 0, 9, 6, 11, par3StructureBoundingBox);
                this.a(par1World, Block.FENCE.id, 0, 8, 6, 11, par3StructureBoundingBox);
                this.a(par1World, Block.FENCE.id, 0, 9, 6, 10, par3StructureBoundingBox);
                var7 = this.c(Block.LADDER.id, 3);
                this.a(par1World, Block.LADDER.id, var7, 10, 1, 13, par3StructureBoundingBox);
                this.a(par1World, Block.LADDER.id, var7, 10, 2, 13, par3StructureBoundingBox);
                this.a(par1World, Block.LADDER.id, var7, 10, 3, 13, par3StructureBoundingBox);
                this.a(par1World, Block.LADDER.id, var7, 10, 4, 13, par3StructureBoundingBox);
                this.a(par1World, Block.LADDER.id, var7, 10, 5, 13, par3StructureBoundingBox);
                this.a(par1World, Block.LADDER.id, var7, 10, 6, 13, par3StructureBoundingBox);
                this.a(par1World, Block.LADDER.id, var7, 10, 7, 13, par3StructureBoundingBox);
                byte var8 = 7;
                byte var9 = 7;
                this.a(par1World, Block.FENCE.id, 0, var8 - 1, 9, var9, par3StructureBoundingBox);
                this.a(par1World, Block.FENCE.id, 0, var8, 9, var9, par3StructureBoundingBox);
                this.a(par1World, Block.FENCE.id, 0, var8 - 1, 8, var9, par3StructureBoundingBox);
                this.a(par1World, Block.FENCE.id, 0, var8, 8, var9, par3StructureBoundingBox);
                this.a(par1World, Block.FENCE.id, 0, var8 - 1, 7, var9, par3StructureBoundingBox);
                this.a(par1World, Block.FENCE.id, 0, var8, 7, var9, par3StructureBoundingBox);
                this.a(par1World, Block.FENCE.id, 0, var8 - 2, 7, var9, par3StructureBoundingBox);
                this.a(par1World, Block.FENCE.id, 0, var8 + 1, 7, var9, par3StructureBoundingBox);
                this.a(par1World, Block.FENCE.id, 0, var8 - 1, 7, var9 - 1, par3StructureBoundingBox);
                this.a(par1World, Block.FENCE.id, 0, var8 - 1, 7, var9 + 1, par3StructureBoundingBox);
                this.a(par1World, Block.FENCE.id, 0, var8, 7, var9 - 1, par3StructureBoundingBox);
                this.a(par1World, Block.FENCE.id, 0, var8, 7, var9 + 1, par3StructureBoundingBox);
                this.a(par1World, Block.TORCH.id, 0, var8 - 2, 8, var9, par3StructureBoundingBox);
                this.a(par1World, Block.TORCH.id, 0, var8 + 1, 8, var9, par3StructureBoundingBox);
                this.a(par1World, Block.TORCH.id, 0, var8 - 1, 8, var9 - 1, par3StructureBoundingBox);
                this.a(par1World, Block.TORCH.id, 0, var8 - 1, 8, var9 + 1, par3StructureBoundingBox);
                this.a(par1World, Block.TORCH.id, 0, var8, 8, var9 - 1, par3StructureBoundingBox);
                this.a(par1World, Block.TORCH.id, 0, var8, 8, var9 + 1, par3StructureBoundingBox);
            }

            this.a(par1World, par3StructureBoundingBox, par2Random, 3, 3, 5, b, 1 + par2Random.nextInt(4));

            if (this.c)
            {
                this.a(par1World, 0, 0, 12, 9, 1, par3StructureBoundingBox);
                this.a(par1World, par3StructureBoundingBox, par2Random, 12, 8, 1, b, 1 + par2Random.nextInt(4));
            }

            return true;
        }
    }
}
