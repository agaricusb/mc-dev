package net.minecraft.server;

public enum EnumEntitySize
{
    SIZE_1,
    SIZE_2,
    SIZE_3,
    SIZE_4,
    SIZE_5,
    SIZE_6;

    public int a(double par1)
    {
        double var3 = par1 - ((double) MathHelper.floor(par1) + 0.5D);

        switch (EntitySizes.a[this.ordinal()])
        {
            case 1:
                if (var3 < 0.0D)
                {
                    if (var3 < -0.3125D)
                    {
                        return MathHelper.f(par1 * 32.0D);
                    }
                }
                else if (var3 < 0.3125D)
                {
                    return MathHelper.f(par1 * 32.0D);
                }

                return MathHelper.floor(par1 * 32.0D);

            case 2:
                if (var3 < 0.0D)
                {
                    if (var3 < -0.3125D)
                    {
                        return MathHelper.floor(par1 * 32.0D);
                    }
                }
                else if (var3 < 0.3125D)
                {
                    return MathHelper.floor(par1 * 32.0D);
                }

                return MathHelper.f(par1 * 32.0D);

            case 3:
                if (var3 > 0.0D)
                {
                    return MathHelper.floor(par1 * 32.0D);
                }

                return MathHelper.f(par1 * 32.0D);

            case 4:
                if (var3 < 0.0D)
                {
                    if (var3 < -0.1875D)
                    {
                        return MathHelper.f(par1 * 32.0D);
                    }
                }
                else if (var3 < 0.1875D)
                {
                    return MathHelper.f(par1 * 32.0D);
                }

                return MathHelper.floor(par1 * 32.0D);

            case 5:
                if (var3 < 0.0D)
                {
                    if (var3 < -0.1875D)
                    {
                        return MathHelper.floor(par1 * 32.0D);
                    }
                }
                else if (var3 < 0.1875D)
                {
                    return MathHelper.floor(par1 * 32.0D);
                }

                return MathHelper.f(par1 * 32.0D);

            case 6:
            default:
                if (var3 > 0.0D)
                {
                    return MathHelper.f(par1 * 32.0D);
                }
                else
                {
                    return MathHelper.floor(par1 * 32.0D);
                }
        }
    }
}
