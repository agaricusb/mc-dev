package net.minecraft.server;

import java.util.Random;

public class BlockDispenser extends BlockContainer
{
    /** Registry for all dispense behaviors. */
    public static final IRegistry a = new RegistryDefault(new DispenseBehaviorItem());
    private Random b = new Random();

    protected BlockDispenser(int par1)
    {
        super(par1, Material.STONE);
        this.textureId = 45;
        this.a(CreativeModeTab.d);
    }

    /**
     * How many world ticks before ticking
     */
    public int r_()
    {
        return 4;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Block.DISPENSER.id;
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
     * sets Dispenser block direction so that the front faces an non-opaque block; chooses west to be direction if all
     * surrounding blocks are opaque.
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
        return par1 == 1 ? this.textureId + 17 : (par1 == 0 ? this.textureId + 17 : (par1 == 3 ? this.textureId + 1 : this.textureId));
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
            TileEntityDispenser var10 = (TileEntityDispenser)par1World.getTileEntity(par2, par3, par4);

            if (var10 != null)
            {
                par5EntityPlayer.openDispenser(var10);
            }

            return true;
        }
    }

    private void dispense(World par1World, int par2, int par3, int par4)
    {
        SourceBlock var5 = new SourceBlock(par1World, par2, par3, par4);
        TileEntityDispenser var6 = (TileEntityDispenser)var5.getTileEntity();

        if (var6 != null)
        {
            int var7 = var6.i();

            if (var7 < 0)
            {
                par1World.triggerEffect(1001, par2, par3, par4, 0);
            }
            else
            {
                ItemStack var8 = var6.getItem(var7);
                IDispenseBehavior var9 = (IDispenseBehavior) a.a(var8.getItem());

                if (var9 != IDispenseBehavior.a)
                {
                    ItemStack var10 = var9.a(var5, var8);
                    var6.setItem(var7, var10.count == 0 ? null : var10);
                }
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par5 > 0 && Block.byId[par5].isPowerSource())
        {
            boolean var6 = par1World.isBlockIndirectlyPowered(par2, par3, par4) || par1World.isBlockIndirectlyPowered(par2, par3 + 1, par4);

            if (var6)
            {
                par1World.a(par2, par3, par4, this.id, this.r_());
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isStatic && (par1World.isBlockIndirectlyPowered(par2, par3, par4) || par1World.isBlockIndirectlyPowered(par2, par3 + 1, par4)))
        {
            this.dispense(par1World, par2, par3, par4);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity a(World par1World)
    {
        return new TileEntityDispenser();
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
        TileEntityDispenser var7 = (TileEntityDispenser)par1World.getTileEntity(par2, par3, par4);

        if (var7 != null)
        {
            for (int var8 = 0; var8 < var7.getSize(); ++var8)
            {
                ItemStack var9 = var7.getItem(var8);

                if (var9 != null)
                {
                    float var10 = this.b.nextFloat() * 0.8F + 0.1F;
                    float var11 = this.b.nextFloat() * 0.8F + 0.1F;
                    float var12 = this.b.nextFloat() * 0.8F + 0.1F;

                    while (var9.count > 0)
                    {
                        int var13 = this.b.nextInt(21) + 10;

                        if (var13 > var9.count)
                        {
                            var13 = var9.count;
                        }

                        var9.count -= var13;
                        EntityItem var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.id, var13, var9.getData()));

                        if (var9.hasTag())
                        {
                            var14.itemStack.setTag((NBTTagCompound) var9.getTag().clone());
                        }

                        float var15 = 0.05F;
                        var14.motX = (double)((float)this.b.nextGaussian() * var15);
                        var14.motY = (double)((float)this.b.nextGaussian() * var15 + 0.2F);
                        var14.motZ = (double)((float)this.b.nextGaussian() * var15);
                        par1World.addEntity(var14);
                    }
                }
            }
        }

        super.remove(par1World, par2, par3, par4, par5, par6);
    }

    public static IPosition a(ISourceBlock par0IBlockSource)
    {
        EnumFacing var1 = EnumFacing.a(par0IBlockSource.h());
        double var2 = par0IBlockSource.getX() + 0.7D * (double)var1.c();
        double var4 = par0IBlockSource.getY();
        double var6 = par0IBlockSource.getZ() + 0.7D * (double)var1.e();
        return new Position(var2, var4, var6);
    }
}
