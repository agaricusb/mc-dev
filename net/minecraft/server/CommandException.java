package net.minecraft.server;

public class CommandException extends RuntimeException
{
    private Object[] a;

    public CommandException(String par1Str, Object... par2ArrayOfObj)
    {
        super(par1Str);
        this.a = par2ArrayOfObj;
    }

    public Object[] a()
    {
        return this.a;
    }
}
