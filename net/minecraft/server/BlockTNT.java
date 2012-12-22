package net.minecraft.server;

import java.util.Random;

public class BlockTNT extends Block
{
    public BlockTNT(int par1, int par2)
    {
        super(par1, par2, Material.TNT);
        this.a(CreativeModeTab.d);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return par1 == 0 ? this.textureId + 2 : (par1 == 1 ? this.textureId + 1 : this.textureId);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        super.onPlace(par1World, par2, par3, par4);

        if (par1World.isBlockIndirectlyPowered(par2, par3, par4))
        {
            this.postBreak(par1World, par2, par3, par4, 1);
            par1World.setTypeId(par2, par3, par4, 0);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par5 > 0 && Block.byId[par5].isPowerSource() && par1World.isBlockIndirectlyPowered(par2, par3, par4))
        {
            this.postBreak(par1World, par2, par3, par4, 1);
            par1World.setTypeId(par2, par3, par4, 0);
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 1;
    }

    /**
     * Called upon the block being destroyed by an explosion
     */
    public void wasExploded(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isStatic)
        {
            EntityTNTPrimed var5 = new EntityTNTPrimed(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F));
            var5.fuseTicks = par1World.random.nextInt(var5.fuseTicks / 4) + var5.fuseTicks / 8;
            par1World.addEntity(var5);
        }
    }

    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void postBreak(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isStatic)
        {
            if ((par5 & 1) == 1)
            {
                EntityTNTPrimed var6 = new EntityTNTPrimed(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F));
                par1World.addEntity(var6);
                par1World.makeSound(var6, "random.fuse", 1.0F, 1.0F);
            }
        }
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par5EntityPlayer.bS() != null && par5EntityPlayer.bS().id == Item.FLINT_AND_STEEL.id)
        {
            this.postBreak(par1World, par2, par3, par4, 1);
            par1World.setTypeId(par2, par3, par4, 0);
            return true;
        }
        else
        {
            return super.interact(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
        }
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void a(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par5Entity instanceof EntityArrow && !par1World.isStatic)
        {
            EntityArrow var6 = (EntityArrow)par5Entity;

            if (var6.isBurning())
            {
                this.postBreak(par1World, par2, par3, par4, 1);
                par1World.setTypeId(par2, par3, par4, 0);
            }
        }
    }

    /**
     * Return whether this block can drop from an explosion.
     */
    public boolean a(Explosion par1Explosion)
    {
        return false;
    }
}
