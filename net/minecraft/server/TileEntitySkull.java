package net.minecraft.server;

public class TileEntitySkull extends TileEntity
{
    /** Entity type for this skull. */
    private int a;

    /** The skull's rotation. */
    private int b;

    /** Extra data for this skull, used as player username by player heads */
    private String c = "";

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setByte("SkullType", (byte)(this.a & 255));
        par1NBTTagCompound.setByte("Rot", (byte)(this.b & 255));
        par1NBTTagCompound.setString("ExtraType", this.c);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.a = par1NBTTagCompound.getByte("SkullType");
        this.b = par1NBTTagCompound.getByte("Rot");

        if (par1NBTTagCompound.hasKey("ExtraType"))
        {
            this.c = par1NBTTagCompound.getString("ExtraType");
        }
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getUpdatePacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.b(var1);
        return new Packet132TileEntityData(this.x, this.y, this.z, 4, var1);
    }

    public void setSkullType(int par1, String par2Str)
    {
        this.a = par1;
        this.c = par2Str;
    }

    public int getSkullType()
    {
        return this.a;
    }

    public void setRotation(int par1)
    {
        this.b = par1;
    }

    public String getExtraType()
    {
        return this.c;
    }
}
