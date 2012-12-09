package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class CommandHandler implements ICommandHandler
{
    /** Map of Strings to the ICommand objects they represent */
    private final Map a = new HashMap();

    /** The set of ICommand objects currently loaded. */
    private final Set b = new HashSet();

    public void a(ICommandListener par1ICommandSender, String par2Str)
    {
        if (par2Str.startsWith("/"))
        {
            par2Str = par2Str.substring(1);
        }

        String[] var3 = par2Str.split(" ");
        String var4 = var3[0];
        var3 = a(var3);
        ICommand var5 = (ICommand)this.a.get(var4);
        int var6 = this.a(var5, var3);

        try
        {
            if (var5 == null)
            {
                throw new ExceptionUnknownCommand();
            }

            if (var5.b(par1ICommandSender))
            {
                if (var6 > -1)
                {
                    EntityPlayer[] var7 = PlayerSelector.getPlayers(par1ICommandSender, var3[var6]);
                    String var8 = var3[var6];
                    EntityPlayer[] var9 = var7;
                    int var10 = var7.length;

                    for (int var11 = 0; var11 < var10; ++var11)
                    {
                        EntityPlayer var12 = var9[var11];
                        var3[var6] = var12.getLocalizedName();

                        try
                        {
                            var5.b(par1ICommandSender, var3);
                        }
                        catch (ExceptionPlayerNotFound var14)
                        {
                            par1ICommandSender.sendMessage("\u00a7c" + par1ICommandSender.a(var14.getMessage(), var14.a()));
                        }
                    }

                    var3[var6] = var8;
                }
                else
                {
                    var5.b(par1ICommandSender, var3);
                }
            }
            else
            {
                par1ICommandSender.sendMessage("\u00a7cYou do not have permission to use this command.");
            }
        }
        catch (ExceptionUsage var15)
        {
            par1ICommandSender.sendMessage("\u00a7c" + par1ICommandSender.a("commands.generic.usage", new Object[]{par1ICommandSender.a(var15.getMessage(), var15.a())}));
        }
        catch (CommandException var16)
        {
            par1ICommandSender.sendMessage("\u00a7c" + par1ICommandSender.a(var16.getMessage(), var16.a()));
        }
        catch (Throwable var17)
        {
            par1ICommandSender.sendMessage("\u00a7c" + par1ICommandSender.a("commands.generic.exception", new Object[0]));
            var17.printStackTrace();
        }
    }

    /**
     * adds the command and any aliases it has to the internal map of available commands
     */
    public ICommand a(ICommand par1ICommand)
    {
        List var2 = par1ICommand.b();
        this.a.put(par1ICommand.c(), par1ICommand);
        this.b.add(par1ICommand);

        if (var2 != null)
        {
            Iterator var3 = var2.iterator();

            while (var3.hasNext())
            {
                String var4 = (String)var3.next();
                ICommand var5 = (ICommand)this.a.get(var4);

                if (var5 == null || !var5.c().equals(var4))
                {
                    this.a.put(var4, par1ICommand);
                }
            }
        }

        return par1ICommand;
    }

    /**
     * creates a new array and sets elements 0..n-2 to be 0..n-1 of the input (n elements)
     */
    private static String[] a(String[] par0ArrayOfStr)
    {
        String[] var1 = new String[par0ArrayOfStr.length - 1];

        for (int var2 = 1; var2 < par0ArrayOfStr.length; ++var2)
        {
            var1[var2 - 1] = par0ArrayOfStr[var2];
        }

        return var1;
    }

    /**
     * Performs a "begins with" string match on each token in par2. Only returns commands that par1 can use.
     */
    public List b(ICommandListener par1ICommandSender, String par2Str)
    {
        String[] var3 = par2Str.split(" ", -1);
        String var4 = var3[0];

        if (var3.length == 1)
        {
            ArrayList var8 = new ArrayList();
            Iterator var6 = this.a.entrySet().iterator();

            while (var6.hasNext())
            {
                Entry var7 = (Entry)var6.next();

                if (CommandAbstract.a(var4, (String) var7.getKey()) && ((ICommand)var7.getValue()).b(par1ICommandSender))
                {
                    var8.add(var7.getKey());
                }
            }

            return var8;
        }
        else
        {
            if (var3.length > 1)
            {
                ICommand var5 = (ICommand)this.a.get(var4);

                if (var5 != null)
                {
                    return var5.a(par1ICommandSender, a(var3));
                }
            }

            return null;
        }
    }

    /**
     * returns all commands that the commandSender can use
     */
    public List a(ICommandListener par1ICommandSender)
    {
        ArrayList var2 = new ArrayList();
        Iterator var3 = this.b.iterator();

        while (var3.hasNext())
        {
            ICommand var4 = (ICommand)var3.next();

            if (var4.b(par1ICommandSender))
            {
                var2.add(var4);
            }
        }

        return var2;
    }

    /**
     * returns a map of string to commads. All commands are returned, not just ones which someone has permission to use.
     */
    public Map a()
    {
        return this.a;
    }

    /**
     * Return a command's first parameter index containing a valid username.
     */
    private int a(ICommand par1ICommand, String[] par2ArrayOfStr)
    {
        if (par1ICommand == null)
        {
            return -1;
        }
        else
        {
            for (int var3 = 0; var3 < par2ArrayOfStr.length; ++var3)
            {
                if (par1ICommand.a(var3) && PlayerSelector.isList(par2ArrayOfStr[var3]))
                {
                    return var3;
                }
            }

            return -1;
        }
    }
}
