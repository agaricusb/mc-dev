package net.minecraft.server;

import java.util.Random;

public class BlockFurnace extends BlockContainer
{
    /**
     * Is the random generator used by furnace to drop the inventory contents in random directions.
     */
    private Random a = new Random();

    /** True if this is an active furnace, false if idle */
    private final boolean b;

    /**
     * This flag is used to prevent the furnace inventory to be dropped upon block removal, is used internally when the
     * furnace block changes from idle to active and vice-versa.
     */
    private static boolean c = false;

    protected BlockFurnace(int par1, boolean par2)
    {
        super(par1, Material.STONE);
        this.b = par2;
        this.textureId = 45;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Block.FURNACE.id;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        super.onPlace(par1World, par2, par3, par4);
        this.l(par1World, par2, par3, par4);
    }

    /**
     * set a blocks direction
     */
    private void l(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isStatic)
        {
            int var5 = par1World.getTypeId(par2, par3, par4 - 1);
            int var6 = par1World.getTypeId(par2, par3, par4 + 1);
            int var7 = par1World.getTypeId(par2 - 1, par3, par4);
            int var8 = par1World.getTypeId(par2 + 1, par3, par4);
            byte var9 = 3;

            if (Block.q[var5] && !Block.q[var6])
            {
                var9 = 3;
            }

            if (Block.q[var6] && !Block.q[var5])
            {
                var9 = 2;
            }

            if (Block.q[var7] && !Block.q[var8])
            {
                var9 = 5;
            }

            if (Block.q[var8] && !Block.q[var7])
            {
                var9 = 4;
            }

            par1World.setData(par2, par3, par4, var9);
        }
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return par1 == 1 ? this.textureId + 17 : (par1 == 0 ? this.textureId + 17 : (par1 == 3 ? this.textureId - 1 : this.textureId));
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isStatic)
        {
            return true;
        }
        else
        {
            TileEntityFurnace var10 = (TileEntityFurnace)par1World.getTileEntity(par2, par3, par4);

            if (var10 != null)
            {
                par5EntityPlayer.openFurnace(var10);
            }

            return true;
        }
    }

    /**
     * Update which block ID the furnace is using depending on whether or not it is burning
     */
    public static void a(boolean par0, World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getData(par2, par3, par4);
        TileEntity var6 = par1World.getTileEntity(par2, par3, par4);
        c = true;

        if (par0)
        {
            par1World.setTypeId(par2, par3, par4, Block.BURNING_FURNACE.id);
        }
        else
        {
            par1World.setTypeId(par2, par3, par4, Block.FURNACE.id);
        }

        c = false;
        par1World.setData(par2, par3, par4, var5);

        if (var6 != null)
        {
            var6.s();
            par1World.setTileEntity(par2, par3, par4, var6);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity a(World par1World)
    {
        return new TileEntityFurnace();
    }

    /**
     * Called when the block is placed in the world.
     */
    public void postPlace(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = MathHelper.floor((double) (par5EntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var6 == 0)
        {
            par1World.setData(par2, par3, par4, 2);
        }

        if (var6 == 1)
        {
            par1World.setData(par2, par3, par4, 5);
        }

        if (var6 == 2)
        {
            par1World.setData(par2, par3, par4, 3);
        }

        if (var6 == 3)
        {
            par1World.setData(par2, par3, par4, 4);
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!c)
        {
            TileEntityFurnace var7 = (TileEntityFurnace)par1World.getTileEntity(par2, par3, par4);

            if (var7 != null)
            {
                for (int var8 = 0; var8 < var7.getSize(); ++var8)
                {
                    ItemStack var9 = var7.getItem(var8);

                    if (var9 != null)
                    {
                        float var10 = this.a.nextFloat() * 0.8F + 0.1F;
                        float var11 = this.a.nextFloat() * 0.8F + 0.1F;
                        float var12 = this.a.nextFloat() * 0.8F + 0.1F;

                        while (var9.count > 0)
                        {
                            int var13 = this.a.nextInt(21) + 10;

                            if (var13 > var9.count)
                            {
                                var13 = var9.count;
                            }

                            var9.count -= var13;
                            EntityItem var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.id, var13, var9.getData()));

                            if (var9.hasTag())
                            {
                                var14.getItemStack().setTag((NBTTagCompound) var9.getTag().clone());
                            }

                            float var15 = 0.05F;
                            var14.motX = (double)((float)this.a.nextGaussian() * var15);
                            var14.motY = (double)((float)this.a.nextGaussian() * var15 + 0.2F);
                            var14.motZ = (double)((float)this.a.nextGaussian() * var15);
                            par1World.addEntity(var14);
                        }
                    }
                }
            }
        }

        super.remove(par1World, par2, par3, par4, par5, par6);
    }
}
