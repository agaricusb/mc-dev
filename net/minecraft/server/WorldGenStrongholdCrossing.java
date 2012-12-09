package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdCrossing extends WorldGenStrongholdPiece
{
    protected final WorldGenStrongholdDoorType a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean h;

    public WorldGenStrongholdCrossing(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.f = par4;
        this.a = this.a(par2Random);
        this.e = par3StructureBoundingBox;
        this.b = par2Random.nextBoolean();
        this.c = par2Random.nextBoolean();
        this.d = par2Random.nextBoolean();
        this.h = par2Random.nextInt(3) > 0;
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece par1StructureComponent, List par2List, Random par3Random)
    {
        int var4 = 3;
        int var5 = 5;

        if (this.f == 1 || this.f == 2)
        {
            var4 = 8 - var4;
            var5 = 8 - var5;
        }

        this.a((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, 5, 1);

        if (this.b)
        {
            this.b((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, var4, 1);
        }

        if (this.c)
        {
            this.b((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, var5, 7);
        }

        if (this.d)
        {
            this.c((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, var4, 1);
        }

        if (this.h)
        {
            this.c((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, var5, 7);
        }
    }

    public static WorldGenStrongholdCrossing a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -4, -3, 0, 10, 9, 11, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenStrongholdCrossing(par6, par1Random, var7, par5) : null;
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
            this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 9, 8, 10, true, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par2Random, par3StructureBoundingBox, this.a, 4, 3, 0);

            if (this.b)
            {
                this.a(par1World, par3StructureBoundingBox, 0, 3, 1, 0, 5, 3, 0, 0, false);
            }

            if (this.d)
            {
                this.a(par1World, par3StructureBoundingBox, 9, 3, 1, 9, 5, 3, 0, 0, false);
            }

            if (this.c)
            {
                this.a(par1World, par3StructureBoundingBox, 0, 5, 7, 0, 7, 9, 0, 0, false);
            }

            if (this.h)
            {
                this.a(par1World, par3StructureBoundingBox, 9, 5, 7, 9, 7, 9, 0, 0, false);
            }

            this.a(par1World, par3StructureBoundingBox, 5, 1, 10, 7, 3, 10, 0, 0, false);
            this.a(par1World, par3StructureBoundingBox, 1, 2, 1, 8, 2, 6, false, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par3StructureBoundingBox, 4, 1, 5, 4, 4, 9, false, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par3StructureBoundingBox, 8, 1, 5, 8, 4, 9, false, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par3StructureBoundingBox, 1, 4, 7, 3, 4, 9, false, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par3StructureBoundingBox, 1, 3, 5, 3, 3, 6, false, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par3StructureBoundingBox, 1, 3, 4, 3, 3, 4, Block.STEP.id, Block.STEP.id, false);
            this.a(par1World, par3StructureBoundingBox, 1, 4, 6, 3, 4, 6, Block.STEP.id, Block.STEP.id, false);
            this.a(par1World, par3StructureBoundingBox, 5, 1, 7, 7, 1, 8, false, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par3StructureBoundingBox, 5, 1, 9, 7, 1, 9, Block.STEP.id, Block.STEP.id, false);
            this.a(par1World, par3StructureBoundingBox, 5, 2, 7, 7, 2, 7, Block.STEP.id, Block.STEP.id, false);
            this.a(par1World, par3StructureBoundingBox, 4, 5, 7, 4, 5, 9, Block.STEP.id, Block.STEP.id, false);
            this.a(par1World, par3StructureBoundingBox, 8, 5, 7, 8, 5, 9, Block.STEP.id, Block.STEP.id, false);
            this.a(par1World, par3StructureBoundingBox, 5, 5, 7, 7, 5, 9, Block.DOUBLE_STEP.id, Block.DOUBLE_STEP.id, false);
            this.a(par1World, Block.TORCH.id, 0, 6, 5, 6, par3StructureBoundingBox);
            return true;
        }
    }
}
