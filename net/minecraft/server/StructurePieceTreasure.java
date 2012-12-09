package net.minecraft.server;

import java.util.Random;

public class StructurePieceTreasure extends WeightedRandomChoice
{
    /** The Item/Block ID to generate in the Chest. */
    private int b;

    /** The Item damage/metadata. */
    private int c;

    /** The minimum chance of item generating. */
    private int d;

    /** The maximum chance of item generating. */
    private int e;

    public StructurePieceTreasure(int par1, int par2, int par3, int par4, int par5)
    {
        super(par5);
        this.b = par1;
        this.c = par2;
        this.d = par3;
        this.e = par4;
    }

    /**
     * Generates the Chest contents.
     */
    public static void a(Random par0Random, StructurePieceTreasure[] par1ArrayOfWeightedRandomChestContent, TileEntityChest par2TileEntityChest, int par3)
    {
        for (int var4 = 0; var4 < par3; ++var4)
        {
            StructurePieceTreasure var5 = (StructurePieceTreasure) WeightedRandom.a(par0Random, par1ArrayOfWeightedRandomChestContent);
            int var6 = var5.d + par0Random.nextInt(var5.e - var5.d + 1);

            if (Item.byId[var5.b].getMaxStackSize() >= var6)
            {
                par2TileEntityChest.setItem(par0Random.nextInt(par2TileEntityChest.getSize()), new ItemStack(var5.b, var6, var5.c));
            }
            else
            {
                for (int var7 = 0; var7 < var6; ++var7)
                {
                    par2TileEntityChest.setItem(par0Random.nextInt(par2TileEntityChest.getSize()), new ItemStack(var5.b, 1, var5.c));
                }
            }
        }
    }

    /**
     * Generates the Dispenser contents.
     */
    public static void a(Random par0Random, StructurePieceTreasure[] par1ArrayOfWeightedRandomChestContent, TileEntityDispenser par2TileEntityDispenser, int par3)
    {
        for (int var4 = 0; var4 < par3; ++var4)
        {
            StructurePieceTreasure var5 = (StructurePieceTreasure) WeightedRandom.a(par0Random, par1ArrayOfWeightedRandomChestContent);
            int var6 = var5.d + par0Random.nextInt(var5.e - var5.d + 1);

            if (Item.byId[var5.b].getMaxStackSize() >= var6)
            {
                par2TileEntityDispenser.setItem(par0Random.nextInt(par2TileEntityDispenser.getSize()), new ItemStack(var5.b, var6, var5.c));
            }
            else
            {
                for (int var7 = 0; var7 < var6; ++var7)
                {
                    par2TileEntityDispenser.setItem(par0Random.nextInt(par2TileEntityDispenser.getSize()), new ItemStack(var5.b, 1, var5.c));
                }
            }
        }
    }
}
