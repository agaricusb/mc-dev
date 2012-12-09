package net.minecraft.server;

import java.util.List;

public class CommandMe extends CommandAbstract
{
    public String c()
    {
        return "me";
    }

    /**
     * Return the required permission level for this command.
     */
    public int a()
    {
        return 0;
    }

    public String a(ICommandListener par1ICommandSender)
    {
        return par1ICommandSender.a("commands.me.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length > 0)
        {
            String var3 = a(par1ICommandSender, par2ArrayOfStr, 0);
            MinecraftServer.getServer().getServerConfigurationManager().sendAll(new Packet3Chat("* " + par1ICommandSender.getName() + " " + var3));
        }
        else
        {
            throw new ExceptionUsage("commands.me.usage", new Object[0]);
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return a(par2ArrayOfStr, MinecraftServer.getServer().getPlayers());
    }
}
