package net.minecraft.server;

import java.util.HashMap;
import java.util.Map;

public class TileEntity
{
    /**
     * A HashMap storing string names of classes mapping to the actual java.lang.Class type.
     */
    private static Map a = new HashMap();

    /**
     * A HashMap storing the classes and mapping to the string names (reverse of nameToClassMap).
     */
    private static Map b = new HashMap();

    /** The reference to the world. */
    protected World world;

    /** The x coordinate of the tile entity. */
    public int x;

    /** The y coordinate of the tile entity. */
    public int y;

    /** The z coordinate of the tile entity. */
    public int z;
    protected boolean o;
    public int p = -1;

    /** the Block type that this TileEntity is contained within */
    public Block q;

    /**
     * Adds a new two-way mapping between the class and its string name in both hashmaps.
     */
    private static void a(Class par0Class, String par1Str)
    {
        if (a.containsKey(par1Str))
        {
            throw new IllegalArgumentException("Duplicate id: " + par1Str);
        }
        else
        {
            a.put(par1Str, par0Class);
            b.put(par0Class, par1Str);
        }
    }

    /**
     * Sets the worldObj for this tileEntity.
     */
    public void b(World par1World)
    {
        this.world = par1World;
    }

    public boolean o()
    {
        return this.world != null;
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        this.x = par1NBTTagCompound.getInt("x");
        this.y = par1NBTTagCompound.getInt("y");
        this.z = par1NBTTagCompound.getInt("z");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        String var2 = (String) b.get(this.getClass());

        if (var2 == null)
        {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        else
        {
            par1NBTTagCompound.setString("id", var2);
            par1NBTTagCompound.setInt("x", this.x);
            par1NBTTagCompound.setInt("y", this.y);
            par1NBTTagCompound.setInt("z", this.z);
        }
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void g() {}

    /**
     * Creates a new entity and loads its data from the specified NBT.
     */
    public static TileEntity c(NBTTagCompound par0NBTTagCompound)
    {
        TileEntity var1 = null;

        try
        {
            Class var2 = (Class) a.get(par0NBTTagCompound.getString("id"));

            if (var2 != null)
            {
                var1 = (TileEntity)var2.newInstance();
            }
        }
        catch (Exception var3)
        {
            var3.printStackTrace();
        }

        if (var1 != null)
        {
            var1.a(par0NBTTagCompound);
        }
        else
        {
            System.out.println("Skipping TileEntity with id " + par0NBTTagCompound.getString("id"));
        }

        return var1;
    }

    /**
     * Returns block data at the location of this entity (client-only).
     */
    public int p()
    {
        if (this.p == -1)
        {
            this.p = this.world.getData(this.x, this.y, this.z);
        }

        return this.p;
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void update()
    {
        if (this.world != null)
        {
            this.p = this.world.getData(this.x, this.y, this.z);
            this.world.b(this.x, this.y, this.z, this);
        }
    }

    /**
     * Gets the block type at the location of this entity (client-only).
     */
    public Block q()
    {
        if (this.q == null)
        {
            this.q = Block.byId[this.world.getTypeId(this.x, this.y, this.z)];
        }

        return this.q;
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getUpdatePacket()
    {
        return null;
    }

    /**
     * returns true if tile entity is invalid, false otherwise
     */
    public boolean r()
    {
        return this.o;
    }

    /**
     * invalidates a tile entity
     */
    public void w_()
    {
        this.o = true;
    }

    /**
     * validates a tile entity
     */
    public void s()
    {
        this.o = false;
    }

    /**
     * Called when a client event is received with the event number and argument, see World.sendClientEvent
     */
    public void b(int par1, int par2) {}

    /**
     * Causes the TileEntity to reset all it's cached values for it's container block, blockID, metaData and in the case
     * of chests, the adjcacent chest check
     */
    public void h()
    {
        this.q = null;
        this.p = -1;
    }

    public void a(CrashReportSystemDetails par1CrashReportCategory)
    {
        par1CrashReportCategory.a("Name", new CrashReportTileEntityName(this));
        CrashReportSystemDetails.a(par1CrashReportCategory, this.x, this.y, this.z, this.q.id, this.p);
    }

    static Map t()
    {
        return b;
    }

    static
    {
        a(TileEntityFurnace.class, "Furnace");
        a(TileEntityChest.class, "Chest");
        a(TileEntityEnderChest.class, "EnderChest");
        a(TileEntityRecordPlayer.class, "RecordPlayer");
        a(TileEntityDispenser.class, "Trap");
        a(TileEntitySign.class, "Sign");
        a(TileEntityMobSpawner.class, "MobSpawner");
        a(TileEntityNote.class, "Music");
        a(TileEntityPiston.class, "Piston");
        a(TileEntityBrewingStand.class, "Cauldron");
        a(TileEntityEnchantTable.class, "EnchantTable");
        a(TileEntityEnderPortal.class, "Airportal");
        a(TileEntityCommand.class, "Control");
        a(TileEntityBeacon.class, "Beacon");
        a(TileEntitySkull.class, "Skull");
    }
}
