package net.minecraft.server;


public class ConvertProgressUpdater implements IProgressUpdate
{
    private long b;

    final MinecraftServer a;

    public ConvertProgressUpdater(MinecraftServer par1MinecraftServer)
    {
        this.a = par1MinecraftServer;
        this.b = System.currentTimeMillis();
    }

    /**
     * Shows the 'Saving level' string.
     */
    public void a(String par1Str) {}

    /**
     * Updates the progress bar on the loading screen to the specified amount. Args: loadProgress
     */
    public void a(int par1)
    {
        if (System.currentTimeMillis() - this.b >= 1000L)
        {
            this.b = System.currentTimeMillis();
            MinecraftServer.log.info("Converting... " + par1 + "%");
        }
    }

    /**
     * Displays a string on the loading screen supposed to indicate what is being done currently.
     */
    public void c(String par1Str) {}
}
