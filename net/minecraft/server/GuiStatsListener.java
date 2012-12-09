package net.minecraft.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GuiStatsListener implements ActionListener
{
    final GuiStatsComponent a;

    GuiStatsListener(GuiStatsComponent par1GuiStatsComponent)
    {
        this.a = par1GuiStatsComponent;
    }

    public void actionPerformed(ActionEvent par1ActionEvent)
    {
        GuiStatsComponent.a(this.a);
    }
}
