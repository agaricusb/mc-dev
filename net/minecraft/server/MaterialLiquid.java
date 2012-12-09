package net.minecraft.server;

public class MaterialLiquid extends Material
{
    public MaterialLiquid(MaterialMapColor par1MapColor)
    {
        super(par1MapColor);
        this.i();
        this.n();
    }

    /**
     * Returns if blocks of these materials are liquids.
     */
    public boolean isLiquid()
    {
        return true;
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean isSolid()
    {
        return false;
    }

    public boolean isBuildable()
    {
        return false;
    }
}
