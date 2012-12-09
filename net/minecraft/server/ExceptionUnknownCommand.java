package net.minecraft.server;

public class ExceptionUnknownCommand extends CommandException
{
    public ExceptionUnknownCommand()
    {
        this("commands.generic.notFound", new Object[0]);
    }

    public ExceptionUnknownCommand(String par1Str, Object... par2ArrayOfObj)
    {
        super(par1Str, par2ArrayOfObj);
    }
}
