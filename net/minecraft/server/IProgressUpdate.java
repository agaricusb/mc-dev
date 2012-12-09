package net.minecraft.server;

public interface IProgressUpdate
{
    /**
     * Shows the 'Saving level' string.
     */
    void a(String var1);

    /**
     * Displays a string on the loading screen supposed to indicate what is being done currently.
     */
    void c(String var1);

    /**
     * Updates the progress bar on the loading screen to the specified amount. Args: loadProgress
     */
    void a(int var1);
}
