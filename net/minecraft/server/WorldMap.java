package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WorldMap extends WorldMapBase
{
    public int centerX;
    public int centerZ;
    public byte map;
    public byte scale;

    /** colours */
    public byte[] colors = new byte[16384];

    /**
     * Holds a reference to the MapInfo of the players who own a copy of the map
     */
    public List f = new ArrayList();

    /**
     * Holds a reference to the players who own a copy of the map and a reference to their MapInfo
     */
    private Map i = new HashMap();
    public Map g = new LinkedHashMap();

    public WorldMap(String par1Str)
    {
        super(par1Str);
    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        this.map = par1NBTTagCompound.getByte("dimension");
        this.centerX = par1NBTTagCompound.getInt("xCenter");
        this.centerZ = par1NBTTagCompound.getInt("zCenter");
        this.scale = par1NBTTagCompound.getByte("scale");

        if (this.scale < 0)
        {
            this.scale = 0;
        }

        if (this.scale > 4)
        {
            this.scale = 4;
        }

        short var2 = par1NBTTagCompound.getShort("width");
        short var3 = par1NBTTagCompound.getShort("height");

        if (var2 == 128 && var3 == 128)
        {
            this.colors = par1NBTTagCompound.getByteArray("colors");
        }
        else
        {
            byte[] var4 = par1NBTTagCompound.getByteArray("colors");
            this.colors = new byte[16384];
            int var5 = (128 - var2) / 2;
            int var6 = (128 - var3) / 2;

            for (int var7 = 0; var7 < var3; ++var7)
            {
                int var8 = var7 + var6;

                if (var8 >= 0 || var8 < 128)
                {
                    for (int var9 = 0; var9 < var2; ++var9)
                    {
                        int var10 = var9 + var5;

                        if (var10 >= 0 || var10 < 128)
                        {
                            this.colors[var10 + var8 * 128] = var4[var9 + var7 * var2];
                        }
                    }
                }
            }
        }
    }

    /**
     * write data to NBTTagCompound from this MapDataBase, similar to Entities and TileEntities
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("dimension", this.map);
        par1NBTTagCompound.setInt("xCenter", this.centerX);
        par1NBTTagCompound.setInt("zCenter", this.centerZ);
        par1NBTTagCompound.setByte("scale", this.scale);
        par1NBTTagCompound.setShort("width", (short) 128);
        par1NBTTagCompound.setShort("height", (short) 128);
        par1NBTTagCompound.setByteArray("colors", this.colors);
    }

    /**
     * Adds the player passed to the list of visible players and checks to see which players are visible
     */
    public void a(EntityHuman par1EntityPlayer, ItemStack par2ItemStack)
    {
        if (!this.i.containsKey(par1EntityPlayer))
        {
            WorldMapHumanTracker var3 = new WorldMapHumanTracker(this, par1EntityPlayer);
            this.i.put(par1EntityPlayer, var3);
            this.f.add(var3);
        }

        if (!par1EntityPlayer.inventory.c(par2ItemStack))
        {
            this.g.remove(par1EntityPlayer.getName());
        }

        for (int var5 = 0; var5 < this.f.size(); ++var5)
        {
            WorldMapHumanTracker var4 = (WorldMapHumanTracker)this.f.get(var5);

            if (!var4.trackee.dead && (var4.trackee.inventory.c(par2ItemStack) || par2ItemStack.y()))
            {
                if (!par2ItemStack.y() && var4.trackee.dimension == this.map)
                {
                    this.a(0, var4.trackee.world, var4.trackee.getName(), var4.trackee.locX, var4.trackee.locZ, (double) var4.trackee.yaw);
                }
            }
            else
            {
                this.i.remove(var4.trackee);
                this.f.remove(var4);
            }
        }

        if (par2ItemStack.y())
        {
            this.a(1, par1EntityPlayer.world, "frame-" + par2ItemStack.z().id, (double) par2ItemStack.z().x, (double) par2ItemStack.z().z, (double) (par2ItemStack.z().direction * 90));
        }
    }

    private void a(int par1, World par2World, String par3Str, double par4, double par6, double par8)
    {
        int var10 = 1 << this.scale;
        float var11 = (float)(par4 - (double)this.centerX) / (float)var10;
        float var12 = (float)(par6 - (double)this.centerZ) / (float)var10;
        byte var13 = (byte)((int)((double)(var11 * 2.0F) + 0.5D));
        byte var14 = (byte)((int)((double)(var12 * 2.0F) + 0.5D));
        byte var16 = 63;
        byte var15;

        if (var11 >= (float)(-var16) && var12 >= (float)(-var16) && var11 <= (float)var16 && var12 <= (float)var16)
        {
            par8 += par8 < 0.0D ? -8.0D : 8.0D;
            var15 = (byte)((int)(par8 * 16.0D / 360.0D));

            if (this.map < 0)
            {
                int var17 = (int)(par2World.getWorldData().getDayTime() / 10L);
                var15 = (byte)(var17 * var17 * 34187121 + var17 * 121 >> 15 & 15);
            }
        }
        else
        {
            if (Math.abs(var11) >= 320.0F || Math.abs(var12) >= 320.0F)
            {
                this.g.remove(par3Str);
                return;
            }

            par1 = 6;
            var15 = 0;

            if (var11 <= (float)(-var16))
            {
                var13 = (byte)((int)((double)(var16 * 2) + 2.5D));
            }

            if (var12 <= (float)(-var16))
            {
                var14 = (byte)((int)((double)(var16 * 2) + 2.5D));
            }

            if (var11 >= (float)var16)
            {
                var13 = (byte)(var16 * 2 + 1);
            }

            if (var12 >= (float)var16)
            {
                var14 = (byte)(var16 * 2 + 1);
            }
        }

        this.g.put(par3Str, new WorldMapDecoration(this, (byte)par1, var13, var14, var15));
    }

    /**
     * Get byte array of packet data to send to players on map for updating map data
     */
    public byte[] getUpdatePacket(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        WorldMapHumanTracker var4 = (WorldMapHumanTracker)this.i.get(par3EntityPlayer);
        return var4 == null ? null : var4.a(par1ItemStack);
    }

    /**
     * Marks a vertical range of pixels as being modified so they will be resent to clients. Parameters: X, lowest Y,
     * highest Y
     */
    public void flagDirty(int par1, int par2, int par3)
    {
        super.c();

        for (int var4 = 0; var4 < this.f.size(); ++var4)
        {
            WorldMapHumanTracker var5 = (WorldMapHumanTracker)this.f.get(var4);

            if (var5.b[par1] < 0 || var5.b[par1] > par2)
            {
                var5.b[par1] = par2;
            }

            if (var5.c[par1] < 0 || var5.c[par1] < par3)
            {
                var5.c[par1] = par3;
            }
        }
    }

    public WorldMapHumanTracker a(EntityHuman par1EntityPlayer)
    {
        WorldMapHumanTracker var2 = (WorldMapHumanTracker)this.i.get(par1EntityPlayer);

        if (var2 == null)
        {
            var2 = new WorldMapHumanTracker(this, par1EntityPlayer);
            this.i.put(par1EntityPlayer, var2);
            this.f.add(var2);
        }

        return var2;
    }
}
