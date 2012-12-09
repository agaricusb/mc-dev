package net.minecraft.server;

import java.util.Random;

public class BlockSnow extends Block
{
    protected BlockSnow(int par1, int par2)
    {
        super(par1, par2, Material.SNOW_LAYER);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        this.b(true);
        this.a(CreativeModeTab.c);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getData(par2, par3, par4) & 7;
        return var5 >= 3 ? AxisAlignedBB.a().a((double) par2 + this.minX, (double) par3 + this.minY, (double) par4 + this.minZ, (double) par2 + this.maxX, (double) ((float) par3 + 0.5F), (double) par4 + this.maxZ) : null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean c()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getData(par2, par3, par4) & 7;
        float var6 = (float)(2 * (1 + var5)) / 16.0F;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, var6, 1.0F);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getTypeId(par2, par3 - 1, par4);
        return var5 != 0 && (var5 == Block.LEAVES.id || Block.byId[var5].c()) ? par1World.getMaterial(par2, par3 - 1, par4).isSolid() : false;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        this.n(par1World, par2, par3, par4);
    }

    /**
     * Checks if this snow block can stay at this location.
     */
    private boolean n(World par1World, int par2, int par3, int par4)
    {
        if (!this.canPlace(par1World, par2, par3, par4))
        {
            this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
            par1World.setTypeId(par2, par3, par4, 0);
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void a(World par1World, EntityHuman par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        int var7 = Item.SNOW_BALL.id;
        this.b(par1World, par3, par4, par5, new ItemStack(var7, 1, 0));
        par1World.setTypeId(par3, par4, par5, 0);
        par2EntityPlayer.a(StatisticList.C[this.id], 1);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Item.SNOW_BALL.id;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 0;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.b(EnumSkyBlock.BLOCK, par2, par3, par4) > 11)
        {
            this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
            par1World.setTypeId(par2, par3, par4, 0);
        }
    }
}
