package net.minecraft.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileIOThread implements Runnable
{
    /** Instance of ThreadedFileIOBase */
    public static final FileIOThread a = new FileIOThread();
    private List b = Collections.synchronizedList(new ArrayList());
    private volatile long c = 0L;
    private volatile long d = 0L;
    private volatile boolean e = false;

    private FileIOThread()
    {
        Thread var1 = new Thread(this, "File IO Thread");
        var1.setPriority(1);
        var1.start();
    }

    public void run()
    {
        while (true)
        {
            this.b();
        }
    }

    /**
     * Process the items that are in the queue
     */
    private void b()
    {
        for (int var1 = 0; var1 < this.b.size(); ++var1)
        {
            IAsyncChunkSaver var2 = (IAsyncChunkSaver)this.b.get(var1);
            boolean var3 = var2.c();

            if (!var3)
            {
                this.b.remove(var1--);
                ++this.d;
            }

            try
            {
                Thread.sleep(this.e ? 0L : 10L);
            }
            catch (InterruptedException var6)
            {
                var6.printStackTrace();
            }
        }

        if (this.b.isEmpty())
        {
            try
            {
                Thread.sleep(25L);
            }
            catch (InterruptedException var5)
            {
                var5.printStackTrace();
            }
        }
    }

    /**
     * threaded io
     */
    public void a(IAsyncChunkSaver par1IThreadedFileIO)
    {
        if (!this.b.contains(par1IThreadedFileIO))
        {
            ++this.c;
            this.b.add(par1IThreadedFileIO);
        }
    }

    public void a() throws InterruptedException
    {
        this.e = true;

        while (this.c != this.d)
        {
            Thread.sleep(10L);
        }

        this.e = false;
    }
}
