package net.minecraft.server;

import java.util.List;

public class CommandKick extends CommandAbstract
{
    public String c()
    {
        return "kick";
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
        return par1ICommandSender.a("commands.kick.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length > 0 && par2ArrayOfStr[0].length() > 1)
        {
            EntityPlayer var3 = MinecraftServer.getServer().getPlayerList().f(par2ArrayOfStr[0]);
            String var4 = "Kicked by an operator.";
            boolean var5 = false;

            if (var3 == null)
            {
                throw new ExceptionPlayerNotFound();
            }
            else
            {
                if (par2ArrayOfStr.length >= 2)
                {
                    var4 = a(par1ICommandSender, par2ArrayOfStr, 1);
                    var5 = true;
                }

                var3.playerConnection.disconnect(var4);

                if (var5)
                {
                    a(par1ICommandSender, "commands.kick.success.reason", new Object[]{var3.getLocalizedName(), var4});
                }
                else
                {
                    a(par1ICommandSender, "commands.kick.success", new Object[]{var3.getLocalizedName()});
                }
            }
        }
        else
        {
            throw new ExceptionUsage("commands.kick.usage", new Object[0]);
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
