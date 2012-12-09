package net.minecraft.server;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandDebug extends CommandAbstract
{
    /** Time the debugging started in milliseconds. */
    private long a = 0L;

    /** The number of ticks when debugging started. */
    private int b = 0;

    public String c()
    {
        return "debug";
    }

    /**
     * Return the required permission level for this command.
     */
    public int a()
    {
        return 3;
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length == 1)
        {
            if (par2ArrayOfStr[0].equals("start"))
            {
                a(par1ICommandSender, "commands.debug.start", new Object[0]);
                MinecraftServer.getServer().ai();
                this.a = System.currentTimeMillis();
                this.b = MinecraftServer.getServer().ah();
                return;
            }

            if (par2ArrayOfStr[0].equals("stop"))
            {
                if (!MinecraftServer.getServer().methodProfiler.a)
                {
                    throw new CommandException("commands.debug.notStarted", new Object[0]);
                }

                long var3 = System.currentTimeMillis();
                int var5 = MinecraftServer.getServer().ah();
                long var6 = var3 - this.a;
                int var8 = var5 - this.b;
                this.a(var6, var8);
                MinecraftServer.getServer().methodProfiler.a = false;
                a(par1ICommandSender, "commands.debug.stop", new Object[]{Float.valueOf((float) var6 / 1000.0F), Integer.valueOf(var8)});
                return;
            }
        }

        throw new ExceptionUsage("commands.debug.usage", new Object[0]);
    }

    private void a(long par1, int par3)
    {
        File var4 = new File(MinecraftServer.getServer().e("debug"), "profile-results-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + ".txt");
        var4.getParentFile().mkdirs();

        try
        {
            FileWriter var5 = new FileWriter(var4);
            var5.write(this.b(par1, par3));
            var5.close();
        }
        catch (Throwable var6)
        {
            Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not save profiler results to " + var4, var6);
        }
    }

    private String b(long par1, int par3)
    {
        StringBuilder var4 = new StringBuilder();
        var4.append("---- Minecraft Profiler Results ----\n");
        var4.append("// ");
        var4.append(d());
        var4.append("\n\n");
        var4.append("Time span: ").append(par1).append(" ms\n");
        var4.append("Tick span: ").append(par3).append(" ticks\n");
        var4.append("// This is approximately ").append(String.format("%.2f", new Object[] {Float.valueOf((float)par3 / ((float)par1 / 1000.0F))})).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
        var4.append("--- BEGIN PROFILE DUMP ---\n\n");
        this.a(0, "root", var4);
        var4.append("--- END PROFILE DUMP ---\n\n");
        return var4.toString();
    }

    private void a(int par1, String par2Str, StringBuilder par3StringBuilder)
    {
        List var4 = MinecraftServer.getServer().methodProfiler.b(par2Str);

        if (var4 != null && var4.size() >= 3)
        {
            for (int var5 = 1; var5 < var4.size(); ++var5)
            {
                ProfilerInfo var6 = (ProfilerInfo)var4.get(var5);
                par3StringBuilder.append(String.format("[%02d] ", new Object[] {Integer.valueOf(par1)}));

                for (int var7 = 0; var7 < par1; ++var7)
                {
                    par3StringBuilder.append(" ");
                }

                par3StringBuilder.append(var6.c);
                par3StringBuilder.append(" - ");
                par3StringBuilder.append(String.format("%.2f", new Object[] {Double.valueOf(var6.a)}));
                par3StringBuilder.append("%/");
                par3StringBuilder.append(String.format("%.2f", new Object[] {Double.valueOf(var6.b)}));
                par3StringBuilder.append("%\n");

                if (!var6.c.equals("unspecified"))
                {
                    try
                    {
                        this.a(par1 + 1, par2Str + "." + var6.c, par3StringBuilder);
                    }
                    catch (Exception var8)
                    {
                        par3StringBuilder.append("[[ EXCEPTION " + var8 + " ]]");
                    }
                }
            }
        }
    }

    /**
     * Returns a random "witty" comment.
     */
    private static String d()
    {
        String[] var0 = new String[] {"Shiny numbers!", "Am I not running fast enough? :(", "I\'m working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it\'ll have more motivation to work faster! Poor server."};

        try
        {
            return var0[(int)(System.nanoTime() % (long)var0.length)];
        }
        catch (Throwable var2)
        {
            return "Witty comment unavailable :(";
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? a(par2ArrayOfStr, new String[]{"start", "stop"}): null;
    }
}
