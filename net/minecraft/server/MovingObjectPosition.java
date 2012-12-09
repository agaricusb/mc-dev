package net.minecraft.server;

public class MovingObjectPosition
{
    /** What type of ray trace hit was this? 0 = block, 1 = entity */
    public EnumMovingObjectType type;

    /** x coordinate of the block ray traced against */
    public int b;

    /** y coordinate of the block ray traced against */
    public int c;

    /** z coordinate of the block ray traced against */
    public int d;

    /**
     * Which side was hit. If its -1 then it went the full length of the ray trace. Bottom = 0, Top = 1, East = 2, West
     * = 3, North = 4, South = 5.
     */
    public int face;

    /** The vector position of the hit */
    public Vec3D pos;

    /** The hit entity */
    public Entity entity;

    public MovingObjectPosition(int par1, int par2, int par3, int par4, Vec3D par5Vec3)
    {
        this.type = EnumMovingObjectType.TILE;
        this.b = par1;
        this.c = par2;
        this.d = par3;
        this.face = par4;
        this.pos = par5Vec3.b.create(par5Vec3.c, par5Vec3.d, par5Vec3.e);
    }

    public MovingObjectPosition(Entity par1Entity)
    {
        this.type = EnumMovingObjectType.ENTITY;
        this.entity = par1Entity;
        this.pos = par1Entity.world.getVec3DPool().create(par1Entity.locX, par1Entity.locY, par1Entity.locZ);
    }
}
