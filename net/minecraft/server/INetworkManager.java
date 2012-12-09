package net.minecraft.server;

import java.net.SocketAddress;

public interface INetworkManager
{
    /**
     * Sets the NetHandler for this NetworkManager. Server-only.
     */
    void a(NetHandler var1);

    /**
     * Adds the packet to the correct send queue (chunk data packets go to a separate queue).
     */
    void queue(Packet var1);

    /**
     * Wakes reader and writer threads
     */
    void a();

    /**
     * Checks timeouts and processes all pending read packets.
     */
    void b();

    /**
     * Returns the socket address of the remote side. Server-only.
     */
    SocketAddress getSocketAddress();

    /**
     * Shuts down the server. (Only actually used on the server)
     */
    void d();

    /**
     * Returns the number of chunk data packets waiting to be sent.
     */
    int e();

    /**
     * Shuts down the network with the specified reason. Closes all streams and sockets, spawns NetworkMasterThread to
     * stop reading and writing threads.
     */
    void a(String var1, Object... var2);
}
