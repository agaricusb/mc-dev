package net.minecraft.server;

import java.util.List;

public class CommandClear extends CommandAbstract
{
    public String c()
    {
        return "clear";
    }

    public String a(ICommandListener par1ICommandSender)
    {
        return par1ICommandSender.a("commands.clear.usage", new Object[0]);
    }

    /**
     * Return the required permission level for this command.
     */
    public int a()
    {
        return 2;
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        EntityPlayer var3 = par2ArrayOfStr.length == 0 ? c(par1ICommandSender) : c(par1ICommandSender, par2ArrayOfStr[0]);
        int var4 = par2ArrayOfStr.length >= 2 ? a(par1ICommandSender, par2ArrayOfStr[1], 1) : -1;
        int var5 = par2ArrayOfStr.length >= 3 ? a(par1ICommandSender, par2ArrayOfStr[2], 0) : -1;
        int var6 = var3.inventory.b(var4, var5);
        var3.defaultContainer.b();
        a(par1ICommandSender, "commands.clear.success", new Object[]{var3.getLocalizedName(), Integer.valueOf(var6)});
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? a(par2ArrayOfStr, this.d()) : null;
    }

    /**
     * Return all usernames currently connected to the server.
     */
    protected String[] d()
    {
        return MinecraftServer.getServer().getPlayers();
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean a(int par1)
    {
        return par1 == 0;
    }
}
