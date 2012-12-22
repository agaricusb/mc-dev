package net.minecraft.server;

import java.util.List;

public class CommandBan extends CommandAbstract
{
    public String c()
    {
        return "ban";
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
        return par1ICommandSender.a("commands.ban.usage", new Object[0]);
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
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].length() > 0)
        {
            EntityPlayer var3 = MinecraftServer.getServer().getPlayerList().f(par2ArrayOfStr[0]);
            BanEntry var4 = new BanEntry(par2ArrayOfStr[0]);
            var4.setSource(par1ICommandSender.getName());

            if (par2ArrayOfStr.length >= 2)
            {
                var4.setReason(a(par1ICommandSender, par2ArrayOfStr, 1));
            }

            MinecraftServer.getServer().getPlayerList().getNameBans().add(var4);

            if (var3 != null)
            {
                var3.playerConnection.disconnect("You are banned from this server.");
            }

            a(par1ICommandSender, "commands.ban.success", new Object[]{par2ArrayOfStr[0]});
        }
        else
        {
            throw new ExceptionUsage("commands.ban.usage", new Object[0]);
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
