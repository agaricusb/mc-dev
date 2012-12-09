package net.minecraft.server;

final class MaterialWeb extends Material
{
    MaterialWeb(MaterialMapColor par1MapColor)
    {
        super(par1MapColor);
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean isSolid()
    {
        return false;
    }
}
