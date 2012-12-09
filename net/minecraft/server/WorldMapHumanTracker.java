package net.minecraft.server;

import java.util.Iterator;

public class WorldMapHumanTracker
{
    /** Reference for EntityPlayer object in MapInfo */
    public final EntityHuman trackee;
    public int[] b;
    public int[] c;

    /**
     * updated by x = mod(x*11,128) +1  x-1 is used to index field_76209_b and field_76210_c
     */
    private int f;
    private int g;

    /**
     * a cache of the result from getPlayersOnMap so that it is not resent when nothing changes
     */
    private byte[] h;
    public int d;
    private boolean i;

    /** reference in MapInfo to MapData object */
    final WorldMap worldMap;

    public WorldMapHumanTracker(WorldMap par1MapData, EntityHuman par2EntityPlayer)
    {
        this.worldMap = par1MapData;
        this.b = new int[128];
        this.c = new int[128];
        this.f = 0;
        this.g = 0;
        this.i = false;
        this.trackee = par2EntityPlayer;

        for (int var3 = 0; var3 < this.b.length; ++var3)
        {
            this.b[var3] = 0;
            this.c[var3] = 127;
        }
    }

    /**
     * returns a 1+players*3 array, of x,y, and color . the name of this function may be partially wrong, as there is a
     * second branch to the code here
     */
    public byte[] a(ItemStack par1ItemStack)
    {
        byte[] var2;

        if (!this.i)
        {
            var2 = new byte[] {(byte)2, this.worldMap.scale};
            this.i = true;
            return var2;
        }
        else
        {
            int var3;
            int var10;

            if (--this.g < 0)
            {
                this.g = 4;
                var2 = new byte[this.worldMap.g.size() * 3 + 1];
                var2[0] = 1;
                var3 = 0;

                for (Iterator var4 = this.worldMap.g.values().iterator(); var4.hasNext(); ++var3)
                {
                    WorldMapDecoration var5 = (WorldMapDecoration)var4.next();
                    var2[var3 * 3 + 1] = (byte)(var5.type << 4 | var5.rotation & 15);
                    var2[var3 * 3 + 2] = var5.locX;
                    var2[var3 * 3 + 3] = var5.locY;
                }

                boolean var9 = !par1ItemStack.y();

                if (this.h != null && this.h.length == var2.length)
                {
                    for (var10 = 0; var10 < var2.length; ++var10)
                    {
                        if (var2[var10] != this.h[var10])
                        {
                            var9 = false;
                            break;
                        }
                    }
                }
                else
                {
                    var9 = false;
                }

                if (!var9)
                {
                    this.h = var2;
                    return var2;
                }
            }

            for (int var8 = 0; var8 < 1; ++var8)
            {
                var3 = this.f++ * 11 % 128;

                if (this.b[var3] >= 0)
                {
                    int var11 = this.c[var3] - this.b[var3] + 1;
                    var10 = this.b[var3];
                    byte[] var6 = new byte[var11 + 3];
                    var6[0] = 0;
                    var6[1] = (byte)var3;
                    var6[2] = (byte)var10;

                    for (int var7 = 0; var7 < var6.length - 3; ++var7)
                    {
                        var6[var7 + 3] = this.worldMap.colors[(var7 + var10) * 128 + var3];
                    }

                    this.c[var3] = -1;
                    this.b[var3] = -1;
                    return var6;
                }
            }

            return null;
        }
    }
}
