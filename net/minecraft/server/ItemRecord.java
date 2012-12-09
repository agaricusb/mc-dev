package net.minecraft.server;

import java.util.HashMap;
import java.util.Map;

public class ItemRecord extends Item
{
    /** List of all record items and their names. */
    private static final Map b = new HashMap();

    /** The name of the record. */
    public final String a;

    protected ItemRecord(int par1, String par2Str)
    {
        super(par1);
        this.a = par2Str;
        this.maxStackSize = 1;
        this.a(CreativeModeTab.f);
        b.put(par2Str, this);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean interactWith(ItemStack par1ItemStack, EntityHuman par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.getTypeId(par4, par5, par6) == Block.JUKEBOX.id && par3World.getData(par4, par5, par6) == 0)
        {
            if (par3World.isStatic)
            {
                return true;
            }
            else
            {
                ((BlockJukeBox) Block.JUKEBOX).a(par3World, par4, par5, par6, par1ItemStack);
                par3World.a((EntityHuman) null, 1005, par4, par5, par6, this.id);
                --par1ItemStack.count;
                return true;
            }
        }
        else
        {
            return false;
        }
    }
}
