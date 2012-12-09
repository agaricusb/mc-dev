package net.minecraft.server;

public class ExceptionInvalidNumber extends CommandException
{
    public ExceptionInvalidNumber()
    {
        this("commands.generic.num.invalid", new Object[0]);
    }

    public ExceptionInvalidNumber(String par1Str, Object... par2ArrayOfObj)
    {
        super(par1Str, par2ArrayOfObj);
    }
}
