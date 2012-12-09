package net.minecraft.server;

import java.util.List;

public class CommandGamerule extends CommandAbstract
{
    public String c()
    {
        return "gamerule";
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
        return par1ICommandSender.a("commands.gamerule.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        String var6;

        if (par2ArrayOfStr.length == 2)
        {
            var6 = par2ArrayOfStr[0];
            String var7 = par2ArrayOfStr[1];
            GameRules var8 = this.d();

            if (var8.e(var6))
            {
                var8.set(var6, var7);
                a(par1ICommandSender, "commands.gamerule.success", new Object[0]);
            }
            else
            {
                a(par1ICommandSender, "commands.gamerule.norule", new Object[]{var6});
            }
        }
        else if (par2ArrayOfStr.length == 1)
        {
            var6 = par2ArrayOfStr[0];
            GameRules var4 = this.d();

            if (var4.e(var6))
            {
                String var5 = var4.get(var6);
                par1ICommandSender.sendMessage(var6 + " = " + var5);
            }
            else
            {
                a(par1ICommandSender, "commands.gamerule.norule", new Object[]{var6});
            }
        }
        else if (par2ArrayOfStr.length == 0)
        {
            GameRules var3 = this.d();
            par1ICommandSender.sendMessage(a(var3.b()));
        }
        else
        {
            throw new ExceptionUsage("commands.gamerule.usage", new Object[0]);
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? a(par2ArrayOfStr, this.d().b()) : (par2ArrayOfStr.length == 2 ? a(par2ArrayOfStr, new String[]{"true", "false"}): null);
    }

    /**
     * Return the game rule set this command should be able to manipulate.
     */
    private GameRules d()
    {
        return MinecraftServer.getServer().getWorldServer(0).getGameRules();
    }
}
