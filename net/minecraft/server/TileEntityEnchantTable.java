package net.minecraft.server;

import java.util.Random;

public class TileEntityEnchantTable extends TileEntity
{
    /** Used by the render to make the book 'bounce' */
    public int a;

    /** Value used for determining how the page flip should look. */
    public float b;

    /** The last tick's pageFlip value. */
    public float c;
    public float d;
    public float e;

    /** The amount that the book is open. */
    public float f;

    /** The amount that the book was open last tick. */
    public float g;
    public float h;
    public float i;
    public float j;
    private static Random r = new Random();

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void g()
    {
        super.g();
        this.g = this.f;
        this.i = this.h;
        EntityHuman var1 = this.world.findNearbyPlayer((double) ((float) this.x + 0.5F), (double) ((float) this.y + 0.5F), (double) ((float) this.z + 0.5F), 3.0D);

        if (var1 != null)
        {
            double var2 = var1.locX - (double)((float)this.x + 0.5F);
            double var4 = var1.locZ - (double)((float)this.z + 0.5F);
            this.j = (float)Math.atan2(var4, var2);
            this.f += 0.1F;

            if (this.f < 0.5F || r.nextInt(40) == 0)
            {
                float var6 = this.d;

                do
                {
                    this.d += (float)(r.nextInt(4) - r.nextInt(4));
                }
                while (var6 == this.d);
            }
        }
        else
        {
            this.j += 0.02F;
            this.f -= 0.1F;
        }

        while (this.h >= (float)Math.PI)
        {
            this.h -= ((float)Math.PI * 2F);
        }

        while (this.h < -(float)Math.PI)
        {
            this.h += ((float)Math.PI * 2F);
        }

        while (this.j >= (float)Math.PI)
        {
            this.j -= ((float)Math.PI * 2F);
        }

        while (this.j < -(float)Math.PI)
        {
            this.j += ((float)Math.PI * 2F);
        }

        float var7;

        for (var7 = this.j - this.h; var7 >= (float)Math.PI; var7 -= ((float)Math.PI * 2F))
        {
            ;
        }

        while (var7 < -(float)Math.PI)
        {
            var7 += ((float)Math.PI * 2F);
        }

        this.h += var7 * 0.4F;

        if (this.f < 0.0F)
        {
            this.f = 0.0F;
        }

        if (this.f > 1.0F)
        {
            this.f = 1.0F;
        }

        ++this.a;
        this.c = this.b;
        float var3 = (this.d - this.b) * 0.4F;
        float var8 = 0.2F;

        if (var3 < -var8)
        {
            var3 = -var8;
        }

        if (var3 > var8)
        {
            var3 = var8;
        }

        this.e += (var3 - this.e) * 0.9F;
        this.b += this.e;
    }
}
