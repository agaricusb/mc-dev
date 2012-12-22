package net.minecraft.server;

import java.util.List;

public class CommandDeop extends CommandAbstract
{
    public String c()
    {
        return "deop";
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
        return par1ICommandSender.a("commands.deop.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0)
        {
            MinecraftServer.getServer().getPlayerList().removeOp(par2ArrayOfStr[0]);
            a(par1ICommandSender, "commands.deop.success", new Object[]{par2ArrayOfStr[0]});
        }
        else
        {
            throw new ExceptionUsage("commands.deop.usage", new Object[0]);
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? a(par2ArrayOfStr, MinecraftServer.getServer().getPlayerList().getOPs()) : null;
    }
}
