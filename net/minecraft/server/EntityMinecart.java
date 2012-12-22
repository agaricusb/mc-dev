package net.minecraft.server;

import java.util.List;

public class EntityMinecart extends Entity implements IInventory
{
    /** Array of item stacks stored in minecart (for storage minecarts). */
    private ItemStack[] items;
    private int e;
    private boolean f;

    /** The type of minecart, 2 for powered, 1 for storage. */
    public int type;
    public double b;
    public double c;
    private final IUpdatePlayerListBox g;
    private boolean h;

    /** Minecart rotational logic matrix */
    private static final int[][][] matrix = new int[][][] {{{0, 0, -1}, {0, 0, 1}}, {{ -1, 0, 0}, {1, 0, 0}}, {{ -1, -1, 0}, {1, 0, 0}}, {{ -1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, { -1, 0, 0}}, {{0, 0, -1}, { -1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};

    /** appears to be the progress of the turn */
    private int j;
    private double at;
    private double au;
    private double av;
    private double aw;
    private double ax;

    public EntityMinecart(World par1World)
    {
        super(par1World);
        this.items = new ItemStack[36];
        this.e = 0;
        this.f = false;
        this.h = true;
        this.m = true;
        this.a(0.98F, 0.7F);
        this.height = this.length / 2.0F;
        this.g = par1World != null ? par1World.a(this) : null;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean f_()
    {
        return false;
    }

    protected void a()
    {
        this.datawatcher.a(16, new Byte((byte) 0));
        this.datawatcher.a(17, new Integer(0));
        this.datawatcher.a(18, new Integer(1));
        this.datawatcher.a(19, new Integer(0));
    }

    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    public AxisAlignedBB g(Entity par1Entity)
    {
        return par1Entity.M() ? par1Entity.boundingBox : null;
    }

    /**
     * returns the bounding box for this entity
     */
    public AxisAlignedBB E()
    {
        return null;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean M()
    {
        return true;
    }

    public EntityMinecart(World par1World, double par2, double par4, double par6, int par8)
    {
        this(par1World);
        this.setPosition(par2, par4 + (double) this.height, par6);
        this.motX = 0.0D;
        this.motY = 0.0D;
        this.motZ = 0.0D;
        this.lastX = par2;
        this.lastY = par4;
        this.lastZ = par6;
        this.type = par8;
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double X()
    {
        return (double)this.length * 0.0D - 0.30000001192092896D;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean damageEntity(DamageSource par1DamageSource, int par2)
    {
        if (!this.world.isStatic && !this.dead)
        {
            if (this.isInvulnerable())
            {
                return false;
            }
            else
            {
                this.i(-this.k());
                this.h(10);
                this.K();
                this.setDamage(this.getDamage() + par2 * 10);

                if (par1DamageSource.getEntity() instanceof EntityHuman && ((EntityHuman)par1DamageSource.getEntity()).abilities.canInstantlyBuild)
                {
                    this.setDamage(100);
                }

                if (this.getDamage() > 40)
                {
                    if (this.passenger != null)
                    {
                        this.passenger.mount(this);
                    }

                    this.die();
                    this.a(Item.MINECART.id, 1, 0.0F);

                    if (this.type == 1)
                    {
                        EntityMinecart var3 = this;

                        for (int var4 = 0; var4 < var3.getSize(); ++var4)
                        {
                            ItemStack var5 = var3.getItem(var4);

                            if (var5 != null)
                            {
                                float var6 = this.random.nextFloat() * 0.8F + 0.1F;
                                float var7 = this.random.nextFloat() * 0.8F + 0.1F;
                                float var8 = this.random.nextFloat() * 0.8F + 0.1F;

                                while (var5.count > 0)
                                {
                                    int var9 = this.random.nextInt(21) + 10;

                                    if (var9 > var5.count)
                                    {
                                        var9 = var5.count;
                                    }

                                    var5.count -= var9;
                                    EntityItem var10 = new EntityItem(this.world, this.locX + (double)var6, this.locY + (double)var7, this.locZ + (double)var8, new ItemStack(var5.id, var9, var5.getData()));
                                    float var11 = 0.05F;
                                    var10.motX = (double)((float)this.random.nextGaussian() * var11);
                                    var10.motY = (double)((float)this.random.nextGaussian() * var11 + 0.2F);
                                    var10.motZ = (double)((float)this.random.nextGaussian() * var11);
                                    this.world.addEntity(var10);
                                }
                            }
                        }

                        this.a(Block.CHEST.id, 1, 0.0F);
                    }
                    else if (this.type == 2)
                    {
                        this.a(Block.FURNACE.id, 1, 0.0F);
                    }
                }

                return true;
            }
        }
        else
        {
            return true;
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean L()
    {
        return !this.dead;
    }

    /**
     * Will get destroyed next tick.
     */
    public void die()
    {
        if (this.h)
        {
            for (int var1 = 0; var1 < this.getSize(); ++var1)
            {
                ItemStack var2 = this.getItem(var1);

                if (var2 != null)
                {
                    float var3 = this.random.nextFloat() * 0.8F + 0.1F;
                    float var4 = this.random.nextFloat() * 0.8F + 0.1F;
                    float var5 = this.random.nextFloat() * 0.8F + 0.1F;

                    while (var2.count > 0)
                    {
                        int var6 = this.random.nextInt(21) + 10;

                        if (var6 > var2.count)
                        {
                            var6 = var2.count;
                        }

                        var2.count -= var6;
                        EntityItem var7 = new EntityItem(this.world, this.locX + (double)var3, this.locY + (double)var4, this.locZ + (double)var5, new ItemStack(var2.id, var6, var2.getData()));

                        if (var2.hasTag())
                        {
                            var7.getItemStack().setTag((NBTTagCompound) var2.getTag().clone());
                        }

                        float var8 = 0.05F;
                        var7.motX = (double)((float)this.random.nextGaussian() * var8);
                        var7.motY = (double)((float)this.random.nextGaussian() * var8 + 0.2F);
                        var7.motZ = (double)((float)this.random.nextGaussian() * var8);
                        this.world.addEntity(var7);
                    }
                }
            }
        }

        super.die();

        if (this.g != null)
        {
            this.g.a();
        }
    }

    public void b(int par1)
    {
        this.h = false;
        super.b(par1);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        if (this.g != null)
        {
            this.g.a();
        }

        if (this.j() > 0)
        {
            this.h(this.j() - 1);
        }

        if (this.getDamage() > 0)
        {
            this.setDamage(this.getDamage() - 1);
        }

        if (this.locY < -64.0D)
        {
            this.C();
        }

        if (this.h() && this.random.nextInt(4) == 0)
        {
            this.world.addParticle("largesmoke", this.locX, this.locY + 0.8D, this.locZ, 0.0D, 0.0D, 0.0D);
        }

        int var2;

        if (!this.world.isStatic && this.world instanceof WorldServer)
        {
            this.world.methodProfiler.a("portal");
            MinecraftServer var1 = ((WorldServer)this.world).getMinecraftServer();
            var2 = this.z();

            if (this.ao)
            {
                if (var1.getAllowNether())
                {
                    if (this.vehicle == null && this.ap++ >= var2)
                    {
                        this.ap = var2;
                        this.portalCooldown = this.ab();
                        byte var3;

                        if (this.world.worldProvider.dimension == -1)
                        {
                            var3 = 0;
                        }
                        else
                        {
                            var3 = -1;
                        }

                        this.b(var3);
                    }

                    this.ao = false;
                }
            }
            else
            {
                if (this.ap > 0)
                {
                    this.ap -= 4;
                }

                if (this.ap < 0)
                {
                    this.ap = 0;
                }
            }

            if (this.portalCooldown > 0)
            {
                --this.portalCooldown;
            }

            this.world.methodProfiler.b();
        }

        if (this.world.isStatic)
        {
            if (this.j > 0)
            {
                double var46 = this.locX + (this.at - this.locX) / (double)this.j;
                double var48 = this.locY + (this.au - this.locY) / (double)this.j;
                double var5 = this.locZ + (this.av - this.locZ) / (double)this.j;
                double var7 = MathHelper.g(this.aw - (double) this.yaw);
                this.yaw = (float)((double)this.yaw + var7 / (double)this.j);
                this.pitch = (float)((double)this.pitch + (this.ax - (double)this.pitch) / (double)this.j);
                --this.j;
                this.setPosition(var46, var48, var5);
                this.b(this.yaw, this.pitch);
            }
            else
            {
                this.setPosition(this.locX, this.locY, this.locZ);
                this.b(this.yaw, this.pitch);
            }
        }
        else
        {
            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;
            this.motY -= 0.03999999910593033D;
            int var45 = MathHelper.floor(this.locX);
            var2 = MathHelper.floor(this.locY);
            int var47 = MathHelper.floor(this.locZ);

            if (BlockMinecartTrack.e_(this.world, var45, var2 - 1, var47))
            {
                --var2;
            }

            double var4 = 0.4D;
            double var6 = 0.0078125D;
            int var8 = this.world.getTypeId(var45, var2, var47);

            if (BlockMinecartTrack.e(var8))
            {
                this.fallDistance = 0.0F;
                Vec3D var9 = this.a(this.locX, this.locY, this.locZ);
                int var10 = this.world.getData(var45, var2, var47);
                this.locY = (double)var2;
                boolean var11 = false;
                boolean var12 = false;

                if (var8 == Block.GOLDEN_RAIL.id)
                {
                    var11 = (var10 & 8) != 0;
                    var12 = !var11;
                }

                if (((BlockMinecartTrack) Block.byId[var8]).p())
                {
                    var10 &= 7;
                }

                if (var10 >= 2 && var10 <= 5)
                {
                    this.locY = (double)(var2 + 1);
                }

                if (var10 == 2)
                {
                    this.motX -= var6;
                }

                if (var10 == 3)
                {
                    this.motX += var6;
                }

                if (var10 == 4)
                {
                    this.motZ += var6;
                }

                if (var10 == 5)
                {
                    this.motZ -= var6;
                }

                int[][] var13 = matrix[var10];
                double var14 = (double)(var13[1][0] - var13[0][0]);
                double var16 = (double)(var13[1][2] - var13[0][2]);
                double var18 = Math.sqrt(var14 * var14 + var16 * var16);
                double var20 = this.motX * var14 + this.motZ * var16;

                if (var20 < 0.0D)
                {
                    var14 = -var14;
                    var16 = -var16;
                }

                double var22 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
                this.motX = var22 * var14 / var18;
                this.motZ = var22 * var16 / var18;
                double var24;
                double var26;

                if (this.passenger != null)
                {
                    var24 = this.passenger.motX * this.passenger.motX + this.passenger.motZ * this.passenger.motZ;
                    var26 = this.motX * this.motX + this.motZ * this.motZ;

                    if (var24 > 1.0E-4D && var26 < 0.01D)
                    {
                        this.motX += this.passenger.motX * 0.1D;
                        this.motZ += this.passenger.motZ * 0.1D;
                        var12 = false;
                    }
                }

                if (var12)
                {
                    var24 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);

                    if (var24 < 0.03D)
                    {
                        this.motX *= 0.0D;
                        this.motY *= 0.0D;
                        this.motZ *= 0.0D;
                    }
                    else
                    {
                        this.motX *= 0.5D;
                        this.motY *= 0.0D;
                        this.motZ *= 0.5D;
                    }
                }

                var24 = 0.0D;
                var26 = (double)var45 + 0.5D + (double)var13[0][0] * 0.5D;
                double var28 = (double)var47 + 0.5D + (double)var13[0][2] * 0.5D;
                double var30 = (double)var45 + 0.5D + (double)var13[1][0] * 0.5D;
                double var32 = (double)var47 + 0.5D + (double)var13[1][2] * 0.5D;
                var14 = var30 - var26;
                var16 = var32 - var28;
                double var34;
                double var36;

                if (var14 == 0.0D)
                {
                    this.locX = (double)var45 + 0.5D;
                    var24 = this.locZ - (double)var47;
                }
                else if (var16 == 0.0D)
                {
                    this.locZ = (double)var47 + 0.5D;
                    var24 = this.locX - (double)var45;
                }
                else
                {
                    var34 = this.locX - var26;
                    var36 = this.locZ - var28;
                    var24 = (var34 * var14 + var36 * var16) * 2.0D;
                }

                this.locX = var26 + var14 * var24;
                this.locZ = var28 + var16 * var24;
                this.setPosition(this.locX, this.locY + (double) this.height, this.locZ);
                var34 = this.motX;
                var36 = this.motZ;

                if (this.passenger != null)
                {
                    var34 *= 0.75D;
                    var36 *= 0.75D;
                }

                if (var34 < -var4)
                {
                    var34 = -var4;
                }

                if (var34 > var4)
                {
                    var34 = var4;
                }

                if (var36 < -var4)
                {
                    var36 = -var4;
                }

                if (var36 > var4)
                {
                    var36 = var4;
                }

                this.move(var34, 0.0D, var36);

                if (var13[0][1] != 0 && MathHelper.floor(this.locX) - var45 == var13[0][0] && MathHelper.floor(this.locZ) - var47 == var13[0][2])
                {
                    this.setPosition(this.locX, this.locY + (double) var13[0][1], this.locZ);
                }
                else if (var13[1][1] != 0 && MathHelper.floor(this.locX) - var45 == var13[1][0] && MathHelper.floor(this.locZ) - var47 == var13[1][2])
                {
                    this.setPosition(this.locX, this.locY + (double) var13[1][1], this.locZ);
                }

                if (this.passenger != null)
                {
                    this.motX *= 0.996999979019165D;
                    this.motY *= 0.0D;
                    this.motZ *= 0.996999979019165D;
                }
                else
                {
                    if (this.type == 2)
                    {
                        double var38 = this.b * this.b + this.c * this.c;

                        if (var38 > 1.0E-4D)
                        {
                            var38 = (double) MathHelper.sqrt(var38);
                            this.b /= var38;
                            this.c /= var38;
                            double var40 = 0.04D;
                            this.motX *= 0.800000011920929D;
                            this.motY *= 0.0D;
                            this.motZ *= 0.800000011920929D;
                            this.motX += this.b * var40;
                            this.motZ += this.c * var40;
                        }
                        else
                        {
                            this.motX *= 0.8999999761581421D;
                            this.motY *= 0.0D;
                            this.motZ *= 0.8999999761581421D;
                        }
                    }

                    this.motX *= 0.9599999785423279D;
                    this.motY *= 0.0D;
                    this.motZ *= 0.9599999785423279D;
                }

                Vec3D var54 = this.a(this.locX, this.locY, this.locZ);

                if (var54 != null && var9 != null)
                {
                    double var39 = (var9.d - var54.d) * 0.05D;
                    var22 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);

                    if (var22 > 0.0D)
                    {
                        this.motX = this.motX / var22 * (var22 + var39);
                        this.motZ = this.motZ / var22 * (var22 + var39);
                    }

                    this.setPosition(this.locX, var54.d, this.locZ);
                }

                int var53 = MathHelper.floor(this.locX);
                int var55 = MathHelper.floor(this.locZ);

                if (var53 != var45 || var55 != var47)
                {
                    var22 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
                    this.motX = var22 * (double)(var53 - var45);
                    this.motZ = var22 * (double)(var55 - var47);
                }

                double var41;

                if (this.type == 2)
                {
                    var41 = this.b * this.b + this.c * this.c;

                    if (var41 > 1.0E-4D && this.motX * this.motX + this.motZ * this.motZ > 0.001D)
                    {
                        var41 = (double) MathHelper.sqrt(var41);
                        this.b /= var41;
                        this.c /= var41;

                        if (this.b * this.motX + this.c * this.motZ < 0.0D)
                        {
                            this.b = 0.0D;
                            this.c = 0.0D;
                        }
                        else
                        {
                            this.b = this.motX;
                            this.c = this.motZ;
                        }
                    }
                }

                if (var11)
                {
                    var41 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);

                    if (var41 > 0.01D)
                    {
                        double var43 = 0.06D;
                        this.motX += this.motX / var41 * var43;
                        this.motZ += this.motZ / var41 * var43;
                    }
                    else if (var10 == 1)
                    {
                        if (this.world.t(var45 - 1, var2, var47))
                        {
                            this.motX = 0.02D;
                        }
                        else if (this.world.t(var45 + 1, var2, var47))
                        {
                            this.motX = -0.02D;
                        }
                    }
                    else if (var10 == 0)
                    {
                        if (this.world.t(var45, var2, var47 - 1))
                        {
                            this.motZ = 0.02D;
                        }
                        else if (this.world.t(var45, var2, var47 + 1))
                        {
                            this.motZ = -0.02D;
                        }
                    }
                }
            }
            else
            {
                if (this.motX < -var4)
                {
                    this.motX = -var4;
                }

                if (this.motX > var4)
                {
                    this.motX = var4;
                }

                if (this.motZ < -var4)
                {
                    this.motZ = -var4;
                }

                if (this.motZ > var4)
                {
                    this.motZ = var4;
                }

                if (this.onGround)
                {
                    this.motX *= 0.5D;
                    this.motY *= 0.5D;
                    this.motZ *= 0.5D;
                }

                this.move(this.motX, this.motY, this.motZ);

                if (!this.onGround)
                {
                    this.motX *= 0.949999988079071D;
                    this.motY *= 0.949999988079071D;
                    this.motZ *= 0.949999988079071D;
                }
            }

            this.D();
            this.pitch = 0.0F;
            double var49 = this.lastX - this.locX;
            double var50 = this.lastZ - this.locZ;

            if (var49 * var49 + var50 * var50 > 0.001D)
            {
                this.yaw = (float)(Math.atan2(var50, var49) * 180.0D / Math.PI);

                if (this.f)
                {
                    this.yaw += 180.0F;
                }
            }

            double var51 = (double) MathHelper.g(this.yaw - this.lastYaw);

            if (var51 < -170.0D || var51 >= 170.0D)
            {
                this.yaw += 180.0F;
                this.f = !this.f;
            }

            this.b(this.yaw, this.pitch);
            List var15 = this.world.getEntities(this, this.boundingBox.grow(0.20000000298023224D, 0.0D, 0.20000000298023224D));

            if (var15 != null && !var15.isEmpty())
            {
                for (int var52 = 0; var52 < var15.size(); ++var52)
                {
                    Entity var17 = (Entity)var15.get(var52);

                    if (var17 != this.passenger && var17.M() && var17 instanceof EntityMinecart)
                    {
                        var17.collide(this);
                    }
                }
            }

            if (this.passenger != null && this.passenger.dead)
            {
                if (this.passenger.vehicle == this)
                {
                    this.passenger.vehicle = null;
                }

                this.passenger = null;
            }

            if (this.e > 0)
            {
                --this.e;
            }

            if (this.e <= 0)
            {
                this.b = this.c = 0.0D;
            }

            this.e(this.e > 0);
        }
    }

    public Vec3D a(double par1, double par3, double par5)
    {
        int var7 = MathHelper.floor(par1);
        int var8 = MathHelper.floor(par3);
        int var9 = MathHelper.floor(par5);

        if (BlockMinecartTrack.e_(this.world, var7, var8 - 1, var9))
        {
            --var8;
        }

        int var10 = this.world.getTypeId(var7, var8, var9);

        if (BlockMinecartTrack.e(var10))
        {
            int var11 = this.world.getData(var7, var8, var9);
            par3 = (double)var8;

            if (((BlockMinecartTrack) Block.byId[var10]).p())
            {
                var11 &= 7;
            }

            if (var11 >= 2 && var11 <= 5)
            {
                par3 = (double)(var8 + 1);
            }

            int[][] var12 = matrix[var11];
            double var13 = 0.0D;
            double var15 = (double)var7 + 0.5D + (double)var12[0][0] * 0.5D;
            double var17 = (double)var8 + 0.5D + (double)var12[0][1] * 0.5D;
            double var19 = (double)var9 + 0.5D + (double)var12[0][2] * 0.5D;
            double var21 = (double)var7 + 0.5D + (double)var12[1][0] * 0.5D;
            double var23 = (double)var8 + 0.5D + (double)var12[1][1] * 0.5D;
            double var25 = (double)var9 + 0.5D + (double)var12[1][2] * 0.5D;
            double var27 = var21 - var15;
            double var29 = (var23 - var17) * 2.0D;
            double var31 = var25 - var19;

            if (var27 == 0.0D)
            {
                par1 = (double)var7 + 0.5D;
                var13 = par5 - (double)var9;
            }
            else if (var31 == 0.0D)
            {
                par5 = (double)var9 + 0.5D;
                var13 = par1 - (double)var7;
            }
            else
            {
                double var33 = par1 - var15;
                double var35 = par5 - var19;
                var13 = (var33 * var27 + var35 * var31) * 2.0D;
            }

            par1 = var15 + var27 * var13;
            par3 = var17 + var29 * var13;
            par5 = var19 + var31 * var13;

            if (var29 < 0.0D)
            {
                ++par3;
            }

            if (var29 > 0.0D)
            {
                par3 += 0.5D;
            }

            return this.world.getVec3DPool().create(par1, par3, par5);
        }
        else
        {
            return null;
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setInt("Type", this.type);

        if (this.type == 2)
        {
            par1NBTTagCompound.setDouble("PushX", this.b);
            par1NBTTagCompound.setDouble("PushZ", this.c);
            par1NBTTagCompound.setShort("Fuel", (short) this.e);
        }
        else if (this.type == 1)
        {
            NBTTagList var2 = new NBTTagList();

            for (int var3 = 0; var3 < this.items.length; ++var3)
            {
                if (this.items[var3] != null)
                {
                    NBTTagCompound var4 = new NBTTagCompound();
                    var4.setByte("Slot", (byte) var3);
                    this.items[var3].save(var4);
                    var2.add(var4);
                }
            }

            par1NBTTagCompound.set("Items", var2);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void a(NBTTagCompound par1NBTTagCompound)
    {
        this.type = par1NBTTagCompound.getInt("Type");

        if (this.type == 2)
        {
            this.b = par1NBTTagCompound.getDouble("PushX");
            this.c = par1NBTTagCompound.getDouble("PushZ");
            this.e = par1NBTTagCompound.getShort("Fuel");
        }
        else if (this.type == 1)
        {
            NBTTagList var2 = par1NBTTagCompound.getList("Items");
            this.items = new ItemStack[this.getSize()];

            for (int var3 = 0; var3 < var2.size(); ++var3)
            {
                NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
                int var5 = var4.getByte("Slot") & 255;

                if (var5 >= 0 && var5 < this.items.length)
                {
                    this.items[var5] = ItemStack.a(var4);
                }
            }
        }
    }

    /**
     * Applies a velocity to each of the entities pushing them away from each other. Args: entity
     */
    public void collide(Entity par1Entity)
    {
        if (!this.world.isStatic)
        {
            if (par1Entity != this.passenger)
            {
                if (par1Entity instanceof EntityLiving && !(par1Entity instanceof EntityHuman) && !(par1Entity instanceof EntityIronGolem) && this.type == 0 && this.motX * this.motX + this.motZ * this.motZ > 0.01D && this.passenger == null && par1Entity.vehicle == null)
                {
                    par1Entity.mount(this);
                }

                double var2 = par1Entity.locX - this.locX;
                double var4 = par1Entity.locZ - this.locZ;
                double var6 = var2 * var2 + var4 * var4;

                if (var6 >= 9.999999747378752E-5D)
                {
                    var6 = (double) MathHelper.sqrt(var6);
                    var2 /= var6;
                    var4 /= var6;
                    double var8 = 1.0D / var6;

                    if (var8 > 1.0D)
                    {
                        var8 = 1.0D;
                    }

                    var2 *= var8;
                    var4 *= var8;
                    var2 *= 0.10000000149011612D;
                    var4 *= 0.10000000149011612D;
                    var2 *= (double)(1.0F - this.Z);
                    var4 *= (double)(1.0F - this.Z);
                    var2 *= 0.5D;
                    var4 *= 0.5D;

                    if (par1Entity instanceof EntityMinecart)
                    {
                        double var10 = par1Entity.locX - this.locX;
                        double var12 = par1Entity.locZ - this.locZ;
                        Vec3D var14 = this.world.getVec3DPool().create(var10, 0.0D, var12).a();
                        Vec3D var15 = this.world.getVec3DPool().create((double) MathHelper.cos(this.yaw * (float) Math.PI / 180.0F), 0.0D, (double) MathHelper.sin(this.yaw * (float) Math.PI / 180.0F)).a();
                        double var16 = Math.abs(var14.b(var15));

                        if (var16 < 0.800000011920929D)
                        {
                            return;
                        }

                        double var18 = par1Entity.motX + this.motX;
                        double var20 = par1Entity.motZ + this.motZ;

                        if (((EntityMinecart)par1Entity).type == 2 && this.type != 2)
                        {
                            this.motX *= 0.20000000298023224D;
                            this.motZ *= 0.20000000298023224D;
                            this.g(par1Entity.motX - var2, 0.0D, par1Entity.motZ - var4);
                            par1Entity.motX *= 0.949999988079071D;
                            par1Entity.motZ *= 0.949999988079071D;
                        }
                        else if (((EntityMinecart)par1Entity).type != 2 && this.type == 2)
                        {
                            par1Entity.motX *= 0.20000000298023224D;
                            par1Entity.motZ *= 0.20000000298023224D;
                            par1Entity.g(this.motX + var2, 0.0D, this.motZ + var4);
                            this.motX *= 0.949999988079071D;
                            this.motZ *= 0.949999988079071D;
                        }
                        else
                        {
                            var18 /= 2.0D;
                            var20 /= 2.0D;
                            this.motX *= 0.20000000298023224D;
                            this.motZ *= 0.20000000298023224D;
                            this.g(var18 - var2, 0.0D, var20 - var4);
                            par1Entity.motX *= 0.20000000298023224D;
                            par1Entity.motZ *= 0.20000000298023224D;
                            par1Entity.g(var18 + var2, 0.0D, var20 + var4);
                        }
                    }
                    else
                    {
                        this.g(-var2, 0.0D, -var4);
                        par1Entity.g(var2 / 4.0D, 0.0D, var4 / 4.0D);
                    }
                }
            }
        }
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return 27;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int par1)
    {
        return this.items[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack splitStack(int par1, int par2)
    {
        if (this.items[par1] != null)
        {
            ItemStack var3;

            if (this.items[par1].count <= par2)
            {
                var3 = this.items[par1];
                this.items[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.items[par1].a(par2);

                if (this.items[par1].count == 0)
                {
                    this.items[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack splitWithoutUpdate(int par1)
    {
        if (this.items[par1] != null)
        {
            ItemStack var2 = this.items[par1];
            this.items[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int par1, ItemStack par2ItemStack)
    {
        this.items[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.count > this.getMaxStackSize())
        {
            par2ItemStack.count = this.getMaxStackSize();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return "container.minecart";
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getMaxStackSize()
    {
        return 64;
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void update() {}

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean a(EntityHuman par1EntityPlayer)
    {
        if (this.type == 0)
        {
            if (this.passenger != null && this.passenger instanceof EntityHuman && this.passenger != par1EntityPlayer)
            {
                return true;
            }

            if (!this.world.isStatic)
            {
                par1EntityPlayer.mount(this);
            }
        }
        else if (this.type == 1)
        {
            if (!this.world.isStatic)
            {
                par1EntityPlayer.openContainer(this);
            }
        }
        else if (this.type == 2)
        {
            ItemStack var2 = par1EntityPlayer.inventory.getItemInHand();

            if (var2 != null && var2.id == Item.COAL.id)
            {
                if (--var2.count == 0)
                {
                    par1EntityPlayer.inventory.setItem(par1EntityPlayer.inventory.itemInHandIndex, (ItemStack) null);
                }

                this.e += 3600;
            }

            this.b = this.locX - par1EntityPlayer.locX;
            this.c = this.locZ - par1EntityPlayer.locZ;
        }

        return true;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean a_(EntityHuman par1EntityPlayer)
    {
        return this.dead ? false : par1EntityPlayer.e(this) <= 64.0D;
    }

    /**
     * Is this minecart powered (Fuel > 0)
     */
    protected boolean h()
    {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    /**
     * Set if this minecart is powered (Fuel > 0)
     */
    protected void e(boolean par1)
    {
        if (par1)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) (this.datawatcher.getByte(16) | 1)));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) (this.datawatcher.getByte(16) & -2)));
        }
    }

    public void startOpen() {}

    public void f() {}

    /**
     * Sets the current amount of damage the minecart has taken. Decreases over time. The cart breaks when this is over
     * 40.
     */
    public void setDamage(int par1)
    {
        this.datawatcher.watch(19, Integer.valueOf(par1));
    }

    /**
     * Gets the current amount of damage the minecart has taken. Decreases over time. The cart breaks when this is over
     * 40.
     */
    public int getDamage()
    {
        return this.datawatcher.getInt(19);
    }

    public void h(int par1)
    {
        this.datawatcher.watch(17, Integer.valueOf(par1));
    }

    public int j()
    {
        return this.datawatcher.getInt(17);
    }

    public void i(int par1)
    {
        this.datawatcher.watch(18, Integer.valueOf(par1));
    }

    public int k()
    {
        return this.datawatcher.getInt(18);
    }
}
