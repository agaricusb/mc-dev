package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

class MinecartTrackLogic
{
    /** Reference to the World object. */
    private World b;
    private int c;
    private int d;
    private int e;

    /**
     * A boolean value that is true if the rail is powered, and false if its not.
     */
    private final boolean f;
    private List g;

    final BlockMinecartTrack a;

    public MinecartTrackLogic(BlockMinecartTrack par1BlockRail, World par2World, int par3, int par4, int par5)
    {
        this.a = par1BlockRail;
        this.g = new ArrayList();
        this.b = par2World;
        this.c = par3;
        this.d = par4;
        this.e = par5;
        int var6 = par2World.getTypeId(par3, par4, par5);
        int var7 = par2World.getData(par3, par4, par5);

        if (BlockMinecartTrack.a((BlockMinecartTrack) Block.byId[var6]))
        {
            this.f = true;
            var7 &= -9;
        }
        else
        {
            this.f = false;
        }

        this.a(var7);
    }

    private void a(int par1)
    {
        this.g.clear();

        if (par1 == 0)
        {
            this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
            this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
        }
        else if (par1 == 1)
        {
            this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
            this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
        }
        else if (par1 == 2)
        {
            this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
            this.g.add(new ChunkPosition(this.c + 1, this.d + 1, this.e));
        }
        else if (par1 == 3)
        {
            this.g.add(new ChunkPosition(this.c - 1, this.d + 1, this.e));
            this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
        }
        else if (par1 == 4)
        {
            this.g.add(new ChunkPosition(this.c, this.d + 1, this.e - 1));
            this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
        }
        else if (par1 == 5)
        {
            this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
            this.g.add(new ChunkPosition(this.c, this.d + 1, this.e + 1));
        }
        else if (par1 == 6)
        {
            this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
            this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
        }
        else if (par1 == 7)
        {
            this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
            this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
        }
        else if (par1 == 8)
        {
            this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
            this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
        }
        else if (par1 == 9)
        {
            this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
            this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
        }
    }

    /**
     * Neighboring tracks have potentially been broken, so prune the connected track list
     */
    private void a()
    {
        for (int var1 = 0; var1 < this.g.size(); ++var1)
        {
            MinecartTrackLogic var2 = this.a((ChunkPosition) this.g.get(var1));

            if (var2 != null && var2.b(this))
            {
                this.g.set(var1, new ChunkPosition(var2.c, var2.d, var2.e));
            }
            else
            {
                this.g.remove(var1--);
            }
        }
    }

    private boolean a(int par1, int par2, int par3)
    {
        return BlockMinecartTrack.e_(this.b, par1, par2, par3) ? true : (BlockMinecartTrack.e_(this.b, par1, par2 + 1, par3) ? true : BlockMinecartTrack.e_(this.b, par1, par2 - 1, par3));
    }

    private MinecartTrackLogic a(ChunkPosition par1ChunkPosition)
    {
        return BlockMinecartTrack.e_(this.b, par1ChunkPosition.x, par1ChunkPosition.y, par1ChunkPosition.z) ? new MinecartTrackLogic(this.a, this.b, par1ChunkPosition.x, par1ChunkPosition.y, par1ChunkPosition.z) : (BlockMinecartTrack.e_(this.b, par1ChunkPosition.x, par1ChunkPosition.y + 1, par1ChunkPosition.z) ? new MinecartTrackLogic(this.a, this.b, par1ChunkPosition.x, par1ChunkPosition.y + 1, par1ChunkPosition.z) : (BlockMinecartTrack.e_(this.b, par1ChunkPosition.x, par1ChunkPosition.y - 1, par1ChunkPosition.z) ? new MinecartTrackLogic(this.a, this.b, par1ChunkPosition.x, par1ChunkPosition.y - 1, par1ChunkPosition.z) : null));
    }

