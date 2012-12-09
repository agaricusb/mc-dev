package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class BlockPistonExtension extends Block
{
    /** The texture for the 'head' of the piston. Sticky or normal. */
    private int a = -1;

    public BlockPistonExtension(int par1, int par2)
    {
        super(par1, par2, Material.PISTON);
        this.a(h);
        this.c(0.5F);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.remove(par1World, par2, par3, par4, par5, par6);
        int var7 = Facing.OPPOSITE_FACING[f(par6)];
        par2 += Facing.b[var7];
        par3 += Facing.c[var7];
        par4 += Facing.d[var7];
        int var8 = par1World.getTypeId(par2, par3, par4);

        if (var8 == Block.PISTON.id || var8 == Block.PISTON_STICKY.id)
        {
            par6 = par1World.getData(par2, par3, par4);

            if (BlockPiston.f(par6))
            {
                Block.byId[var8].c(par1World, par2, par3, par4, par6, 0);
                par1World.setTypeId(par2, par3, par4, 0);
            }
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        int var3 = f(par2);
        return par1 == var3 ? (this.a >= 0 ? this.a : ((par2 & 8) != 0 ? this.textureId - 1 : this.textureId)) : (var3 < 6 && par1 == Facing.OPPOSITE_FACING[var3] ? 107 : 108);
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 17;
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
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return false;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4, int par5)
    {
        return false;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 0;
    }

    /**
     * if the specified block is in the given AABB, add its collision bounding box to the given list
     */
    public void a(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        int var8 = par1World.getData(par2, par3, par4);

        switch (f(var8))
        {
            case 0:
                this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
                super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.a(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
                super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                break;

            case 1:
                this.a(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
                super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.a(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
                super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                break;

            case 2:
                this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
                super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.a(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
                super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                break;

            case 3:
                this.a(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
                super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.a(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
                super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                break;

            case 4:
                this.a(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
                super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.a(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
                super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                break;

            case 5:
                this.a(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
                this.a(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
                super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }

        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getData(par2, par3, par4);

        switch (f(var5))
        {
            case 0:
                this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
                break;

            case 1:
                this.a(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            case 2:
                this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
                break;

            case 3:
                this.a(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
                break;

            case 4:
                this.a(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
                break;

            case 5:
                this.a(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = f(par1World.getData(par2, par3, par4));
        int var7 = par1World.getTypeId(par2 - Facing.b[var6], par3 - Facing.c[var6], par4 - Facing.d[var6]);

        if (var7 != Block.PISTON.id && var7 != Block.PISTON_STICKY.id)
        {
            par1World.setTypeId(par2, par3, par4, 0);
        }
        else
        {
            Block.byId[var7].doPhysics(par1World, par2 - Facing.b[var6], par3 - Facing.c[var6], par4 - Facing.d[var6], par5);
        }
    }

    public static int f(int par0)
    {
        return par0 & 7;
    }
}
