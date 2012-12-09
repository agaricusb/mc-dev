package net.minecraft.server;

public class TileEntityNote extends TileEntity
{
    /** Note to play */
    public byte note = 0;

    /** stores the latest redstone state */
    public boolean b = false;

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setByte("note", this.note);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.note = par1NBTTagCompound.getByte("note");

        if (this.note < 0)
        {
            this.note = 0;
        }

        if (this.note > 24)
        {
            this.note = 24;
        }
    }

    /**
     * change pitch by -> (currentPitch + 1) % 25
     */
    public void a()
    {
        this.note = (byte)((this.note + 1) % 25);
        this.update();
    }

    /**
     * plays the stored note
     */
    public void play(World par1World, int par2, int par3, int par4)
    {
        if (par1World.getMaterial(par2, par3 + 1, par4) == Material.AIR)
        {
            Material var5 = par1World.getMaterial(par2, par3 - 1, par4);
            byte var6 = 0;

            if (var5 == Material.STONE)
            {
                var6 = 1;
            }

            if (var5 == Material.SAND)
            {
                var6 = 2;
            }

            if (var5 == Material.SHATTERABLE)
            {
                var6 = 3;
            }

            if (var5 == Material.WOOD)
            {
                var6 = 4;
            }

            par1World.playNote(par2, par3, par4, Block.NOTE_BLOCK.id, var6, this.note);
        }
    }
}
