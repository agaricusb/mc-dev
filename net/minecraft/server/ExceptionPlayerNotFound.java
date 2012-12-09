package net.minecraft.server;

public class ExceptionPlayerNotFound extends CommandException
{
    public ExceptionPlayerNotFound()
    {
        this("commands.generic.player.notFound", new Object[0]);
    }

    public ExceptionPlayerNotFound(String par1Str, Object... par2ArrayOfObj)
    {
        super(par1Str, par2ArrayOfObj);
    }
}
