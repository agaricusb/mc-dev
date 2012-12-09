package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdStairs extends WorldGenStrongholdPiece
{
    private final WorldGenStrongholdDoorType a;
    private final boolean b;
    private final boolean c;

    public WorldGenStrongholdStairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.f = par4;
        this.a = this.a(par2Random);
        this.e = par3StructureBoundingBox;
        this.b = par2Random.nextInt(2) == 0;
        this.c = par2Random.nextInt(2) == 0;
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece par1StructureComponent, List par2List, Random par3Random)
    {
        this.a((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, 1, 1);

        if (this.b)
        {
            this.b((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, 1, 2);
        }

        if (this.c)
        {
            this.c((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, 1, 2);
        }
    }

    public static WorldGenStrongholdStairs a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -1, -1, 0, 5, 5, 7, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenStrongholdStairs(par6, par1Random, var7, par5) : null;
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
            this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 6, true, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par2Random, par3StructureBoundingBox, this.a, 1, 1, 0);
            this.a(par1World, par2Random, par3StructureBoundingBox, WorldGenStrongholdDoorType.a, 1, 1, 6);
            this.a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 1, 2, 1, Block.TORCH.id, 0);
            this.a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 3, 2, 1, Block.TORCH.id, 0);
            this.a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 1, 2, 5, Block.TORCH.id, 0);
            this.a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 3, 2, 5, Block.TORCH.id, 0);

            if (this.b)
            {
                this.a(par1World, par3StructureBoundingBox, 0, 1, 2, 0, 3, 4, 0, 0, false);
            }

            if (this.c)
            {
                this.a(par1World, par3StructureBoundingBox, 4, 1, 2, 4, 3, 4, 0, 0, false);
            }

            return true;
        }
    }
}
