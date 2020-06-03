package theSilverEcho.tweaks.cosmetic;

public class aF
{
	private static final double[] a = new double[65536];

	private static final double[] DOUBLES = new double[360];

	public static double a(double paramDouble)
	{
		return a[(int) (paramDouble * 10430.3779296875D) & 0xFFFF];
	}

	public static double b(double paramDouble)
	{
		return a[(int) (paramDouble * 10430.3779296875D + 16384.0D) & 0xFFFF];
	}

	public static double a(int paramInt)
	{
		paramInt %= 360;
		return DOUBLES[(paramInt < 0) ? (360 + paramInt) : paramInt];
	}

	public static double b(int paramInt)
	{
		paramInt += 90;
		paramInt %= 360;
		return DOUBLES[(paramInt < 0) ? (360 + paramInt) : paramInt];
	}

	public static int a(int paramInt1, int paramInt2, int paramInt3)
	{
		return (paramInt1 < paramInt2) ? paramInt2 : (Math.min(paramInt1, paramInt3));
	}

	public static float a(float paramFloat1, float paramFloat2, float paramFloat3)
	{
		return (paramFloat1 < paramFloat2) ? paramFloat2 : (Math.min(paramFloat1, paramFloat3));
	}

	public static double a(double paramDouble1, double paramDouble2, double paramDouble3)
	{
		return (paramDouble1 < paramDouble2) ? paramDouble2 : (Math.min(paramDouble1, paramDouble3));
	}

	public static int c(double paramDouble)
	{
		int i = (int) paramDouble;
		return (paramDouble < i) ? (i - 1) : i;
	}

	public static int a(float paramFloat)
	{
		int i = (int) paramFloat;
		return (paramFloat < i) ? (i - 1) : i;
	}

	public static double d(double paramDouble)
	{
		return paramDouble - Math.floor(paramDouble);
	}

//	static
//	{
//		byte b;
//		for (b = 0; b < 65536; b++)
//			a[b] = Math.sin(b * Math.PI * 2.0D / 65536.0D);
//		for (b = 0; b < 'U'; b++)
//			DOUBLES[b] = Math.sin(Math.toRadians(b));
//	}
}
