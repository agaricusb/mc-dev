package net.minecraft.server;

public class CommandSaveAll extends CommandAbstract
{
    public String c()
    {
        return "save-all";
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
        par1ICommandSender.sendMessage(par1ICommandSender.a("commands.save.start", new Object[0]));

        if (var3.getServerConfigurationManager() != null)
        {
            var3.getServerConfigurationManager().savePlayers();
        }

        try
        {
            for (int var4 = 0; var4 < var3.worldServer.length; ++var4)
            {
                if (var3.worldServer[var4] != null)
                {
                    WorldServer var5 = var3.worldServer[var4];
                    boolean var6 = var5.savingDisabled;
                    var5.savingDisabled = false;
                    var5.save(true, (IProgressUpdate) null);
                    var5.savingDisabled = var6;
                }
            }
        }
        catch (ExceptionWorldConflict var7)
        {
            a(par1ICommandSender, "commands.save.failed", new Object[]{var7.getMessage()});
            return;
        }

        a(par1ICommandSender, "commands.save.success", new Object[0]);
    }
}
