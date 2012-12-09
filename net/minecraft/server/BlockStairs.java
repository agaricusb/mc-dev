package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class BlockStairs extends Block
{
    private static final int[][] a = new int[][] {{2, 6}, {3, 7}, {2, 3}, {6, 7}, {0, 4}, {1, 5}, {0, 1}, {4, 5}};
    private static final int[] b = new int[] {1, -1, 0, 0};
    private static final int[] c = new int[] {0, 0, 1, -1};

    /** The block that is used as model for the stair. */
    private final Block cD;
    private final int cE;
    private boolean cF = false;
    private int cG = 0;

    protected BlockStairs(int par1, Block par2Block, int par3)
    {
        super(par1, par2Block.textureId, par2Block.material);
        this.cD = par2Block;
        this.cE = par3;
        this.c(par2Block.strength);
        this.b(par2Block.durability / 3.0F);
        this.a(par2Block.stepSound);
        this.h(255);
        this.a(CreativeModeTab.b);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        if (this.cF)
        {
            this.a(0.5F * (float) (this.cG % 2), 0.5F * (float) (this.cG / 2 % 2), 0.5F * (float) (this.cG / 4 % 2), 0.5F + 0.5F * (float) (this.cG % 2), 0.5F + 0.5F * (float) (this.cG / 2 % 2), 0.5F + 0.5F * (float) (this.cG / 4 % 2));
        }
        else
        {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
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
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 10;
    }

    public void d(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getData(par2, par3, par4);

        if ((var5 & 4) != 0)
        {
            this.a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    public static boolean e(int par0)
    {
        return par0 > 0 && Block.byId[par0] instanceof BlockStairs;
    }

    private boolean f(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        int var6 = par1IBlockAccess.getTypeId(par2, par3, par4);
        return e(var6) && par1IBlockAccess.getData(par2, par3, par4) == par5;
    }

    public boolean g(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getData(par2, par3, par4);
        int var6 = var5 & 3;
        float var7 = 0.5F;
        float var8 = 1.0F;

        if ((var5 & 4) != 0)
        {
            var7 = 0.0F;
            var8 = 0.5F;
        }

        float var9 = 0.0F;
        float var10 = 1.0F;
        float var11 = 0.0F;
        float var12 = 0.5F;
        boolean var13 = true;
        int var14;
        int var15;
        int var16;

        if (var6 == 0)
        {
            var9 = 0.5F;
            var12 = 1.0F;
            var14 = par1IBlockAccess.getTypeId(par2 + 1, par3, par4);
            var15 = par1IBlockAccess.getData(par2 + 1, par3, par4);

            if (e(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 3 && !this.f(par1IBlockAccess, par2, par3, par4 + 1, var5))
                {
                    var12 = 0.5F;
                    var13 = false;
                }
                else if (var16 == 2 && !this.f(par1IBlockAccess, par2, par3, par4 - 1, var5))
                {
                    var11 = 0.5F;
                    var13 = false;
                }
            }
        }
        else if (var6 == 1)
        {
            var10 = 0.5F;
            var12 = 1.0F;
            var14 = par1IBlockAccess.getTypeId(par2 - 1, par3, par4);
            var15 = par1IBlockAccess.getData(par2 - 1, par3, par4);

            if (e(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 3 && !this.f(par1IBlockAccess, par2, par3, par4 + 1, var5))
                {
                    var12 = 0.5F;
                    var13 = false;
                }
                else if (var16 == 2 && !this.f(par1IBlockAccess, par2, par3, par4 - 1, var5))
                {
                    var11 = 0.5F;
                    var13 = false;
                }
            }
        }
        else if (var6 == 2)
        {
            var11 = 0.5F;
            var12 = 1.0F;
            var14 = par1IBlockAccess.getTypeId(par2, par3, par4 + 1);
            var15 = par1IBlockAccess.getData(par2, par3, par4 + 1);

            if (e(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 1 && !this.f(par1IBlockAccess, par2 + 1, par3, par4, var5))
                {
                    var10 = 0.5F;
                    var13 = false;
                }
                else if (var16 == 0 && !this.f(par1IBlockAccess, par2 - 1, par3, par4, var5))
                {
                    var9 = 0.5F;
                    var13 = false;
                }
            }
        }
        else if (var6 == 3)
        {
            var14 = par1IBlockAccess.getTypeId(par2, par3, par4 - 1);
            var15 = par1IBlockAccess.getData(par2, par3, par4 - 1);

            if (e(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 1 && !this.f(par1IBlockAccess, par2 + 1, par3, par4, var5))
                {
                    var10 = 0.5F;
                    var13 = false;
                }
                else if (var16 == 0 && !this.f(par1IBlockAccess, par2 - 1, par3, par4, var5))
                {
                    var9 = 0.5F;
                    var13 = false;
                }
            }
        }

        this.a(var9, var7, var11, var10, var8, var12);
        return var13;
    }

    public boolean h(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getData(par2, par3, par4);
        int var6 = var5 & 3;
        float var7 = 0.5F;
        float var8 = 1.0F;

        if ((var5 & 4) != 0)
        {
            var7 = 0.0F;
            var8 = 0.5F;
        }

        float var9 = 0.0F;
        float var10 = 0.5F;
        float var11 = 0.5F;
        float var12 = 1.0F;
        boolean var13 = false;
        int var14;
        int var15;
        int var16;

        if (var6 == 0)
        {
            var14 = par1IBlockAccess.getTypeId(par2 - 1, par3, par4);
            var15 = par1IBlockAccess.getData(par2 - 1, par3, par4);

            if (e(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 3 && !this.f(par1IBlockAccess, par2, par3, par4 - 1, var5))
                {
                    var11 = 0.0F;
                    var12 = 0.5F;
                    var13 = true;
                }
                else if (var16 == 2 && !this.f(par1IBlockAccess, par2, par3, par4 + 1, var5))
                {
                    var11 = 0.5F;
                    var12 = 1.0F;
                    var13 = true;
                }
            }
        }
        else if (var6 == 1)
        {
            var14 = par1IBlockAccess.getTypeId(par2 + 1, par3, par4);
            var15 = par1IBlockAccess.getData(par2 + 1, par3, par4);

            if (e(var14) && (var5 & 4) == (var15 & 4))
            {
                var9 = 0.5F;
                var10 = 1.0F;
                var16 = var15 & 3;

                if (var16 == 3 && !this.f(par1IBlockAccess, par2, par3, par4 - 1, var5))
                {
                    var11 = 0.0F;
                    var12 = 0.5F;
                    var13 = true;
                }
                else if (var16 == 2 && !this.f(par1IBlockAccess, par2, par3, par4 + 1, var5))
                {
                    var11 = 0.5F;
                    var12 = 1.0F;
                    var13 = true;
                }
            }
        }
        else if (var6 == 2)
        {
            var14 = par1IBlockAccess.getTypeId(par2, par3, par4 - 1);
            var15 = par1IBlockAccess.getData(par2, par3, par4 - 1);

            if (e(var14) && (var5 & 4) == (var15 & 4))
            {
                var11 = 0.0F;
                var12 = 0.5F;
                var16 = var15 & 3;

                if (var16 == 1 && !this.f(par1IBlockAccess, par2 - 1, par3, par4, var5))
                {
                    var13 = true;
                }
                else if (var16 == 0 && !this.f(par1IBlockAccess, par2 + 1, par3, par4, var5))
                {
                    var9 = 0.5F;
                    var10 = 1.0F;
                    var13 = true;
                }
            }
        }
        else if (var6 == 3)
        {
            var14 = par1IBlockAccess.getTypeId(par2, par3, par4 + 1);
            var15 = par1IBlockAccess.getData(par2, par3, par4 + 1);

            if (e(var14) && (var5 & 4) == (var15 & 4))
            {
                var16 = var15 & 3;

                if (var16 == 1 && !this.f(par1IBlockAccess, par2 - 1, par3, par4, var5))
                {
                    var13 = true;
                }
                else if (var16 == 0 && !this.f(par1IBlockAccess, par2 + 1, par3, par4, var5))
                {
                    var9 = 0.5F;
                    var10 = 1.0F;
                    var13 = true;
                }
            }
        }

        if (var13)
        {
            this.a(var9, var7, var11, var10, var8, var12);
        }

        return var13;
    }

    /**
     * if the specified block is in the given AABB, add its collision bounding box to the given list
     */
    public void a(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        this.d(par1World, par2, par3, par4);
        super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        boolean var8 = this.g(par1World, par2, par3, par4);
        super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);

        if (var8 && this.h(par1World, par2, par3, par4))
        {
            super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }

        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void attack(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer)
    {
        this.cD.attack(par1World, par2, par3, par4, par5EntityPlayer);
    }

    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void postBreak(World par1World, int par2, int par3, int par4, int par5)
    {
        this.cD.postBreak(par1World, par2, par3, par4, par5);
    }

    /**
     * Returns how much this block can resist explosions from the passed in entity.
     */
    public float a(Entity par1Entity)
    {
        return this.cD.a(par1Entity);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return this.cD.a(par1, this.cE);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return this.cD.a(par1, this.cE);
    }

    /**
     * How many world ticks before ticking
     */
    public int r_()
    {
        return this.cD.r_();
    }

    /**
     * Can add to the passed in vector for a movement vector to be applied to the entity. Args: x, y, z, entity, vec3d
     */
    public void a(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3D par6Vec3)
    {
        this.cD.a(par1World, par2, par3, par4, par5Entity, par6Vec3);
    }

    /**
     * Returns if this block is collidable (only used by Fire). Args: x, y, z
     */
    public boolean m()
    {
        return this.cD.m();
    }

    /**
     * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
     */
    public boolean a(int par1, boolean par2)
    {
        return this.cD.a(par1, par2);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return this.cD.canPlace(par1World, par2, par3, par4);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        this.doPhysics(par1World, par2, par3, par4, 0);
        this.cD.onPlace(par1World, par2, par3, par4);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        this.cD.remove(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Called whenever an entity is walking on top of this block. Args: world, x, y, z, entity
     */
    public void b(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        this.cD.b(par1World, par2, par3, par4, par5Entity);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        this.cD.b(par1World, par2, par3, par4, par5Random);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        return this.cD.interact(par1World, par2, par3, par4, par5EntityPlayer, 0, 0.0F, 0.0F, 0.0F);
    }

    /**
     * Called upon the block being destroyed by an explosion
     */
    public void wasExploded(World par1World, int par2, int par3, int par4)
    {
        this.cD.wasExploded(par1World, par2, par3, par4);
    }

    /**
     * Called when the block is placed in the world.
     */
    public void postPlace(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = MathHelper.floor((double) (par5EntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 3;
        int var7 = par1World.getData(par2, par3, par4) & 4;

        if (var6 == 0)
        {
            par1World.setData(par2, par3, par4, 2 | var7);
        }

        if (var6 == 1)
        {
            par1World.setData(par2, par3, par4, 1 | var7);
        }

        if (var6 == 2)
        {
            par1World.setData(par2, par3, par4, 3 | var7);
        }

        if (var6 == 3)
        {
            par1World.setData(par2, par3, par4, 0 | var7);
        }
    }

    public int getPlacedData(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        return par5 != 0 && (par5 == 1 || (double)par7 <= 0.5D) ? par9 : par9 | 4;
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition a(World par1World, int par2, int par3, int par4, Vec3D par5Vec3, Vec3D par6Vec3)
    {
        MovingObjectPosition[] var7 = new MovingObjectPosition[8];
        int var8 = par1World.getData(par2, par3, par4);
        int var9 = var8 & 3;
        boolean var10 = (var8 & 4) == 4;
        int[] var11 = a[var9 + (var10 ? 4 : 0)];
        this.cF = true;
        int var14;
        int var15;
        int var16;

        for (int var12 = 0; var12 < 8; ++var12)
        {
            this.cG = var12;
            int[] var13 = var11;
            var14 = var11.length;

            for (var15 = 0; var15 < var14; ++var15)
            {
                var16 = var13[var15];

                if (var16 == var12)
                {
                    ;
                }
            }

            var7[var12] = super.a(par1World, par2, par3, par4, par5Vec3, par6Vec3);
        }

        int[] var21 = var11;
        int var24 = var11.length;

        for (var14 = 0; var14 < var24; ++var14)
        {
            var15 = var21[var14];
            var7[var15] = null;
        }

        MovingObjectPosition var23 = null;
        double var22 = 0.0D;
        MovingObjectPosition[] var25 = var7;
        var16 = var7.length;

        for (int var17 = 0; var17 < var16; ++var17)
        {
            MovingObjectPosition var18 = var25[var17];

            if (var18 != null)
            {
                double var19 = var18.pos.distanceSquared(par6Vec3);

                if (var19 > var22)
                {
                    var23 = var18;
                    var22 = var19;
                }
            }
        }

        return var23;
    }
}
