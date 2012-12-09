package net.minecraft.server;

public class EntityCaveSpider extends EntitySpider
{
    public EntityCaveSpider(World par1World)
    {
        super(par1World);
        this.texture = "/mob/cavespider.png";
        this.a(0.7F, 0.5F);
    }

    public int getMaxHealth()
    {
        return 12;
    }

    public boolean m(Entity par1Entity)
    {
        if (super.m(par1Entity))
        {
            if (par1Entity instanceof EntityLiving)
            {
                byte var2 = 0;

                if (this.world.difficulty > 1)
                {
                    if (this.world.difficulty == 2)
                    {
                        var2 = 7;
                    }
                    else if (this.world.difficulty == 3)
                    {
                        var2 = 15;
                    }
                }

                if (var2 > 0)
                {
                    ((EntityLiving)par1Entity).addEffect(new MobEffect(MobEffectList.POISON.id, var2 * 20, 0));
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Initialize this creature.
     */
    public void bG() {}
}
