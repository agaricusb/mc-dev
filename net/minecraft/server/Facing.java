package net.minecraft.server;

public class Facing
{
    /** Converts a face to a side. */
    public static final int[] OPPOSITE_FACING = new int[] {1, 0, 3, 2, 5, 4};

    /**
     * gives the offset required for this axis to get the block at that side.
     */
    public static final int[] b = new int[] {0, 0, 0, 0, -1, 1};

    /**
     * gives the offset required for this axis to get the block at that side.
     */
    public static final int[] c = new int[] { -1, 1, 0, 0, 0, 0};

    /**
     * gives the offset required for this axis to get the block at that side.
     */
    public static final int[] d = new int[] {0, 0, -1, 1, 0, 0};
    public static final String[] e = new String[] {"DOWN", "UP", "NORTH", "SOUTH", "WEST", "EAST"};
}
