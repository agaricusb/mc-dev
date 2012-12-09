package net.minecraft.server;

public class WorldProviderNormal extends WorldProvider
{
    /**
     * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
     */
    public String getName()
    {
        return "Overworld";
    }
}
