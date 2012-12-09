package net.minecraft.server;

import java.util.Random;

public class BlockDoor extends Block
{
    protected BlockDoor(int par1, Material par2Material)
    {
        super(par1, par2Material);
        this.textureId = 97;

        if (par2Material == Material.ORE)
        {
            ++this.textureId;
        }

        float var3 = 0.5F;
        float var4 = 1.0F;
        this.a(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4, 0.5F + var3);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean c()
    {
        return false;
    }

    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = this.b_(par1IBlockAccess, par2, par3, par4);
        return (var5 & 4) != 0;
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
        return 7;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        this.updateShape(par1World, par2, par3, par4);
        return super.e(par1World, par2, par3, par4);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        this.e(this.b_(par1IBlockAccess, par2, par3, par4));
    }

    /**
     * Returns 0, 1, 2 or 3 depending on where the hinge is.
     */
    public int d(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return this.b_(par1IBlockAccess, par2, par3, par4) & 3;
    }

    public boolean a_(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return (this.b_(par1IBlockAccess, par2, par3, par4) & 4) != 0;
    }

    private void e(int par1)
    {
        float var2 = 0.1875F;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        int var3 = par1 & 3;
        boolean var4 = (par1 & 4) != 0;
        boolean var5 = (par1 & 16) != 0;

        if (var3 == 0)
        {
            if (var4)
            {
                if (!var5)
                {
                    this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
                }
                else
                {
                    this.a(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
                }
            }
            else
            {
                this.a(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
            }
        }
        else if (var3 == 1)
        {
            if (var4)
            {
                if (!var5)
                {
                    this.a(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
                else
                {
                    this.a(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
                }
            }
            else
            {
                this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
            }
        }
        else if (var3 == 2)
        {
            if (var4)
            {
                if (!var5)
                {
                    this.a(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
                }
                else
                {
                    this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
                }
            }
            else
            {
                this.a(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
        else if (var3 == 3)
        {
            if (var4)
            {
                if (!var5)
                {
                    this.a(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
                }
                else
                {
                    this.a(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
            }
            else
            {
                this.a(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void attack(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer) {}

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (this.material == Material.ORE)
        {
            return true;
        }
        else
        {
            int var10 = this.b_(par1World, par2, par3, par4);
            int var11 = var10 & 7;
            var11 ^= 4;

            if ((var10 & 8) == 0)
            {
                par1World.setData(par2, par3, par4, var11);
                par1World.e(par2, par3, par4, par2, par3, par4);
            }
            else
            {
                par1World.setData(par2, par3 - 1, par4, var11);
                par1World.e(par2, par3 - 1, par4, par2, par3, par4);
            }

            par1World.a(par5EntityPlayer, 1003, par2, par3, par4, 0);
            return true;
        }
    }

    /**
     * A function to open a door.
     */
    public void setDoor(World par1World, int par2, int par3, int par4, boolean par5)
    {
        int var6 = this.b_(par1World, par2, par3, par4);
        boolean var7 = (var6 & 4) != 0;

        if (var7 != par5)
        {
            int var8 = var6 & 7;
            var8 ^= 4;

            if ((var6 & 8) == 0)
            {
                par1World.setData(par2, par3, par4, var8);
                par1World.e(par2, par3, par4, par2, par3, par4);
            }
            else
            {
                par1World.setData(par2, par3 - 1, par4, var8);
                par1World.e(par2, par3 - 1, par4, par2, par3, par4);
            }

            par1World.a((EntityHuman) null, 1003, par2, par3, par4, 0);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = par1World.getData(par2, par3, par4);

        if ((var6 & 8) == 0)
        {
            boolean var7 = false;

            if (par1World.getTypeId(par2, par3 + 1, par4) != this.id)
            {
                par1World.setTypeId(par2, par3, par4, 0);
                var7 = true;
            }

            if (!par1World.v(par2, par3 - 1, par4))
            {
                par1World.setTypeId(par2, par3, par4, 0);
                var7 = true;

                if (par1World.getTypeId(par2, par3 + 1, par4) == this.id)
                {
                    par1World.setTypeId(par2, par3 + 1, par4, 0);
                }
            }

            if (var7)
            {
                if (!par1World.isStatic)
                {
                    this.c(par1World, par2, par3, par4, var6, 0);
                }
            }
            else
            {
                boolean var8 = par1World.isBlockIndirectlyPowered(par2, par3, par4) || par1World.isBlockIndirectlyPowered(par2, par3 + 1, par4);

                if ((var8 || par5 > 0 && Block.byId[par5].isPowerSource()) && par5 != this.id)
                {
                    this.setDoor(par1World, par2, par3, par4, var8);
                }
            }
        }
        else
        {
            if (par1World.getTypeId(par2, par3 - 1, par4) != this.id)
            {
                par1World.setTypeId(par2, par3, par4, 0);
            }

            if (par5 > 0 && par5 != this.id)
            {
                this.doPhysics(par1World, par2, par3 - 1, par4, par5);
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return (par1 & 8) != 0 ? 0 : (this.material == Material.ORE ? Item.IRON_DOOR.id : Item.WOOD_DOOR.id);
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
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return par3 >= 255 ? false : par1World.v(par2, par3 - 1, par4) && super.canPlace(par1World, par2, par3, par4) && super.canPlace(par1World, par2, par3 + 1, par4);
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
     * Returns the full metadata value created by combining the metadata of both blocks the door takes up.
     */
    public int b_(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getData(par2, par3, par4);
        boolean var6 = (var5 & 8) != 0;
        int var7;
        int var8;

        if (var6)
        {
            var7 = par1IBlockAccess.getData(par2, par3 - 1, par4);
            var8 = var5;
        }
        else
        {
            var7 = var5;
            var8 = par1IBlockAccess.getData(par2, par3 + 1, par4);
        }

        boolean var9 = (var8 & 1) != 0;
        return var7 & 7 | (var6 ? 8 : 0) | (var9 ? 16 : 0);
    }

    /**
     * Called when the block is attempted to be harvested
     */
    public void a(World par1World, int par2, int par3, int par4, int par5, EntityHuman par6EntityPlayer)
    {
        if (par6EntityPlayer.abilities.canInstantlyBuild && (par5 & 8) != 0 && par1World.getTypeId(par2, par3 - 1, par4) == this.id)
        {
            par1World.setTypeId(par2, par3 - 1, par4, 0);
        }
    }
}
