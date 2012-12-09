package net.minecraft.server;

import java.net.DatagramPacket;
import java.util.Date;
import java.util.Random;

class RemoteStatusChallenge
{
    /** The creation timestamp for this auth */
    private long time;

    /** A random integer value to be used for client response authentication */
    private int token;

    /** A client-provided request ID associated with this query */
    private byte[] identity;

    /** A unique string of bytes used to verify client auth */
    private byte[] response;

    /** The request ID stored as a String */
    private String f;

    /** The RConThreadQuery that this is probably an inner class of */
    final RemoteStatusListener a;

    public RemoteStatusChallenge(RemoteStatusListener par1RConThreadQuery, DatagramPacket par2DatagramPacket)
    {
        this.a = par1RConThreadQuery;
        this.time = (new Date()).getTime();
        byte[] var3 = par2DatagramPacket.getData();
        this.identity = new byte[4];
        this.identity[0] = var3[3];
        this.identity[1] = var3[4];
        this.identity[2] = var3[5];
        this.identity[3] = var3[6];
        this.f = new String(this.identity);
        this.token = (new Random()).nextInt(16777216);
        this.response = String.format("\t%s%d\u0000", new Object[] {this.f, Integer.valueOf(this.token)}).getBytes();
    }

    /**
     * Returns true if the auth's creation timestamp is less than the given time, otherwise false
     */
    public Boolean isExpired(long par1)
    {
        return Boolean.valueOf(this.time < par1);
    }

    /**
     * Returns the random challenge number assigned to this auth
     */
    public int getToken()
    {
        return this.token;
    }

    /**
     * Returns the auth challenge value
     */
    public byte[] getChallengeResponse()
    {
        return this.response;
    }

    /**
     * Returns the request ID provided by the client.
     */
    public byte[] getIdentityToken()
    {
        return this.identity;
    }
}
