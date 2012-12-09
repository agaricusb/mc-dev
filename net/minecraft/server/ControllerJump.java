package net.minecraft.server;

public class ControllerJump
{
    private EntityLiving a;
    private boolean b = false;

    public ControllerJump(EntityLiving par1EntityLiving)
    {
        this.a = par1EntityLiving;
    }

    public void a()
    {
        this.b = true;
    }

    /**
     * Called to actually make the entity jump if isJumping is true.
     */
    public void b()
    {
        this.a.e(this.b);
        this.b = false;
    }
}
