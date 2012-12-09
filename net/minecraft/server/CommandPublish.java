package net.minecraft.server;

public class CommandPublish extends CommandAbstract
{
    public String c()
    {
        return "publish";
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
        String var3 = MinecraftServer.getServer().a(EnumGamemode.SURVIVAL, false);

        if (var3 != null)
        {
            a(par1ICommandSender, "commands.publish.started", new Object[]{var3});
        }
        else
        {
            a(par1ICommandSender, "commands.publish.failed", new Object[0]);
        }
    }
}
