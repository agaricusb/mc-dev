package net.minecraft.server;

public class BlockJukeBox extends BlockContainer
{
    protected BlockJukeBox(int par1, int par2)
    {
        super(par1, par2, Material.WOOD);
        this.a(CreativeModeTab.c);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return this.textureId + (par1 == 1 ? 1 : 0);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.getData(par2, par3, par4) == 0)
        {
            return false;
        }
        else
        {
            this.dropRecord(par1World, par2, par3, par4);
            return true;
        }
    }

    /**
     * Insert the specified music disc in the jukebox at the given coordinates
     */
    public void a(World par1World, int par2, int par3, int par4, ItemStack par5ItemStack)
    {
        if (!par1World.isStatic)
        {
            TileEntityRecordPlayer var6 = (TileEntityRecordPlayer)par1World.getTileEntity(par2, par3, par4);

            if (var6 != null)
            {
                var6.record = par5ItemStack.cloneItemStack();
                var6.update();
                par1World.setData(par2, par3, par4, 1);
            }
        }
    }

    /**
     * Ejects the current record inside of the jukebox.
     */
    public void dropRecord(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isStatic)
        {
            TileEntityRecordPlayer var5 = (TileEntityRecordPlayer)par1World.getTileEntity(par2, par3, par4);

            if (var5 != null)
            {
                ItemStack var6 = var5.record;

                if (var6 != null)
                {
                    par1World.triggerEffect(1005, par2, par3, par4, 0);
                    par1World.a((String) null, par2, par3, par4);
                    var5.record = null;
                    var5.update();
                    par1World.setData(par2, par3, par4, 0);
                    float var7 = 0.7F;
                    double var8 = (double)(par1World.random.nextFloat() * var7) + (double)(1.0F - var7) * 0.5D;
                    double var10 = (double)(par1World.random.nextFloat() * var7) + (double)(1.0F - var7) * 0.2D + 0.6D;
                    double var12 = (double)(par1World.random.nextFloat() * var7) + (double)(1.0F - var7) * 0.5D;
                    ItemStack var14 = var6.cloneItemStack();
                    EntityItem var15 = new EntityItem(par1World, (double)par2 + var8, (double)par3 + var10, (double)par4 + var12, var14);
                    var15.pickupDelay = 10;
                    par1World.addEntity(var15);
                }
            }
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        this.dropRecord(par1World, par2, par3, par4);
        super.remove(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (!par1World.isStatic)
        {
            super.dropNaturally(par1World, par2, par3, par4, par5, par6, 0);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity a(World par1World)
    {
        return new TileEntityRecordPlayer();
    }
}
