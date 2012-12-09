package net.minecraft.server;

import java.util.List;

public class CommandSpawnpoint extends CommandAbstract
{
    public String c()
    {
        return "spawnpoint";
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
        return par1ICommandSender.a("commands.spawnpoint.usage", new Object[0]);
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        EntityPlayer var3 = par2ArrayOfStr.length == 0 ? c(par1ICommandSender) : c(par1ICommandSender, par2ArrayOfStr[0]);

        if (par2ArrayOfStr.length == 4)
        {
            if (var3.world != null)
            {
                byte var4 = 1;
                int var5 = 30000000;
                int var10 = var4 + 1;
                int var6 = a(par1ICommandSender, par2ArrayOfStr[var4], -var5, var5);
                int var7 = a(par1ICommandSender, par2ArrayOfStr[var10++], 0, 256);
                int var8 = a(par1ICommandSender, par2ArrayOfStr[var10++], -var5, var5);
                var3.setRespawnPosition(new ChunkCoordinates(var6, var7, var8), true);
                a(par1ICommandSender, "commands.spawnpoint.success", new Object[]{var3.getLocalizedName(), Integer.valueOf(var6), Integer.valueOf(var7), Integer.valueOf(var8)});
            }
        }
        else
        {
            if (par2ArrayOfStr.length > 1)
            {
                throw new ExceptionUsage("commands.spawnpoint.usage", new Object[0]);
            }

            ChunkCoordinates var11 = var3.b();
            var3.setRespawnPosition(var11, true);
            a(par1ICommandSender, "commands.spawnpoint.success", new Object[]{var3.getLocalizedName(), Integer.valueOf(var11.x), Integer.valueOf(var11.y), Integer.valueOf(var11.z)});
        }
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
