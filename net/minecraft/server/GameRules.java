package net.minecraft.server;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

public class GameRules
{
    private TreeMap a = new TreeMap();

    public GameRules()
    {
        this.a("doFireTick", "true");
        this.a("mobGriefing", "true");
        this.a("keepInventory", "false");
        this.a("doMobSpawning", "true");
        this.a("doMobLoot", "true");
        this.a("doTileDrops", "true");
        this.a("commandBlockOutput", "true");
    }

    /**
     * Define a game rule and its default value.
     */
    public void a(String par1Str, String par2Str)
    {
        this.a.put(par1Str, new GameRuleValue(par2Str));
    }

    public void set(String par1Str, String par2Str)
    {
        GameRuleValue var3 = (GameRuleValue)this.a.get(par1Str);

        if (var3 != null)
        {
            var3.a(par2Str);
        }
        else
        {
            this.a(par1Str, par2Str);
        }
    }

    /**
     * Gets the string Game Rule value.
     */
    public String get(String par1Str)
    {
        GameRuleValue var2 = (GameRuleValue)this.a.get(par1Str);
        return var2 != null ? var2.a() : "";
    }

    /**
     * Gets the boolean Game Rule value.
     */
    public boolean getBoolean(String par1Str)
    {
        GameRuleValue var2 = (GameRuleValue)this.a.get(par1Str);
        return var2 != null ? var2.b() : false;
    }

    /**
     * Return the defined game rules as NBT.
     */
    public NBTTagCompound a()
    {
        NBTTagCompound var1 = new NBTTagCompound("GameRules");
        Iterator var2 = this.a.keySet().iterator();

        while (var2.hasNext())
        {
            String var3 = (String)var2.next();
            GameRuleValue var4 = (GameRuleValue)this.a.get(var3);
            var1.setString(var3, var4.a());
        }

        return var1;
    }

    /**
     * Set defined game rules from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        Collection var2 = par1NBTTagCompound.c();
        Iterator var3 = var2.iterator();

        while (var3.hasNext())
        {
            NBTBase var4 = (NBTBase)var3.next();
            String var5 = var4.getName();
            String var6 = par1NBTTagCompound.getString(var4.getName());
            this.set(var5, var6);
        }
    }

    /**
     * Return the defined game rules.
     */
    public String[] b()
    {
        return (String[])this.a.keySet().toArray(new String[0]);
    }

    /**
     * Return whether the specified game rule is defined.
     */
    public boolean e(String par1Str)
    {
        return this.a.containsKey(par1Str);
    }
}
