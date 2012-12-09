package net.minecraft.server;

public class StepSound
{
    public final String a;
    public final float b;
    public final float c;

    public StepSound(String par1Str, float par2, float par3)
    {
        this.a = par1Str;
        this.b = par2;
        this.c = par3;
    }

    public float getVolume1()
    {
        return this.b;
    }

    public float getVolume2()
    {
        return this.c;
    }

    /**
     * Used when a block breaks, EXA: Player break, Shep eating grass, etc..
     */
    public String getBreakSound()
    {
        return "dig." + this.a;
    }

    /**
     * Used when a entity walks over, or otherwise interacts with the block.
     */
    public String getStepSound()
    {
        return "step." + this.a;
    }

    /**
     * Used when a player places a block.
     */
    public String getPlaceSound()
    {
        return this.getBreakSound();
    }
}
