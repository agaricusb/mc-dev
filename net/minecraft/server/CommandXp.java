package net.minecraft.server;

import java.util.List;

public class CommandXp extends CommandAbstract
{
    public String c()
    {
        return "xp";
    }

    /**
     * Return the required permission level for this command.
     */
    public int a()
    {
        return 2;
    }

    public String a(ICommandListener par1ICommandSender)
    {
        return par1ICommandSender.a("commands.xp.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length <= 0)
        {
            throw new ExceptionUsage("commands.xp.usage", new Object[0]);
        }
        else
        {
            String var4 = par2ArrayOfStr[0];
            boolean var5 = var4.endsWith("l") || var4.endsWith("L");

            if (var5 && var4.length() > 1)
            {
                var4 = var4.substring(0, var4.length() - 1);
            }

            int var6 = a(par1ICommandSender, var4);
            boolean var7 = var6 < 0;

            if (var7)
            {
                var6 *= -1;
            }

            EntityPlayer var3;

            if (par2ArrayOfStr.length > 1)
            {
                var3 = c(par1ICommandSender, par2ArrayOfStr[1]);
            }
            else
            {
                var3 = c(par1ICommandSender);
            }

            if (var5)
            {
                if (var7)
                {
                    var3.levelDown(-var6);
                    a(par1ICommandSender, "commands.xp.success.negative.levels", new Object[]{Integer.valueOf(var6), var3.getLocalizedName()});
                }
                else
                {
                    var3.levelDown(var6);
                    a(par1ICommandSender, "commands.xp.success.levels", new Object[]{Integer.valueOf(var6), var3.getLocalizedName()});
                }
            }
            else
            {
                if (var7)
                {
                    throw new ExceptionUsage("commands.xp.failure.widthdrawXp", new Object[0]);
                }

                var3.giveExp(var6);
                a(par1ICommandSender, "commands.xp.success", new Object[]{Integer.valueOf(var6), var3.getLocalizedName()});
            }
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 2 ? a(par2ArrayOfStr, this.d()) : null;
    }

    protected String[] d()
    {
        return MinecraftServer.getServer().getPlayers();
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean a(int par1)
    {
        return par1 == 1;
    }
}
