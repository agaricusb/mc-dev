package net.minecraft.server;

public interface ICommandDispatcher
{
    /**
     * Sends a message to the admins of the server from a given CommandSender with the given resource string and given
     * extra srings. If the int par2 is even or zero, the original sender is also notified.
     */
    void a(ICommandListener var1, int var2, String var3, Object... var4);
}
