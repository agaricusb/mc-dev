package net.minecraft.server;

import java.util.Random;

public class BlockSand extends Block
{
    /** Do blocks fall instantly to where they stop or do they fall over time */
    public static boolean instaFall = false;

    public BlockSand(int par1, int par2)
    {
        super(par1, par2, Material.SAND);
        this.a(CreativeModeTab.b);
    }

    public BlockSand(int par1, int par2, Material par3Material)
    {
        super(par1, par2, par3Material);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        par1World.a(par2, par3, par4, this.id, this.r_());
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        par1World.a(par2, par3, par4, this.id, this.r_());
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isStatic)
        {
            this.l(par1World, par2, par3, par4);
        }
    }

    /**
     * If there is space to fall below will start this block falling
     */
    private void l(World par1World, int par2, int par3, int par4)
    {
        if (canFall(par1World, par2, par3 - 1, par4) && par3 >= 0)
        {
            byte var8 = 32;

            if (!instaFall && par1World.d(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
            {
                if (!par1World.isStatic)
                {
                    EntityFallingBlock var9 = new EntityFallingBlock(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), this.id, par1World.getData(par2, par3, par4));
                    this.a(var9);
                    par1World.addEntity(var9);
                }
            }
            else
            {
                par1World.setTypeId(par2, par3, par4, 0);

                while (canFall(par1World, par2, par3 - 1, par4) && par3 > 0)
                {
                    --par3;
                }

                if (par3 > 0)
                {
                    par1World.setTypeId(par2, par3, par4, this.id);
                }
            }
        }
    }

    /**
     * Called when the falling block entity for this block is created
     */
    protected void a(EntityFallingBlock par1EntityFallingSand) {}

    /**
     * How many world ticks before ticking
     */
    public int r_()
    {
        return 5;
    }

    /**
     * Checks to see if the sand can fall into the block below it
     */
    public static boolean canFall(World par0World, int par1, int par2, int par3)
    {
        int var4 = par0World.getTypeId(par1, par2, par3);

        if (var4 == 0)
        {
            return true;
        }
        else if (var4 == Block.FIRE.id)
        {
            return true;
        }
        else
        {
            Material var5 = Block.byId[var4].material;
            return var5 == Material.WATER ? true : var5 == Material.LAVA;
        }
    }

    /**
     * Called when the falling block entity for this block hits the ground and turns back into a block
     */
    public void a_(World par1World, int par2, int par3, int par4, int par5) {}
}
