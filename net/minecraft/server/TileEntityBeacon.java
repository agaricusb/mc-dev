package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class TileEntityBeacon extends TileEntity implements IInventory
{
    /** List of effects that Beacon can apply */
    public static final MobEffectList[][] a = new MobEffectList[][] {{MobEffectList.FASTER_MOVEMENT, MobEffectList.FASTER_DIG}, {MobEffectList.RESISTANCE, MobEffectList.JUMP}, {MobEffectList.INCREASE_DAMAGE}, {MobEffectList.REGENERATION}};
    private boolean d;

    /** Level of this beacon's pyramid. */
    private int e = -1;

    /** Primary potion effect given by this beacon. */
    private int f;

    /** Secondary potion effect given by this beacon. */
    private int g;

    /** Item given to this beacon as payment. */
    private ItemStack h;

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void g()
    {
        if (this.world.getTime() % 80L == 0L)
        {
            this.v();
            this.u();
        }
    }

    private void u()
    {
        if (this.d && this.e > 0 && !this.world.isStatic && this.f > 0)
        {
            double var1 = (double)(this.e * 8 + 8);
            byte var3 = 0;

            if (this.e >= 4 && this.f == this.g)
            {
                var3 = 1;
            }

            AxisAlignedBB var4 = AxisAlignedBB.a().a((double) this.x, (double) this.y, (double) this.z, (double) (this.x + 1), (double) (this.y + 1), (double) (this.z + 1)).grow(var1, var1, var1);
            List var5 = this.world.a(EntityHuman.class, var4);
            Iterator var6 = var5.iterator();
            EntityHuman var7;

            while (var6.hasNext())
            {
                var7 = (EntityHuman)var6.next();
                var7.addEffect(new MobEffect(this.f, 180, var3, true));
            }

            if (this.e >= 4 && this.f != this.g && this.g > 0)
            {
                var6 = var5.iterator();

                while (var6.hasNext())
                {
                    var7 = (EntityHuman)var6.next();
                    var7.addEffect(new MobEffect(this.g, 180, 0, true));
                }
            }
        }
    }

    private void v()
    {
        if (!this.world.k(this.x, this.y + 1, this.z))
        {
            this.d = false;
            this.e = 0;
        }
        else
        {
            this.d = true;
            this.e = 0;

            for (int var1 = 1; var1 <= 4; this.e = var1++)
            {
                int var2 = this.y - var1;

                if (var2 < 0)
                {
                    break;
                }

                boolean var3 = true;

                for (int var4 = this.x - var1; var4 <= this.x + var1 && var3; ++var4)
                {
                    for (int var5 = this.z - var1; var5 <= this.z + var1; ++var5)
                    {
                        int var6 = this.world.getTypeId(var4, var2, var5);

                        if (var6 != Block.EMERALD_BLOCK.id && var6 != Block.GOLD_BLOCK.id && var6 != Block.DIAMOND_BLOCK.id && var6 != Block.IRON_BLOCK.id)
                        {
                            var3 = false;
                            break;
                        }
                    }
                }

                if (!var3)
                {
                    break;
                }
            }

            if (this.e == 0)
            {
                this.d = false;
            }
        }
    }

    /**
     * Return the primary potion effect given by this beacon.
     */
    public int i()
    {
        return this.f;
    }

    /**
     * Return the secondary potion effect given by this beacon.
     */
    public int j()
    {
        return this.g;
    }

    /**
     * Return the levels of this beacon's pyramid.
     */
    public int k()
    {
        return this.e;
    }

    public void d(int par1)
    {
        this.f = 0;

        for (int var2 = 0; var2 < this.e && var2 < 3; ++var2)
        {
            MobEffectList[] var3 = a[var2];
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5)
            {
                MobEffectList var6 = var3[var5];

                if (var6.id == par1)
                {
                    this.f = par1;
                    return;
                }
            }
        }
    }

    public void e(int par1)
    {
        this.g = 0;

        if (this.e >= 4)
        {
            for (int var2 = 0; var2 < 4; ++var2)
            {
                MobEffectList[] var3 = a[var2];
                int var4 = var3.length;

                for (int var5 = 0; var5 < var4; ++var5)
                {
                    MobEffectList var6 = var3[var5];

                    if (var6.id == par1)
                    {
                        this.g = par1;
                        return;
                    }
                }
            }
        }
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getUpdatePacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.b(var1);
        return new Packet132TileEntityData(this.x, this.y, this.z, 3, var1);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.f = par1NBTTagCompound.getInt("Primary");
        this.g = par1NBTTagCompound.getInt("Secondary");
        this.e = par1NBTTagCompound.getInt("Levels");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setInt("Primary", this.f);
        par1NBTTagCompound.setInt("Secondary", this.g);
        par1NBTTagCompound.setInt("Levels", this.e);
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return 1;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int par1)
    {
        return par1 == 0 ? this.h : null;
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack splitStack(int par1, int par2)
    {
        if (par1 == 0 && this.h != null)
        {
            if (par2 >= this.h.count)
            {
                ItemStack var3 = this.h;
                this.h = null;
                return var3;
            }
            else
            {
                this.h.count -= par2;
                return new ItemStack(this.h.id, par2, this.h.getData());
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack splitWithoutUpdate(int par1)
    {
        if (par1 == 0 && this.h != null)
        {
            ItemStack var2 = this.h;
            this.h = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int par1, ItemStack par2ItemStack)
    {
        if (par1 == 0)
        {
            this.h = par2ItemStack;
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return "container.beacon";
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getMaxStackSize()
    {
        return 1;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean a_(EntityHuman par1EntityPlayer)
    {
        return this.world.getTileEntity(this.x, this.y, this.z) != this ? false : par1EntityPlayer.e((double) this.x + 0.5D, (double) this.y + 0.5D, (double) this.z + 0.5D) <= 64.0D;
    }

    public void startOpen() {}

    public void f() {}
}
