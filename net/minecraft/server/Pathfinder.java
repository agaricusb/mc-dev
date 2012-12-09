package net.minecraft.server;

public class Pathfinder
{
    /** Used to find obstacles */
    private IBlockAccess a;

    /** The path being generated */
    private Path b = new Path();

    /** The points in the path */
    private IntHashMap c = new IntHashMap();

    /** Selection of path points to add to the path */
    private PathPoint[] d = new PathPoint[32];

    /** should the PathFinder go through wodden door blocks */
    private boolean e;

    /**
     * should the PathFinder disregard BlockMovement type materials in its path
     */
    private boolean f;
    private boolean g;

    /** tells the FathFinder to not stop pathing underwater */
    private boolean h;

    public Pathfinder(IBlockAccess par1IBlockAccess, boolean par2, boolean par3, boolean par4, boolean par5)
    {
        this.a = par1IBlockAccess;
        this.e = par2;
        this.f = par3;
        this.g = par4;
        this.h = par5;
    }

    /**
     * Creates a path from one entity to another within a minimum distance
     */
    public PathEntity a(Entity par1Entity, Entity par2Entity, float par3)
    {
        return this.a(par1Entity, par2Entity.locX, par2Entity.boundingBox.b, par2Entity.locZ, par3);
    }

    /**
     * Creates a path from an entity to a specified location within a minimum distance
     */
    public PathEntity a(Entity par1Entity, int par2, int par3, int par4, float par5)
    {
        return this.a(par1Entity, (double) ((float) par2 + 0.5F), (double) ((float) par3 + 0.5F), (double) ((float) par4 + 0.5F), par5);
    }

    /**
     * Internal implementation of creating a path from an entity to a point
     */
    private PathEntity a(Entity par1Entity, double par2, double par4, double par6, float par8)
    {
        this.b.a();
        this.c.c();
        boolean var9 = this.g;
        int var10 = MathHelper.floor(par1Entity.boundingBox.b + 0.5D);

        if (this.h && par1Entity.H())
        {
            var10 = (int)par1Entity.boundingBox.b;

            for (int var11 = this.a.getTypeId(MathHelper.floor(par1Entity.locX), var10, MathHelper.floor(par1Entity.locZ)); var11 == Block.WATER.id || var11 == Block.STATIONARY_WATER.id; var11 = this.a.getTypeId(MathHelper.floor(par1Entity.locX), var10, MathHelper.floor(par1Entity.locZ)))
            {
                ++var10;
            }

            var9 = this.g;
            this.g = false;
        }
        else
        {
            var10 = MathHelper.floor(par1Entity.boundingBox.b + 0.5D);
        }

        PathPoint var15 = this.a(MathHelper.floor(par1Entity.boundingBox.a), var10, MathHelper.floor(par1Entity.boundingBox.c));
        PathPoint var12 = this.a(MathHelper.floor(par2 - (double) (par1Entity.width / 2.0F)), MathHelper.floor(par4), MathHelper.floor(par6 - (double) (par1Entity.width / 2.0F)));
        PathPoint var13 = new PathPoint(MathHelper.d(par1Entity.width + 1.0F), MathHelper.d(par1Entity.length + 1.0F), MathHelper.d(par1Entity.width + 1.0F));
        PathEntity var14 = this.a(par1Entity, var15, var12, var13, par8);
        this.g = var9;
        return var14;
    }

    /**
     * Adds a path from start to end and returns the whole path (args: unused, start, end, unused, maxDistance)
     */
    private PathEntity a(Entity par1Entity, PathPoint par2PathPoint, PathPoint par3PathPoint, PathPoint par4PathPoint, float par5)
    {
        par2PathPoint.e = 0.0F;
        par2PathPoint.f = par2PathPoint.b(par3PathPoint);
        par2PathPoint.g = par2PathPoint.f;
        this.b.a();
        this.b.a(par2PathPoint);
        PathPoint var6 = par2PathPoint;

        while (!this.b.e())
        {
            PathPoint var7 = this.b.c();

            if (var7.equals(par3PathPoint))
            {
                return this.a(par2PathPoint, par3PathPoint);
            }

            if (var7.b(par3PathPoint) < var6.b(par3PathPoint))
            {
                var6 = var7;
            }

            var7.i = true;
            int var8 = this.b(par1Entity, var7, par4PathPoint, par3PathPoint, par5);

            for (int var9 = 0; var9 < var8; ++var9)
            {
                PathPoint var10 = this.d[var9];
                float var11 = var7.e + var7.b(var10);

                if (!var10.a() || var11 < var10.e)
                {
                    var10.h = var7;
                    var10.e = var11;
                    var10.f = var10.b(par3PathPoint);

                    if (var10.a())
                    {
                        this.b.a(var10, var10.e + var10.f);
                    }
                    else
                    {
                        var10.g = var10.e + var10.f;
                        this.b.a(var10);
                    }
                }
            }
        }

        if (var6 == par2PathPoint)
        {
            return null;
        }
        else
        {
            return this.a(par2PathPoint, var6);
        }
    }

