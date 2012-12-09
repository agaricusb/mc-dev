package net.minecraft.server;

import java.util.List;

public class CommandGive extends CommandAbstract
{
    public String c()
    {
        return "give";
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
        return par1ICommandSender.a("commands.give.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length >= 2)
        {
            EntityPlayer var3 = c(par1ICommandSender, par2ArrayOfStr[0]);
            int var4 = a(par1ICommandSender, par2ArrayOfStr[1], 1);
            int var5 = 1;
            int var6 = 0;

            if (Item.byId[var4] == null)
            {
                throw new ExceptionInvalidNumber("commands.give.notFound", new Object[] {Integer.valueOf(var4)});
            }
            else
            {
                if (par2ArrayOfStr.length >= 3)
                {
                    var5 = a(par1ICommandSender, par2ArrayOfStr[2], 1, 64);
                }

                if (par2ArrayOfStr.length >= 4)
                {
                    var6 = a(par1ICommandSender, par2ArrayOfStr[3]);
                }

                ItemStack var7 = new ItemStack(var4, var5, var6);
                EntityItem var8 = var3.drop(var7);
                var8.pickupDelay = 0;
                a(par1ICommandSender, "commands.give.success", new Object[]{Item.byId[var4].i(var7), Integer.valueOf(var4), Integer.valueOf(var5), var3.getLocalizedName()});
            }
        }
        else
        {
            throw new ExceptionUsage("commands.give.usage", new Object[0]);
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? a(par2ArrayOfStr, this.d()) : null;
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
        return par1 == 0;
    }
}
