package net.minecraft.server;

public class TileEntitySign extends TileEntity
{
    /** An array of four strings storing the lines of text on the sign. */
    public String[] lines = new String[] {"", "", "", ""};

    /**
     * The index of the line currently being edited. Only used on client side, but defined on both. Note this is only
     * really used when the > < are going to be visible.
     */
    public int b = -1;
    private boolean isEditable = true;

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setString("Text1", this.lines[0]);
        par1NBTTagCompound.setString("Text2", this.lines[1]);
        par1NBTTagCompound.setString("Text3", this.lines[2]);
        par1NBTTagCompound.setString("Text4", this.lines[3]);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        this.isEditable = false;
        super.a(par1NBTTagCompound);

        for (int var2 = 0; var2 < 4; ++var2)
        {
            this.lines[var2] = par1NBTTagCompound.getString("Text" + (var2 + 1));

            if (this.lines[var2].length() > 15)
            {
                this.lines[var2] = this.lines[var2].substring(0, 15);
            }
        }
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getUpdatePacket()
    {
        String[] var1 = new String[4];
        System.arraycopy(this.lines, 0, var1, 0, 4);
        return new Packet130UpdateSign(this.x, this.y, this.z, var1);
    }

    public boolean a()
    {
        return this.isEditable;
    }
}
