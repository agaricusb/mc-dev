package net.minecraft.server;

import java.util.Random;

public class BlockMinecartTrack extends Block
{
    /** Power related rails have this field at true. */
    private final boolean a;

    /**
     * Returns true if the block at the coordinates of world passed is a valid rail block (current is rail, powered or
     * detector).
     */
    public static final boolean e_(World par0World, int par1, int par2, int par3)
    {
        int var4 = par0World.getTypeId(par1, par2, par3);
        return var4 == Block.RAILS.id || var4 == Block.GOLDEN_RAIL.id || var4 == Block.DETECTOR_RAIL.id;
    }

    /**
     * Return true if the parameter is a blockID for a valid rail block (current is rail, powered or detector).
     */
    public static final boolean e(int par0)
    {
        return par0 == Block.RAILS.id || par0 == Block.GOLDEN_RAIL.id || par0 == Block.DETECTOR_RAIL.id;
    }

    protected BlockMinecartTrack(int par1, int par2, boolean par3)
    {
        super(par1, par2, Material.ORIENTABLE);
        this.a = par3;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        this.a(CreativeModeTab.e);
    }

    /**
     * Returns true if the block is power related rail.
     */
    public boolean p()
    {
        return this.a;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        return null;
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
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition a(World par1World, int par2, int par3, int par4, Vec3D par5Vec3, Vec3D par6Vec3)
    {
        this.updateShape(par1World, par2, par3, par4);
        return super.a(par1World, par2, par3, par4, par5Vec3, par6Vec3);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getData(par2, par3, par4);

        if (var5 >= 2 && var5 <= 5)
        {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
        }
        else
        {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        if (this.a)
        {
            if (this.id == Block.GOLDEN_RAIL.id && (par2 & 8) == 0)
            {
                return this.textureId - 16;
            }
        }
        else if (par2 >= 6)
        {
            return this.textureId - 16;
        }

        return this.textureId;
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
        return 9;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 1;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return par1World.v(par2, par3 - 1, par4);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isStatic)
        {
            this.a(par1World, par2, par3, par4, true);

            if (this.id == Block.GOLDEN_RAIL.id)
            {
                this.doPhysics(par1World, par2, par3, par4, this.id);
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isStatic)
        {
            int var6 = par1World.getData(par2, par3, par4);
            int var7 = var6;

            if (this.a)
            {
                var7 = var6 & 7;
            }

            boolean var8 = false;

            if (!par1World.v(par2, par3 - 1, par4))
            {
                var8 = true;
            }

            if (var7 == 2 && !par1World.v(par2 + 1, par3, par4))
            {
                var8 = true;
            }

            if (var7 == 3 && !par1World.v(par2 - 1, par3, par4))
            {
                var8 = true;
            }

            if (var7 == 4 && !par1World.v(par2, par3, par4 - 1))
            {
                var8 = true;
            }

            if (var7 == 5 && !par1World.v(par2, par3, par4 + 1))
            {
                var8 = true;
            }

            if (var8)
            {
                this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
                par1World.setTypeId(par2, par3, par4, 0);
            }
            else if (this.id == Block.GOLDEN_RAIL.id)
            {
                boolean var9 = par1World.isBlockIndirectlyPowered(par2, par3, par4);
                var9 = var9 || this.a(par1World, par2, par3, par4, var6, true, 0) || this.a(par1World, par2, par3, par4, var6, false, 0);
                boolean var10 = false;

                if (var9 && (var6 & 8) == 0)
                {
                    par1World.setData(par2, par3, par4, var7 | 8);
                    var10 = true;
                }
                else if (!var9 && (var6 & 8) != 0)
                {
                    par1World.setData(par2, par3, par4, var7);
                    var10 = true;
                }

                if (var10)
                {
                    par1World.applyPhysics(par2, par3 - 1, par4, this.id);

                    if (var7 == 2 || var7 == 3 || var7 == 4 || var7 == 5)
                    {
                        par1World.applyPhysics(par2, par3 + 1, par4, this.id);
                    }
                }
            }
            else if (par5 > 0 && Block.byId[par5].isPowerSource() && !this.a && MinecartTrackLogic.a(new MinecartTrackLogic(this, par1World, par2, par3, par4)) == 3)
            {
                this.a(par1World, par2, par3, par4, false);
            }
        }
    }

    /**
     * Completely recalculates the track shape based on neighboring tracks
     */
    private void a(World par1World, int par2, int par3, int par4, boolean par5)
    {
        if (!par1World.isStatic)
        {
            (new MinecartTrackLogic(this, par1World, par2, par3, par4)).a(par1World.isBlockIndirectlyPowered(par2, par3, par4), par5);
        }
    }

    /**
     * Powered minecart rail is conductive like wire, so check for powered neighbors
     */
    private boolean a(World par1World, int par2, int par3, int par4, int par5, boolean par6, int par7)
    {
        if (par7 >= 8)
        {
            return false;
        }
        else
        {
            int var8 = par5 & 7;
            boolean var9 = true;

            switch (var8)
            {
                case 0:
                    if (par6)
                    {
                        ++par4;
                    }
                    else
                    {
                        --par4;
                    }

                    break;

                case 1:
                    if (par6)
                    {
                        --par2;
                    }
                    else
                    {
                        ++par2;
                    }

                    break;

                case 2:
                    if (par6)
                    {
                        --par2;
                    }
                    else
                    {
                        ++par2;
                        ++par3;
                        var9 = false;
                    }

                    var8 = 1;
                    break;

                case 3:
                    if (par6)
                    {
                        --par2;
                        ++par3;
                        var9 = false;
                    }
                    else
                    {
                        ++par2;
                    }

                    var8 = 1;
                    break;

                case 4:
                    if (par6)
                    {
                        ++par4;
                    }
                    else
                    {
                        --par4;
                        ++par3;
                        var9 = false;
                    }

                    var8 = 0;
                    break;

                case 5:
                    if (par6)
                    {
                        ++par4;
                        ++par3;
                        var9 = false;
                    }
                    else
                    {
                        --par4;
                    }

                    var8 = 0;
            }

            return this.a(par1World, par2, par3, par4, par6, par7, var8) ? true : var9 && this.a(par1World, par2, par3 - 1, par4, par6, par7, var8);
        }
    }

    /**
     * Returns true if the specified rail is passing power to its neighbor
     */
    private boolean a(World par1World, int par2, int par3, int par4, boolean par5, int par6, int par7)
    {
        int var8 = par1World.getTypeId(par2, par3, par4);

        if (var8 == Block.GOLDEN_RAIL.id)
        {
            int var9 = par1World.getData(par2, par3, par4);
            int var10 = var9 & 7;

            if (par7 == 1 && (var10 == 0 || var10 == 4 || var10 == 5))
            {
                return false;
            }

            if (par7 == 0 && (var10 == 1 || var10 == 2 || var10 == 3))
            {
                return false;
            }

            if ((var9 & 8) != 0)
            {
                if (par1World.isBlockIndirectlyPowered(par2, par3, par4))
                {
                    return true;
                }

                return this.a(par1World, par2, par3, par4, var9, par5, par6 + 1);
            }
        }

        return false;
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int q_()
    {
        return 0;
    }

    /**
     * Return true if the blocks passed is a power related rail.
     */
    static boolean a(BlockMinecartTrack par0BlockRail)
    {
        return par0BlockRail.a;
    }
}
