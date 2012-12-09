package net.minecraft.server;

public class Navigation
{
    private EntityLiving a;
    private World b;

    /** The PathEntity being followed. */
    private PathEntity c;
    private float d;

    /**
     * The number of blocks (extra) +/- in each axis that get pulled out as cache for the pathfinder's search space
     */
    private float e;
    private boolean f = false;

    /** Time, in number of ticks, following the current path */
    private int g;

    /**
     * The time when the last position check was done (to detect successful movement)
     */
    private int h;

    /**
     * Coordinates of the entity's position last time a check was done (part of monitoring getting 'stuck')
     */
    private Vec3D i = Vec3D.a(0.0D, 0.0D, 0.0D);

    /**
     * Specifically, if a wooden door block is even considered to be passable by the pathfinder
     */
    private boolean j = true;

    /** If door blocks are considered passable even when closed */
    private boolean k = false;

    /** If water blocks are avoided (at least by the pathfinder) */
    private boolean l = false;

    /**
     * If the entity can swim. Swimming AI enables this and the pathfinder will also cause the entity to swim straight
     * upwards when underwater
     */
    private boolean m = false;

    public Navigation(EntityLiving par1EntityLiving, World par2World, float par3)
    {
        this.a = par1EntityLiving;
        this.b = par2World;
        this.e = par3;
    }

    public void a(boolean par1)
    {
        this.l = par1;
    }

    public boolean a()
    {
        return this.l;
    }

    public void b(boolean par1)
    {
        this.k = par1;
    }

    /**
     * Sets if the entity can enter open doors
     */
    public void c(boolean par1)
    {
        this.j = par1;
    }

    /**
     * Returns true if the entity can break doors, false otherwise
     */
    public boolean c()
    {
        return this.k;
    }

    /**
     * Sets if the path should avoid sunlight
     */
    public void d(boolean par1)
    {
        this.f = par1;
    }

    /**
     * Sets the speed
     */
    public void a(float par1)
    {
        this.d = par1;
    }

    /**
     * Sets if the entity can swim
     */
    public void e(boolean par1)
    {
        this.m = par1;
    }

    /**
     * Returns the path to the given coordinates
     */
    public PathEntity a(double par1, double par3, double par5)
    {
        return !this.k() ? null : this.b.a(this.a, MathHelper.floor(par1), (int) par3, MathHelper.floor(par5), this.e, this.j, this.k, this.l, this.m);
    }

    /**
     * Try to find and set a path to XYZ. Returns true if successful.
     */
    public boolean a(double par1, double par3, double par5, float par7)
    {
        PathEntity var8 = this.a((double) MathHelper.floor(par1), (double) ((int) par3), (double) MathHelper.floor(par5));
        return this.a(var8, par7);
    }

    /**
     * Returns the path to the given EntityLiving
     */
    public PathEntity a(EntityLiving par1EntityLiving)
    {
        return !this.k() ? null : this.b.findPath(this.a, par1EntityLiving, this.e, this.j, this.k, this.l, this.m);
    }

    /**
     * Try to find and set a path to EntityLiving. Returns true if successful.
     */
    public boolean a(EntityLiving par1EntityLiving, float par2)
    {
        PathEntity var3 = this.a(par1EntityLiving);
        return var3 != null ? this.a(var3, par2) : false;
    }

    /**
     * sets the active path data if path is 100% unique compared to old path, checks to adjust path for sun avoiding
     * ents and stores end coords
     */
    public boolean a(PathEntity par1PathEntity, float par2)
    {
        if (par1PathEntity == null)
        {
            this.c = null;
            return false;
        }
        else
        {
            if (!par1PathEntity.a(this.c))
            {
                this.c = par1PathEntity;
            }

            if (this.f)
            {
                this.m();
            }

            if (this.c.d() == 0)
            {
                return false;
            }
            else
            {
                this.d = par2;
                Vec3D var3 = this.i();
                this.h = this.g;
                this.i.c = var3.c;
                this.i.d = var3.d;
                this.i.e = var3.e;
                return true;
            }
        }
    }

    /**
     * gets the actively used PathEntity
     */
    public PathEntity d()
    {
        return this.c;
    }

