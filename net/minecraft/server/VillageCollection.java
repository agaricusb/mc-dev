package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VillageCollection extends WorldMapBase
{
    private World world;

    /**
     * This is a black hole. You can add data to this list through a public interface, but you can't query that
     * information in any way and it's not used internally either.
     */
    private final List b = new ArrayList();
    private final List c = new ArrayList();
    private final List villages = new ArrayList();
    private int time = 0;

    public VillageCollection(String par1Str)
    {
        super(par1Str);
    }

    public VillageCollection(World par1World)
    {
        super("villages");
        this.world = par1World;
        this.c();
    }

    public void a(World par1World)
    {
        this.world = par1World;
        Iterator var2 = this.villages.iterator();

        while (var2.hasNext())
        {
            Village var3 = (Village)var2.next();
            var3.a(par1World);
        }
    }

    /**
     * This is a black hole. You can add data to this list through a public interface, but you can't query that
     * information in any way and it's not used internally either.
     */
    public void a(int par1, int par2, int par3)
    {
        if (this.b.size() <= 64)
        {
            if (!this.d(par1, par2, par3))
            {
                this.b.add(new ChunkCoordinates(par1, par2, par3));
            }
        }
    }

    /**
     * Runs a single tick for the village collection
     */
    public void tick()
    {
        ++this.time;
        Iterator var1 = this.villages.iterator();

        while (var1.hasNext())
        {
            Village var2 = (Village)var1.next();
            var2.tick(this.time);
        }

        this.e();
        this.f();
        this.g();

        if (this.time % 400 == 0)
        {
            this.c();
        }
    }

    private void e()
    {
        Iterator var1 = this.villages.iterator();

        while (var1.hasNext())
        {
            Village var2 = (Village)var1.next();

            if (var2.isAbandoned())
            {
                var1.remove();
                this.c();
            }
        }
    }

    /**
     * Get a list of villages.
     */
    public List getVillages()
    {
        return this.villages;
    }

    /**
     * Finds the nearest village, but only the given coordinates are withing it's bounding box plus the given the
     * distance.
     */
    public Village getClosestVillage(int par1, int par2, int par3, int par4)
    {
        Village var5 = null;
        float var6 = Float.MAX_VALUE;
        Iterator var7 = this.villages.iterator();

        while (var7.hasNext())
        {
            Village var8 = (Village)var7.next();
            float var9 = var8.getCenter().e(par1, par2, par3);

            if (var9 < var6)
            {
                int var10 = par4 + var8.getSize();

                if (var9 <= (float)(var10 * var10))
                {
                    var5 = var8;
                    var6 = var9;
                }
            }
        }

        return var5;
    }

    private void f()
    {
        if (!this.b.isEmpty())
        {
            this.a((ChunkCoordinates) this.b.remove(0));
        }
    }

    private void g()
    {
        int var1 = 0;

        while (var1 < this.c.size())
        {
            VillageDoor var2 = (VillageDoor)this.c.get(var1);
            boolean var3 = false;
            Iterator var4 = this.villages.iterator();

            while (true)
            {
                if (var4.hasNext())
                {
                    Village var5 = (Village)var4.next();
                    int var6 = (int)var5.getCenter().e(var2.locX, var2.locY, var2.locZ);
                    int var7 = 32 + var5.getSize();

                    if (var6 > var7 * var7)
                    {
                        continue;
                    }

                    var5.addDoor(var2);
                    var3 = true;
                }

                if (!var3)
                {
                    Village var8 = new Village(this.world);
                    var8.addDoor(var2);
                    this.villages.add(var8);
                    this.c();
                }

                ++var1;
                break;
            }
        }

        this.c.clear();
    }

    private void a(ChunkCoordinates par1ChunkCoordinates)
    {
        byte var2 = 16;
        byte var3 = 4;
        byte var4 = 16;

        for (int var5 = par1ChunkCoordinates.x - var2; var5 < par1ChunkCoordinates.x + var2; ++var5)
        {
            for (int var6 = par1ChunkCoordinates.y - var3; var6 < par1ChunkCoordinates.y + var3; ++var6)
            {
                for (int var7 = par1ChunkCoordinates.z - var4; var7 < par1ChunkCoordinates.z + var4; ++var7)
                {
                    if (this.e(var5, var6, var7))
                    {
                        VillageDoor var8 = this.b(var5, var6, var7);

                        if (var8 == null)
                        {
                            this.c(var5, var6, var7);
                        }
                        else
                        {
                            var8.addedTime = this.time;
                        }
                    }
                }
            }
        }
    }

    private VillageDoor b(int par1, int par2, int par3)
    {
        Iterator var4 = this.c.iterator();
        VillageDoor var5;

        do
        {
            if (!var4.hasNext())
            {
                var4 = this.villages.iterator();
                VillageDoor var6;

                do
                {
                    if (!var4.hasNext())
                    {
                        return null;
                    }

                    Village var7 = (Village)var4.next();
                    var6 = var7.e(par1, par2, par3);
                }
                while (var6 == null);

                return var6;
            }

            var5 = (VillageDoor)var4.next();
        }
        while (var5.locX != par1 || var5.locZ != par3 || Math.abs(var5.locY - par2) > 1);

        return var5;
    }

    private void c(int par1, int par2, int par3)
    {
        int var4 = ((BlockDoor) Block.WOODEN_DOOR).d((IBlockAccess)this.world, par1, par2, par3);
        int var5;
        int var6;

        if (var4 != 0 && var4 != 2)
        {
            var5 = 0;

            for (var6 = -5; var6 < 0; ++var6)
            {
                if (this.world.k(par1, par2, par3 + var6))
                {
                    --var5;
                }
            }

            for (var6 = 1; var6 <= 5; ++var6)
            {
                if (this.world.k(par1, par2, par3 + var6))
                {
                    ++var5;
                }
            }

            if (var5 != 0)
            {
                this.c.add(new VillageDoor(par1, par2, par3, 0, var5 > 0 ? -2 : 2, this.time));
            }
        }
        else
        {
            var5 = 0;

            for (var6 = -5; var6 < 0; ++var6)
            {
                if (this.world.k(par1 + var6, par2, par3))
                {
                    --var5;
                }
            }

            for (var6 = 1; var6 <= 5; ++var6)
            {
                if (this.world.k(par1 + var6, par2, par3))
                {
                    ++var5;
                }
            }

            if (var5 != 0)
            {
                this.c.add(new VillageDoor(par1, par2, par3, var5 > 0 ? -2 : 2, 0, this.time));
            }
        }
    }

    private boolean d(int par1, int par2, int par3)
    {
        Iterator var4 = this.b.iterator();
        ChunkCoordinates var5;

        do
        {
            if (!var4.hasNext())
            {
                return false;
            }

            var5 = (ChunkCoordinates)var4.next();
        }
        while (var5.x != par1 || var5.y != par2 || var5.z != par3);

        return true;
    }

    private boolean e(int par1, int par2, int par3)
    {
        int var4 = this.world.getTypeId(par1, par2, par3);
        return var4 == Block.WOODEN_DOOR.id;
    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        this.time = par1NBTTagCompound.getInt("Tick");
        NBTTagList var2 = par1NBTTagCompound.getList("Villages");

        for (int var3 = 0; var3 < var2.size(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
            Village var5 = new Village();
            var5.a(var4);
            this.villages.add(var5);
        }
    }

    /**
     * write data to NBTTagCompound from this MapDataBase, similar to Entities and TileEntities
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setInt("Tick", this.time);
        NBTTagList var2 = new NBTTagList("Villages");
        Iterator var3 = this.villages.iterator();

        while (var3.hasNext())
        {
            Village var4 = (Village)var3.next();
            NBTTagCompound var5 = new NBTTagCompound("Village");
            var4.b(var5);
            var2.add(var5);
        }

        par1NBTTagCompound.set("Villages", var2);
    }
}
