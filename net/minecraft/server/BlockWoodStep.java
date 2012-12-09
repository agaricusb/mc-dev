package net.minecraft.server;

import java.util.Random;

public class BlockWoodStep extends BlockStepAbstract
{
    /** The type of tree this slab came from. */
    public static final String[] a = new String[] {"oak", "spruce", "birch", "jungle"};

    public BlockWoodStep(int par1, boolean par2)
    {
        super(par1, par2, Material.WOOD);
        this.a(CreativeModeTab.b);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        switch (par2 & 7)
        {
            case 1:
                return 198;

            case 2:
                return 214;

            case 3:
                return 199;

            default:
                return 4;
        }
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return this.a(par1, 0);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Block.WOOD_STEP.id;
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack f_(int par1)
    {
        return new ItemStack(Block.WOOD_STEP.id, 2, par1 & 7);
    }

    /**
     * Returns the slab block name with step type.
     */
    public String d(int par1)
    {
        if (par1 < 0 || par1 >= a.length)
        {
            par1 = 0;
        }

        return super.a() + "." + a[par1];
    }
}
