package net.minecraft.server;

import java.util.Random;

public class BlockDiode extends BlockDirectional
{
    /** The offsets for the two torches in redstone repeater blocks. */
    public static final double[] a = new double[] { -0.0625D, 0.0625D, 0.1875D, 0.3125D};

    /** The states in which the redstone repeater blocks can be. */
    private static final int[] b = new int[] {1, 2, 3, 4};

    /** Tells whether the repeater is powered or not */
    private final boolean c;

    protected BlockDiode(int par1, boolean par2)
    {
        super(par1, 6, Material.ORIENTABLE);
        this.c = par2;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return !par1World.v(par2, par3 - 1, par4) ? false : super.canPlace(par1World, par2, par3, par4);
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean d(World par1World, int par2, int par3, int par4)
    {
        return !par1World.v(par2, par3 - 1, par4) ? false : super.d(par1World, par2, par3, par4);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int var6 = par1World.getData(par2, par3, par4);
        boolean var7 = this.e(par1World, par2, par3, par4, var6);

        if (!var7)
        {
            boolean var8 = this.i(par1World, par2, par3, par4, var6);

            if (this.c && !var8)
            {
                par1World.setTypeIdAndData(par2, par3, par4, Block.DIODE_OFF.id, var6);
            }
            else if (!this.c)
            {
                par1World.setTypeIdAndData(par2, par3, par4, Block.DIODE_ON.id, var6);

                if (!var8)
                {
                    int var9 = (var6 & 12) >> 2;
                    par1World.a(par2, par3, par4, Block.DIODE_ON.id, b[var9] * 2);
                }
            }
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return par1 == 0 ? (this.c ? 99 : 115) : (par1 == 1 ? (this.c ? 147 : 131) : 5);
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 15;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return this.a(par1, 0);
    }

    /**
     * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
     * side
     */
    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return this.b(par1IBlockAccess, par2, par3, par4, par5);
    }

    /**
     * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
     * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
     * Y, Z, side
     */
    public boolean b(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (!this.c)
        {
            return false;
        }
        else
        {
            int var6 = e(par1IBlockAccess.getData(par2, par3, par4));
            return var6 == 0 && par5 == 3 ? true : (var6 == 1 && par5 == 4 ? true : (var6 == 2 && par5 == 2 ? true : var6 == 3 && par5 == 5));
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!this.d(par1World, par2, par3, par4))
        {
            this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
            par1World.setTypeId(par2, par3, par4, 0);
            par1World.applyPhysics(par2 + 1, par3, par4, this.id);
            par1World.applyPhysics(par2 - 1, par3, par4, this.id);
            par1World.applyPhysics(par2, par3, par4 + 1, this.id);
            par1World.applyPhysics(par2, par3, par4 - 1, this.id);
            par1World.applyPhysics(par2, par3 - 1, par4, this.id);
            par1World.applyPhysics(par2, par3 + 1, par4, this.id);
        }
        else
        {
            int var6 = par1World.getData(par2, par3, par4);
            boolean var7 = this.e(par1World, par2, par3, par4, var6);

            if (!var7)
            {
                boolean var8 = this.i(par1World, par2, par3, par4, var6);
                int var9 = (var6 & 12) >> 2;

                if (this.c && !var8 || !this.c && var8)
                {
                    byte var10 = 0;

                    if (this.d(par1World, par2, par3, par4, var6))
                    {
                        var10 = -1;
                    }

                    par1World.a(par2, par3, par4, this.id, b[var9] * 2, var10);
                }
            }
        }
    }

    private boolean i(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = e(par5);

        switch (var6)
        {
            case 0:
                return par1World.isBlockFaceIndirectlyPowered(par2, par3, par4 + 1, 3) || par1World.getTypeId(par2, par3, par4 + 1) == Block.REDSTONE_WIRE.id && par1World.getData(par2, par3, par4 + 1) > 0;

            case 1:
                return par1World.isBlockFaceIndirectlyPowered(par2 - 1, par3, par4, 4) || par1World.getTypeId(par2 - 1, par3, par4) == Block.REDSTONE_WIRE.id && par1World.getData(par2 - 1, par3, par4) > 0;

            case 2:
                return par1World.isBlockFaceIndirectlyPowered(par2, par3, par4 - 1, 2) || par1World.getTypeId(par2, par3, par4 - 1) == Block.REDSTONE_WIRE.id && par1World.getData(par2, par3, par4 - 1) > 0;

            case 3:
                return par1World.isBlockFaceIndirectlyPowered(par2 + 1, par3, par4, 5) || par1World.getTypeId(par2 + 1, par3, par4) == Block.REDSTONE_WIRE.id && par1World.getData(par2 + 1, par3, par4) > 0;

            default:
                return false;
        }
    }

    public boolean e(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        int var6 = e(par5);

        switch (var6)
        {
            case 0:
            case 2:
                return par1IBlockAccess.isBlockFacePowered(par2 - 1, par3, par4, 4) && c(par1IBlockAccess.getTypeId(par2 - 1, par3, par4)) || par1IBlockAccess.isBlockFacePowered(par2 + 1, par3, par4, 5) && c(par1IBlockAccess.getTypeId(par2 + 1, par3, par4));

            case 1:
            case 3:
                return par1IBlockAccess.isBlockFacePowered(par2, par3, par4 + 1, 3) && c(par1IBlockAccess.getTypeId(par2, par3, par4 + 1)) || par1IBlockAccess.isBlockFacePowered(par2, par3, par4 - 1, 2) && c(par1IBlockAccess.getTypeId(par2, par3, par4 - 1));

            default:
                return false;
        }
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        int var10 = par1World.getData(par2, par3, par4);
        int var11 = (var10 & 12) >> 2;
        var11 = var11 + 1 << 2 & 12;
        par1World.setData(par2, par3, par4, var11 | var10 & 3);
        return true;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean isPowerSource()
    {
        return true;
    }

    /**
     * Called when the block is placed in the world.
     */
    public void postPlace(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = ((MathHelper.floor((double) (par5EntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
        par1World.setData(par2, par3, par4, var6);
        boolean var7 = this.i(par1World, par2, par3, par4, var6);

        if (var7)
        {
            par1World.a(par2, par3, par4, this.id, 1);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        par1World.applyPhysics(par2 + 1, par3, par4, this.id);
        par1World.applyPhysics(par2 - 1, par3, par4, this.id);
        par1World.applyPhysics(par2, par3, par4 + 1, this.id);
        par1World.applyPhysics(par2, par3, par4 - 1, this.id);
        par1World.applyPhysics(par2, par3 - 1, par4, this.id);
        par1World.applyPhysics(par2, par3 + 1, par4, this.id);
    }

    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void postBreak(World par1World, int par2, int par3, int par4, int par5)
    {
        if (this.c)
        {
            par1World.applyPhysics(par2 + 1, par3, par4, this.id);
            par1World.applyPhysics(par2 - 1, par3, par4, this.id);
            par1World.applyPhysics(par2, par3, par4 + 1, this.id);
            par1World.applyPhysics(par2, par3, par4 - 1, this.id);
            par1World.applyPhysics(par2, par3 - 1, par4, this.id);
            par1World.applyPhysics(par2, par3 + 1, par4, this.id);
        }

        super.postBreak(par1World, par2, par3, par4, par5);
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
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Item.DIODE.id;
    }

    public static boolean c(int par0)
    {
        return par0 == Block.DIODE_ON.id || par0 == Block.DIODE_OFF.id;
    }

    public boolean d(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = e(par5);

        if (c(par1World.getTypeId(par2 - Direction.a[var6], par3, par4 - Direction.b[var6])))
        {
            int var7 = par1World.getData(par2 - Direction.a[var6], par3, par4 - Direction.b[var6]);
            int var8 = e(var7);
            return var8 != var6;
        }
        else
        {
            return false;
        }
    }
}
