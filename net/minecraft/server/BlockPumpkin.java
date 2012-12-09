package net.minecraft.server;

public class BlockPumpkin extends BlockDirectional
{
    /** Boolean used to seperate different states of blocks */
    private boolean a;

    protected BlockPumpkin(int par1, int par2, boolean par3)
    {
        super(par1, Material.PUMPKIN);
        this.textureId = par2;
        this.b(true);
        this.a = par3;
        this.a(CreativeModeTab.b);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        if (par1 == 1)
        {
            return this.textureId;
        }
        else if (par1 == 0)
        {
            return this.textureId;
        }
        else
        {
            int var3 = this.textureId + 1 + 16;

            if (this.a)
            {
                ++var3;
            }

            return par2 == 2 && par1 == 2 ? var3 : (par2 == 3 && par1 == 5 ? var3 : (par2 == 0 && par1 == 3 ? var3 : (par2 == 1 && par1 == 4 ? var3 : this.textureId + 16)));
        }
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return par1 == 1 ? this.textureId : (par1 == 0 ? this.textureId : (par1 == 3 ? this.textureId + 1 + 16 : this.textureId + 16));
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        super.onPlace(par1World, par2, par3, par4);

        if (par1World.getTypeId(par2, par3 - 1, par4) == Block.SNOW_BLOCK.id && par1World.getTypeId(par2, par3 - 2, par4) == Block.SNOW_BLOCK.id)
        {
            if (!par1World.isStatic)
            {
                par1World.setRawTypeId(par2, par3, par4, 0);
                par1World.setRawTypeId(par2, par3 - 1, par4, 0);
                par1World.setRawTypeId(par2, par3 - 2, par4, 0);
                EntitySnowman var9 = new EntitySnowman(par1World);
                var9.setPositionRotation((double) par2 + 0.5D, (double) par3 - 1.95D, (double) par4 + 0.5D, 0.0F, 0.0F);
                par1World.addEntity(var9);
                par1World.update(par2, par3, par4, 0);
                par1World.update(par2, par3 - 1, par4, 0);
                par1World.update(par2, par3 - 2, par4, 0);
            }

            for (int var10 = 0; var10 < 120; ++var10)
            {
                par1World.addParticle("snowshovel", (double) par2 + par1World.random.nextDouble(), (double) (par3 - 2) + par1World.random.nextDouble() * 2.5D, (double) par4 + par1World.random.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        }
        else if (par1World.getTypeId(par2, par3 - 1, par4) == Block.IRON_BLOCK.id && par1World.getTypeId(par2, par3 - 2, par4) == Block.IRON_BLOCK.id)
        {
            boolean var5 = par1World.getTypeId(par2 - 1, par3 - 1, par4) == Block.IRON_BLOCK.id && par1World.getTypeId(par2 + 1, par3 - 1, par4) == Block.IRON_BLOCK.id;
            boolean var6 = par1World.getTypeId(par2, par3 - 1, par4 - 1) == Block.IRON_BLOCK.id && par1World.getTypeId(par2, par3 - 1, par4 + 1) == Block.IRON_BLOCK.id;

            if (var5 || var6)
            {
                par1World.setRawTypeId(par2, par3, par4, 0);
                par1World.setRawTypeId(par2, par3 - 1, par4, 0);
                par1World.setRawTypeId(par2, par3 - 2, par4, 0);

                if (var5)
                {
                    par1World.setRawTypeId(par2 - 1, par3 - 1, par4, 0);
                    par1World.setRawTypeId(par2 + 1, par3 - 1, par4, 0);
                }
                else
                {
                    par1World.setRawTypeId(par2, par3 - 1, par4 - 1, 0);
                    par1World.setRawTypeId(par2, par3 - 1, par4 + 1, 0);
                }

                EntityIronGolem var7 = new EntityIronGolem(par1World);
                var7.setPlayerCreated(true);
                var7.setPositionRotation((double) par2 + 0.5D, (double) par3 - 1.95D, (double) par4 + 0.5D, 0.0F, 0.0F);
                par1World.addEntity(var7);

                for (int var8 = 0; var8 < 120; ++var8)
                {
                    par1World.addParticle("snowballpoof", (double) par2 + par1World.random.nextDouble(), (double) (par3 - 2) + par1World.random.nextDouble() * 3.9D, (double) par4 + par1World.random.nextDouble(), 0.0D, 0.0D, 0.0D);
                }

                par1World.update(par2, par3, par4, 0);
                par1World.update(par2, par3 - 1, par4, 0);
                par1World.update(par2, par3 - 2, par4, 0);

                if (var5)
                {
                    par1World.update(par2 - 1, par3 - 1, par4, 0);
                    par1World.update(par2 + 1, par3 - 1, par4, 0);
                }
                else
                {
                    par1World.update(par2, par3 - 1, par4 - 1, 0);
                    par1World.update(par2, par3 - 1, par4 + 1, 0);
                }
            }
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getTypeId(par2, par3, par4);
        return (var5 == 0 || Block.byId[var5].material.isReplaceable()) && par1World.v(par2, par3 - 1, par4);
    }

    /**
     * Called when the block is placed in the world.
     */
    public void postPlace(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = MathHelper.floor((double) (par5EntityLiving.yaw * 4.0F / 360.0F) + 2.5D) & 3;
        par1World.setData(par2, par3, par4, var6);
    }
}
