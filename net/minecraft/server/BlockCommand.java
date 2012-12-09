package net.minecraft.server;

import java.util.Random;

public class BlockCommand extends BlockContainer
{
    public BlockCommand(int par1)
    {
        super(par1, 184, Material.ORE);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity a(World par1World)
    {
        return new TileEntityCommand();
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isStatic)
        {
            boolean var6 = par1World.isBlockIndirectlyPowered(par2, par3, par4);
            int var7 = par1World.getData(par2, par3, par4);
            boolean var8 = (var7 & 1) != 0;

            if (var6 && !var8)
            {
                par1World.setRawData(par2, par3, par4, var7 | 1);
                par1World.a(par2, par3, par4, this.id, this.r_());
            }
            else if (!var6 && var8)
            {
                par1World.setRawData(par2, par3, par4, var7 & -2);
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        TileEntity var6 = par1World.getTileEntity(par2, par3, par4);

        if (var6 != null && var6 instanceof TileEntityCommand)
        {
            ((TileEntityCommand)var6).a(par1World);
        }
    }

    /**
     * How many world ticks before ticking
     */
    public int r_()
    {
        return 1;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntityCommand var10 = (TileEntityCommand)par1World.getTileEntity(par2, par3, par4);

        if (var10 != null)
        {
            par5EntityPlayer.a(var10);
        }

        return true;
    }
}
