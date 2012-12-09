package net.minecraft.server;

public class CommandStop extends CommandAbstract
{
    public String c()
    {
        return "stop";
    }

    /**
     * Return the required permission level for this command.
     */
    public int a()
    {
        return 4;
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        a(par1ICommandSender, "commands.stop.start", new Object[0]);
        MinecraftServer.getServer().safeShutdown();
    }
}
