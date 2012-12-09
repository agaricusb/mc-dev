package net.minecraft.server;

public class DemoItemInWorldManager extends ItemInWorldManager
{
    private boolean c = false;
    private boolean d = false;
    private int e = 0;
    private int f = 0;

    public DemoItemInWorldManager(World par1World)
    {
        super(par1World);
    }

    public void a()
    {
        super.a();
        ++this.f;
        long var1 = this.world.getTime();
        long var3 = var1 / 24000L + 1L;

        if (!this.c && this.f > 20)
        {
            this.c = true;
            this.player.netServerHandler.sendPacket(new Packet70Bed(5, 0));
        }

        this.d = var1 > 120500L;

        if (this.d)
        {
            ++this.e;
        }

        if (var1 % 24000L == 500L)
        {
            if (var3 <= 6L)
            {
                this.player.sendMessage(this.player.a("demo.day." + var3, new Object[0]));
            }
        }
        else if (var3 == 1L)
        {
            if (var1 == 100L)
            {
                this.player.netServerHandler.sendPacket(new Packet70Bed(5, 101));
            }
            else if (var1 == 175L)
            {
                this.player.netServerHandler.sendPacket(new Packet70Bed(5, 102));
            }
            else if (var1 == 250L)
            {
                this.player.netServerHandler.sendPacket(new Packet70Bed(5, 103));
            }
        }
        else if (var3 == 5L && var1 % 24000L == 22000L)
        {
            this.player.sendMessage(this.player.a("demo.day.warning", new Object[0]));
        }
    }

    /**
     * Sends a message to the player reminding them that this is the demo version
     */
    private void e()
    {
        if (this.e > 100)
        {
            this.player.sendMessage(this.player.a("demo.reminder", new Object[0]));
            this.e = 0;
        }
    }

    public void dig(int par1, int par2, int par3, int par4)
    {
        if (this.d)
        {
            this.e();
        }
        else
        {
            super.dig(par1, par2, par3, par4);
        }
    }

    public void a(int par1, int par2, int par3)
    {
        if (!this.d)
        {
            super.a(par1, par2, par3);
        }
    }

    /**
     * Attempts to harvest a block at the given coordinate
     */
    public boolean breakBlock(int par1, int par2, int par3)
    {
        return this.d ? false : super.breakBlock(par1, par2, par3);
    }

    /**
     * Attempts to right-click use an item by the given EntityPlayer in the given World
     */
    public boolean useItem(EntityHuman par1EntityPlayer, World par2World, ItemStack par3ItemStack)
    {
        if (this.d)
        {
            this.e();
            return false;
        }
        else
        {
            return super.useItem(par1EntityPlayer, par2World, par3ItemStack);
        }
    }

    /**
     * Activate the clicked on block, otherwise use the held item. Args: player, world, itemStack, x, y, z, side,
     * xOffset, yOffset, zOffset
     */
    public boolean interact(EntityHuman par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (this.d)
        {
            this.e();
            return false;
        }
        else
        {
            return super.interact(par1EntityPlayer, par2World, par3ItemStack, par4, par5, par6, par7, par8, par9, par10);
        }
    }
}
