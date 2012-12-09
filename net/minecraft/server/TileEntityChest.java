package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class TileEntityChest extends TileEntity implements IInventory
{
    private ItemStack[] items = new ItemStack[36];

    /** Determines if the check for adjacent chests has taken place. */
    public boolean a = false;

    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityChest b;

    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityChest c;

    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityChest d;

    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityChest e;

    /** The current angle of the lid (between 0 and 1) */
    public float f;

    /** The angle of the lid last tick */
    public float g;

    /** The number of players currently using this chest */
    public int h;

    /** Server sync counter (once per 20 ticks) */
    private int ticks;

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return 27;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int par1)
    {
        return this.items[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack splitStack(int par1, int par2)
    {
        if (this.items[par1] != null)
        {
            ItemStack var3;

            if (this.items[par1].count <= par2)
            {
                var3 = this.items[par1];
                this.items[par1] = null;
                this.update();
                return var3;
            }
            else
            {
                var3 = this.items[par1].a(par2);

                if (this.items[par1].count == 0)
                {
                    this.items[par1] = null;
                }

                this.update();
                return var3;
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
        if (this.items[par1] != null)
        {
            ItemStack var2 = this.items[par1];
            this.items[par1] = null;
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
        this.items[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.count > this.getMaxStackSize())
        {
            par2ItemStack.count = this.getMaxStackSize();
        }

        this.update();
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return "container.chest";
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getList("Items");
        this.items = new ItemStack[this.getSize()];

        for (int var3 = 0; var3 < var2.size(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.items.length)
            {
                this.items[var5] = ItemStack.a(var4);
            }
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.items.length; ++var3)
        {
            if (this.items[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.items[var3].save(var4);
                var2.add(var4);
            }
        }

        par1NBTTagCompound.set("Items", var2);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getMaxStackSize()
    {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean a_(EntityHuman par1EntityPlayer)
    {
        return this.world.getTileEntity(this.x, this.y, this.z) != this ? false : par1EntityPlayer.e((double) this.x + 0.5D, (double) this.y + 0.5D, (double) this.z + 0.5D) <= 64.0D;
    }

    /**
     * Causes the TileEntity to reset all it's cached values for it's container block, blockID, metaData and in the case
     * of chests, the adjcacent chest check
     */
    public void h()
    {
        super.h();
        this.a = false;
    }

    private void a(TileEntityChest par1TileEntityChest, int par2)
    {
        if (par1TileEntityChest.r())
        {
            this.a = false;
        }
        else if (this.a)
        {
            switch (par2)
            {
                case 0:
                    if (this.e != par1TileEntityChest)
                    {
                        this.a = false;
                    }

                    break;

                case 1:
                    if (this.d != par1TileEntityChest)
                    {
                        this.a = false;
                    }

                    break;

                case 2:
                    if (this.b != par1TileEntityChest)
                    {
                        this.a = false;
                    }

                    break;

                case 3:
                    if (this.c != par1TileEntityChest)
                    {
                        this.a = false;
                    }
            }
        }
    }

    /**
     * Performs the check for adjacent chests to determine if this chest is double or not.
     */
    public void i()
    {
        if (!this.a)
        {
            this.a = true;
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = null;

            if (this.world.getTypeId(this.x - 1, this.y, this.z) == Block.CHEST.id)
            {
                this.d = (TileEntityChest)this.world.getTileEntity(this.x - 1, this.y, this.z);
            }

            if (this.world.getTypeId(this.x + 1, this.y, this.z) == Block.CHEST.id)
            {
                this.c = (TileEntityChest)this.world.getTileEntity(this.x + 1, this.y, this.z);
            }

            if (this.world.getTypeId(this.x, this.y, this.z - 1) == Block.CHEST.id)
            {
                this.b = (TileEntityChest)this.world.getTileEntity(this.x, this.y, this.z - 1);
            }

            if (this.world.getTypeId(this.x, this.y, this.z + 1) == Block.CHEST.id)
            {
                this.e = (TileEntityChest)this.world.getTileEntity(this.x, this.y, this.z + 1);
            }

            if (this.b != null)
            {
                this.b.a(this, 0);
            }

            if (this.e != null)
            {
                this.e.a(this, 2);
            }

            if (this.c != null)
            {
                this.c.a(this, 1);
            }

            if (this.d != null)
            {
                this.d.a(this, 3);
            }
        }
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void g()
    {
        super.g();
        this.i();
        ++this.ticks;
        float var1;

        if (!this.world.isStatic && this.h != 0 && (this.ticks + this.x + this.y + this.z) % 200 == 0)
        {
            this.h = 0;
            var1 = 5.0F;
            List var2 = this.world.a(EntityHuman.class, AxisAlignedBB.a().a((double) ((float) this.x - var1), (double) ((float) this.y - var1), (double) ((float) this.z - var1), (double) ((float) (this.x + 1) + var1), (double) ((float) (this.y + 1) + var1), (double) ((float) (this.z + 1) + var1)));
            Iterator var3 = var2.iterator();

            while (var3.hasNext())
            {
                EntityHuman var4 = (EntityHuman)var3.next();

                if (var4.activeContainer instanceof ContainerChest)
                {
                    IInventory var5 = ((ContainerChest)var4.activeContainer).d();

                    if (var5 == this || var5 instanceof InventoryLargeChest && ((InventoryLargeChest)var5).a(this))
                    {
                        ++this.h;
                    }
                }
            }
        }

        this.g = this.f;
        var1 = 0.1F;
        double var11;

        if (this.h > 0 && this.f == 0.0F && this.b == null && this.d == null)
        {
            double var8 = (double)this.x + 0.5D;
            var11 = (double)this.z + 0.5D;

            if (this.e != null)
            {
                var11 += 0.5D;
            }

            if (this.c != null)
            {
                var8 += 0.5D;
            }

            this.world.makeSound(var8, (double) this.y + 0.5D, var11, "random.chestopen", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
        }

        if (this.h == 0 && this.f > 0.0F || this.h > 0 && this.f < 1.0F)
        {
            float var9 = this.f;

            if (this.h > 0)
            {
                this.f += var1;
            }
            else
            {
                this.f -= var1;
            }

            if (this.f > 1.0F)
            {
                this.f = 1.0F;
            }

            float var10 = 0.5F;

            if (this.f < var10 && var9 >= var10 && this.b == null && this.d == null)
            {
                var11 = (double)this.x + 0.5D;
                double var6 = (double)this.z + 0.5D;

                if (this.e != null)
                {
                    var6 += 0.5D;
                }

                if (this.c != null)
                {
                    var11 += 0.5D;
                }

                this.world.makeSound(var11, (double) this.y + 0.5D, var6, "random.chestclosed", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
            }

            if (this.f < 0.0F)
            {
                this.f = 0.0F;
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
            this.h = par2;
        }
    }

    public void startOpen()
    {
        ++this.h;
        this.world.playNote(this.x, this.y, this.z, Block.CHEST.id, 1, this.h);
    }

    public void f()
    {
        --this.h;
        this.world.playNote(this.x, this.y, this.z, Block.CHEST.id, 1, this.h);
    }

    /**
     * invalidates a tile entity
     */
    public void w_()
    {
        super.w_();
        this.h();
        this.i();
    }
}
