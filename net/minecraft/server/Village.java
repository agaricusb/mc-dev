package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class Village
{
    private World world;

    /** list of VillageDoorInfo objects */
    private final List doors = new ArrayList();

    /**
     * This is the sum of all door coordinates and used to calculate the actual village center by dividing by the number
     * of doors.
     */
    private final ChunkCoordinates c = new ChunkCoordinates(0, 0, 0);

    /** This is the actual village center. */
    private final ChunkCoordinates center = new ChunkCoordinates(0, 0, 0);
    private int size = 0;
    private int f = 0;
    private int time = 0;
    private int population = 0;

    /** Timestamp of tick count when villager last bred */
    private int noBreedTicks;

    /** List of player reputations with this village */
    private TreeMap playerStandings = new TreeMap();
    private List aggressors = new ArrayList();
    private int ironGolemCount = 0;

    public Village() {}

    public Village(World par1World)
    {
        this.world = par1World;
    }

    public void a(World par1World)
    {
        this.world = par1World;
    }

    /**
     * Called periodically by VillageCollection
     */
    public void tick(int par1)
    {
        this.time = par1;
        this.m();
        this.l();

        if (par1 % 20 == 0)
        {
            this.k();
        }

        if (par1 % 30 == 0)
        {
            this.countPopulation();
        }

        int var2 = this.population / 10;

        if (this.ironGolemCount < var2 && this.doors.size() > 20 && this.world.random.nextInt(7000) == 0)
        {
            Vec3D var3 = this.a(MathHelper.d((float) this.center.x), MathHelper.d((float) this.center.y), MathHelper.d((float) this.center.z), 2, 4, 2);

            if (var3 != null)
            {
                EntityIronGolem var4 = new EntityIronGolem(this.world);
                var4.setPosition(var3.c, var3.d, var3.e);
                this.world.addEntity(var4);
                ++this.ironGolemCount;
            }
        }
    }

    /**
     * Tries up to 10 times to get a valid spawning location before eventually failing and returning null.
     */
    private Vec3D a(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        for (int var7 = 0; var7 < 10; ++var7)
        {
            int var8 = par1 + this.world.random.nextInt(16) - 8;
            int var9 = par2 + this.world.random.nextInt(6) - 3;
            int var10 = par3 + this.world.random.nextInt(16) - 8;

            if (this.a(var8, var9, var10) && this.b(var8, var9, var10, par4, par5, par6))
            {
                return this.world.getVec3DPool().create((double) var8, (double) var9, (double) var10);
            }
        }

        return null;
    }

    private boolean b(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        if (!this.world.v(par1, par2 - 1, par3))
        {
            return false;
        }
        else
        {
            int var7 = par1 - par4 / 2;
            int var8 = par3 - par6 / 2;

            for (int var9 = var7; var9 < var7 + par4; ++var9)
            {
                for (int var10 = par2; var10 < par2 + par5; ++var10)
                {
                    for (int var11 = var8; var11 < var8 + par6; ++var11)
                    {
                        if (this.world.t(var9, var10, var11))
                        {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    private void countPopulation()
    {
        List var1 = this.world.a(EntityIronGolem.class, AxisAlignedBB.a().a((double) (this.center.x - this.size), (double) (this.center.y - 4), (double) (this.center.z - this.size), (double) (this.center.x + this.size), (double) (this.center.y + 4), (double) (this.center.z + this.size)));
        this.ironGolemCount = var1.size();
    }

    private void k()
    {
        List var1 = this.world.a(EntityVillager.class, AxisAlignedBB.a().a((double) (this.center.x - this.size), (double) (this.center.y - 4), (double) (this.center.z - this.size), (double) (this.center.x + this.size), (double) (this.center.y + 4), (double) (this.center.z + this.size)));
        this.population = var1.size();

        if (this.population == 0)
        {
            this.playerStandings.clear();
        }
    }

    public ChunkCoordinates getCenter()
    {
        return this.center;
    }

    public int getSize()
    {
        return this.size;
    }

    /**
     * Actually get num village door info entries, but that boils down to number of doors. Called by
     * EntityAIVillagerMate and VillageSiege
     */
    public int getDoorCount()
    {
        return this.doors.size();
    }

    public int d()
    {
        return this.time - this.f;
    }

    public int getPopulationCount()
    {
        return this.population;
    }

    /**
     * Returns true, if the given coordinates are within the bounding box of the village.
     */
    public boolean a(int par1, int par2, int par3)
    {
        return this.center.e(par1, par2, par3) < (float)(this.size * this.size);
    }

    /**
     * called only by class EntityAIMoveThroughVillage
     */
    public List getDoors()
    {
        return this.doors;
    }

    public VillageDoor b(int par1, int par2, int par3)
    {
        VillageDoor var4 = null;
        int var5 = Integer.MAX_VALUE;
        Iterator var6 = this.doors.iterator();

        while (var6.hasNext())
        {
            VillageDoor var7 = (VillageDoor)var6.next();
            int var8 = var7.b(par1, par2, par3);

            if (var8 < var5)
            {
                var4 = var7;
                var5 = var8;
            }
        }

        return var4;
    }

    /**
     * Find a door suitable for shelter. If there are more doors in a distance of 16 blocks, then the least restricted
     * one (i.e. the one protecting the lowest number of villagers) of them is chosen, else the nearest one regardless
     * of restriction.
     */
    public VillageDoor c(int par1, int par2, int par3)
    {
        VillageDoor var4 = null;
        int var5 = Integer.MAX_VALUE;
        Iterator var6 = this.doors.iterator();

        while (var6.hasNext())
        {
            VillageDoor var7 = (VillageDoor)var6.next();
            int var8 = var7.b(par1, par2, par3);

            if (var8 > 256)
            {
                var8 *= 1000;
            }
            else
            {
                var8 = var7.f();
            }

            if (var8 < var5)
            {
                var4 = var7;
                var5 = var8;
            }
        }

        return var4;
    }

    public VillageDoor e(int par1, int par2, int par3)
    {
        if (this.center.e(par1, par2, par3) > (float)(this.size * this.size))
        {
            return null;
        }
        else
        {
            Iterator var4 = this.doors.iterator();
            VillageDoor var5;

            do
            {
                if (!var4.hasNext())
                {
                    return null;
                }

                var5 = (VillageDoor)var4.next();
            }
            while (var5.locX != par1 || var5.locZ != par3 || Math.abs(var5.locY - par2) > 1);

            return var5;
        }
    }

    public void addDoor(VillageDoor par1VillageDoorInfo)
    {
        this.doors.add(par1VillageDoorInfo);
        this.c.x += par1VillageDoorInfo.locX;
        this.c.y += par1VillageDoorInfo.locY;
        this.c.z += par1VillageDoorInfo.locZ;
        this.n();
        this.f = par1VillageDoorInfo.addedTime;
    }

    /**
     * Returns true, if there is not a single village door left. Called by VillageCollection
     */
    public boolean isAbandoned()
    {
        return this.doors.isEmpty();
    }

    public void a(EntityLiving par1EntityLiving)
    {
        Iterator var2 = this.aggressors.iterator();
        VillageAggressor var3;

        do
        {
            if (!var2.hasNext())
            {
                this.aggressors.add(new VillageAggressor(this, par1EntityLiving, this.time));
                return;
            }

            var3 = (VillageAggressor)var2.next();
        }
        while (var3.a != par1EntityLiving);

        var3.b = this.time;
    }

    public EntityLiving b(EntityLiving par1EntityLiving)
    {
        double var2 = Double.MAX_VALUE;
        VillageAggressor var4 = null;

        for (int var5 = 0; var5 < this.aggressors.size(); ++var5)
        {
            VillageAggressor var6 = (VillageAggressor)this.aggressors.get(var5);
            double var7 = var6.a.e(par1EntityLiving);

            if (var7 <= var2)
            {
                var4 = var6;
                var2 = var7;
            }
        }

        return var4 != null ? var4.a : null;
    }

    public EntityHuman c(EntityLiving par1EntityLiving)
    {
        double var2 = Double.MAX_VALUE;
        EntityHuman var4 = null;
        Iterator var5 = this.playerStandings.keySet().iterator();

        while (var5.hasNext())
        {
            String var6 = (String)var5.next();

            if (this.d(var6))
            {
                EntityHuman var7 = this.world.a(var6);

                if (var7 != null)
                {
                    double var8 = var7.e(par1EntityLiving);

                    if (var8 <= var2)
                    {
                        var4 = var7;
                        var2 = var8;
                    }
                }
            }
        }

        return var4;
    }

    private void l()
    {
        Iterator var1 = this.aggressors.iterator();

        while (var1.hasNext())
        {
            VillageAggressor var2 = (VillageAggressor)var1.next();

            if (!var2.a.isAlive() || Math.abs(this.time - var2.b) > 300)
            {
                var1.remove();
            }
        }
    }

    private void m()
    {
        boolean var1 = false;
        boolean var2 = this.world.random.nextInt(50) == 0;
        Iterator var3 = this.doors.iterator();

        while (var3.hasNext())
        {
            VillageDoor var4 = (VillageDoor)var3.next();

            if (var2)
            {
                var4.d();
            }

            if (!this.isDoor(var4.locX, var4.locY, var4.locZ) || Math.abs(this.time - var4.addedTime) > 1200)
            {
                this.c.x -= var4.locX;
                this.c.y -= var4.locY;
                this.c.z -= var4.locZ;
                var1 = true;
                var4.removed = true;
                var3.remove();
            }
        }

        if (var1)
        {
            this.n();
        }
    }

    private boolean isDoor(int par1, int par2, int par3)
    {
        int var4 = this.world.getTypeId(par1, par2, par3);
        return var4 <= 0 ? false : var4 == Block.WOODEN_DOOR.id;
    }

    private void n()
    {
        int var1 = this.doors.size();

        if (var1 == 0)
        {
            this.center.b(0, 0, 0);
            this.size = 0;
        }
        else
        {
            this.center.b(this.c.x / var1, this.c.y / var1, this.c.z / var1);
            int var2 = 0;
            VillageDoor var4;

            for (Iterator var3 = this.doors.iterator(); var3.hasNext(); var2 = Math.max(var4.b(this.center.x, this.center.y, this.center.z), var2))
            {
                var4 = (VillageDoor)var3.next();
            }

            this.size = Math.max(32, (int)Math.sqrt((double)var2) + 1);
        }
    }

    /**
     * Return the village reputation for a player
     */
    public int a(String par1Str)
    {
        Integer var2 = (Integer)this.playerStandings.get(par1Str);
        return var2 != null ? var2.intValue() : 0;
    }

    /**
     * Set the village reputation for a player.
     */
    public int a(String par1Str, int par2)
    {
        int var3 = this.a(par1Str);
        int var4 = MathHelper.a(var3 + par2, -30, 10);
        this.playerStandings.put(par1Str, Integer.valueOf(var4));
        return var4;
    }

    /**
     * Return whether this player has a too low reputation with this village.
     */
    public boolean d(String par1Str)
    {
        return this.a(par1Str) <= -15;
    }

    /**
     * Read this village's data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        this.population = par1NBTTagCompound.getInt("PopSize");
        this.size = par1NBTTagCompound.getInt("Radius");
        this.ironGolemCount = par1NBTTagCompound.getInt("Golems");
        this.f = par1NBTTagCompound.getInt("Stable");
        this.time = par1NBTTagCompound.getInt("Tick");
        this.noBreedTicks = par1NBTTagCompound.getInt("MTick");
        this.center.x = par1NBTTagCompound.getInt("CX");
        this.center.y = par1NBTTagCompound.getInt("CY");
        this.center.z = par1NBTTagCompound.getInt("CZ");
        this.c.x = par1NBTTagCompound.getInt("ACX");
        this.c.y = par1NBTTagCompound.getInt("ACY");
        this.c.z = par1NBTTagCompound.getInt("ACZ");
        NBTTagList var2 = par1NBTTagCompound.getList("Doors");

        for (int var3 = 0; var3 < var2.size(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
            VillageDoor var5 = new VillageDoor(var4.getInt("X"), var4.getInt("Y"), var4.getInt("Z"), var4.getInt("IDX"), var4.getInt("IDZ"), var4.getInt("TS"));
            this.doors.add(var5);
        }

        NBTTagList var6 = par1NBTTagCompound.getList("Players");

        for (int var7 = 0; var7 < var6.size(); ++var7)
        {
            NBTTagCompound var8 = (NBTTagCompound)var6.get(var7);
            this.playerStandings.put(var8.getString("Name"), Integer.valueOf(var8.getInt("S")));
        }
    }

    /**
     * Write this village's data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setInt("PopSize", this.population);
        par1NBTTagCompound.setInt("Radius", this.size);
        par1NBTTagCompound.setInt("Golems", this.ironGolemCount);
        par1NBTTagCompound.setInt("Stable", this.f);
        par1NBTTagCompound.setInt("Tick", this.time);
        par1NBTTagCompound.setInt("MTick", this.noBreedTicks);
        par1NBTTagCompound.setInt("CX", this.center.x);
        par1NBTTagCompound.setInt("CY", this.center.y);
        par1NBTTagCompound.setInt("CZ", this.center.z);
        par1NBTTagCompound.setInt("ACX", this.c.x);
        par1NBTTagCompound.setInt("ACY", this.c.y);
        par1NBTTagCompound.setInt("ACZ", this.c.z);
        NBTTagList var2 = new NBTTagList("Doors");
        Iterator var3 = this.doors.iterator();

        while (var3.hasNext())
        {
            VillageDoor var4 = (VillageDoor)var3.next();
            NBTTagCompound var5 = new NBTTagCompound("Door");
            var5.setInt("X", var4.locX);
            var5.setInt("Y", var4.locY);
            var5.setInt("Z", var4.locZ);
            var5.setInt("IDX", var4.d);
            var5.setInt("IDZ", var4.e);
            var5.setInt("TS", var4.addedTime);
            var2.add(var5);
        }

        par1NBTTagCompound.set("Doors", var2);
        NBTTagList var7 = new NBTTagList("Players");
        Iterator var8 = this.playerStandings.keySet().iterator();

        while (var8.hasNext())
        {
            String var9 = (String)var8.next();
            NBTTagCompound var6 = new NBTTagCompound(var9);
            var6.setString("Name", var9);
            var6.setInt("S", ((Integer) this.playerStandings.get(var9)).intValue());
            var7.add(var6);
        }

        par1NBTTagCompound.set("Players", var7);
    }

    /**
     * Prevent villager breeding for a fixed interval of time
     */
    public void h()
    {
        this.noBreedTicks = this.time;
    }

    /**
     * Return whether villagers mating refractory period has passed
     */
    public boolean i()
    {
        return this.noBreedTicks == 0 || this.time - this.noBreedTicks >= 3600;
    }

    public void b(int par1)
    {
        Iterator var2 = this.playerStandings.keySet().iterator();

        while (var2.hasNext())
        {
            String var3 = (String)var2.next();
            this.a(var3, par1);
        }
    }
}
