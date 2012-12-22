package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TileEntityMobSpawner extends TileEntity
{
    /** The stored delay before a new spawn. */
    public int spawnDelay = -1;

    /**
     * The string ID of the mobs being spawned from this spawner. Defaults to pig, apparently.
     */
    private String mobName = "Pig";
    private List mobs = null;

    /** The extra NBT data to add to spawned entities */
    private TileEntityMobSpawnerData spawnData = null;
    public double b;
    public double c = 0.0D;
    private int minSpawnDelay = 200;
    private int maxSpawnDelay = 800;
    private int spawnCount = 4;
    private Entity j;

    /** Maximum number of entities for limiting mob spawning */
    private int maxNearbyEntities = 6;

    /** Required player range for mob spawning to occur */
    private int requiredPlayerRange = 16;

    /** Range for spawning new entities with mob spawners */
    private int spawnRange = 4;

    public TileEntityMobSpawner()
    {
        this.spawnDelay = 20;
    }

    public String getMobName()
    {
        return this.spawnData == null ? this.mobName : this.spawnData.c;
    }

    public void a(String par1Str)
    {
        this.mobName = par1Str;
    }

    /**
     * Returns true if there is a player in range (using World.getClosestPlayer)
     */
    public boolean b()
    {
        return this.world.findNearbyPlayer((double) this.x + 0.5D, (double) this.y + 0.5D, (double) this.z + 0.5D, (double) this.requiredPlayerRange) != null;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void g()
    {
        if (this.b())
        {
            double var5;

            if (this.world.isStatic)
            {
                double var1 = (double)((float)this.x + this.world.random.nextFloat());
                double var3 = (double)((float)this.y + this.world.random.nextFloat());
                var5 = (double)((float)this.z + this.world.random.nextFloat());
                this.world.addParticle("smoke", var1, var3, var5, 0.0D, 0.0D, 0.0D);
                this.world.addParticle("flame", var1, var3, var5, 0.0D, 0.0D, 0.0D);

                if (this.spawnDelay > 0)
                {
                    --this.spawnDelay;
                }

                this.c = this.b;
                this.b = (this.b + (double)(1000.0F / ((float)this.spawnDelay + 200.0F))) % 360.0D;
            }
            else
            {
                if (this.spawnDelay == -1)
                {
                    this.e();
                }

                if (this.spawnDelay > 0)
                {
                    --this.spawnDelay;
                    return;
                }

                boolean var12 = false;

                for (int var2 = 0; var2 < this.spawnCount; ++var2)
                {
                    Entity var13 = EntityTypes.createEntityByName(this.getMobName(), this.world);

                    if (var13 == null)
                    {
                        return;
                    }

                    int var4 = this.world.a(var13.getClass(), AxisAlignedBB.a().a((double) this.x, (double) this.y, (double) this.z, (double) (this.x + 1), (double) (this.y + 1), (double) (this.z + 1)).grow((double) (this.spawnRange * 2), 4.0D, (double) (this.spawnRange * 2))).size();

                    if (var4 >= this.maxNearbyEntities)
                    {
                        this.e();
                        return;
                    }

                    if (var13 != null)
                    {
                        var5 = (double)this.x + (this.world.random.nextDouble() - this.world.random.nextDouble()) * (double)this.spawnRange;
                        double var7 = (double)(this.y + this.world.random.nextInt(3) - 1);
                        double var9 = (double)this.z + (this.world.random.nextDouble() - this.world.random.nextDouble()) * (double)this.spawnRange;
                        EntityLiving var11 = var13 instanceof EntityLiving ? (EntityLiving)var13 : null;
                        var13.setPositionRotation(var5, var7, var9, this.world.random.nextFloat() * 360.0F, 0.0F);

                        if (var11 == null || var11.canSpawn())
                        {
                            this.a(var13);
                            this.world.addEntity(var13);
                            this.world.triggerEffect(2004, this.x, this.y, this.z, 0);

                            if (var11 != null)
                            {
                                var11.aR();
                            }

                            var12 = true;
                        }
                    }
                }

                if (var12)
                {
                    this.e();
                }
            }

            super.g();
        }
    }

    public void a(Entity par1Entity)
    {
        if (this.spawnData != null)
        {
            NBTTagCompound var2 = new NBTTagCompound();
            par1Entity.c(var2);
            Iterator var3 = this.spawnData.b.c().iterator();

            while (var3.hasNext())
            {
                NBTBase var4 = (NBTBase)var3.next();
                var2.set(var4.getName(), var4.clone());
            }

            par1Entity.e(var2);
        }
        else if (par1Entity instanceof EntityLiving && par1Entity.world != null)
        {
            ((EntityLiving)par1Entity).bG();
        }
    }

    /**
     * Sets the delay before a new spawn (base delay of 200 + random number up to 600).
     */
    private void e()
    {
        if (this.maxSpawnDelay <= this.minSpawnDelay)
        {
            this.spawnDelay = this.minSpawnDelay;
        }
        else
        {
            this.spawnDelay = this.minSpawnDelay + this.world.random.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
        }

        if (this.mobs != null && this.mobs.size() > 0)
        {
            this.spawnData = (TileEntityMobSpawnerData) WeightedRandom.a(this.world.random, this.mobs);
            this.world.notify(this.x, this.y, this.z);
        }

        this.world.playNote(this.x, this.y, this.z, this.q().id, 1, 0);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.mobName = par1NBTTagCompound.getString("EntityId");
        this.spawnDelay = par1NBTTagCompound.getShort("Delay");

        if (par1NBTTagCompound.hasKey("SpawnPotentials"))
        {
            this.mobs = new ArrayList();
            NBTTagList var2 = par1NBTTagCompound.getList("SpawnPotentials");

            for (int var3 = 0; var3 < var2.size(); ++var3)
            {
                this.mobs.add(new TileEntityMobSpawnerData(this, (NBTTagCompound)var2.get(var3)));
            }
        }
        else
        {
            this.mobs = null;
        }

        if (par1NBTTagCompound.hasKey("SpawnData"))
        {
            this.spawnData = new TileEntityMobSpawnerData(this, par1NBTTagCompound.getCompound("SpawnData"), this.mobName);
        }
        else
        {
            this.spawnData = null;
        }

        if (par1NBTTagCompound.hasKey("MinSpawnDelay"))
        {
            this.minSpawnDelay = par1NBTTagCompound.getShort("MinSpawnDelay");
            this.maxSpawnDelay = par1NBTTagCompound.getShort("MaxSpawnDelay");
            this.spawnCount = par1NBTTagCompound.getShort("SpawnCount");
        }

        if (par1NBTTagCompound.hasKey("MaxNearbyEntities"))
        {
            this.maxNearbyEntities = par1NBTTagCompound.getShort("MaxNearbyEntities");
            this.requiredPlayerRange = par1NBTTagCompound.getShort("RequiredPlayerRange");
        }

        if (par1NBTTagCompound.hasKey("SpawnRange"))
        {
            this.spawnRange = par1NBTTagCompound.getShort("SpawnRange");
        }

        if (this.world != null && this.world.isStatic)
        {
            this.j = null;
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setString("EntityId", this.getMobName());
        par1NBTTagCompound.setShort("Delay", (short) this.spawnDelay);
        par1NBTTagCompound.setShort("MinSpawnDelay", (short) this.minSpawnDelay);
        par1NBTTagCompound.setShort("MaxSpawnDelay", (short) this.maxSpawnDelay);
        par1NBTTagCompound.setShort("SpawnCount", (short) this.spawnCount);
        par1NBTTagCompound.setShort("MaxNearbyEntities", (short) this.maxNearbyEntities);
        par1NBTTagCompound.setShort("RequiredPlayerRange", (short) this.requiredPlayerRange);
        par1NBTTagCompound.setShort("SpawnRange", (short) this.spawnRange);

        if (this.spawnData != null)
        {
            par1NBTTagCompound.setCompound("SpawnData", (NBTTagCompound) this.spawnData.b.clone());
        }

        if (this.spawnData != null || this.mobs != null && this.mobs.size() > 0)
        {
            NBTTagList var2 = new NBTTagList();

            if (this.mobs != null && this.mobs.size() > 0)
            {
                Iterator var3 = this.mobs.iterator();

                while (var3.hasNext())
                {
                    TileEntityMobSpawnerData var4 = (TileEntityMobSpawnerData)var3.next();
                    var2.add(var4.a());
                }
            }
            else
            {
                var2.add(this.spawnData.a());
            }

            par1NBTTagCompound.set("SpawnPotentials", var2);
        }
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getUpdatePacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.b(var1);
        var1.o("SpawnPotentials");
        return new Packet132TileEntityData(this.x, this.y, this.z, 1, var1);
    }

    /**
     * Called when a client event is received with the event number and argument, see World.sendClientEvent
     */
    public void b(int par1, int par2)
    {
        if (par1 == 1 && this.world.isStatic)
        {
            this.spawnDelay = this.minSpawnDelay;
        }
    }
}
