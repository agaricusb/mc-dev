package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

public class CommandOp extends CommandAbstract
{
    public String c()
    {
        return "op";
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
        return par1ICommandSender.a("commands.op.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0)
        {
            MinecraftServer.getServer().getServerConfigurationManager().addOp(par2ArrayOfStr[0]);
            a(par1ICommandSender, "commands.op.success", new Object[]{par2ArrayOfStr[0]});
        }
        else
        {
            throw new ExceptionUsage("commands.op.usage", new Object[0]);
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length == 1)
        {
            String var3 = par2ArrayOfStr[par2ArrayOfStr.length - 1];
            ArrayList var4 = new ArrayList();
            String[] var5 = MinecraftServer.getServer().getPlayers();
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7)
            {
                String var8 = var5[var7];

                if (!MinecraftServer.getServer().getServerConfigurationManager().isOp(var8) && a(var3, var8))
                {
                    var4.add(var8);
                }
            }

            return var4;
        }
        else
        {
            return null;
        }
    }
}
