package net.minecraft.server;

public abstract class EntityFlying extends EntityLiving
{
    public EntityFlying(World par1World)
    {
        super(par1World);
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void a(float par1) {}

    /**
     * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
     * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
     */
    protected void a(double par1, boolean par3) {}

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    public void e(float par1, float par2)
    {
        if (this.H())
        {
            this.a(par1, par2, 0.02F);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.800000011920929D;
            this.motY *= 0.800000011920929D;
            this.motZ *= 0.800000011920929D;
        }
        else if (this.J())
        {
            this.a(par1, par2, 0.02F);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.5D;
            this.motY *= 0.5D;
            this.motZ *= 0.5D;
        }
        else
        {
            float var3 = 0.91F;

            if (this.onGround)
            {
                var3 = 0.54600006F;
                int var4 = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));

                if (var4 > 0)
                {
                    var3 = Block.byId[var4].frictionFactor * 0.91F;
                }
            }

            float var8 = 0.16277136F / (var3 * var3 * var3);
            this.a(par1, par2, this.onGround ? 0.1F * var8 : 0.02F);
            var3 = 0.91F;

            if (this.onGround)
            {
                var3 = 0.54600006F;
                int var5 = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));

                if (var5 > 0)
                {
                    var3 = Block.byId[var5].frictionFactor * 0.91F;
                }
            }

            this.move(this.motX, this.motY, this.motZ);
            this.motX *= (double)var3;
            this.motY *= (double)var3;
            this.motZ *= (double)var3;
        }

        this.bf = this.bg;
        double var10 = this.locX - this.lastX;
        double var9 = this.locZ - this.lastZ;
        float var7 = MathHelper.sqrt(var10 * var10 + var9 * var9) * 4.0F;

        if (var7 > 1.0F)
        {
            var7 = 1.0F;
        }

        this.bg += (var7 - this.bg) * 0.4F;
        this.bh += this.bg;
    }

    /**
     * returns true if this entity is by a ladder, false otherwise
     */
    public boolean g_()
    {
        return false;
    }
}