    public void e()
    {
        ++this.g;

        if (!this.f())
        {
            if (this.k())
            {
                this.h();
            }

            if (!this.f())
            {
                Vec3D var1 = this.c.a(this.a);

                if (var1 != null)
                {
                    this.a.getControllerMove().a(var1.c, var1.d, var1.e, this.d);
                }
            }
        }
    }

    private void h()
    {
        Vec3D var1 = this.i();
        int var2 = this.c.d();

        for (int var3 = this.c.e(); var3 < this.c.d(); ++var3)
        {
            if (this.c.a(var3).b != (int)var1.d)
            {
                var2 = var3;
                break;
            }
        }

        float var8 = this.a.width * this.a.width;
        int var4;

        for (var4 = this.c.e(); var4 < var2; ++var4)
        {
            if (var1.distanceSquared(this.c.a(this.a, var4)) < (double)var8)
            {
                this.c.c(var4 + 1);
            }
        }

        var4 = MathHelper.f(this.a.width);
        int var5 = (int)this.a.length + 1;
        int var6 = var4;

        for (int var7 = var2 - 1; var7 >= this.c.e(); --var7)
        {
            if (this.a(var1, this.c.a(this.a, var7), var4, var5, var6))
            {
                this.c.c(var7);
                break;
            }
        }

        if (this.g - this.h > 100)
        {
            if (var1.distanceSquared(this.i) < 2.25D)
            {
                this.g();
            }

            this.h = this.g;
            this.i.c = var1.c;
            this.i.d = var1.d;
            this.i.e = var1.e;
        }
    }

    /**
     * If null path or reached the end
     */
    public boolean f()
    {
        return this.c == null || this.c.b();
    }

    /**
     * sets active PathEntity to null
     */
    public void g()
    {
        this.c = null;
    }

    private Vec3D i()
    {
        return this.b.getVec3DPool().create(this.a.locX, (double) this.j(), this.a.locZ);
    }

    /**
     * Gets the safe pathing Y position for the entity depending on if it can path swim or not
     */
    private int j()
    {
        if (this.a.H() && this.m)
        {
            int var1 = (int)this.a.boundingBox.b;
            int var2 = this.b.getTypeId(MathHelper.floor(this.a.locX), var1, MathHelper.floor(this.a.locZ));
            int var3 = 0;

            do
            {
                if (var2 != Block.WATER.id && var2 != Block.STATIONARY_WATER.id)
                {
                    return var1;
                }

                ++var1;
                var2 = this.b.getTypeId(MathHelper.floor(this.a.locX), var1, MathHelper.floor(this.a.locZ));
                ++var3;
            }
            while (var3 <= 16);

            return (int)this.a.boundingBox.b;
        }
        else
        {
            return (int)(this.a.boundingBox.b + 0.5D);
        }
    }

    /**
     * If on ground or swimming and can swim
     */
    private boolean k()
    {
        return this.a.onGround || this.m && this.l();
    }

    /**
     * Returns true if the entity is in water or lava, false otherwise
     */
    private boolean l()
    {
        return this.a.H() || this.a.J();
    }

    /**
     * Trims path data from the end to the first sun covered block
     */
    private void m()
    {
        if (!this.b.k(MathHelper.floor(this.a.locX), (int) (this.a.boundingBox.b + 0.5D), MathHelper.floor(this.a.locZ)))
        {
            for (int var1 = 0; var1 < this.c.d(); ++var1)
            {
                PathPoint var2 = this.c.a(var1);

                if (this.b.k(var2.a, var2.b, var2.c))
                {
                    this.c.b(var1 - 1);
                    return;
                }
            }
        }
    }

