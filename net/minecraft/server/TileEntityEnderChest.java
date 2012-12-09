package net.minecraft.server;

public class TileEntityEnderChest extends TileEntity
{
    /** The current angle of the chest lid (between 0 and 1) */
    public float a;

    /** The angle of the chest lid last tick */
    public float b;

    /** The number of players currently using this ender chest. */
    public int c;

    /** Server sync counter (once per 20 ticks) */
    private int d;

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void g()
    {
        super.g();

        if (++this.d % 20 * 4 == 0)
        {
            this.world.playNote(this.x, this.y, this.z, Block.ENDER_CHEST.id, 1, this.c);
        }

        this.b = this.a;
        float var1 = 0.1F;
        double var4;

        if (this.c > 0 && this.a == 0.0F)
        {
            double var2 = (double)this.x + 0.5D;
            var4 = (double)this.z + 0.5D;
            this.world.makeSound(var2, (double) this.y + 0.5D, var4, "random.chestopen", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
        }

        if (this.c == 0 && this.a > 0.0F || this.c > 0 && this.a < 1.0F)
        {
            float var8 = this.a;

            if (this.c > 0)
            {
                this.a += var1;
            }
            else
            {
                this.a -= var1;
            }

            if (this.a > 1.0F)
            {
                this.a = 1.0F;
            }

            float var3 = 0.5F;

            if (this.a < var3 && var8 >= var3)
            {
                var4 = (double)this.x + 0.5D;
                double var6 = (double)this.z + 0.5D;
                this.world.makeSound(var4, (double) this.y + 0.5D, var6, "random.chestclosed", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
            }

            if (this.a < 0.0F)
            {
                this.a = 0.0F;
            }
        }
    }

    /**
     * Called when a client event is received with the event number and argument, see World.sendClientEvent
     */
    public void b(int par1, int par2)
    {
        if (par1 == 1)
        {
            this.c = par2;
        }
    }

    /**
     * invalidates a tile entity
     */
    public void w_()
    {
        this.h();
        super.w_();
    }

    public void a()
    {
        ++this.c;
        this.world.playNote(this.x, this.y, this.z, Block.ENDER_CHEST.id, 1, this.c);
    }

    public void b()
    {
        --this.c;
        this.world.playNote(this.x, this.y, this.z, Block.ENDER_CHEST.id, 1, this.c);
    }

    public boolean a(EntityHuman par1EntityPlayer)
    {
        return this.world.getTileEntity(this.x, this.y, this.z) != this ? false : par1EntityPlayer.e((double) this.x + 0.5D, (double) this.y + 0.5D, (double) this.z + 0.5D) <= 64.0D;
    }
}
