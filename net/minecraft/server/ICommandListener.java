package net.minecraft.server;

public interface ICommandListener
{
    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    String getName();

    void sendMessage(String var1);

    /**
     * Returns true if the command sender is allowed to use the given command.
     */
    boolean a(int var1, String var2);

    /**
     * Translates and formats the given string key with the given arguments.
     */
    String a(String var1, Object... var2);

    /**
     * Return the position for this command sender.
     */
    ChunkCoordinates b();
}
