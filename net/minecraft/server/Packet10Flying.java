package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet10Flying extends Packet
{
    /** The player's X position. */
    public double x;

    /** The player's Y position. */
    public double y;

    /** The player's Z position. */
    public double z;

    /** The player's stance. (boundingBox.minY) */
    public double stance;

    /** The player's yaw rotation. */
    public float yaw;

    /** The player's pitch rotation. */
    public float pitch;

    /** True if the client is on the ground. */
    public boolean g;

    /** Boolean set to true if the player is moving. */
    public boolean hasPos;

    /** Boolean set to true if the player is rotating. */
    public boolean hasLook;

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void handle(Connection par1NetHandler)
    {
        par1NetHandler.a(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.g = par1DataInputStream.read() != 0;
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.write(this.g ? 1 : 0);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 1;
    }

    /**
     * only false for the abstract Packet class, all real packets return true
     */
    public boolean e()
    {
        return true;
    }

    /**
     * eg return packet30entity.entityId == entityId; WARNING : will throw if you compare a packet to a different packet
     * class
     */
    public boolean a(Packet par1Packet)
    {
        return true;
    }
}
