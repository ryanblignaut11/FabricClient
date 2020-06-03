package theSilverEcho.tweaks.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import theSilverEcho.tweaks.Tweaks;
import theSilverEcho.tweaks.config.Config;
import theSilverEcho.tweaks.config.ModSlider;

public class CustomSidebarConfigScreen extends Screen
{
	public CustomSidebarConfigScreen()
	{
		super(new LiteralText("SidebarConfig1"));
	}

	@Override protected void init()
	{
		super.init();

		int center = this.width / 2 - 150 / 2;


		ModSlider redSlider = new ModSlider(center, 20, Config.getSidebarRed(), "Red");
		redSlider.setRunnable(() -> Config.setSidebarRed((int) (redSlider.getSliderValue() * 255)));
		this.addButton(redSlider);

		ModSlider greenSlider = new ModSlider(center, 40, Config.getSidebarGreen(), "Green");
		greenSlider.setRunnable(() -> Config.setSidebarGreen((int) (greenSlider.getSliderValue() * 255)));
		this.addButton(greenSlider);

		ModSlider blueSlider = new ModSlider(center, 60, Config.getSidebarBlue(), "Blue");
		blueSlider.setRunnable(() -> Config.setSidebarBlue((int) (blueSlider.getSliderValue() * 255)));
		this.addButton(blueSlider);

		ModSlider alphaSlider = new ModSlider(center, 80, Config.getSidebarAlpha(), "alpha");
		alphaSlider.setRunnable(() -> Config.setSidebarAlpha((int) (alphaSlider.getSliderValue() * 255)));
		this.addButton(alphaSlider);

	/*	ModSlider sizeSlider = new ModSlider(center, 100, Config.getSidebarSize(), "size");
		sizeSlider.setRunnable(() -> Config.setSidebarSize((float) (sizeSlider.getSliderValue()*2)));
		this.addButton(sizeSlider);*/

	}

	@Override public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY)
	{
		super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		boolean mouseIn = Tweaks.customSidebar.isMouseIn((int) mouseX, (int) mouseY);
		if (mouseIn && Tweaks.customSidebar != null)
		{
			Config.setSidebarXOffset((int) (Config.getSidebarXOffset() + Math.signum(deltaX)));
			Config.setSidebarYOffset((int) (Config.getSidebarYOffset() + Math.signum(deltaY)));
			return true;
		}
		return true;
	}

	@Override public boolean mouseScrolled(double d, double e, double amount)
	{

		if (Tweaks.customSidebar.isMouseIn(((int) d), ((int) e)))
		{
			sendMessage(Config.getSidebarSize()+"");
			if (Config.getSidebarSize() + 0.1*Math.signum(amount) > 0.5 && Config.getSidebarSize() + 0.1*Math.signum(amount) < 3)
				Config.setSidebarSize((float) ( Config.getSidebarSize()+0.1*Math.signum(amount)));

		}
		return true;
	}
}
