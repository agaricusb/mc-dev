package net.minecraft.server;

import java.util.Random;

public class RandomPositionGenerator
{
    /**
     * used to store a driection when the user passes a point to move towards or away from. WARNING: NEVER THREAD SAFE.
     * MULTIPLE findTowards and findAway calls, will share this var
     */
    private static Vec3D a = Vec3D.a(0.0D, 0.0D, 0.0D);

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks
     */
    public static Vec3D a(EntityCreature par0EntityCreature, int par1, int par2)
    {
        return c(par0EntityCreature, par1, par2, (Vec3D) null);
    }

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks in the direction of the point par3
     */
    public static Vec3D a(EntityCreature par0EntityCreature, int par1, int par2, Vec3D par3Vec3)
    {
        a.c = par3Vec3.c - par0EntityCreature.locX;
        a.d = par3Vec3.d - par0EntityCreature.locY;
        a.e = par3Vec3.e - par0EntityCreature.locZ;
        return c(par0EntityCreature, par1, par2, a);
    }

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks in the reverse direction of the point par3
     */
    public static Vec3D b(EntityCreature par0EntityCreature, int par1, int par2, Vec3D par3Vec3)
    {
        a.c = par0EntityCreature.locX - par3Vec3.c;
        a.d = par0EntityCreature.locY - par3Vec3.d;
        a.e = par0EntityCreature.locZ - par3Vec3.e;
        return c(par0EntityCreature, par1, par2, a);
    }

    /**
     * searches 10 blocks at random in a within par1(x,z) and par2 (y) distance, ignores those not in the direction of
     * par3Vec3, then points to the tile for which creature.getBlockPathWeight returns the highest number
     */
    private static Vec3D c(EntityCreature par0EntityCreature, int par1, int par2, Vec3D par3Vec3)
    {
        Random var4 = par0EntityCreature.aB();
        boolean var5 = false;
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        float var9 = -99999.0F;
        boolean var10;

        if (par0EntityCreature.aM())
        {
            double var11 = (double)(par0EntityCreature.aJ().e(MathHelper.floor(par0EntityCreature.locX), MathHelper.floor(par0EntityCreature.locY), MathHelper.floor(par0EntityCreature.locZ)) + 4.0F);
            double var13 = (double)(par0EntityCreature.aK() + (float)par1);
            var10 = var11 < var13 * var13;
        }
        else
        {
            var10 = false;
        }

        for (int var16 = 0; var16 < 10; ++var16)
        {
            int var12 = var4.nextInt(2 * par1) - par1;
            int var17 = var4.nextInt(2 * par2) - par2;
            int var14 = var4.nextInt(2 * par1) - par1;

            if (par3Vec3 == null || (double)var12 * par3Vec3.c + (double)var14 * par3Vec3.e >= 0.0D)
            {
                var12 += MathHelper.floor(par0EntityCreature.locX);
                var17 += MathHelper.floor(par0EntityCreature.locY);
                var14 += MathHelper.floor(par0EntityCreature.locZ);

                if (!var10 || par0EntityCreature.e(var12, var17, var14))
                {
                    float var15 = par0EntityCreature.a(var12, var17, var14);

                    if (var15 > var9)
                    {
                        var9 = var15;
                        var6 = var12;
                        var7 = var17;
                        var8 = var14;
                        var5 = true;
                    }
                }
            }
        }

        if (var5)
        {
            return par0EntityCreature.world.getVec3DPool().create((double) var6, (double) var7, (double) var8);
        }
        else
        {
            return null;
        }
    }
}
