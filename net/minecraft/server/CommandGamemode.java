package net.minecraft.server;

import java.util.List;

public class CommandGamemode extends CommandAbstract
{
    public String c()
    {
        return "gamemode";
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
        return par1ICommandSender.a("commands.gamemode.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length > 0)
        {
            EnumGamemode var3 = this.d(par1ICommandSender, par2ArrayOfStr[0]);
            EntityPlayer var4 = par2ArrayOfStr.length >= 2 ? c(par1ICommandSender, par2ArrayOfStr[1]) : c(par1ICommandSender);
            var4.a(var3);
            var4.fallDistance = 0.0F;
            String var5 = LocaleI18n.get("gameMode." + var3.b());

            if (var4 != par1ICommandSender)
            {
                a(par1ICommandSender, 1, "commands.gamemode.success.other", new Object[]{var4.getLocalizedName(), var5});
            }
            else
            {
                a(par1ICommandSender, 1, "commands.gamemode.success.self", new Object[]{var5});
            }
        }
        else
        {
            throw new ExceptionUsage("commands.gamemode.usage", new Object[0]);
        }
    }

    /**
     * Gets the Game Mode specified in the command.
     */
    protected EnumGamemode d(ICommandListener par1ICommandSender, String par2Str)
    {
        return !par2Str.equalsIgnoreCase(EnumGamemode.SURVIVAL.b()) && !par2Str.equalsIgnoreCase("s") ? (!par2Str.equalsIgnoreCase(EnumGamemode.CREATIVE.b()) && !par2Str.equalsIgnoreCase("c") ? (!par2Str.equalsIgnoreCase(EnumGamemode.ADVENTURE.b()) && !par2Str.equalsIgnoreCase("a") ? WorldSettings.a(a(par1ICommandSender, par2Str, 0, EnumGamemode.values().length - 2)) : EnumGamemode.ADVENTURE) : EnumGamemode.CREATIVE) : EnumGamemode.SURVIVAL;
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? a(par2ArrayOfStr, new String[]{"survival", "creative", "adventure"}): (par2ArrayOfStr.length == 2 ? a(par2ArrayOfStr, this.d()) : null);
    }

    /**
     * Returns String array containing all player usernames in the server.
     */
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
