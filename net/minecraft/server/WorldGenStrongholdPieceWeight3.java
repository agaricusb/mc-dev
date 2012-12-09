package net.minecraft.server;

class WorldGenStrongholdPieceWeight3
{
    static final int[] a = new int[WorldGenStrongholdDoorType.values().length];

    static
    {
        try
        {
            a[WorldGenStrongholdDoorType.a.ordinal()] = 1;
        }
        catch (NoSuchFieldError var4)
        {
            ;
        }

        try
        {
            a[WorldGenStrongholdDoorType.b.ordinal()] = 2;
        }
        catch (NoSuchFieldError var3)
        {
            ;
        }

        try
        {
            a[WorldGenStrongholdDoorType.c.ordinal()] = 3;
        }
        catch (NoSuchFieldError var2)
        {
            ;
        }

        try
        {
            a[WorldGenStrongholdDoorType.d.ordinal()] = 4;
        }
        catch (NoSuchFieldError var1)
        {
            ;
        }
    }
}
