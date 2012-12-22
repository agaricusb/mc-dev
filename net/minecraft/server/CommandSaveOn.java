package net.minecraft.server;


public class CommandSaveOn extends CommandAbstract
{
    public String c()
    {
        return "save-on";
    }

    /**
     * Return the required permission level for this command.
     */
    public int a()
    {
        return 4;
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        MinecraftServer var3 = MinecraftServer.getServer();

        for (int var4 = 0; var4 < var3.worldServer.length; ++var4)
        {
            if (var3.worldServer[var4] != null)
            {
                WorldServer var5 = var3.worldServer[var4];
                var5.savingDisabled = false;
            }
        }

        a(par1ICommandSender, "commands.save.enabled", new Object[0]);
    }
}
