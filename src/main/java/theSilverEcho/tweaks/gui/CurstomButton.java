package theSilverEcho.tweaks.gui;

import net.minecraft.client.gui.Drawable;
import theSilverEcho.tweaks.Tweaks;

import java.awt.*;

public class CurstomButton extends GuiHelper implements Drawable
{
	protected int width;
	protected int height;
	public int x;
	public int y;
	private String message;
	private boolean wasHovered;
	protected boolean isHovered;
	public boolean active;
	public boolean visible;
	protected float alpha;
	protected long nextNarration;

	public CurstomButton(int width, int height, int x, int y, String message)
	{
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.message = message;
	}

	@Override public void render(int mouseX, int mouseY, float delta)
	{
		//		fillGradient(7, y, x, width, height, -1, 1);
		Tweaks.renderer.drawString(message, x, y, -1, false);
		this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		Color col = Color.red;
		if (isHovered)
			col = Color.CYAN;
		fill(7, x, y, width, height, col.getRGB());

	}
}
