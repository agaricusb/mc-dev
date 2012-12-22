package net.minecraft.server;

import java.util.Iterator;
import java.util.Random;

public class BlockChest extends BlockContainer
{
    private Random a = new Random();

    protected BlockChest(int par1)
    {
        super(par1, Material.WOOD);
        this.textureId = 26;
        this.a(CreativeModeTab.c);
        this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
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
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 22;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        if (par1IBlockAccess.getTypeId(par2, par3, par4 - 1) == this.id)
        {
            this.a(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
        }
        else if (par1IBlockAccess.getTypeId(par2, par3, par4 + 1) == this.id)
        {
            this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
        }
        else if (par1IBlockAccess.getTypeId(par2 - 1, par3, par4) == this.id)
        {
            this.a(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        }
        else if (par1IBlockAccess.getTypeId(par2 + 1, par3, par4) == this.id)
        {
            this.a(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
        }
        else
        {
            this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        super.onPlace(par1World, par2, par3, par4);
        this.d_(par1World, par2, par3, par4);
        int var5 = par1World.getTypeId(par2, par3, par4 - 1);
        int var6 = par1World.getTypeId(par2, par3, par4 + 1);
        int var7 = par1World.getTypeId(par2 - 1, par3, par4);
        int var8 = par1World.getTypeId(par2 + 1, par3, par4);

        if (var5 == this.id)
        {
            this.d_(par1World, par2, par3, par4 - 1);
        }

        if (var6 == this.id)
        {
            this.d_(par1World, par2, par3, par4 + 1);
        }

        if (var7 == this.id)
        {
            this.d_(par1World, par2 - 1, par3, par4);
        }

        if (var8 == this.id)
        {
            this.d_(par1World, par2 + 1, par3, par4);
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    public void postPlace(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = par1World.getTypeId(par2, par3, par4 - 1);
        int var7 = par1World.getTypeId(par2, par3, par4 + 1);
        int var8 = par1World.getTypeId(par2 - 1, par3, par4);
        int var9 = par1World.getTypeId(par2 + 1, par3, par4);
        byte var10 = 0;
        int var11 = MathHelper.floor((double) (par5EntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var11 == 0)
        {
            var10 = 2;
        }

        if (var11 == 1)
        {
            var10 = 5;
        }

        if (var11 == 2)
        {
            var10 = 3;
        }

        if (var11 == 3)
        {
            var10 = 4;
        }

        if (var6 != this.id && var7 != this.id && var8 != this.id && var9 != this.id)
        {
            par1World.setData(par2, par3, par4, var10);
        }
        else
        {
            if ((var6 == this.id || var7 == this.id) && (var10 == 4 || var10 == 5))
            {
                if (var6 == this.id)
                {
                    par1World.setData(par2, par3, par4 - 1, var10);
                }
                else
                {
                    par1World.setData(par2, par3, par4 + 1, var10);
                }

                par1World.setData(par2, par3, par4, var10);
            }

            if ((var8 == this.id || var9 == this.id) && (var10 == 2 || var10 == 3))
            {
                if (var8 == this.id)
                {
                    par1World.setData(par2 - 1, par3, par4, var10);
                }
                else
                {
                    par1World.setData(par2 + 1, par3, par4, var10);
                }

                par1World.setData(par2, par3, par4, var10);
            }
        }
    }

    /**
     * Turns the adjacent chests to a double chest.
     */
    public void d_(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isStatic)
        {
            int var5 = par1World.getTypeId(par2, par3, par4 - 1);
            int var6 = par1World.getTypeId(par2, par3, par4 + 1);
            int var7 = par1World.getTypeId(par2 - 1, par3, par4);
            int var8 = par1World.getTypeId(par2 + 1, par3, par4);
            boolean var9 = true;
            int var10;
            int var11;
            boolean var12;
            byte var13;
            int var14;

            if (var5 != this.id && var6 != this.id)
            {
                if (var7 != this.id && var8 != this.id)
                {
                    var13 = 3;

                    if (Block.q[var5] && !Block.q[var6])
                    {
                        var13 = 3;
                    }

                    if (Block.q[var6] && !Block.q[var5])
                    {
                        var13 = 2;
                    }

                    if (Block.q[var7] && !Block.q[var8])
                    {
                        var13 = 5;
                    }

                    if (Block.q[var8] && !Block.q[var7])
                    {
                        var13 = 4;
                    }
                }
                else
                {
                    var10 = par1World.getTypeId(var7 == this.id ? par2 - 1 : par2 + 1, par3, par4 - 1);
                    var11 = par1World.getTypeId(var7 == this.id ? par2 - 1 : par2 + 1, par3, par4 + 1);
                    var13 = 3;
                    var12 = true;

                    if (var7 == this.id)
                    {
                        var14 = par1World.getData(par2 - 1, par3, par4);
                    }
                    else
                    {
                        var14 = par1World.getData(par2 + 1, par3, par4);
                    }

                    if (var14 == 2)
                    {
                        var13 = 2;
                    }

                    if ((Block.q[var5] || Block.q[var10]) && !Block.q[var6] && !Block.q[var11])
                    {
                        var13 = 3;
                    }

                    if ((Block.q[var6] || Block.q[var11]) && !Block.q[var5] && !Block.q[var10])
                    {
                        var13 = 2;
                    }
                }
            }
            else
            {
                var10 = par1World.getTypeId(par2 - 1, par3, var5 == this.id ? par4 - 1 : par4 + 1);
                var11 = par1World.getTypeId(par2 + 1, par3, var5 == this.id ? par4 - 1 : par4 + 1);
                var13 = 5;
                var12 = true;

                if (var5 == this.id)
                {
                    var14 = par1World.getData(par2, par3, par4 - 1);
                }
                else
                {
                    var14 = par1World.getData(par2, par3, par4 + 1);
                }

                if (var14 == 4)
                {
                    var13 = 4;
                }

                if ((Block.q[var7] || Block.q[var10]) && !Block.q[var8] && !Block.q[var11])
                {
                    var13 = 5;
                }

                if ((Block.q[var8] || Block.q[var11]) && !Block.q[var7] && !Block.q[var10])
                {
                    var13 = 4;
                }
            }

            par1World.setData(par2, par3, par4, var13);
        }
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return 4;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        int var5 = 0;

        if (par1World.getTypeId(par2 - 1, par3, par4) == this.id)
        {
            ++var5;
        }

        if (par1World.getTypeId(par2 + 1, par3, par4) == this.id)
        {
            ++var5;
        }

        if (par1World.getTypeId(par2, par3, par4 - 1) == this.id)
        {
            ++var5;
        }

        if (par1World.getTypeId(par2, par3, par4 + 1) == this.id)
        {
            ++var5;
        }

        return var5 > 1 ? false : (this.l(par1World, par2 - 1, par3, par4) ? false : (this.l(par1World, par2 + 1, par3, par4) ? false : (this.l(par1World, par2, par3, par4 - 1) ? false : !this.l(par1World, par2, par3, par4 + 1))));
    }

    /**
     * Checks the neighbor blocks to see if there is a chest there. Args: world, x, y, z
     */
    private boolean l(World par1World, int par2, int par3, int par4)
    {
        return par1World.getTypeId(par2, par3, par4) != this.id ? false : (par1World.getTypeId(par2 - 1, par3, par4) == this.id ? true : (par1World.getTypeId(par2 + 1, par3, par4) == this.id ? true : (par1World.getTypeId(par2, par3, par4 - 1) == this.id ? true : par1World.getTypeId(par2, par3, par4 + 1) == this.id)));
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        super.doPhysics(par1World, par2, par3, par4, par5);
        TileEntityChest var6 = (TileEntityChest)par1World.getTileEntity(par2, par3, par4);

        if (var6 != null)
        {
            var6.h();
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        TileEntityChest var7 = (TileEntityChest)par1World.getTileEntity(par2, par3, par4);

        if (var7 != null)
        {
            for (int var8 = 0; var8 < var7.getSize(); ++var8)
            {
                ItemStack var9 = var7.getItem(var8);

                if (var9 != null)
                {
                    float var10 = this.a.nextFloat() * 0.8F + 0.1F;
                    float var11 = this.a.nextFloat() * 0.8F + 0.1F;
                    EntityItem var14;

                    for (float var12 = this.a.nextFloat() * 0.8F + 0.1F; var9.count > 0; par1World.addEntity(var14))
                    {
                        int var13 = this.a.nextInt(21) + 10;

                        if (var13 > var9.count)
                        {
                            var13 = var9.count;
                        }

                        var9.count -= var13;
                        var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.id, var13, var9.getData()));
                        float var15 = 0.05F;
                        var14.motX = (double)((float)this.a.nextGaussian() * var15);
                        var14.motY = (double)((float)this.a.nextGaussian() * var15 + 0.2F);
                        var14.motZ = (double)((float)this.a.nextGaussian() * var15);

                        if (var9.hasTag())
                        {
                            var14.getItemStack().setTag((NBTTagCompound) var9.getTag().clone());
                        }
                    }
                }
            }
        }

        super.remove(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        Object var10 = (TileEntityChest)par1World.getTileEntity(par2, par3, par4);

        if (var10 == null)
        {
            return true;
        }
        else if (par1World.t(par2, par3 + 1, par4))
        {
            return true;
        }
        else if (n(par1World, par2, par3, par4))
        {
            return true;
        }
        else if (par1World.getTypeId(par2 - 1, par3, par4) == this.id && (par1World.t(par2 - 1, par3 + 1, par4) || n(par1World, par2 - 1, par3, par4)))
        {
            return true;
        }
        else if (par1World.getTypeId(par2 + 1, par3, par4) == this.id && (par1World.t(par2 + 1, par3 + 1, par4) || n(par1World, par2 + 1, par3, par4)))
        {
            return true;
        }
        else if (par1World.getTypeId(par2, par3, par4 - 1) == this.id && (par1World.t(par2, par3 + 1, par4 - 1) || n(par1World, par2, par3, par4 - 1)))
        {
            return true;
        }
        else if (par1World.getTypeId(par2, par3, par4 + 1) == this.id && (par1World.t(par2, par3 + 1, par4 + 1) || n(par1World, par2, par3, par4 + 1)))
        {
            return true;
        }
        else
        {
            if (par1World.getTypeId(par2 - 1, par3, par4) == this.id)
            {
                var10 = new InventoryLargeChest("container.chestDouble", (TileEntityChest)par1World.getTileEntity(par2 - 1, par3, par4), (IInventory)var10);
            }

            if (par1World.getTypeId(par2 + 1, par3, par4) == this.id)
            {
                var10 = new InventoryLargeChest("container.chestDouble", (IInventory)var10, (TileEntityChest)par1World.getTileEntity(par2 + 1, par3, par4));
            }

            if (par1World.getTypeId(par2, par3, par4 - 1) == this.id)
            {
                var10 = new InventoryLargeChest("container.chestDouble", (TileEntityChest)par1World.getTileEntity(par2, par3, par4 - 1), (IInventory)var10);
            }

            if (par1World.getTypeId(par2, par3, par4 + 1) == this.id)
            {
                var10 = new InventoryLargeChest("container.chestDouble", (IInventory)var10, (TileEntityChest)par1World.getTileEntity(par2, par3, par4 + 1));
            }

            if (par1World.isStatic)
            {
                return true;
            }
            else
            {
                par5EntityPlayer.openContainer((IInventory) var10);
                return true;
            }
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity a(World par1World)
    {
        return new TileEntityChest();
    }

    /**
     * Looks for a sitting ocelot within certain bounds. Such an ocelot is considered to be blocking access to the
     * chest.
     */
    private static boolean n(World par0World, int par1, int par2, int par3)
    {
        Iterator var4 = par0World.a(EntityOcelot.class, AxisAlignedBB.a().a((double) par1, (double) (par2 + 1), (double) par3, (double) (par1 + 1), (double) (par2 + 2), (double) (par3 + 1))).iterator();
        EntityOcelot var6;

        do
        {
            if (!var4.hasNext())
            {
                return false;
            }

            EntityOcelot var5 = (EntityOcelot)var4.next();
            var6 = (EntityOcelot)var5;
        }
        while (!var6.isSitting());

        return true;
    }
}
