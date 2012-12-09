package net.minecraft.server;

import java.util.List;

public class BlockPiston extends Block
{
    /** This pistons is the sticky one? */
    private boolean a;

    public BlockPiston(int par1, int par2, boolean par3)
    {
        super(par1, par2, Material.PISTON);
        this.a = par3;
        this.a(h);
        this.c(0.5F);
        this.a(CreativeModeTab.d);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        int var3 = e(par2);
        return var3 > 5 ? this.textureId : (par1 == var3 ? (!f(par2) && this.minX <= 0.0D && this.minY <= 0.0D && this.minZ <= 0.0D && this.maxX >= 1.0D && this.maxY >= 1.0D && this.maxZ >= 1.0D ? this.textureId : 110) : (par1 == Facing.OPPOSITE_FACING[var3] ? 109 : 108));
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 16;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean c()
    {
        return false;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        return false;
    }

    /**
     * Called when the block is placed in the world.
     */
    public void postPlace(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = b(par1World, par2, par3, par4, (EntityHuman) par5EntityLiving);
        par1World.setData(par2, par3, par4, var6);

        if (!par1World.isStatic)
        {
            this.l(par1World, par2, par3, par4);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isStatic)
        {
            this.l(par1World, par2, par3, par4);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isStatic && par1World.getTileEntity(par2, par3, par4) == null)
        {
            this.l(par1World, par2, par3, par4);
        }
    }

    /**
     * handles attempts to extend or retract the piston.
     */
    private void l(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getData(par2, par3, par4);
        int var6 = e(var5);

        if (var6 != 7)
        {
            boolean var7 = this.d(par1World, par2, par3, par4, var6);

            if (var7 && !f(var5))
            {
                if (i(par1World, par2, par3, par4, var6))
                {
                    par1World.playNote(par2, par3, par4, this.id, 0, var6);
                }
            }
            else if (!var7 && f(var5))
            {
                par1World.playNote(par2, par3, par4, this.id, 1, var6);
            }
        }
    }

    /**
     * checks the block to that side to see if it is indirectly powered.
     */
    private boolean d(World par1World, int par2, int par3, int par4, int par5)
    {
        return par5 != 0 && par1World.isBlockFaceIndirectlyPowered(par2, par3 - 1, par4, 0) ? true : (par5 != 1 && par1World.isBlockFaceIndirectlyPowered(par2, par3 + 1, par4, 1) ? true : (par5 != 2 && par1World.isBlockFaceIndirectlyPowered(par2, par3, par4 - 1, 2) ? true : (par5 != 3 && par1World.isBlockFaceIndirectlyPowered(par2, par3, par4 + 1, 3) ? true : (par5 != 5 && par1World.isBlockFaceIndirectlyPowered(par2 + 1, par3, par4, 5) ? true : (par5 != 4 && par1World.isBlockFaceIndirectlyPowered(par2 - 1, par3, par4, 4) ? true : (par1World.isBlockFaceIndirectlyPowered(par2, par3, par4, 0) ? true : (par1World.isBlockFaceIndirectlyPowered(par2, par3 + 2, par4, 1) ? true : (par1World.isBlockFaceIndirectlyPowered(par2, par3 + 1, par4 - 1, 2) ? true : (par1World.isBlockFaceIndirectlyPowered(par2, par3 + 1, par4 + 1, 3) ? true : (par1World.isBlockFaceIndirectlyPowered(par2 - 1, par3 + 1, par4, 4) ? true : par1World.isBlockFaceIndirectlyPowered(par2 + 1, par3 + 1, par4, 5)))))))))));
    }

    /**
     * Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
     * entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
     */
    public void b(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (par5 == 0)
        {
            par1World.setRawData(par2, par3, par4, par6 | 8);
        }
        else
        {
            par1World.setRawData(par2, par3, par4, par6);
        }

        if (par5 == 0)
        {
            if (this.j(par1World, par2, par3, par4, par6))
            {
                par1World.setData(par2, par3, par4, par6 | 8);
                par1World.makeSound((double) par2 + 0.5D, (double) par3 + 0.5D, (double) par4 + 0.5D, "tile.piston.out", 0.5F, par1World.random.nextFloat() * 0.25F + 0.6F);
            }
            else
            {
                par1World.setRawData(par2, par3, par4, par6);
            }
        }
        else if (par5 == 1)
        {
            TileEntity var7 = par1World.getTileEntity(par2 + Facing.b[par6], par3 + Facing.c[par6], par4 + Facing.d[par6]);

            if (var7 instanceof TileEntityPiston)
            {
                ((TileEntityPiston)var7).f();
            }

            par1World.setRawTypeIdAndData(par2, par3, par4, Block.PISTON_MOVING.id, par6);
            par1World.setTileEntity(par2, par3, par4, BlockPistonMoving.a(this.id, par6, par6, false, true));

            if (this.a)
            {
                int var8 = par2 + Facing.b[par6] * 2;
                int var9 = par3 + Facing.c[par6] * 2;
                int var10 = par4 + Facing.d[par6] * 2;
                int var11 = par1World.getTypeId(var8, var9, var10);
                int var12 = par1World.getData(var8, var9, var10);
                boolean var13 = false;

                if (var11 == Block.PISTON_MOVING.id)
                {
                    TileEntity var14 = par1World.getTileEntity(var8, var9, var10);

                    if (var14 instanceof TileEntityPiston)
                    {
                        TileEntityPiston var15 = (TileEntityPiston)var14;

                        if (var15.c() == par6 && var15.b())
                        {
                            var15.f();
                            var11 = var15.a();
                            var12 = var15.p();
                            var13 = true;
                        }
                    }
                }

                if (!var13 && var11 > 0 && a(var11, par1World, var8, var9, var10, false) && (Block.byId[var11].q_() == 0 || var11 == Block.PISTON.id || var11 == Block.PISTON_STICKY.id))
                {
                    par2 += Facing.b[par6];
                    par3 += Facing.c[par6];
                    par4 += Facing.d[par6];
                    par1World.setRawTypeIdAndData(par2, par3, par4, Block.PISTON_MOVING.id, var12);
                    par1World.setTileEntity(par2, par3, par4, BlockPistonMoving.a(var11, var12, par6, false, false));
                    par1World.setTypeId(var8, var9, var10, 0);
                }
                else if (!var13)
                {
                    par1World.setTypeId(par2 + Facing.b[par6], par3 + Facing.c[par6], par4 + Facing.d[par6], 0);
                }
            }
            else
            {
                par1World.setTypeId(par2 + Facing.b[par6], par3 + Facing.c[par6], par4 + Facing.d[par6], 0);
            }

            par1World.makeSound((double) par2 + 0.5D, (double) par3 + 0.5D, (double) par4 + 0.5D, "tile.piston.in", 0.5F, par1World.random.nextFloat() * 0.15F + 0.6F);
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getData(par2, par3, par4);

        if (f(var5))
        {
            switch (e(var5))
            {
                case 0:
                    this.a(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
                    break;

                case 1:
                    this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                    break;

                case 2:
                    this.a(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
                    break;

                case 3:
                    this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
                    break;

                case 4:
                    this.a(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                    break;

                case 5:
                    this.a(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
            }
        }
        else
        {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void f()
    {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * if the specified block is in the given AABB, add its collision bounding box to the given list
     */
    public void a(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        this.updateShape(par1World, par2, par3, par4);
        return super.e(par1World, par2, par3, par4);
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * returns an int which describes the direction the piston faces
     */
    public static int e(int par0)
    {
        return par0 & 7;
    }

    /**
     * Determine if the metadata is related to something powered.
     */
    public static boolean f(int par0)
    {
        return (par0 & 8) != 0;
    }

    /**
     * gets the way this piston should face for that entity that placed it.
     */
    public static int b(World par0World, int par1, int par2, int par3, EntityHuman par4EntityPlayer)
    {
        if (MathHelper.abs((float) par4EntityPlayer.locX - (float) par1) < 2.0F && MathHelper.abs((float) par4EntityPlayer.locZ - (float) par3) < 2.0F)
        {
            double var5 = par4EntityPlayer.locY + 1.82D - (double)par4EntityPlayer.height;

            if (var5 - (double)par2 > 2.0D)
            {
                return 1;
            }

            if ((double)par2 - var5 > 0.0D)
            {
                return 0;
            }
        }

        int var7 = MathHelper.floor((double) (par4EntityPlayer.yaw * 4.0F / 360.0F) + 0.5D) & 3;
        return var7 == 0 ? 2 : (var7 == 1 ? 5 : (var7 == 2 ? 3 : (var7 == 3 ? 4 : 0)));
    }

    /**
     * returns true if the piston can push the specified block
     */
    private static boolean a(int par0, World par1World, int par2, int par3, int par4, boolean par5)
    {
        if (par0 == Block.OBSIDIAN.id)
        {
            return false;
        }
        else
        {
            if (par0 != Block.PISTON.id && par0 != Block.PISTON_STICKY.id)
            {
                if (Block.byId[par0].m(par1World, par2, par3, par4) == -1.0F)
                {
                    return false;
                }

                if (Block.byId[par0].q_() == 2)
                {
                    return false;
                }

                if (!par5 && Block.byId[par0].q_() == 1)
                {
                    return false;
                }
            }
            else if (f(par1World.getData(par2, par3, par4)))
            {
                return false;
            }

            return !(Block.byId[par0] instanceof BlockContainer);
        }
    }

    /**
     * checks to see if this piston could push the blocks in front of it.
     */
    private static boolean i(World par0World, int par1, int par2, int par3, int par4)
    {
        int var5 = par1 + Facing.b[par4];
        int var6 = par2 + Facing.c[par4];
        int var7 = par3 + Facing.d[par4];
        int var8 = 0;

        while (true)
        {
            if (var8 < 13)
            {
                if (var6 <= 0 || var6 >= 255)
                {
                    return false;
                }

                int var9 = par0World.getTypeId(var5, var6, var7);

                if (var9 != 0)
                {
                    if (!a(var9, par0World, var5, var6, var7, true))
                    {
                        return false;
                    }

                    if (Block.byId[var9].q_() != 1)
                    {
                        if (var8 == 12)
                        {
                            return false;
                        }

                        var5 += Facing.b[par4];
                        var6 += Facing.c[par4];
                        var7 += Facing.d[par4];
                        ++var8;
                        continue;
                    }
                }
            }

            return true;
        }
    }

    /**
     * attempts to extend the piston. returns false if impossible.
     */
    private boolean j(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = par2 + Facing.b[par5];
        int var7 = par3 + Facing.c[par5];
        int var8 = par4 + Facing.d[par5];
        int var9 = 0;

        while (true)
        {
            int var10;

            if (var9 < 13)
            {
                if (var7 <= 0 || var7 >= 255)
                {
                    return false;
                }

                var10 = par1World.getTypeId(var6, var7, var8);

                if (var10 != 0)
                {
                    if (!a(var10, par1World, var6, var7, var8, true))
                    {
                        return false;
                    }

                    if (Block.byId[var10].q_() != 1)
                    {
                        if (var9 == 12)
                        {
                            return false;
                        }

                        var6 += Facing.b[par5];
                        var7 += Facing.c[par5];
                        var8 += Facing.d[par5];
                        ++var9;
                        continue;
                    }

                    Block.byId[var10].c(par1World, var6, var7, var8, par1World.getData(var6, var7, var8), 0);
                    par1World.setTypeId(var6, var7, var8, 0);
                }
            }

            while (var6 != par2 || var7 != par3 || var8 != par4)
            {
                var9 = var6 - Facing.b[par5];
                var10 = var7 - Facing.c[par5];
                int var11 = var8 - Facing.d[par5];
                int var12 = par1World.getTypeId(var9, var10, var11);
                int var13 = par1World.getData(var9, var10, var11);

                if (var12 == this.id && var9 == par2 && var10 == par3 && var11 == par4)
                {
                    par1World.setRawTypeIdAndData(var6, var7, var8, Block.PISTON_MOVING.id, par5 | (this.a ? 8 : 0), false);
                    par1World.setTileEntity(var6, var7, var8, BlockPistonMoving.a(Block.PISTON_EXTENSION.id, par5 | (this.a ? 8 : 0), par5, true, false));
                }
                else
                {
                    par1World.setRawTypeIdAndData(var6, var7, var8, Block.PISTON_MOVING.id, var13, false);
                    par1World.setTileEntity(var6, var7, var8, BlockPistonMoving.a(var12, var13, par5, true, false));
                }

                var6 = var9;
                var7 = var10;
                var8 = var11;
            }

            return true;
        }
    }
}
