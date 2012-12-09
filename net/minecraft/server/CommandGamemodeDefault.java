package net.minecraft.server;

public class CommandGamemodeDefault extends CommandGamemode
{
    public String c()
    {
        return "defaultgamemode";
    }

    public String a(ICommandListener par1ICommandSender)
    {
        return par1ICommandSender.a("commands.defaultgamemode.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length > 0)
        {
            EnumGamemode var3 = this.d(par1ICommandSender, par2ArrayOfStr[0]);
            this.a(var3);
            String var4 = LocaleI18n.get("gameMode." + var3.b());
            a(par1ICommandSender, "commands.defaultgamemode.success", new Object[]{var4});
        }
        else
        {
            throw new ExceptionUsage("commands.defaultgamemode.usage", new Object[0]);
        }
    }

    protected void a(EnumGamemode par1EnumGameType)
    {
        MinecraftServer.getServer().a(par1EnumGameType);
    }
}
