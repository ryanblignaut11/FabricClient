package theSilverEcho.tweaks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;

import java.awt.*;

public class ColourHelper
{




	/*
	b-color-base: linear-gradient(#2e3440, #4c566a);
	b-cyan: #8FBCBB;
	b-btn: linear-gradient(#3B4252,#434C5E);
	b-color-wsmoke:
		new Color(143,188,187);
	b-color-w: #E5E9F0;

	*/

	public static Color rgb()
	{
		return Color.getHSBColor(-(float) (System.currentTimeMillis() % 10000L) / 10000.0F, 1/*0.8F*/, 1/*0.8F*/);
	}

	public static int rgbInt()
	{
		return new Color(ColourHelper.rgb().getRed(), ColourHelper.rgb().getGreen(), ColourHelper.rgb().getBlue(), 255 * 40 / 100).getRGB();
	}

	public static int fullRgbInt()
	{
		return rgb().getRed() * 65536 + rgb().getGreen() * 256 + rgb().getBlue();
	}

	public static Color rgb(int speed)
	{
		return Color.getHSBColor(-(float) (System.currentTimeMillis() % speed) / speed, 0.8F, 0.8F);
	}

	public static int fullRgbInt(int speed)
	{
		return rgb(speed).getRed() * 65536 + rgb(speed).getGreen() * 256 + rgb(speed).getBlue();
	}

	private static Color getChromaColor(double x, double y, double offsetScale)
	{
		float v = 2000.0F;
		return new Color(
				Color.HSBtoRGB((float) (((double) System.currentTimeMillis() - x * 10.0D * offsetScale - y * 10.0D * offsetScale) % (double) v) / v,
						0.8F, 0.8F));
	}

	public static void drawChromaString(String text, int x, int y, double offsetScale)
	{
		TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
		char[] var7 = text.toCharArray();
		int var8 = var7.length;

		for (char c : var7)
		{
			int i = getChromaColor(x, y, offsetScale).getRGB();
			String tmp = String.valueOf(c);
			renderer.draw(tmp, x, y, i);
			x += renderer.getStringWidth(tmp);
		}

	}

}
