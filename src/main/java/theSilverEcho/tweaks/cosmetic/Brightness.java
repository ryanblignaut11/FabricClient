package theSilverEcho.tweaks.cosmetic;

import net.minecraft.client.MinecraftClient;
import theSilverEcho.tweaks.config.Config;

public class Brightness
{
	public static void toggleBrightness()
	{
		Config.setBrightnessEnabled(!Config.isBrightnessEnabled());
		if (!Config.isBrightnessEnabled())
		{
			MinecraftClient.getInstance().options.gamma = Config.getPreviousBrightness();

		} else
		{
			Config.setPreviousBrightness((float) MinecraftClient.getInstance().options.gamma);
			MinecraftClient.getInstance().options.gamma = 100;

		}

	}
}
