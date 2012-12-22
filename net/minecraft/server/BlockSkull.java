package net.minecraft.server;

import java.util.Random;

public class BlockSkull extends BlockContainer
{
    protected BlockSkull(int par1)
    {
        super(par1, Material.ORIENTABLE);
        this.textureId = 104;
        this.a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return -1;
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

        switch (var5)
        {
            case 1:
            default:
                this.a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
                break;

            case 2:
                this.a(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
                break;

            case 3:
                this.a(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
                break;

            case 4:
                this.a(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
                break;

            case 5:
                this.a(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        this.updateShape(par1World, par2, par3, par4);
        return super.e(par1World, par2, par3, par4);
    }

    /**
     * Called when the block is placed in the world.
     */
    public void postPlace(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = MathHelper.floor((double) (par5EntityLiving.yaw * 4.0F / 360.0F) + 2.5D) & 3;
        par1World.setData(par2, par3, par4, var6);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity a(World par1World)
    {
        return new TileEntitySkull();
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDropData(World par1World, int par2, int par3, int par4)
    {
        TileEntity var5 = par1World.getTileEntity(par2, par3, par4);
        return var5 != null && var5 instanceof TileEntitySkull ? ((TileEntitySkull)var5).getSkullType() : super.getDropData(par1World, par2, par3, par4);
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int getDropData(int par1)
    {
        return par1;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {}

    /**
     * Called when the block is attempted to be harvested
     */
    public void a(World par1World, int par2, int par3, int par4, int par5, EntityHuman par6EntityPlayer)
    {
        if (par6EntityPlayer.abilities.canInstantlyBuild)
        {
            par5 |= 8;
            par1World.setData(par2, par3, par4, par5);
        }

        super.a(par1World, par2, par3, par4, par5, par6EntityPlayer);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!par1World.isStatic)
        {
            if ((par6 & 8) == 0)
            {
                ItemStack var7 = new ItemStack(Item.SKULL.id, 1, this.getDropData(par1World, par2, par3, par4));
                TileEntitySkull var8 = (TileEntitySkull)par1World.getTileEntity(par2, par3, par4);

                if (var8.getSkullType() == 3 && var8.getExtraType() != null && var8.getExtraType().length() > 0)
                {
                    var7.setTag(new NBTTagCompound());
                    var7.getTag().setString("SkullOwner", var8.getExtraType());
                }

                this.b(par1World, par2, par3, par4, var7);
            }

            super.remove(par1World, par2, par3, par4, par5, par6);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Item.SKULL.id;
    }

    /**
     * This method attempts to create a wither at the given location and skull
     */
    public void a(World par1World, int par2, int par3, int par4, TileEntitySkull par5TileEntitySkull)
    {
        if (par5TileEntitySkull.getSkullType() == 1 && par3 >= 2 && par1World.difficulty > 0)
        {
            int var6 = Block.SOUL_SAND.id;
            int var7;
            EntityWither var8;
            int var9;

            for (var7 = -2; var7 <= 0; ++var7)
            {
                if (par1World.getTypeId(par2, par3 - 1, par4 + var7) == var6 && par1World.getTypeId(par2, par3 - 1, par4 + var7 + 1) == var6 && par1World.getTypeId(par2, par3 - 2, par4 + var7 + 1) == var6 && par1World.getTypeId(par2, par3 - 1, par4 + var7 + 2) == var6 && this.d(par1World, par2, par3, par4 + var7, 1) && this.d(par1World, par2, par3, par4 + var7 + 1, 1) && this.d(par1World, par2, par3, par4 + var7 + 2, 1))
                {
                    par1World.setRawData(par2, par3, par4 + var7, 8);
                    par1World.setRawData(par2, par3, par4 + var7 + 1, 8);
                    par1World.setRawData(par2, par3, par4 + var7 + 2, 8);
                    par1World.setRawTypeId(par2, par3, par4 + var7, 0);
                    par1World.setRawTypeId(par2, par3, par4 + var7 + 1, 0);
                    par1World.setRawTypeId(par2, par3, par4 + var7 + 2, 0);
                    par1World.setRawTypeId(par2, par3 - 1, par4 + var7, 0);
                    par1World.setRawTypeId(par2, par3 - 1, par4 + var7 + 1, 0);
                    par1World.setRawTypeId(par2, par3 - 1, par4 + var7 + 2, 0);
                    par1World.setRawTypeId(par2, par3 - 2, par4 + var7 + 1, 0);

                    if (!par1World.isStatic)
                    {
                        var8 = new EntityWither(par1World);
                        var8.setPositionRotation((double) par2 + 0.5D, (double) par3 - 1.45D, (double) (par4 + var7) + 1.5D, 90.0F, 0.0F);
                        var8.ax = 90.0F;
                        var8.m();
                        par1World.addEntity(var8);
                    }

                    for (var9 = 0; var9 < 120; ++var9)
                    {
                        par1World.addParticle("snowballpoof", (double) par2 + par1World.random.nextDouble(), (double) (par3 - 2) + par1World.random.nextDouble() * 3.9D, (double) (par4 + var7 + 1) + par1World.random.nextDouble(), 0.0D, 0.0D, 0.0D);
                    }

                    par1World.update(par2, par3, par4 + var7, 0);
                    par1World.update(par2, par3, par4 + var7 + 1, 0);
                    par1World.update(par2, par3, par4 + var7 + 2, 0);
                    par1World.update(par2, par3 - 1, par4 + var7, 0);
                    par1World.update(par2, par3 - 1, par4 + var7 + 1, 0);
                    par1World.update(par2, par3 - 1, par4 + var7 + 2, 0);
                    par1World.update(par2, par3 - 2, par4 + var7 + 1, 0);
                    return;
                }
            }

            for (var7 = -2; var7 <= 0; ++var7)
            {
                if (par1World.getTypeId(par2 + var7, par3 - 1, par4) == var6 && par1World.getTypeId(par2 + var7 + 1, par3 - 1, par4) == var6 && par1World.getTypeId(par2 + var7 + 1, par3 - 2, par4) == var6 && par1World.getTypeId(par2 + var7 + 2, par3 - 1, par4) == var6 && this.d(par1World, par2 + var7, par3, par4, 1) && this.d(par1World, par2 + var7 + 1, par3, par4, 1) && this.d(par1World, par2 + var7 + 2, par3, par4, 1))
                {
                    par1World.setRawData(par2 + var7, par3, par4, 8);
                    par1World.setRawData(par2 + var7 + 1, par3, par4, 8);
                    par1World.setRawData(par2 + var7 + 2, par3, par4, 8);
                    par1World.setRawTypeId(par2 + var7, par3, par4, 0);
                    par1World.setRawTypeId(par2 + var7 + 1, par3, par4, 0);
                    par1World.setRawTypeId(par2 + var7 + 2, par3, par4, 0);
                    par1World.setRawTypeId(par2 + var7, par3 - 1, par4, 0);
                    par1World.setRawTypeId(par2 + var7 + 1, par3 - 1, par4, 0);
                    par1World.setRawTypeId(par2 + var7 + 2, par3 - 1, par4, 0);
                    par1World.setRawTypeId(par2 + var7 + 1, par3 - 2, par4, 0);

                    if (!par1World.isStatic)
                    {
                        var8 = new EntityWither(par1World);
                        var8.setPositionRotation((double) (par2 + var7) + 1.5D, (double) par3 - 1.45D, (double) par4 + 0.5D, 0.0F, 0.0F);
                        var8.m();
                        par1World.addEntity(var8);
                    }

                    for (var9 = 0; var9 < 120; ++var9)
                    {
                        par1World.addParticle("snowballpoof", (double) (par2 + var7 + 1) + par1World.random.nextDouble(), (double) (par3 - 2) + par1World.random.nextDouble() * 3.9D, (double) par4 + par1World.random.nextDouble(), 0.0D, 0.0D, 0.0D);
                    }

                    par1World.update(par2 + var7, par3, par4, 0);
                    par1World.update(par2 + var7 + 1, par3, par4, 0);
                    par1World.update(par2 + var7 + 2, par3, par4, 0);
                    par1World.update(par2 + var7, par3 - 1, par4, 0);
                    par1World.update(par2 + var7 + 1, par3 - 1, par4, 0);
                    par1World.update(par2 + var7 + 2, par3 - 1, par4, 0);
                    par1World.update(par2 + var7 + 1, par3 - 2, par4, 0);
                    return;
                }
            }
        }
    }

    private boolean d(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par1World.getTypeId(par2, par3, par4) != this.id)
        {
            return false;
        }
        else
        {
            TileEntity var6 = par1World.getTileEntity(par2, par3, par4);
            return var6 != null && var6 instanceof TileEntitySkull ? ((TileEntitySkull)var6).getSkullType() == par5 : false;
        }
    }
}
