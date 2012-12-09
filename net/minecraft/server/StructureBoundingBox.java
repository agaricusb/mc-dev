package net.minecraft.server;

public class StructureBoundingBox
{
    /** The first x coordinate of a bounding box. */
    public int a;

    /** The first y coordinate of a bounding box. */
    public int b;

    /** The first z coordinate of a bounding box. */
    public int c;

    /** The second x coordinate of a bounding box. */
    public int d;

    /** The second y coordinate of a bounding box. */
    public int e;

    /** The second z coordinate of a bounding box. */
    public int f;

    public StructureBoundingBox() {}

    /**
     * returns a new StructureBoundingBox with MAX values
     */
    public static StructureBoundingBox a()
    {
        return new StructureBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    /**
     * used to project a possible new component Bounding Box - to check if it would cut anything already spawned
     */
    public static StructureBoundingBox a(int par0, int par1, int par2, int par3, int par4, int par5, int par6, int par7, int par8, int par9)
    {
        switch (par9)
        {
            case 0:
                return new StructureBoundingBox(par0 + par3, par1 + par4, par2 + par5, par0 + par6 - 1 + par3, par1 + par7 - 1 + par4, par2 + par8 - 1 + par5);

            case 1:
                return new StructureBoundingBox(par0 - par8 + 1 + par5, par1 + par4, par2 + par3, par0 + par5, par1 + par7 - 1 + par4, par2 + par6 - 1 + par3);

            case 2:
                return new StructureBoundingBox(par0 + par3, par1 + par4, par2 - par8 + 1 + par5, par0 + par6 - 1 + par3, par1 + par7 - 1 + par4, par2 + par5);

            case 3:
                return new StructureBoundingBox(par0 + par5, par1 + par4, par2 + par3, par0 + par8 - 1 + par5, par1 + par7 - 1 + par4, par2 + par6 - 1 + par3);

            default:
                return new StructureBoundingBox(par0 + par3, par1 + par4, par2 + par5, par0 + par6 - 1 + par3, par1 + par7 - 1 + par4, par2 + par8 - 1 + par5);
        }
    }

    public StructureBoundingBox(StructureBoundingBox par1StructureBoundingBox)
    {
        this.a = par1StructureBoundingBox.a;
        this.b = par1StructureBoundingBox.b;
        this.c = par1StructureBoundingBox.c;
        this.d = par1StructureBoundingBox.d;
        this.e = par1StructureBoundingBox.e;
        this.f = par1StructureBoundingBox.f;
    }

    public StructureBoundingBox(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3;
        this.d = par4;
        this.e = par5;
        this.f = par6;
    }

    public StructureBoundingBox(int par1, int par2, int par3, int par4)
    {
        this.a = par1;
        this.c = par2;
        this.d = par3;
        this.f = par4;
        this.b = 1;
        this.e = 512;
    }

    /**
     * Discover if bounding box can fit within the current bounding box object.
     */
    public boolean a(StructureBoundingBox par1StructureBoundingBox)
    {
        return this.d >= par1StructureBoundingBox.a && this.a <= par1StructureBoundingBox.d && this.f >= par1StructureBoundingBox.c && this.c <= par1StructureBoundingBox.f && this.e >= par1StructureBoundingBox.b && this.b <= par1StructureBoundingBox.e;
    }

    /**
     * Discover if a coordinate is inside the bounding box area.
     */
    public boolean a(int par1, int par2, int par3, int par4)
    {
        return this.d >= par1 && this.a <= par3 && this.f >= par2 && this.c <= par4;
    }

    /**
     * Expands a bounding box's dimensions to include the supplied bounding box.
     */
    public void b(StructureBoundingBox par1StructureBoundingBox)
    {
        this.a = Math.min(this.a, par1StructureBoundingBox.a);
        this.b = Math.min(this.b, par1StructureBoundingBox.b);
        this.c = Math.min(this.c, par1StructureBoundingBox.c);
        this.d = Math.max(this.d, par1StructureBoundingBox.d);
        this.e = Math.max(this.e, par1StructureBoundingBox.e);
        this.f = Math.max(this.f, par1StructureBoundingBox.f);
    }

    /**
     * Offsets the current bounding box by the specified coordinates. Args: x, y, z
     */
    public void a(int par1, int par2, int par3)
    {
        this.a += par1;
        this.b += par2;
        this.c += par3;
        this.d += par1;
        this.e += par2;
        this.f += par3;
    }

    /**
     * Discover if a coordinate is inside the bounding box volume.
     */
    public boolean b(int par1, int par2, int par3)
    {
        return par1 >= this.a && par1 <= this.d && par3 >= this.c && par3 <= this.f && par2 >= this.b && par2 <= this.e;
    }

    /**
     * Get dimension of the bounding box in the x direction.
     */
    public int b()
    {
        return this.d - this.a + 1;
    }

    /**
     * Get dimension of the bounding box in the y direction.
     */
    public int c()
    {
        return this.e - this.b + 1;
    }

    /**
     * Get dimension of the bounding box in the z direction.
     */
    public int d()
    {
        return this.f - this.c + 1;
    }

    public int e()
    {
        return this.a + (this.d - this.a + 1) / 2;
    }

    public int f()
    {
        return this.b + (this.e - this.b + 1) / 2;
    }

    public int g()
    {
        return this.c + (this.f - this.c + 1) / 2;
    }

    public String toString()
    {
        return "(" + this.a + ", " + this.b + ", " + this.c + "; " + this.d + ", " + this.e + ", " + this.f + ")";
    }
}
