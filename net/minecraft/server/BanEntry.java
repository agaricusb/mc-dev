package net.minecraft.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class BanEntry
{
    public static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

    /** Creates Ban Entry in the logger. */
    public static Logger b = Logger.getLogger("Minecraft");
    private final String c;
    private Date d = new Date();
    private String e = "(Unknown)";
    private Date f = null;
    private String g = "Banned by an operator.";

    public BanEntry(String par1Str)
    {
        this.c = par1Str;
    }

    public String getName()
    {
        return this.c;
    }

    public Date getCreated()
    {
        return this.d;
    }

    /**
     * null == start ban now
     */
    public void setCreated(Date par1Date)
    {
        this.d = par1Date != null ? par1Date : new Date();
    }

    public String getSource()
    {
        return this.e;
    }

    public void setSource(String par1Str)
    {
        this.e = par1Str;
    }

    public Date getExpires()
    {
        return this.f;
    }

    public void setExpires(Date par1Date)
    {
        this.f = par1Date;
    }

    public boolean hasExpired()
    {
        return this.f == null ? false : this.f.before(new Date());
    }

    public String getReason()
    {
        return this.g;
    }

    public void setReason(String par1Str)
    {
        this.g = par1Str;
    }

    public String g()
    {
        StringBuilder var1 = new StringBuilder();
        var1.append(this.getName());
        var1.append("|");
        var1.append(a.format(this.getCreated()));
        var1.append("|");
        var1.append(this.getSource());
        var1.append("|");
        var1.append(this.getExpires() == null ? "Forever" : a.format(this.getExpires()));
        var1.append("|");
        var1.append(this.getReason());
        return var1.toString();
    }

    public static BanEntry c(String par0Str)
    {
        if (par0Str.trim().length() < 2)
        {
            return null;
        }
        else
        {
            String[] var1 = par0Str.trim().split(Pattern.quote("|"), 5);
            BanEntry var2 = new BanEntry(var1[0].trim());
            byte var3 = 0;
            int var10000 = var1.length;
            int var7 = var3 + 1;

            if (var10000 <= var7)
            {
                return var2;
            }
            else
            {
                try
                {
                    var2.setCreated(a.parse(var1[var7].trim()));
                }
                catch (ParseException var6)
                {
                    b.log(Level.WARNING, "Could not read creation date format for ban entry \'" + var2.getName() + "\' (was: \'" + var1[var7] + "\')", var6);
                }

                var10000 = var1.length;
                ++var7;

                if (var10000 <= var7)
                {
                    return var2;
                }
                else
                {
                    var2.setSource(var1[var7].trim());
                    var10000 = var1.length;
                    ++var7;

                    if (var10000 <= var7)
                    {
                        return var2;
                    }
                    else
                    {
                        try
                        {
                            String var4 = var1[var7].trim();

                            if (!var4.equalsIgnoreCase("Forever") && var4.length() > 0)
                            {
                                var2.setExpires(a.parse(var4));
                            }
                        }
                        catch (ParseException var5)
                        {
                            b.log(Level.WARNING, "Could not read expiry date format for ban entry \'" + var2.getName() + "\' (was: \'" + var1[var7] + "\')", var5);
                        }

                        var10000 = var1.length;
                        ++var7;

                        if (var10000 <= var7)
                        {
                            return var2;
                        }
                        else
                        {
                            var2.setReason(var1[var7].trim());
                            return var2;
                        }
                    }
                }
            }
        }
    }
}
