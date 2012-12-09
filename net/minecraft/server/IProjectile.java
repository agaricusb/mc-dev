package net.minecraft.server;

public interface IProjectile
{
    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    void shoot(double var1, double var3, double var5, float var7, float var8);
}
