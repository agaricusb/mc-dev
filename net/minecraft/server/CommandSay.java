package net.minecraft.server;

import java.util.List;

public class CommandSay extends CommandAbstract
{
    public String c()
    {
        return "say";
    }

    /**
     * Return the required permission level for this command.
     */
    public int a()
    {
        return 1;
    }

    public String a(ICommandListener par1ICommandSender)
    {
        return par1ICommandSender.a("commands.say.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length > 0 && par2ArrayOfStr[0].length() > 0)
        {
            String var3 = a(par1ICommandSender, par2ArrayOfStr, 0, true);
            MinecraftServer.getServer().getServerConfigurationManager().sendAll(new Packet3Chat(String.format("[%s] %s", new Object[]{par1ICommandSender.getName(), var3})));
        }
        else
        {
            throw new ExceptionUsage("commands.say.usage", new Object[0]);
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length >= 1 ? a(par2ArrayOfStr, MinecraftServer.getServer().getPlayers()) : null;
    }
}
