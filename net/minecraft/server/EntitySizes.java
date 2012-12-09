package net.minecraft.server;

class EntitySizes
{
    static final int[] a = new int[EnumEntitySize.values().length];

    static
    {
        try
        {
            a[EnumEntitySize.SIZE_1.ordinal()] = 1;
        }
        catch (NoSuchFieldError var6)
        {
            ;
        }

        try
        {
            a[EnumEntitySize.SIZE_2.ordinal()] = 2;
        }
        catch (NoSuchFieldError var5)
        {
            ;
        }

        try
        {
            a[EnumEntitySize.SIZE_3.ordinal()] = 3;
        }
        catch (NoSuchFieldError var4)
        {
            ;
        }

        try
        {
            a[EnumEntitySize.SIZE_4.ordinal()] = 4;
        }
        catch (NoSuchFieldError var3)
        {
            ;
        }

        try
        {
            a[EnumEntitySize.SIZE_5.ordinal()] = 5;
        }
        catch (NoSuchFieldError var2)
        {
            ;
        }

        try
        {
            a[EnumEntitySize.SIZE_6.ordinal()] = 6;
        }
        catch (NoSuchFieldError var1)
        {
            ;
        }
    }
}
