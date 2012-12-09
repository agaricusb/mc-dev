package net.minecraft.server;

import java.util.Random;

public abstract class StructurePieceBlockSelector
{
    protected int a;
    protected int b;

    /**
     * picks Block Ids and Metadata (Silverfish)
     */
    public abstract void a(Random var1, int var2, int var3, int var4, boolean var5);

    public int a()
    {
        return this.a;
    }

    public int b()
    {
        return this.b;
    }
}
