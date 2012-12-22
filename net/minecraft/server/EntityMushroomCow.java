package net.minecraft.server;

public class EntityMushroomCow extends EntityCow
{
    public EntityMushroomCow(World par1World)
    {
        super(par1World);
        this.texture = "/mob/redcow.png";
        this.a(0.9F, 1.3F);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean a(EntityHuman par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.getItemInHand();

        if (var2 != null && var2.id == Item.BOWL.id && this.getAge() >= 0)
        {
            if (var2.count == 1)
            {
                par1EntityPlayer.inventory.setItem(par1EntityPlayer.inventory.itemInHandIndex, new ItemStack(Item.MUSHROOM_SOUP));
                return true;
            }

            if (par1EntityPlayer.inventory.pickup(new ItemStack(Item.MUSHROOM_SOUP)) && !par1EntityPlayer.abilities.canInstantlyBuild)
            {
                par1EntityPlayer.inventory.splitStack(par1EntityPlayer.inventory.itemInHandIndex, 1);
                return true;
            }
        }

        if (var2 != null && var2.id == Item.SHEARS.id && this.getAge() >= 0)
        {
            this.die();
            this.world.addParticle("largeexplode", this.locX, this.locY + (double) (this.length / 2.0F), this.locZ, 0.0D, 0.0D, 0.0D);

            if (!this.world.isStatic)
            {
                EntityCow var3 = new EntityCow(this.world);
                var3.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
                var3.setHealth(this.getHealth());
                var3.ax = this.ax;
                this.world.addEntity(var3);

                for (int var4 = 0; var4 < 5; ++var4)
                {
                    this.world.addEntity(new EntityItem(this.world, this.locX, this.locY + (double) this.length, this.locZ, new ItemStack(Block.RED_MUSHROOM)));
                }
            }

            return true;
        }
        else
        {
            return super.a(par1EntityPlayer);
        }
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityMushroomCow c(EntityAgeable par1EntityAgeable)
    {
        return new EntityMushroomCow(this.world);
    }

    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        return this.b(par1EntityAgeable);
    }
}
