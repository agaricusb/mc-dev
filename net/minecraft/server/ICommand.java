package net.minecraft.server;

import java.util.List;

public interface ICommand extends Comparable
{
    String c();

    String a(ICommandListener var1);

    List b();

    void b(ICommandListener var1, String[] var2);

    /**
     * Returns true if the given command sender is allowed to use this command.
     */
    boolean b(ICommandListener var1);

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    List a(ICommandListener var1, String[] var2);

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    boolean a(int var1);
}
