package net.minecraft.server;

public class ServerCommand
{
    /** The command string. */
    public final String command;
    public final ICommandListener source;

    public ServerCommand(String par1Str, ICommandListener par2ICommandSender)
    {
        this.command = par1Str;
        this.source = par2ICommandSender;
    }
}
