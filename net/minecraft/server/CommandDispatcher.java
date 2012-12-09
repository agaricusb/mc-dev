package net.minecraft.server;

import java.util.Iterator;

public class CommandDispatcher extends CommandHandler implements ICommandDispatcher
{
    public CommandDispatcher()
    {
        this.a(new CommandTime());
        this.a(new CommandGamemode());
        this.a(new CommandDifficulty());
        this.a(new CommandGamemodeDefault());
        this.a(new CommandKill());
        this.a(new CommandToggleDownfall());
        this.a(new CommandWeather());
        this.a(new CommandXp());
        this.a(new CommandTp());
        this.a(new CommandGive());
        this.a(new CommandEnchant());
        this.a(new CommandMe());
        this.a(new CommandSeed());
        this.a(new CommandHelp());
        this.a(new CommandDebug());
        this.a(new CommandTell());
        this.a(new CommandSay());
        this.a(new CommandSpawnpoint());
        this.a(new CommandGamerule());
        this.a(new CommandClear());

        if (MinecraftServer.getServer().T())
        {
            this.a(new CommandOp());
            this.a(new CommandDeop());
            this.a(new CommandStop());
            this.a(new CommandSaveAll());
            this.a(new CommandSaveOff());
            this.a(new CommandSaveOn());
            this.a(new CommandBanIp());
            this.a(new CommandPardonIP());
            this.a(new CommandBan());
            this.a(new CommandBanList());
            this.a(new CommandPardon());
            this.a(new CommandKick());
            this.a(new CommandList());
            this.a(new CommandWhitelist());
        }
        else
        {
            this.a(new CommandPublish());
        }

        CommandAbstract.a(this);
    }

    /**
     * Sends a message to the admins of the server from a given CommandSender with the given resource string and given
     * extra srings. If the int par2 is even or zero, the original sender is also notified.
     */
    public void a(ICommandListener par1ICommandSender, int par2, String par3Str, Object... par4ArrayOfObj)
    {
        boolean var5 = true;

        if (par1ICommandSender instanceof TileEntityCommand && !MinecraftServer.getServer().worldServer[0].getGameRules().getBoolean("commandBlockOutput"))
        {
            var5 = false;
        }

        if (var5)
        {
            Iterator var6 = MinecraftServer.getServer().getServerConfigurationManager().players.iterator();

            while (var6.hasNext())
            {
                EntityPlayer var7 = (EntityPlayer)var6.next();

                if (var7 != par1ICommandSender && MinecraftServer.getServer().getServerConfigurationManager().isOp(var7.name))
                {
                    var7.sendMessage("\u00a77\u00a7o[" + par1ICommandSender.getName() + ": " + var7.a(par3Str, par4ArrayOfObj) + "]");
                }
            }
        }

        if (par1ICommandSender != MinecraftServer.getServer())
        {
            MinecraftServer.log.info("[" + par1ICommandSender.getName() + ": " + MinecraftServer.getServer().a(par3Str, par4ArrayOfObj) + "]");
        }

        if ((par2 & 1) != 1)
        {
            par1ICommandSender.sendMessage(par1ICommandSender.a(par3Str, par4ArrayOfObj));
        }
    }
}
