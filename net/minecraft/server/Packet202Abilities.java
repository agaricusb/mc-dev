package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet202Abilities extends Packet
{
    /** Disables player damage. */
    private boolean a = false;

    /** Indicates whether the player is flying or not. */
    private boolean b = false;

    /** Whether or not to allow the player to fly when they double jump. */
    private boolean c = false;

    /**
     * Used to determine if creative mode is enabled, and therefore if items should be depleted on usage
     */
    private boolean d = false;
    private float e;
    private float f;

    public Packet202Abilities() {}

    public Packet202Abilities(PlayerAbilities par1PlayerCapabilities)
    {
        this.a(par1PlayerCapabilities.isInvulnerable);
        this.b(par1PlayerCapabilities.isFlying);
        this.c(par1PlayerCapabilities.canFly);
        this.d(par1PlayerCapabilities.canInstantlyBuild);
        this.a(par1PlayerCapabilities.a());
        this.b(par1PlayerCapabilities.b());
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        byte var2 = par1DataInputStream.readByte();
        this.a((var2 & 1) > 0);
        this.b((var2 & 2) > 0);
        this.c((var2 & 4) > 0);
        this.d((var2 & 8) > 0);
        this.a((float) par1DataInputStream.readByte() / 255.0F);
        this.b((float) par1DataInputStream.readByte() / 255.0F);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        byte var2 = 0;

        if (this.d())
        {
            var2 = (byte)(var2 | 1);
        }

        if (this.f())
        {
            var2 = (byte)(var2 | 2);
        }

        if (this.g())
        {
            var2 = (byte)(var2 | 4);
        }

        if (this.h())
        {
            var2 = (byte)(var2 | 8);
        }

        par1DataOutputStream.writeByte(var2);
        par1DataOutputStream.writeByte((int)(this.e * 255.0F));
        par1DataOutputStream.writeByte((int)(this.f * 255.0F));
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void handle(NetHandler par1NetHandler)
    {
        par1NetHandler.a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 2;
    }

    public boolean d()
    {
        return this.a;
    }

    /**
     * Sets whether damage is disabled or not.
     */
    public void a(boolean par1)
    {
        this.a = par1;
    }

    public boolean f()
    {
        return this.b;
    }

    /**
     * Sets whether we're currently flying or not.
     */
    public void b(boolean par1)
    {
        this.b = par1;
    }

    public boolean g()
    {
        return this.c;
    }

    public void c(boolean par1)
    {
        this.c = par1;
    }

    public boolean h()
    {
        return this.d;
    }

    public void d(boolean par1)
    {
        this.d = par1;
    }

    /**
     * Sets the flying speed.
     */
    public void a(float par1)
    {
        this.e = par1;
    }

    /**
     * Sets the walking speed.
     */
    public void b(float par1)
    {
        this.f = par1;
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
