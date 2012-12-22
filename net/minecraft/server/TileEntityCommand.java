package net.minecraft.server;


public class TileEntityCommand extends TileEntity implements ICommandListener
{
    /** The command this block will execute when powered. */
    private String a = "";

    /**
     * Sets the command this block will execute when powered.
     */
    public void b(String par1Str)
    {
        this.a = par1Str;
        this.update();
    }

    /**
     * Execute the command, called when the command block is powered.
     */
    public void a(World par1World)
    {
        if (!par1World.isStatic)
        {
            MinecraftServer var2 = MinecraftServer.getServer();

            if (var2 != null && var2.getEnableCommandBlock())
            {
                ICommandHandler var3 = var2.getCommandHandler();
                var3.a(this, this.a);
            }
        }
    }

    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    public String getName()
    {
        return "@";
    }

    public void sendMessage(String par1Str) {}

    /**
     * Returns true if the command sender is allowed to use the given command.
     */
    public boolean a(int par1, String par2Str)
    {
        return par1 <= 2;
    }

    /**
     * Translates and formats the given string key with the given arguments.
     */
    public String a(String par1Str, Object... par2ArrayOfObj)
    {
        return par1Str;
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setString("Command", this.a);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.a = par1NBTTagCompound.getString("Command");
    }

    /**
     * Return the position for this command sender.
     */
    public ChunkCoordinates b()
    {
        return new ChunkCoordinates(this.x, this.y, this.z);
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getUpdatePacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.b(var1);
        return new Packet132TileEntityData(this.x, this.y, this.z, 2, var1);
    }
}
