package net.minecraft.server;

public interface IMinecraftServer
{
    /**
     * Gets an integer property. If it does not exist, set it to the specified value.
     */
    int a(String var1, int var2);

    /**
     * Gets a string property. If it does not exist, set it to the specified value.
     */
    String a(String var1, String var2);

    /**
     * Saves an Object with the given property name.
     */
    void a(String var1, Object var2);

    /**
     * Saves all of the server properties to the properties file.
     */
    void a();

    /**
     * Returns the filename where server properties are stored
     */
    String b_();

    /**
     * Returns the server's hostname.
     */
    String u();

    /**
     * Never used, but "getServerPort" is already taken.
     */
    int v();

    /**
     * Returns the server message of the day
     */
    String w();

    /**
     * Returns the server's Minecraft version as string.
     */
    String getVersion();

    /**
     * Returns the number of players currently on the server.
     */
    int y();

    /**
     * Returns the maximum number of players allowed on the server.
     */
    int z();

    /**
     * Returns an array of the usernames of all the connected players.
     */
    String[] getPlayers();

    String J();

    /**
     * Used by RCon's Query in the form of "MajorServerMod 1.2.3: MyPlugin 1.3; AnotherPlugin 2.1; AndSoForth 1.0".
     */
    String getPlugins();

    /**
     * Handle a command received by an RCon instance
     */
    String h(String var1);

    /**
     * Returns true if debugging is enabled, false otherwise.
     */
    boolean isDebugging();

    /**
     * Logs the message with a level of INFO.
     */
    void info(String var1);

    /**
     * Logs the message with a level of WARN.
     */
    void warning(String var1);

    /**
     * Logs the error message with a level of SEVERE.
     */
    void i(String var1);

    /**
     * If isDebuggingEnabled(), logs the message with a level of INFO.
     */
    void j(String var1);
}
