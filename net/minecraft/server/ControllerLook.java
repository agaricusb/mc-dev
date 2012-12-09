package net.minecraft.server;

public class ControllerLook
{
    private EntityLiving a;

    /**
     * The amount of change that is made each update for an entity facing a direction.
     */
    private float b;

    /**
     * The amount of change that is made each update for an entity facing a direction.
     */
    private float c;

    /** Whether or not the entity is trying to look at something. */
    private boolean d = false;
    private double e;
    private double f;
    private double g;

    public ControllerLook(EntityLiving par1EntityLiving)
    {
        this.a = par1EntityLiving;
    }

    /**
     * Sets position to look at using entity
     */
    public void a(Entity par1Entity, float par2, float par3)
    {
        this.e = par1Entity.locX;

        if (par1Entity instanceof EntityLiving)
        {
            this.f = par1Entity.locY + (double)par1Entity.getHeadHeight();
        }
        else
        {
            this.f = (par1Entity.boundingBox.b + par1Entity.boundingBox.e) / 2.0D;
        }

        this.g = par1Entity.locZ;
        this.b = par2;
        this.c = par3;
        this.d = true;
    }

    /**
     * Sets position to look at
     */
    public void a(double par1, double par3, double par5, float par7, float par8)
    {
        this.e = par1;
        this.f = par3;
        this.g = par5;
        this.b = par7;
        this.c = par8;
        this.d = true;
    }

    /**
     * Updates look
     */
    public void a()
    {
        this.a.pitch = 0.0F;

        if (this.d)
        {
            this.d = false;
            double var1 = this.e - this.a.locX;
            double var3 = this.f - (this.a.locY + (double)this.a.getHeadHeight());
            double var5 = this.g - this.a.locZ;
            double var7 = (double) MathHelper.sqrt(var1 * var1 + var5 * var5);
            float var9 = (float)(Math.atan2(var5, var1) * 180.0D / Math.PI) - 90.0F;
            float var10 = (float)(-(Math.atan2(var3, var7) * 180.0D / Math.PI));
            this.a.pitch = this.a(this.a.pitch, var10, this.c);
            this.a.ay = this.a(this.a.ay, var9, this.b);
        }
        else
        {
            this.a.ay = this.a(this.a.ay, this.a.aw, 10.0F);
        }

        float var11 = MathHelper.g(this.a.ay - this.a.aw);

        if (!this.a.getNavigation().f())
        {
            if (var11 < -75.0F)
            {
                this.a.ay = this.a.aw - 75.0F;
            }

            if (var11 > 75.0F)
            {
                this.a.ay = this.a.aw + 75.0F;
            }
        }
    }

    private float a(float par1, float par2, float par3)
    {
        float var4 = MathHelper.g(par2 - par1);

        if (var4 > par3)
        {
            var4 = par3;
        }

        if (var4 < -par3)
        {
            var4 = -par3;
        }

        return par1 + var4;
    }
}
