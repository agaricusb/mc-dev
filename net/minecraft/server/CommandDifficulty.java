package net.minecraft.server;

import java.util.List;

public class CommandDifficulty extends CommandAbstract
{
    private static final String[] a = new String[] {"options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard"};

    public String c()
    {
        return "difficulty";
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
        return par1ICommandSender.a("commands.difficulty.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length > 0)
        {
            int var3 = this.d(par1ICommandSender, par2ArrayOfStr[0]);
            MinecraftServer.getServer().c(var3);
            String var4 = LocaleI18n.get(a[var3]);
            a(par1ICommandSender, 1, "commands.difficulty.success", new Object[]{var4});
        }
        else
        {
            throw new ExceptionUsage("commands.difficulty.usage", new Object[0]);
        }
    }

    /**
     * Return the difficulty value for the specified string.
     */
    protected int d(ICommandListener par1ICommandSender, String par2Str)
    {
        return !par2Str.equalsIgnoreCase("peaceful") && !par2Str.equalsIgnoreCase("p") ? (!par2Str.equalsIgnoreCase("easy") && !par2Str.equalsIgnoreCase("e") ? (!par2Str.equalsIgnoreCase("normal") && !par2Str.equalsIgnoreCase("n") ? (!par2Str.equalsIgnoreCase("hard") && !par2Str.equalsIgnoreCase("h") ? a(par1ICommandSender, par2Str, 0, 3) : 3) : 2) : 1) : 0;
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? a(par2ArrayOfStr, new String[]{"peaceful", "easy", "normal", "hard"}): null;
    }
}
