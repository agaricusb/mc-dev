package net.minecraft.server;

public class Direction
{
    public static final int[] a = new int[] {0, -1, 0, 1};
    public static final int[] b = new int[] {1, 0, -1, 0};
    public static final String[] c = new String[] {"SOUTH", "WEST", "NORTH", "EAST"};
    public static final int[] d = new int[] {3, 4, 2, 5};
    public static final int[] e = new int[] { -1, -1, 2, 0, 1, 3};
    public static final int[] f = new int[] {2, 3, 0, 1};
    public static final int[] g = new int[] {1, 2, 3, 0};
    public static final int[] h = new int[] {3, 0, 1, 2};
    public static final int[][] i = new int[][] {{1, 0, 3, 2, 5, 4}, {1, 0, 5, 4, 2, 3}, {1, 0, 2, 3, 4, 5}, {1, 0, 4, 5, 3, 2}};

    public static int a(double par0, double par2)
    {
        return MathHelper.abs((float) par0) > MathHelper.abs((float) par2) ? (par0 > 0.0D ? 1 : 3) : (par2 > 0.0D ? 2 : 0);
    }
}
