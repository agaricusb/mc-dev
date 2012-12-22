package net.minecraft.server;

public class FoodMetaData
{
    /** The player's food level. */
    private int foodLevel = 20;

    /** The player's food saturation. */
    private float saturationLevel = 5.0F;

    /** The player's food exhaustion. */
    private float exhaustionLevel;

    /** The player's food timer value. */
    private int foodTickTimer = 0;
    private int e = 20;

    /**
     * Args: int foodLevel, float foodSaturationModifier
     */
    public void eat(int par1, float par2)
    {
        this.foodLevel = Math.min(par1 + this.foodLevel, 20);
        this.saturationLevel = Math.min(this.saturationLevel + (float)par1 * par2 * 2.0F, (float)this.foodLevel);
    }

    /**
     * Eat some food.
     */
    public void a(ItemFood par1ItemFood)
    {
        this.eat(par1ItemFood.getNutrition(), par1ItemFood.getSaturationModifier());
    }

    /**
     * Handles the food game logic.
     */
    public void a(EntityHuman par1EntityPlayer)
    {
        int var2 = par1EntityPlayer.world.difficulty;
        this.e = this.foodLevel;

        if (this.exhaustionLevel > 4.0F)
        {
            this.exhaustionLevel -= 4.0F;

            if (this.saturationLevel > 0.0F)
            {
                this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
            }
            else if (var2 > 0)
            {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }

        if (this.foodLevel >= 18 && par1EntityPlayer.cd())
        {
            ++this.foodTickTimer;

            if (this.foodTickTimer >= 80)
            {
                par1EntityPlayer.heal(1);
                this.foodTickTimer = 0;
            }
        }
        else if (this.foodLevel <= 0)
        {
            ++this.foodTickTimer;

            if (this.foodTickTimer >= 80)
            {
                if (par1EntityPlayer.getHealth() > 10 || var2 >= 3 || par1EntityPlayer.getHealth() > 1 && var2 >= 2)
                {
                    par1EntityPlayer.damageEntity(DamageSource.STARVE, 1);
                }

                this.foodTickTimer = 0;
            }
        }
        else
        {
            this.foodTickTimer = 0;
        }
    }

    /**
     * Reads the food data for the player.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        if (par1NBTTagCompound.hasKey("foodLevel"))
        {
            this.foodLevel = par1NBTTagCompound.getInt("foodLevel");
            this.foodTickTimer = par1NBTTagCompound.getInt("foodTickTimer");
            this.saturationLevel = par1NBTTagCompound.getFloat("foodSaturationLevel");
            this.exhaustionLevel = par1NBTTagCompound.getFloat("foodExhaustionLevel");
        }
    }

    /**
     * Writes the food data for the player.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setInt("foodLevel", this.foodLevel);
        par1NBTTagCompound.setInt("foodTickTimer", this.foodTickTimer);
        par1NBTTagCompound.setFloat("foodSaturationLevel", this.saturationLevel);
        par1NBTTagCompound.setFloat("foodExhaustionLevel", this.exhaustionLevel);
    }

    /**
     * Get the player's food level.
     */
    public int a()
    {
        return this.foodLevel;
    }

    /**
     * Get whether the player must eat food.
     */
    public boolean c()
    {
        return this.foodLevel < 20;
    }

    /**
     * adds input to foodExhaustionLevel to a max of 40
     */
    public void a(float par1)
    {
        this.exhaustionLevel = Math.min(this.exhaustionLevel + par1, 40.0F);
    }

    /**
     * Get the player's food saturation level.
     */
    public float e()
    {
        return this.saturationLevel;
    }
}
