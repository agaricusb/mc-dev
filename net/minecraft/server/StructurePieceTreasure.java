package net.minecraft.server;

import java.util.Random;

public class StructurePieceTreasure extends WeightedRandomChoice
{
    /** The Item/Block ID to generate in the Chest. */
    private ItemStack b = null;

    /** The minimum chance of item generating. */
    private int c;

    /** The maximum chance of item generating. */
    private int d;

    public StructurePieceTreasure(int par1, int par2, int par3, int par4, int par5)
    {
        super(par5);
        this.b = new ItemStack(par1, 1, par2);
        this.c = par3;
        this.d = par4;
    }

    public StructurePieceTreasure(ItemStack par1ItemStack, int par2, int par3, int par4)
    {
        super(par4);
        this.b = par1ItemStack;
        this.c = par2;
        this.d = par3;
    }

    /**
     * Generates the Chest contents.
     */
    public static void a(Random par0Random, StructurePieceTreasure[] par1ArrayOfWeightedRandomChestContent, TileEntityChest par2TileEntityChest, int par3)
    {
        for (int var4 = 0; var4 < par3; ++var4)
        {
            StructurePieceTreasure var5 = (StructurePieceTreasure) WeightedRandom.a(par0Random, par1ArrayOfWeightedRandomChestContent);
            int var6 = var5.c + par0Random.nextInt(var5.d - var5.c + 1);

            if (var5.b.getMaxStackSize() >= var6)
            {
                ItemStack var7 = var5.b.cloneItemStack();
                var7.count = var6;
                par2TileEntityChest.setItem(par0Random.nextInt(par2TileEntityChest.getSize()), var7);
            }
            else
            {
                for (int var9 = 0; var9 < var6; ++var9)
                {
                    ItemStack var8 = var5.b.cloneItemStack();
                    var8.count = 1;
                    par2TileEntityChest.setItem(par0Random.nextInt(par2TileEntityChest.getSize()), var8);
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
            int var6 = var5.c + par0Random.nextInt(var5.d - var5.c + 1);

            if (var5.b.getMaxStackSize() >= var6)
            {
                ItemStack var7 = var5.b.cloneItemStack();
                var7.count = var6;
                par2TileEntityDispenser.setItem(par0Random.nextInt(par2TileEntityDispenser.getSize()), var7);
            }
            else
            {
                for (int var9 = 0; var9 < var6; ++var9)
                {
                    ItemStack var8 = var5.b.cloneItemStack();
                    var8.count = 1;
                    par2TileEntityDispenser.setItem(par0Random.nextInt(par2TileEntityDispenser.getSize()), var8);
                }
            }
        }
    }

    public static StructurePieceTreasure[] a(StructurePieceTreasure[] par0ArrayOfWeightedRandomChestContent, StructurePieceTreasure... par1ArrayOfWeightedRandomChestContent)
    {
        StructurePieceTreasure[] var2 = new StructurePieceTreasure[par0ArrayOfWeightedRandomChestContent.length + par1ArrayOfWeightedRandomChestContent.length];
        int var3 = 0;

        for (int var4 = 0; var4 < par0ArrayOfWeightedRandomChestContent.length; ++var4)
        {
            var2[var3++] = par0ArrayOfWeightedRandomChestContent[var4];
        }

        StructurePieceTreasure[] var8 = par1ArrayOfWeightedRandomChestContent;
        int var5 = par1ArrayOfWeightedRandomChestContent.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            StructurePieceTreasure var7 = var8[var6];
            var2[var3++] = var7;
        }

        return var2;
    }
}
