package net.minecraft.server;

import java.util.List;

public class CommandBanList extends CommandAbstract
{
    public String c()
    {
        return "banlist";
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
        return (MinecraftServer.getServer().getServerConfigurationManager().getIPBans().isEnabled() || MinecraftServer.getServer().getServerConfigurationManager().getNameBans().isEnabled()) && super.b(par1ICommandSender);
    }

    public String a(ICommandListener par1ICommandSender)
    {
        return par1ICommandSender.a("commands.banlist.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].equalsIgnoreCase("ips"))
        {
            par1ICommandSender.sendMessage(par1ICommandSender.a("commands.banlist.ips", new Object[]{Integer.valueOf(MinecraftServer.getServer().getServerConfigurationManager().getIPBans().getEntries().size())}));
            par1ICommandSender.sendMessage(a(MinecraftServer.getServer().getServerConfigurationManager().getIPBans().getEntries().keySet().toArray()));
        }
        else
        {
            par1ICommandSender.sendMessage(par1ICommandSender.a("commands.banlist.players", new Object[]{Integer.valueOf(MinecraftServer.getServer().getServerConfigurationManager().getNameBans().getEntries().size())}));
            par1ICommandSender.sendMessage(a(MinecraftServer.getServer().getServerConfigurationManager().getNameBans().getEntries().keySet().toArray()));
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? a(par2ArrayOfStr, new String[]{"players", "ips"}): null;
    }
}
