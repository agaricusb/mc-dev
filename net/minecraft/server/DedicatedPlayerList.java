package net.minecraft.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;

public class DedicatedPlayerList extends PlayerList
{
    private File e;
    private File f;

    public DedicatedPlayerList(DedicatedServer par1DedicatedServer)
    {
        super(par1DedicatedServer);
        this.e = par1DedicatedServer.e("ops.txt");
        this.f = par1DedicatedServer.e("white-list.txt");
        this.d = par1DedicatedServer.a("view-distance", 10);
        this.maxPlayers = par1DedicatedServer.a("max-players", 20);
        this.setHasWhitelist(par1DedicatedServer.a("white-list", false));

        if (!par1DedicatedServer.I())
        {
            this.getNameBans().setEnabled(true);
            this.getIPBans().setEnabled(true);
        }

        this.getNameBans().load();
        this.getNameBans().save();
        this.getIPBans().load();
        this.getIPBans().save();
        this.t();
        this.v();
        this.u();
        this.w();
    }

    public void setHasWhitelist(boolean par1)
    {
        super.setHasWhitelist(par1);
        this.getServer().a("white-list", Boolean.valueOf(par1));
        this.getServer().a();
    }

    /**
     * This adds a username to the ops list, then saves the op list
     */
    public void addOp(String par1Str)
    {
        super.addOp(par1Str);
        this.u();
    }

    /**
     * This removes a username from the ops list, then saves the op list
     */
    public void removeOp(String par1Str)
    {
        super.removeOp(par1Str);
        this.u();
    }

    /**
     * Remove the specified player from the whitelist.
     */
    public void removeWhitelist(String par1Str)
    {
        super.removeWhitelist(par1Str);
        this.w();
    }

    /**
     * Add the specified player to the white list.
     */
    public void addWhitelist(String par1Str)
    {
        super.addWhitelist(par1Str);
        this.w();
    }

    /**
     * Either does nothing, or calls readWhiteList.
     */
    public void reloadWhitelist()
    {
        this.v();
    }

    private void t()
    {
        try
        {
            this.getOPs().clear();
            BufferedReader var1 = new BufferedReader(new FileReader(this.e));
            String var2 = "";

            while ((var2 = var1.readLine()) != null)
            {
                this.getOPs().add(var2.trim().toLowerCase());
            }

            var1.close();
        }
        catch (Exception var3)
        {
            a.warning("Failed to load operators list: " + var3);
        }
    }

    private void u()
    {
        try
        {
            PrintWriter var1 = new PrintWriter(new FileWriter(this.e, false));
            Iterator var2 = this.getOPs().iterator();

            while (var2.hasNext())
            {
                String var3 = (String)var2.next();
                var1.println(var3);
            }

            var1.close();
        }
        catch (Exception var4)
        {
            a.warning("Failed to save operators list: " + var4);
        }
    }

    private void v()
    {
        try
        {
            this.getWhitelisted().clear();
            BufferedReader var1 = new BufferedReader(new FileReader(this.f));
            String var2 = "";

            while ((var2 = var1.readLine()) != null)
            {
                this.getWhitelisted().add(var2.trim().toLowerCase());
            }

            var1.close();
        }
        catch (Exception var3)
        {
            a.warning("Failed to load white-list: " + var3);
        }
    }

    private void w()
    {
        try
        {
            PrintWriter var1 = new PrintWriter(new FileWriter(this.f, false));
            Iterator var2 = this.getWhitelisted().iterator();

            while (var2.hasNext())
            {
                String var3 = (String)var2.next();
                var1.println(var3);
            }

            var1.close();
        }
        catch (Exception var4)
        {
            a.warning("Failed to save white-list: " + var4);
        }
    }

    /**
     * Determine if the player is allowed to connect based on current server settings.
     */
    public boolean isWhitelisted(String par1Str)
    {
        par1Str = par1Str.trim().toLowerCase();
        return !this.getHasWhitelist() || this.isOp(par1Str) || this.getWhitelisted().contains(par1Str);
    }

    public DedicatedServer getServer()
    {
        return (DedicatedServer)super.getServer();
    }

    /* CBMCP - remove synthetic method
    public MinecraftServer getServer()
    {
        return this.getServer();
    }
    */
}
