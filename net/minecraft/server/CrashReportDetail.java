package net.minecraft.server;

class CrashReportDetail
{
    private final String a;
    private final String b;

    public CrashReportDetail(String par1Str, Object par2Obj)
    {
        this.a = par1Str;

        if (par2Obj == null)
        {
            this.b = "~~NULL~~";
        }
        else if (par2Obj instanceof Throwable)
        {
            Throwable var3 = (Throwable)par2Obj;
            this.b = "~~ERROR~~ " + var3.getClass().getSimpleName() + ": " + var3.getMessage();
        }
        else
        {
            this.b = par2Obj.toString();
        }
    }

    public String a()
    {
        return this.a;
    }

    public String b()
    {
        return this.b;
    }
}
