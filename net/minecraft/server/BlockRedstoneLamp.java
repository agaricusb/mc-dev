package net.minecraft.server;

import java.util.Random;

public class BlockRedstoneLamp extends Block
{
    /** Whether this lamp block is the powered version. */
    private final boolean a;

    public BlockRedstoneLamp(int par1, boolean par2)
    {
        super(par1, 211, Material.BUILDABLE_GLASS);
        this.a = par2;

        if (par2)
        {
            this.a(1.0F);
            ++this.textureId;
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isStatic)
        {
            if (this.a && !par1World.isBlockIndirectlyPowered(par2, par3, par4))
            {
                par1World.a(par2, par3, par4, this.id, 4);
            }
            else if (!this.a && par1World.isBlockIndirectlyPowered(par2, par3, par4))
            {
                par1World.setTypeId(par2, par3, par4, Block.REDSTONE_LAMP_ON.id);
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
            if (this.a && !par1World.isBlockIndirectlyPowered(par2, par3, par4))
            {
                par1World.a(par2, par3, par4, this.id, 4);
            }
            else if (!this.a && par1World.isBlockIndirectlyPowered(par2, par3, par4))
            {
                par1World.setTypeId(par2, par3, par4, Block.REDSTONE_LAMP_ON.id);
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isStatic && this.a && !par1World.isBlockIndirectlyPowered(par2, par3, par4))
        {
            par1World.setTypeId(par2, par3, par4, Block.REDSTONE_LAMP_OFF.id);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Block.REDSTONE_LAMP_OFF.id;
    }
}
