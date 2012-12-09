package net.minecraft.server;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EntityTrackerEntry
{
    /** The entity that this EntityTrackerEntry tracks. */
    public Entity tracker;
    public int b;

    /** check for sync when ticks % updateFrequency==0 */
    public int c;

    /** The encoded entity X position. */
    public int xLoc;

    /** The encoded entity Y position. */
    public int yLoc;

    /** The encoded entity Z position. */
    public int zLoc;

    /** The encoded entity yaw rotation. */
    public int yRot;

    /** The encoded entity pitch rotation. */
    public int xRot;
    public int i;
    public double j;
    public double k;
    public double l;
    public int m = 0;
    private double p;
    private double q;
    private double r;
    private boolean s = false;
    private boolean isMoving;

    /**
     * every 400 ticks a  full teleport packet is sent, rather than just a "move me +x" command, so that position
     * remains fully synced.
     */
    private int u = 0;
    private Entity v;
    private boolean w = false;
    public boolean n = false;
    public Set trackedPlayers = new HashSet();

    public EntityTrackerEntry(Entity par1Entity, int par2, int par3, boolean par4)
    {
        this.tracker = par1Entity;
        this.b = par2;
        this.c = par3;
        this.isMoving = par4;
        this.xLoc = MathHelper.floor(par1Entity.locX * 32.0D);
        this.yLoc = MathHelper.floor(par1Entity.locY * 32.0D);
        this.zLoc = MathHelper.floor(par1Entity.locZ * 32.0D);
        this.yRot = MathHelper.d(par1Entity.yaw * 256.0F / 360.0F);
        this.xRot = MathHelper.d(par1Entity.pitch * 256.0F / 360.0F);
        this.i = MathHelper.d(par1Entity.ap() * 256.0F / 360.0F);
    }

    public boolean equals(Object par1Obj)
    {
        return par1Obj instanceof EntityTrackerEntry ? ((EntityTrackerEntry)par1Obj).tracker.id == this.tracker.id : false;
    }

    public int hashCode()
    {
        return this.tracker.id;
    }

    public void track(List par1List)
    {
        this.n = false;

        if (!this.s || this.tracker.e(this.p, this.q, this.r) > 16.0D)
        {
            this.p = this.tracker.locX;
            this.q = this.tracker.locY;
            this.r = this.tracker.locZ;
            this.s = true;
            this.n = true;
            this.scanPlayers(par1List);
        }

        if (this.v != this.tracker.vehicle)
        {
            this.v = this.tracker.vehicle;
            this.broadcast(new Packet39AttachEntity(this.tracker, this.tracker.vehicle));
        }

        if (this.tracker instanceof EntityItemFrame && this.m % 10 == 0)
        {
            EntityItemFrame var23 = (EntityItemFrame)this.tracker;
            ItemStack var24 = var23.i();

            if (var24 != null && var24.getItem() instanceof ItemWorldMap)
            {
                WorldMap var26 = Item.MAP.getSavedMap(var24, this.tracker.world);
                Iterator var29 = par1List.iterator();

                while (var29.hasNext())
                {
                    EntityHuman var30 = (EntityHuman)var29.next();
                    EntityPlayer var31 = (EntityPlayer)var30;
                    var26.a(var31, var24);

                    if (var31.netServerHandler.lowPriorityCount() <= 5)
                    {
                        Packet var32 = Item.MAP.c(var24, this.tracker.world, var31);

                        if (var32 != null)
                        {
                            var31.netServerHandler.sendPacket(var32);
                        }
                    }
                }
            }

            DataWatcher var28 = this.tracker.getDataWatcher();

            if (var28.a())
            {
                this.broadcastIncludingSelf(new Packet40EntityMetadata(this.tracker.id, var28, false));
            }
        }
        else if (this.m++ % this.c == 0 || this.tracker.am)
        {
            int var2;
            int var3;

            if (this.tracker.vehicle == null)
            {
                ++this.u;
                var2 = this.tracker.ar.a(this.tracker.locX);
                var3 = MathHelper.floor(this.tracker.locY * 32.0D);
                int var4 = this.tracker.ar.a(this.tracker.locZ);
                int var5 = MathHelper.d(this.tracker.yaw * 256.0F / 360.0F);
                int var6 = MathHelper.d(this.tracker.pitch * 256.0F / 360.0F);
                int var7 = var2 - this.xLoc;
                int var8 = var3 - this.yLoc;
                int var9 = var4 - this.zLoc;
                Object var10 = null;
                boolean var11 = Math.abs(var7) >= 4 || Math.abs(var8) >= 4 || Math.abs(var9) >= 4 || this.m % 60 == 0;
                boolean var12 = Math.abs(var5 - this.yRot) >= 4 || Math.abs(var6 - this.xRot) >= 4;

                if (var7 >= -128 && var7 < 128 && var8 >= -128 && var8 < 128 && var9 >= -128 && var9 < 128 && this.u <= 400 && !this.w)
                {
                    if (var11 && var12)
                    {
                        var10 = new Packet33RelEntityMoveLook(this.tracker.id, (byte)var7, (byte)var8, (byte)var9, (byte)var5, (byte)var6);
                    }
                    else if (var11)
                    {
                        var10 = new Packet31RelEntityMove(this.tracker.id, (byte)var7, (byte)var8, (byte)var9);
                    }
                    else if (var12)
                    {
                        var10 = new Packet32EntityLook(this.tracker.id, (byte)var5, (byte)var6);
                    }
                }
                else
                {
                    this.u = 0;
                    var10 = new Packet34EntityTeleport(this.tracker.id, var2, var3, var4, (byte)var5, (byte)var6);
                }

                if (this.isMoving)
                {
                    double var13 = this.tracker.motX - this.j;
                    double var15 = this.tracker.motY - this.k;
                    double var17 = this.tracker.motZ - this.l;
                    double var19 = 0.02D;
                    double var21 = var13 * var13 + var15 * var15 + var17 * var17;

                    if (var21 > var19 * var19 || var21 > 0.0D && this.tracker.motX == 0.0D && this.tracker.motY == 0.0D && this.tracker.motZ == 0.0D)
                    {
                        this.j = this.tracker.motX;
                        this.k = this.tracker.motY;
                        this.l = this.tracker.motZ;
                        this.broadcast(new Packet28EntityVelocity(this.tracker.id, this.j, this.k, this.l));
                    }
                }

                if (var10 != null)
                {
                    this.broadcast((Packet) var10);
                }

                DataWatcher var33 = this.tracker.getDataWatcher();

                if (var33.a())
                {
                    this.broadcastIncludingSelf(new Packet40EntityMetadata(this.tracker.id, var33, false));
                }

                if (var11)
                {
                    this.xLoc = var2;
                    this.yLoc = var3;
                    this.zLoc = var4;
                }

                if (var12)
                {
                    this.yRot = var5;
                    this.xRot = var6;
                }

                this.w = false;
            }
            else
            {
                var2 = MathHelper.d(this.tracker.yaw * 256.0F / 360.0F);
                var3 = MathHelper.d(this.tracker.pitch * 256.0F / 360.0F);
                boolean var25 = Math.abs(var2 - this.yRot) >= 4 || Math.abs(var3 - this.xRot) >= 4;

                if (var25)
                {
                    this.broadcast(new Packet32EntityLook(this.tracker.id, (byte) var2, (byte) var3));
                    this.yRot = var2;
                    this.xRot = var3;
                }

                this.xLoc = this.tracker.ar.a(this.tracker.locX);
                this.yLoc = MathHelper.floor(this.tracker.locY * 32.0D);
                this.zLoc = this.tracker.ar.a(this.tracker.locZ);
                DataWatcher var27 = this.tracker.getDataWatcher();

                if (var27.a())
                {
                    this.broadcastIncludingSelf(new Packet40EntityMetadata(this.tracker.id, var27, false));
                }

                this.w = true;
            }

            var2 = MathHelper.d(this.tracker.ap() * 256.0F / 360.0F);

            if (Math.abs(var2 - this.i) >= 4)
            {
                this.broadcast(new Packet35EntityHeadRotation(this.tracker.id, (byte) var2));
                this.i = var2;
            }

            this.tracker.am = false;
        }

        if (this.tracker.velocityChanged)
        {
            this.broadcastIncludingSelf(new Packet28EntityVelocity(this.tracker));
            this.tracker.velocityChanged = false;
        }
    }

    public void broadcast(Packet par1Packet)
    {
        Iterator var2 = this.trackedPlayers.iterator();

        while (var2.hasNext())
        {
            EntityPlayer var3 = (EntityPlayer)var2.next();
            var3.netServerHandler.sendPacket(par1Packet);
        }
    }

    public void broadcastIncludingSelf(Packet par1Packet)
    {
        this.broadcast(par1Packet);

        if (this.tracker instanceof EntityPlayer)
        {
            ((EntityPlayer)this.tracker).netServerHandler.sendPacket(par1Packet);
        }
    }

    public void a()
    {
        Iterator var1 = this.trackedPlayers.iterator();

        while (var1.hasNext())
        {
            EntityPlayer var2 = (EntityPlayer)var1.next();
            var2.removeQueue.add(Integer.valueOf(this.tracker.id));
        }
    }

    public void a(EntityPlayer par1EntityPlayerMP)
    {
        if (this.trackedPlayers.contains(par1EntityPlayerMP))
        {
            par1EntityPlayerMP.removeQueue.add(Integer.valueOf(this.tracker.id));
            this.trackedPlayers.remove(par1EntityPlayerMP);
        }
    }

    public void updatePlayer(EntityPlayer par1EntityPlayerMP)
    {
        if (par1EntityPlayerMP != this.tracker)
        {
            double var2 = par1EntityPlayerMP.locX - (double)(this.xLoc / 32);
            double var4 = par1EntityPlayerMP.locZ - (double)(this.zLoc / 32);

            if (var2 >= (double)(-this.b) && var2 <= (double)this.b && var4 >= (double)(-this.b) && var4 <= (double)this.b)
            {
                if (!this.trackedPlayers.contains(par1EntityPlayerMP) && this.d(par1EntityPlayerMP))
                {
                    this.trackedPlayers.add(par1EntityPlayerMP);
                    Packet var6 = this.b();
                    par1EntityPlayerMP.netServerHandler.sendPacket(var6);

                    if (this.tracker instanceof EntityItemFrame)
                    {
                        par1EntityPlayerMP.netServerHandler.sendPacket(new Packet40EntityMetadata(this.tracker.id, this.tracker.getDataWatcher(), true));
                    }

                    this.j = this.tracker.motX;
                    this.k = this.tracker.motY;
                    this.l = this.tracker.motZ;

                    if (this.isMoving && !(var6 instanceof Packet24MobSpawn))
                    {
                        par1EntityPlayerMP.netServerHandler.sendPacket(new Packet28EntityVelocity(this.tracker.id, this.tracker.motX, this.tracker.motY, this.tracker.motZ));
                    }

                    if (this.tracker.vehicle != null)
                    {
                        par1EntityPlayerMP.netServerHandler.sendPacket(new Packet39AttachEntity(this.tracker, this.tracker.vehicle));
                    }

                    if (this.tracker instanceof EntityLiving)
                    {
                        for (int var7 = 0; var7 < 5; ++var7)
                        {
                            ItemStack var8 = ((EntityLiving)this.tracker).getEquipment(var7);

                            if (var8 != null)
                            {
                                par1EntityPlayerMP.netServerHandler.sendPacket(new Packet5EntityEquipment(this.tracker.id, var7, var8));
                            }
                        }
                    }

                    if (this.tracker instanceof EntityHuman)
                    {
                        EntityHuman var11 = (EntityHuman)this.tracker;

                        if (var11.isSleeping())
                        {
                            par1EntityPlayerMP.netServerHandler.sendPacket(new Packet17EntityLocationAction(this.tracker, 0, MathHelper.floor(this.tracker.locX), MathHelper.floor(this.tracker.locY), MathHelper.floor(this.tracker.locZ)));
                        }
                    }

                    if (this.tracker instanceof EntityLiving)
                    {
                        EntityLiving var10 = (EntityLiving)this.tracker;
                        Iterator var12 = var10.getEffects().iterator();

                        while (var12.hasNext())
                        {
                            MobEffect var9 = (MobEffect)var12.next();
                            par1EntityPlayerMP.netServerHandler.sendPacket(new Packet41MobEffect(this.tracker.id, var9));
                        }
                    }
                }
            }
            else if (this.trackedPlayers.contains(par1EntityPlayerMP))
            {
                this.trackedPlayers.remove(par1EntityPlayerMP);
                par1EntityPlayerMP.removeQueue.add(Integer.valueOf(this.tracker.id));
            }
        }
    }

    private boolean d(EntityPlayer par1EntityPlayerMP)
    {
        return par1EntityPlayerMP.p().getPlayerManager().a(par1EntityPlayerMP, this.tracker.ai, this.tracker.ak);
    }

    public void scanPlayers(List par1List)
    {
        for (int var2 = 0; var2 < par1List.size(); ++var2)
        {
            this.updatePlayer((EntityPlayer) par1List.get(var2));
        }
    }

    private Packet b()
    {
        if (this.tracker.dead)
        {
            System.out.println("Fetching addPacket for removed entity");
        }

        if (this.tracker instanceof EntityItem)
        {
            EntityItem var9 = (EntityItem)this.tracker;
            Packet21PickupSpawn var10 = new Packet21PickupSpawn(var9);
            var9.locX = (double)var10.b / 32.0D;
            var9.locY = (double)var10.c / 32.0D;
            var9.locZ = (double)var10.d / 32.0D;
            return var10;
        }
        else if (this.tracker instanceof EntityPlayer)
        {
            return new Packet20NamedEntitySpawn((EntityHuman)this.tracker);
        }
        else
        {
            if (this.tracker instanceof EntityMinecart)
            {
                EntityMinecart var1 = (EntityMinecart)this.tracker;

                if (var1.type == 0)
                {
                    return new Packet23VehicleSpawn(this.tracker, 10);
                }

                if (var1.type == 1)
                {
                    return new Packet23VehicleSpawn(this.tracker, 11);
                }

                if (var1.type == 2)
                {
                    return new Packet23VehicleSpawn(this.tracker, 12);
                }
            }

            if (this.tracker instanceof EntityBoat)
            {
                return new Packet23VehicleSpawn(this.tracker, 1);
            }
            else if (!(this.tracker instanceof IAnimal) && !(this.tracker instanceof EntityEnderDragon))
            {
                if (this.tracker instanceof EntityFishingHook)
                {
                    EntityHuman var8 = ((EntityFishingHook)this.tracker).owner;
                    return new Packet23VehicleSpawn(this.tracker, 90, var8 != null ? var8.id : this.tracker.id);
                }
                else if (this.tracker instanceof EntityArrow)
                {
                    Entity var7 = ((EntityArrow)this.tracker).shooter;
                    return new Packet23VehicleSpawn(this.tracker, 60, var7 != null ? var7.id : this.tracker.id);
                }
                else if (this.tracker instanceof EntitySnowball)
                {
                    return new Packet23VehicleSpawn(this.tracker, 61);
                }
                else if (this.tracker instanceof EntityPotion)
                {
                    return new Packet23VehicleSpawn(this.tracker, 73, ((EntityPotion)this.tracker).getPotionValue());
                }
                else if (this.tracker instanceof EntityThrownExpBottle)
                {
                    return new Packet23VehicleSpawn(this.tracker, 75);
                }
                else if (this.tracker instanceof EntityEnderPearl)
                {
                    return new Packet23VehicleSpawn(this.tracker, 65);
                }
                else if (this.tracker instanceof EntityEnderSignal)
                {
                    return new Packet23VehicleSpawn(this.tracker, 72);
                }
                else
                {
                    Packet23VehicleSpawn var2;

                    if (this.tracker instanceof EntityFireball)
                    {
                        EntityFireball var6 = (EntityFireball)this.tracker;
                        var2 = null;
                        byte var3 = 63;

                        if (this.tracker instanceof EntitySmallFireball)
                        {
                            var3 = 64;
                        }
                        else if (this.tracker instanceof EntityWitherSkull)
                        {
                            var3 = 66;
                        }

                        if (var6.shooter != null)
                        {
                            var2 = new Packet23VehicleSpawn(this.tracker, var3, ((EntityFireball)this.tracker).shooter.id);
                        }
                        else
                        {
                            var2 = new Packet23VehicleSpawn(this.tracker, var3, 0);
                        }

                        var2.e = (int)(var6.dirX * 8000.0D);
                        var2.f = (int)(var6.dirY * 8000.0D);
                        var2.g = (int)(var6.dirZ * 8000.0D);
                        return var2;
                    }
                    else if (this.tracker instanceof EntityEgg)
                    {
                        return new Packet23VehicleSpawn(this.tracker, 62);
                    }
                    else if (this.tracker instanceof EntityTNTPrimed)
                    {
                        return new Packet23VehicleSpawn(this.tracker, 50);
                    }
                    else if (this.tracker instanceof EntityEnderCrystal)
                    {
                        return new Packet23VehicleSpawn(this.tracker, 51);
                    }
                    else if (this.tracker instanceof EntityFallingBlock)
                    {
                        EntityFallingBlock var5 = (EntityFallingBlock)this.tracker;
                        return new Packet23VehicleSpawn(this.tracker, 70, var5.id | var5.data << 16);
                    }
                    else if (this.tracker instanceof EntityPainting)
                    {
                        return new Packet25EntityPainting((EntityPainting)this.tracker);
                    }
                    else if (this.tracker instanceof EntityItemFrame)
                    {
                        EntityItemFrame var4 = (EntityItemFrame)this.tracker;
                        var2 = new Packet23VehicleSpawn(this.tracker, 71, var4.direction);
                        var2.b = MathHelper.d((float) (var4.x * 32));
                        var2.c = MathHelper.d((float) (var4.y * 32));
                        var2.d = MathHelper.d((float) (var4.z * 32));
                        return var2;
                    }
                    else if (this.tracker instanceof EntityExperienceOrb)
                    {
                        return new Packet26AddExpOrb((EntityExperienceOrb)this.tracker);
                    }
                    else
                    {
                        throw new IllegalArgumentException("Don\'t know how to add " + this.tracker.getClass() + "!");
                    }
                }
            }
            else
            {
                this.i = MathHelper.d(this.tracker.ap() * 256.0F / 360.0F);
                return new Packet24MobSpawn((EntityLiving)this.tracker);
            }
        }
    }

    /**
     * Remove a tracked player from our list and tell the tracked player to destroy us from their world.
     */
    public void clear(EntityPlayer par1EntityPlayerMP)
    {
        if (this.trackedPlayers.contains(par1EntityPlayerMP))
        {
            this.trackedPlayers.remove(par1EntityPlayerMP);
            par1EntityPlayerMP.removeQueue.add(Integer.valueOf(this.tracker.id));
        }
    }
}
