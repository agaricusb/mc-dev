package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdLeftTurn extends WorldGenStrongholdPiece
{
    protected final WorldGenStrongholdDoorType a;

    public WorldGenStrongholdLeftTurn(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.f = par4;
        this.a = this.a(par2Random);
        this.e = par3StructureBoundingBox;
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece par1StructureComponent, List par2List, Random par3Random)
    {
        if (this.f != 2 && this.f != 3)
        {
            this.c((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, 1, 1);
        }
        else
        {
            this.b((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, 1, 1);
        }
    }

    public static WorldGenStrongholdLeftTurn a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -1, -1, 0, 5, 5, 5, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenStrongholdLeftTurn(par6, par1Random, var7, par5) : null;
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
            this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 4, true, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par2Random, par3StructureBoundingBox, this.a, 1, 1, 0);

            if (this.f != 2 && this.f != 3)
            {
                this.a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 3, 0, 0, false);
            }
            else
            {
                this.a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, 0, 0, false);
            }

            return true;
        }
    }
}
