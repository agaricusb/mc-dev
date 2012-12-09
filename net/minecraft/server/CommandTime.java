package net.minecraft.server;

import java.util.List;

public class CommandTime extends CommandAbstract
{
    public String c()
    {
        return "time";
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
        return par1ICommandSender.a("commands.time.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length > 1)
        {
            int var3;

            if (par2ArrayOfStr[0].equals("set"))
            {
                if (par2ArrayOfStr[1].equals("day"))
                {
                    var3 = 0;
                }
                else if (par2ArrayOfStr[1].equals("night"))
                {
                    var3 = 12500;
                }
                else
                {
                    var3 = a(par1ICommandSender, par2ArrayOfStr[1], 0);
                }

                this.a(par1ICommandSender, var3);
                a(par1ICommandSender, "commands.time.set", new Object[]{Integer.valueOf(var3)});
                return;
            }

            if (par2ArrayOfStr[0].equals("add"))
            {
                var3 = a(par1ICommandSender, par2ArrayOfStr[1], 0);
                this.b(par1ICommandSender, var3);
                a(par1ICommandSender, "commands.time.added", new Object[]{Integer.valueOf(var3)});
                return;
            }
        }

        throw new ExceptionUsage("commands.time.usage", new Object[0]);
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? a(par2ArrayOfStr, new String[]{"set", "add"}): (par2ArrayOfStr.length == 2 && par2ArrayOfStr[0].equals("set") ? a(par2ArrayOfStr, new String[]{"day", "night"}): null);
    }

    /**
     * Set the time in the server object.
     */
    protected void a(ICommandListener par1ICommandSender, int par2)
    {
        for (int var3 = 0; var3 < MinecraftServer.getServer().worldServer.length; ++var3)
        {
            MinecraftServer.getServer().worldServer[var3].setDayTime((long) par2);
        }
    }

    /**
     * Adds (or removes) time in the server object.
     */
    protected void b(ICommandListener par1ICommandSender, int par2)
    {
        for (int var3 = 0; var3 < MinecraftServer.getServer().worldServer.length; ++var3)
        {
            WorldServer var4 = MinecraftServer.getServer().worldServer[var3];
            var4.setDayTime(var4.getDayTime() + (long) par2);
        }
    }
}
