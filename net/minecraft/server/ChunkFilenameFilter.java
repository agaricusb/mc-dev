package net.minecraft.server;

import java.io.File;
import java.io.FilenameFilter;

class ChunkFilenameFilter implements FilenameFilter
{
    final WorldLoaderServer a;

    ChunkFilenameFilter(WorldLoaderServer par1AnvilSaveConverter)
    {
        this.a = par1AnvilSaveConverter;
    }

    public boolean accept(File par1File, String par2Str)
    {
        return par2Str.endsWith(".mcr");
    }
}
