package net.minecraft.server;

import java.util.List;

public class CommandTp extends CommandAbstract
{
    public String c()
    {
        return "tp";
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
        return par1ICommandSender.a("commands.tp.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length < 1)
        {
            throw new ExceptionUsage("commands.tp.usage", new Object[0]);
        }
        else
        {
            EntityPlayer var3;

            if (par2ArrayOfStr.length != 2 && par2ArrayOfStr.length != 4)
            {
                var3 = c(par1ICommandSender);
            }
            else
            {
                var3 = c(par1ICommandSender, par2ArrayOfStr[0]);

                if (var3 == null)
                {
                    throw new ExceptionPlayerNotFound();
                }
            }

            if (par2ArrayOfStr.length != 3 && par2ArrayOfStr.length != 4)
            {
                if (par2ArrayOfStr.length == 1 || par2ArrayOfStr.length == 2)
                {
                    EntityPlayer var11 = c(par1ICommandSender, par2ArrayOfStr[par2ArrayOfStr.length - 1]);

                    if (var11 == null)
                    {
                        throw new ExceptionPlayerNotFound();
                    }

                    if (var11.world != var3.world)
                    {
                        a(par1ICommandSender, "commands.tp.notSameDimension", new Object[0]);
                        return;
                    }

                    var3.playerConnection.a(var11.locX, var11.locY, var11.locZ, var11.yaw, var11.pitch);
                    a(par1ICommandSender, "commands.tp.success", new Object[]{var3.getLocalizedName(), var11.getLocalizedName()});
                }
            }
            else if (var3.world != null)
            {
                int var4 = par2ArrayOfStr.length - 3;
                double var5 = this.a(par1ICommandSender, var3.locX, par2ArrayOfStr[var4++]);
                double var7 = this.a(par1ICommandSender, var3.locY, par2ArrayOfStr[var4++], 0, 0);
                double var9 = this.a(par1ICommandSender, var3.locZ, par2ArrayOfStr[var4++]);
                var3.enderTeleportTo(var5, var7, var9);
                a(par1ICommandSender, "commands.tp.success.coordinates", new Object[]{var3.getLocalizedName(), Double.valueOf(var5), Double.valueOf(var7), Double.valueOf(var9)});
            }
        }
    }

    private double a(ICommandListener par1ICommandSender, double par2, String par4Str)
    {
        return this.a(par1ICommandSender, par2, par4Str, -30000000, 30000000);
    }

    private double a(ICommandListener par1ICommandSender, double par2, String par4Str, int par5, int par6)
    {
        boolean var7 = par4Str.startsWith("~");
        double var8 = var7 ? par2 : 0.0D;

        if (!var7 || par4Str.length() > 1)
        {
            boolean var10 = par4Str.contains(".");

            if (var7)
            {
                par4Str = par4Str.substring(1);
            }

            var8 += b(par1ICommandSender, par4Str);

            if (!var10 && !var7)
            {
                var8 += 0.5D;
            }
        }

        if (par5 != 0 || par6 != 0)
        {
            if (var8 < (double)par5)
            {
                throw new ExceptionInvalidNumber("commands.generic.double.tooSmall", new Object[] {Double.valueOf(var8), Integer.valueOf(par5)});
            }

            if (var8 > (double)par6)
            {
                throw new ExceptionInvalidNumber("commands.generic.double.tooBig", new Object[] {Double.valueOf(var8), Integer.valueOf(par6)});
            }
        }

        return var8;
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length != 1 && par2ArrayOfStr.length != 2 ? null : a(par2ArrayOfStr, MinecraftServer.getServer().getPlayers());
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean a(int par1)
    {
        return par1 == 0;
    }
}
