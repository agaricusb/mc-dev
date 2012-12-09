package net.minecraft.server;

import java.util.HashMap;
import java.util.TimerTask;

class MojangStatisticsTask extends TimerTask
{
    /** The PlayerUsageSnooper object. */
    final MojangStatisticsGenerator a;

    MojangStatisticsTask(MojangStatisticsGenerator par1PlayerUsageSnooper)
    {
        this.a = par1PlayerUsageSnooper;
    }

    public void run()
    {
        if (MojangStatisticsGenerator.a(this.a).getSnooperEnabled())
        {
            HashMap var1;

            synchronized (MojangStatisticsGenerator.b(this.a))
            {
                var1 = new HashMap(MojangStatisticsGenerator.c(this.a));
            }

            var1.put("snooper_count", Integer.valueOf(MojangStatisticsGenerator.d(this.a)));
            HttpUtilities.a(MojangStatisticsGenerator.e(this.a), var1, true);
        }
    }
}
