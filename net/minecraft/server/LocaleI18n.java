package net.minecraft.server;

public class LocaleI18n
{
    private static LocaleLanguage a = LocaleLanguage.a();

    /**
     * Translates a Stat name
     */
    public static String get(String par0Str)
    {
        return a.b(par0Str);
    }

    /**
     * Translates a Stat name with format args
     */
    public static String get(String par0Str, Object... par1ArrayOfObj)
    {
        return a.a(par0Str, par1ArrayOfObj);
    }
}
