package net.minecraft.server;

public class CommandKill extends CommandAbstract
{
    public String c()
    {
        return "kill";
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
        EntityPlayer var3 = c(par1ICommandSender);
        var3.d(DamageSource.OUT_OF_WORLD, 1000);
        par1ICommandSender.sendMessage("Ouch. That looks like it hurt.");
    }
}
