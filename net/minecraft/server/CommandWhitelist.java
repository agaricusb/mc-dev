package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

public class CommandWhitelist extends CommandAbstract
{
    public String c()
    {
        return "whitelist";
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
        return par1ICommandSender.a("commands.whitelist.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length >= 1)
        {
            if (par2ArrayOfStr[0].equals("on"))
            {
                MinecraftServer.getServer().getServerConfigurationManager().setHasWhitelist(true);
                a(par1ICommandSender, "commands.whitelist.enabled", new Object[0]);
                return;
            }

            if (par2ArrayOfStr[0].equals("off"))
            {
                MinecraftServer.getServer().getServerConfigurationManager().setHasWhitelist(false);
                a(par1ICommandSender, "commands.whitelist.disabled", new Object[0]);
                return;
            }

            if (par2ArrayOfStr[0].equals("list"))
            {
                par1ICommandSender.sendMessage(par1ICommandSender.a("commands.whitelist.list", new Object[]{Integer.valueOf(MinecraftServer.getServer().getServerConfigurationManager().getWhitelisted().size()), Integer.valueOf(MinecraftServer.getServer().getServerConfigurationManager().getSeenPlayers().length)}));
                par1ICommandSender.sendMessage(a(MinecraftServer.getServer().getServerConfigurationManager().getWhitelisted().toArray(new String[0])));
                return;
            }

            if (par2ArrayOfStr[0].equals("add"))
            {
                if (par2ArrayOfStr.length < 2)
                {
                    throw new ExceptionUsage("commands.whitelist.add.usage", new Object[0]);
                }

                MinecraftServer.getServer().getServerConfigurationManager().addWhitelist(par2ArrayOfStr[1]);
                a(par1ICommandSender, "commands.whitelist.add.success", new Object[]{par2ArrayOfStr[1]});
                return;
            }

            if (par2ArrayOfStr[0].equals("remove"))
            {
                if (par2ArrayOfStr.length < 2)
                {
                    throw new ExceptionUsage("commands.whitelist.remove.usage", new Object[0]);
                }

                MinecraftServer.getServer().getServerConfigurationManager().removeWhitelist(par2ArrayOfStr[1]);
                a(par1ICommandSender, "commands.whitelist.remove.success", new Object[]{par2ArrayOfStr[1]});
                return;
            }

            if (par2ArrayOfStr[0].equals("reload"))
            {
                MinecraftServer.getServer().getServerConfigurationManager().reloadWhitelist();
                a(par1ICommandSender, "commands.whitelist.reloaded", new Object[0]);
                return;
            }
        }

        throw new ExceptionUsage("commands.whitelist.usage", new Object[0]);
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length == 1)
        {
            return a(par2ArrayOfStr, new String[]{"on", "off", "list", "add", "remove", "reload"});
        }
        else
        {
            if (par2ArrayOfStr.length == 2)
            {
                if (par2ArrayOfStr[0].equals("add"))
                {
                    String[] var3 = MinecraftServer.getServer().getServerConfigurationManager().getSeenPlayers();
                    ArrayList var4 = new ArrayList();
                    String var5 = par2ArrayOfStr[par2ArrayOfStr.length - 1];
                    String[] var6 = var3;
                    int var7 = var3.length;

                    for (int var8 = 0; var8 < var7; ++var8)
                    {
                        String var9 = var6[var8];

                        if (a(var5, var9) && !MinecraftServer.getServer().getServerConfigurationManager().getWhitelisted().contains(var9))
                        {
                            var4.add(var9);
                        }
                    }

                    return var4;
                }

                if (par2ArrayOfStr[0].equals("remove"))
                {
                    return a(par2ArrayOfStr, MinecraftServer.getServer().getServerConfigurationManager().getWhitelisted());
                }
            }

            return null;
        }
    }
}
