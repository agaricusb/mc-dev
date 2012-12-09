package net.minecraft.server;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CommandHelp extends CommandAbstract
{
    public String c()
    {
        return "help";
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
        return par1ICommandSender.a("commands.help.usage", new Object[0]);
    }

    public List b()
    {
        return Arrays.asList(new String[] {"?"});
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        List var3 = this.d(par1ICommandSender);
        byte var4 = 7;
        int var5 = (var3.size() - 1) / var4;
        boolean var6 = false;
        ICommand var9;
        int var11;

        try
        {
            var11 = par2ArrayOfStr.length == 0 ? 0 : a(par1ICommandSender, par2ArrayOfStr[0], 1, var5 + 1) - 1;
        }
        catch (ExceptionInvalidNumber var10)
        {
            Map var8 = this.d();
            var9 = (ICommand)var8.get(par2ArrayOfStr[0]);

            if (var9 != null)
            {
                throw new ExceptionUsage(var9.a(par1ICommandSender), new Object[0]);
            }

            throw new ExceptionUnknownCommand();
        }

        int var7 = Math.min((var11 + 1) * var4, var3.size());
        par1ICommandSender.sendMessage("\u00a72" + par1ICommandSender.a("commands.help.header", new Object[]{Integer.valueOf(var11 + 1), Integer.valueOf(var5 + 1)}));

        for (int var12 = var11 * var4; var12 < var7; ++var12)
        {
            var9 = (ICommand)var3.get(var12);
            par1ICommandSender.sendMessage(var9.a(par1ICommandSender));
        }

        if (var11 == 0 && par1ICommandSender instanceof EntityHuman)
        {
            par1ICommandSender.sendMessage("\u00a7a" + par1ICommandSender.a("commands.help.footer", new Object[0]));
        }
    }

    /**
     * Returns a sorted list of all possible commands for the given ICommandSender.
     */
    protected List d(ICommandListener par1ICommandSender)
    {
        List var2 = MinecraftServer.getServer().getCommandHandler().a(par1ICommandSender);
        Collections.sort(var2);
        return var2;
    }

    protected Map d()
    {
        return MinecraftServer.getServer().getCommandHandler().a();
    }
}
