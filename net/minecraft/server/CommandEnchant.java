package net.minecraft.server;

import java.util.List;

public class CommandEnchant extends CommandAbstract
{
    public String c()
    {
        return "enchant";
    }

    /**
     * Return the required permission level for this command.
     */
    public int a()
    {
        return 2;
    }

    public String a(ICommandListener par1ICommandSender)
    {
        return par1ICommandSender.a("commands.enchant.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length < 2)
        {
            throw new ExceptionUsage("commands.enchant.usage", new Object[0]);
        }
        else
        {
            EntityPlayer var3 = c(par1ICommandSender, par2ArrayOfStr[0]);
            int var4 = a(par1ICommandSender, par2ArrayOfStr[1], 1, Enchantment.byId.length - 1);
            int var5 = 1;
            ItemStack var6 = var3.bT();

            if (var6 == null)
            {
                a(par1ICommandSender, "commands.enchant.noItem", new Object[0]);
            }
            else
            {
                Enchantment var7 = Enchantment.byId[var4];

                if (var7 == null)
                {
                    throw new ExceptionInvalidNumber("commands.enchant.notFound", new Object[] {Integer.valueOf(var4)});
                }
                else if (!var7.slot.canEnchant(var6.getItem()))
                {
                    a(par1ICommandSender, "commands.enchant.cantEnchant", new Object[0]);
                }
                else
                {
                    if (par2ArrayOfStr.length >= 3)
                    {
                        var5 = a(par1ICommandSender, par2ArrayOfStr[2], var7.getStartLevel(), var7.getMaxLevel());
                    }

                    if (var6.hasTag())
                    {
                        NBTTagList var8 = var6.getEnchantments();

                        if (var8 != null)
                        {
                            for (int var9 = 0; var9 < var8.size(); ++var9)
                            {
                                short var10 = ((NBTTagCompound)var8.get(var9)).getShort("id");

                                if (Enchantment.byId[var10] != null)
                                {
                                    Enchantment var11 = Enchantment.byId[var10];

                                    if (!var11.a(var7))
                                    {
                                        a(par1ICommandSender, "commands.enchant.cantCombine", new Object[]{var7.c(var5), var11.c(((NBTTagCompound) var8.get(var9)).getShort("lvl"))});
                                        return;
                                    }
                                }
                            }
                        }
                    }

                    var6.addEnchantment(var7, var5);
                    a(par1ICommandSender, "commands.enchant.success", new Object[0]);
                }
            }
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? a(par2ArrayOfStr, this.d()) : null;
    }

    protected String[] d()
    {
        return MinecraftServer.getServer().getPlayers();
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean a(int par1)
    {
        return par1 == 0;
    }
}
