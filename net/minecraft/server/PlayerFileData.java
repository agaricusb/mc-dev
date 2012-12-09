package net.minecraft.server;

public interface PlayerFileData
{
    /**
     * Writes the player data to disk from the specified PlayerEntityMP.
     */
    void save(EntityHuman var1);

    /**
     * Reads the player data from disk into the specified PlayerEntityMP.
     */
    void load(EntityHuman var1);

    /**
     * Returns an array of usernames for which player.dat exists for.
     */
    String[] getSeenPlayers();
}
