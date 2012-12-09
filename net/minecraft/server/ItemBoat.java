package net.minecraft.server;

import java.util.List;

public class ItemBoat extends Item
{
    public ItemBoat(int par1)
    {
        super(par1);
        this.maxStackSize = 1;
        this.a(CreativeModeTab.e);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        float var4 = 1.0F;
        float var5 = par3EntityPlayer.lastPitch + (par3EntityPlayer.pitch - par3EntityPlayer.lastPitch) * var4;
        float var6 = par3EntityPlayer.lastYaw + (par3EntityPlayer.yaw - par3EntityPlayer.lastYaw) * var4;
        double var7 = par3EntityPlayer.lastX + (par3EntityPlayer.locX - par3EntityPlayer.lastX) * (double)var4;
        double var9 = par3EntityPlayer.lastY + (par3EntityPlayer.locY - par3EntityPlayer.lastY) * (double)var4 + 1.62D - (double)par3EntityPlayer.height;
        double var11 = par3EntityPlayer.lastZ + (par3EntityPlayer.locZ - par3EntityPlayer.lastZ) * (double)var4;
        Vec3D var13 = par2World.getVec3DPool().create(var7, var9, var11);
        float var14 = MathHelper.cos(-var6 * 0.017453292F - (float) Math.PI);
        float var15 = MathHelper.sin(-var6 * 0.017453292F - (float) Math.PI);
        float var16 = -MathHelper.cos(-var5 * 0.017453292F);
        float var17 = MathHelper.sin(-var5 * 0.017453292F);
        float var18 = var15 * var16;
        float var20 = var14 * var16;
        double var21 = 5.0D;
        Vec3D var23 = var13.add((double) var18 * var21, (double) var17 * var21, (double) var20 * var21);
        MovingObjectPosition var24 = par2World.rayTrace(var13, var23, true);

        if (var24 == null)
        {
            return par1ItemStack;
        }
        else
        {
            Vec3D var25 = par3EntityPlayer.i(var4);
            boolean var26 = false;
            float var27 = 1.0F;
            List var28 = par2World.getEntities(par3EntityPlayer, par3EntityPlayer.boundingBox.a(var25.c * var21, var25.d * var21, var25.e * var21).grow((double) var27, (double) var27, (double) var27));
            int var29;

            for (var29 = 0; var29 < var28.size(); ++var29)
            {
                Entity var30 = (Entity)var28.get(var29);

                if (var30.L())
                {
                    float var31 = var30.Y();
                    AxisAlignedBB var32 = var30.boundingBox.grow((double) var31, (double) var31, (double) var31);

                    if (var32.a(var13))
                    {
                        var26 = true;
                    }
                }
            }

            if (var26)
            {
                return par1ItemStack;
            }
            else
            {
                if (var24.type == EnumMovingObjectType.TILE)
                {
                    var29 = var24.b;
                    int var33 = var24.c;
                    int var34 = var24.d;

                    if (par2World.getTypeId(var29, var33, var34) == Block.SNOW.id)
                    {
                        --var33;
                    }

                    EntityBoat var35 = new EntityBoat(par2World, (double)((float)var29 + 0.5F), (double)((float)var33 + 1.0F), (double)((float)var34 + 0.5F));

                    if (!par2World.getCubes(var35, var35.boundingBox.grow(-0.1D, -0.1D, -0.1D)).isEmpty())
                    {
                        return par1ItemStack;
                    }

                    if (!par2World.isStatic)
                    {
                        par2World.addEntity(var35);
                    }

                    if (!par3EntityPlayer.abilities.canInstantlyBuild)
                    {
                        --par1ItemStack.count;
                    }
                }

                return par1ItemStack;
            }
        }
    }
}
