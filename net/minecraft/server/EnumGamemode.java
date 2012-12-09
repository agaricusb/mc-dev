package net.minecraft.server;

public enum EnumGamemode
{
    NONE(-1, ""),
    SURVIVAL(0, "survival"),
    CREATIVE(1, "creative"),
    ADVENTURE(2, "adventure");
    int e;
    String f;

    private EnumGamemode(int par3, String par4Str)
    {
        this.e = par3;
        this.f = par4Str;
    }

    /**
     * Returns the ID of this game type
     */
    public int a()
    {
        return this.e;
    }

    /**
     * Returns the name of this game type
     */
    public String b()
    {
        return this.f;
    }

    /**
     * Configures the player capabilities based on the game type
     */
    public void a(PlayerAbilities par1PlayerCapabilities)
    {
        if (this == CREATIVE)
        {
            par1PlayerCapabilities.canFly = true;
            par1PlayerCapabilities.canInstantlyBuild = true;
            par1PlayerCapabilities.isInvulnerable = true;
        }
        else
        {
            par1PlayerCapabilities.canFly = false;
            par1PlayerCapabilities.canInstantlyBuild = false;
            par1PlayerCapabilities.isInvulnerable = false;
            par1PlayerCapabilities.isFlying = false;
        }

        par1PlayerCapabilities.mayBuild = !this.isAdventure();
    }

    /**
     * Returns true if this is the ADVENTURE game type
     */
    public boolean isAdventure()
    {
        return this == ADVENTURE;
    }

    /**
     * Returns true if this is the CREATIVE game type
     */
    public boolean d()
    {
        return this == CREATIVE;
    }

    /**
     * Returns the game type with the specified ID, or SURVIVAL if none found. Args: id
     */
    public static EnumGamemode a(int par0)
    {
        EnumGamemode[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3)
        {
            EnumGamemode var4 = var1[var3];

            if (var4.e == par0)
            {
                return var4;
            }
        }

        return SURVIVAL;
    }
}
