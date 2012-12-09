package net.minecraft.server;

public class RemoteControlCommandListener implements ICommandListener
{
    /** Single instance of RConConsoleSource */
    public static final RemoteControlCommandListener instance = new RemoteControlCommandListener();

    /** RCon string buffer for log */
    private StringBuffer b = new StringBuffer();

    /**
     * Clears the RCon log
     */
    public void c()
    {
        this.b.setLength(0);
    }

    /**
     * Gets the contents of the RCon log
     */
    public String d()
    {
        return this.b.toString();
    }

    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    public String getName()
    {
        return "Rcon";
    }

    public void sendMessage(String par1Str)
    {
        this.b.append(par1Str);
    }

    /**
     * Returns true if the command sender is allowed to use the given command.
     */
    public boolean a(int par1, String par2Str)
    {
        return true;
    }

    /**
     * Translates and formats the given string key with the given arguments.
     */
    public String a(String par1Str, Object... par2ArrayOfObj)
    {
        return LocaleLanguage.a().a(par1Str, par2ArrayOfObj);
    }

    /**
     * Return the position for this command sender.
     */
    public ChunkCoordinates b()
    {
        return new ChunkCoordinates(0, 0, 0);
    }
}
