package net.minecraft.server;

public class ControllerMove
{
    /** The EntityLiving that is being moved */
    private EntityLiving a;
    private double b;
    private double c;
    private double d;

    /** The speed at which the entity should move */
    private float e;
    private boolean f = false;

    public ControllerMove(EntityLiving par1EntityLiving)
    {
        this.a = par1EntityLiving;
        this.b = par1EntityLiving.locX;
        this.c = par1EntityLiving.locY;
        this.d = par1EntityLiving.locZ;
    }

    public boolean a()
    {
        return this.f;
    }

    public float b()
    {
        return this.e;
    }

    /**
     * Sets the speed and location to move to
     */
    public void a(double par1, double par3, double par5, float par7)
    {
        this.b = par1;
        this.c = par3;
        this.d = par5;
        this.e = par7;
        this.f = true;
    }

    public void c()
    {
        this.a.f(0.0F);

        if (this.f)
        {
            this.f = false;
            int var1 = MathHelper.floor(this.a.boundingBox.b + 0.5D);
            double var2 = this.b - this.a.locX;
            double var4 = this.d - this.a.locZ;
            double var6 = this.c - (double)var1;
            double var8 = var2 * var2 + var6 * var6 + var4 * var4;

            if (var8 >= 2.500000277905201E-7D)
            {
                float var10 = (float)(Math.atan2(var4, var2) * 180.0D / Math.PI) - 90.0F;
                this.a.yaw = this.a(this.a.yaw, var10, 30.0F);
                this.a.e(this.e * this.a.bB());

                if (var6 > 0.0D && var2 * var2 + var4 * var4 < 1.0D)
                {
                    this.a.getControllerJump().a();
                }
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
