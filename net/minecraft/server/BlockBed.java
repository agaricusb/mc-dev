package net.minecraft.server;

import java.util.Iterator;
import java.util.Random;

public class BlockBed extends BlockDirectional
{
    /** Maps the foot-of-bed block to the head-of-bed block. */
    public static final int[][] a = new int[][] {{0, 1}, { -1, 0}, {0, -1}, {1, 0}};

    public BlockBed(int par1)
    {
        super(par1, 134, Material.CLOTH);
        this.p();
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
            int var10 = par1World.getData(par2, par3, par4);

            if (!b_(var10))
            {
                int var11 = e(var10);
                par2 += a[var11][0];
                par4 += a[var11][1];

                if (par1World.getTypeId(par2, par3, par4) != this.id)
                {
                    return true;
                }

                var10 = par1World.getData(par2, par3, par4);
            }

            if (!par1World.worldProvider.e())
            {
                double var19 = (double)par2 + 0.5D;
                double var21 = (double)par3 + 0.5D;
                double var15 = (double)par4 + 0.5D;
                par1World.setTypeId(par2, par3, par4, 0);
                int var17 = e(var10);
                par2 += a[var17][0];
                par4 += a[var17][1];

                if (par1World.getTypeId(par2, par3, par4) == this.id)
                {
                    par1World.setTypeId(par2, par3, par4, 0);
                    var19 = (var19 + (double)par2 + 0.5D) / 2.0D;
                    var21 = (var21 + (double)par3 + 0.5D) / 2.0D;
                    var15 = (var15 + (double)par4 + 0.5D) / 2.0D;
                }

                par1World.createExplosion((Entity) null, (double) ((float) par2 + 0.5F), (double) ((float) par3 + 0.5F), (double) ((float) par4 + 0.5F), 5.0F, true, true);
                return true;
            }
            else
            {
                if (c_(var10))
                {
                    EntityHuman var18 = null;
                    Iterator var12 = par1World.players.iterator();

                    while (var12.hasNext())
                    {
                        EntityHuman var13 = (EntityHuman)var12.next();

                        if (var13.isSleeping())
                        {
                            ChunkCoordinates var14 = var13.ca;

                            if (var14.x == par2 && var14.y == par3 && var14.z == par4)
                            {
                                var18 = var13;
                            }
                        }
                    }

                    if (var18 != null)
                    {
                        par5EntityPlayer.b("tile.bed.occupied");
                        return true;
                    }

                    a(par1World, par2, par3, par4, false);
                }

                EnumBedResult var20 = par5EntityPlayer.a(par2, par3, par4);

                if (var20 == EnumBedResult.OK)
                {
                    a(par1World, par2, par3, par4, true);
                    return true;
                }
                else
                {
                    if (var20 == EnumBedResult.NOT_POSSIBLE_NOW)
                    {
                        par5EntityPlayer.b("tile.bed.noSleep");
                    }
                    else if (var20 == EnumBedResult.NOT_SAFE)
                    {
                        par5EntityPlayer.b("tile.bed.notSafe");
                    }

                    return true;
                }
            }
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        if (par1 == 0)
        {
            return Block.WOOD.textureId;
        }
        else
        {
            int var3 = e(par2);
            int var4 = Direction.i[var3][par1];
            return b_(par2) ? (var4 == 2 ? this.textureId + 2 + 16 : (var4 != 5 && var4 != 4 ? this.textureId + 1 : this.textureId + 1 + 16)) : (var4 == 3 ? this.textureId - 1 + 16 : (var4 != 5 && var4 != 4 ? this.textureId : this.textureId + 16));
        }
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 14;
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
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        this.p();
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = par1World.getData(par2, par3, par4);
        int var7 = e(var6);

        if (b_(var6))
        {
            if (par1World.getTypeId(par2 - a[var7][0], par3, par4 - a[var7][1]) != this.id)
            {
                par1World.setTypeId(par2, par3, par4, 0);
            }
        }
        else if (par1World.getTypeId(par2 + a[var7][0], par3, par4 + a[var7][1]) != this.id)
        {
            par1World.setTypeId(par2, par3, par4, 0);

            if (!par1World.isStatic)
            {
                this.c(par1World, par2, par3, par4, var6, 0);
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return b_(par1) ? 0 : Item.BED.id;
    }

    /**
     * Set the bounds of the bed block.
     */
    private void p()
    {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
    }

    /**
     * Returns whether or not this bed block is the head of the bed.
     */
    public static boolean b_(int par0)
    {
        return (par0 & 8) != 0;
    }

    /**
     * Return whether or not the bed is occupied.
     */
    public static boolean c_(int par0)
    {
        return (par0 & 4) != 0;
    }

    /**
     * Sets whether or not the bed is occupied.
     */
    public static void a(World par0World, int par1, int par2, int par3, boolean par4)
    {
        int var5 = par0World.getData(par1, par2, par3);

        if (par4)
        {
            var5 |= 4;
        }
        else
        {
            var5 &= -5;
        }

        par0World.setData(par1, par2, par3, var5);
    }

    /**
     * Gets the nearest empty chunk coordinates for the player to wake up from a bed into.
     */
    public static ChunkCoordinates b(World par0World, int par1, int par2, int par3, int par4)
    {
        int var5 = par0World.getData(par1, par2, par3);
        int var6 = BlockDirectional.e(var5);

        for (int var7 = 0; var7 <= 1; ++var7)
        {
            int var8 = par1 - a[var6][0] * var7 - 1;
            int var9 = par3 - a[var6][1] * var7 - 1;
            int var10 = var8 + 2;
            int var11 = var9 + 2;

            for (int var12 = var8; var12 <= var10; ++var12)
            {
                for (int var13 = var9; var13 <= var11; ++var13)
                {
                    if (par0World.v(var12, par2 - 1, var13) && par0World.isEmpty(var12, par2, var13) && par0World.isEmpty(var12, par2 + 1, var13))
                    {
                        if (par4 <= 0)
                        {
                            return new ChunkCoordinates(var12, par2, var13);
                        }

                        --par4;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (!b_(par5))
        {
            super.dropNaturally(par1World, par2, par3, par4, par5, par6, 0);
        }
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int q_()
    {
        return 1;
    }

    /**
     * Called when the block is attempted to be harvested
     */
    public void a(World par1World, int par2, int par3, int par4, int par5, EntityHuman par6EntityPlayer)
    {
        if (par6EntityPlayer.abilities.canInstantlyBuild && b_(par5))
        {
            int var7 = e(par5);
            par2 -= a[var7][0];
            par4 -= a[var7][1];

            if (par1World.getTypeId(par2, par3, par4) == this.id)
            {
                par1World.setTypeId(par2, par3, par4, 0);
            }
        }
    }
}
