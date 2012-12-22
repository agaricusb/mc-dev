package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public abstract class EntityHuman extends EntityLiving implements ICommandListener
{
    /** Inventory of the player */
    public PlayerInventory inventory = new PlayerInventory(this);
    private InventoryEnderChest enderChest = new InventoryEnderChest();

    /**
     * The Container for the player's inventory (which opens when they press E)
     */
    public Container defaultContainer;

    /** The Container the player has open. */
    public Container activeContainer;

    /** The food object of the player, the general hunger logic. */
    protected FoodMetaData foodData = new FoodMetaData();

    /**
     * Used to tell if the player pressed jump twice. If this is at 0 and it's pressed (And they are allowed to fly, as
     * defined in the player's movementInput) it sets this to 7. If it's pressed and it's greater than 0 enable fly.
     */
    protected int bN = 0;
    public byte bO = 0;
    public float bP;
    public float bQ;
    public String name;

    /**
     * Used by EntityPlayer to prevent too many xp orbs from getting absorbed at once.
     */
    public int bS = 0;
    public double bT;
    public double bU;
    public double bV;
    public double bW;
    public double bX;
    public double bY;

    /** Boolean value indicating weather a player is sleeping or not */
    protected boolean sleeping;

    /** the current location of the player */
    public ChunkCoordinates ca;
    private int sleepTicks;
    public float cb;
    public float cc;

    /** holds the spawn chunk of the player */
    private ChunkCoordinates c;

    /**
     * Whether this player's spawn point is forced, preventing execution of bed checks.
     */
    private boolean d;

    /** Holds the coordinate of the player when enter a minecraft to ride. */
    private ChunkCoordinates e;

    /** The player's capabilities. (See class PlayerCapabilities) */
    public PlayerAbilities abilities = new PlayerAbilities();

    /** The current experience level the player is on. */
    public int expLevel;

    /**
     * The total amount of experience the player has. This also includes the amount of experience within their
     * Experience Bar.
     */
    public int expTotal;

    /**
     * The current amount of experience the player has within their Experience Bar.
     */
    public float exp;

    /**
     * This is the item that is in use when the player is holding down the useItemButton (e.g., bow, food, sword)
     */
    private ItemStack f;

    /**
     * This field starts off equal to getMaxItemUseDuration and is decremented on each tick
     */
    private int g;
    protected float ch = 0.1F;
    protected float ci = 0.02F;
    private int h = 0;

    /**
     * An instance of a fishing rod's hook. If this isn't null, the icon image of the fishing rod is slightly different
     */
    public EntityFishingHook hookedFish = null;

    public EntityHuman(World par1World)
    {
        super(par1World);
        this.defaultContainer = new ContainerPlayer(this.inventory, !par1World.isStatic, this);
        this.activeContainer = this.defaultContainer;
        this.height = 1.62F;
        ChunkCoordinates var2 = par1World.getSpawn();
        this.setPositionRotation((double) var2.x + 0.5D, (double) (var2.y + 1), (double) var2.z + 0.5D, 0.0F, 0.0F);
        this.aJ = "humanoid";
        this.aI = 180.0F;
        this.maxFireTicks = 20;
        this.texture = "/mob/char.png";
    }

    public int getMaxHealth()
    {
        return 20;
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(16, Byte.valueOf((byte) 0));
        this.datawatcher.a(17, Byte.valueOf((byte) 0));
        this.datawatcher.a(18, Integer.valueOf(0));
    }

    /**
     * Checks if the entity is currently using an item (e.g., bow, food, sword) by holding down the useItemButton
     */
    public boolean bM()
    {
        return this.f != null;
    }

    public void bO()
    {
        if (this.f != null)
        {
            this.f.b(this.world, this, this.g);
        }

        this.bP();
    }

    public void bP()
    {
        this.f = null;
        this.g = 0;

        if (!this.world.isStatic)
        {
            this.d(false);
        }
    }

    public boolean bh()
    {
        return this.bM() && Item.byId[this.f.id].b_(this.f) == EnumAnimation.d;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        if (this.f != null)
        {
            ItemStack var1 = this.inventory.getItemInHand();

            if (var1 == this.f)
            {
                if (this.g <= 25 && this.g % 4 == 0)
                {
                    this.c(var1, 5);
                }

                if (--this.g == 0 && !this.world.isStatic)
                {
                    this.n();
                }
            }
            else
            {
                this.bP();
            }
        }

        if (this.bS > 0)
        {
            --this.bS;
        }

        if (this.isSleeping())
        {
            ++this.sleepTicks;

            if (this.sleepTicks > 100)
            {
                this.sleepTicks = 100;
            }

            if (!this.world.isStatic)
            {
                if (!this.j())
                {
                    this.a(true, true, false);
                }
                else if (this.world.u())
                {
                    this.a(false, true, true);
                }
            }
        }
        else if (this.sleepTicks > 0)
        {
            ++this.sleepTicks;

            if (this.sleepTicks >= 110)
            {
                this.sleepTicks = 0;
            }
        }

        super.j_();

        if (!this.world.isStatic && this.activeContainer != null && !this.activeContainer.a(this))
        {
            this.closeInventory();
            this.activeContainer = this.defaultContainer;
        }

        if (this.isBurning() && this.abilities.isInvulnerable)
        {
            this.extinguish();
        }

        this.bT = this.bW;
        this.bU = this.bX;
        this.bV = this.bY;
        double var9 = this.locX - this.bW;
        double var3 = this.locY - this.bX;
        double var5 = this.locZ - this.bY;
        double var7 = 10.0D;

        if (var9 > var7)
        {
            this.bT = this.bW = this.locX;
        }

        if (var5 > var7)
        {
            this.bV = this.bY = this.locZ;
        }

        if (var3 > var7)
        {
            this.bU = this.bX = this.locY;
        }

        if (var9 < -var7)
        {
            this.bT = this.bW = this.locX;
        }

        if (var5 < -var7)
        {
            this.bV = this.bY = this.locZ;
        }

        if (var3 < -var7)
        {
            this.bU = this.bX = this.locY;
        }

        this.bW += var9 * 0.25D;
        this.bY += var5 * 0.25D;
        this.bX += var3 * 0.25D;
        this.a(StatisticList.k, 1);

        if (this.vehicle == null)
        {
            this.e = null;
        }

        if (!this.world.isStatic)
        {
            this.foodData.a(this);
        }
    }

    /**
     * Return the amount of time this entity should stay in a portal before being transported.
     */
    public int z()
    {
        return this.abilities.isInvulnerable ? 0 : 80;
    }

    /**
     * Return the amount of cooldown before this entity can use a portal again.
     */
    public int ab()
    {
        return 10;
    }

    public void makeSound(String par1Str, float par2, float par3)
    {
        this.world.a(this, par1Str, par2, par3);
    }

    /**
     * Plays sounds and makes particles for item in use state
     */
    protected void c(ItemStack par1ItemStack, int par2)
    {
        if (par1ItemStack.n() == EnumAnimation.c)
        {
            this.makeSound("random.drink", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
        }

        if (par1ItemStack.n() == EnumAnimation.b)
        {
            for (int var3 = 0; var3 < par2; ++var3)
            {
                Vec3D var4 = this.world.getVec3DPool().create(((double) this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
                var4.a(-this.pitch * (float) Math.PI / 180.0F);
                var4.b(-this.yaw * (float) Math.PI / 180.0F);
                Vec3D var5 = this.world.getVec3DPool().create(((double) this.random.nextFloat() - 0.5D) * 0.3D, (double) (-this.random.nextFloat()) * 0.6D - 0.3D, 0.6D);
                var5.a(-this.pitch * (float) Math.PI / 180.0F);
                var5.b(-this.yaw * (float) Math.PI / 180.0F);
                var5 = var5.add(this.locX, this.locY + (double) this.getHeadHeight(), this.locZ);
                this.world.addParticle("iconcrack_" + par1ItemStack.getItem().id, var5.c, var5.d, var5.e, var4.c, var4.d + 0.05D, var4.e);
            }

            this.makeSound("random.eat", 0.5F + 0.5F * (float) this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        }
    }

    /**
     * Used for when item use count runs out, ie: eating completed
     */
    protected void n()
    {
        if (this.f != null)
        {
            this.c(this.f, 16);
            int var1 = this.f.count;
            ItemStack var2 = this.f.b(this.world, this);

            if (var2 != this.f || var2 != null && var2.count != var1)
            {
                this.inventory.items[this.inventory.itemInHandIndex] = var2;

                if (var2.count == 0)
                {
                    this.inventory.items[this.inventory.itemInHandIndex] = null;
                }
            }

            this.bP();
        }
    }

    /**
     * Dead and sleeping entities cannot move
     */
    protected boolean bg()
    {
        return this.getHealth() <= 0 || this.isSleeping();
    }

    /**
     * set current crafting inventory back to the 2x2 square
     */
    protected void closeInventory()
    {
        this.activeContainer = this.defaultContainer;
    }

    /**
     * Handles updating while being ridden by an entity
     */
    public void U()
    {
        double var1 = this.locX;
        double var3 = this.locY;
        double var5 = this.locZ;
        float var7 = this.yaw;
        float var8 = this.pitch;
        super.U();
        this.bP = this.bQ;
        this.bQ = 0.0F;
        this.k(this.locX - var1, this.locY - var3, this.locZ - var5);

        if (this.vehicle instanceof EntityPig)
        {
            this.pitch = var8;
            this.yaw = var7;
            this.ax = ((EntityPig)this.vehicle).ax;
        }
    }

    protected void bn()
    {
        this.bo();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        if (this.bN > 0)
        {
            --this.bN;
        }

        if (this.world.difficulty == 0 && this.getHealth() < this.getMaxHealth() && this.ticksLived % 20 * 12 == 0)
        {
            this.heal(1);
        }

        this.inventory.j();
        this.bP = this.bQ;
        super.c();
        this.aN = this.abilities.b();
        this.aO = this.ci;

        if (this.isSprinting())
        {
            this.aN = (float)((double)this.aN + (double)this.abilities.b() * 0.3D);
            this.aO = (float)((double)this.aO + (double)this.ci * 0.3D);
        }

        float var1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        float var2 = (float)Math.atan(-this.motY * 0.20000000298023224D) * 15.0F;

        if (var1 > 0.1F)
        {
            var1 = 0.1F;
        }

        if (!this.onGround || this.getHealth() <= 0)
        {
            var1 = 0.0F;
        }

        if (this.onGround || this.getHealth() <= 0)
        {
            var2 = 0.0F;
        }

        this.bQ += (var1 - this.bQ) * 0.4F;
        this.bb += (var2 - this.bb) * 0.8F;

        if (this.getHealth() > 0)
        {
            List var3 = this.world.getEntities(this, this.boundingBox.grow(1.0D, 0.5D, 1.0D));

            if (var3 != null)
            {
                for (int var4 = 0; var4 < var3.size(); ++var4)
                {
                    Entity var5 = (Entity)var3.get(var4);

                    if (!var5.dead)
                    {
                        this.r(var5);
                    }
                }
            }
        }
    }

    private void r(Entity par1Entity)
    {
        par1Entity.c_(this);
    }

    public int getScore()
    {
        return this.datawatcher.getInt(18);
    }

    /**
     * Set player's score
     */
    public void setScore(int par1)
    {
        this.datawatcher.watch(18, Integer.valueOf(par1));
    }

    /**
     * Add to player's score
     */
    public void addScore(int par1)
    {
        int var2 = this.getScore();
        this.datawatcher.watch(18, Integer.valueOf(var2 + par1));
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void die(DamageSource par1DamageSource)
    {
        super.die(par1DamageSource);
        this.a(0.2F, 0.2F);
        this.setPosition(this.locX, this.locY, this.locZ);
        this.motY = 0.10000000149011612D;

        if (this.name.equals("Notch"))
        {
            this.a(new ItemStack(Item.APPLE, 1), true);
        }

        if (!this.world.getGameRules().getBoolean("keepInventory"))
        {
            this.inventory.l();
        }

        if (par1DamageSource != null)
        {
            this.motX = (double)(-MathHelper.cos((this.aX + this.yaw) * (float) Math.PI / 180.0F) * 0.1F);
            this.motZ = (double)(-MathHelper.sin((this.aX + this.yaw) * (float) Math.PI / 180.0F) * 0.1F);
        }
        else
        {
            this.motX = this.motZ = 0.0D;
        }

        this.height = 0.1F;
        this.a(StatisticList.y, 1);
    }

    /**
     * Adds a value to the player score. Currently not actually used and the entity passed in does nothing. Args:
     * entity, scoreToAdd
     */
    public void c(Entity par1Entity, int par2)
    {
        this.addScore(par2);

        if (par1Entity instanceof EntityHuman)
        {
            this.a(StatisticList.A, 1);
        }
        else
        {
            this.a(StatisticList.z, 1);
        }
    }

    /**
     * Called when player presses the drop item key
     */
    public EntityItem f(boolean par1)
    {
        return this.a(this.inventory.splitStack(this.inventory.itemInHandIndex, par1 && this.inventory.getItemInHand() != null ? this.inventory.getItemInHand().count : 1), false);
    }

    /**
     * Args: itemstack - called when player drops an item stack that's not in his inventory (like items still placed in
     * a workbench while the workbench'es GUI gets closed)
     */
    public EntityItem drop(ItemStack par1ItemStack)
    {
        return this.a(par1ItemStack, false);
    }

    /**
     * Args: itemstack, flag
     */
    public EntityItem a(ItemStack par1ItemStack, boolean par2)
    {
        if (par1ItemStack == null)
        {
            return null;
        }
        else
        {
            EntityItem var3 = new EntityItem(this.world, this.locX, this.locY - 0.30000001192092896D + (double)this.getHeadHeight(), this.locZ, par1ItemStack);
            var3.pickupDelay = 40;
            float var4 = 0.1F;
            float var5;

            if (par2)
            {
                var5 = this.random.nextFloat() * 0.5F;
                float var6 = this.random.nextFloat() * (float)Math.PI * 2.0F;
                var3.motX = (double)(-MathHelper.sin(var6) * var5);
                var3.motZ = (double)(MathHelper.cos(var6) * var5);
                var3.motY = 0.20000000298023224D;
            }
            else
            {
                var4 = 0.3F;
                var3.motX = (double)(-MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float) Math.PI) * var4);
                var3.motZ = (double)(MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float) Math.PI) * var4);
                var3.motY = (double)(-MathHelper.sin(this.pitch / 180.0F * (float) Math.PI) * var4 + 0.1F);
                var4 = 0.02F;
                var5 = this.random.nextFloat() * (float)Math.PI * 2.0F;
                var4 *= this.random.nextFloat();
                var3.motX += Math.cos((double)var5) * (double)var4;
                var3.motY += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                var3.motZ += Math.sin((double)var5) * (double)var4;
            }

            this.a(var3);
            this.a(StatisticList.v, 1);
            return var3;
        }
    }

    /**
     * Joins the passed in entity item with the world. Args: entityItem
     */
    protected void a(EntityItem par1EntityItem)
    {
        this.world.addEntity(par1EntityItem);
    }

    /**
     * Returns how strong the player is against the specified block at this moment
     */
    public float a(Block par1Block)
    {
        float var2 = this.inventory.a(par1Block);
        int var3 = EnchantmentManager.getDigSpeedEnchantmentLevel(this);
        ItemStack var4 = this.inventory.getItemInHand();

        if (var3 > 0 && var4 != null)
        {
            float var5 = (float)(var3 * var3 + 1);

            if (!var4.b(par1Block) && var2 <= 1.0F)
            {
                var2 += var5 * 0.08F;
            }
            else
            {
                var2 += var5;
            }
        }

        if (this.hasEffect(MobEffectList.FASTER_DIG))
        {
            var2 *= 1.0F + (float)(this.getEffect(MobEffectList.FASTER_DIG).getAmplifier() + 1) * 0.2F;
        }

        if (this.hasEffect(MobEffectList.SLOWER_DIG))
        {
            var2 *= 1.0F - (float)(this.getEffect(MobEffectList.SLOWER_DIG).getAmplifier() + 1) * 0.2F;
        }

        if (this.a(Material.WATER) && !EnchantmentManager.hasWaterWorkerEnchantment(this))
        {
            var2 /= 5.0F;
        }

        if (!this.onGround)
        {
            var2 /= 5.0F;
        }

        return var2;
    }

    /**
     * Checks if the player has the ability to harvest a block (checks current inventory item for a tool if necessary)
     */
    public boolean b(Block par1Block)
    {
        return this.inventory.b(par1Block);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getList("Inventory");
        this.inventory.b(var2);
        this.inventory.itemInHandIndex = par1NBTTagCompound.getInt("SelectedItemSlot");
        this.sleeping = par1NBTTagCompound.getBoolean("Sleeping");
        this.sleepTicks = par1NBTTagCompound.getShort("SleepTimer");
        this.exp = par1NBTTagCompound.getFloat("XpP");
        this.expLevel = par1NBTTagCompound.getInt("XpLevel");
        this.expTotal = par1NBTTagCompound.getInt("XpTotal");
        this.setScore(par1NBTTagCompound.getInt("Score"));

        if (this.sleeping)
        {
            this.ca = new ChunkCoordinates(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
            this.a(true, true, false);
        }

        if (par1NBTTagCompound.hasKey("SpawnX") && par1NBTTagCompound.hasKey("SpawnY") && par1NBTTagCompound.hasKey("SpawnZ"))
        {
            this.c = new ChunkCoordinates(par1NBTTagCompound.getInt("SpawnX"), par1NBTTagCompound.getInt("SpawnY"), par1NBTTagCompound.getInt("SpawnZ"));
            this.d = par1NBTTagCompound.getBoolean("SpawnForced");
        }

        this.foodData.a(par1NBTTagCompound);
        this.abilities.b(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("EnderItems"))
        {
            NBTTagList var3 = par1NBTTagCompound.getList("EnderItems");
            this.enderChest.a(var3);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.set("Inventory", this.inventory.a(new NBTTagList()));
        par1NBTTagCompound.setInt("SelectedItemSlot", this.inventory.itemInHandIndex);
        par1NBTTagCompound.setBoolean("Sleeping", this.sleeping);
        par1NBTTagCompound.setShort("SleepTimer", (short) this.sleepTicks);
        par1NBTTagCompound.setFloat("XpP", this.exp);
        par1NBTTagCompound.setInt("XpLevel", this.expLevel);
        par1NBTTagCompound.setInt("XpTotal", this.expTotal);
        par1NBTTagCompound.setInt("Score", this.getScore());

        if (this.c != null)
        {
            par1NBTTagCompound.setInt("SpawnX", this.c.x);
            par1NBTTagCompound.setInt("SpawnY", this.c.y);
            par1NBTTagCompound.setInt("SpawnZ", this.c.z);
            par1NBTTagCompound.setBoolean("SpawnForced", this.d);
        }

        this.foodData.b(par1NBTTagCompound);
        this.abilities.a(par1NBTTagCompound);
        par1NBTTagCompound.set("EnderItems", this.enderChest.g());
    }

    /**
     * Displays the GUI for interacting with a chest inventory. Args: chestInventory
     */
    public void openContainer(IInventory par1IInventory) {}

    public void startEnchanting(int par1, int par2, int par3) {}

    /**
     * Displays the GUI for interacting with an anvil.
     */
    public void openAnvil(int par1, int par2, int par3) {}

    /**
     * Displays the crafting GUI for a workbench.
     */
    public void startCrafting(int par1, int par2, int par3) {}

    public float getHeadHeight()
    {
        return 0.12F;
    }

    /**
     * sets the players height back to normal after doing things like sleeping and dieing
     */
    protected void e_()
    {
        this.height = 1.62F;
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
        else if (this.abilities.isInvulnerable && !par1DamageSource.ignoresInvulnerability())
        {
            return false;
        }
        else
        {
            this.bB = 0;

            if (this.getHealth() <= 0)
            {
                return false;
            }
            else
            {
                if (this.isSleeping() && !this.world.isStatic)
                {
                    this.a(true, true, false);
                }

                if (par1DamageSource.n())
                {
                    if (this.world.difficulty == 0)
                    {
                        par2 = 0;
                    }

                    if (this.world.difficulty == 1)
                    {
                        par2 = par2 / 2 + 1;
                    }

                    if (this.world.difficulty == 3)
                    {
                        par2 = par2 * 3 / 2;
                    }
                }

                if (par2 == 0)
                {
                    return false;
                }
                else
                {
                    Entity var3 = par1DamageSource.getEntity();

                    if (var3 instanceof EntityArrow && ((EntityArrow)var3).shooter != null)
                    {
                        var3 = ((EntityArrow)var3).shooter;
                    }

                    if (var3 instanceof EntityLiving)
                    {
                        this.a((EntityLiving) var3, false);
                    }

                    this.a(StatisticList.x, par2);
                    return super.damageEntity(par1DamageSource, par2);
                }
            }
        }
    }

    /**
     * Reduces damage, depending on potions
     */
    protected int c(DamageSource par1DamageSource, int par2)
    {
        int var3 = super.c(par1DamageSource, par2);

        if (var3 <= 0)
        {
            return 0;
        }
        else
        {
            int var4 = EnchantmentManager.a(this.inventory.armor, par1DamageSource);

            if (var4 > 20)
            {
                var4 = 20;
            }

            if (var4 > 0 && var4 <= 20)
            {
                int var5 = 25 - var4;
                int var6 = var3 * var5 + this.aT;
                var3 = var6 / 25;
                this.aT = var6 % 25;
            }

            return var3;
        }
    }

    /**
     * returns if pvp is enabled or not
     */
    protected boolean h()
    {
        return false;
    }

    /**
     * Called when the player attack or gets attacked, it's alert all wolves in the area that are owned by the player to
     * join the attack or defend the player.
     */
    protected void a(EntityLiving par1EntityLiving, boolean par2)
    {
        if (!(par1EntityLiving instanceof EntityCreeper) && !(par1EntityLiving instanceof EntityGhast))
        {
            if (par1EntityLiving instanceof EntityWolf)
            {
                EntityWolf var3 = (EntityWolf)par1EntityLiving;

                if (var3.isTamed() && this.name.equals(var3.getOwnerName()))
                {
                    return;
                }
            }

            if (!(par1EntityLiving instanceof EntityHuman) || this.h())
            {
                List var6 = this.world.a(EntityWolf.class, AxisAlignedBB.a().a(this.locX, this.locY, this.locZ, this.locX + 1.0D, this.locY + 1.0D, this.locZ + 1.0D).grow(16.0D, 4.0D, 16.0D));
                Iterator var4 = var6.iterator();

                while (var4.hasNext())
                {
                    EntityWolf var5 = (EntityWolf)var4.next();

                    if (var5.isTamed() && var5.l() == null && this.name.equals(var5.getOwnerName()) && (!par2 || !var5.isSitting()))
                    {
                        var5.setSitting(false);
                        var5.setTarget(par1EntityLiving);
                    }
                }
            }
        }
    }

    protected void k(int par1)
    {
        this.inventory.g(par1);
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    public int aW()
    {
        return this.inventory.k();
    }

    public float bR()
    {
        int var1 = 0;
        ItemStack[] var2 = this.inventory.armor;
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            ItemStack var5 = var2[var4];

            if (var5 != null)
            {
                ++var1;
            }
        }

        return (float)var1 / (float)this.inventory.armor.length;
    }

    /**
     * Deals damage to the entity. If its a EntityPlayer then will take damage from the armor first and then health
     * second with the reduced value. Args: damageAmount
     */
    protected void d(DamageSource par1DamageSource, int par2)
    {
        if (!this.isInvulnerable())
        {
            if (!par1DamageSource.ignoresArmor() && this.bh())
            {
                par2 = 1 + par2 >> 1;
            }

            par2 = this.b(par1DamageSource, par2);
            par2 = this.c(par1DamageSource, par2);
            this.j(par1DamageSource.d());
            this.health -= par2;
        }
    }

    /**
     * Displays the furnace GUI for the passed in furnace entity. Args: tileEntityFurnace
     */
    public void openFurnace(TileEntityFurnace par1TileEntityFurnace) {}

    /**
     * Displays the dipsenser GUI for the passed in dispenser entity. Args: TileEntityDispenser
     */
    public void openDispenser(TileEntityDispenser par1TileEntityDispenser) {}

    /**
     * Displays the GUI for editing a sign. Args: tileEntitySign
     */
    public void a(TileEntity par1TileEntity) {}

    /**
     * Displays the GUI for interacting with a brewing stand.
     */
    public void openBrewingStand(TileEntityBrewingStand par1TileEntityBrewingStand) {}

    /**
     * Displays the GUI for interacting with a beacon.
     */
    public void openBeacon(TileEntityBeacon par1TileEntityBeacon) {}

    public void openTrade(IMerchant par1IMerchant) {}

    /**
     * Displays the GUI for interacting with a book.
     */
    public void d(ItemStack par1ItemStack) {}

    public boolean p(Entity par1Entity)
    {
        if (par1Entity.a(this))
        {
            return true;
        }
        else
        {
            ItemStack var2 = this.bS();

            if (var2 != null && par1Entity instanceof EntityLiving)
            {
                if (this.abilities.canInstantlyBuild)
                {
                    var2 = var2.cloneItemStack();
                }

                if (var2.a((EntityLiving) par1Entity))
                {
                    if (var2.count <= 0 && !this.abilities.canInstantlyBuild)
                    {
                        this.bT();
                    }

                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Returns the currently being used item by the player.
     */
    public ItemStack bS()
    {
        return this.inventory.getItemInHand();
    }

    /**
     * Destroys the currently equipped item from the player's inventory.
     */
    public void bT()
    {
        this.inventory.setItem(this.inventory.itemInHandIndex, (ItemStack) null);
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double W()
    {
        return (double)(this.height - 0.5F);
    }

    /**
     * Attacks for the player the targeted entity with the currently equipped item.  The equipped item has hitEntity
     * called on it. Args: targetEntity
     */
    public void attack(Entity par1Entity)
    {
        if (par1Entity.aq())
        {
            if (!par1Entity.j(this))
            {
                int var2 = this.inventory.a(par1Entity);

                if (this.hasEffect(MobEffectList.INCREASE_DAMAGE))
                {
                    var2 += 3 << this.getEffect(MobEffectList.INCREASE_DAMAGE).getAmplifier();
                }

                if (this.hasEffect(MobEffectList.WEAKNESS))
                {
                    var2 -= 2 << this.getEffect(MobEffectList.WEAKNESS).getAmplifier();
                }

                int var3 = 0;
                int var4 = 0;

                if (par1Entity instanceof EntityLiving)
                {
                    var4 = EnchantmentManager.a(this, (EntityLiving) par1Entity);
                    var3 += EnchantmentManager.getKnockbackEnchantmentLevel(this, (EntityLiving) par1Entity);
                }

                if (this.isSprinting())
                {
                    ++var3;
                }

                if (var2 > 0 || var4 > 0)
                {
                    boolean var5 = this.fallDistance > 0.0F && !this.onGround && !this.g_() && !this.H() && !this.hasEffect(MobEffectList.BLINDNESS) && this.vehicle == null && par1Entity instanceof EntityLiving;

                    if (var5)
                    {
                        var2 += this.random.nextInt(var2 / 2 + 2);
                    }

                    var2 += var4;
                    boolean var6 = false;
                    int var7 = EnchantmentManager.getFireAspectEnchantmentLevel(this);

                    if (par1Entity instanceof EntityLiving && var7 > 0 && !par1Entity.isBurning())
                    {
                        var6 = true;
                        par1Entity.setOnFire(1);
                    }

                    boolean var8 = par1Entity.damageEntity(DamageSource.playerAttack(this), var2);

                    if (var8)
                    {
                        if (var3 > 0)
                        {
                            par1Entity.g((double) (-MathHelper.sin(this.yaw * (float) Math.PI / 180.0F) * (float) var3 * 0.5F), 0.1D, (double) (MathHelper.cos(this.yaw * (float) Math.PI / 180.0F) * (float) var3 * 0.5F));
                            this.motX *= 0.6D;
                            this.motZ *= 0.6D;
                            this.setSprinting(false);
                        }

                        if (var5)
                        {
                            this.b(par1Entity);
                        }

                        if (var4 > 0)
                        {
                            this.c(par1Entity);
                        }

                        if (var2 >= 18)
                        {
                            this.a(AchievementList.E);
                        }

                        this.l(par1Entity);

                        if (par1Entity instanceof EntityLiving)
                        {
                            EnchantmentThorns.a(this, (EntityLiving) par1Entity, this.random);
                        }
                    }

                    ItemStack var9 = this.bS();

                    if (var9 != null && par1Entity instanceof EntityLiving)
                    {
                        var9.a((EntityLiving) par1Entity, this);

                        if (var9.count <= 0)
                        {
                            this.bT();
                        }
                    }

                    if (par1Entity instanceof EntityLiving)
                    {
                        if (par1Entity.isAlive())
                        {
                            this.a((EntityLiving) par1Entity, true);
                        }

                        this.a(StatisticList.w, var2);

                        if (var7 > 0 && var8)
                        {
                            par1Entity.setOnFire(var7 * 4);
                        }
                        else if (var6)
                        {
                            par1Entity.extinguish();
                        }
                    }

                    this.j(0.3F);
                }
            }
        }
    }

    /**
     * Called when the player performs a critical hit on the Entity. Args: entity that was hit critically
     */
    public void b(Entity par1Entity) {}

    public void c(Entity par1Entity) {}

    /**
     * Will get destroyed next tick.
     */
    public void die()
    {
        super.die();
        this.defaultContainer.b(this);

        if (this.activeContainer != null)
        {
            this.activeContainer.b(this);
        }
    }

    /**
     * Checks if this entity is inside of an opaque block
     */
    public boolean inBlock()
    {
        return !this.sleeping && super.inBlock();
    }

    public boolean bV()
    {
        return false;
    }

    /**
     * puts player to sleep on specified bed if possible
     */
    public EnumBedResult a(int par1, int par2, int par3)
    {
        if (!this.world.isStatic)
        {
            if (this.isSleeping() || !this.isAlive())
            {
                return EnumBedResult.OTHER_PROBLEM;
            }

            if (!this.world.worldProvider.d())
            {
                return EnumBedResult.NOT_POSSIBLE_HERE;
            }

            if (this.world.u())
            {
                return EnumBedResult.NOT_POSSIBLE_NOW;
            }

            if (Math.abs(this.locX - (double)par1) > 3.0D || Math.abs(this.locY - (double)par2) > 2.0D || Math.abs(this.locZ - (double)par3) > 3.0D)
            {
                return EnumBedResult.TOO_FAR_AWAY;
            }

            double var4 = 8.0D;
            double var6 = 5.0D;
            List var8 = this.world.a(EntityMonster.class, AxisAlignedBB.a().a((double) par1 - var4, (double) par2 - var6, (double) par3 - var4, (double) par1 + var4, (double) par2 + var6, (double) par3 + var4));

            if (!var8.isEmpty())
            {
                return EnumBedResult.NOT_SAFE;
            }
        }

        this.a(0.2F, 0.2F);
        this.height = 0.2F;

        if (this.world.isLoaded(par1, par2, par3))
        {
            int var9 = this.world.getData(par1, par2, par3);
            int var5 = BlockBed.e(var9);
            float var10 = 0.5F;
            float var7 = 0.5F;

            switch (var5)
            {
                case 0:
                    var7 = 0.9F;
                    break;

                case 1:
                    var10 = 0.1F;
                    break;

                case 2:
                    var7 = 0.1F;
                    break;

                case 3:
                    var10 = 0.9F;
            }

            this.x(var5);
            this.setPosition((double) ((float) par1 + var10), (double) ((float) par2 + 0.9375F), (double) ((float) par3 + var7));
        }
        else
        {
            this.setPosition((double) ((float) par1 + 0.5F), (double) ((float) par2 + 0.9375F), (double) ((float) par3 + 0.5F));
        }

        this.sleeping = true;
        this.sleepTicks = 0;
        this.ca = new ChunkCoordinates(par1, par2, par3);
        this.motX = this.motZ = this.motY = 0.0D;

        if (!this.world.isStatic)
        {
            this.world.everyoneSleeping();
        }

        return EnumBedResult.OK;
    }

    private void x(int par1)
    {
        this.cb = 0.0F;
        this.cc = 0.0F;

        switch (par1)
        {
            case 0:
                this.cc = -1.8F;
                break;

            case 1:
                this.cb = 1.8F;
                break;

            case 2:
                this.cc = 1.8F;
                break;

            case 3:
                this.cb = -1.8F;
        }
    }

    /**
     * Wake up the player if they're sleeping.
     */
    public void a(boolean par1, boolean par2, boolean par3)
    {
        this.a(0.6F, 1.8F);
        this.e_();
        ChunkCoordinates var4 = this.ca;
        ChunkCoordinates var5 = this.ca;

        if (var4 != null && this.world.getTypeId(var4.x, var4.y, var4.z) == Block.BED.id)
        {
            BlockBed.a(this.world, var4.x, var4.y, var4.z, false);
            var5 = BlockBed.b(this.world, var4.x, var4.y, var4.z, 0);

            if (var5 == null)
            {
                var5 = new ChunkCoordinates(var4.x, var4.y + 1, var4.z);
            }

            this.setPosition((double) ((float) var5.x + 0.5F), (double) ((float) var5.y + this.height + 0.1F), (double) ((float) var5.z + 0.5F));
        }

        this.sleeping = false;

        if (!this.world.isStatic && par2)
        {
            this.world.everyoneSleeping();
        }

        if (par1)
        {
            this.sleepTicks = 0;
        }
        else
        {
            this.sleepTicks = 100;
        }

        if (par3)
        {
            this.setRespawnPosition(this.ca, false);
        }
    }

    /**
     * Checks if the player is currently in a bed
     */
    private boolean j()
    {
        return this.world.getTypeId(this.ca.x, this.ca.y, this.ca.z) == Block.BED.id;
    }

    /**
     * Ensure that a block enabling respawning exists at the specified coordinates and find an empty space nearby to
     * spawn.
     */
    public static ChunkCoordinates getBed(World par0World, ChunkCoordinates par1ChunkCoordinates, boolean par2)
    {
        IChunkProvider var3 = par0World.I();
        var3.getChunkAt(par1ChunkCoordinates.x - 3 >> 4, par1ChunkCoordinates.z - 3 >> 4);
        var3.getChunkAt(par1ChunkCoordinates.x + 3 >> 4, par1ChunkCoordinates.z - 3 >> 4);
        var3.getChunkAt(par1ChunkCoordinates.x - 3 >> 4, par1ChunkCoordinates.z + 3 >> 4);
        var3.getChunkAt(par1ChunkCoordinates.x + 3 >> 4, par1ChunkCoordinates.z + 3 >> 4);

        if (par0World.getTypeId(par1ChunkCoordinates.x, par1ChunkCoordinates.y, par1ChunkCoordinates.z) == Block.BED.id)
        {
            ChunkCoordinates var8 = BlockBed.b(par0World, par1ChunkCoordinates.x, par1ChunkCoordinates.y, par1ChunkCoordinates.z, 0);
            return var8;
        }
        else
        {
            Material var4 = par0World.getMaterial(par1ChunkCoordinates.x, par1ChunkCoordinates.y, par1ChunkCoordinates.z);
            Material var5 = par0World.getMaterial(par1ChunkCoordinates.x, par1ChunkCoordinates.y + 1, par1ChunkCoordinates.z);
            boolean var6 = !var4.isBuildable() && !var4.isLiquid();
            boolean var7 = !var5.isBuildable() && !var5.isLiquid();
            return par2 && var6 && var7 ? par1ChunkCoordinates : null;
        }
    }

    /**
     * Returns whether player is sleeping or not
     */
    public boolean isSleeping()
    {
        return this.sleeping;
    }

    /**
     * Returns whether or not the player is asleep and the screen has fully faded.
     */
    public boolean isDeeplySleeping()
    {
        return this.sleeping && this.sleepTicks >= 100;
    }

    protected void b(int par1, boolean par2)
    {
        byte var3 = this.datawatcher.getByte(16);

        if (par2)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) (var3 | 1 << par1)));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) (var3 & ~(1 << par1))));
        }
    }

    /**
     * Add a chat message to the player
     */
    public void b(String par1Str) {}

    /**
     * Returns the location of the bed the player will respawn at, or null if the player has not slept in a bed.
     */
    public ChunkCoordinates getBed()
    {
        return this.c;
    }

    public boolean isRespawnForced()
    {
        return this.d;
    }

    /**
     * Defines a spawn coordinate to player spawn. Used by bed after the player sleep on it.
     */
    public void setRespawnPosition(ChunkCoordinates par1ChunkCoordinates, boolean par2)
    {
        if (par1ChunkCoordinates != null)
        {
            this.c = new ChunkCoordinates(par1ChunkCoordinates);
            this.d = par2;
        }
        else
        {
            this.c = null;
            this.d = false;
        }
    }

    /**
     * Will trigger the specified trigger.
     */
    public void a(Statistic par1StatBase)
    {
        this.a(par1StatBase, 1);
    }

    /**
     * Adds a value to a statistic field.
     */
    public void a(Statistic par1StatBase, int par2) {}

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void bi()
    {
        super.bi();
        this.a(StatisticList.u, 1);

        if (this.isSprinting())
        {
            this.j(0.8F);
        }
        else
        {
            this.j(0.2F);
        }
    }

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    public void e(float par1, float par2)
    {
        double var3 = this.locX;
        double var5 = this.locY;
        double var7 = this.locZ;

        if (this.abilities.isFlying && this.vehicle == null)
        {
            double var9 = this.motY;
            float var11 = this.aO;
            this.aO = this.abilities.a();
            super.e(par1, par2);
            this.motY = var9 * 0.6D;
            this.aO = var11;
        }
        else
        {
            super.e(par1, par2);
        }

        this.checkMovement(this.locX - var3, this.locY - var5, this.locZ - var7);
    }

    /**
     * Adds a value to a movement statistic field - like run, walk, swin or climb.
     */
    public void checkMovement(double par1, double par3, double par5)
    {
        if (this.vehicle == null)
        {
            int var7;

            if (this.a(Material.WATER))
            {
                var7 = Math.round(MathHelper.sqrt(par1 * par1 + par3 * par3 + par5 * par5) * 100.0F);

                if (var7 > 0)
                {
                    this.a(StatisticList.q, var7);
                    this.j(0.015F * (float) var7 * 0.01F);
                }
            }
            else if (this.H())
            {
                var7 = Math.round(MathHelper.sqrt(par1 * par1 + par5 * par5) * 100.0F);

                if (var7 > 0)
                {
                    this.a(StatisticList.m, var7);
                    this.j(0.015F * (float) var7 * 0.01F);
                }
            }
            else if (this.g_())
            {
                if (par3 > 0.0D)
                {
                    this.a(StatisticList.o, (int) Math.round(par3 * 100.0D));
                }
            }
            else if (this.onGround)
            {
                var7 = Math.round(MathHelper.sqrt(par1 * par1 + par5 * par5) * 100.0F);

                if (var7 > 0)
                {
                    this.a(StatisticList.l, var7);

                    if (this.isSprinting())
                    {
                        this.j(0.099999994F * (float) var7 * 0.01F);
                    }
                    else
                    {
                        this.j(0.01F * (float) var7 * 0.01F);
                    }
                }
            }
            else
            {
                var7 = Math.round(MathHelper.sqrt(par1 * par1 + par5 * par5) * 100.0F);

                if (var7 > 25)
                {
                    this.a(StatisticList.p, var7);
                }
            }
        }
    }

    /**
     * Adds a value to a mounted movement statistic field - by minecart, boat, or pig.
     */
    private void k(double par1, double par3, double par5)
    {
        if (this.vehicle != null)
        {
            int var7 = Math.round(MathHelper.sqrt(par1 * par1 + par3 * par3 + par5 * par5) * 100.0F);

            if (var7 > 0)
            {
                if (this.vehicle instanceof EntityMinecart)
                {
                    this.a(StatisticList.r, var7);

                    if (this.e == null)
                    {
                        this.e = new ChunkCoordinates(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
                    }
                    else if ((double)this.e.e(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) >= 1000000.0D)
                    {
                        this.a(AchievementList.q, 1);
                    }
                }
                else if (this.vehicle instanceof EntityBoat)
                {
                    this.a(StatisticList.s, var7);
                }
                else if (this.vehicle instanceof EntityPig)
                {
                    this.a(StatisticList.t, var7);
                }
            }
        }
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void a(float par1)
    {
        if (!this.abilities.canFly)
        {
            if (par1 >= 2.0F)
            {
                this.a(StatisticList.n, (int) Math.round((double) par1 * 100.0D));
            }

            super.a(par1);
        }
    }

    /**
     * This method gets called when the entity kills another one.
     */
    public void a(EntityLiving par1EntityLiving)
    {
        if (par1EntityLiving instanceof IMonster)
        {
            this.a(AchievementList.s);
        }
    }

    /**
     * Sets the Entity inside a web block.
     */
    public void am()
    {
        if (!this.abilities.isFlying)
        {
            super.am();
        }
    }

    public ItemStack q(int par1)
    {
        return this.inventory.f(par1);
    }

    protected void bE() {}

    protected void bF() {}

    /**
     * Add experience points to player.
     */
    public void giveExp(int par1)
    {
        this.addScore(par1);
        int var2 = Integer.MAX_VALUE - this.expTotal;

        if (par1 > var2)
        {
            par1 = var2;
        }

        this.exp += (float)par1 / (float)this.getExpToLevel();

        for (this.expTotal += par1; this.exp >= 1.0F; this.exp /= (float)this.getExpToLevel())
        {
            this.exp = (this.exp - 1.0F) * (float)this.getExpToLevel();
            this.levelDown(1);
        }
    }

    /**
     * Add experience levels to this player.
     */
    public void levelDown(int par1)
    {
        this.expLevel += par1;

        if (this.expLevel < 0)
        {
            this.expLevel = 0;
            this.exp = 0.0F;
            this.expTotal = 0;
        }

        if (par1 > 0 && this.expLevel % 5 == 0 && (float)this.h < (float)this.ticksLived - 100.0F)
        {
            float var2 = this.expLevel > 30 ? 1.0F : (float)this.expLevel / 30.0F;
            this.world.makeSound(this, "random.levelup", var2 * 0.75F, 1.0F);
            this.h = this.ticksLived;
        }
    }

    /**
     * This method returns the cap amount of experience that the experience bar can hold. With each level, the
     * experience cap on the player's experience bar is raised by 10.
     */
    public int getExpToLevel()
    {
        return this.expLevel >= 30 ? 62 + (this.expLevel - 30) * 7 : (this.expLevel >= 15 ? 17 + (this.expLevel - 15) * 3 : 17);
    }

    /**
     * increases exhaustion level by supplied amount
     */
    public void j(float par1)
    {
        if (!this.abilities.isInvulnerable)
        {
            if (!this.world.isStatic)
            {
                this.foodData.a(par1);
            }
        }
    }

    /**
     * Returns the player's FoodStats object.
     */
    public FoodMetaData getFoodData()
    {
        return this.foodData;
    }

    public boolean g(boolean par1)
    {
        return (par1 || this.foodData.c()) && !this.abilities.isInvulnerable;
    }

    /**
     * Checks if the player's health is not full and not zero.
     */
    public boolean cd()
    {
        return this.getHealth() > 0 && this.getHealth() < this.getMaxHealth();
    }

    /**
     * sets the itemInUse when the use item button is clicked. Args: itemstack, int maxItemUseDuration
     */
    public void a(ItemStack par1ItemStack, int par2)
    {
        if (par1ItemStack != this.f)
        {
            this.f = par1ItemStack;
            this.g = par2;

            if (!this.world.isStatic)
            {
                this.d(true);
            }
        }
    }

    /**
     * Returns true if the item the player is holding can harvest the block at the given coords. Args: x, y, z.
     */
    public boolean f(int par1, int par2, int par3)
    {
        if (this.abilities.mayBuild)
        {
            return true;
        }
        else
        {
            int var4 = this.world.getTypeId(par1, par2, par3);

            if (var4 > 0)
            {
                Block var5 = Block.byId[var4];

                if (var5.material.q())
                {
                    return true;
                }

                if (this.bS() != null)
                {
                    ItemStack var6 = this.bS();

                    if (var6.b(var5) || var6.a(var5) > 1.0F)
                    {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public boolean a(int par1, int par2, int par3, int par4, ItemStack par5ItemStack)
    {
        return this.abilities.mayBuild ? true : (par5ItemStack != null ? par5ItemStack.x() : false);
    }

    /**
     * Get the experience points the entity currently has.
     */
    protected int getExpValue(EntityHuman par1EntityPlayer)
    {
        if (this.world.getGameRules().getBoolean("keepInventory"))
        {
            return 0;
        }
        else
        {
            int var2 = this.expLevel * 7;
            return var2 > 100 ? 100 : var2;
        }
    }

    /**
     * Only use is to identify if class is an instance of player for experience dropping
     */
    protected boolean alwaysGivesExp()
    {
        return true;
    }

    /**
     * Gets the username of the entity.
     */
    public String getLocalizedName()
    {
        return this.name;
    }

    /**
     * Copies the values from the given player into this player if boolean par2 is true. Always clones Ender Chest
     * Inventory.
     */
    public void copyTo(EntityHuman par1EntityPlayer, boolean par2)
    {
        if (par2)
        {
            this.inventory.b(par1EntityPlayer.inventory);
            this.health = par1EntityPlayer.health;
            this.foodData = par1EntityPlayer.foodData;
            this.expLevel = par1EntityPlayer.expLevel;
            this.expTotal = par1EntityPlayer.expTotal;
            this.exp = par1EntityPlayer.exp;
            this.setScore(par1EntityPlayer.getScore());
            this.ar = par1EntityPlayer.ar;
        }
        else if (this.world.getGameRules().getBoolean("keepInventory"))
        {
            this.inventory.b(par1EntityPlayer.inventory);
            this.expLevel = par1EntityPlayer.expLevel;
            this.expTotal = par1EntityPlayer.expTotal;
            this.exp = par1EntityPlayer.exp;
            this.setScore(par1EntityPlayer.getScore());
        }

        this.enderChest = par1EntityPlayer.enderChest;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean f_()
    {
        return !this.abilities.isFlying;
    }

    /**
     * Sends the player's abilities to the server (if there is one).
     */
    public void updateAbilities() {}

    /**
     * Sets the player's game mode and sends it to them.
     */
    public void a(EnumGamemode par1EnumGameType) {}

    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    public String getName()
    {
        return this.name;
    }

    public LocaleLanguage getLocale()
    {
        return LocaleLanguage.a();
    }

    /**
     * Translates and formats the given string key with the given arguments.
     */
    public String a(String par1Str, Object... par2ArrayOfObj)
    {
        return this.getLocale().a(par1Str, par2ArrayOfObj);
    }

    /**
     * Returns the InventoryEnderChest of this player.
     */
    public InventoryEnderChest getEnderChest()
    {
        return this.enderChest;
    }

    /**
     * 0: Tool in Hand; 1-4: Armor
     */
    public ItemStack getEquipment(int par1)
    {
        return par1 == 0 ? this.inventory.getItemInHand() : this.inventory.armor[par1 - 1];
    }

    /**
     * Returns the item that this EntityLiving is holding, if any.
     */
    public ItemStack bD()
    {
        return this.inventory.getItemInHand();
    }

    /**
     * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is armor. Params: Item, slot
     */
    public void setEquipment(int par1, ItemStack par2ItemStack)
    {
        this.inventory.armor[par1] = par2ItemStack;
    }

    /**
     * returns the inventory of this entity (only used in EntityPlayerMP it seems)
     */
    public ItemStack[] getEquipment()
    {
        return this.inventory.armor;
    }
}
