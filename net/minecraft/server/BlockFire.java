package net.minecraft.server;

import java.util.Random;

public class BlockFire extends Block
{
    /** The chance this block will encourage nearby blocks to catch on fire */
    private int[] a = new int[256];

    /**
     * This is an array indexed by block ID the larger the number in the array the more likely a block type will catch
     * fires
     */
    private int[] b = new int[256];

    protected BlockFire(int par1, int par2)
    {
        super(par1, par2, Material.FIRE);
        this.b(true);
    }

    /**
     * This method is called on a block after all other blocks gets already created. You can use it to reference and
     * configure something on the block that needs the others ones.
     */
    public void t_()
    {
        this.a(Block.WOOD.id, 5, 20);
        this.a(Block.WOOD_DOUBLE_STEP.id, 5, 20);
        this.a(Block.WOOD_STEP.id, 5, 20);
        this.a(Block.FENCE.id, 5, 20);
        this.a(Block.WOOD_STAIRS.id, 5, 20);
        this.a(Block.BIRCH_WOOD_STAIRS.id, 5, 20);
        this.a(Block.SPRUCE_WOOD_STAIRS.id, 5, 20);
        this.a(Block.JUNGLE_WOOD_STAIRS.id, 5, 20);
        this.a(Block.LOG.id, 5, 5);
        this.a(Block.LEAVES.id, 30, 60);
        this.a(Block.BOOKSHELF.id, 30, 20);
        this.a(Block.TNT.id, 15, 100);
        this.a(Block.LONG_GRASS.id, 60, 100);
        this.a(Block.WOOL.id, 30, 60);
        this.a(Block.VINE.id, 15, 100);
    }

    /**
     * Sets the burn rate for a block. The larger abilityToCatchFire the more easily it will catch. The larger
     * chanceToEncourageFire the faster it will burn and spread to other blocks. Args: blockID, chanceToEncourageFire,
     * abilityToCatchFire
     */
    private void a(int par1, int par2, int par3)
    {
        this.a[par1] = par2;
        this.b[par1] = par3;
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
        return 3;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 0;
    }

