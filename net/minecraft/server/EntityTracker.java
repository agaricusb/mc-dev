package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EntityTracker
{
    private final WorldServer world;

    /**
     * List of tracked entities, used for iteration operations on tracked entities.
     */
    private Set b = new HashSet();

    /** Used for identity lookup of tracked entities. */
    private IntHashMap trackedEntities = new IntHashMap();
    private int d;

    public EntityTracker(WorldServer par1WorldServer)
    {
        this.world = par1WorldServer;
        this.d = par1WorldServer.getMinecraftServer().getPlayerList().a();
    }

    public void track(Entity par1Entity)
    {
        if (par1Entity instanceof EntityPlayer)
        {
            this.addEntity(par1Entity, 512, 2);
            EntityPlayer var2 = (EntityPlayer)par1Entity;
            Iterator var3 = this.b.iterator();

            while (var3.hasNext())
            {
                EntityTrackerEntry var4 = (EntityTrackerEntry)var3.next();

                if (var4.tracker != var2)
                {
                    var4.updatePlayer(var2);
                }
            }
        }
        else if (par1Entity instanceof EntityFishingHook)
        {
            this.addEntity(par1Entity, 64, 5, true);
        }
        else if (par1Entity instanceof EntityArrow)
        {
            this.addEntity(par1Entity, 64, 20, false);
        }
        else if (par1Entity instanceof EntitySmallFireball)
        {
            this.addEntity(par1Entity, 64, 10, false);
        }
        else if (par1Entity instanceof EntityFireball)
        {
            this.addEntity(par1Entity, 64, 10, false);
        }
        else if (par1Entity instanceof EntitySnowball)
        {
            this.addEntity(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityEnderPearl)
        {
            this.addEntity(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityEnderSignal)
        {
            this.addEntity(par1Entity, 64, 4, true);
        }
        else if (par1Entity instanceof EntityEgg)
        {
            this.addEntity(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityPotion)
        {
            this.addEntity(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityThrownExpBottle)
        {
            this.addEntity(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityFireworks)
        {
            this.addEntity(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityItem)
        {
            this.addEntity(par1Entity, 64, 20, true);
        }
        else if (par1Entity instanceof EntityMinecart)
        {
            this.addEntity(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntityBoat)
        {
            this.addEntity(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntitySquid)
        {
            this.addEntity(par1Entity, 64, 3, true);
        }
        else if (par1Entity instanceof EntityWither)
        {
            this.addEntity(par1Entity, 80, 3, false);
        }
        else if (par1Entity instanceof EntityBat)
        {
            this.addEntity(par1Entity, 80, 3, false);
        }
        else if (par1Entity instanceof IAnimal)
        {
            this.addEntity(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntityEnderDragon)
        {
            this.addEntity(par1Entity, 160, 3, true);
        }
        else if (par1Entity instanceof EntityTNTPrimed)
        {
            this.addEntity(par1Entity, 160, 10, true);
        }
        else if (par1Entity instanceof EntityFallingBlock)
        {
            this.addEntity(par1Entity, 160, 20, true);
        }
        else if (par1Entity instanceof EntityPainting)
        {
            this.addEntity(par1Entity, 160, Integer.MAX_VALUE, false);
        }
        else if (par1Entity instanceof EntityExperienceOrb)
        {
            this.addEntity(par1Entity, 160, 20, true);
        }
        else if (par1Entity instanceof EntityEnderCrystal)
        {
            this.addEntity(par1Entity, 256, Integer.MAX_VALUE, false);
        }
        else if (par1Entity instanceof EntityItemFrame)
        {
            this.addEntity(par1Entity, 160, Integer.MAX_VALUE, false);
        }
    }

    public void addEntity(Entity par1Entity, int par2, int par3)
    {
        this.addEntity(par1Entity, par2, par3, false);
    }

    public void addEntity(Entity par1Entity, int par2, int par3, boolean par4)
    {
        if (par2 > this.d)
        {
            par2 = this.d;
        }

        if (this.trackedEntities.b(par1Entity.id))
        {
            throw new IllegalStateException("Entity is already tracked!");
        }
        else
        {
            EntityTrackerEntry var5 = new EntityTrackerEntry(par1Entity, par2, par3, par4);
            this.b.add(var5);
            this.trackedEntities.a(par1Entity.id, var5);
            var5.scanPlayers(this.world.players);
        }
    }

    public void untrackEntity(Entity par1Entity)
    {
        if (par1Entity instanceof EntityPlayer)
        {
            EntityPlayer var2 = (EntityPlayer)par1Entity;
            Iterator var3 = this.b.iterator();

            while (var3.hasNext())
            {
                EntityTrackerEntry var4 = (EntityTrackerEntry)var3.next();
                var4.a(var2);
            }
        }

        EntityTrackerEntry var5 = (EntityTrackerEntry)this.trackedEntities.d(par1Entity.id);

        if (var5 != null)
        {
            this.b.remove(var5);
            var5.a();
        }
    }

    public void updatePlayers()
    {
        ArrayList var1 = new ArrayList();
        Iterator var2 = this.b.iterator();

        while (var2.hasNext())
        {
            EntityTrackerEntry var3 = (EntityTrackerEntry)var2.next();
            var3.track(this.world.players);

            if (var3.n && var3.tracker instanceof EntityPlayer)
            {
                var1.add((EntityPlayer)var3.tracker);
            }
        }

        for (int var6 = 0; var6 < var1.size(); ++var6)
        {
            EntityPlayer var7 = (EntityPlayer)var1.get(var6);
            Iterator var4 = this.b.iterator();

            while (var4.hasNext())
            {
                EntityTrackerEntry var5 = (EntityTrackerEntry)var4.next();

                if (var5.tracker != var7)
                {
                    var5.updatePlayer(var7);
                }
            }
        }
    }

    public void a(Entity par1Entity, Packet par2Packet)
    {
        EntityTrackerEntry var3 = (EntityTrackerEntry)this.trackedEntities.get(par1Entity.id);

        if (var3 != null)
        {
            var3.broadcast(par2Packet);
        }
    }

    public void sendPacketToEntity(Entity par1Entity, Packet par2Packet)
    {
        EntityTrackerEntry var3 = (EntityTrackerEntry)this.trackedEntities.get(par1Entity.id);

        if (var3 != null)
        {
            var3.broadcastIncludingSelf(par2Packet);
        }
    }

    public void untrackPlayer(EntityPlayer par1EntityPlayerMP)
    {
        Iterator var2 = this.b.iterator();

        while (var2.hasNext())
        {
            EntityTrackerEntry var3 = (EntityTrackerEntry)var2.next();
            var3.clear(par1EntityPlayerMP);
        }
    }

    public void a(EntityPlayer par1EntityPlayerMP, Chunk par2Chunk)
    {
        Iterator var3 = this.b.iterator();

        while (var3.hasNext())
        {
            EntityTrackerEntry var4 = (EntityTrackerEntry)var3.next();

            if (var4.tracker != par1EntityPlayerMP && var4.tracker.ai == par2Chunk.x && var4.tracker.ak == par2Chunk.z)
            {
                var4.updatePlayer(par1EntityPlayerMP);
            }
        }
    }
}
