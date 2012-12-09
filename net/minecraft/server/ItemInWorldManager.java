package net.minecraft.server;

public class ItemInWorldManager
{
    /** The world object that this object is connected to. */
    public World world;

    /** The EntityPlayerMP object that this object is connected to. */
    public EntityPlayer player;
    private EnumGamemode gamemode;

    /** True if the player is destroying a block */
    private boolean d;
    private int lastDigTick;
    private int f;
    private int g;
    private int h;
    private int currentTick;

    /**
     * Set to true when the "finished destroying block" packet is received but the block wasn't fully damaged yet. The
     * block will not be destroyed while this is false.
     */
    private boolean j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;

    public ItemInWorldManager(World par1World)
    {
        this.gamemode = EnumGamemode.NONE;
        this.o = -1;
        this.world = par1World;
    }

    public void setGameMode(EnumGamemode par1EnumGameType)
    {
        this.gamemode = par1EnumGameType;
        par1EnumGameType.a(this.player.abilities);
        this.player.updateAbilities();
    }

    public EnumGamemode getGameMode()
    {
        return this.gamemode;
    }

    /**
     * Get if we are in creative game mode.
     */
    public boolean isCreative()
    {
        return this.gamemode.d();
    }

    /**
     * if the gameType is currently NOT_SET then change it to par1
     */
    public void b(EnumGamemode par1EnumGameType)
    {
        if (this.gamemode == EnumGamemode.NONE)
        {
            this.gamemode = par1EnumGameType;
        }

        this.setGameMode(this.gamemode);
    }

    public void a()
    {
        ++this.currentTick;
        int var1;
        float var4;
        int var5;

        if (this.j)
        {
            var1 = this.currentTick - this.n;
            int var2 = this.world.getTypeId(this.k, this.l, this.m);

            if (var2 == 0)
            {
                this.j = false;
            }
            else
            {
                Block var3 = Block.byId[var2];
                var4 = var3.getDamage(this.player, this.player.world, this.k, this.l, this.m) * (float)(var1 + 1);
                var5 = (int)(var4 * 10.0F);

                if (var5 != this.o)
                {
                    this.world.g(this.player.id, this.k, this.l, this.m, var5);
                    this.o = var5;
                }

                if (var4 >= 1.0F)
                {
                    this.j = false;
                    this.breakBlock(this.k, this.l, this.m);
                }
            }
        }
        else if (this.d)
        {
            var1 = this.world.getTypeId(this.f, this.g, this.h);
            Block var6 = Block.byId[var1];

            if (var6 == null)
            {
                this.world.g(this.player.id, this.f, this.g, this.h, -1);
                this.o = -1;
                this.d = false;
            }
            else
            {
                int var7 = this.currentTick - this.lastDigTick;
                var4 = var6.getDamage(this.player, this.player.world, this.f, this.g, this.h) * (float)(var7 + 1);
                var5 = (int)(var4 * 10.0F);

                if (var5 != this.o)
                {
                    this.world.g(this.player.id, this.f, this.g, this.h, var5);
                    this.o = var5;
                }
            }
        }
    }

    public void dig(int par1, int par2, int par3, int par4)
    {
        if (!this.gamemode.isAdventure() || this.player.f(par1, par2, par3))
        {
            if (this.isCreative())
            {
                if (!this.world.douseFire((EntityHuman) null, par1, par2, par3, par4))
                {
                    this.breakBlock(par1, par2, par3);
                }
            }
            else
            {
                this.world.douseFire(this.player, par1, par2, par3, par4);
                this.lastDigTick = this.currentTick;
                float var5 = 1.0F;
                int var6 = this.world.getTypeId(par1, par2, par3);

                if (var6 > 0)
                {
                    Block.byId[var6].attack(this.world, par1, par2, par3, this.player);
                    var5 = Block.byId[var6].getDamage(this.player, this.player.world, par1, par2, par3);
                }

                if (var6 > 0 && var5 >= 1.0F)
                {
                    this.breakBlock(par1, par2, par3);
                }
                else
                {
                    this.d = true;
                    this.f = par1;
                    this.g = par2;
                    this.h = par3;
                    int var7 = (int)(var5 * 10.0F);
                    this.world.g(this.player.id, par1, par2, par3, var7);
                    this.o = var7;
                }
            }
        }
    }

