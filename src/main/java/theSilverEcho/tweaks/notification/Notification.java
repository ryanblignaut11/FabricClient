package theSilverEcho.tweaks.notification;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import theSilverEcho.tweaks.gui.GuiHelper;

import java.awt.*;

public class Notification extends GuiHelper
{
	private final String title, message;
	private long start;
	private final long fadeIn, fadeOut, end;

	public Notification(String title, String message, int duration)
	{
		this.title = title;
		this.message = message;

		fadeIn = 200 * duration;
		fadeOut = fadeIn + 500 * duration;
		end = fadeIn + fadeOut;

	}

	public void show()
	{
		start = System.currentTimeMillis();
	}

	public boolean isShow()
	{
		return getTime() <= end;
	}

	private long getTime()
	{
		return System.currentTimeMillis() - start;
	}

	public void render()
	{
		double offset;
		int width = 120;
		int height = 30;
		long time = getTime();

		if (time < fadeIn)
		{
			offset = Math.tanh(time / (double) (fadeIn) * 3.0) * width;
		} else if (time > fadeOut)
		{
			offset = (Math.tanh(3.0 - (time - fadeOut) / (double) (end - fadeOut) * 3.0) * width);
		} else
		{
			offset = width;
		}
		try
		{

			int scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
			int scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
			int color = new Color(0, 255, 0, 220).getRGB();

			GL11.glLineWidth(2);
			GuiHelper.fill(GL11.GL_LINE_LOOP, (int) (scaledWidth - offset), scaledHeight - 5 - height, scaledWidth - 2, scaledHeight - 5, color);
			GuiHelper.fill(GL11.GL_QUADS, (int) (scaledWidth - offset), scaledHeight - 5 - height, scaledWidth - 2, scaledHeight - 5,
					new Color(0, 0, 0, 220).getRGB());
			GuiHelper.fill(GL11.GL_QUADS, (int) (scaledWidth - offset), scaledHeight - 5 - height, (int) (scaledWidth - offset + 3), scaledHeight - 5,
					color);

			MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tweaks","textures/ui/widgets.png"));
			GuiHelper.fill(GL11.GL_QUADS, (int) (scaledWidth - offset), scaledHeight - 5 - height, (int) (scaledWidth - offset + 3), scaledHeight - 5,
					color);
			blit( (int) (scaledWidth - offset), scaledHeight - 5 - height,0,0,20,20);

			MinecraftClient.getInstance().textRenderer.draw(title, (int) (scaledWidth - offset + 8), scaledHeight - height, -1);
			MinecraftClient.getInstance().textRenderer.draw(message, (int) (scaledWidth - offset + 8), scaledHeight - height + 15, -1);
		} catch (Exception e)
		{

			e.printStackTrace();
		}

	}
}
