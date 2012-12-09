package net.minecraft.server;

public class CommandList extends CommandAbstract
{
    public String c()
    {
        return "list";
    }

    /**
     * Return the required permission level for this command.
     */
    public int a()
    {
        return 0;
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        par1ICommandSender.sendMessage(par1ICommandSender.a("commands.players.list", new Object[]{Integer.valueOf(MinecraftServer.getServer().y()), Integer.valueOf(MinecraftServer.getServer().z())}));
        par1ICommandSender.sendMessage(MinecraftServer.getServer().getServerConfigurationManager().c());
    }
}
