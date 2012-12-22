package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BlockRedstoneWire extends Block
{
    /**
     * When false, power transmission methods do not look at other redstone wires. Used internally during
     * updateCurrentStrength.
     */
    private boolean a = true;
    private Set b = new HashSet();

    public BlockRedstoneWire(int par1, int par2)
    {
        super(par1, par2, Material.ORIENTABLE);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return this.textureId;
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
        return 5;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return par1World.v(par2, par3 - 1, par4) || par1World.getTypeId(par2, par3 - 1, par4) == Block.GLOWSTONE.id;
    }

    /**
     * Sets the strength of the wire current (0-15) for this block based on neighboring blocks and propagates to
     * neighboring redstone wires
     */
    private void l(World par1World, int par2, int par3, int par4)
    {
        this.a(par1World, par2, par3, par4, par2, par3, par4);
        ArrayList var5 = new ArrayList(this.b);
        this.b.clear();

        for (int var6 = 0; var6 < var5.size(); ++var6)
        {
            ChunkPosition var7 = (ChunkPosition)var5.get(var6);
            par1World.applyPhysics(var7.x, var7.y, var7.z, this.id);
        }
    }

    private void a(World par1World, int par2, int par3, int par4, int par5, int par6, int par7)
    {
        int var8 = par1World.getData(par2, par3, par4);
        int var9 = 0;
        this.a = false;
        boolean var10 = par1World.isBlockIndirectlyPowered(par2, par3, par4);
        this.a = true;
        int var11;
        int var12;
        int var13;

        if (var10)
        {
            var9 = 15;
        }
        else
        {
            for (var11 = 0; var11 < 4; ++var11)
            {
                var12 = par2;
                var13 = par4;

                if (var11 == 0)
                {
                    var12 = par2 - 1;
                }

                if (var11 == 1)
                {
                    ++var12;
                }

                if (var11 == 2)
                {
                    var13 = par4 - 1;
                }

                if (var11 == 3)
                {
                    ++var13;
                }

                if (var12 != par5 || par3 != par6 || var13 != par7)
                {
                    var9 = this.getPower(par1World, var12, par3, var13, var9);
                }

                if (par1World.t(var12, par3, var13) && !par1World.t(par2, par3 + 1, par4))
                {
                    if (var12 != par5 || par3 + 1 != par6 || var13 != par7)
                    {
                        var9 = this.getPower(par1World, var12, par3 + 1, var13, var9);
                    }
                }
                else if (!par1World.t(var12, par3, var13) && (var12 != par5 || par3 - 1 != par6 || var13 != par7))
                {
                    var9 = this.getPower(par1World, var12, par3 - 1, var13, var9);
                }
            }

            if (var9 > 0)
            {
                --var9;
            }
            else
            {
                var9 = 0;
            }
        }

        if (var8 != var9)
        {
            par1World.suppressPhysics = true;
            par1World.setData(par2, par3, par4, var9);
            par1World.e(par2, par3, par4, par2, par3, par4);
            par1World.suppressPhysics = false;

            for (var11 = 0; var11 < 4; ++var11)
            {
                var12 = par2;
                var13 = par4;
                int var14 = par3 - 1;

                if (var11 == 0)
                {
                    var12 = par2 - 1;
                }

                if (var11 == 1)
                {
                    ++var12;
                }

                if (var11 == 2)
                {
                    var13 = par4 - 1;
                }

                if (var11 == 3)
                {
                    ++var13;
                }

                if (par1World.t(var12, par3, var13))
                {
                    var14 += 2;
                }

                boolean var15 = false;
                int var16 = this.getPower(par1World, var12, par3, var13, -1);
                var9 = par1World.getData(par2, par3, par4);

                if (var9 > 0)
                {
                    --var9;
                }

                if (var16 >= 0 && var16 != var9)
                {
                    this.a(par1World, var12, par3, var13, par2, par3, par4);
                }

                var16 = this.getPower(par1World, var12, var14, var13, -1);
                var9 = par1World.getData(par2, par3, par4);

                if (var9 > 0)
                {
                    --var9;
                }

                if (var16 >= 0 && var16 != var9)
                {
                    this.a(par1World, var12, var14, var13, par2, par3, par4);
                }
            }

            if (var8 < var9 || var9 == 0)
            {
                this.b.add(new ChunkPosition(par2, par3, par4));
                this.b.add(new ChunkPosition(par2 - 1, par3, par4));
                this.b.add(new ChunkPosition(par2 + 1, par3, par4));
                this.b.add(new ChunkPosition(par2, par3 - 1, par4));
                this.b.add(new ChunkPosition(par2, par3 + 1, par4));
                this.b.add(new ChunkPosition(par2, par3, par4 - 1));
                this.b.add(new ChunkPosition(par2, par3, par4 + 1));
            }
        }
    }

    /**
     * Calls World.notifyBlocksOfNeighborChange() for all neighboring blocks, but only if the given block is a redstone
     * wire.
     */
    private void n(World par1World, int par2, int par3, int par4)
    {
        if (par1World.getTypeId(par2, par3, par4) == this.id)
        {
            par1World.applyPhysics(par2, par3, par4, this.id);
            par1World.applyPhysics(par2 - 1, par3, par4, this.id);
            par1World.applyPhysics(par2 + 1, par3, par4, this.id);
            par1World.applyPhysics(par2, par3, par4 - 1, this.id);
            par1World.applyPhysics(par2, par3, par4 + 1, this.id);
            par1World.applyPhysics(par2, par3 - 1, par4, this.id);
            par1World.applyPhysics(par2, par3 + 1, par4, this.id);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        super.onPlace(par1World, par2, par3, par4);

        if (!par1World.isStatic)
        {
            this.l(par1World, par2, par3, par4);
            par1World.applyPhysics(par2, par3 + 1, par4, this.id);
            par1World.applyPhysics(par2, par3 - 1, par4, this.id);
            this.n(par1World, par2 - 1, par3, par4);
            this.n(par1World, par2 + 1, par3, par4);
            this.n(par1World, par2, par3, par4 - 1);
            this.n(par1World, par2, par3, par4 + 1);

            if (par1World.t(par2 - 1, par3, par4))
            {
                this.n(par1World, par2 - 1, par3 + 1, par4);
            }
            else
            {
                this.n(par1World, par2 - 1, par3 - 1, par4);
            }

            if (par1World.t(par2 + 1, par3, par4))
            {
                this.n(par1World, par2 + 1, par3 + 1, par4);
            }
            else
            {
                this.n(par1World, par2 + 1, par3 - 1, par4);
            }

            if (par1World.t(par2, par3, par4 - 1))
            {
                this.n(par1World, par2, par3 + 1, par4 - 1);
            }
            else
            {
                this.n(par1World, par2, par3 - 1, par4 - 1);
            }

            if (par1World.t(par2, par3, par4 + 1))
            {
                this.n(par1World, par2, par3 + 1, par4 + 1);
            }
            else
            {
                this.n(par1World, par2, par3 - 1, par4 + 1);
            }
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.remove(par1World, par2, par3, par4, par5, par6);

        if (!par1World.isStatic)
        {
            par1World.applyPhysics(par2, par3 + 1, par4, this.id);
            par1World.applyPhysics(par2, par3 - 1, par4, this.id);
            par1World.applyPhysics(par2 + 1, par3, par4, this.id);
            par1World.applyPhysics(par2 - 1, par3, par4, this.id);
            par1World.applyPhysics(par2, par3, par4 + 1, this.id);
            par1World.applyPhysics(par2, par3, par4 - 1, this.id);
            this.l(par1World, par2, par3, par4);
            this.n(par1World, par2 - 1, par3, par4);
            this.n(par1World, par2 + 1, par3, par4);
            this.n(par1World, par2, par3, par4 - 1);
            this.n(par1World, par2, par3, par4 + 1);

            if (par1World.t(par2 - 1, par3, par4))
            {
                this.n(par1World, par2 - 1, par3 + 1, par4);
            }
            else
            {
                this.n(par1World, par2 - 1, par3 - 1, par4);
            }

            if (par1World.t(par2 + 1, par3, par4))
            {
                this.n(par1World, par2 + 1, par3 + 1, par4);
            }
            else
            {
                this.n(par1World, par2 + 1, par3 - 1, par4);
            }

            if (par1World.t(par2, par3, par4 - 1))
            {
                this.n(par1World, par2, par3 + 1, par4 - 1);
            }
            else
            {
                this.n(par1World, par2, par3 - 1, par4 - 1);
            }

            if (par1World.t(par2, par3, par4 + 1))
            {
                this.n(par1World, par2, par3 + 1, par4 + 1);
            }
            else
            {
                this.n(par1World, par2, par3 - 1, par4 + 1);
            }
        }
    }

    /**
     * Returns the current strength at the specified block if it is greater than the passed value, or the passed value
     * otherwise. Signature: (world, x, y, z, strength)
     */
    private int getPower(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par1World.getTypeId(par2, par3, par4) != this.id)
        {
            return par5;
        }
        else
        {
            int var6 = par1World.getData(par2, par3, par4);
            return var6 > par5 ? var6 : par5;
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
            boolean var7 = this.canPlace(par1World, par2, par3, par4);

            if (var7)
            {
                this.l(par1World, par2, par3, par4);
            }
            else
            {
                this.c(par1World, par2, par3, par4, var6, 0);
                par1World.setTypeId(par2, par3, par4, 0);
            }

            super.doPhysics(par1World, par2, par3, par4, par5);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Item.REDSTONE.id;
    }

    /**
     * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
     * side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return !this.a ? false : this.b(par1IBlockAccess, par2, par3, par4, par5);
    }

    /**
     * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
     * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
     * Y, Z, side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean b(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (!this.a)
        {
            return false;
        }
        else if (par1IBlockAccess.getData(par2, par3, par4) == 0)
        {
            return false;
        }
        else if (par5 == 1)
        {
            return true;
        }
        else
        {
            boolean var6 = g(par1IBlockAccess, par2 - 1, par3, par4, 1) || !par1IBlockAccess.t(par2 - 1, par3, par4) && g(par1IBlockAccess, par2 - 1, par3 - 1, par4, -1);
            boolean var7 = g(par1IBlockAccess, par2 + 1, par3, par4, 3) || !par1IBlockAccess.t(par2 + 1, par3, par4) && g(par1IBlockAccess, par2 + 1, par3 - 1, par4, -1);
            boolean var8 = g(par1IBlockAccess, par2, par3, par4 - 1, 2) || !par1IBlockAccess.t(par2, par3, par4 - 1) && g(par1IBlockAccess, par2, par3 - 1, par4 - 1, -1);
            boolean var9 = g(par1IBlockAccess, par2, par3, par4 + 1, 0) || !par1IBlockAccess.t(par2, par3, par4 + 1) && g(par1IBlockAccess, par2, par3 - 1, par4 + 1, -1);

            if (!par1IBlockAccess.t(par2, par3 + 1, par4))
            {
                if (par1IBlockAccess.t(par2 - 1, par3, par4) && g(par1IBlockAccess, par2 - 1, par3 + 1, par4, -1))
                {
                    var6 = true;
                }

                if (par1IBlockAccess.t(par2 + 1, par3, par4) && g(par1IBlockAccess, par2 + 1, par3 + 1, par4, -1))
                {
                    var7 = true;
                }

                if (par1IBlockAccess.t(par2, par3, par4 - 1) && g(par1IBlockAccess, par2, par3 + 1, par4 - 1, -1))
                {
                    var8 = true;
                }

                if (par1IBlockAccess.t(par2, par3, par4 + 1) && g(par1IBlockAccess, par2, par3 + 1, par4 + 1, -1))
                {
                    var9 = true;
                }
            }

            return !var8 && !var7 && !var6 && !var9 && par5 >= 2 && par5 <= 5 ? true : (par5 == 2 && var8 && !var6 && !var7 ? true : (par5 == 3 && var9 && !var6 && !var7 ? true : (par5 == 4 && var6 && !var8 && !var9 ? true : par5 == 5 && var7 && !var8 && !var9)));
        }
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean isPowerSource()
    {
        return this.a;
    }

    /**
     * Returns true if redstone wire can connect to the specified block. Params: World, X, Y, Z, side (not a normal
     * notch-side, this can be 0, 1, 2, 3 or -1)
     */
    public static boolean f(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, int par4)
    {
        int var5 = par0IBlockAccess.getTypeId(par1, par2, par3);

        if (var5 == Block.REDSTONE_WIRE.id)
        {
            return true;
        }
        else if (var5 == 0)
        {
            return false;
        }
        else if (var5 != Block.DIODE_OFF.id && var5 != Block.DIODE_ON.id)
        {
            return Block.byId[var5].isPowerSource() && par4 != -1;
        }
        else
        {
            int var6 = par0IBlockAccess.getData(par1, par2, par3);
            return par4 == (var6 & 3) || par4 == Direction.f[var6 & 3];
        }
    }

    /**
     * Returns true if the block coordinate passed can provide power, or is a redstone wire, or if its a repeater that
     * is powered.
     */
    public static boolean g(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, int par4)
    {
        if (f(par0IBlockAccess, par1, par2, par3, par4))
        {
            return true;
        }
        else
        {
            int var5 = par0IBlockAccess.getTypeId(par1, par2, par3);

            if (var5 == Block.DIODE_ON.id)
            {
                int var6 = par0IBlockAccess.getData(par1, par2, par3);
                return par4 == (var6 & 3);
            }
            else
            {
                return false;
            }
        }
    }
}
