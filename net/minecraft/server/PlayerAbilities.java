package net.minecraft.server;

public class PlayerAbilities
{
    /** Disables player damage. */
    public boolean isInvulnerable = false;

    /** Sets/indicates whether the player is flying. */
    public boolean isFlying = false;

    /** whether or not to allow the player to fly when they double jump. */
    public boolean canFly = false;

    /**
     * Used to determine if creative mode is enabled, and therefore if items should be depleted on usage
     */
    public boolean canInstantlyBuild = false;

    /** Indicates whether the player is allowed to modify the surroundings */
    public boolean mayBuild = true;
    private float flySpeed = 0.05F;
    private float walkSpeed = 0.1F;

    public void a(NBTTagCompound par1NBTTagCompound)
    {
        NBTTagCompound var2 = new NBTTagCompound();
        var2.setBoolean("invulnerable", this.isInvulnerable);
        var2.setBoolean("flying", this.isFlying);
        var2.setBoolean("mayfly", this.canFly);
        var2.setBoolean("instabuild", this.canInstantlyBuild);
        var2.setBoolean("mayBuild", this.mayBuild);
        var2.setFloat("flySpeed", this.flySpeed);
        var2.setFloat("walkSpeed", this.walkSpeed);
        par1NBTTagCompound.set("abilities", var2);
    }

    public void b(NBTTagCompound par1NBTTagCompound)
    {
        if (par1NBTTagCompound.hasKey("abilities"))
        {
            NBTTagCompound var2 = par1NBTTagCompound.getCompound("abilities");
            this.isInvulnerable = var2.getBoolean("invulnerable");
            this.isFlying = var2.getBoolean("flying");
            this.canFly = var2.getBoolean("mayfly");
            this.canInstantlyBuild = var2.getBoolean("instabuild");

            if (var2.hasKey("flySpeed"))
            {
                this.flySpeed = var2.getFloat("flySpeed");
                this.walkSpeed = var2.getFloat("walkSpeed");
            }

            if (var2.hasKey("mayBuild"))
            {
                this.mayBuild = var2.getBoolean("mayBuild");
            }
        }
    }

    public float a()
    {
        return this.flySpeed;
    }

    public float b()
    {
        return this.walkSpeed;
    }
}
