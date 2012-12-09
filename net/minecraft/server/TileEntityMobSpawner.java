package net.minecraft.server;

import java.util.Iterator;

public class TileEntityMobSpawner extends TileEntity
{
    /** The stored delay before a new spawn. */
    public int spawnDelay = -1;

    /**
     * The string ID of the mobs being spawned from this spawner. Defaults to pig, apparently.
     */
    private String mobName = "Pig";

    /** The extra NBT data to add to spawned entities */
    private NBTTagCompound spawnData = null;
    public double b;
    public double c = 0.0D;
    private int minSpawnDelay = 200;
    private int maxSpawnDelay = 800;
    private int spawnCount = 4;
    private int maxNearbyEntities = 6;
    private int requiredPlayerRange = 16;
    private int spawnRange = 4;

    public TileEntityMobSpawner()
    {
        this.spawnDelay = 20;
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
            if (this.world.isStatic)
            {
                double var1 = (double)((float)this.x + this.world.random.nextFloat());
                double var3 = (double)((float)this.y + this.world.random.nextFloat());
                double var5 = (double)((float)this.z + this.world.random.nextFloat());
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

                for (int var11 = 0; var11 < this.spawnCount; ++var11)
                {
                    Entity var2 = EntityTypes.createEntityByName(this.mobName, this.world);

                    if (var2 == null)
                    {
                        return;
                    }

                    int var12 = this.world.a(var2.getClass(), AxisAlignedBB.a().a((double) this.x, (double) this.y, (double) this.z, (double) (this.x + 1), (double) (this.y + 1), (double) (this.z + 1)).grow((double) (this.spawnRange * 2), 4.0D, (double) (this.spawnRange * 2))).size();

                    if (var12 >= this.maxNearbyEntities)
                    {
                        this.e();
                        return;
                    }

                    if (var2 != null)
                    {
                        double var4 = (double)this.x + (this.world.random.nextDouble() - this.world.random.nextDouble()) * (double)this.spawnRange;
                        double var6 = (double)(this.y + this.world.random.nextInt(3) - 1);
                        double var8 = (double)this.z + (this.world.random.nextDouble() - this.world.random.nextDouble()) * (double)this.spawnRange;
                        EntityLiving var10 = var2 instanceof EntityLiving ? (EntityLiving)var2 : null;
                        var2.setPositionRotation(var4, var6, var8, this.world.random.nextFloat() * 360.0F, 0.0F);

                        if (var10 == null || var10.canSpawn())
                        {
                            this.a(var2);
                            this.world.addEntity(var2);
                            this.world.triggerEffect(2004, this.x, this.y, this.z, 0);

                            if (var10 != null)
                            {
                                var10.aR();
                            }

                            this.e();
                        }
                    }
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
            Iterator var3 = this.spawnData.c().iterator();

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

        if (par1NBTTagCompound.hasKey("SpawnData"))
        {
            this.spawnData = par1NBTTagCompound.getCompound("SpawnData");
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
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setString("EntityId", this.mobName);
        par1NBTTagCompound.setShort("Delay", (short)this.spawnDelay);
        par1NBTTagCompound.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
        par1NBTTagCompound.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
        par1NBTTagCompound.setShort("SpawnCount", (short)this.spawnCount);
        par1NBTTagCompound.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
        par1NBTTagCompound.setShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
        par1NBTTagCompound.setShort("SpawnRange", (short)this.spawnRange);

        if (this.spawnData != null)
        {
            par1NBTTagCompound.setCompound("SpawnData", this.spawnData);
        }
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getUpdatePacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.b(var1);
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
