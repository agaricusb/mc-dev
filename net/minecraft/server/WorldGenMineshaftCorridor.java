package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenMineshaftCorridor extends StructurePiece
{
    private final boolean a;
    private final boolean b;
    private boolean c;

    /**
     * A count of the different sections of this mine. The space between ceiling supports.
     */
    private int d;

    public WorldGenMineshaftCorridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.f = par4;
        this.e = par3StructureBoundingBox;
        this.a = par2Random.nextInt(3) == 0;
        this.b = !this.a && par2Random.nextInt(23) == 0;

        if (this.f != 2 && this.f != 0)
        {
            this.d = par3StructureBoundingBox.b() / 5;
        }
        else
        {
            this.d = par3StructureBoundingBox.d() / 5;
        }
    }

    public static StructureBoundingBox a(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
    {
        StructureBoundingBox var6 = new StructureBoundingBox(par2, par3, par4, par2, par3 + 2, par4);
        int var7;

        for (var7 = par1Random.nextInt(3) + 2; var7 > 0; --var7)
        {
            int var8 = var7 * 5;

            switch (par5)
            {
                case 0:
                    var6.d = par2 + 2;
                    var6.f = par4 + (var8 - 1);
                    break;

                case 1:
                    var6.a = par2 - (var8 - 1);
                    var6.f = par4 + 2;
                    break;

                case 2:
                    var6.d = par2 + 2;
                    var6.c = par4 - (var8 - 1);
                    break;

                case 3:
                    var6.d = par2 + (var8 - 1);
                    var6.f = par4 + 2;
            }

            if (StructurePiece.a(par0List, var6) == null)
            {
                break;
            }
        }

        return var7 > 0 ? var6 : null;
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece par1StructureComponent, List par2List, Random par3Random)
    {
        int var4 = this.c();
        int var5 = par3Random.nextInt(4);

        switch (this.f)
        {
            case 0:
                if (var5 <= 1)
                {
                    WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a, this.e.b - 1 + par3Random.nextInt(3), this.e.f + 1, this.f, var4);
                }
                else if (var5 == 2)
                {
                    WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a - 1, this.e.b - 1 + par3Random.nextInt(3), this.e.f - 3, 1, var4);
                }
                else
                {
                    WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.d + 1, this.e.b - 1 + par3Random.nextInt(3), this.e.f - 3, 3, var4);
                }

                break;

            case 1:
                if (var5 <= 1)
                {
                    WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a - 1, this.e.b - 1 + par3Random.nextInt(3), this.e.c, this.f, var4);
                }
                else if (var5 == 2)
                {
                    WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a, this.e.b - 1 + par3Random.nextInt(3), this.e.c - 1, 2, var4);
                }
                else
                {
                    WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a, this.e.b - 1 + par3Random.nextInt(3), this.e.f + 1, 0, var4);
                }

                break;

            case 2:
                if (var5 <= 1)
                {
                    WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a, this.e.b - 1 + par3Random.nextInt(3), this.e.c - 1, this.f, var4);
                }
                else if (var5 == 2)
                {
                    WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a - 1, this.e.b - 1 + par3Random.nextInt(3), this.e.c, 1, var4);
                }
                else
                {
                    WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.d + 1, this.e.b - 1 + par3Random.nextInt(3), this.e.c, 3, var4);
                }

                break;

            case 3:
                if (var5 <= 1)
                {
                    WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.d + 1, this.e.b - 1 + par3Random.nextInt(3), this.e.c, this.f, var4);
                }
                else if (var5 == 2)
                {
                    WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.d - 3, this.e.b - 1 + par3Random.nextInt(3), this.e.c - 1, 2, var4);
                }
                else
                {
                    WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.d - 3, this.e.b - 1 + par3Random.nextInt(3), this.e.f + 1, 0, var4);
                }
        }

        if (var4 < 8)
        {
            int var6;
            int var7;

            if (this.f != 2 && this.f != 0)
            {
                for (var6 = this.e.a + 3; var6 + 3 <= this.e.d; var6 += 5)
                {
                    var7 = par3Random.nextInt(5);

                    if (var7 == 0)
                    {
                        WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, var6, this.e.b, this.e.c - 1, 2, var4 + 1);
                    }
                    else if (var7 == 1)
                    {
                        WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, var6, this.e.b, this.e.f + 1, 0, var4 + 1);
                    }
                }
            }
            else
            {
                for (var6 = this.e.c + 3; var6 + 3 <= this.e.f; var6 += 5)
                {
                    var7 = par3Random.nextInt(5);

                    if (var7 == 0)
                    {
                        WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.a - 1, this.e.b, var6, 1, var4 + 1);
                    }
                    else if (var7 == 1)
                    {
                        WorldGenMineshaftPieces.a(par1StructureComponent, par2List, par3Random, this.e.d + 1, this.e.b, var6, 3, var4 + 1);
                    }
                }
            }
        }
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
            int var8 = this.d * 5 - 1;
            this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 2, 1, var8, 0, 0, false);
            this.a(par1World, par3StructureBoundingBox, par2Random, 0.8F, 0, 2, 0, 2, 2, var8, 0, 0, false);

            if (this.b)
            {
                this.a(par1World, par3StructureBoundingBox, par2Random, 0.6F, 0, 0, 0, 2, 1, var8, Block.WEB.id, 0, false);
            }

            int var9;
            int var10;
            int var11;

            for (var9 = 0; var9 < this.d; ++var9)
            {
                var10 = 2 + var9 * 5;
                this.a(par1World, par3StructureBoundingBox, 0, 0, var10, 0, 1, var10, Block.FENCE.id, 0, false);
                this.a(par1World, par3StructureBoundingBox, 2, 0, var10, 2, 1, var10, Block.FENCE.id, 0, false);

                if (par2Random.nextInt(4) == 0)
                {
                    this.a(par1World, par3StructureBoundingBox, 0, 2, var10, 0, 2, var10, Block.WOOD.id, 0, false);
                    this.a(par1World, par3StructureBoundingBox, 2, 2, var10, 2, 2, var10, Block.WOOD.id, 0, false);
                }
                else
                {
                    this.a(par1World, par3StructureBoundingBox, 0, 2, var10, 2, 2, var10, Block.WOOD.id, 0, false);
                }

                this.a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 0, 2, var10 - 1, Block.WEB.id, 0);
                this.a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 2, 2, var10 - 1, Block.WEB.id, 0);
                this.a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 0, 2, var10 + 1, Block.WEB.id, 0);
                this.a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 2, 2, var10 + 1, Block.WEB.id, 0);
                this.a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 0, 2, var10 - 2, Block.WEB.id, 0);
                this.a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 2, 2, var10 - 2, Block.WEB.id, 0);
                this.a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 0, 2, var10 + 2, Block.WEB.id, 0);
                this.a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 2, 2, var10 + 2, Block.WEB.id, 0);
                this.a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 1, 2, var10 - 1, Block.TORCH.id, 0);
                this.a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 1, 2, var10 + 1, Block.TORCH.id, 0);

                if (par2Random.nextInt(100) == 0)
                {
                    this.a(par1World, par3StructureBoundingBox, par2Random, 2, 0, var10 - 1, WorldGenMineshaftPieces.a(), 3 + par2Random.nextInt(4));
                }

                if (par2Random.nextInt(100) == 0)
                {
                    this.a(par1World, par3StructureBoundingBox, par2Random, 0, 0, var10 + 1, WorldGenMineshaftPieces.a(), 3 + par2Random.nextInt(4));
                }

                if (this.b && !this.c)
                {
                    var11 = this.a(0);
                    int var12 = var10 - 1 + par2Random.nextInt(3);
                    int var13 = this.a(1, var12);
                    var12 = this.b(1, var12);

                    if (par3StructureBoundingBox.b(var13, var11, var12))
                    {
                        this.c = true;
                        par1World.setTypeId(var13, var11, var12, Block.MOB_SPAWNER.id);
                        TileEntityMobSpawner var14 = (TileEntityMobSpawner)par1World.getTileEntity(var13, var11, var12);

                        if (var14 != null)
                        {
                            var14.a("CaveSpider");
                        }
                    }
                }
            }

            for (var9 = 0; var9 <= 2; ++var9)
            {
                for (var10 = 0; var10 <= var8; ++var10)
                {
                    var11 = this.a(par1World, var9, -1, var10, par3StructureBoundingBox);

                    if (var11 == 0)
                    {
                        this.a(par1World, Block.WOOD.id, 0, var9, -1, var10, par3StructureBoundingBox);
                    }
                }
            }

            if (this.a)
            {
                for (var9 = 0; var9 <= var8; ++var9)
                {
                    var10 = this.a(par1World, 1, -1, var9, par3StructureBoundingBox);

                    if (var10 > 0 && Block.q[var10])
                    {
                        this.a(par1World, par3StructureBoundingBox, par2Random, 0.7F, 1, 0, var9, Block.RAILS.id, this.c(Block.RAILS.id, 0));
                    }
                }
            }

            return true;
        }
    }
}
