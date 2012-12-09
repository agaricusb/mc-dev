package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class VillageSiege
{
    private World world;
    private boolean b = false;
    private int c = -1;
    private int d;
    private int e;

    /** Instance of Village. */
    private Village f;
    private int g;
    private int h;
    private int i;

    public VillageSiege(World par1World)
    {
        this.world = par1World;
    }

    /**
     * Runs a single tick for the village siege
     */
    public void a()
    {
        boolean var1 = false;

        if (var1)
        {
            if (this.c == 2)
            {
                this.d = 100;
                return;
            }
        }
        else
        {
            if (this.world.u())
            {
                this.c = 0;
                return;
            }

            if (this.c == 2)
            {
                return;
            }

            if (this.c == 0)
            {
                float var2 = this.world.c(0.0F);

                if ((double)var2 < 0.5D || (double)var2 > 0.501D)
                {
                    return;
                }

                this.c = this.world.random.nextInt(10) == 0 ? 1 : 2;
                this.b = false;

                if (this.c == 2)
                {
                    return;
                }
            }
        }

        if (!this.b)
        {
            if (!this.b())
            {
                return;
            }

            this.b = true;
        }

        if (this.e > 0)
        {
            --this.e;
        }
        else
        {
            this.e = 2;

            if (this.d > 0)
            {
                this.c();
                --this.d;
            }
            else
            {
                this.c = 2;
            }
        }
    }

    private boolean b()
    {
        List var1 = this.world.players;
        Iterator var2 = var1.iterator();

        while (var2.hasNext())
        {
            EntityHuman var3 = (EntityHuman)var2.next();
            this.f = this.world.villages.getClosestVillage((int) var3.locX, (int) var3.locY, (int) var3.locZ, 1);

            if (this.f != null && this.f.getDoorCount() >= 10 && this.f.d() >= 20 && this.f.getPopulationCount() >= 20)
            {
                ChunkCoordinates var4 = this.f.getCenter();
                float var5 = (float)this.f.getSize();
                boolean var6 = false;
                int var7 = 0;

                while (true)
                {
                    if (var7 < 10)
                    {
                        this.g = var4.x + (int)((double)(MathHelper.cos(this.world.random.nextFloat() * (float) Math.PI * 2.0F) * var5) * 0.9D);
                        this.h = var4.y;
                        this.i = var4.z + (int)((double)(MathHelper.sin(this.world.random.nextFloat() * (float) Math.PI * 2.0F) * var5) * 0.9D);
                        var6 = false;
                        Iterator var8 = this.world.villages.getVillages().iterator();

                        while (var8.hasNext())
                        {
                            Village var9 = (Village)var8.next();

                            if (var9 != this.f && var9.a(this.g, this.h, this.i))
                            {
                                var6 = true;
                                break;
                            }
                        }

                        if (var6)
                        {
                            ++var7;
                            continue;
                        }
                    }

                    if (var6)
                    {
                        return false;
                    }

                    Vec3D var10 = this.a(this.g, this.h, this.i);

                    if (var10 != null)
                    {
                        this.e = 0;
                        this.d = 20;
                        return true;
                    }

                    break;
                }
            }
        }

        return false;
    }

    private boolean c()
    {
        Vec3D var1 = this.a(this.g, this.h, this.i);

        if (var1 == null)
        {
            return false;
        }
        else
        {
            EntityZombie var2;

            try
            {
                var2 = new EntityZombie(this.world);
                var2.bG();
                var2.setVillager(false);
            }
            catch (Exception var4)
            {
                var4.printStackTrace();
                return false;
            }

            var2.setPositionRotation(var1.c, var1.d, var1.e, this.world.random.nextFloat() * 360.0F, 0.0F);
            this.world.addEntity(var2);
            ChunkCoordinates var3 = this.f.getCenter();
            var2.b(var3.x, var3.y, var3.z, this.f.getSize());
            return true;
        }
    }

    private Vec3D a(int par1, int par2, int par3)
    {
        for (int var4 = 0; var4 < 10; ++var4)
        {
            int var5 = par1 + this.world.random.nextInt(16) - 8;
            int var6 = par2 + this.world.random.nextInt(6) - 3;
            int var7 = par3 + this.world.random.nextInt(16) - 8;

            if (this.f.a(var5, var6, var7) && SpawnerCreature.a(EnumCreatureType.MONSTER, this.world, var5, var6, var7))
            {
                this.world.getVec3DPool().create((double) var5, (double) var6, (double) var7);
            }
        }

        return null;
    }
}
