package net.minecraft.server;

import java.util.List;

public class CommandPardon extends CommandAbstract
{
    public String c()
    {
        return "pardon";
    }

    /**
     * Return the required permission level for this command.
     */
    public int a()
    {
        return 3;
    }

    public String a(ICommandListener par1ICommandSender)
    {
        return par1ICommandSender.a("commands.unban.usage", new Object[0]);
    }

    /**
     * Returns true if the given command sender is allowed to use this command.
     */
    public boolean b(ICommandListener par1ICommandSender)
    {
        return MinecraftServer.getServer().getPlayerList().getNameBans().isEnabled() && super.b(par1ICommandSender);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0)
        {
            MinecraftServer.getServer().getPlayerList().getNameBans().remove(par2ArrayOfStr[0]);
            a(par1ICommandSender, "commands.unban.success", new Object[]{par2ArrayOfStr[0]});
        }
        else
        {
            throw new ExceptionUsage("commands.unban.usage", new Object[0]);
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? a(par2ArrayOfStr, MinecraftServer.getServer().getPlayerList().getNameBans().getEntries().keySet()) : null;
    }
}
