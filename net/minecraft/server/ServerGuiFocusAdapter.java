package net.minecraft.server;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class ServerGuiFocusAdapter extends FocusAdapter
{
    /** Reference to the ServerGui object. */
    final ServerGUI a;

    ServerGuiFocusAdapter(ServerGUI par1ServerGUI)
    {
        this.a = par1ServerGUI;
    }

    public void focusGained(FocusEvent par1FocusEvent) {}
}
