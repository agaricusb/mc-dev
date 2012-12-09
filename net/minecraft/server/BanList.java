package net.minecraft.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BanList
{
    private final InsensitiveStringMap a = new InsensitiveStringMap();
    private final File b;

    /** set to true if not singlePlayer */
    private boolean c = true;

    public BanList(File par1File)
    {
        this.b = par1File;
    }

    public boolean isEnabled()
    {
        return this.c;
    }

    public void setEnabled(boolean par1)
    {
        this.c = par1;
    }

    /**
     * removes expired Bans before returning
     */
    public Map getEntries()
    {
        this.removeExpired();
        return this.a;
    }

    public boolean isBanned(String par1Str)
    {
        if (!this.isEnabled())
        {
            return false;
        }
        else
        {
            this.removeExpired();
            return this.a.containsKey(par1Str);
        }
    }

    public void add(BanEntry par1BanEntry)
    {
        this.a.put(par1BanEntry.getName(), par1BanEntry);
        this.save();
    }

    public void remove(String par1Str)
    {
        this.a.remove(par1Str);
        this.save();
    }

    public void removeExpired()
    {
        Iterator var1 = this.a.values().iterator();

        while (var1.hasNext())
        {
            BanEntry var2 = (BanEntry)var1.next();

            if (var2.hasExpired())
            {
                var1.remove();
            }
        }
    }

    /**
     * Loads the ban list from the file (adds every entry, does not clear the current list).
     */
    public void load()
    {
        if (this.b.isFile())
        {
            BufferedReader var1;

            try
            {
                var1 = new BufferedReader(new FileReader(this.b));
            }
            catch (FileNotFoundException var4)
            {
                throw new Error();
            }

            String var2;

            try
            {
                while ((var2 = var1.readLine()) != null)
                {
                    if (!var2.startsWith("#"))
                    {
                        BanEntry var3 = BanEntry.c(var2);

                        if (var3 != null)
                        {
                            this.a.put(var3.getName(), var3);
                        }
                    }
                }
            }
            catch (IOException var5)
            {
                Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not load ban list", var5);
            }
        }
    }

    public void save()
    {
        this.save(true);
    }

    /**
     * par1: include header
     */
    public void save(boolean par1)
    {
        this.removeExpired();

        try
        {
            PrintWriter var2 = new PrintWriter(new FileWriter(this.b, false));

            if (par1)
            {
                var2.println("# Updated " + (new SimpleDateFormat()).format(new Date()) + " by Minecraft " + "1.4.5");
                var2.println("# victim name | ban date | banned by | banned until | reason");
                var2.println();
            }

            Iterator var3 = this.a.values().iterator();

            while (var3.hasNext())
            {
                BanEntry var4 = (BanEntry)var3.next();
                var2.println(var4.g());
            }

            var2.close();
        }
        catch (IOException var5)
        {
            Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not save ban list", var5);
        }
    }
}