    /**
     * How many world ticks before ticking
     */
    public int r_()
    {
        return 30;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.getGameRules().getBoolean("doFireTick"))
        {
            boolean var6 = par1World.getTypeId(par2, par3 - 1, par4) == Block.NETHERRACK.id;

            if (par1World.worldProvider instanceof WorldProviderTheEnd && par1World.getTypeId(par2, par3 - 1, par4) == Block.BEDROCK.id)
            {
                var6 = true;
            }

            if (!this.canPlace(par1World, par2, par3, par4))
            {
                par1World.setTypeId(par2, par3, par4, 0);
            }

            if (!var6 && par1World.N() && (par1World.D(par2, par3, par4) || par1World.D(par2 - 1, par3, par4) || par1World.D(par2 + 1, par3, par4) || par1World.D(par2, par3, par4 - 1) || par1World.D(par2, par3, par4 + 1)))
            {
                par1World.setTypeId(par2, par3, par4, 0);
            }
            else
            {
                int var7 = par1World.getData(par2, par3, par4);

                if (var7 < 15)
                {
                    par1World.setRawData(par2, par3, par4, var7 + par5Random.nextInt(3) / 2);
                }

                par1World.a(par2, par3, par4, this.id, this.r_() + par5Random.nextInt(10));

                if (!var6 && !this.l(par1World, par2, par3, par4))
                {
                    if (!par1World.v(par2, par3 - 1, par4) || var7 > 3)
                    {
                        par1World.setTypeId(par2, par3, par4, 0);
                    }
                }
                else if (!var6 && !this.d(par1World, par2, par3 - 1, par4) && var7 == 15 && par5Random.nextInt(4) == 0)
                {
                    par1World.setTypeId(par2, par3, par4, 0);
                }
                else
                {
                    boolean var8 = par1World.E(par2, par3, par4);
                    byte var9 = 0;

                    if (var8)
                    {
                        var9 = -50;
                    }

                    this.a(par1World, par2 + 1, par3, par4, 300 + var9, par5Random, var7);
                    this.a(par1World, par2 - 1, par3, par4, 300 + var9, par5Random, var7);
                    this.a(par1World, par2, par3 - 1, par4, 250 + var9, par5Random, var7);
                    this.a(par1World, par2, par3 + 1, par4, 250 + var9, par5Random, var7);
                    this.a(par1World, par2, par3, par4 - 1, 300 + var9, par5Random, var7);
                    this.a(par1World, par2, par3, par4 + 1, 300 + var9, par5Random, var7);

                    for (int var10 = par2 - 1; var10 <= par2 + 1; ++var10)
                    {
                        for (int var11 = par4 - 1; var11 <= par4 + 1; ++var11)
                        {
                            for (int var12 = par3 - 1; var12 <= par3 + 4; ++var12)
                            {
                                if (var10 != par2 || var12 != par3 || var11 != par4)
                                {
                                    int var13 = 100;

                                    if (var12 > par3 + 1)
                                    {
                                        var13 += (var12 - (par3 + 1)) * 100;
                                    }

                                    int var14 = this.n(par1World, var10, var12, var11);

                                    if (var14 > 0)
                                    {
                                        int var15 = (var14 + 40 + par1World.difficulty * 7) / (var7 + 30);

                                        if (var8)
                                        {
                                            var15 /= 2;
                                        }

                                        if (var15 > 0 && par5Random.nextInt(var13) <= var15 && (!par1World.N() || !par1World.D(var10, var12, var11)) && !par1World.D(var10 - 1, var12, par4) && !par1World.D(var10 + 1, var12, var11) && !par1World.D(var10, var12, var11 - 1) && !par1World.D(var10, var12, var11 + 1))
                                        {
                                            int var16 = var7 + par5Random.nextInt(5) / 4;

                                            if (var16 > 15)
                                            {
                                                var16 = 15;
                                            }

                                            par1World.setTypeIdAndData(var10, var12, var11, this.id, var16);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean l()
    {
        return false;
    }

    private void a(World par1World, int par2, int par3, int par4, int par5, Random par6Random, int par7)
    {
        int var8 = this.b[par1World.getTypeId(par2, par3, par4)];

        if (par6Random.nextInt(par5) < var8)
        {
            boolean var9 = par1World.getTypeId(par2, par3, par4) == Block.TNT.id;

            if (par6Random.nextInt(par7 + 10) < 5 && !par1World.D(par2, par3, par4))
            {
                int var10 = par7 + par6Random.nextInt(5) / 4;

                if (var10 > 15)
                {
                    var10 = 15;
                }

                par1World.setTypeIdAndData(par2, par3, par4, this.id, var10);
            }
            else
            {
                par1World.setTypeId(par2, par3, par4, 0);
            }

            if (var9)
            {
                Block.TNT.postBreak(par1World, par2, par3, par4, 1);
            }
        }
    }

    /**
     * Returns true if at least one block next to this one can burn.
     */
    private boolean l(World par1World, int par2, int par3, int par4)
    {
        return this.d(par1World, par2 + 1, par3, par4) ? true : (this.d(par1World, par2 - 1, par3, par4) ? true : (this.d(par1World, par2, par3 - 1, par4) ? true : (this.d(par1World, par2, par3 + 1, par4) ? true : (this.d(par1World, par2, par3, par4 - 1) ? true : this.d(par1World, par2, par3, par4 + 1)))));
    }

    /**
     * Gets the highest chance of a neighbor block encouraging this block to catch fire
     */
    private int n(World par1World, int par2, int par3, int par4)
    {
        byte var5 = 0;

        if (!par1World.isEmpty(par2, par3, par4))
        {
            return 0;
        }
        else
        {
            int var6 = this.d(par1World, par2 + 1, par3, par4, var5);
            var6 = this.d(par1World, par2 - 1, par3, par4, var6);
            var6 = this.d(par1World, par2, par3 - 1, par4, var6);
            var6 = this.d(par1World, par2, par3 + 1, par4, var6);
            var6 = this.d(par1World, par2, par3, par4 - 1, var6);
            var6 = this.d(par1World, par2, par3, par4 + 1, var6);
            return var6;
        }
    }

    /**
     * Returns if this block is collidable (only used by Fire). Args: x, y, z
     */
    public boolean m()
    {
        return false;
    }

    /**
     * Checks the specified block coordinate to see if it can catch fire.  Args: blockAccess, x, y, z
     */
    public boolean d(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return this.a[par1IBlockAccess.getTypeId(par2, par3, par4)] > 0;
    }

    /**
     * Retrieves a specified block's chance to encourage their neighbors to burn and if the number is greater than the
     * current number passed in it will return its number instead of the passed in one.  Args: world, x, y, z,
     * curChanceToEncourageFire
     */
    public int d(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = this.a[par1World.getTypeId(par2, par3, par4)];
        return var6 > par5 ? var6 : par5;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return par1World.v(par2, par3 - 1, par4) || this.l(par1World, par2, par3, par4);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.v(par2, par3 - 1, par4) && !this.l(par1World, par2, par3, par4))
        {
            par1World.setTypeId(par2, par3, par4, 0);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        if (par1World.worldProvider.dimension > 0 || par1World.getTypeId(par2, par3 - 1, par4) != Block.OBSIDIAN.id || !Block.PORTAL.i_(par1World, par2, par3, par4))
        {
            if (!par1World.v(par2, par3 - 1, par4) && !this.l(par1World, par2, par3, par4))
            {
                par1World.setTypeId(par2, par3, par4, 0);
            }
            else
            {
                par1World.a(par2, par3, par4, this.id, this.r_() + par1World.random.nextInt(10));
            }
        }
    }
}
