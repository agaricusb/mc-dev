package net.minecraft.server;

public class EntityAIBodyControl
{
    /** Instance of EntityLiving. */
    private EntityLiving entity;
    private int b = 0;
    private float c = 0.0F;

    public EntityAIBodyControl(EntityLiving par1EntityLiving)
    {
        this.entity = par1EntityLiving;
    }

    public void a()
    {
        double var1 = this.entity.locX - this.entity.lastX;
        double var3 = this.entity.locZ - this.entity.lastZ;

        if (var1 * var1 + var3 * var3 > 2.500000277905201E-7D)
        {
            this.entity.ax = this.entity.yaw;
            this.entity.az = this.a(this.entity.ax, this.entity.az, 75.0F);
            this.c = this.entity.az;
            this.b = 0;
        }
        else
        {
            float var5 = 75.0F;

            if (Math.abs(this.entity.az - this.c) > 15.0F)
            {
                this.b = 0;
                this.c = this.entity.az;
            }
            else
            {
                ++this.b;

                if (this.b > 10)
                {
                    var5 = Math.max(1.0F - (float)(this.b - 10) / 10.0F, 0.0F) * 75.0F;
                }
            }

            this.entity.ax = this.a(this.entity.az, this.entity.ax, var5);
        }
    }

    private float a(float par1, float par2, float par3)
    {
        float var4 = MathHelper.g(par1 - par2);

        if (var4 < -par3)
        {
            var4 = -par3;
        }

        if (var4 >= par3)
        {
            var4 = par3;
        }

        return par1 - var4;
    }
}
