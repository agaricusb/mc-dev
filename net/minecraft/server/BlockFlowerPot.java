package net.minecraft.server;

import java.util.Random;

public class BlockFlowerPot extends Block
{
    public BlockFlowerPot(int par1)
    {
        super(par1, Material.ORIENTABLE);
        this.textureId = 186;
        this.f();
        this.r();
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void f()
    {
        float var1 = 0.375F;
        float var2 = var1 / 2.0F;
        this.a(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var1, 0.5F + var2);
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
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 33;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        ItemStack var10 = par5EntityPlayer.inventory.getItemInHand();

        if (var10 == null)
        {
            return false;
        }
        else if (par1World.getData(par2, par3, par4) != 0)
        {
            return false;
        }
        else
        {
            int var11 = a(var10);

            if (var11 > 0)
            {
                par1World.setData(par2, par3, par4, var11);

                if (!par5EntityPlayer.abilities.canInstantlyBuild && --var10.count <= 0)
                {
                    par5EntityPlayer.inventory.setItem(par5EntityPlayer.inventory.itemInHandIndex, (ItemStack) null);
                }

                return true;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDropData(World par1World, int par2, int par3, int par4)
    {
        ItemStack var5 = c(par1World.getData(par2, par3, par4));
        return var5 == null ? Item.FLOWER_POT.id : var5.getData();
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return super.canPlace(par1World, par2, par3, par4) && par1World.v(par2, par3 - 1, par4);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.v(par2, par3 - 1, par4))
        {
            this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
            par1World.setTypeId(par2, par3, par4, 0);
        }
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropNaturally(par1World, par2, par3, par4, par5, par6, par7);

        if (par5 > 0)
        {
            ItemStack var8 = c(par5);

            if (var8 != null)
            {
                this.b(par1World, par2, par3, par4, var8);
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Item.FLOWER_POT.id;
    }

    /**
     * Return the item associated with the specified flower pot metadata value.
     */
    public static ItemStack c(int par0)
    {
        switch (par0)
        {
            case 1:
                return new ItemStack(Block.RED_ROSE);

            case 2:
                return new ItemStack(Block.YELLOW_FLOWER);

            case 3:
                return new ItemStack(Block.SAPLING, 1, 0);

            case 4:
                return new ItemStack(Block.SAPLING, 1, 1);

            case 5:
                return new ItemStack(Block.SAPLING, 1, 2);

            case 6:
                return new ItemStack(Block.SAPLING, 1, 3);

            case 7:
                return new ItemStack(Block.RED_MUSHROOM);

            case 8:
                return new ItemStack(Block.BROWN_MUSHROOM);

            case 9:
                return new ItemStack(Block.CACTUS);

            case 10:
                return new ItemStack(Block.DEAD_BUSH);

            case 11:
                return new ItemStack(Block.LONG_GRASS, 1, 2);

            default:
                return null;
        }
    }

    /**
     * Return the flower pot metadata value associated with the specified item.
     */
    public static int a(ItemStack par0ItemStack)
    {
        int var1 = par0ItemStack.getItem().id;

        if (var1 == Block.RED_ROSE.id)
        {
            return 1;
        }
        else if (var1 == Block.YELLOW_FLOWER.id)
        {
            return 2;
        }
        else if (var1 == Block.CACTUS.id)
        {
            return 9;
        }
        else if (var1 == Block.BROWN_MUSHROOM.id)
        {
            return 8;
        }
        else if (var1 == Block.RED_MUSHROOM.id)
        {
            return 7;
        }
        else if (var1 == Block.DEAD_BUSH.id)
        {
            return 10;
        }
        else
        {
            if (var1 == Block.SAPLING.id)
            {
                switch (par0ItemStack.getData())
                {
                    case 0:
                        return 3;

                    case 1:
                        return 4;

                    case 2:
                        return 5;

                    case 3:
                        return 6;
                }
            }

            if (var1 == Block.LONG_GRASS.id)
            {
                switch (par0ItemStack.getData())
                {
                    case 2:
                        return 11;
                }
            }

            return 0;
        }
    }
}