    /**
     * Returns true when an entity of specified size could safely walk in a straight line between the two points. Args:
     * pos1, pos2, entityXSize, entityYSize, entityZSize
     */
    private boolean a(Vec3D par1Vec3, Vec3D par2Vec3, int par3, int par4, int par5)
    {
        int var6 = MathHelper.floor(par1Vec3.c);
        int var7 = MathHelper.floor(par1Vec3.e);
        double var8 = par2Vec3.c - par1Vec3.c;
        double var10 = par2Vec3.e - par1Vec3.e;
        double var12 = var8 * var8 + var10 * var10;

        if (var12 < 1.0E-8D)
        {
            return false;
        }
        else
        {
            double var14 = 1.0D / Math.sqrt(var12);
            var8 *= var14;
            var10 *= var14;
            par3 += 2;
            par5 += 2;

            if (!this.a(var6, (int) par1Vec3.d, var7, par3, par4, par5, par1Vec3, var8, var10))
            {
                return false;
            }
            else
            {
                par3 -= 2;
                par5 -= 2;
                double var16 = 1.0D / Math.abs(var8);
                double var18 = 1.0D / Math.abs(var10);
                double var20 = (double)(var6 * 1) - par1Vec3.c;
                double var22 = (double)(var7 * 1) - par1Vec3.e;

                if (var8 >= 0.0D)
                {
                    ++var20;
                }

                if (var10 >= 0.0D)
                {
                    ++var22;
                }

                var20 /= var8;
                var22 /= var10;
                int var24 = var8 < 0.0D ? -1 : 1;
                int var25 = var10 < 0.0D ? -1 : 1;
                int var26 = MathHelper.floor(par2Vec3.c);
                int var27 = MathHelper.floor(par2Vec3.e);
                int var28 = var26 - var6;
                int var29 = var27 - var7;

                do
                {
                    if (var28 * var24 <= 0 && var29 * var25 <= 0)
                    {
                        return true;
                    }

                    if (var20 < var22)
                    {
                        var20 += var16;
                        var6 += var24;
                        var28 = var26 - var6;
                    }
                    else
                    {
                        var22 += var18;
                        var7 += var25;
                        var29 = var27 - var7;
                    }
                }
                while (this.a(var6, (int) par1Vec3.d, var7, par3, par4, par5, par1Vec3, var8, var10));

                return false;
            }
        }
    }

    /**
     * Returns true when an entity could stand at a position, including solid blocks under the entire entity. Args:
     * xOffset, yOffset, zOffset, entityXSize, entityYSize, entityZSize, originPosition, vecX, vecZ
     */
    private boolean a(int par1, int par2, int par3, int par4, int par5, int par6, Vec3D par7Vec3, double par8, double par10)
    {
        int var12 = par1 - par4 / 2;
        int var13 = par3 - par6 / 2;

        if (!this.b(var12, par2, var13, par4, par5, par6, par7Vec3, par8, par10))
        {
            return false;
        }
        else
        {
            for (int var14 = var12; var14 < var12 + par4; ++var14)
            {
                for (int var15 = var13; var15 < var13 + par6; ++var15)
                {
                    double var16 = (double)var14 + 0.5D - par7Vec3.c;
                    double var18 = (double)var15 + 0.5D - par7Vec3.e;

                    if (var16 * par8 + var18 * par10 >= 0.0D)
                    {
                        int var20 = this.b.getTypeId(var14, par2 - 1, var15);

                        if (var20 <= 0)
                        {
                            return false;
                        }

                        Material var21 = Block.byId[var20].material;

                        if (var21 == Material.WATER && !this.a.H())
                        {
                            return false;
                        }

                        if (var21 == Material.LAVA)
                        {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    /**
     * Returns true if an entity does not collide with any solid blocks at the position. Args: xOffset, yOffset,
     * zOffset, entityXSize, entityYSize, entityZSize, originPosition, vecX, vecZ
     */
    private boolean b(int par1, int par2, int par3, int par4, int par5, int par6, Vec3D par7Vec3, double par8, double par10)
    {
        for (int var12 = par1; var12 < par1 + par4; ++var12)
        {
            for (int var13 = par2; var13 < par2 + par5; ++var13)
            {
                for (int var14 = par3; var14 < par3 + par6; ++var14)
                {
                    double var15 = (double)var12 + 0.5D - par7Vec3.c;
                    double var17 = (double)var14 + 0.5D - par7Vec3.e;

                    if (var15 * par8 + var17 * par10 >= 0.0D)
                    {
                        int var19 = this.b.getTypeId(var12, var13, var14);

                        if (var19 > 0 && !Block.byId[var19].c(this.b, var12, var13, var14))
                        {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
