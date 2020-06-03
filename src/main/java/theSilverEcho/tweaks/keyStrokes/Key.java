package theSilverEcho.tweaks.keyStrokes;

import net.minecraft.client.options.KeyBinding;
import org.lwjgl.opengl.GL11;
import theSilverEcho.tweaks.gui.GuiHelper;

import java.awt.*;

public class Key extends BaseKey
{

	private final KeyBinding keyBinding;
	private boolean wasPressed;
	private long lastPress;
	private float percentFaded;

	public Key(KeyBinding keyBinding, int xOffset, int yOffset)
	{
		super(xOffset, yOffset);
		this.keyBinding = keyBinding;
		this.wasPressed = true;
		this.lastPress = 0;

	}

	private boolean isKeyOrMouseDown(KeyBinding keyBinding)
	{
		return keyBinding.isPressed();
	}

	@Override protected void renderKey(int x, int y)
	{
		boolean pressed = keyBinding.isPressed();
		if (wasPressed != pressed)
		{
			wasPressed = pressed;
			lastPress = System.currentTimeMillis();

		}

		int textColor = new Color(0, 200, 150, 0).getRGB();
		int pressedTextColor = new Color(0, 200, 0, 0).getRGB();
		int color;

		double textBrightness;

		if (pressed)
		{
			color = Math.min(255, (int) (250 * 5.0 * (System.currentTimeMillis() - lastPress)));
			textBrightness = Math.max(0.0, 1.0 - (System.currentTimeMillis() - lastPress) / (250 * 5.0));
		} else
		{
			color = Math.max(0, 255 - (int) (250 * 5.0 * (System.currentTimeMillis() - lastPress)));
			textBrightness = Math.min(1.0, (double) (System.currentTimeMillis() - lastPress) / (250 * 5.0));
		}

		int red = textColor >> 16 & 0xFF;
		int green = textColor >> 8 & 0xFF;
		int blue = textColor & 0xFF;
		int colorN = new Color(0, 111, 0)
				.getRGB() + ((int) (red * textBrightness) << 16) + ((int) (green * textBrightness) << 8) + (int) (blue * textBrightness);

		float xPos = (float) (x + xOffset + 8);
		float yPos = (float) (y + yOffset + 8);

		GuiHelper.fill(/*7,*/xOffset + x, yOffset + y, xOffset + x + 22, yOffset + y + 22,
				/*new Color(111, 0, 0, 54).getRGB() + (color << 16) + (color << 8) + color)*/pressed ? Color.red.getRGB() : colorN);/*new Color(0, 0,0,
				54).getRGB() + (color << 16) + (color << 8) + color)*/
		;
//		GL11.glLineWidth(3);
		GuiHelper.fill(2, xOffset + x, yOffset + y, xOffset + x + 22, yOffset + y + 22, new Color(255, 255, 255, 220).getRGB());
		minecraft.textRenderer.draw(keyBinding.getLocalizedName().toUpperCase(), (int) xPos, (int) yPos, pressed ? pressedTextColor : colorN);
		GL11.glLineWidth(1);

	}
}
