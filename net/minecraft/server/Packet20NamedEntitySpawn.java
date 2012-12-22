package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Packet20NamedEntitySpawn extends Packet
{
    /** The entity ID, in this case it's the player ID. */
    public int a;

    /** The player's name. */
    public String b;

    /** The player's X position. */
    public int c;

    /** The player's Y position. */
    public int d;

    /** The player's Z position. */
    public int e;

    /** The player's rotation. */
    public byte f;

    /** The player's pitch. */
    public byte g;

    /** The current item the player is holding. */
    public int h;
    private DataWatcher i;
    private List j;

    public Packet20NamedEntitySpawn() {}

    public Packet20NamedEntitySpawn(EntityHuman par1EntityPlayer)
    {
        this.a = par1EntityPlayer.id;
        this.b = par1EntityPlayer.name;
        this.c = MathHelper.floor(par1EntityPlayer.locX * 32.0D);
        this.d = MathHelper.floor(par1EntityPlayer.locY * 32.0D);
        this.e = MathHelper.floor(par1EntityPlayer.locZ * 32.0D);
        this.f = (byte)((int)(par1EntityPlayer.yaw * 256.0F / 360.0F));
        this.g = (byte)((int)(par1EntityPlayer.pitch * 256.0F / 360.0F));
        ItemStack var2 = par1EntityPlayer.inventory.getItemInHand();
        this.h = var2 == null ? 0 : var2.id;
        this.i = par1EntityPlayer.getDataWatcher();
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = a(par1DataInputStream, 16);
        this.c = par1DataInputStream.readInt();
        this.d = par1DataInputStream.readInt();
        this.e = par1DataInputStream.readInt();
        this.f = par1DataInputStream.readByte();
        this.g = par1DataInputStream.readByte();
        this.h = par1DataInputStream.readShort();
        this.j = DataWatcher.a(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        a(this.b, par1DataOutputStream);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.writeInt(this.d);
        par1DataOutputStream.writeInt(this.e);
        par1DataOutputStream.writeByte(this.f);
        par1DataOutputStream.writeByte(this.g);
        par1DataOutputStream.writeShort(this.h);
        this.i.a(par1DataOutputStream);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void handle(Connection par1NetHandler)
    {
        par1NetHandler.a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 28;
    }
}
