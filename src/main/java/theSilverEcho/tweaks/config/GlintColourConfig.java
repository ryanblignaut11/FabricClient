package theSilverEcho.tweaks.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;

public class GlintColourConfig extends Screen
{
	public GlintColourConfig()
	{
		super(new LiteralText("config"));
	}

	@Override protected void init()
	{
		super.init();
		int width = MinecraftClient.getInstance().getWindow().getWidth();
		int center = this.width / 2 - 150 / 2;
		ModSlider redSlider = new ModSlider(center, 20, Config.getGlintRed(), "Red");
		redSlider.setRunnable(() -> Config.setGlintRed((int) (redSlider.getSliderValue() * 255)));
		this.addButton(redSlider);

		ModSlider blueSlider = new ModSlider(center, 40, Config.getGlintBlue(), "Blue");
		blueSlider.setRunnable(() -> Config.setGlintBlue((int) (blueSlider.getSliderValue() * 255)));
		this.addButton(blueSlider);
		ModSlider greenSlider = new ModSlider(center/*width / 2*/ /*+ 150 / 2*/, 60, Config.getGlintGreen(), "Green");
		greenSlider.setRunnable(() -> Config.setGlintGreen((int) (greenSlider.getSliderValue() * 255)));
		this.addButton(greenSlider);

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
