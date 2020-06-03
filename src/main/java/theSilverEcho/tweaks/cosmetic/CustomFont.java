package theSilverEcho.tweaks.cosmetic;

import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.texture.TextureManager;

public class CustomFont extends TextRenderer
{
	public CustomFont(TextureManager textureManager, FontStorage fontStorage)
	{
		super(textureManager, fontStorage);
	}

	@Override public int drawWithShadow(String text, float x, float y, int color)
	{
		return super.drawWithShadow(text, x, y, color);
	}

	@Override public int draw(String text, float x, float y, int color)
	{
		return super.draw(text, x, y, color);
	}
}
