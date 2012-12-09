package net.minecraft.server;

import java.util.Arrays;
import java.util.List;

public class CommandTell extends CommandAbstract
{
    public List b()
    {
        return Arrays.asList(new String[] {"w", "msg"});
    }

    public String c()
    {
        return "tell";
    }

    /**
     * Return the required permission level for this command.
     */
    public int a()
    {
        return 0;
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length < 2)
        {
            throw new ExceptionUsage("commands.message.usage", new Object[0]);
        }
        else
        {
            EntityPlayer var3 = c(par1ICommandSender, par2ArrayOfStr[0]);

            if (var3 == null)
            {
                throw new ExceptionPlayerNotFound();
            }
            else if (var3 == par1ICommandSender)
            {
                throw new ExceptionPlayerNotFound("commands.message.sameTarget", new Object[0]);
            }
            else
            {
                String var4 = a(par1ICommandSender, par2ArrayOfStr, 1, !(par1ICommandSender instanceof EntityHuman));
                var3.sendMessage("\u00a77\u00a7o" + var3.a("commands.message.display.incoming", new Object[]{par1ICommandSender.getName(), var4}));
                par1ICommandSender.sendMessage("\u00a77\u00a7o" + par1ICommandSender.a("commands.message.display.outgoing", new Object[]{var3.getName(), var4}));
            }
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return a(par2ArrayOfStr, MinecraftServer.getServer().getPlayers());
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean a(int par1)
    {
        return par1 == 0;
    }
}
