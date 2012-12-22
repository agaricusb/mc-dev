package net.minecraft.server;


public class CommandSeed extends CommandAbstract
{
    /**
     * Returns true if the given command sender is allowed to use this command.
     */
    public boolean b(ICommandListener par1ICommandSender)
    {
        return MinecraftServer.getServer().I() || super.b(par1ICommandSender);
    }

    public String c()
    {
        return "seed";
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
        Object var3 = par1ICommandSender instanceof EntityHuman ? ((EntityHuman)par1ICommandSender).world : MinecraftServer.getServer().getWorldServer(0);
        par1ICommandSender.sendMessage("Seed: " + ((World) var3).getSeed());
    }
}
