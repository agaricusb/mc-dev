package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandBanIp extends CommandAbstract
{
    public static final Pattern a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public String c()
    {
        return "ban-ip";
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
        return MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled() && super.b(par1ICommandSender);
    }

    public String a(ICommandListener par1ICommandSender)
    {
        return par1ICommandSender.a("commands.banip.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].length() > 1)
        {
            Matcher var3 = a.matcher(par2ArrayOfStr[0]);
            String var4 = null;

            if (par2ArrayOfStr.length >= 2)
            {
                var4 = a(par1ICommandSender, par2ArrayOfStr, 1);
            }

            if (var3.matches())
            {
                this.a(par1ICommandSender, par2ArrayOfStr[0], var4);
            }
            else
            {
                EntityPlayer var5 = MinecraftServer.getServer().getPlayerList().f(par2ArrayOfStr[0]);

                if (var5 == null)
                {
                    throw new ExceptionPlayerNotFound("commands.banip.invalid", new Object[0]);
                }

                this.a(par1ICommandSender, var5.q(), var4);
            }
        }
        else
        {
            throw new ExceptionUsage("commands.banip.usage", new Object[0]);
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? a(par2ArrayOfStr, MinecraftServer.getServer().getPlayers()) : null;
    }

    /**
     * Actually does the banning work.
     */
    protected void a(ICommandListener par1ICommandSender, String par2Str, String par3Str)
    {
        BanEntry var4 = new BanEntry(par2Str);
        var4.setSource(par1ICommandSender.getName());

        if (par3Str != null)
        {
            var4.setReason(par3Str);
        }

        MinecraftServer.getServer().getPlayerList().getIPBans().add(var4);
        List var5 = MinecraftServer.getServer().getPlayerList().j(par2Str);
        String[] var6 = new String[var5.size()];
        int var7 = 0;
        EntityPlayer var9;

        for (Iterator var8 = var5.iterator(); var8.hasNext(); var6[var7++] = var9.getLocalizedName())
        {
            var9 = (EntityPlayer)var8.next();
            var9.playerConnection.disconnect("You have been IP banned.");
        }

        if (var5.isEmpty())
        {
            a(par1ICommandSender, "commands.banip.success", new Object[]{par2Str});
        }
        else
        {
            a(par1ICommandSender, "commands.banip.success.players", new Object[]{par2Str, a(var6)});
        }
    }
}
