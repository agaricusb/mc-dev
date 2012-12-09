package net.minecraft.server;

public class OldChunk
{
    public long a;
    public boolean b;
    public byte[] c;
    public OldNibbleArray d;
    public OldNibbleArray e;
    public OldNibbleArray f;
    public byte[] g;
    public NBTTagList h;
    public NBTTagList i;
    public NBTTagList j;
    public final int k;
    public final int l;

    public OldChunk(int par1, int par2)
    {
        this.k = par1;
        this.l = par2;
    }
}
