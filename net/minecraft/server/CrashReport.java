package net.minecraft.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrashReport
{
    /** Description of the crash report. */
    private final String a;

    /** The Throwable that is the "cause" for this crash and Crash Report. */
    private final Throwable b;
    private final CrashReportSystemDetails c = new CrashReportSystemDetails(this, "System Details");

    /** Holds the keys and values of all crash report sections. */
    private final List d = new ArrayList();

    /** File of crash report. */
    private File e = null;
    private boolean f = true;
    private StackTraceElement[] g = new StackTraceElement[0];

    public CrashReport(String par1Str, Throwable par2Throwable)
    {
        this.a = par1Str;
        this.b = par2Throwable;
        this.h();
    }

    /**
     * Populates this crash report with initial information about the running server and operating system / java
     * environment
     */
    private void h()
    {
        this.c.a("Minecraft Version", new CrashReportVersion(this));
        this.c.a("Operating System", new CrashReportOperatingSystem(this));
        this.c.a("Java Version", new CrashReportJavaVersion(this));
        this.c.a("Java VM Version", new CrashReportJavaVMVersion(this));
        this.c.a("Memory", new CrashReportMemory(this));
        this.c.a("JVM Flags", new CrashReportJVMFlags(this));
        this.c.a("AABB Pool Size", new CrashReportAABBPoolSize(this));
        this.c.a("Suspicious classes", new CrashReportSuspiciousClasses(this));
        this.c.a("IntCache", new CrashReportIntCacheSize(this));
    }

    /**
     * Returns the description of the Crash Report.
     */
    public String a()
    {
        return this.a;
    }

    /**
     * Returns the Throwable object that is the cause for the crash and Crash Report.
     */
    public Throwable b()
    {
        return this.b;
    }

    /**
     * Gets the various sections of the crash report into the given StringBuilder
     */
    public void a(StringBuilder par1StringBuilder)
    {
        if (this.g != null && this.g.length > 0)
        {
            par1StringBuilder.append("-- Head --\n");
            par1StringBuilder.append("Stacktrace:\n");
            StackTraceElement[] var2 = this.g;
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4)
            {
                StackTraceElement var5 = var2[var4];
                par1StringBuilder.append("\t").append("at ").append(var5.toString());
                par1StringBuilder.append("\n");
            }

            par1StringBuilder.append("\n");
        }

        Iterator var6 = this.d.iterator();

        while (var6.hasNext())
        {
            CrashReportSystemDetails var7 = (CrashReportSystemDetails)var6.next();
            var7.a(par1StringBuilder);
            par1StringBuilder.append("\n\n");
        }

        this.c.a(par1StringBuilder);
    }

    /**
     * Gets the stack trace of the Throwable that caused this crash report, or if that fails, the cause .toString().
     */
    public String d()
    {
        StringWriter var1 = null;
        PrintWriter var2 = null;
        String var3 = this.b.toString();

        try
        {
            var1 = new StringWriter();
            var2 = new PrintWriter(var1);
            this.b.printStackTrace(var2);
            var3 = var1.toString();
        }
        finally
        {
            try
            {
                if (var1 != null)
                {
                    var1.close();
                }

                if (var2 != null)
                {
                    var2.close();
                }
            }
            catch (IOException var10)
            {
                ;
            }
        }

        return var3;
    }

    /**
     * Gets the complete report with headers, stack trace, and different sections as a string.
     */
    public String e()
    {
        StringBuilder var1 = new StringBuilder();
        var1.append("---- Minecraft Crash Report ----\n");
        var1.append("// ");
        var1.append(i());
        var1.append("\n\n");
        var1.append("Time: ");
        var1.append((new SimpleDateFormat()).format(new Date()));
        var1.append("\n");
        var1.append("Description: ");
        var1.append(this.a);
        var1.append("\n\n");
        var1.append(this.d());
        var1.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

        for (int var2 = 0; var2 < 87; ++var2)
        {
            var1.append("-");
        }

        var1.append("\n\n");
        this.a(var1);
        return var1.toString();
    }

    /**
     * Saves the complete crash report to the given File.
     */
    public boolean a(File par1File)
    {
        if (this.e != null)
        {
            return false;
        }
        else
        {
            if (par1File.getParentFile() != null)
            {
                par1File.getParentFile().mkdirs();
            }

            try
            {
                FileWriter var2 = new FileWriter(par1File);
                var2.write(this.e());
                var2.close();
                this.e = par1File;
                return true;
            }
            catch (Throwable var3)
            {
                Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not save crash report to " + par1File, var3);
                return false;
            }
        }
    }

    public CrashReportSystemDetails g()
    {
        return this.c;
    }

    public CrashReportSystemDetails a(String par1Str)
    {
        return this.a(par1Str, 1);
    }

    public CrashReportSystemDetails a(String par1Str, int par2)
    {
        CrashReportSystemDetails var3 = new CrashReportSystemDetails(this, par1Str);

        if (this.f)
        {
            int var4 = var3.a(par2);
            StackTraceElement[] var5 = this.b.getStackTrace();
            StackTraceElement var6 = null;
            StackTraceElement var7 = null;

            if (var5 != null && var5.length - var4 < var5.length)
            {
                var6 = var5[var5.length - var4];

                if (var5.length + 1 - var4 < var5.length)
                {
                    var7 = var5[var5.length + 1 - var4];
                }
            }

            this.f = var3.a(var6, var7);

            if (var4 > 0 && !this.d.isEmpty())
            {
                CrashReportSystemDetails var8 = (CrashReportSystemDetails)this.d.get(this.d.size() - 1);
                var8.b(var4);
            }
            else if (var5 != null && var5.length >= var4)
            {
                this.g = new StackTraceElement[var5.length - var4];
                System.arraycopy(var5, 0, this.g, 0, this.g.length);
            }
            else
            {
                this.f = false;
            }
        }

        this.d.add(var3);
        return var3;
    }

    /**
     * Gets a random witty comment for inclusion in this CrashReport
     */
    private static String i()
    {
        String[] var0 = new String[] {"Who set us up the TNT?", "Everything\'s going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I\'m sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don\'t be sad. I\'ll do better next time, I promise!", "Don\'t be sad, have a hug! <3", "I just don\'t know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn\'t worry myself about that.", "I bet Cylons wouldn\'t have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I\'m Minecraft, and I\'m a crashaholic.", "Ooh. Shiny.", "This doesn\'t make any sense!", "Why is it breaking :(", "Don\'t do that.", "Ouch. That hurt :(", "You\'re mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!"};

        try
        {
            return var0[(int)(System.nanoTime() % (long)var0.length)];
        }
        catch (Throwable var2)
        {
            return "Witty comment unavailable :(";
        }
    }

    public static CrashReport a(Throwable par0Throwable, String par1Str)
    {
        CrashReport var2;

        if (par0Throwable instanceof ReportedException)
        {
            var2 = ((ReportedException)par0Throwable).a();
        }
        else
        {
            var2 = new CrashReport(par1Str, par0Throwable);
        }

        return var2;
    }
}
