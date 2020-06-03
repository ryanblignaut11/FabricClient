package theSilverEcho.tweaks.keyStrokes;

import net.minecraft.client.MinecraftClient;

import java.awt.*;

public abstract class BaseKey
{
	protected final MinecraftClient minecraft = MinecraftClient.getInstance();
	protected final int xOffset;
	protected final int yOffset;

	protected abstract void renderKey(int x, int y);

	/*protected final int getColor() {
		return mod.getSettings().isChroma() ? Color.HSBtoRGB((float) ((System.currentTimeMillis() - (xOffset * 10) - (yOffset * 10)) % 2000) / 2000.0F,
				0.8F, 0.8F) : new Color(mod.getSettings().getRed(), mod.getSettings().getGreen(), mod.getSettings().getBlue()).getRGB();
	}*/
	public BaseKey(int xOffset, int yOffset)
	{
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	protected final void drawCenteredString(String text, int x, int y, int color)
	{
		minecraft.textRenderer.draw(text, (float) (x - minecraft.textRenderer.getStringWidth(text) / 2), (float) y, color);
	}

}
