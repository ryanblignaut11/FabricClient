package theSilverEcho.tweaks.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.List;

public class ConfigMenu extends Screen
{
	protected ConfigMenu()
	{
		super(new LiteralText("modScreen"));
	}

	@Override public void init(MinecraftClient client, int width, int height)
	{
		super.init(client, width, height);
	}
	@Override public void onClose()
	{
		super.onClose();
	}

	public boolean isPauseScreen()
	{
		return false;
	}

}
