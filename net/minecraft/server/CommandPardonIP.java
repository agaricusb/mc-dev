package net.minecraft.server;

import java.util.List;
import java.util.regex.Matcher;

public class CommandPardonIP extends CommandAbstract
{
    public String c()
    {
        return "pardon-ip";
    }

    /**
     * Return the required permission level for this command.
     */
    public int a()
    {
        return 3;
    }

    /**
     * Returns true if the given command sender is allowed to use this command.
     */
    public boolean b(ICommandListener par1ICommandSender)
    {
        return MinecraftServer.getServer().getServerConfigurationManager().getIPBans().isEnabled() && super.b(par1ICommandSender);
    }

    public String a(ICommandListener par1ICommandSender)
    {
        return par1ICommandSender.a("commands.unbanip.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 1)
        {
            Matcher var3 = CommandBanIp.a.matcher(par2ArrayOfStr[0]);

            if (var3.matches())
            {
                MinecraftServer.getServer().getServerConfigurationManager().getIPBans().remove(par2ArrayOfStr[0]);
                a(par1ICommandSender, "commands.unbanip.success", new Object[]{par2ArrayOfStr[0]});
            }
            else
            {
                throw new ExceptionInvalidSyntax("commands.unbanip.invalid", new Object[0]);
            }
        }
        else
        {
            throw new ExceptionUsage("commands.unbanip.usage", new Object[0]);
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? a(par2ArrayOfStr, MinecraftServer.getServer().getServerConfigurationManager().getIPBans().getEntries().keySet()) : null;
    }
}
