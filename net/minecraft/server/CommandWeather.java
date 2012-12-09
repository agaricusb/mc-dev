package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class CommandWeather extends CommandAbstract
{
    public String c()
    {
        return "weather";
    }

    /**
     * Return the required permission level for this command.
     */
    public int a()
    {
        return 2;
    }

    public void b(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length < 1)
        {
            throw new ExceptionUsage("commands.weather.usage", new Object[0]);
        }
        else
        {
            int var3 = (300 + (new Random()).nextInt(600)) * 20;

            if (par2ArrayOfStr.length >= 2)
            {
                var3 = a(par1ICommandSender, par2ArrayOfStr[1], 1, 1000000) * 20;
            }

            WorldServer var4 = MinecraftServer.getServer().worldServer[0];
            WorldData var5 = var4.getWorldData();
            var5.setWeatherDuration(var3);
            var5.setThunderDuration(var3);

            if ("clear".equalsIgnoreCase(par2ArrayOfStr[0]))
            {
                var5.setStorm(false);
                var5.setThundering(false);
                a(par1ICommandSender, "commands.weather.clear", new Object[0]);
            }
            else if ("rain".equalsIgnoreCase(par2ArrayOfStr[0]))
            {
                var5.setStorm(true);
                var5.setThundering(false);
                a(par1ICommandSender, "commands.weather.rain", new Object[0]);
            }
            else if ("thunder".equalsIgnoreCase(par2ArrayOfStr[0]))
            {
                var5.setStorm(true);
                var5.setThundering(true);
                a(par1ICommandSender, "commands.weather.thunder", new Object[0]);
            }
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List a(ICommandListener par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? a(par2ArrayOfStr, new String[]{"clear", "rain", "thunder"}): null;
    }
}
