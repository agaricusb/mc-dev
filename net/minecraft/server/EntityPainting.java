package net.minecraft.server;

import java.util.ArrayList;

public class EntityPainting extends EntityHanging
{
    public EnumArt art;

    public EntityPainting(World par1World)
    {
        super(par1World);
    }

    public EntityPainting(World par1World, int par2, int par3, int par4, int par5)
    {
        super(par1World, par2, par3, par4, par5);
        ArrayList var6 = new ArrayList();
        EnumArt[] var7 = EnumArt.values();
        int var8 = var7.length;

        for (int var9 = 0; var9 < var8; ++var9)
        {
            EnumArt var10 = var7[var9];
            this.art = var10;
            this.setDirection(par5);

            if (this.survives())
            {
                var6.add(var10);
            }
        }

        if (!var6.isEmpty())
        {
            this.art = (EnumArt)var6.get(this.random.nextInt(var6.size()));
        }

        this.setDirection(par5);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setString("Motive", this.art.B);
        super.b(par1NBTTagCompound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        String var2 = par1NBTTagCompound.getString("Motive");
        EnumArt[] var3 = EnumArt.values();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            EnumArt var6 = var3[var5];

            if (var6.B.equals(var2))
            {
                this.art = var6;
            }
        }

        if (this.art == null)
        {
            this.art = EnumArt.KEBAB;
        }

        super.a(par1NBTTagCompound);
    }

    public int d()
    {
        return this.art.C;
    }

    public int g()
    {
        return this.art.D;
    }

    /**
     * Drop the item currently on this item frame.
     */
    public void h()
    {
        this.a(new ItemStack(Item.PAINTING), 0.0F);
    }
}
