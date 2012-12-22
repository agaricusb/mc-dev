package net.minecraft.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerSelector
{
    /**
     * This matches the at-tokens introduced for command blocks, including their arguments, if any.
     */
    private static final Pattern a = Pattern.compile("^@([parf])(?:\\[([\\w=,-]*)\\])?$");

    /**
     * This matches things like "-1,,4", and is used for getting x,y,z,range from the token's argument list.
     */
    private static final Pattern b = Pattern.compile("\\G(-?\\w*)(?:$|,)");

    /**
     * This matches things like "rm=4,c=2" and is used for handling named token arguments.
     */
    private static final Pattern c = Pattern.compile("\\G(\\w{1,2})=(-?\\w+)(?:$|,)");

    /**
     * Returns the one player that matches the given at-token.  Returns null if more than one player matches.
     */
    public static EntityPlayer getPlayer(ICommandListener par0ICommandSender, String par1Str)
    {
        EntityPlayer[] var2 = getPlayers(par0ICommandSender, par1Str);
        return var2 != null && var2.length == 1 ? var2[0] : null;
    }

    /**
     * Returns a nicely-formatted string listing the matching players.
     */
    public static String getPlayerNames(ICommandListener par0ICommandSender, String par1Str)
    {
        EntityPlayer[] var2 = getPlayers(par0ICommandSender, par1Str);

        if (var2 != null && var2.length != 0)
        {
            String[] var3 = new String[var2.length];

            for (int var4 = 0; var4 < var3.length; ++var4)
            {
                var3[var4] = var2[var4].getLocalizedName();
            }

            return CommandAbstract.a(var3);
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns an array of all players matched by the given at-token.
     */
    public static EntityPlayer[] getPlayers(ICommandListener par0ICommandSender, String par1Str)
    {
        Matcher var2 = a.matcher(par1Str);

        if (var2.matches())
        {
            Map var3 = h(var2.group(2));
            String var4 = var2.group(1);
            int var5 = c(var4);
            int var6 = d(var4);
            int var7 = f(var4);
            int var8 = e(var4);
            int var9 = g(var4);
            int var10 = EnumGamemode.NONE.a();
            ChunkCoordinates var11 = par0ICommandSender.b();

            if (var3.containsKey("rm"))
            {
                var5 = MathHelper.a((String) var3.get("rm"), var5);
            }

            if (var3.containsKey("r"))
            {
                var6 = MathHelper.a((String) var3.get("r"), var6);
            }

            if (var3.containsKey("lm"))
            {
                var7 = MathHelper.a((String) var3.get("lm"), var7);
            }

            if (var3.containsKey("l"))
            {
                var8 = MathHelper.a((String) var3.get("l"), var8);
            }

            if (var3.containsKey("x"))
            {
                var11.x = MathHelper.a((String) var3.get("x"), var11.x);
            }

            if (var3.containsKey("y"))
            {
                var11.y = MathHelper.a((String) var3.get("y"), var11.y);
            }

            if (var3.containsKey("z"))
            {
                var11.z = MathHelper.a((String) var3.get("z"), var11.z);
            }

            if (var3.containsKey("m"))
            {
                var10 = MathHelper.a((String) var3.get("m"), var10);
            }

            if (var3.containsKey("c"))
            {
                var9 = MathHelper.a((String) var3.get("c"), var9);
            }

            List var12;

            if (!var4.equals("p") && !var4.equals("a"))
            {
                if (!var4.equals("r"))
                {
                    return null;
                }
                else
                {
                    var12 = MinecraftServer.getServer().getPlayerList().a(var11, var5, var6, 0, var10, var7, var8);
                    Collections.shuffle(var12);
                    var12 = var12.subList(0, Math.min(var9, var12.size()));
                    return var12 != null && !var12.isEmpty() ? (EntityPlayer[])var12.toArray(new EntityPlayer[0]) : new EntityPlayer[0];
                }
            }
            else
            {
                var12 = MinecraftServer.getServer().getPlayerList().a(var11, var5, var6, var9, var10, var7, var8);
                return var12 != null && !var12.isEmpty() ? (EntityPlayer[])var12.toArray(new EntityPlayer[0]) : new EntityPlayer[0];
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns whether the given pattern can match more than one player.
     */
    public static boolean isList(String par0Str)
    {
        Matcher var1 = a.matcher(par0Str);

        if (var1.matches())
        {
            Map var2 = h(var1.group(2));
            String var3 = var1.group(1);
            int var4 = g(var3);

            if (var2.containsKey("c"))
            {
                var4 = MathHelper.a((String) var2.get("c"), var4);
            }

            return var4 != 1;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns whether the given token (parameter 1) has exactly the given arguments (parameter 2).
     */
    public static boolean isPattern(String par0Str, String par1Str)
    {
        Matcher var2 = a.matcher(par0Str);

        if (!var2.matches())
        {
            return false;
        }
        else
        {
            String var3 = var2.group(1);
            return par1Str == null || par1Str.equals(var3);
        }
    }

    /**
     * Returns whether the given token has any arguments set.
     */
    public static boolean isPattern(String par0Str)
    {
        return isPattern(par0Str, (String) null);
    }

    /**
     * Gets the default minimum range (argument rm).
     */
    private static final int c(String par0Str)
    {
        return 0;
    }

    /**
     * Gets the default maximum range (argument r).
     */
    private static final int d(String par0Str)
    {
        return 0;
    }

    /**
     * Gets the default maximum experience level (argument l)
     */
    private static final int e(String par0Str)
    {
        return Integer.MAX_VALUE;
    }

    /**
     * Gets the default minimum experience level (argument lm)
     */
    private static final int f(String par0Str)
    {
        return 0;
    }

    /**
     * Gets the default number of players to return (argument c, 0 for infinite)
     */
    private static final int g(String par0Str)
    {
        return par0Str.equals("a") ? 0 : 1;
    }

    /**
     * Parses the given argument string, turning it into a HashMap&lt;String, String&gt; of name-&gt;value.
     */
    private static Map h(String par0Str)
    {
        HashMap var1 = new HashMap();

        if (par0Str == null)
        {
            return var1;
        }
        else
        {
            Matcher var2 = b.matcher(par0Str);
            int var3 = 0;
            int var4;

            for (var4 = -1; var2.find(); var4 = var2.end())
            {
                String var5 = null;

                switch (var3++)
                {
                    case 0:
                        var5 = "x";
                        break;

                    case 1:
                        var5 = "y";
                        break;

                    case 2:
                        var5 = "z";
                        break;

                    case 3:
                        var5 = "r";
                }

                if (var5 != null && var2.group(1).length() > 0)
                {
                    var1.put(var5, var2.group(1));
                }
            }

            if (var4 < par0Str.length())
            {
                var2 = c.matcher(var4 == -1 ? par0Str : par0Str.substring(var4));

                while (var2.find())
                {
                    var1.put(var2.group(1), var2.group(2));
                }
            }

            return var1;
        }
    }
}
