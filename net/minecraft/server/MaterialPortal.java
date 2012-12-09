package net.minecraft.server;

public class MaterialPortal extends Material
{
    public MaterialPortal(MaterialMapColor par1MapColor)
    {
        super(par1MapColor);
    }

    public boolean isBuildable()
    {
        return false;
    }

    /**
     * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
     */
    public boolean blocksLight()
    {
        return false;
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean isSolid()
    {
        return false;
    }
}
