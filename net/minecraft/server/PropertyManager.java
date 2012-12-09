package net.minecraft.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyManager
{
    /** Reference to the logger. */
    public static Logger a = Logger.getLogger("Minecraft");

    /** The server properties object. */
    private Properties properties = new Properties();

    /** The server properties file. */
    private File c;

    public PropertyManager(File par1File)
    {
        this.c = par1File;

        if (par1File.exists())
        {
            FileInputStream var2 = null;

            try
            {
                var2 = new FileInputStream(par1File);
                this.properties.load(var2);
            }
            catch (Exception var12)
            {
                a.log(Level.WARNING, "Failed to load " + par1File, var12);
                this.a();
            }
            finally
            {
                if (var2 != null)
                {
                    try
                    {
                        var2.close();
                    }
                    catch (IOException var11)
                    {
                        ;
                    }
                }
            }
        }
        else
        {
            a.log(Level.WARNING, par1File + " does not exist");
            this.a();
        }
    }

    /**
     * Generates a new properties file.
     */
    public void a()
    {
        a.log(Level.INFO, "Generating new properties file");
        this.savePropertiesFile();
    }

    /**
     * Writes the properties to the properties file.
     */
    public void savePropertiesFile()
    {
        FileOutputStream var1 = null;

        try
        {
            var1 = new FileOutputStream(this.c);
            this.properties.store(var1, "Minecraft server properties");
        }
        catch (Exception var11)
        {
            a.log(Level.WARNING, "Failed to save " + this.c, var11);
            this.a();
        }
        finally
        {
            if (var1 != null)
            {
                try
                {
                    var1.close();
                }
                catch (IOException var10)
                {
                    ;
                }
            }
        }
    }

    /**
     * Returns this PropertyManager's file object used for property saving.
     */
    public File c()
    {
        return this.c;
    }

    /**
     * Returns a string property. If the property doesn't exist the default is returned.
     */
    public String getString(String par1Str, String par2Str)
    {
        if (!this.properties.containsKey(par1Str))
        {
            this.properties.setProperty(par1Str, par2Str);
            this.savePropertiesFile();
        }

        return this.properties.getProperty(par1Str, par2Str);
    }

    /**
     * Gets an integer property. If it does not exist, set it to the specified value.
     */
    public int getInt(String par1Str, int par2)
    {
        try
        {
            return Integer.parseInt(this.getString(par1Str, "" + par2));
        }
        catch (Exception var4)
        {
            this.properties.setProperty(par1Str, "" + par2);
            return par2;
        }
    }

    /**
     * Gets a boolean property. If it does not exist, set it to the specified value.
     */
    public boolean getBoolean(String par1Str, boolean par2)
    {
        try
        {
            return Boolean.parseBoolean(this.getString(par1Str, "" + par2));
        }
        catch (Exception var4)
        {
            this.properties.setProperty(par1Str, "" + par2);
            return par2;
        }
    }

    /**
     * Saves an Object with the given property name.
     */
    public void a(String par1Str, Object par2Obj)
    {
        this.properties.setProperty(par1Str, "" + par2Obj);
    }
}
