package net.minecraft.server;

public class ExceptionInvalidSyntax extends CommandException
{
    public ExceptionInvalidSyntax()
    {
        this("commands.generic.snytax", new Object[0]);
    }

    public ExceptionInvalidSyntax(String par1Str, Object... par2ArrayOfObj)
    {
        super(par1Str, par2ArrayOfObj);
    }
}
