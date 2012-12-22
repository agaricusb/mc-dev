package net.minecraft.server;

import java.util.Vector;
import javax.swing.JList;

public class PlayerListBox extends JList implements IUpdatePlayerListBox
{
    /** Reference to the MinecraftServer object. */
    private MinecraftServer a;

    /** Counts the number of updates. */
    private int b = 0;

    public PlayerListBox(MinecraftServer par1MinecraftServer)
    {
        this.a = par1MinecraftServer;
        par1MinecraftServer.a(this);
    }

    /**
     * Updates the JList with a new model.
     */
    public void a()
    {
        if (this.b++ % 20 == 0)
        {
            Vector var1 = new Vector();

            for (int var2 = 0; var2 < this.a.getPlayerList().players.size(); ++var2)
            {
                var1.add(((EntityPlayer)this.a.getPlayerList().players.get(var2)).name);
            }

            this.setListData(var1);
        }
    }
}
