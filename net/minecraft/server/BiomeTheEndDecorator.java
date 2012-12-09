package net.minecraft.server;

public class BiomeTheEndDecorator extends BiomeDecorator
{
    protected WorldGenerator L;

    public BiomeTheEndDecorator(BiomeBase par1BiomeGenBase)
    {
        super(par1BiomeGenBase);
        this.L = new WorldGenEnder(Block.WHITESTONE.id);
    }

    /**
     * The method that does the work of actually decorating chunks
     */
    protected void a()
    {
        this.b();

        if (this.b.nextInt(5) == 0)
        {
            int var1 = this.c + this.b.nextInt(16) + 8;
            int var2 = this.d + this.b.nextInt(16) + 8;
            int var3 = this.a.i(var1, var2);

            if (var3 > 0)
            {
                ;
            }

            this.L.a(this.a, this.b, var1, var3, var2);
        }

        if (this.c == 0 && this.d == 0)
        {
            EntityEnderDragon var4 = new EntityEnderDragon(this.a);
            var4.setPositionRotation(0.0D, 128.0D, 0.0D, this.b.nextFloat() * 360.0F, 0.0F);
            this.a.addEntity(var4);
        }
    }
}
