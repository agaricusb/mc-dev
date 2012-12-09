package net.minecraft.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import javax.swing.JComponent;
import javax.swing.Timer;

public class GuiStatsComponent extends JComponent
{
    private static final DecimalFormat a = new DecimalFormat("########0.000");

    /** An array containing the columns that make up the memory use graph. */
    private int[] b = new int[256];

    /**
     * Counts the number of updates. Used as the index into the memoryUse array to display the latest value.
     */
    private int c = 0;

    /** An array containing the strings displayed in this stats component. */
    private String[] d = new String[11];
    private final MinecraftServer e;

    public GuiStatsComponent(MinecraftServer par1MinecraftServer)
    {
        this.e = par1MinecraftServer;
        this.setPreferredSize(new Dimension(456, 246));
        this.setMinimumSize(new Dimension(456, 246));
        this.setMaximumSize(new Dimension(456, 246));
        (new Timer(500, new GuiStatsListener(this))).start();
        this.setBackground(Color.BLACK);
    }

    /**
     * Updates the stat values and calls paint to redraw the component.
     */
    private void a()
    {
        long var1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.gc();
        this.d[0] = "Memory use: " + var1 / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
        this.d[1] = "Threads: " + NetworkManager.a.get() + " + " + NetworkManager.b.get();
        this.d[2] = "Avg tick: " + a.format(this.a(this.e.j) * 1.0E-6D) + " ms";
        this.d[3] = "Avg sent: " + (int)this.a(this.e.f) + ", Avg size: " + (int)this.a(this.e.g);
        this.d[4] = "Avg rec: " + (int)this.a(this.e.h) + ", Avg size: " + (int)this.a(this.e.i);

        if (this.e.worldServer != null)
        {
            for (int var3 = 0; var3 < this.e.worldServer.length; ++var3)
            {
                this.d[5 + var3] = "Lvl " + var3 + " tick: " + a.format(this.a(this.e.k[var3]) * 1.0E-6D) + " ms";

                if (this.e.worldServer[var3] != null && this.e.worldServer[var3].chunkProviderServer != null)
                {
                    this.d[5 + var3] = this.d[5 + var3] + ", " + this.e.worldServer[var3].chunkProviderServer.getName();
                    this.d[5 + var3] = this.d[5 + var3] + ", Vec3: " + this.e.worldServer[var3].getVec3DPool().d() + " / " + this.e.worldServer[var3].getVec3DPool().c();
                }
            }
        }

        this.b[this.c++ & 255] = (int)(this.a(this.e.g) * 100.0D / 12500.0D);
        this.repaint();
    }

    private double a(long[] par1ArrayOfLong)
    {
        long var2 = 0L;

        for (int var4 = 0; var4 < par1ArrayOfLong.length; ++var4)
        {
            var2 += par1ArrayOfLong[var4];
        }

        return (double)var2 / (double)par1ArrayOfLong.length;
    }

    public void paint(Graphics par1Graphics)
    {
        par1Graphics.setColor(new Color(16777215));
        par1Graphics.fillRect(0, 0, 456, 246);
        int var2;

        for (var2 = 0; var2 < 256; ++var2)
        {
            int var3 = this.b[var2 + this.c & 255];
            par1Graphics.setColor(new Color(var3 + 28 << 16));
            par1Graphics.fillRect(var2, 100 - var3, 1, var3);
        }

        par1Graphics.setColor(Color.BLACK);

        for (var2 = 0; var2 < this.d.length; ++var2)
        {
            String var4 = this.d[var2];

            if (var4 != null)
            {
                par1Graphics.drawString(var4, 32, 116 + var2 * 16);
            }
        }
    }

    /**
     * Public static accessor to call updateStats.
     */
    static void a(GuiStatsComponent par0GuiStatsComponent)
    {
        par0GuiStatsComponent.a();
    }
}
