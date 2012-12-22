package net.minecraft.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PotionBrewer
{
    public static final String a = null;
    public static final String b;
    public static final String c = "+0-1-2-3&4-4+13";
    public static final String d;
    public static final String e;
    public static final String f;
    public static final String g;
    public static final String h;
    public static final String i;
    public static final String j;
    public static final String k;
    public static final String l;
    private static final HashMap effectDurations = new HashMap();

    /** Potion effect amplifier map */
    private static final HashMap effectAmplifiers = new HashMap();
    private static final HashMap o;

    /** An array of possible potion prefix names, as translation IDs. */
    private static final String[] appearances;

    /**
     * Checks if the bit at 1 << j is on in i.
     */
    public static boolean a(int par0, int par1)
    {
        return (par0 & 1 << par1) != 0;
    }

    /**
     * Returns 1 if the flag is set, 0 if it is not set.
     */
    private static int c(int par0, int par1)
    {
        return a(par0, par1) ? 1 : 0;
    }

    /**
     * Returns 0 if the flag is set, 1 if it is not set.
     */
    private static int d(int par0, int par1)
    {
        return a(par0, par1) ? 0 : 1;
    }

    public static int a(int par0)
    {
        return a(par0, 5, 4, 3, 2, 1);
    }

    /**
     * Given a {@link Collection}<{@link MobEffect}> will return an Integer color.
     */
    public static int a(Collection par0Collection)
    {
        int var1 = 3694022;

        if (par0Collection != null && !par0Collection.isEmpty())
        {
            float var2 = 0.0F;
            float var3 = 0.0F;
            float var4 = 0.0F;
            float var5 = 0.0F;
            Iterator var6 = par0Collection.iterator();

            while (var6.hasNext())
            {
                MobEffect var7 = (MobEffect)var6.next();
                int var8 = MobEffectList.byId[var7.getEffectId()].j();

                for (int var9 = 0; var9 <= var7.getAmplifier(); ++var9)
                {
                    var2 += (float)(var8 >> 16 & 255) / 255.0F;
                    var3 += (float)(var8 >> 8 & 255) / 255.0F;
                    var4 += (float)(var8 >> 0 & 255) / 255.0F;
                    ++var5;
                }
            }

            var2 = var2 / var5 * 255.0F;
            var3 = var3 / var5 * 255.0F;
            var4 = var4 / var5 * 255.0F;
            return (int)var2 << 16 | (int)var3 << 8 | (int)var4;
        }
        else
        {
            return var1;
        }
    }

    public static boolean b(Collection par0Collection)
    {
        Iterator var1 = par0Collection.iterator();
        MobEffect var2;

        do
        {
            if (!var1.hasNext())
            {
                return true;
            }

            var2 = (MobEffect)var1.next();
        }
        while (var2.isAmbient());

        return false;
    }

    public static String c(int par0)
    {
        int var1 = a(par0);
        return appearances[var1];
    }

    private static int a(boolean par0, boolean par1, boolean par2, int par3, int par4, int par5, int par6)
    {
        int var7 = 0;

        if (par0)
        {
            var7 = d(par6, par4);
        }
        else if (par3 != -1)
        {
            if (par3 == 0 && h(par6) == par4)
            {
                var7 = 1;
            }
            else if (par3 == 1 && h(par6) > par4)
            {
                var7 = 1;
            }
            else if (par3 == 2 && h(par6) < par4)
            {
                var7 = 1;
            }
        }
        else
        {
            var7 = c(par6, par4);
        }

        if (par1)
        {
            var7 *= par5;
        }

        if (par2)
        {
            var7 *= -1;
        }

        return var7;
    }

    /**
     * Returns the number of 1 bits in the given integer.
     */
    private static int h(int par0)
    {
        int var1;

        for (var1 = 0; par0 > 0; ++var1)
        {
            par0 &= par0 - 1;
        }

        return var1;
    }

    private static int a(String par0Str, int par1, int par2, int par3)
    {
        if (par1 < par0Str.length() && par2 >= 0 && par1 < par2)
        {
            int var4 = par0Str.indexOf(124, par1);
            int var5;
            int var17;

            if (var4 >= 0 && var4 < par2)
            {
                var5 = a(par0Str, par1, var4 - 1, par3);

                if (var5 > 0)
                {
                    return var5;
                }
                else
                {
                    var17 = a(par0Str, var4 + 1, par2, par3);
                    return var17 > 0 ? var17 : 0;
                }
            }
            else
            {
                var5 = par0Str.indexOf(38, par1);

                if (var5 >= 0 && var5 < par2)
                {
                    var17 = a(par0Str, par1, var5 - 1, par3);

                    if (var17 <= 0)
                    {
                        return 0;
                    }
                    else
                    {
                        int var18 = a(par0Str, var5 + 1, par2, par3);
                        return var18 <= 0 ? 0 : (var17 > var18 ? var17 : var18);
                    }
                }
                else
                {
                    boolean var6 = false;
                    boolean var7 = false;
                    boolean var8 = false;
                    boolean var9 = false;
                    boolean var10 = false;
                    byte var11 = -1;
                    int var12 = 0;
                    int var13 = 0;
                    int var14 = 0;

                    for (int var15 = par1; var15 < par2; ++var15)
                    {
                        char var16 = par0Str.charAt(var15);

                        if (var16 >= 48 && var16 <= 57)
                        {
                            if (var6)
                            {
                                var13 = var16 - 48;
                                var7 = true;
                            }
                            else
                            {
                                var12 *= 10;
                                var12 += var16 - 48;
                                var8 = true;
                            }
                        }
                        else if (var16 == 42)
                        {
                            var6 = true;
                        }
                        else if (var16 == 33)
                        {
                            if (var8)
                            {
                                var14 += a(var9, var7, var10, var11, var12, var13, par3);
                                var9 = false;
                                var10 = false;
                                var6 = false;
                                var7 = false;
                                var8 = false;
                                var13 = 0;
                                var12 = 0;
                                var11 = -1;
                            }

                            var9 = true;
                        }
                        else if (var16 == 45)
                        {
                            if (var8)
                            {
                                var14 += a(var9, var7, var10, var11, var12, var13, par3);
                                var9 = false;
                                var10 = false;
                                var6 = false;
                                var7 = false;
                                var8 = false;
                                var13 = 0;
                                var12 = 0;
                                var11 = -1;
                            }

                            var10 = true;
                        }
                        else if (var16 != 61 && var16 != 60 && var16 != 62)
                        {
                            if (var16 == 43 && var8)
                            {
                                var14 += a(var9, var7, var10, var11, var12, var13, par3);
                                var9 = false;
                                var10 = false;
                                var6 = false;
                                var7 = false;
                                var8 = false;
                                var13 = 0;
                                var12 = 0;
                                var11 = -1;
                            }
                        }
                        else
                        {
                            if (var8)
                            {
                                var14 += a(var9, var7, var10, var11, var12, var13, par3);
                                var9 = false;
                                var10 = false;
                                var6 = false;
                                var7 = false;
                                var8 = false;
                                var13 = 0;
                                var12 = 0;
                                var11 = -1;
                            }

                            if (var16 == 61)
                            {
                                var11 = 0;
                            }
                            else if (var16 == 60)
                            {
                                var11 = 2;
                            }
                            else if (var16 == 62)
                            {
                                var11 = 1;
                            }
                        }
                    }

                    if (var8)
                    {
                        var14 += a(var9, var7, var10, var11, var12, var13, par3);
                    }

                    return var14;
                }
            }
        }
        else
        {
            return 0;
        }
    }

    /**
     * Returns a list of effects for the specified potion damage value.
     */
    public static List getEffects(int par0, boolean par1)
    {
        ArrayList var2 = null;
        MobEffectList[] var3 = MobEffectList.byId;
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            MobEffectList var6 = var3[var5];

            if (var6 != null && (!var6.i() || par1))
            {
                String var7 = (String) effectDurations.get(Integer.valueOf(var6.getId()));

                if (var7 != null)
                {
                    int var8 = a(var7, 0, var7.length(), par0);

                    if (var8 > 0)
                    {
                        int var9 = 0;
                        String var10 = (String) effectAmplifiers.get(Integer.valueOf(var6.getId()));

                        if (var10 != null)
                        {
                            var9 = a(var10, 0, var10.length(), par0);

                            if (var9 < 0)
                            {
                                var9 = 0;
                            }
                        }

                        if (var6.isInstant())
                        {
                            var8 = 1;
                        }
                        else
                        {
                            var8 = 1200 * (var8 * 3 + (var8 - 1) * 2);
                            var8 >>= var9;
                            var8 = (int)Math.round((double)var8 * var6.getDurationModifier());

                            if ((par0 & 16384) != 0)
                            {
                                var8 = (int)Math.round((double)var8 * 0.75D + 0.5D);
                            }
                        }

                        if (var2 == null)
                        {
                            var2 = new ArrayList();
                        }

                        MobEffect var11 = new MobEffect(var6.getId(), var8, var9);

                        if ((par0 & 16384) != 0)
                        {
                            var11.setSplash(true);
                        }

                        var2.add(var11);
                    }
                }
            }
        }

        return var2;
    }

    /**
     * Manipulates the specified bit of the potion damage value according to the rules passed from applyIngredient.
     */
    private static int a(int par0, int par1, boolean par2, boolean par3, boolean par4)
    {
        if (par4)
        {
            if (!a(par0, par1))
            {
                return 0;
            }
        }
        else if (par2)
        {
            par0 &= ~(1 << par1);
        }
        else if (par3)
        {
            if ((par0 & 1 << par1) == 0)
            {
                par0 |= 1 << par1;
            }
            else
            {
                par0 &= ~(1 << par1);
            }
        }
        else
        {
            par0 |= 1 << par1;
        }

        return par0;
    }

    /**
     * Returns the new potion damage value after the specified ingredient info is applied to the specified potion.
     */
    public static int a(int par0, String par1Str)
    {
        byte var2 = 0;
        int var3 = par1Str.length();
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        boolean var7 = false;
        int var8 = 0;

        for (int var9 = var2; var9 < var3; ++var9)
        {
            char var10 = par1Str.charAt(var9);

            if (var10 >= 48 && var10 <= 57)
            {
                var8 *= 10;
                var8 += var10 - 48;
                var4 = true;
            }
            else if (var10 == 33)
            {
                if (var4)
                {
                    par0 = a(par0, var8, var6, var5, var7);
                    var7 = false;
                    var5 = false;
                    var6 = false;
                    var4 = false;
                    var8 = 0;
                }

                var5 = true;
            }
            else if (var10 == 45)
            {
                if (var4)
                {
                    par0 = a(par0, var8, var6, var5, var7);
                    var7 = false;
                    var5 = false;
                    var6 = false;
                    var4 = false;
                    var8 = 0;
                }

                var6 = true;
            }
            else if (var10 == 43)
            {
                if (var4)
                {
                    par0 = a(par0, var8, var6, var5, var7);
                    var7 = false;
                    var5 = false;
                    var6 = false;
                    var4 = false;
                    var8 = 0;
                }
            }
            else if (var10 == 38)
            {
                if (var4)
                {
                    par0 = a(par0, var8, var6, var5, var7);
                    var7 = false;
                    var5 = false;
                    var6 = false;
                    var4 = false;
                    var8 = 0;
                }

                var7 = true;
            }
        }

        if (var4)
        {
            par0 = a(par0, var8, var6, var5, var7);
        }

        return par0 & 32767;
    }

    public static int a(int par0, int par1, int par2, int par3, int par4, int par5)
    {
        return (a(par0, par1) ? 16 : 0) | (a(par0, par2) ? 8 : 0) | (a(par0, par3) ? 4 : 0) | (a(par0, par4) ? 2 : 0) | (a(par0, par5) ? 1 : 0);
    }

    static
    {
        effectDurations.put(Integer.valueOf(MobEffectList.REGENERATION.getId()), "0 & !1 & !2 & !3 & 0+6");
        b = "-0+1-2-3&4-4+13";
        effectDurations.put(Integer.valueOf(MobEffectList.FASTER_MOVEMENT.getId()), "!0 & 1 & !2 & !3 & 1+6");
        h = "+0+1-2-3&4-4+13";
        effectDurations.put(Integer.valueOf(MobEffectList.FIRE_RESISTANCE.getId()), "0 & 1 & !2 & !3 & 0+6");
        f = "+0-1+2-3&4-4+13";
        effectDurations.put(Integer.valueOf(MobEffectList.HEAL.getId()), "0 & !1 & 2 & !3");
        d = "-0-1+2-3&4-4+13";
        effectDurations.put(Integer.valueOf(MobEffectList.POISON.getId()), "!0 & !1 & 2 & !3 & 2+6");
        e = "-0+3-4+13";
        effectDurations.put(Integer.valueOf(MobEffectList.WEAKNESS.getId()), "!0 & !1 & !2 & 3 & 3+6");
        effectDurations.put(Integer.valueOf(MobEffectList.HARM.getId()), "!0 & !1 & 2 & 3");
        effectDurations.put(Integer.valueOf(MobEffectList.SLOWER_MOVEMENT.getId()), "!0 & 1 & !2 & 3 & 3+6");
        g = "+0-1-2+3&4-4+13";
        effectDurations.put(Integer.valueOf(MobEffectList.INCREASE_DAMAGE.getId()), "0 & !1 & !2 & 3 & 3+6");
        l = "-0+1+2-3+13&4-4";
        effectDurations.put(Integer.valueOf(MobEffectList.NIGHT_VISION.getId()), "!0 & 1 & 2 & !3 & 2+6");
        effectDurations.put(Integer.valueOf(MobEffectList.INVISIBILITY.getId()), "!0 & 1 & 2 & 3 & 2+6");
        j = "+5-6-7";
        effectAmplifiers.put(Integer.valueOf(MobEffectList.FASTER_MOVEMENT.getId()), "5");
        effectAmplifiers.put(Integer.valueOf(MobEffectList.FASTER_DIG.getId()), "5");
        effectAmplifiers.put(Integer.valueOf(MobEffectList.INCREASE_DAMAGE.getId()), "5");
        effectAmplifiers.put(Integer.valueOf(MobEffectList.REGENERATION.getId()), "5");
        effectAmplifiers.put(Integer.valueOf(MobEffectList.HARM.getId()), "5");
        effectAmplifiers.put(Integer.valueOf(MobEffectList.HEAL.getId()), "5");
        effectAmplifiers.put(Integer.valueOf(MobEffectList.RESISTANCE.getId()), "5");
        effectAmplifiers.put(Integer.valueOf(MobEffectList.POISON.getId()), "5");
        i = "-5+6-7";
        k = "+14&13-13";
        o = new HashMap();
        appearances = new String[] {"potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", "potion.prefix.gross", "potion.prefix.stinky"};
    }
}
