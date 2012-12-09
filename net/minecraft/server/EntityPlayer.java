package net.minecraft.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EntityPlayer extends EntityHuman implements ICrafting
{
    private LocaleLanguage locale = new LocaleLanguage("en_US");

    /**
     * The NetServerHandler assigned to this player by the ServerConfigurationManager.
     */
    public NetServerHandler netServerHandler;

    /** Reference to the MinecraftServer object. */
    public MinecraftServer server;

    /** The ItemInWorldManager belonging to this player */
    public ItemInWorldManager itemInWorldManager;

    /** player X position as seen by PlayerManager */
    public double d;

    /** player Z position as seen by PlayerManager */
    public double e;

    /** LinkedList that holds the loaded chunks. */
    public final List chunkCoordIntPairQueue = new LinkedList();

    /** entities added to this list will  be packet29'd to the player */
    public final List removeQueue = new LinkedList();

    /** amount of health the client was last set to */
    private int ck = -99999999;

    /** set to foodStats.GetFoodLevel */
    private int cl = -99999999;

    /** set to foodStats.getSaturationLevel() == 0.0F each tick */
    private boolean cm = true;

    /** Amount of experience the client was last set to */
    private int lastSentExp = -99999999;

    /** how many ticks of invulnerability(spawn protection) this player has */
    private int invulnerableTicks = 60;

    /** must be between 3>x>15 (strictly between) */
    private int cp = 0;
    private int cq = 0;
    private boolean cr = true;

    /**
     * The currently in use window ID. Incremented every time a window is opened.
     */
    private int containerCounter = 0;

    /**
     * set to true when player is moving quantity of items from one inventory to another(crafting) but item in either
     * slot is not changed
     */
    public boolean h;
    public int ping;

    /**
     * Set when a player beats the ender dragon, used to respawn the player at the spawn point while retaining inventory
     * and XP
     */
    public boolean viewingCredits = false;

    public EntityPlayer(MinecraftServer par1MinecraftServer, World par2World, String par3Str, ItemInWorldManager par4ItemInWorldManager)
    {
        super(par2World);
        par4ItemInWorldManager.player = this;
        this.itemInWorldManager = par4ItemInWorldManager;
        this.cp = par1MinecraftServer.getServerConfigurationManager().o();
        ChunkCoordinates var5 = par2World.getSpawn();
        int var6 = var5.x;
        int var7 = var5.z;
        int var8 = var5.y;

        if (!par2World.worldProvider.f && par2World.getWorldData().getGameType() != EnumGamemode.ADVENTURE)
        {
            int var9 = Math.max(5, par1MinecraftServer.getSpawnProtection() - 6);
            var6 += this.random.nextInt(var9 * 2) - var9;
            var7 += this.random.nextInt(var9 * 2) - var9;
            var8 = par2World.i(var6, var7);
        }

        this.setPositionRotation((double) var6 + 0.5D, (double) var8, (double) var7 + 0.5D, 0.0F, 0.0F);
        this.server = par1MinecraftServer;
        this.X = 0.0F;
        this.name = par3Str;
        this.height = 0.0F;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("playerGameType"))
        {
            this.itemInWorldManager.setGameMode(EnumGamemode.a(par1NBTTagCompound.getInt("playerGameType")));
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setInt("playerGameType", this.itemInWorldManager.getGameMode().a());
    }

    /**
     * Add experience levels to this player.
     */
    public void levelDown(int par1)
    {
        super.levelDown(par1);
        this.lastSentExp = -1;
    }

    public void syncInventory()
    {
        this.activeContainer.addSlotListener(this);
    }

    /**
     * sets the players height back to normal after doing things like sleeping and dieing
     */
    protected void e_()
    {
        this.height = 0.0F;
    }

    public float getHeadHeight()
    {
        return 1.62F;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        this.itemInWorldManager.a();
        --this.invulnerableTicks;
        this.activeContainer.b();

        while (!this.removeQueue.isEmpty())
        {
            int var1 = Math.min(this.removeQueue.size(), 127);
            int[] var2 = new int[var1];
            Iterator var3 = this.removeQueue.iterator();
            int var4 = 0;

            while (var3.hasNext() && var4 < var1)
            {
                var2[var4++] = ((Integer)var3.next()).intValue();
                var3.remove();
            }

            this.netServerHandler.sendPacket(new Packet29DestroyEntity(var2));
        }

        if (!this.chunkCoordIntPairQueue.isEmpty())
        {
            ArrayList var6 = new ArrayList();
            Iterator var7 = this.chunkCoordIntPairQueue.iterator();
            ArrayList var8 = new ArrayList();

            while (var7.hasNext() && var6.size() < 5)
            {
                ChunkCoordIntPair var9 = (ChunkCoordIntPair)var7.next();
                var7.remove();

                if (var9 != null && this.world.isLoaded(var9.x << 4, 0, var9.z << 4))
                {
                    var6.add(this.world.getChunkAt(var9.x, var9.z));
                    var8.addAll(((WorldServer)this.world).getTileEntities(var9.x * 16, 0, var9.z * 16, var9.x * 16 + 16, 256, var9.z * 16 + 16));
                }
            }

            if (!var6.isEmpty())
            {
                this.netServerHandler.sendPacket(new Packet56MapChunkBulk(var6));
                Iterator var11 = var8.iterator();

                while (var11.hasNext())
                {
                    TileEntity var5 = (TileEntity)var11.next();
                    this.b(var5);
                }

                var11 = var6.iterator();

                while (var11.hasNext())
                {
                    Chunk var10 = (Chunk)var11.next();
                    this.p().getTracker().a(this, var10);
                }
            }
        }
    }

    public void g()
    {
        super.j_();

        for (int var1 = 0; var1 < this.inventory.getSize(); ++var1)
        {
            ItemStack var2 = this.inventory.getItem(var1);

            if (var2 != null && Item.byId[var2.id].f() && this.netServerHandler.lowPriorityCount() <= 5)
            {
                Packet var3 = ((ItemWorldMapBase) Item.byId[var2.id]).c(var2, this.world, this);

                if (var3 != null)
                {
                    this.netServerHandler.sendPacket(var3);
                }
            }
        }

        if (this.getHealth() != this.ck || this.cl != this.foodData.a() || this.foodData.e() == 0.0F != this.cm)
        {
            this.netServerHandler.sendPacket(new Packet8UpdateHealth(this.getHealth(), this.foodData.a(), this.foodData.e()));
            this.ck = this.getHealth();
            this.cl = this.foodData.a();
            this.cm = this.foodData.e() == 0.0F;
        }

        if (this.expTotal != this.lastSentExp)
        {
            this.lastSentExp = this.expTotal;
            this.netServerHandler.sendPacket(new Packet43SetExperience(this.exp, this.expTotal, this.expLevel));
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void die(DamageSource par1DamageSource)
    {
        this.server.getServerConfigurationManager().sendAll(new Packet3Chat(par1DamageSource.getLocalizedDeathMessage(this)));

        if (!this.world.getGameRules().getBoolean("keepInventory"))
        {
            this.inventory.l();
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean damageEntity(DamageSource par1DamageSource, int par2)
    {
        if (this.isInvulnerable())
        {
            return false;
        }
        else
        {
            boolean var3 = this.server.T() && this.server.getPvP() && "fall".equals(par1DamageSource.translationIndex);

            if (!var3 && this.invulnerableTicks > 0)
            {
                return false;
            }
            else
            {
                if (!this.server.getPvP() && par1DamageSource instanceof EntityDamageSource)
                {
                    Entity var4 = par1DamageSource.getEntity();

                    if (var4 instanceof EntityHuman)
                    {
                        return false;
                    }

                    if (var4 instanceof EntityArrow)
                    {
                        EntityArrow var5 = (EntityArrow)var4;

                        if (var5.shooter instanceof EntityHuman)
                        {
                            return false;
                        }
                    }
                }

                return super.damageEntity(par1DamageSource, par2); // attackEntityFrom
            }
        }
    }

    /**
     * returns if pvp is enabled or not
     */
    protected boolean h()
    {
        return this.server.getPvP();
    }

    public void b(int par1)
    {
        if (this.dimension == 1 && par1 == 1)
        {
            this.a(AchievementList.C);
            this.world.kill(this);
            this.viewingCredits = true;
            this.netServerHandler.sendPacket(new Packet70Bed(4, 0));
        }
        else
        {
            if (this.dimension == 1 && par1 == 0)
            {
                this.a(AchievementList.B);
                ChunkCoordinates var2 = this.server.getWorldServer(par1).getDimensionSpawn();

                if (var2 != null)
                {
                    this.netServerHandler.a((double) var2.x, (double) var2.y, (double) var2.z, 0.0F, 0.0F);
                }

                par1 = 1;
            }
            else
            {
                this.a(AchievementList.x);
            }

            this.server.getServerConfigurationManager().changeDimension(this, par1);
            this.lastSentExp = -1;
            this.ck = -1;
            this.cl = -1;
        }
    }

    /**
     * gets description packets from all TileEntity's that override func_20070
     */
    private void b(TileEntity par1TileEntity)
    {
        if (par1TileEntity != null)
        {
            Packet var2 = par1TileEntity.getUpdatePacket();

            if (var2 != null)
            {
                this.netServerHandler.sendPacket(var2);
            }
        }
    }

    /**
     * Called whenever an item is picked up from walking over it. Args: pickedUpEntity, stackSize
     */
    public void receive(Entity par1Entity, int par2)
    {
        super.receive(par1Entity, par2);
        this.activeContainer.b();
    }

    /**
     * puts player to sleep on specified bed if possible
     */
    public EnumBedResult a(int par1, int par2, int par3)
    {
        EnumBedResult var4 = super.a(par1, par2, par3);

        if (var4 == EnumBedResult.OK)
        {
            Packet17EntityLocationAction var5 = new Packet17EntityLocationAction(this, 0, par1, par2, par3);
            this.p().getTracker().a(this, var5);
            this.netServerHandler.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            this.netServerHandler.sendPacket(var5);
        }

        return var4;
    }

    /**
     * Wake up the player if they're sleeping.
     */
    public void a(boolean par1, boolean par2, boolean par3)
    {
        if (this.isSleeping())
        {
            this.p().getTracker().sendPacketToEntity(this, new Packet18ArmAnimation(this, 3));
        }

        super.a(par1, par2, par3);

        if (this.netServerHandler != null)
        {
            this.netServerHandler.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
        }
    }

    /**
     * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
     */
    public void mount(Entity par1Entity)
    {
        super.mount(par1Entity);
        this.netServerHandler.sendPacket(new Packet39AttachEntity(this, this.vehicle));
        this.netServerHandler.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
    }

    /**
     * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
     * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
     */
    protected void a(double par1, boolean par3) {}

    /**
     * process player falling based on movement packet
     */
    public void b(double par1, boolean par3)
    {
        super.a(par1, par3);
    }

    /**
     * get the next window id to use
     */
    private void nextContainerCounter()
    {
        this.containerCounter = this.containerCounter % 100 + 1;
    }

    /**
     * Displays the crafting GUI for a workbench.
     */
    public void startCrafting(int par1, int par2, int par3)
    {
        this.nextContainerCounter();
        this.netServerHandler.sendPacket(new Packet100OpenWindow(this.containerCounter, 1, "Crafting", 9));
        this.activeContainer = new ContainerWorkbench(this.inventory, this.world, par1, par2, par3);
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }

    public void startEnchanting(int par1, int par2, int par3)
    {
        this.nextContainerCounter();
        this.netServerHandler.sendPacket(new Packet100OpenWindow(this.containerCounter, 4, "Enchanting", 9));
        this.activeContainer = new ContainerEnchantTable(this.inventory, this.world, par1, par2, par3);
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }

    /**
     * Displays the GUI for interacting with an anvil.
     */
    public void openAnvil(int par1, int par2, int par3)
    {
        this.nextContainerCounter();
        this.netServerHandler.sendPacket(new Packet100OpenWindow(this.containerCounter, 8, "Repairing", 9));
        this.activeContainer = new ContainerAnvil(this.inventory, this.world, par1, par2, par3, this);
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }

    /**
     * Displays the GUI for interacting with a chest inventory. Args: chestInventory
     */
    public void openContainer(IInventory par1IInventory)
    {
        if (this.activeContainer != this.defaultContainer)
        {
            this.closeInventory();
        }

        this.nextContainerCounter();
        this.netServerHandler.sendPacket(new Packet100OpenWindow(this.containerCounter, 0, par1IInventory.getName(), par1IInventory.getSize()));
        this.activeContainer = new ContainerChest(this.inventory, par1IInventory);
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }

    /**
     * Displays the furnace GUI for the passed in furnace entity. Args: tileEntityFurnace
     */
    public void openFurnace(TileEntityFurnace par1TileEntityFurnace)
    {
        this.nextContainerCounter();
        this.netServerHandler.sendPacket(new Packet100OpenWindow(this.containerCounter, 2, par1TileEntityFurnace.getName(), par1TileEntityFurnace.getSize()));
        this.activeContainer = new ContainerFurnace(this.inventory, par1TileEntityFurnace);
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }

    /**
     * Displays the dipsenser GUI for the passed in dispenser entity. Args: TileEntityDispenser
     */
    public void openDispenser(TileEntityDispenser par1TileEntityDispenser)
    {
        this.nextContainerCounter();
        this.netServerHandler.sendPacket(new Packet100OpenWindow(this.containerCounter, 3, par1TileEntityDispenser.getName(), par1TileEntityDispenser.getSize()));
        this.activeContainer = new ContainerDispenser(this.inventory, par1TileEntityDispenser);
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }

    /**
     * Displays the GUI for interacting with a brewing stand.
     */
    public void openBrewingStand(TileEntityBrewingStand par1TileEntityBrewingStand)
    {
        this.nextContainerCounter();
        this.netServerHandler.sendPacket(new Packet100OpenWindow(this.containerCounter, 5, par1TileEntityBrewingStand.getName(), par1TileEntityBrewingStand.getSize()));
        this.activeContainer = new ContainerBrewingStand(this.inventory, par1TileEntityBrewingStand);
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }

    /**
     * Displays the GUI for interacting with a beacon.
     */
    public void openBeacon(TileEntityBeacon par1TileEntityBeacon)
    {
        this.nextContainerCounter();
        this.netServerHandler.sendPacket(new Packet100OpenWindow(this.containerCounter, 7, par1TileEntityBeacon.getName(), par1TileEntityBeacon.getSize()));
        this.activeContainer = new ContainerBeacon(this.inventory, par1TileEntityBeacon);
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
    }

    public void openTrade(IMerchant par1IMerchant)
    {
        this.nextContainerCounter();
        this.activeContainer = new ContainerMerchant(this.inventory, par1IMerchant, this.world);
        this.activeContainer.windowId = this.containerCounter;
        this.activeContainer.addSlotListener(this);
        InventoryMerchant var2 = ((ContainerMerchant)this.activeContainer).getMerchantInventory();
        this.netServerHandler.sendPacket(new Packet100OpenWindow(this.containerCounter, 6, var2.getName(), var2.getSize()));
        MerchantRecipeList var3 = par1IMerchant.getOffers(this);

        if (var3 != null)
        {
            try
            {
                ByteArrayOutputStream var4 = new ByteArrayOutputStream();
                DataOutputStream var5 = new DataOutputStream(var4);
                var5.writeInt(this.containerCounter);
                var3.a(var5);
                this.netServerHandler.sendPacket(new Packet250CustomPayload("MC|TrList", var4.toByteArray()));
            }
            catch (IOException var6)
            {
                var6.printStackTrace();
            }
        }
    }

    /**
     * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
     * contents of that slot. Args: Container, slot number, slot contents
     */
    public void a(Container par1Container, int par2, ItemStack par3ItemStack)
    {
        if (!(par1Container.getSlot(par2) instanceof SlotResult))
        {
            if (!this.h)
            {
                this.netServerHandler.sendPacket(new Packet103SetSlot(par1Container.windowId, par2, par3ItemStack));
            }
        }
    }

    public void updateInventory(Container par1Container)
    {
        this.a(par1Container, par1Container.a());
    }

    /**
     * update the crafting window inventory with the items in the list
     */
    public void a(Container par1Container, List par2List)
    {
        this.netServerHandler.sendPacket(new Packet104WindowItems(par1Container.windowId, par2List));
        this.netServerHandler.sendPacket(new Packet103SetSlot(-1, -1, this.inventory.getCarried()));
    }

    /**
     * Sends two ints to the client-side Container. Used for furnace burning time, smelting progress, brewing progress,
     * and enchanting level. Normally the first int identifies which variable to update, and the second contains the new
     * value. Both are truncated to shorts in non-local SMP.
     */
    public void setContainerData(Container par1Container, int par2, int par3)
    {
        this.netServerHandler.sendPacket(new Packet105CraftProgressBar(par1Container.windowId, par2, par3));
    }

    /**
     * set current crafting inventory back to the 2x2 square
     */
    public void closeInventory()
    {
        this.netServerHandler.sendPacket(new Packet101CloseWindow(this.activeContainer.windowId));
        this.k();
    }

    /**
     * updates item held by mouse
     */
    public void broadcastCarriedItem()
    {
        if (!this.h)
        {
            this.netServerHandler.sendPacket(new Packet103SetSlot(-1, -1, this.inventory.getCarried()));
        }
    }

    /**
     * close the current crafting gui
     */
    public void k()
    {
        this.activeContainer.b(this);
        this.activeContainer = this.defaultContainer;
    }

    /**
     * Adds a value to a statistic field.
     */
    public void a(Statistic par1StatBase, int par2)
    {
        if (par1StatBase != null)
        {
            if (!par1StatBase.f)
            {
                while (par2 > 100)
                {
                    this.netServerHandler.sendPacket(new Packet200Statistic(par1StatBase.e, 100));
                    par2 -= 100;
                }

                this.netServerHandler.sendPacket(new Packet200Statistic(par1StatBase.e, par2));
            }
        }
    }

    public void l()
    {
        if (this.vehicle != null)
        {
            this.mount(this.vehicle);
        }

        if (this.passenger != null)
        {
            this.passenger.mount(this);
        }

        if (this.sleeping)
        {
            this.a(true, false, false);
        }
    }

    /**
     * this function is called when a players inventory is sent to him, lastHealth is updated on any dimension
     * transitions, then reset.
     */
    public void m()
    {
        this.ck = -99999999;
    }

    /**
     * Add a chat message to the player
     */
    public void b(String par1Str)
    {
        LocaleLanguage var2 = LocaleLanguage.a();
        String var3 = var2.b(par1Str);
        this.netServerHandler.sendPacket(new Packet3Chat(var3));
    }

    /**
     * Used for when item use count runs out, ie: eating completed
     */
    protected void n()
    {
        this.netServerHandler.sendPacket(new Packet38EntityStatus(this.id, (byte)9));
        super.n();
    }

    /**
     * sets the itemInUse when the use item button is clicked. Args: itemstack, int maxItemUseDuration
     */
    public void a(ItemStack par1ItemStack, int par2)
    {
        super.a(par1ItemStack, par2);

        if (par1ItemStack != null && par1ItemStack.getItem() != null && par1ItemStack.getItem().d_(par1ItemStack) == EnumAnimation.b)
        {
            this.p().getTracker().sendPacketToEntity(this, new Packet18ArmAnimation(this, 5));
        }
    }

    /**
     * Copies the values from the given player into this player if boolean par2 is true. Always clones Ender Chest
     * Inventory.
     */
    public void copyTo(EntityHuman par1EntityPlayer, boolean par2)
    {
        super.copyTo(par1EntityPlayer, par2);
        this.lastSentExp = -1;
        this.ck = -1;
        this.cl = -1;
        this.removeQueue.addAll(((EntityPlayer)par1EntityPlayer).removeQueue);
    }

    protected void a(MobEffect par1PotionEffect)
    {
        super.a(par1PotionEffect);
        this.netServerHandler.sendPacket(new Packet41MobEffect(this.id, par1PotionEffect));
    }

    protected void b(MobEffect par1PotionEffect)
    {
        super.b(par1PotionEffect);
        this.netServerHandler.sendPacket(new Packet41MobEffect(this.id, par1PotionEffect));
    }

    protected void c(MobEffect par1PotionEffect)
    {
        super.c(par1PotionEffect);
        this.netServerHandler.sendPacket(new Packet42RemoveMobEffect(this.id, par1PotionEffect));
    }

    /**
     * Sets the position of the entity and updates the 'last' variables
     */
    public void enderTeleportTo(double par1, double par3, double par5)
    {
        this.netServerHandler.a(par1, par3, par5, this.yaw, this.pitch);
    }

    /**
     * Called when the player performs a critical hit on the Entity. Args: entity that was hit critically
     */
    public void b(Entity par1Entity)
    {
        this.p().getTracker().sendPacketToEntity(this, new Packet18ArmAnimation(par1Entity, 6));
    }

    public void c(Entity par1Entity)
    {
        this.p().getTracker().sendPacketToEntity(this, new Packet18ArmAnimation(par1Entity, 7));
    }

    /**
     * Sends the player's abilities to the server (if there is one).
     */
    public void updateAbilities()
    {
        if (this.netServerHandler != null)
        {
            this.netServerHandler.sendPacket(new Packet202Abilities(this.abilities));
        }
    }

    public WorldServer p()
    {
        return (WorldServer)this.world;
    }

    /**
     * Sets the player's game type
     */
    public void a(EnumGamemode par1EnumGameType)
    {
        this.itemInWorldManager.setGameMode(par1EnumGameType);
        this.netServerHandler.sendPacket(new Packet70Bed(3, par1EnumGameType.a()));
    }

    public void sendMessage(String par1Str)
    {
        this.netServerHandler.sendPacket(new Packet3Chat(par1Str));
    }

    /**
     * Returns true if the command sender is allowed to use the given command.
     */
    public boolean a(int par1, String par2Str)
    {
        return "seed".equals(par2Str) && !this.server.T() ? true : (!"tell".equals(par2Str) && !"help".equals(par2Str) && !"me".equals(par2Str) ? this.server.getServerConfigurationManager().isOp(this.name) : true);
    }

    public String q()
    {
        String var1 = this.netServerHandler.networkManager.getSocketAddress().toString();
        var1 = var1.substring(var1.indexOf("/") + 1);
        var1 = var1.substring(0, var1.indexOf(":"));
        return var1;
    }

    public void a(Packet204LocaleAndViewDistance par1Packet204ClientInfo)
    {
        if (this.locale.b().containsKey(par1Packet204ClientInfo.d()))
        {
            this.locale.a(par1Packet204ClientInfo.d());
        }

        int var2 = 256 >> par1Packet204ClientInfo.f();

        if (var2 > 3 && var2 < 15)
        {
            this.cp = var2;
        }

        this.cq = par1Packet204ClientInfo.g();
        this.cr = par1Packet204ClientInfo.h();

        if (this.server.I() && this.server.H().equals(this.name))
        {
            this.server.c(par1Packet204ClientInfo.i());
        }

        this.b(1, !par1Packet204ClientInfo.j());
    }

    public LocaleLanguage getLocale()
    {
        return this.locale;
    }

    public int getChatFlags()
    {
        return this.cq;
    }

    /**
     * on recieving this message the client (if permission is given) will download the requested textures
     */
    public void a(String par1Str, int par2)
    {
        String var3 = par1Str + "\u0000" + par2;
        this.netServerHandler.sendPacket(new Packet250CustomPayload("MC|TPack", var3.getBytes()));
    }

    /**
     * Return the position for this command sender.
     */
    public ChunkCoordinates b()
    {
        return new ChunkCoordinates(MathHelper.floor(this.locX), MathHelper.floor(this.locY + 0.5D), MathHelper.floor(this.locZ));
    }
}
