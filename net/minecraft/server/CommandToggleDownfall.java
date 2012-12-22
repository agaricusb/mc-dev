package net.minecraft.server;


public class CommandToggleDownfall extends CommandAbstract
{
    public String c()
    {
        return "toggledownfall";
    }

    /**
     * Return the required permission level for this command.
     */
    public int a()
    {
        return 2;
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        this.d();
        a(par1ICommandSender, "commands.downfall.success", new Object[0]);
    }

    /**
     * Toggle rain and enable thundering.
     */
    protected void d()
    {
        MinecraftServer.getServer().worldServer[0].y();
        MinecraftServer.getServer().worldServer[0].getWorldData().setThundering(true);
    }
}
