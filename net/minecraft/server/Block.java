package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class Block
{
    /**
     * used as foreach item, if item.tab = current tab, display it on the screen
     */
    private CreativeModeTab creativeTab;
    public static final StepSound d = new StepSound("stone", 1.0F, 1.0F);
    public static final StepSound e = new StepSound("wood", 1.0F, 1.0F);
    public static final StepSound f = new StepSound("gravel", 1.0F, 1.0F);
    public static final StepSound g = new StepSound("grass", 1.0F, 1.0F);
    public static final StepSound h = new StepSound("stone", 1.0F, 1.0F);
    public static final StepSound i = new StepSound("stone", 1.0F, 1.5F);
    public static final StepSound j = new StepSoundStone("stone", 1.0F, 1.0F);
    public static final StepSound k = new StepSound("cloth", 1.0F, 1.0F);
    public static final StepSound l = new StepSound("sand", 1.0F, 1.0F);
    public static final StepSound m = new StepSound("snow", 1.0F, 1.0F);
    public static final StepSound n = new StepSoundLadder("ladder", 1.0F, 1.0F);
    public static final StepSound o = new StepSoundAnvil("anvil", 0.3F, 1.0F);

    /** List of ly/ff (BlockType) containing the already registered blocks. */
    public static final Block[] byId = new Block[4096];

    /**
     * An array of 4096 booleans corresponding to the result of the isOpaqueCube() method for each block ID
     */
    public static final boolean[] q = new boolean[4096];

    /** How much light is subtracted for going through this block */
    public static final int[] lightBlock = new int[4096];

    /** Array of booleans that tells if a block can grass */
    public static final boolean[] s = new boolean[4096];

    /** Amount of light emitted */
    public static final int[] lightEmission = new int[4096];
    public static final boolean[] u = new boolean[4096];

    /**
     * Flag if block ID should use the brightest neighbor light value as its own
     */
    public static boolean[] v = new boolean[4096];
    public static final Block STONE = (new BlockStone(1, 1)).c(1.5F).b(10.0F).a(h).b("stone");
    public static final BlockGrass GRASS = (BlockGrass)(new BlockGrass(2)).c(0.6F).a(g).b("grass");
    public static final Block DIRT = (new BlockDirt(3, 2)).c(0.5F).a(f).b("dirt");
    public static final Block COBBLESTONE = (new Block(4, 16, Material.STONE)).c(2.0F).b(10.0F).a(h).b("stonebrick").a(CreativeModeTab.b);
    public static final Block WOOD = (new BlockWood(5)).c(2.0F).b(5.0F).a(e).b("wood").r();
    public static final Block SAPLING = (new BlockSapling(6, 15)).c(0.0F).a(g).b("sapling").r();
    public static final Block BEDROCK = (new Block(7, 17, Material.STONE)).s().b(6000000.0F).a(h).b("bedrock").D().a(CreativeModeTab.b);
    public static final Block WATER = (new BlockFlowing(8, Material.WATER)).c(100.0F).h(3).b("water").D().r();
    public static final Block STATIONARY_WATER = (new BlockStationary(9, Material.WATER)).c(100.0F).h(3).b("water").D().r();
    public static final Block LAVA = (new BlockFlowing(10, Material.LAVA)).c(0.0F).a(1.0F).h(255).b("lava").D().r();

    /** Stationary lava source block */
    public static final Block STATIONARY_LAVA = (new BlockStationary(11, Material.LAVA)).c(100.0F).a(1.0F).h(255).b("lava").D().r();
    public static final Block SAND = (new BlockSand(12, 18)).c(0.5F).a(l).b("sand");
    public static final Block GRAVEL = (new BlockGravel(13, 19)).c(0.6F).a(f).b("gravel");
    public static final Block GOLD_ORE = (new BlockOre(14, 32)).c(3.0F).b(5.0F).a(h).b("oreGold");
    public static final Block IRON_ORE = (new BlockOre(15, 33)).c(3.0F).b(5.0F).a(h).b("oreIron");
    public static final Block COAL_ORE = (new BlockOre(16, 34)).c(3.0F).b(5.0F).a(h).b("oreCoal");
    public static final Block LOG = (new BlockLog(17)).c(2.0F).a(e).b("log").r();
    public static final BlockLeaves LEAVES = (BlockLeaves)(new BlockLeaves(18, 52)).c(0.2F).h(1).a(g).b("leaves").r();
    public static final Block SPONGE = (new BlockSponge(19)).c(0.6F).a(g).b("sponge");
    public static final Block GLASS = (new BlockGlass(20, 49, Material.SHATTERABLE, false)).c(0.3F).a(j).b("glass");
    public static final Block LAPIS_ORE = (new BlockOre(21, 160)).c(3.0F).b(5.0F).a(h).b("oreLapis");
    public static final Block LAPIS_BLOCK = (new Block(22, 144, Material.STONE)).c(3.0F).b(5.0F).a(h).b("blockLapis").a(CreativeModeTab.b);
    public static final Block DISPENSER = (new BlockDispenser(23)).c(3.5F).a(h).b("dispenser").r();
    public static final Block SANDSTONE = (new BlockSandStone(24)).a(h).c(0.8F).b("sandStone").r();
    public static final Block NOTE_BLOCK = (new BlockNote(25)).c(0.8F).b("musicBlock").r();
    public static final Block BED = (new BlockBed(26)).c(0.2F).b("bed").D().r();
    public static final Block GOLDEN_RAIL = (new BlockMinecartTrack(27, 179, true)).c(0.7F).a(i).b("goldenRail").r();
    public static final Block DETECTOR_RAIL = (new BlockMinecartDetector(28, 195)).c(0.7F).a(i).b("detectorRail").r();
    public static final Block PISTON_STICKY = (new BlockPiston(29, 106, true)).b("pistonStickyBase").r();
    public static final Block WEB = (new BlockWeb(30, 11)).h(1).c(4.0F).b("web");
    public static final BlockLongGrass LONG_GRASS = (BlockLongGrass)(new BlockLongGrass(31, 39)).c(0.0F).a(g).b("tallgrass");
    public static final BlockDeadBush DEAD_BUSH = (BlockDeadBush)(new BlockDeadBush(32, 55)).c(0.0F).a(g).b("deadbush");
    public static final Block PISTON = (new BlockPiston(33, 107, false)).b("pistonBase").r();
    public static final BlockPistonExtension PISTON_EXTENSION = (BlockPistonExtension)(new BlockPistonExtension(34, 107)).r();
    public static final Block WOOL = (new BlockCloth()).c(0.8F).a(k).b("cloth").r();
    public static final BlockPistonMoving PISTON_MOVING = new BlockPistonMoving(36);
    public static final BlockFlower YELLOW_FLOWER = (BlockFlower)(new BlockFlower(37, 13)).c(0.0F).a(g).b("flower");
    public static final BlockFlower RED_ROSE = (BlockFlower)(new BlockFlower(38, 12)).c(0.0F).a(g).b("rose");
    public static final BlockFlower BROWN_MUSHROOM = (BlockFlower)(new BlockMushroom(39, 29)).c(0.0F).a(g).a(0.125F).b("mushroom");
    public static final BlockFlower RED_MUSHROOM = (BlockFlower)(new BlockMushroom(40, 28)).c(0.0F).a(g).b("mushroom");
    public static final Block GOLD_BLOCK = (new BlockOreBlock(41, 23)).c(3.0F).b(10.0F).a(i).b("blockGold");
    public static final Block IRON_BLOCK = (new BlockOreBlock(42, 22)).c(5.0F).b(10.0F).a(i).b("blockIron");

    /** stoneDoubleSlab */
    public static final BlockStepAbstract DOUBLE_STEP = (BlockStepAbstract)(new BlockStep(43, true)).c(2.0F).b(10.0F).a(h).b("stoneSlab");

    /** stoneSingleSlab */
    public static final BlockStepAbstract STEP = (BlockStepAbstract)(new BlockStep(44, false)).c(2.0F).b(10.0F).a(h).b("stoneSlab");
    public static final Block BRICK = (new Block(45, 7, Material.STONE)).c(2.0F).b(10.0F).a(h).b("brick").a(CreativeModeTab.b);
    public static final Block TNT = (new BlockTNT(46, 8)).c(0.0F).a(g).b("tnt");
    public static final Block BOOKSHELF = (new BlockBookshelf(47, 35)).c(1.5F).a(e).b("bookshelf");
    public static final Block MOSSY_COBBLESTONE = (new Block(48, 36, Material.STONE)).c(2.0F).b(10.0F).a(h).b("stoneMoss").a(CreativeModeTab.b);
    public static final Block OBSIDIAN = (new BlockObsidian(49, 37)).c(50.0F).b(2000.0F).a(h).b("obsidian");
    public static final Block TORCH = (new BlockTorch(50, 80)).c(0.0F).a(0.9375F).a(e).b("torch").r();
    public static final BlockFire FIRE = (BlockFire)(new BlockFire(51, 31)).c(0.0F).a(1.0F).a(e).b("fire").D();
    public static final Block MOB_SPAWNER = (new BlockMobSpawner(52, 65)).c(5.0F).a(i).b("mobSpawner").D();
    public static final Block WOOD_STAIRS = (new BlockStairs(53, WOOD, 0)).b("stairsWood").r();
    public static final Block CHEST = (new BlockChest(54)).c(2.5F).a(e).b("chest").r();
    public static final Block REDSTONE_WIRE = (new BlockRedstoneWire(55, 164)).c(0.0F).a(d).b("redstoneDust").D().r();
    public static final Block DIAMOND_ORE = (new BlockOre(56, 50)).c(3.0F).b(5.0F).a(h).b("oreDiamond");
    public static final Block DIAMOND_BLOCK = (new BlockOreBlock(57, 24)).c(5.0F).b(10.0F).a(i).b("blockDiamond");
    public static final Block WORKBENCH = (new BlockWorkbench(58)).c(2.5F).a(e).b("workbench");
    public static final Block CROPS = (new BlockCrops(59, 88)).b("crops");
    public static final Block SOIL = (new BlockSoil(60)).c(0.6F).a(f).b("farmland").r();
    public static final Block FURNACE = (new BlockFurnace(61, false)).c(3.5F).a(h).b("furnace").r().a(CreativeModeTab.c);
    public static final Block BURNING_FURNACE = (new BlockFurnace(62, true)).c(3.5F).a(h).a(0.875F).b("furnace").r();
    public static final Block SIGN_POST = (new BlockSign(63, TileEntitySign.class, true)).c(1.0F).a(e).b("sign").D().r();
    public static final Block WOODEN_DOOR = (new BlockDoor(64, Material.WOOD)).c(3.0F).a(e).b("doorWood").D().r();
    public static final Block LADDER = (new BlockLadder(65, 83)).c(0.4F).a(n).b("ladder").r();
    public static final Block RAILS = (new BlockMinecartTrack(66, 128, false)).c(0.7F).a(i).b("rail").r();
    public static final Block COBBLESTONE_STAIRS = (new BlockStairs(67, COBBLESTONE, 0)).b("stairsStone").r();
    public static final Block WALL_SIGN = (new BlockSign(68, TileEntitySign.class, false)).c(1.0F).a(e).b("sign").D().r();
    public static final Block LEVER = (new BlockLever(69, 96)).c(0.5F).a(e).b("lever").r();
    public static final Block STONE_PLATE = (new BlockPressurePlate(70, STONE.textureId, EnumMobType.MOBS, Material.STONE)).c(0.5F).a(h).b("pressurePlate").r();
    public static final Block IRON_DOOR_BLOCK = (new BlockDoor(71, Material.ORE)).c(5.0F).a(i).b("doorIron").D().r();
    public static final Block WOOD_PLATE = (new BlockPressurePlate(72, WOOD.textureId, EnumMobType.EVERYTHING, Material.WOOD)).c(0.5F).a(e).b("pressurePlate").r();
    public static final Block REDSTONE_ORE = (new BlockRedstoneOre(73, 51, false)).c(3.0F).b(5.0F).a(h).b("oreRedstone").r().a(CreativeModeTab.b);
    public static final Block GLOWING_REDSTONE_ORE = (new BlockRedstoneOre(74, 51, true)).a(0.625F).c(3.0F).b(5.0F).a(h).b("oreRedstone").r();
    public static final Block REDSTONE_TORCH_OFF = (new BlockRedstoneTorch(75, 115, false)).c(0.0F).a(e).b("notGate").r();
    public static final Block REDSTONE_TORCH_ON = (new BlockRedstoneTorch(76, 99, true)).c(0.0F).a(0.5F).a(e).b("notGate").r().a(CreativeModeTab.d);
    public static final Block STONE_BUTTON = (new BlockButton(77, STONE.textureId, false)).c(0.5F).a(h).b("button").r();
    public static final Block SNOW = (new BlockSnow(78, 66)).c(0.1F).a(m).b("snow").r().h(0);
    public static final Block ICE = (new BlockIce(79, 67)).c(0.5F).h(3).a(j).b("ice");
    public static final Block SNOW_BLOCK = (new BlockSnowBlock(80, 66)).c(0.2F).a(m).b("snow");
    public static final Block CACTUS = (new BlockCactus(81, 70)).c(0.4F).a(k).b("cactus");
    public static final Block CLAY = (new BlockClay(82, 72)).c(0.6F).a(f).b("clay");
    public static final Block SUGAR_CANE_BLOCK = (new BlockReed(83, 73)).c(0.0F).a(g).b("reeds").D();
    public static final Block JUKEBOX = (new BlockJukeBox(84, 74)).c(2.0F).b(10.0F).a(h).b("jukebox").r();
    public static final Block FENCE = (new BlockFence(85, 4)).c(2.0F).b(5.0F).a(e).b("fence");
    public static final Block PUMPKIN = (new BlockPumpkin(86, 102, false)).c(1.0F).a(e).b("pumpkin").r();
    public static final Block NETHERRACK = (new BlockBloodStone(87, 103)).c(0.4F).a(h).b("hellrock");
    public static final Block SOUL_SAND = (new BlockSlowSand(88, 104)).c(0.5F).a(l).b("hellsand");
    public static final Block GLOWSTONE = (new BlockLightStone(89, 105, Material.SHATTERABLE)).c(0.3F).a(j).a(1.0F).b("lightgem");

    /** The purple teleport blocks inside the obsidian circle */
    public static final BlockPortal PORTAL = (BlockPortal)(new BlockPortal(90, 14)).c(-1.0F).a(j).a(0.75F).b("portal");
    public static final Block JACK_O_LANTERN = (new BlockPumpkin(91, 102, true)).c(1.0F).a(e).a(1.0F).b("litpumpkin").r();
    public static final Block CAKE_BLOCK = (new BlockCake(92, 121)).c(0.5F).a(k).b("cake").D().r();
    public static final Block DIODE_OFF = (new BlockDiode(93, false)).c(0.0F).a(e).b("diode").D().r();
    public static final Block DIODE_ON = (new BlockDiode(94, true)).c(0.0F).a(0.625F).a(e).b("diode").D().r();

    /**
     * April fools secret locked chest, only spawns on new chunks on 1st April.
     */
    public static final Block LOCKED_CHEST = (new BlockLockedChest(95)).c(0.0F).a(1.0F).a(e).b("lockedchest").b(true).r();
    public static final Block TRAP_DOOR = (new BlockTrapdoor(96, Material.WOOD)).c(3.0F).a(e).b("trapdoor").D().r();
    public static final Block MONSTER_EGGS = (new BlockMonsterEggs(97)).c(0.75F).b("monsterStoneEgg");
    public static final Block SMOOTH_BRICK = (new BlockSmoothBrick(98)).c(1.5F).b(10.0F).a(h).b("stonebricksmooth");
    public static final Block BIG_MUSHROOM_1 = (new BlockHugeMushroom(99, Material.WOOD, 142, 0)).c(0.2F).a(e).b("mushroom").r();
    public static final Block BIG_MUSHROOM_2 = (new BlockHugeMushroom(100, Material.WOOD, 142, 1)).c(0.2F).a(e).b("mushroom").r();
    public static final Block IRON_FENCE = (new BlockThinFence(101, 85, 85, Material.ORE, true)).c(5.0F).b(10.0F).a(i).b("fenceIron");
    public static final Block THIN_GLASS = (new BlockThinFence(102, 49, 148, Material.SHATTERABLE, false)).c(0.3F).a(j).b("thinGlass");
    public static final Block MELON = (new BlockMelon(103)).c(1.0F).a(e).b("melon");
    public static final Block PUMPKIN_STEM = (new BlockStem(104, PUMPKIN)).c(0.0F).a(e).b("pumpkinStem").r();
    public static final Block MELON_STEM = (new BlockStem(105, MELON)).c(0.0F).a(e).b("pumpkinStem").r();
    public static final Block VINE = (new BlockVine(106)).c(0.2F).a(g).b("vine").r();
    public static final Block FENCE_GATE = (new BlockFenceGate(107, 4)).c(2.0F).b(5.0F).a(e).b("fenceGate").r();
    public static final Block BRICK_STAIRS = (new BlockStairs(108, BRICK, 0)).b("stairsBrick").r();
    public static final Block STONE_STAIRS = (new BlockStairs(109, SMOOTH_BRICK, 0)).b("stairsStoneBrickSmooth").r();
    public static final BlockMycel MYCEL = (BlockMycel)(new BlockMycel(110)).c(0.6F).a(g).b("mycel");
    public static final Block WATER_LILY = (new BlockWaterLily(111, 76)).c(0.0F).a(g).b("waterlily");
    public static final Block NETHER_BRICK = (new Block(112, 224, Material.STONE)).c(2.0F).b(10.0F).a(h).b("netherBrick").a(CreativeModeTab.b);
    public static final Block NETHER_FENCE = (new BlockFence(113, 224, Material.STONE)).c(2.0F).b(10.0F).a(h).b("netherFence");
    public static final Block NETHER_BRICK_STAIRS = (new BlockStairs(114, NETHER_BRICK, 0)).b("stairsNetherBrick").r();
    public static final Block NETHER_WART = (new BlockNetherWart(115)).b("netherStalk").r();
    public static final Block ENCHANTMENT_TABLE = (new BlockEnchantmentTable(116)).c(5.0F).b(2000.0F).b("enchantmentTable");
    public static final Block BREWING_STAND = (new BlockBrewingStand(117)).c(0.5F).a(0.125F).b("brewingStand").r();
    public static final Block CAULDRON = (new BlockCauldron(118)).c(2.0F).b("cauldron").r();
    public static final Block ENDER_PORTAL = (new BlockEnderPortal(119, Material.PORTAL)).c(-1.0F).b(6000000.0F);
    public static final Block ENDER_PORTAL_FRAME = (new BlockEnderPortalFrame(120)).a(j).a(0.125F).c(-1.0F).b("endPortalFrame").r().b(6000000.0F).a(CreativeModeTab.c);
    public static final Block WHITESTONE = (new Block(121, 175, Material.STONE)).c(3.0F).b(15.0F).a(h).b("whiteStone").a(CreativeModeTab.b);
    public static final Block DRAGON_EGG = (new BlockDragonEgg(122, 167)).c(3.0F).b(15.0F).a(h).a(0.125F).b("dragonEgg");
    public static final Block REDSTONE_LAMP_OFF = (new BlockRedstoneLamp(123, false)).c(0.3F).a(j).b("redstoneLight").a(CreativeModeTab.d);
    public static final Block REDSTONE_LAMP_ON = (new BlockRedstoneLamp(124, true)).c(0.3F).a(j).b("redstoneLight");
    public static final BlockStepAbstract WOOD_DOUBLE_STEP = (BlockStepAbstract)(new BlockWoodStep(125, true)).c(2.0F).b(5.0F).a(e).b("woodSlab");
    public static final BlockStepAbstract WOOD_STEP = (BlockStepAbstract)(new BlockWoodStep(126, false)).c(2.0F).b(5.0F).a(e).b("woodSlab");
    public static final Block COCOA = (new BlockCocoa(127)).c(0.2F).b(5.0F).a(e).b("cocoa").r();
    public static final Block SANDSTONE_STAIRS = (new BlockStairs(128, SANDSTONE, 0)).b("stairsSandStone").r();
    public static final Block EMERALD_ORE = (new BlockOre(129, 171)).c(3.0F).b(5.0F).a(h).b("oreEmerald");
    public static final Block ENDER_CHEST = (new BlockEnderChest(130)).c(22.5F).b(1000.0F).a(h).b("enderChest").r().a(0.5F);
    public static final BlockTripwireHook TRIPWIRE_SOURCE = (BlockTripwireHook)(new BlockTripwireHook(131)).b("tripWireSource").r();
    public static final Block TRIPWIRE = (new BlockTripwire(132)).b("tripWire").r();
    public static final Block EMERALD_BLOCK = (new BlockOreBlock(133, 25)).c(5.0F).b(10.0F).a(i).b("blockEmerald");
    public static final Block SPRUCE_WOOD_STAIRS = (new BlockStairs(134, WOOD, 1)).b("stairsWoodSpruce").r();
    public static final Block BIRCH_WOOD_STAIRS = (new BlockStairs(135, WOOD, 2)).b("stairsWoodBirch").r();
    public static final Block JUNGLE_WOOD_STAIRS = (new BlockStairs(136, WOOD, 3)).b("stairsWoodJungle").r();
    public static final Block COMMAND = (new BlockCommand(137)).b("commandBlock");
    public static final Block BEACON = (new BlockBeacon(138)).b("beacon").a(1.0F);
    public static final Block COBBLE_WALL = (new BlockCobbleWall(139, COBBLESTONE)).b("cobbleWall");
    public static final Block FLOWER_POT = (new BlockFlowerPot(140)).c(0.0F).a(d).b("flowerPot");
    public static final Block CARROTS = (new BlockCarrots(141)).b("carrots");
    public static final Block POTATOES = (new BlockPotatoes(142)).b("potatoes");
    public static final Block WOOD_BUTTON = (new BlockButton(143, WOOD.textureId, true)).c(0.5F).a(e).b("button").r();
    public static final Block SKULL = (new BlockSkull(144)).c(1.0F).a(h).b("skull").r();
    public static final Block ANVIL = (new BlockAnvil(145)).c(5.0F).a(o).b(2000.0F).b("anvil").r();

    /**
     * The index of the texture to be displayed for this block. May vary based on graphics settings. Mostly seems to
     * come from terrain.png, and the index is 0-based (grass is 0).
     */
    public int textureId;

    /** ID of the block. */
    public final int id;

    /** Indicates how many hits it takes to break a block. */
    protected float strength;

    /** Indicates the blocks resistance to explosions. */
    protected float durability;

    /**
     * set to true when Block's constructor is called through the chain of super()'s. Note: Never used
     */
    protected boolean cp;

    /**
     * If this field is true, the block is counted for statistics (mined or placed)
     */
    protected boolean cq;

    /**
     * Flags whether or not this block is of a type that needs random ticking. Ref-counted by ExtendedBlockStorage in
     * order to broadly cull a chunk from the random chunk update list for efficiency's sake.
     */
    protected boolean cr;

    /** true if the Block contains a Tile Entity */
    protected boolean isTileEntity;

    /** minimum X for the block bounds (local coordinates) */
    protected double minX;

    /** minimum Y for the block bounds (local coordinates) */
    protected double minY;

    /** minimum Z for the block bounds (local coordinates) */
    protected double minZ;

    /** maximum X for the block bounds (local coordinates) */
    protected double maxX;

    /** maximum Y for the block bounds (local coordinates) */
    protected double maxY;

    /** maximum Z for the block bounds (local coordinates) */
    protected double maxZ;

    /** Sound of stepping on the block */
    public StepSound stepSound;
    public float cA;

    /** Block material definition. */
    public final Material material;

    /**
     * Determines how much velocity is maintained while moving on top of this block
     */
    public float frictionFactor;
    private String name;

    protected Block(int par1, Material par2Material)
    {
        this.cp = true;
        this.cq = true;
        this.stepSound = d;
        this.cA = 1.0F;
        this.frictionFactor = 0.6F;

        if (byId[par1] != null)
        {
            throw new IllegalArgumentException("Slot " + par1 + " is already occupied by " + byId[par1] + " when adding " + this);
        }
        else
        {
            this.material = par2Material;
            byId[par1] = this;
            this.id = par1;
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            q[par1] = this.c();
            lightBlock[par1] = this.c() ? 255 : 0;
            s[par1] = !par2Material.blocksLight();
        }
    }

    /**
     * Blocks with this attribute will not notify all near blocks when it's metadata change. The default behavior is
     * always notify every neightbor block when anything changes.
     */
    protected Block r()
    {
        u[this.id] = true;
        return this;
    }

    /**
     * This method is called on a block after all other blocks gets already created. You can use it to reference and
     * configure something on the block that needs the others ones.
     */
    protected void t_() {}

    protected Block(int par1, int par2, Material par3Material)
    {
        this(par1, par3Material);
        this.textureId = par2;
    }

    /**
     * Sets the footstep sound for the block. Returns the object for convenience in constructing.
     */
    protected Block a(StepSound par1StepSound)
    {
        this.stepSound = par1StepSound;
        return this;
    }

    /**
     * Sets how much light is blocked going through this block. Returns the object for convenience in constructing.
     */
    protected Block h(int par1)
    {
        lightBlock[this.id] = par1;
        return this;
    }

    /**
     * Sets the amount of light emitted by a block from 0.0f to 1.0f (converts internally to 0-15). Returns the object
     * for convenience in constructing.
     */
    protected Block a(float par1)
    {
        lightEmission[this.id] = (int)(15.0F * par1);
        return this;
    }

    /**
     * Sets the the blocks resistance to explosions. Returns the object for convenience in constructing.
     */
    protected Block b(float par1)
    {
        this.durability = par1 * 3.0F;
        return this;
    }

    public static boolean i(int par0)
    {
        Block var1 = byId[par0];
        return var1 == null ? false : var1.material.k() && var1.b();
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return true;
    }

    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return !this.material.isSolid();
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 0;
    }

    /**
     * Sets how many hits it takes to break a block.
     */
    protected Block c(float par1)
    {
        this.strength = par1;

        if (this.durability < par1 * 5.0F)
        {
            this.durability = par1 * 5.0F;
        }

        return this;
    }

    /**
     * This method will make the hardness of the block equals to -1, and the block is indestructible.
     */
    protected Block s()
    {
        this.c(-1.0F);
        return this;
    }

    /**
     * Returns the block hardness at a location. Args: world, x, y, z
     */
    public float m(World par1World, int par2, int par3, int par4)
    {
        return this.strength;
    }

    /**
     * Sets whether this block type will receive random update ticks
     */
    protected Block b(boolean par1)
    {
        this.cr = par1;
        return this;
    }

    /**
     * Returns whether or not this block is of a type that needs random ticking. Called for ref-counting purposes by
     * ExtendedBlockStorage in order to broadly cull a chunk from the random chunk update list for efficiency's sake.
     */
    public boolean isTicking()
    {
        return this.cr;
    }

    public boolean u()
    {
        return this.isTileEntity;
    }

    /**
     * Sets the bounds of the block.  minX, minY, minZ, maxX, maxY, maxZ
     */
    protected final void a(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        this.minX = (double)par1;
        this.minY = (double)par2;
        this.minZ = (double)par3;
        this.maxX = (double)par4;
        this.maxY = (double)par5;
        this.maxZ = (double)par6;
    }

    /**
     * Returns Returns true if the given side of this block type should be rendered (if it's solid or not), if the
     * adjacent block is at the given coordinates. Args: blockAccess, x, y, z, side
     */
    public boolean a_(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return par1IBlockAccess.getMaterial(par2, par3, par4).isBuildable();
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return this.a(par1);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return this.textureId;
    }

    /**
     * if the specified block is in the given AABB, add its collision bounding box to the given list
     */
    public void a(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        AxisAlignedBB var8 = this.e(par1World, par2, par3, par4);

        if (var8 != null && par5AxisAlignedBB.a(var8))
        {
            par6List.add(var8);
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        return AxisAlignedBB.a().a((double) par2 + this.minX, (double) par3 + this.minY, (double) par4 + this.minZ, (double) par2 + this.maxX, (double) par3 + this.maxY, (double) par4 + this.maxZ);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean c()
    {
        return true;
    }

    /**
     * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
     */
    public boolean a(int par1, boolean par2)
    {
        return this.m();
    }

    /**
     * Returns if this block is collidable (only used by Fire). Args: x, y, z
     */
    public boolean m()
    {
        return true;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random) {}

    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void postBreak(World par1World, int par2, int par3, int par4, int par5) {}

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5) {}

    /**
     * How many world ticks before ticking
     */
    public int r_()
    {
        return 10;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4) {}

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6) {}

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 1;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return this.id;
    }

    /**
     * Gets the hardness of block at the given coordinates in the given world, relative to the ability of the given
     * EntityPlayer.
     */
    public float getDamage(EntityHuman par1EntityPlayer, World par2World, int par3, int par4, int par5)
    {
        float var6 = this.m(par2World, par3, par4, par5);
        return var6 < 0.0F ? 0.0F : (!par1EntityPlayer.b(this) ? 1.0F / var6 / 100.0F : par1EntityPlayer.a(this) / var6 / 30.0F);
    }

    /**
     * Drops the specified block items
     */
    public final void c(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        this.dropNaturally(par1World, par2, par3, par4, par5, 1.0F, par6);
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (!par1World.isStatic)
        {
            int var8 = this.getDropCount(par7, par1World.random);

            for (int var9 = 0; var9 < var8; ++var9)
            {
                if (par1World.random.nextFloat() <= par6)
                {
                    int var10 = this.getDropType(par5, par1World.random, par7);

                    if (var10 > 0)
                    {
                        this.b(par1World, par2, par3, par4, new ItemStack(var10, 1, this.getDropData(par5)));
                    }
                }
            }
        }
    }

    /**
     * Spawns EntityItem in the world for the given ItemStack if the world is not remote.
     */
    protected void b(World par1World, int par2, int par3, int par4, ItemStack par5ItemStack)
    {
        if (!par1World.isStatic && par1World.getGameRules().getBoolean("doTileDrops"))
        {
            float var6 = 0.7F;
            double var7 = (double)(par1World.random.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
            double var9 = (double)(par1World.random.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
            double var11 = (double)(par1World.random.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
            EntityItem var13 = new EntityItem(par1World, (double)par2 + var7, (double)par3 + var9, (double)par4 + var11, par5ItemStack);
            var13.pickupDelay = 10;
            par1World.addEntity(var13);
        }
    }

    /**
     * called by spawner, ore, redstoneOre blocks
     */
    protected void f(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isStatic)
        {
            while (par5 > 0)
            {
                int var6 = EntityExperienceOrb.getOrbValue(par5);
                par5 -= var6;
                par1World.addEntity(new EntityExperienceOrb(par1World, (double) par2 + 0.5D, (double) par3 + 0.5D, (double) par4 + 0.5D, var6));
            }
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int getDropData(int par1)
    {
        return 0;
    }

    /**
     * Returns how much this block can resist explosions from the passed in entity.
     */
    public float a(Entity par1Entity)
    {
        return this.durability / 5.0F;
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition a(World par1World, int par2, int par3, int par4, Vec3D par5Vec3, Vec3D par6Vec3)
    {
        this.updateShape(par1World, par2, par3, par4);
        par5Vec3 = par5Vec3.add((double) (-par2), (double) (-par3), (double) (-par4));
        par6Vec3 = par6Vec3.add((double) (-par2), (double) (-par3), (double) (-par4));
        Vec3D var7 = par5Vec3.b(par6Vec3, this.minX);
        Vec3D var8 = par5Vec3.b(par6Vec3, this.maxX);
        Vec3D var9 = par5Vec3.c(par6Vec3, this.minY);
        Vec3D var10 = par5Vec3.c(par6Vec3, this.maxY);
        Vec3D var11 = par5Vec3.d(par6Vec3, this.minZ);
        Vec3D var12 = par5Vec3.d(par6Vec3, this.maxZ);

        if (!this.a(var7))
        {
            var7 = null;
        }

        if (!this.a(var8))
        {
            var8 = null;
        }

        if (!this.b(var9))
        {
            var9 = null;
        }

        if (!this.b(var10))
        {
            var10 = null;
        }

        if (!this.c(var11))
        {
            var11 = null;
        }

        if (!this.c(var12))
        {
            var12 = null;
        }

        Vec3D var13 = null;

        if (var7 != null && (var13 == null || par5Vec3.distanceSquared(var7) < par5Vec3.distanceSquared(var13)))
        {
            var13 = var7;
        }

        if (var8 != null && (var13 == null || par5Vec3.distanceSquared(var8) < par5Vec3.distanceSquared(var13)))
        {
            var13 = var8;
        }

        if (var9 != null && (var13 == null || par5Vec3.distanceSquared(var9) < par5Vec3.distanceSquared(var13)))
        {
            var13 = var9;
        }

        if (var10 != null && (var13 == null || par5Vec3.distanceSquared(var10) < par5Vec3.distanceSquared(var13)))
        {
            var13 = var10;
        }

        if (var11 != null && (var13 == null || par5Vec3.distanceSquared(var11) < par5Vec3.distanceSquared(var13)))
        {
            var13 = var11;
        }

        if (var12 != null && (var13 == null || par5Vec3.distanceSquared(var12) < par5Vec3.distanceSquared(var13)))
        {
            var13 = var12;
        }

        if (var13 == null)
        {
            return null;
        }
        else
        {
            byte var14 = -1;

            if (var13 == var7)
            {
                var14 = 4;
            }

            if (var13 == var8)
            {
                var14 = 5;
            }

            if (var13 == var9)
            {
                var14 = 0;
            }

            if (var13 == var10)
            {
                var14 = 1;
            }

            if (var13 == var11)
            {
                var14 = 2;
            }

            if (var13 == var12)
            {
                var14 = 3;
            }

            return new MovingObjectPosition(par2, par3, par4, var14, var13.add((double) par2, (double) par3, (double) par4));
        }
    }

    /**
     * Checks if a vector is within the Y and Z bounds of the block.
     */
    private boolean a(Vec3D par1Vec3)
    {
        return par1Vec3 == null ? false : par1Vec3.d >= this.minY && par1Vec3.d <= this.maxY && par1Vec3.e >= this.minZ && par1Vec3.e <= this.maxZ;
    }

    /**
     * Checks if a vector is within the X and Z bounds of the block.
     */
    private boolean b(Vec3D par1Vec3)
    {
        return par1Vec3 == null ? false : par1Vec3.c >= this.minX && par1Vec3.c <= this.maxX && par1Vec3.e >= this.minZ && par1Vec3.e <= this.maxZ;
    }

    /**
     * Checks if a vector is within the X and Y bounds of the block.
     */
    private boolean c(Vec3D par1Vec3)
    {
        return par1Vec3 == null ? false : par1Vec3.c >= this.minX && par1Vec3.c <= this.maxX && par1Vec3.d >= this.minY && par1Vec3.d <= this.maxY;
    }

    /**
     * Called upon the block being destroyed by an explosion
     */
    public void wasExploded(World par1World, int par2, int par3, int par4) {}

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4, int par5)
    {
        return this.canPlace(par1World, par2, par3, par4);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getTypeId(par2, par3, par4);
        return var5 == 0 || byId[var5].material.isReplaceable();
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        return false;
    }

    /**
     * Called whenever an entity is walking on top of this block. Args: world, x, y, z, entity
     */
    public void b(World par1World, int par2, int par3, int par4, Entity par5Entity) {}

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int getPlacedData(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        return par9;
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void attack(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer) {}

    /**
     * Can add to the passed in vector for a movement vector to be applied to the entity. Args: x, y, z, entity, vec3d
     */
    public void a(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3D par6Vec3) {}

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {}

    public final double v()
    {
        return this.minX;
    }

    /**
     * returns the block bounderies maxX value
     */
    public final double w()
    {
        return this.maxX;
    }

    /**
     * returns the block bounderies minY value
     */
    public final double x()
    {
        return this.minY;
    }

    /**
     * returns the block bounderies maxY value
     */
    public final double y()
    {
        return this.maxY;
    }

    /**
     * returns the block bounderies minZ value
     */
    public final double z()
    {
        return this.minZ;
    }

    /**
     * returns the block bounderies maxZ value
     */
    public final double A()
    {
        return this.maxZ;
    }

    /**
     * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
     * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
     * Y, Z, side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean b(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return false;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean isPowerSource()
    {
        return false;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void a(World par1World, int par2, int par3, int par4, Entity par5Entity) {}

    /**
     * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
     * side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return false;
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void f() {}

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void a(World par1World, EntityHuman par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        par2EntityPlayer.a(StatisticList.C[this.id], 1);
        par2EntityPlayer.j(0.025F);

        if (this.s_() && EnchantmentManager.hasSilkTouchEnchantment(par2EntityPlayer))
        {
            ItemStack var8 = this.f_(par6);

            if (var8 != null)
            {
                this.b(par1World, par3, par4, par5, var8);
            }
        }
        else
        {
            int var7 = EnchantmentManager.getBonusBlockLootEnchantmentLevel(par2EntityPlayer);
            this.c(par1World, par3, par4, par5, par6, var7);
        }
    }

    /**
     * Return true if a player with Silk Touch can harvest this block directly, and not its normal drops.
     */
    protected boolean s_()
    {
        return this.b() && !this.isTileEntity;
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack f_(int par1)
    {
        int var2 = 0;

        if (this.id >= 0 && this.id < Item.byId.length && Item.byId[this.id].l())
        {
            var2 = par1;
        }

        return new ItemStack(this.id, 1, var2);
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int getDropCount(int par1, Random par2Random)
    {
        return this.a(par2Random);
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean d(World par1World, int par2, int par3, int par4)
    {
        return true;
    }

    /**
     * Called when the block is placed in the world.
     */
    public void postPlace(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving) {}

    /**
     * Called after a block is placed
     */
    public void postPlace(World par1World, int par2, int par3, int par4, int par5) {}

    /**
     * set name of block from language file
     */
    public Block b(String par1Str)
    {
        this.name = "tile." + par1Str;
        return this;
    }

    /**
     * gets the localized version of the name of this block using StatCollector.translateToLocal. Used for the statistic
     * page.
     */
    public String getName()
    {
        return LocaleI18n.get(this.a() + ".name");
    }

    public String a()
    {
        return this.name;
    }

    /**
     * Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
     * entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
     */
    public void b(World par1World, int par2, int par3, int par4, int par5, int par6) {}

    /**
     * Return the state of blocks statistics flags - if the block is counted for mined and placed.
     */
    public boolean C()
    {
        return this.cq;
    }

    /**
     * Disable statistics for the block, the block will no count for mined or placed.
     */
    protected Block D()
    {
        this.cq = false;
        return this;
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int q_()
    {
        return this.material.getPushReaction();
    }

    /**
     * Block's chance to react to an entity falling on it.
     */
    public void a(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6) {}

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDropData(World par1World, int par2, int par3, int par4)
    {
        return this.getDropData(par1World.getData(par2, par3, par4));
    }

    /**
     * Sets the CreativeTab to display this block on.
     */
    public Block a(CreativeModeTab par1CreativeTabs)
    {
        this.creativeTab = par1CreativeTabs;
        return this;
    }

    /**
     * Called when the block is attempted to be harvested
     */
    public void a(World par1World, int par2, int par3, int par4, int par5, EntityHuman par6EntityPlayer) {}

    /**
     * Called when this block is set (with meta data).
     */
    public void h(World par1World, int par2, int par3, int par4, int par5) {}

    /**
     * currently only used by BlockCauldron to incrament meta-data during rain
     */
    public void f(World par1World, int par2, int par3, int par4) {}

    public boolean l()
    {
        return true;
    }

    /**
     * Return whether this block can drop from an explosion.
     */
    public boolean a(Explosion par1Explosion)
    {
        return true;
    }

    static
    {
        Item.byId[WOOL.id] = (new ItemCloth(WOOL.id - 256)).b("cloth");
        Item.byId[LOG.id] = (new ItemMultiTexture(LOG.id - 256, LOG, BlockLog.a)).b("log");
        Item.byId[WOOD.id] = (new ItemMultiTexture(WOOD.id - 256, WOOD, BlockWood.a)).b("wood");
        Item.byId[MONSTER_EGGS.id] = (new ItemMultiTexture(MONSTER_EGGS.id - 256, MONSTER_EGGS, BlockMonsterEggs.a)).b("monsterStoneEgg");
        Item.byId[SMOOTH_BRICK.id] = (new ItemMultiTexture(SMOOTH_BRICK.id - 256, SMOOTH_BRICK, BlockSmoothBrick.a)).b("stonebricksmooth");
        Item.byId[SANDSTONE.id] = (new ItemMultiTexture(SANDSTONE.id - 256, SANDSTONE, BlockSandStone.a)).b("sandStone");
        Item.byId[STEP.id] = (new ItemStep(STEP.id - 256, STEP, DOUBLE_STEP, false)).b("stoneSlab");
        Item.byId[DOUBLE_STEP.id] = (new ItemStep(DOUBLE_STEP.id - 256, STEP, DOUBLE_STEP, true)).b("stoneSlab");
        Item.byId[WOOD_STEP.id] = (new ItemStep(WOOD_STEP.id - 256, WOOD_STEP, WOOD_DOUBLE_STEP, false)).b("woodSlab");
        Item.byId[WOOD_DOUBLE_STEP.id] = (new ItemStep(WOOD_DOUBLE_STEP.id - 256, WOOD_STEP, WOOD_DOUBLE_STEP, true)).b("woodSlab");
        Item.byId[SAPLING.id] = (new ItemMultiTexture(SAPLING.id - 256, SAPLING, BlockSapling.a)).b("sapling");
        Item.byId[LEAVES.id] = (new ItemLeaves(LEAVES.id - 256)).b("leaves");
        Item.byId[VINE.id] = new ItemWithAuxData(VINE.id - 256, false);
        Item.byId[LONG_GRASS.id] = (new ItemWithAuxData(LONG_GRASS.id - 256, true)).a(new String[]{"shrub", "grass", "fern"});
        Item.byId[WATER_LILY.id] = new ItemWaterLily(WATER_LILY.id - 256);
        Item.byId[PISTON.id] = new ItemPiston(PISTON.id - 256);
        Item.byId[PISTON_STICKY.id] = new ItemPiston(PISTON_STICKY.id - 256);
        Item.byId[COBBLE_WALL.id] = (new ItemMultiTexture(COBBLE_WALL.id - 256, COBBLE_WALL, BlockCobbleWall.a)).b("cobbleWall");
        Item.byId[ANVIL.id] = (new ItemAnvil(ANVIL)).b("anvil");

        for (int var0 = 0; var0 < 256; ++var0)
        {
            if (byId[var0] != null)
            {
                if (Item.byId[var0] == null)
                {
                    Item.byId[var0] = new ItemBlock(var0 - 256);
                    byId[var0].t_();
                }

                boolean var1 = false;

                if (var0 > 0 && byId[var0].d() == 10)
                {
                    var1 = true;
                }

                if (var0 > 0 && byId[var0] instanceof BlockStepAbstract)
                {
                    var1 = true;
                }

                if (var0 == SOIL.id)
                {
                    var1 = true;
                }

                if (s[var0])
                {
                    var1 = true;
                }

                if (lightBlock[var0] == 0)
                {
                    var1 = true;
                }

                v[var0] = var1;
            }
        }

        s[0] = true;
        StatisticList.b();
    }
}
