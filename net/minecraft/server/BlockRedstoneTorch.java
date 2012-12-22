package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockRedstoneTorch extends BlockTorch
{
    /** Whether the redstone torch is currently active or not. */
    private boolean isOn = false;

    /** Map of ArrayLists of RedstoneUpdateInfo. Key of map is World. */
    private static Map b = new HashMap();

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return par1 == 1 ? Block.REDSTONE_WIRE.a(par1, par2) : super.a(par1, par2);
    }

    private boolean a(World par1World, int par2, int par3, int par4, boolean par5)
    {
        if (!b.containsKey(par1World))
        {
            b.put(par1World, new ArrayList());
        }

        List var6 = (List) b.get(par1World);

        if (par5)
        {
            var6.add(new RedstoneUpdateInfo(par2, par3, par4, par1World.getTime()));
        }

        int var7 = 0;

        for (int var8 = 0; var8 < var6.size(); ++var8)
        {
            RedstoneUpdateInfo var9 = (RedstoneUpdateInfo)var6.get(var8);

            if (var9.a == par2 && var9.b == par3 && var9.c == par4)
            {
                ++var7;

                if (var7 >= 8)
                {
                    return true;
                }
            }
        }

        return false;
    }

    protected BlockRedstoneTorch(int par1, int par2, boolean par3)
    {
        super(par1, par2);
        this.isOn = par3;
        this.b(true);
        this.a((CreativeModeTab) null);
    }

    /**
     * How many world ticks before ticking
     */
    public int r_()
    {
        return 2;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        if (par1World.getData(par2, par3, par4) == 0)
        {
            super.onPlace(par1World, par2, par3, par4);
        }

        if (this.isOn)
        {
            par1World.applyPhysics(par2, par3 - 1, par4, this.id);
            par1World.applyPhysics(par2, par3 + 1, par4, this.id);
            par1World.applyPhysics(par2 - 1, par3, par4, this.id);
            par1World.applyPhysics(par2 + 1, par3, par4, this.id);
            par1World.applyPhysics(par2, par3, par4 - 1, this.id);
            par1World.applyPhysics(par2, par3, par4 + 1, this.id);
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (this.isOn)
        {
            par1World.applyPhysics(par2, par3 - 1, par4, this.id);
            par1World.applyPhysics(par2, par3 + 1, par4, this.id);
            par1World.applyPhysics(par2 - 1, par3, par4, this.id);
            par1World.applyPhysics(par2 + 1, par3, par4, this.id);
            par1World.applyPhysics(par2, par3, par4 - 1, this.id);
            par1World.applyPhysics(par2, par3, par4 + 1, this.id);
        }
    }

    /**
     * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
     * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
     * Y, Z, side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean b(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (!this.isOn)
        {
            return false;
        }
        else
        {
            int var6 = par1IBlockAccess.getData(par2, par3, par4);
            return var6 == 5 && par5 == 1 ? false : (var6 == 3 && par5 == 3 ? false : (var6 == 4 && par5 == 2 ? false : (var6 == 1 && par5 == 5 ? false : var6 != 2 || par5 != 4)));
        }
    }

    /**
     * Returns true or false based on whether the block the torch is attached to is providing indirect power.
     */
    private boolean l(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getData(par2, par3, par4);
        return var5 == 5 && par1World.isBlockFaceIndirectlyPowered(par2, par3 - 1, par4, 0) ? true : (var5 == 3 && par1World.isBlockFaceIndirectlyPowered(par2, par3, par4 - 1, 2) ? true : (var5 == 4 && par1World.isBlockFaceIndirectlyPowered(par2, par3, par4 + 1, 3) ? true : (var5 == 1 && par1World.isBlockFaceIndirectlyPowered(par2 - 1, par3, par4, 4) ? true : var5 == 2 && par1World.isBlockFaceIndirectlyPowered(par2 + 1, par3, par4, 5))));
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        boolean var6 = this.l(par1World, par2, par3, par4);
        List var7 = (List) b.get(par1World);

        while (var7 != null && !var7.isEmpty() && par1World.getTime() - ((RedstoneUpdateInfo)var7.get(0)).d > 60L)
        {
            var7.remove(0);
        }

        if (this.isOn)
        {
            if (var6)
            {
                par1World.setTypeIdAndData(par2, par3, par4, Block.REDSTONE_TORCH_OFF.id, par1World.getData(par2, par3, par4));

                if (this.a(par1World, par2, par3, par4, true))
                {
                    par1World.makeSound((double) ((float) par2 + 0.5F), (double) ((float) par3 + 0.5F), (double) ((float) par4 + 0.5F), "random.fizz", 0.5F, 2.6F + (par1World.random.nextFloat() - par1World.random.nextFloat()) * 0.8F);

                    for (int var8 = 0; var8 < 5; ++var8)
                    {
                        double var9 = (double)par2 + par5Random.nextDouble() * 0.6D + 0.2D;
                        double var11 = (double)par3 + par5Random.nextDouble() * 0.6D + 0.2D;
                        double var13 = (double)par4 + par5Random.nextDouble() * 0.6D + 0.2D;
                        par1World.addParticle("smoke", var9, var11, var13, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
        else if (!var6 && !this.a(par1World, par2, par3, par4, false))
        {
            par1World.setTypeIdAndData(par2, par3, par4, Block.REDSTONE_TORCH_ON.id, par1World.getData(par2, par3, par4));
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        super.doPhysics(par1World, par2, par3, par4, par5);
        par1World.a(par2, par3, par4, this.id, this.r_());
    }

    /**
     * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
     * side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return par5 == 0 ? this.b(par1IBlockAccess, par2, par3, par4, par5) : false;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Block.REDSTONE_TORCH_ON.id;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean isPowerSource()
    {
        return true;
    }
}
