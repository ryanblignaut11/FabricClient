package theSilverEcho.tweaks.gui;

public class MathUtil
{
	public static float distance(float x, float y, float x1, float y1)
	{
		return (float) Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
	}
}