    /**
     * populates pathOptions with available points and returns the number of options found (args: unused1, currentPoint,
     * unused2, targetPoint, maxDistance)
     */
    private int b(Entity par1Entity, PathPoint par2PathPoint, PathPoint par3PathPoint, PathPoint par4PathPoint, float par5)
    {
        int var6 = 0;
        byte var7 = 0;

        if (this.a(par1Entity, par2PathPoint.a, par2PathPoint.b + 1, par2PathPoint.c, par3PathPoint) == 1)
        {
            var7 = 1;
        }

        PathPoint var8 = this.a(par1Entity, par2PathPoint.a, par2PathPoint.b, par2PathPoint.c + 1, par3PathPoint, var7);
        PathPoint var9 = this.a(par1Entity, par2PathPoint.a - 1, par2PathPoint.b, par2PathPoint.c, par3PathPoint, var7);
        PathPoint var10 = this.a(par1Entity, par2PathPoint.a + 1, par2PathPoint.b, par2PathPoint.c, par3PathPoint, var7);
        PathPoint var11 = this.a(par1Entity, par2PathPoint.a, par2PathPoint.b, par2PathPoint.c - 1, par3PathPoint, var7);

        if (var8 != null && !var8.i && var8.a(par4PathPoint) < par5)
        {
            this.d[var6++] = var8;
        }

        if (var9 != null && !var9.i && var9.a(par4PathPoint) < par5)
        {
            this.d[var6++] = var9;
        }

        if (var10 != null && !var10.i && var10.a(par4PathPoint) < par5)
        {
            this.d[var6++] = var10;
        }

        if (var11 != null && !var11.i && var11.a(par4PathPoint) < par5)
        {
            this.d[var6++] = var11;
        }

        return var6;
    }

    /**
     * Returns a point that the entity can safely move to
     */
    private PathPoint a(Entity par1Entity, int par2, int par3, int par4, PathPoint par5PathPoint, int par6)
    {
        PathPoint var7 = null;
        int var8 = this.a(par1Entity, par2, par3, par4, par5PathPoint);

        if (var8 == 2)
        {
            return this.a(par2, par3, par4);
        }
        else
        {
            if (var8 == 1)
            {
                var7 = this.a(par2, par3, par4);
            }

            if (var7 == null && par6 > 0 && var8 != -3 && var8 != -4 && this.a(par1Entity, par2, par3 + par6, par4, par5PathPoint) == 1)
            {
                var7 = this.a(par2, par3 + par6, par4);
                par3 += par6;
            }

            if (var7 != null)
            {
                int var9 = 0;
                int var10 = 0;

                while (par3 > 0)
                {
                    var10 = this.a(par1Entity, par2, par3 - 1, par4, par5PathPoint);

                    if (this.g && var10 == -1)
                    {
                        return null;
                    }

                    if (var10 != 1)
                    {
                        break;
                    }

                    if (var9++ >= par1Entity.as())
                    {
                        return null;
                    }

                    --par3;

                    if (par3 > 0)
                    {
                        var7 = this.a(par2, par3, par4);
                    }
                }

                if (var10 == -2)
                {
                    return null;
                }
            }

            return var7;
        }
    }

    /**
     * Returns a mapped point or creates and adds one
     */
    private final PathPoint a(int par1, int par2, int par3)
    {
        int var4 = PathPoint.a(par1, par2, par3);
        PathPoint var5 = (PathPoint)this.c.get(var4);

        if (var5 == null)
        {
            var5 = new PathPoint(par1, par2, par3);
            this.c.a(var4, var5);
        }

        return var5;
    }

    /**
     * Given an x y z, returns a vertical offset needed to search to find a block to stand on
     */
    public int a(Entity par1Entity, int par2, int par3, int par4, PathPoint par5PathPoint)
    {
        return a(par1Entity, par2, par3, par4, par5PathPoint, this.g, this.f, this.e);
    }

    public static int a(Entity par0Entity, int par1, int par2, int par3, PathPoint par4PathPoint, boolean par5, boolean par6, boolean par7)
    {
        boolean var8 = false;

        for (int var9 = par1; var9 < par1 + par4PathPoint.a; ++var9)
        {
            for (int var10 = par2; var10 < par2 + par4PathPoint.b; ++var10)
            {
                for (int var11 = par3; var11 < par3 + par4PathPoint.c; ++var11)
                {
                    int var12 = par0Entity.world.getTypeId(var9, var10, var11);

                    if (var12 > 0)
                    {
                        if (var12 == Block.TRAP_DOOR.id)
                        {
                            var8 = true;
                        }
                        else if (var12 != Block.WATER.id && var12 != Block.STATIONARY_WATER.id)
                        {
                            if (!par7 && var12 == Block.WOODEN_DOOR.id)
                            {
                                return 0;
                            }
                        }
                        else
                        {
                            if (par5)
                            {
                                return -1;
                            }

                            var8 = true;
                        }

                        Block var13 = Block.byId[var12];

                        if (!var13.c(par0Entity.world, var9, var10, var11) && (!par6 || var12 != Block.WOODEN_DOOR.id))
                        {
                            int var14 = var13.d();

                            if (var14 == 11 || var12 == Block.FENCE_GATE.id || var14 == 32)
                            {
                                return -3;
                            }

                            if (var12 == Block.TRAP_DOOR.id)
                            {
                                return -4;
                            }

                            Material var15 = var13.material;

                            if (var15 != Material.LAVA)
                            {
                                return 0;
                            }

                            if (!par0Entity.J())
                            {
                                return -2;
                            }
                        }
                    }
                }
            }
        }

        return var8 ? 2 : 1;
    }

    /**
     * Returns a new PathEntity for a given start and end point
     */
    private PathEntity a(PathPoint par1PathPoint, PathPoint par2PathPoint)
    {
        int var3 = 1;
        PathPoint var4;

        for (var4 = par2PathPoint; var4.h != null; var4 = var4.h)
        {
            ++var3;
        }

        PathPoint[] var5 = new PathPoint[var3];
        var4 = par2PathPoint;
        --var3;

        for (var5[var3] = par2PathPoint; var4.h != null; var5[var3] = var4)
        {
            var4 = var4.h;
            --var3;
        }

        return new PathEntity(var5);
    }
}
