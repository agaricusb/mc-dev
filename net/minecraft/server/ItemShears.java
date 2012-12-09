package net.minecraft.server;

public class ItemShears extends Item
{
    public ItemShears(int par1)
    {
        super(par1);
        this.d(1);
        this.setMaxDurability(238);
        this.a(CreativeModeTab.i);
    }

    public boolean a(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
        if (par3 != Block.LEAVES.id && par3 != Block.WEB.id && par3 != Block.LONG_GRASS.id && par3 != Block.VINE.id && par3 != Block.TRIPWIRE.id)
        {
            return super.a(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLiving);
        }
        else
        {
            par1ItemStack.damage(1, par7EntityLiving);
            return true;
        }
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canDestroySpecialBlock(Block par1Block)
    {
        return par1Block.id == Block.WEB.id || par1Block.id == Block.REDSTONE_WIRE.id || par1Block.id == Block.TRIPWIRE.id;
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getDestroySpeed(ItemStack par1ItemStack, Block par2Block)
    {
        return par2Block.id != Block.WEB.id && par2Block.id != Block.LEAVES.id ? (par2Block.id == Block.WOOL.id ? 5.0F : super.getDestroySpeed(par1ItemStack, par2Block)) : 15.0F;
    }
}
