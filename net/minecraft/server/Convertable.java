package net.minecraft.server;

public interface Convertable
{
    /**
     * Returns back a loader for the specified save directory
     */
    IDataManager a(String var1, boolean var2);

    void d();

    /**
     * @args: Takes one argument - the name of the directory of the world to delete. @desc: Delete the world by deleting
     * the associated directory recursively.
     */
    boolean e(String var1);

    /**
     * gets if the map is old chunk saving (true) or McRegion (false)
     */
    boolean isConvertable(String var1);

    /**
     * converts the map to mcRegion
     */
    boolean convert(String var1, IProgressUpdate var2);
}
