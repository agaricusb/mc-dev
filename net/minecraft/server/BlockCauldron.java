package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class BlockCauldron extends Block
{
    public BlockCauldron(int par1)
    {
        super(par1, Material.ORE);
        this.textureId = 154;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return par1 == 1 ? 138 : (par1 == 0 ? 155 : 154);
    }

    /**
     * if the specified block is in the given AABB, add its collision bounding box to the given list
     */
    public void a(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
        super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        float var8 = 0.125F;
        this.a(0.0F, 0.0F, 0.0F, var8, 1.0F, 1.0F);
        super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var8);
        super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.a(1.0F - var8, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.a(0.0F, 0.0F, 1.0F - var8, 1.0F, 1.0F, 1.0F);
        super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.f();
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void f()
    {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
        return 24;
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
        if (par1World.isStatic)
        {
            return true;
        }
        else
        {
            ItemStack var10 = par5EntityPlayer.inventory.getItemInHand();

            if (var10 == null)
            {
                return true;
            }
            else
            {
                int var11 = par1World.getData(par2, par3, par4);

                if (var10.id == Item.WATER_BUCKET.id)
                {
                    if (var11 < 3)
                    {
                        if (!par5EntityPlayer.abilities.canInstantlyBuild)
                        {
                            par5EntityPlayer.inventory.setItem(par5EntityPlayer.inventory.itemInHandIndex, new ItemStack(Item.BUCKET));
                        }

                        par1World.setData(par2, par3, par4, 3);
                    }

                    return true;
                }
                else
                {
                    if (var10.id == Item.GLASS_BOTTLE.id)
                    {
                        if (var11 > 0)
                        {
                            ItemStack var12 = new ItemStack(Item.POTION, 1, 0);

                            if (!par5EntityPlayer.inventory.pickup(var12))
                            {
                                par1World.addEntity(new EntityItem(par1World, (double) par2 + 0.5D, (double) par3 + 1.5D, (double) par4 + 0.5D, var12));
                            }
                            else if (par5EntityPlayer instanceof EntityPlayer)
                            {
                                ((EntityPlayer)par5EntityPlayer).updateInventory(par5EntityPlayer.defaultContainer);
                            }

                            --var10.count;

                            if (var10.count <= 0)
                            {
                                par5EntityPlayer.inventory.setItem(par5EntityPlayer.inventory.itemInHandIndex, (ItemStack) null);
                            }

                            par1World.setData(par2, par3, par4, var11 - 1);
                        }
                    }
                    else if (var11 > 0 && var10.getItem() instanceof ItemArmor && ((ItemArmor)var10.getItem()).d() == EnumArmorMaterial.CLOTH)
                    {
                        ItemArmor var13 = (ItemArmor)var10.getItem();
                        var13.c(var10);
                        par1World.setData(par2, par3, par4, var11 - 1);
                        return true;
                    }

                    return true;
                }
            }
        }
    }

    /**
     * currently only used by BlockCauldron to incrament meta-data during rain
     */
    public void f(World par1World, int par2, int par3, int par4)
    {
        if (par1World.random.nextInt(20) == 1)
        {
            int var5 = par1World.getData(par2, par3, par4);

            if (var5 < 3)
            {
                par1World.setData(par2, par3, par4, var5 + 1);
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Item.CAULDRON.id;
    }
}
