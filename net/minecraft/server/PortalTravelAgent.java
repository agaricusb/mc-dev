package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PortalTravelAgent
{
    private final WorldServer a;

    /** A private Random() function in Teleporter */
    private final Random b;
    private final LongHashMap c = new LongHashMap();
    private final List d = new ArrayList();

    public PortalTravelAgent(WorldServer par1WorldServer)
    {
        this.a = par1WorldServer;
        this.b = new Random(par1WorldServer.getSeed());
    }

    /**
     * Place an entity in a nearby portal, creating one if necessary.
     */
    public void a(Entity par1Entity, double par2, double par4, double par6, float par8)
    {
        if (this.a.worldProvider.dimension != 1)
        {
            if (!this.b(par1Entity, par2, par4, par6, par8))
            {
                this.a(par1Entity);
                this.b(par1Entity, par2, par4, par6, par8);
            }
        }
        else
        {
            int var9 = MathHelper.floor(par1Entity.locX);
            int var10 = MathHelper.floor(par1Entity.locY) - 1;
            int var11 = MathHelper.floor(par1Entity.locZ);
            byte var12 = 1;
            byte var13 = 0;

            for (int var14 = -2; var14 <= 2; ++var14)
            {
                for (int var15 = -2; var15 <= 2; ++var15)
                {
                    for (int var16 = -1; var16 < 3; ++var16)
                    {
                        int var17 = var9 + var15 * var12 + var14 * var13;
                        int var18 = var10 + var16;
                        int var19 = var11 + var15 * var13 - var14 * var12;
                        boolean var20 = var16 < 0;
                        this.a.setTypeId(var17, var18, var19, var20 ? Block.OBSIDIAN.id : 0);
                    }
                }
            }

            par1Entity.setPositionRotation((double) var9, (double) var10, (double) var11, par1Entity.yaw, 0.0F);
            par1Entity.motX = par1Entity.motY = par1Entity.motZ = 0.0D;
        }
    }

    /**
     * Place an entity in a nearby portal which already exists.
     */
    public boolean b(Entity par1Entity, double par2, double par4, double par6, float par8)
    {
        short var9 = 128;
        double var10 = -1.0D;
        int var12 = 0;
        int var13 = 0;
        int var14 = 0;
        int var15 = MathHelper.floor(par1Entity.locX);
        int var16 = MathHelper.floor(par1Entity.locZ);
        long var17 = ChunkCoordIntPair.a(var15, var16);
        boolean var19 = true;
        double var27;
        int var48;

        if (this.c.contains(var17))
        {
            ChunkCoordinatesPortal var20 = (ChunkCoordinatesPortal)this.c.getEntry(var17);
            var10 = 0.0D;
            var12 = var20.x;
            var13 = var20.y;
            var14 = var20.z;
            var20.d = this.a.getTime();
            var19 = false;
        }
        else
        {
            for (var48 = var15 - var9; var48 <= var15 + var9; ++var48)
            {
                double var21 = (double)var48 + 0.5D - par1Entity.locX;

                for (int var23 = var16 - var9; var23 <= var16 + var9; ++var23)
                {
                    double var24 = (double)var23 + 0.5D - par1Entity.locZ;

                    for (int var26 = this.a.P() - 1; var26 >= 0; --var26)
                    {
                        if (this.a.getTypeId(var48, var26, var23) == Block.PORTAL.id)
                        {
                            while (this.a.getTypeId(var48, var26 - 1, var23) == Block.PORTAL.id)
                            {
                                --var26;
                            }

                            var27 = (double)var26 + 0.5D - par1Entity.locY;
                            double var29 = var21 * var21 + var27 * var27 + var24 * var24;

                            if (var10 < 0.0D || var29 < var10)
                            {
                                var10 = var29;
                                var12 = var48;
                                var13 = var26;
                                var14 = var23;
                            }
                        }
                    }
                }
            }
        }

        if (var10 >= 0.0D)
        {
            if (var19)
            {
                this.c.put(var17, new ChunkCoordinatesPortal(this, var12, var13, var14, this.a.getTime()));
                this.d.add(Long.valueOf(var17));
            }

            double var49 = (double)var12 + 0.5D;
            double var25 = (double)var13 + 0.5D;
            var27 = (double)var14 + 0.5D;
            int var50 = -1;

            if (this.a.getTypeId(var12 - 1, var13, var14) == Block.PORTAL.id)
            {
                var50 = 2;
            }

            if (this.a.getTypeId(var12 + 1, var13, var14) == Block.PORTAL.id)
            {
                var50 = 0;
            }

            if (this.a.getTypeId(var12, var13, var14 - 1) == Block.PORTAL.id)
            {
                var50 = 3;
            }

            if (this.a.getTypeId(var12, var13, var14 + 1) == Block.PORTAL.id)
            {
                var50 = 1;
            }

            int var30 = par1Entity.at();

            if (var50 > -1)
            {
                int var31 = Direction.h[var50];
                int var32 = Direction.a[var50];
                int var33 = Direction.b[var50];
                int var34 = Direction.a[var31];
                int var35 = Direction.b[var31];
                boolean var36 = !this.a.isEmpty(var12 + var32 + var34, var13, var14 + var33 + var35) || !this.a.isEmpty(var12 + var32 + var34, var13 + 1, var14 + var33 + var35);
                boolean var37 = !this.a.isEmpty(var12 + var32, var13, var14 + var33) || !this.a.isEmpty(var12 + var32, var13 + 1, var14 + var33);

                if (var36 && var37)
                {
                    var50 = Direction.f[var50];
                    var31 = Direction.f[var31];
                    var32 = Direction.a[var50];
                    var33 = Direction.b[var50];
                    var34 = Direction.a[var31];
                    var35 = Direction.b[var31];
                    var48 = var12 - var34;
                    var49 -= (double)var34;
                    int var22 = var14 - var35;
                    var27 -= (double)var35;
                    var36 = !this.a.isEmpty(var48 + var32 + var34, var13, var22 + var33 + var35) || !this.a.isEmpty(var48 + var32 + var34, var13 + 1, var22 + var33 + var35);
                    var37 = !this.a.isEmpty(var48 + var32, var13, var22 + var33) || !this.a.isEmpty(var48 + var32, var13 + 1, var22 + var33);
                }

                float var38 = 0.5F;
                float var39 = 0.5F;

                if (!var36 && var37)
                {
                    var38 = 1.0F;
                }
                else if (var36 && !var37)
                {
                    var38 = 0.0F;
                }
                else if (var36 && var37)
                {
                    var39 = 0.0F;
                }

                var49 += (double)((float)var34 * var38 + var39 * (float)var32);
                var27 += (double)((float)var35 * var38 + var39 * (float)var33);
                float var40 = 0.0F;
                float var41 = 0.0F;
                float var42 = 0.0F;
                float var43 = 0.0F;

                if (var50 == var30)
                {
                    var40 = 1.0F;
                    var41 = 1.0F;
                }
                else if (var50 == Direction.f[var30])
                {
                    var40 = -1.0F;
                    var41 = -1.0F;
                }
                else if (var50 == Direction.g[var30])
                {
                    var42 = 1.0F;
                    var43 = -1.0F;
                }
                else
                {
                    var42 = -1.0F;
                    var43 = 1.0F;
                }

                double var44 = par1Entity.motX;
                double var46 = par1Entity.motZ;
                par1Entity.motX = var44 * (double)var40 + var46 * (double)var43;
                par1Entity.motZ = var44 * (double)var42 + var46 * (double)var41;
                par1Entity.yaw = par8 - (float)(var30 * 90) + (float)(var50 * 90);
            }
            else
            {
                par1Entity.motX = par1Entity.motY = par1Entity.motZ = 0.0D;
            }

            par1Entity.setPositionRotation(var49, var25, var27, par1Entity.yaw, par1Entity.pitch);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean a(Entity par1Entity)
    {
        byte var2 = 16;
        double var3 = -1.0D;
        int var5 = MathHelper.floor(par1Entity.locX);
        int var6 = MathHelper.floor(par1Entity.locY);
        int var7 = MathHelper.floor(par1Entity.locZ);
        int var8 = var5;
        int var9 = var6;
        int var10 = var7;
        int var11 = 0;
        int var12 = this.b.nextInt(4);
        int var13;
        double var14;
        double var17;
        int var16;
        int var19;
        int var21;
        int var20;
        int var23;
        int var22;
        int var25;
        int var24;
        int var27;
        int var26;
        double var31;
        double var32;

        for (var13 = var5 - var2; var13 <= var5 + var2; ++var13)
        {
            var14 = (double)var13 + 0.5D - par1Entity.locX;

            for (var16 = var7 - var2; var16 <= var7 + var2; ++var16)
            {
                var17 = (double)var16 + 0.5D - par1Entity.locZ;
                label274:

                for (var19 = this.a.P() - 1; var19 >= 0; --var19)
                {
                    if (this.a.isEmpty(var13, var19, var16))
                    {
                        while (var19 > 0 && this.a.isEmpty(var13, var19 - 1, var16))
                        {
                            --var19;
                        }

                        for (var20 = var12; var20 < var12 + 4; ++var20)
                        {
                            var21 = var20 % 2;
                            var22 = 1 - var21;

                            if (var20 % 4 >= 2)
                            {
                                var21 = -var21;
                                var22 = -var22;
                            }

                            for (var23 = 0; var23 < 3; ++var23)
                            {
                                for (var24 = 0; var24 < 4; ++var24)
                                {
                                    for (var25 = -1; var25 < 4; ++var25)
                                    {
                                        var26 = var13 + (var24 - 1) * var21 + var23 * var22;
                                        var27 = var19 + var25;
                                        int var28 = var16 + (var24 - 1) * var22 - var23 * var21;

                                        if (var25 < 0 && !this.a.getMaterial(var26, var27, var28).isBuildable() || var25 >= 0 && !this.a.isEmpty(var26, var27, var28))
                                        {
                                            continue label274;
                                        }
                                    }
                                }
                            }

                            var32 = (double)var19 + 0.5D - par1Entity.locY;
                            var31 = var14 * var14 + var32 * var32 + var17 * var17;

                            if (var3 < 0.0D || var31 < var3)
                            {
                                var3 = var31;
                                var8 = var13;
                                var9 = var19;
                                var10 = var16;
                                var11 = var20 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (var3 < 0.0D)
        {
            for (var13 = var5 - var2; var13 <= var5 + var2; ++var13)
            {
                var14 = (double)var13 + 0.5D - par1Entity.locX;

                for (var16 = var7 - var2; var16 <= var7 + var2; ++var16)
                {
                    var17 = (double)var16 + 0.5D - par1Entity.locZ;
                    label222:

                    for (var19 = this.a.P() - 1; var19 >= 0; --var19)
                    {
                        if (this.a.isEmpty(var13, var19, var16))
                        {
                            while (var19 > 0 && this.a.isEmpty(var13, var19 - 1, var16))
                            {
                                --var19;
                            }

                            for (var20 = var12; var20 < var12 + 2; ++var20)
                            {
                                var21 = var20 % 2;
                                var22 = 1 - var21;

                                for (var23 = 0; var23 < 4; ++var23)
                                {
                                    for (var24 = -1; var24 < 4; ++var24)
                                    {
                                        var25 = var13 + (var23 - 1) * var21;
                                        var26 = var19 + var24;
                                        var27 = var16 + (var23 - 1) * var22;

                                        if (var24 < 0 && !this.a.getMaterial(var25, var26, var27).isBuildable() || var24 >= 0 && !this.a.isEmpty(var25, var26, var27))
                                        {
                                            continue label222;
                                        }
                                    }
                                }

                                var32 = (double)var19 + 0.5D - par1Entity.locY;
                                var31 = var14 * var14 + var32 * var32 + var17 * var17;

                                if (var3 < 0.0D || var31 < var3)
                                {
                                    var3 = var31;
                                    var8 = var13;
                                    var9 = var19;
                                    var10 = var16;
                                    var11 = var20 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int var29 = var8;
        int var15 = var9;
        var16 = var10;
        int var30 = var11 % 2;
        int var18 = 1 - var30;

        if (var11 % 4 >= 2)
        {
            var30 = -var30;
            var18 = -var18;
        }

        boolean var33;

        if (var3 < 0.0D)
        {
            if (var9 < 70)
            {
                var9 = 70;
            }

            if (var9 > this.a.P() - 10)
            {
                var9 = this.a.P() - 10;
            }

            var15 = var9;

            for (var19 = -1; var19 <= 1; ++var19)
            {
                for (var20 = 1; var20 < 3; ++var20)
                {
                    for (var21 = -1; var21 < 3; ++var21)
                    {
                        var22 = var29 + (var20 - 1) * var30 + var19 * var18;
                        var23 = var15 + var21;
                        var24 = var16 + (var20 - 1) * var18 - var19 * var30;
                        var33 = var21 < 0;
                        this.a.setTypeId(var22, var23, var24, var33 ? Block.OBSIDIAN.id : 0);
                    }
                }
            }
        }

        for (var19 = 0; var19 < 4; ++var19)
        {
            this.a.suppressPhysics = true;

            for (var20 = 0; var20 < 4; ++var20)
            {
                for (var21 = -1; var21 < 4; ++var21)
                {
                    var22 = var29 + (var20 - 1) * var30;
                    var23 = var15 + var21;
                    var24 = var16 + (var20 - 1) * var18;
                    var33 = var20 == 0 || var20 == 3 || var21 == -1 || var21 == 3;
                    this.a.setTypeId(var22, var23, var24, var33 ? Block.OBSIDIAN.id : Block.PORTAL.id);
                }
            }

            this.a.suppressPhysics = false;

            for (var20 = 0; var20 < 4; ++var20)
            {
                for (var21 = -1; var21 < 4; ++var21)
                {
                    var22 = var29 + (var20 - 1) * var30;
                    var23 = var15 + var21;
                    var24 = var16 + (var20 - 1) * var18;
                    this.a.applyPhysics(var22, var23, var24, this.a.getTypeId(var22, var23, var24));
                }
            }
        }

        return true;
    }

    public void a(long par1)
    {
        if (par1 % 100L == 0L)
        {
            Iterator var3 = this.d.iterator();
            long var4 = par1 - 600L;

            while (var3.hasNext())
            {
                Long var6 = (Long)var3.next();
                ChunkCoordinatesPortal var7 = (ChunkCoordinatesPortal)this.c.getEntry(var6.longValue());

                if (var7 == null || var7.d < var4)
                {
                    var3.remove();
                    this.c.remove(var6.longValue());
                }
            }
        }
    }
}