    private boolean b(MinecartTrackLogic par1RailLogic)
    {
        for (int var2 = 0; var2 < this.g.size(); ++var2)
        {
            ChunkPosition var3 = (ChunkPosition)this.g.get(var2);

            if (var3.x == par1RailLogic.c && var3.z == par1RailLogic.e)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns true if the specified block is in the same railway.
     */
    private boolean b(int par1, int par2, int par3)
    {
        for (int var4 = 0; var4 < this.g.size(); ++var4)
        {
            ChunkPosition var5 = (ChunkPosition)this.g.get(var4);

            if (var5.x == par1 && var5.z == par3)
            {
                return true;
            }
        }

        return false;
    }

    private int b()
    {
        int var1 = 0;

        if (this.a(this.c, this.d, this.e - 1))
        {
            ++var1;
        }

        if (this.a(this.c, this.d, this.e + 1))
        {
            ++var1;
        }

        if (this.a(this.c - 1, this.d, this.e))
        {
            ++var1;
        }

        if (this.a(this.c + 1, this.d, this.e))
        {
            ++var1;
        }

        return var1;
    }

    /**
     * Determines whether or not the track can bend to meet the specified rail
     */
    private boolean c(MinecartTrackLogic par1RailLogic)
    {
        if (this.b(par1RailLogic))
        {
            return true;
        }
        else if (this.g.size() == 2)
        {
            return false;
        }
        else if (this.g.isEmpty())
        {
            return true;
        }
        else
        {
            ChunkPosition var2 = (ChunkPosition)this.g.get(0);
            return true;
        }
    }

    /**
     * The specified neighbor has just formed a new connection, so update accordingly
     */
    private void d(MinecartTrackLogic par1RailLogic)
    {
        this.g.add(new ChunkPosition(par1RailLogic.c, par1RailLogic.d, par1RailLogic.e));
        boolean var2 = this.b(this.c, this.d, this.e - 1);
        boolean var3 = this.b(this.c, this.d, this.e + 1);
        boolean var4 = this.b(this.c - 1, this.d, this.e);
        boolean var5 = this.b(this.c + 1, this.d, this.e);
        byte var6 = -1;

        if (var2 || var3)
        {
            var6 = 0;
        }

        if (var4 || var5)
        {
            var6 = 1;
        }

        if (!this.f)
        {
            if (var3 && var5 && !var2 && !var4)
            {
                var6 = 6;
            }

            if (var3 && var4 && !var2 && !var5)
            {
                var6 = 7;
            }

            if (var2 && var4 && !var3 && !var5)
            {
                var6 = 8;
            }

            if (var2 && var5 && !var3 && !var4)
            {
                var6 = 9;
            }
        }

        if (var6 == 0)
        {
            if (BlockMinecartTrack.e_(this.b, this.c, this.d + 1, this.e - 1))
            {
                var6 = 4;
            }

            if (BlockMinecartTrack.e_(this.b, this.c, this.d + 1, this.e + 1))
            {
                var6 = 5;
            }
        }

        if (var6 == 1)
        {
            if (BlockMinecartTrack.e_(this.b, this.c + 1, this.d + 1, this.e))
            {
                var6 = 2;
            }

            if (BlockMinecartTrack.e_(this.b, this.c - 1, this.d + 1, this.e))
            {
                var6 = 3;
            }
        }

        if (var6 < 0)
        {
            var6 = 0;
        }

        int var7 = var6;

        if (this.f)
        {
            var7 = this.b.getData(this.c, this.d, this.e) & 8 | var6;
        }

        this.b.setData(this.c, this.d, this.e, var7);
    }

    /**
     * Determines whether or not the target rail can connect to this rail
     */
    private boolean c(int par1, int par2, int par3)
    {
        MinecartTrackLogic var4 = this.a(new ChunkPosition(par1, par2, par3));

        if (var4 == null)
        {
            return false;
        }
        else
        {
            var4.a();
            return var4.c(this);
        }
    }

    /**
     * Completely recalculates the track shape based on neighboring tracks and power state
     */
    public void a(boolean par1, boolean par2)
    {
        boolean var3 = this.c(this.c, this.d, this.e - 1);
        boolean var4 = this.c(this.c, this.d, this.e + 1);
        boolean var5 = this.c(this.c - 1, this.d, this.e);
        boolean var6 = this.c(this.c + 1, this.d, this.e);
        byte var7 = -1;

        if ((var3 || var4) && !var5 && !var6)
        {
            var7 = 0;
        }

        if ((var5 || var6) && !var3 && !var4)
        {
            var7 = 1;
        }

        if (!this.f)
        {
            if (var4 && var6 && !var3 && !var5)
            {
                var7 = 6;
            }

            if (var4 && var5 && !var3 && !var6)
            {
                var7 = 7;
            }

            if (var3 && var5 && !var4 && !var6)
            {
                var7 = 8;
            }

            if (var3 && var6 && !var4 && !var5)
            {
                var7 = 9;
            }
        }

        if (var7 == -1)
        {
            if (var3 || var4)
            {
                var7 = 0;
            }

            if (var5 || var6)
            {
                var7 = 1;
            }

            if (!this.f)
            {
                if (par1)
                {
                    if (var4 && var6)
                    {
                        var7 = 6;
                    }

                    if (var5 && var4)
                    {
                        var7 = 7;
                    }

                    if (var6 && var3)
                    {
                        var7 = 9;
                    }

                    if (var3 && var5)
                    {
                        var7 = 8;
                    }
                }
                else
                {
                    if (var3 && var5)
                    {
                        var7 = 8;
                    }

                    if (var6 && var3)
                    {
                        var7 = 9;
                    }

                    if (var5 && var4)
                    {
                        var7 = 7;
                    }

                    if (var4 && var6)
                    {
                        var7 = 6;
                    }
                }
            }
        }

        if (var7 == 0)
        {
            if (BlockMinecartTrack.e_(this.b, this.c, this.d + 1, this.e - 1))
            {
                var7 = 4;
            }

            if (BlockMinecartTrack.e_(this.b, this.c, this.d + 1, this.e + 1))
            {
                var7 = 5;
            }
        }

        if (var7 == 1)
        {
            if (BlockMinecartTrack.e_(this.b, this.c + 1, this.d + 1, this.e))
            {
                var7 = 2;
            }

            if (BlockMinecartTrack.e_(this.b, this.c - 1, this.d + 1, this.e))
            {
                var7 = 3;
            }
        }

        if (var7 < 0)
        {
            var7 = 0;
        }

        this.a(var7);
        int var8 = var7;

        if (this.f)
        {
            var8 = this.b.getData(this.c, this.d, this.e) & 8 | var7;
        }

        if (par2 || this.b.getData(this.c, this.d, this.e) != var8)
        {
            this.b.setData(this.c, this.d, this.e, var8);

            for (int var9 = 0; var9 < this.g.size(); ++var9)
            {
                MinecartTrackLogic var10 = this.a((ChunkPosition) this.g.get(var9));

                if (var10 != null)
                {
                    var10.a();

                    if (var10.c(this))
                    {
                        var10.d(this);
                    }
                }
            }
        }
    }

    /**
     * Get the number of adjacent tracks.
     */
    static int a(MinecartTrackLogic par0RailLogic)
    {
        return par0RailLogic.b();
    }
}
