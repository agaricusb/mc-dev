package net.minecraft.server;

class GameRuleValue
{
    private String a;
    private boolean b;
    private int c;
    private double d;

    public GameRuleValue(String par1Str)
    {
        this.a(par1Str);
    }

    /**
     * Set this game rule value.
     */
    public void a(String par1Str)
    {
        this.a = par1Str;
        this.b = Boolean.parseBoolean(par1Str);

        try
        {
            this.c = Integer.parseInt(par1Str);
        }
        catch (NumberFormatException var4)
        {
            ;
        }

        try
        {
            this.d = Double.parseDouble(par1Str);
        }
        catch (NumberFormatException var3)
        {
            ;
        }
    }

    /**
     * Gets the GameRule's value as String.
     */
    public String a()
    {
        return this.a;
    }

    /**
     * Gets the GameRule's value as boolean.
     */
    public boolean b()
    {
        return this.b;
    }
}
