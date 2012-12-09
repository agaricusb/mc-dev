package net.minecraft.server;

public class TileEntityRecordPlayer extends TileEntity
{
    /** ID of record which is in Jukebox */
    public ItemStack record;

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("RecordItem"))
        {
            this.record = ItemStack.a(par1NBTTagCompound.getCompound("RecordItem"));
        }
        else
        {
            this.record = new ItemStack(par1NBTTagCompound.getInt("Record"), 1, 0);
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);

        if (this.record != null)
        {
            par1NBTTagCompound.setCompound("RecordItem", this.record.save(new NBTTagCompound()));
            par1NBTTagCompound.setInt("Record", this.record.id);
        }
    }
}
