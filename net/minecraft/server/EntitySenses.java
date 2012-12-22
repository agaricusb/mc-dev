package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

public class EntitySenses
{
    EntityLiving entity;

    /** Cache of entities which we can see */
    List seenEntities = new ArrayList();

    /** Cache of entities which we cannot see */
    List unseenEntities = new ArrayList();

    public EntitySenses(EntityLiving par1EntityLiving)
    {
        this.entity = par1EntityLiving;
    }

    /**
     * Clears canSeeCachePositive and canSeeCacheNegative.
     */
    public void a()
    {
        this.seenEntities.clear();
        this.unseenEntities.clear();
    }

    /**
     * Checks, whether 'our' entity can see the entity given as argument (true) or not (false), caching the result.
     */
    public boolean canSee(Entity par1Entity)
    {
        if (this.seenEntities.contains(par1Entity))
        {
            return true;
        }
        else if (this.unseenEntities.contains(par1Entity))
        {
            return false;
        }
        else
        {
            this.entity.world.methodProfiler.a("canSee");
            boolean var2 = this.entity.n(par1Entity);
            this.entity.world.methodProfiler.b();

            if (var2)
            {
                this.seenEntities.add(par1Entity);
            }
            else
            {
                this.unseenEntities.add(par1Entity);
            }

            return var2;
        }
    }
}
