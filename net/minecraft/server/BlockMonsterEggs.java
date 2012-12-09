package net.minecraft.server;

import java.util.Random;

public class BlockMonsterEggs extends Block
{
    /** Block names that can be a silverfish stone. */
    public static final String[] a = new String[] {"stone", "cobble", "brick"};

    public BlockMonsterEggs(int par1)
    {
        super(par1, 1, Material.CLAY);
        this.c(0.0F);
        this.a(CreativeModeTab.c);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return par2 == 1 ? Block.COBBLESTONE.textureId : (par2 == 2 ? Block.SMOOTH_BRICK.textureId : Block.STONE.textureId);
    }

    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void postBreak(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isStatic)
        {
            EntitySilverfish var6 = new EntitySilverfish(par1World);
            var6.setPositionRotation((double) par2 + 0.5D, (double) par3, (double) par4 + 0.5D, 0.0F, 0.0F);
            par1World.addEntity(var6);
            var6.aR();
        }

        super.postBreak(par1World, par2, par3, par4, par5);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 0;
    }

    /**
     * Gets the blockID of the block this block is pretending to be according to this block's metadata.
     */
    public static boolean e(int par0)
    {
        return par0 == Block.STONE.id || par0 == Block.COBBLESTONE.id || par0 == Block.SMOOTH_BRICK.id;
    }

    /**
     * Returns the metadata to use when a Silverfish hides in the block. Sets the block to BlockSilverfish with this
     * metadata. It changes the displayed texture client side to look like a normal block.
     */
    public static int f(int par0)
    {
        return par0 == Block.COBBLESTONE.id ? 1 : (par0 == Block.SMOOTH_BRICK.id ? 2 : 0);
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack f_(int par1)
    {
        Block var2 = Block.STONE;

        if (par1 == 1)
        {
            var2 = Block.COBBLESTONE;
        }

        if (par1 == 2)
        {
            var2 = Block.SMOOTH_BRICK;
        }

        return new ItemStack(var2);
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDropData(World par1World, int par2, int par3, int par4)
    {
        return par1World.getData(par2, par3, par4);
    }
}
