package net.minecraft.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

class ServerGuiCommandListener implements ActionListener
{
    /** Text field. */
    final JTextField a;

    /** Reference to the ServerGui object. */
    final ServerGUI b;

    ServerGuiCommandListener(ServerGUI par1ServerGUI, JTextField par2JTextField)
    {
        this.b = par1ServerGUI;
        this.a = par2JTextField;
    }

    public void actionPerformed(ActionEvent par1ActionEvent)
    {
        String var2 = this.a.getText().trim();

        if (var2.length() > 0)
        {
            ServerGUI.a(this.b).issueCommand(var2, MinecraftServer.getServer());
        }

        this.a.setText("");
    }
}