    public void a(int par1, int par2, int par3)
    {
        if (par1 == this.f && par2 == this.g && par3 == this.h)
        {
            int var4 = this.currentTick - this.lastDigTick;
            int var5 = this.world.getTypeId(par1, par2, par3);

            if (var5 != 0)
            {
                Block var6 = Block.byId[var5];
                float var7 = var6.getDamage(this.player, this.player.world, par1, par2, par3) * (float)(var4 + 1);

                if (var7 >= 0.7F)
                {
                    this.d = false;
                    this.world.g(this.player.id, par1, par2, par3, -1);
                    this.breakBlock(par1, par2, par3);
                }
                else if (!this.j)
                {
                    this.d = false;
                    this.j = true;
                    this.k = par1;
                    this.l = par2;
                    this.m = par3;
                    this.n = this.lastDigTick;
                }
            }
        }
    }

    /**
     * note: this ignores the pars passed in and continues to destroy the onClickedBlock
     */
    public void c(int par1, int par2, int par3)
    {
        this.d = false;
        this.world.g(this.player.id, this.f, this.g, this.h, -1);
    }

    /**
     * Removes a block and triggers the appropriate events
     */
    private boolean d(int par1, int par2, int par3)
    {
        Block var4 = Block.byId[this.world.getTypeId(par1, par2, par3)];
        int var5 = this.world.getData(par1, par2, par3);

        if (var4 != null)
        {
            var4.a(this.world, par1, par2, par3, var5, this.player);
        }

        boolean var6 = this.world.setTypeId(par1, par2, par3, 0);

        if (var4 != null && var6)
        {
            var4.postBreak(this.world, par1, par2, par3, var5);
        }

        return var6;
    }

    /**
     * Attempts to harvest a block at the given coordinate
     */
    public boolean breakBlock(int par1, int par2, int par3)
    {
        if (this.gamemode.isAdventure() && !this.player.f(par1, par2, par3))
        {
            return false;
        }
        else
        {
            int var4 = this.world.getTypeId(par1, par2, par3);
            int var5 = this.world.getData(par1, par2, par3);
            this.world.a(this.player, 2001, par1, par2, par3, var4 + (this.world.getData(par1, par2, par3) << 12));
            boolean var6 = this.d(par1, par2, par3);

            if (this.isCreative())
            {
                this.player.netServerHandler.sendPacket(new Packet53BlockChange(par1, par2, par3, this.world));
            }
            else
            {
                ItemStack var7 = this.player.bT();
                boolean var8 = this.player.b(Block.byId[var4]);

                if (var7 != null)
                {
                    var7.a(this.world, var4, par1, par2, par3, this.player);

                    if (var7.count == 0)
                    {
                        this.player.bU();
                    }
                }

                if (var6 && var8)
                {
                    Block.byId[var4].a(this.world, this.player, par1, par2, par3, var5);
                }
            }

            return var6;
        }
    }

    /**
     * Attempts to right-click use an item by the given EntityPlayer in the given World
     */
    public boolean useItem(EntityHuman par1EntityPlayer, World par2World, ItemStack par3ItemStack)
    {
        int var4 = par3ItemStack.count;
        int var5 = par3ItemStack.getData();
        ItemStack var6 = par3ItemStack.a(par2World, par1EntityPlayer);

        if (var6 == par3ItemStack && (var6 == null || var6.count == var4 && var6.m() <= 0 && var6.getData() == var5))
        {
            return false;
        }
        else
        {
            par1EntityPlayer.inventory.items[par1EntityPlayer.inventory.itemInHandIndex] = var6;

            if (this.isCreative())
            {
                var6.count = var4;

                if (var6.f())
                {
                    var6.setData(var5);
                }
            }

            if (var6.count == 0)
            {
                par1EntityPlayer.inventory.items[par1EntityPlayer.inventory.itemInHandIndex] = null;
            }

            if (!par1EntityPlayer.bM())
            {
                ((EntityPlayer)par1EntityPlayer).updateInventory(par1EntityPlayer.defaultContainer);
            }

            return true;
        }
    }

    /**
     * Activate the clicked on block, otherwise use the held item. Args: player, world, itemStack, x, y, z, side,
     * xOffset, yOffset, zOffset
     */
    public boolean interact(EntityHuman par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        int var11 = par2World.getTypeId(par4, par5, par6);

        if (var11 > 0 && Block.byId[var11].interact(par2World, par4, par5, par6, par1EntityPlayer, par7, par8, par9, par10))
        {
            return true;
        }
        else if (par3ItemStack == null)
        {
            return false;
        }
        else if (this.isCreative())
        {
            int var12 = par3ItemStack.getData();
            int var13 = par3ItemStack.count;
            boolean var14 = par3ItemStack.placeItem(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
            par3ItemStack.setData(var12);
            par3ItemStack.count = var13;
            return var14;
        }
        else
        {
            return par3ItemStack.placeItem(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
        }
    }

    /**
     * Sets the world instance.
     */
    public void a(WorldServer par1WorldServer)
    {
        this.world = par1WorldServer;
    }
}
