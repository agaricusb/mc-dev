package net.minecraft.server;

public class MonsterEggInfo
{
    /** The entityID of the spawned mob */
    public int a;

    /** Base color of the egg */
    public int b;

    /** Color of the egg spots */
    public int c;

    public MonsterEggInfo(int par1, int par2, int par3)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3;
    }
}
