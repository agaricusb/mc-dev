package net.minecraft.server;

import java.util.Random;

public class WorldGenBonusChest extends WorldGenerator
{
    /**
     * Instance of WeightedRandomChestContent what will randomly generate items into the Bonus Chest.
     */
    private final StructurePieceTreasure[] a;

    /**
     * Value of this int will determine how much items gonna generate in Bonus Chest.
     */
    private final int b;

    public WorldGenBonusChest(StructurePieceTreasure[] par1ArrayOfWeightedRandomChestContent, int par2)
    {
        this.a = par1ArrayOfWeightedRandomChestContent;
        this.b = par2;
    }

    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var12;

        for (boolean var6 = false; ((var12 = par1World.getTypeId(par3, par4, par5)) == 0 || var12 == Block.LEAVES.id) && par4 > 1; --par4)
        {
            ;
        }

        if (par4 < 1)
        {
            return false;
        }
        else
        {
            ++par4;

            for (int var7 = 0; var7 < 4; ++var7)
            {
                int var8 = par3 + par2Random.nextInt(4) - par2Random.nextInt(4);
                int var9 = par4 + par2Random.nextInt(3) - par2Random.nextInt(3);
                int var10 = par5 + par2Random.nextInt(4) - par2Random.nextInt(4);

                if (par1World.isEmpty(var8, var9, var10) && par1World.v(var8, var9 - 1, var10))
                {
                    par1World.setTypeId(var8, var9, var10, Block.CHEST.id);
                    TileEntityChest var11 = (TileEntityChest)par1World.getTileEntity(var8, var9, var10);

                    if (var11 != null && var11 != null)
                    {
                        StructurePieceTreasure.a(par2Random, this.a, var11, this.b);
                    }

                    if (par1World.isEmpty(var8 - 1, var9, var10) && par1World.v(var8 - 1, var9 - 1, var10))
                    {
                        par1World.setTypeId(var8 - 1, var9, var10, Block.TORCH.id);
                    }

                    if (par1World.isEmpty(var8 + 1, var9, var10) && par1World.v(var8 - 1, var9 - 1, var10))
                    {
                        par1World.setTypeId(var8 + 1, var9, var10, Block.TORCH.id);
                    }

                    if (par1World.isEmpty(var8, var9, var10 - 1) && par1World.v(var8 - 1, var9 - 1, var10))
                    {
                        par1World.setTypeId(var8, var9, var10 - 1, Block.TORCH.id);
                    }

                    if (par1World.isEmpty(var8, var9, var10 + 1) && par1World.v(var8 - 1, var9 - 1, var10))
                    {
                        par1World.setTypeId(var8, var9, var10 + 1, Block.TORCH.id);
                    }

                    return true;
                }
            }

            return false;
        }
    }
}
