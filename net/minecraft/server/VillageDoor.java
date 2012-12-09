package net.minecraft.server;

public class VillageDoor
{
    public final int locX;
    public final int locY;
    public final int locZ;
    public final int d;
    public final int e;
    public int addedTime;
    public boolean removed = false;
    private int bookings = 0;

    public VillageDoor(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        this.locX = par1;
        this.locY = par2;
        this.locZ = par3;
        this.d = par4;
        this.e = par5;
        this.addedTime = par6;
    }

    /**
     * Returns the squared distance between this door and the given coordinate.
     */
    public int b(int par1, int par2, int par3)
    {
        int var4 = par1 - this.locX;
        int var5 = par2 - this.locY;
        int var6 = par3 - this.locZ;
        return var4 * var4 + var5 * var5 + var6 * var6;
    }

    /**
     * Get the square of the distance from a location 2 blocks away from the door considered 'inside' and the given
     * arguments
     */
    public int c(int par1, int par2, int par3)
    {
        int var4 = par1 - this.locX - this.d;
        int var5 = par2 - this.locY;
        int var6 = par3 - this.locZ - this.e;
        return var4 * var4 + var5 * var5 + var6 * var6;
    }

    public int getIndoorsX()
    {
        return this.locX + this.d;
    }

    public int getIndoorsY()
    {
        return this.locY;
    }

    public int getIndoorsZ()
    {
        return this.locZ + this.e;
    }

    public boolean a(int par1, int par2)
    {
        int var3 = par1 - this.locX;
        int var4 = par2 - this.locZ;
        return var3 * this.d + var4 * this.e >= 0;
    }

    public void d()
    {
        this.bookings = 0;
    }

    public void e()
    {
        ++this.bookings;
    }

    public int f()
    {
        return this.bookings;
    }
}
