package net.minecraft.server;

public interface IMojangStatistics
{
    void a(MojangStatisticsGenerator var1);

    void b(MojangStatisticsGenerator var1);

    /**
     * Returns whether snooping is enabled or not.
     */
    boolean getSnooperEnabled();
}
