package net.minecraft.server;

public class BlockAnvil extends BlockSand
{
    /** List of types/statues the Anvil can be in. */
    public static final String[] a = new String[] {"intact", "slightlyDamaged", "veryDamaged"};
    public int b = 0;

    protected BlockAnvil(int par1)
    {
        super(par1, 215, Material.HEAVY);
        this.h(0);
        this.a(CreativeModeTab.c);
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
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
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        if (this.b == 3 && par1 == 1)
        {
            int var3 = par2 >> 2;

            switch (var3)
            {
                case 1:
                    return this.textureId + 1;

                case 2:
                    return this.textureId + 16 + 1;

                default:
                    return this.textureId + 16;
            }
        }
        else
        {
            return this.textureId;
        }
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return super.a(par1);
    }

    /**
     * Called when the block is placed in the world.
     */
    public void postPlace(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = MathHelper.floor((double) (par5EntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 3;
        int var7 = par1World.getData(par2, par3, par4) >> 2;
        ++var6;
        var6 %= 4;

        if (var6 == 0)
        {
            par1World.setData(par2, par3, par4, 2 | var7 << 2);
        }

        if (var6 == 1)
        {
            par1World.setData(par2, par3, par4, 3 | var7 << 2);
        }

        if (var6 == 2)
        {
            par1World.setData(par2, par3, par4, 0 | var7 << 2);
        }

        if (var6 == 3)
        {
            par1World.setData(par2, par3, par4, 1 | var7 << 2);
        }
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
            par5EntityPlayer.openAnvil(par2, par3, par4);
            return true;
        }
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 35;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int getDropData(int par1)
    {
        return par1 >> 2;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getData(par2, par3, par4) & 3;

        if (var5 != 3 && var5 != 1)
        {
            this.a(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
        }
        else
        {
            this.a(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
        }
    }

    /**
     * Called when the falling block entity for this block is created
     */
    protected void a(EntityFallingBlock par1EntityFallingSand)
    {
        par1EntityFallingSand.e(true);
    }

    /**
     * Called when the falling block entity for this block hits the ground and turns back into a block
     */
    public void a_(World par1World, int par2, int par3, int par4, int par5)
    {
        par1World.triggerEffect(1022, par2, par3, par4, 0);
    }
}
