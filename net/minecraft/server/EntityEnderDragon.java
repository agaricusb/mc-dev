package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class EntityEnderDragon extends EntityLiving implements IComplex
{
    public double a;
    public double b;
    public double c;

    /**
     * Ring buffer array for the last 64 Y-positions and yaw rotations. Used to calculate offsets for the animations.
     */
    public double[][] d = new double[64][3];

    /**
     * Index into the ring buffer. Incremented once per tick and restarts at 0 once it reaches the end of the buffer.
     */
    public int e = -1;

    /** An array containing all body parts of this dragon */
    public EntityComplexPart[] children;

    /** The head bounding box of a dragon */
    public EntityComplexPart g;

    /** The body bounding box of a dragon */
    public EntityComplexPart h;
    public EntityComplexPart i;
    public EntityComplexPart j;
    public EntityComplexPart bJ;
    public EntityComplexPart bK;
    public EntityComplexPart bL;

    /** Animation time at previous tick. */
    public float bM = 0.0F;

    /**
     * Animation time, used to control the speed of the animation cycles (wings flapping, jaw opening, etc.)
     */
    public float bN = 0.0F;

    /** Force selecting a new flight target at next tick if set to true. */
    public boolean bO = false;

    /**
     * Activated if the dragon is flying though obsidian, white stone or bedrock. Slows movement and animation speed.
     */
    public boolean bP = false;
    private Entity bS;
    public int bQ = 0;

    /** The current endercrystal that is healing this dragon */
    public EntityEnderCrystal bR = null;

    public EntityEnderDragon(World par1World)
    {
        super(par1World);
        this.children = new EntityComplexPart[] {this.g = new EntityComplexPart(this, "head", 6.0F, 6.0F), this.h = new EntityComplexPart(this, "body", 8.0F, 8.0F), this.i = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.j = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bJ = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bK = new EntityComplexPart(this, "wing", 4.0F, 4.0F), this.bL = new EntityComplexPart(this, "wing", 4.0F, 4.0F)};
        this.setHealth(this.getMaxHealth());
        this.texture = "/mob/enderdragon/ender.png";
        this.a(16.0F, 8.0F);
        this.Y = true;
        this.fireProof = true;
        this.b = 100.0D;
        this.al = true;
    }

    public int getMaxHealth()
    {
        return 200;
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(16, new Integer(this.getMaxHealth()));
    }

    /**
     * Returns a double[3] array with movement offsets, used to calculate trailing tail/neck positions. [0] = yaw
     * offset, [1] = y offset, [2] = unused, always 0. Parameters: buffer index offset, partial ticks.
     */
    public double[] a(int par1, float par2)
    {
        if (this.health <= 0)
        {
            par2 = 0.0F;
        }

        par2 = 1.0F - par2;
        int var3 = this.e - par1 * 1 & 63;
        int var4 = this.e - par1 * 1 - 1 & 63;
        double[] var5 = new double[3];
        double var6 = this.d[var3][0];
        double var8 = MathHelper.g(this.d[var4][0] - var6);
        var5[0] = var6 + var8 * (double)par2;
        var6 = this.d[var3][1];
        var8 = this.d[var4][1] - var6;
        var5[1] = var6 + var8 * (double)par2;
        var5[2] = this.d[var3][2] + (this.d[var4][2] - this.d[var3][2]) * (double)par2;
        return var5;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        float var1;
        float var2;

        if (!this.world.isStatic)
        {
            this.datawatcher.watch(16, Integer.valueOf(this.health));
        }
        else
        {
            var1 = MathHelper.cos(this.bN * (float) Math.PI * 2.0F);
            var2 = MathHelper.cos(this.bM * (float) Math.PI * 2.0F);

            if (var2 <= -0.3F && var1 >= -0.3F)
            {
                this.world.a(this.locX, this.locY, this.locZ, "mob.enderdragon.wings", 5.0F, 0.8F + this.random.nextFloat() * 0.3F, false);
            }
        }

        this.bM = this.bN;
        float var3;

        if (this.health <= 0)
        {
            var1 = (this.random.nextFloat() - 0.5F) * 8.0F;
            var2 = (this.random.nextFloat() - 0.5F) * 4.0F;
            var3 = (this.random.nextFloat() - 0.5F) * 8.0F;
            this.world.addParticle("largeexplode", this.locX + (double) var1, this.locY + 2.0D + (double) var2, this.locZ + (double) var3, 0.0D, 0.0D, 0.0D);
        }
        else
        {
            this.h();
            var1 = 0.2F / (MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 10.0F + 1.0F);
            var1 *= (float)Math.pow(2.0D, this.motY);

            if (this.bP)
            {
                this.bN += var1 * 0.5F;
            }
            else
            {
                this.bN += var1;
            }

            this.yaw = MathHelper.g(this.yaw);

            if (this.e < 0)
            {
                for (int var25 = 0; var25 < this.d.length; ++var25)
                {
                    this.d[var25][0] = (double)this.yaw;
                    this.d[var25][1] = this.locY;
                }
            }

            if (++this.e == this.d.length)
            {
                this.e = 0;
            }

            this.d[this.e][0] = (double)this.yaw;
            this.d[this.e][1] = this.locY;
            double var4;
            double var6;
            double var8;
            double var26;
            float var33;

            if (this.world.isStatic)
            {
                if (this.bt > 0)
                {
                    var26 = this.locX + (this.bu - this.locX) / (double)this.bt;
                    var4 = this.locY + (this.bv - this.locY) / (double)this.bt;
                    var6 = this.locZ + (this.bw - this.locZ) / (double)this.bt;
                    var8 = MathHelper.g(this.bx - (double) this.yaw);
                    this.yaw = (float)((double)this.yaw + var8 / (double)this.bt);
                    this.pitch = (float)((double)this.pitch + (this.by - (double)this.pitch) / (double)this.bt);
                    --this.bt;
                    this.setPosition(var26, var4, var6);
                    this.b(this.yaw, this.pitch);
                }
            }
            else
            {
                var26 = this.a - this.locX;
                var4 = this.b - this.locY;
                var6 = this.c - this.locZ;
                var8 = var26 * var26 + var4 * var4 + var6 * var6;

                if (this.bS != null)
                {
                    this.a = this.bS.locX;
                    this.c = this.bS.locZ;
                    double var10 = this.a - this.locX;
                    double var12 = this.c - this.locZ;
                    double var14 = Math.sqrt(var10 * var10 + var12 * var12);
                    double var16 = 0.4000000059604645D + var14 / 80.0D - 1.0D;

                    if (var16 > 10.0D)
                    {
                        var16 = 10.0D;
                    }

                    this.b = this.bS.boundingBox.b + var16;
                }
                else
                {
                    this.a += this.random.nextGaussian() * 2.0D;
                    this.c += this.random.nextGaussian() * 2.0D;
                }

                if (this.bO || var8 < 100.0D || var8 > 22500.0D || this.positionChanged || this.G)
                {
                    this.i();
                }

                var4 /= (double) MathHelper.sqrt(var26 * var26 + var6 * var6);
                var33 = 0.6F;

                if (var4 < (double)(-var33))
                {
                    var4 = (double)(-var33);
                }

                if (var4 > (double)var33)
                {
                    var4 = (double)var33;
                }

                this.motY += var4 * 0.10000000149011612D;
                this.yaw = MathHelper.g(this.yaw);
                double var11 = 180.0D - Math.atan2(var26, var6) * 180.0D / Math.PI;
                double var13 = MathHelper.g(var11 - (double) this.yaw);

                if (var13 > 50.0D)
                {
                    var13 = 50.0D;
                }

                if (var13 < -50.0D)
                {
                    var13 = -50.0D;
                }

                Vec3D var15 = this.world.getVec3DPool().create(this.a - this.locX, this.b - this.locY, this.c - this.locZ).a();
                Vec3D var40 = this.world.getVec3DPool().create((double) MathHelper.sin(this.yaw * (float) Math.PI / 180.0F), this.motY, (double) (-MathHelper.cos(this.yaw * (float) Math.PI / 180.0F))).a();
                float var17 = (float)(var40.b(var15) + 0.5D) / 1.5F;

                if (var17 < 0.0F)
                {
                    var17 = 0.0F;
                }

                this.bE *= 0.8F;
                float var18 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 1.0F + 1.0F;
                double var19 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 1.0D + 1.0D;

                if (var19 > 40.0D)
                {
                    var19 = 40.0D;
                }

                this.bE = (float)((double)this.bE + var13 * (0.699999988079071D / var19 / (double)var18));
                this.yaw += this.bE * 0.1F;
                float var21 = (float)(2.0D / (var19 + 1.0D));
                float var22 = 0.06F;
                this.a(0.0F, -1.0F, var22 * (var17 * var21 + (1.0F - var21)));

                if (this.bP)
                {
                    this.move(this.motX * 0.800000011920929D, this.motY * 0.800000011920929D, this.motZ * 0.800000011920929D);
                }
                else
                {
                    this.move(this.motX, this.motY, this.motZ);
                }

                Vec3D var23 = this.world.getVec3DPool().create(this.motX, this.motY, this.motZ).a();
                float var24 = (float)(var23.b(var40) + 1.0D) / 2.0F;
                var24 = 0.8F + 0.15F * var24;
                this.motX *= (double)var24;
                this.motZ *= (double)var24;
                this.motY *= 0.9100000262260437D;
            }

            this.ax = this.yaw;
            this.g.width = this.g.length = 3.0F;
            this.i.width = this.i.length = 2.0F;
            this.j.width = this.j.length = 2.0F;
            this.bJ.width = this.bJ.length = 2.0F;
            this.h.length = 3.0F;
            this.h.width = 5.0F;
            this.bK.length = 2.0F;
            this.bK.width = 4.0F;
            this.bL.length = 3.0F;
            this.bL.width = 4.0F;
            var2 = (float)(this.a(5, 1.0F)[1] - this.a(10, 1.0F)[1]) * 10.0F / 180.0F * (float)Math.PI;
            var3 = MathHelper.cos(var2);
            float var28 = -MathHelper.sin(var2);
            float var5 = this.yaw * (float)Math.PI / 180.0F;
            float var27 = MathHelper.sin(var5);
            float var7 = MathHelper.cos(var5);
            this.h.j_();
            this.h.setPositionRotation(this.locX + (double) (var27 * 0.5F), this.locY, this.locZ - (double) (var7 * 0.5F), 0.0F, 0.0F);
            this.bK.j_();
            this.bK.setPositionRotation(this.locX + (double) (var7 * 4.5F), this.locY + 2.0D, this.locZ + (double) (var27 * 4.5F), 0.0F, 0.0F);
            this.bL.j_();
            this.bL.setPositionRotation(this.locX - (double) (var7 * 4.5F), this.locY + 2.0D, this.locZ - (double) (var27 * 4.5F), 0.0F, 0.0F);

            if (!this.world.isStatic && this.hurtTicks == 0)
            {
                this.a(this.world.getEntities(this, this.bK.boundingBox.grow(4.0D, 2.0D, 4.0D).d(0.0D, -2.0D, 0.0D)));
                this.a(this.world.getEntities(this, this.bL.boundingBox.grow(4.0D, 2.0D, 4.0D).d(0.0D, -2.0D, 0.0D)));
                this.b(this.world.getEntities(this, this.g.boundingBox.grow(1.0D, 1.0D, 1.0D)));
            }

            double[] var29 = this.a(5, 1.0F);
            double[] var9 = this.a(0, 1.0F);
            var33 = MathHelper.sin(this.yaw * (float) Math.PI / 180.0F - this.bE * 0.01F);
            float var32 = MathHelper.cos(this.yaw * (float) Math.PI / 180.0F - this.bE * 0.01F);
            this.g.j_();
            this.g.setPositionRotation(this.locX + (double) (var33 * 5.5F * var3), this.locY + (var9[1] - var29[1]) * 1.0D + (double) (var28 * 5.5F), this.locZ - (double) (var32 * 5.5F * var3), 0.0F, 0.0F);

            for (int var30 = 0; var30 < 3; ++var30)
            {
                EntityComplexPart var31 = null;

                if (var30 == 0)
                {
                    var31 = this.i;
                }

                if (var30 == 1)
                {
                    var31 = this.j;
                }

                if (var30 == 2)
                {
                    var31 = this.bJ;
                }

                double[] var35 = this.a(12 + var30 * 2, 1.0F);
                float var34 = this.yaw * (float)Math.PI / 180.0F + this.b(var35[0] - var29[0]) * (float)Math.PI / 180.0F * 1.0F;
                float var38 = MathHelper.sin(var34);
                float var37 = MathHelper.cos(var34);
                float var36 = 1.5F;
                float var39 = (float)(var30 + 1) * 2.0F;
                var31.j_();
                var31.setPositionRotation(this.locX - (double) ((var27 * var36 + var38 * var39) * var3), this.locY + (var35[1] - var29[1]) * 1.0D - (double) ((var39 + var36) * var28) + 1.5D, this.locZ + (double) ((var7 * var36 + var37 * var39) * var3), 0.0F, 0.0F);
            }

            if (!this.world.isStatic)
            {
                this.bP = this.a(this.g.boundingBox) | this.a(this.h.boundingBox);
            }
        }
    }

    /**
     * Updates the state of the enderdragon's current endercrystal.
     */
    private void h()
    {
        if (this.bR != null)
        {
            if (this.bR.dead)
            {
                if (!this.world.isStatic)
                {
                    this.a(this.g, DamageSource.EXPLOSION, 10);
                }

                this.bR = null;
            }
            else if (this.ticksLived % 10 == 0 && this.health < this.getMaxHealth())
            {
                ++this.health;
            }
        }

        if (this.random.nextInt(10) == 0)
        {
            float var1 = 32.0F;
            List var2 = this.world.a(EntityEnderCrystal.class, this.boundingBox.grow((double) var1, (double) var1, (double) var1));
            EntityEnderCrystal var3 = null;
            double var4 = Double.MAX_VALUE;
            Iterator var6 = var2.iterator();

            while (var6.hasNext())
            {
                EntityEnderCrystal var7 = (EntityEnderCrystal)var6.next();
                double var8 = var7.e(this);

                if (var8 < var4)
                {
                    var4 = var8;
                    var3 = var7;
                }
            }

            this.bR = var3;
        }
    }

    /**
     * Pushes all entities inside the list away from the enderdragon.
     */
    private void a(List par1List)
    {
        double var2 = (this.h.boundingBox.a + this.h.boundingBox.d) / 2.0D;
        double var4 = (this.h.boundingBox.c + this.h.boundingBox.f) / 2.0D;
        Iterator var6 = par1List.iterator();

        while (var6.hasNext())
        {
            Entity var7 = (Entity)var6.next();

            if (var7 instanceof EntityLiving)
            {
                double var8 = var7.locX - var2;
                double var10 = var7.locZ - var4;
                double var12 = var8 * var8 + var10 * var10;
                var7.g(var8 / var12 * 4.0D, 0.20000000298023224D, var10 / var12 * 4.0D);
            }
        }
    }

    /**
     * Attacks all entities inside this list, dealing 5 hearts of damage.
     */
    private void b(List par1List)
    {
        for (int var2 = 0; var2 < par1List.size(); ++var2)
        {
            Entity var3 = (Entity)par1List.get(var2);

            if (var3 instanceof EntityLiving)
            {
                var3.damageEntity(DamageSource.mobAttack(this), 10);
            }
        }
    }

    /**
     * Sets a new target for the flight AI. It can be a random coordinate or a nearby player.
     */
    private void i()
    {
        this.bO = false;

        if (this.random.nextInt(2) == 0 && !this.world.players.isEmpty())
        {
            this.bS = (Entity)this.world.players.get(this.random.nextInt(this.world.players.size()));
        }
        else
        {
            boolean var1 = false;

            do
            {
                this.a = 0.0D;
                this.b = (double)(70.0F + this.random.nextFloat() * 50.0F);
                this.c = 0.0D;
                this.a += (double)(this.random.nextFloat() * 120.0F - 60.0F);
                this.c += (double)(this.random.nextFloat() * 120.0F - 60.0F);
                double var2 = this.locX - this.a;
                double var4 = this.locY - this.b;
                double var6 = this.locZ - this.c;
                var1 = var2 * var2 + var4 * var4 + var6 * var6 > 100.0D;
            }
            while (!var1);

            this.bS = null;
        }
    }

    /**
     * Simplifies the value of a number by adding/subtracting 180 to the point that the number is between -180 and 180.
     */
    private float b(double par1)
    {
        return (float) MathHelper.g(par1);
    }

    /**
     * Destroys all blocks that aren't associated with 'The End' inside the given bounding box.
     */
    private boolean a(AxisAlignedBB par1AxisAlignedBB)
    {
        int var2 = MathHelper.floor(par1AxisAlignedBB.a);
        int var3 = MathHelper.floor(par1AxisAlignedBB.b);
        int var4 = MathHelper.floor(par1AxisAlignedBB.c);
        int var5 = MathHelper.floor(par1AxisAlignedBB.d);
        int var6 = MathHelper.floor(par1AxisAlignedBB.e);
        int var7 = MathHelper.floor(par1AxisAlignedBB.f);
        boolean var8 = false;
        boolean var9 = false;

        for (int var10 = var2; var10 <= var5; ++var10)
        {
            for (int var11 = var3; var11 <= var6; ++var11)
            {
                for (int var12 = var4; var12 <= var7; ++var12)
                {
                    int var13 = this.world.getTypeId(var10, var11, var12);

                    if (var13 != 0)
                    {
                        if (var13 != Block.OBSIDIAN.id && var13 != Block.WHITESTONE.id && var13 != Block.BEDROCK.id)
                        {
                            var9 = true;
                            this.world.setTypeId(var10, var11, var12, 0);
                        }
                        else
                        {
                            var8 = true;
                        }
                    }
                }
            }
        }

        if (var9)
        {
            double var16 = par1AxisAlignedBB.a + (par1AxisAlignedBB.d - par1AxisAlignedBB.a) * (double)this.random.nextFloat();
            double var17 = par1AxisAlignedBB.b + (par1AxisAlignedBB.e - par1AxisAlignedBB.b) * (double)this.random.nextFloat();
            double var14 = par1AxisAlignedBB.c + (par1AxisAlignedBB.f - par1AxisAlignedBB.c) * (double)this.random.nextFloat();
            this.world.addParticle("largeexplode", var16, var17, var14, 0.0D, 0.0D, 0.0D);
        }

        return var8;
    }

    public boolean a(EntityComplexPart par1EntityDragonPart, DamageSource par2DamageSource, int par3)
    {
        if (par1EntityDragonPart != this.g)
        {
            par3 = par3 / 4 + 1;
        }

        float var4 = this.yaw * (float)Math.PI / 180.0F;
        float var5 = MathHelper.sin(var4);
        float var6 = MathHelper.cos(var4);
        this.a = this.locX + (double)(var5 * 5.0F) + (double)((this.random.nextFloat() - 0.5F) * 2.0F);
        this.b = this.locY + (double)(this.random.nextFloat() * 3.0F) + 1.0D;
        this.c = this.locZ - (double)(var6 * 5.0F) + (double)((this.random.nextFloat() - 0.5F) * 2.0F);
        this.bS = null;

        if (par2DamageSource.getEntity() instanceof EntityHuman || par2DamageSource == DamageSource.EXPLOSION)
        {
            this.dealDamage(par2DamageSource, par3);
        }

        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean damageEntity(DamageSource par1DamageSource, int par2)
    {
        return false;
    }

    protected boolean dealDamage(DamageSource par1DamageSource, int par2)
    {
        return super.damageEntity(par1DamageSource, par2);
    }

    /**
     * handles entity death timer, experience orb and particle creation
     */
    protected void aP()
    {
        ++this.bQ;

        if (this.bQ >= 180 && this.bQ <= 200)
        {
            float var1 = (this.random.nextFloat() - 0.5F) * 8.0F;
            float var2 = (this.random.nextFloat() - 0.5F) * 4.0F;
            float var3 = (this.random.nextFloat() - 0.5F) * 8.0F;
            this.world.addParticle("hugeexplosion", this.locX + (double) var1, this.locY + 2.0D + (double) var2, this.locZ + (double) var3, 0.0D, 0.0D, 0.0D);
        }

        int var4;
        int var5;

        if (!this.world.isStatic)
        {
            if (this.bQ > 150 && this.bQ % 5 == 0)
            {
                var4 = 1000;

                while (var4 > 0)
                {
                    var5 = EntityExperienceOrb.getOrbValue(var4);
                    var4 -= var5;
                    this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, var5));
                }
            }

            if (this.bQ == 1)
            {
                this.world.e(1018, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
            }
        }

        this.move(0.0D, 0.10000000149011612D, 0.0D);
        this.ax = this.yaw += 20.0F;

        if (this.bQ == 200 && !this.world.isStatic)
        {
            var4 = 2000;

            while (var4 > 0)
            {
                var5 = EntityExperienceOrb.getOrbValue(var4);
                var4 -= var5;
                this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, var5));
            }

            this.c(MathHelper.floor(this.locX), MathHelper.floor(this.locZ));
            this.die();
        }
    }

    /**
     * Creates the ender portal leading back to the normal world after defeating the enderdragon.
     */
    private void c(int par1, int par2)
    {
        byte var3 = 64;
        BlockEnderPortal.a = true;
        byte var4 = 4;

        for (int var5 = var3 - 1; var5 <= var3 + 32; ++var5)
        {
            for (int var6 = par1 - var4; var6 <= par1 + var4; ++var6)
            {
                for (int var7 = par2 - var4; var7 <= par2 + var4; ++var7)
                {
                    double var8 = (double)(var6 - par1);
                    double var10 = (double)(var7 - par2);
                    double var12 = var8 * var8 + var10 * var10;

                    if (var12 <= ((double)var4 - 0.5D) * ((double)var4 - 0.5D))
                    {
                        if (var5 < var3)
                        {
                            if (var12 <= ((double)(var4 - 1) - 0.5D) * ((double)(var4 - 1) - 0.5D))
                            {
                                this.world.setTypeId(var6, var5, var7, Block.BEDROCK.id);
                            }
                        }
                        else if (var5 > var3)
                        {
                            this.world.setTypeId(var6, var5, var7, 0);
                        }
                        else if (var12 > ((double)(var4 - 1) - 0.5D) * ((double)(var4 - 1) - 0.5D))
                        {
                            this.world.setTypeId(var6, var5, var7, Block.BEDROCK.id);
                        }
                        else
                        {
                            this.world.setTypeId(var6, var5, var7, Block.ENDER_PORTAL.id);
                        }
                    }
                }
            }
        }

        this.world.setTypeId(par1, var3 + 0, par2, Block.BEDROCK.id);
        this.world.setTypeId(par1, var3 + 1, par2, Block.BEDROCK.id);
        this.world.setTypeId(par1, var3 + 2, par2, Block.BEDROCK.id);
        this.world.setTypeId(par1 - 1, var3 + 2, par2, Block.TORCH.id);
        this.world.setTypeId(par1 + 1, var3 + 2, par2, Block.TORCH.id);
        this.world.setTypeId(par1, var3 + 2, par2 - 1, Block.TORCH.id);
        this.world.setTypeId(par1, var3 + 2, par2 + 1, Block.TORCH.id);
        this.world.setTypeId(par1, var3 + 3, par2, Block.BEDROCK.id);
        this.world.setTypeId(par1, var3 + 4, par2, Block.DRAGON_EGG.id);
        BlockEnderPortal.a = false;
    }

    /**
     * Makes the entity despawn if requirements are reached
     */
    protected void bk() {}

    /**
     * Return the Entity parts making up this Entity (currently only for dragons)
     */
    public Entity[] ao()
    {
        return this.children;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean L()
    {
        return false;
    }

    public World d()
    {
        return this.world;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "mob.enderdragon.growl";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.enderdragon.hit";
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float aX()
    {
        return 5.0F;
    }
}
