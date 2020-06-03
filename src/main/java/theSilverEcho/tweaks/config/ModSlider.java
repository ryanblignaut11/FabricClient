package theSilverEcho.tweaks.config;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import theSilverEcho.tweaks.ColourHelper;

public class ModSlider extends SliderWidget
{
	private Runnable runnable;
	private double sliderValue;
	private final String name;
	private double configColour;
	public static final Identifier WIDGETS_LOCATION = new Identifier("tweaks", "textures/ui/widgets.png");

	public ModSlider(int x, int y, double progress, String name)
	{
		super(x, y, 150, 20, progress);
		this.name = name;
		this.configColour = progress;
		this.updateMessage();
	}

	@Override public void renderButton(int mouseX, int mouseY, float delta)
	{
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		TextRenderer textRenderer = minecraftClient.textRenderer;
		minecraftClient.getTextureManager().bindTexture(WIDGETS_LOCATION);
		if (Config.getGlintRed() == 0 && Config.getGlintGreen() == 0 && Config.getGlintBlue() == 0)
			RenderSystem.color4f(ColourHelper.rgb().getRed() / 255F, ColourHelper.rgb().getGreen() / 255F, ColourHelper.rgb().getBlue() / 255F,
					this.alpha);
		else
			RenderSystem.color4f(Config.getGlintRed() / 255F,/*1.0F,*/ Config.getGlintGreen() / 255F, Config.getGlintBlue() / 255F, this.alpha);
		int i = this.getYImage(this.isHovered());
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
		this.blit(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
		this.blit(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
		this.renderBg(minecraftClient, mouseX, mouseY);
		int j = this.active ? 16777215 : 10526880;
		this.drawCenteredString(textRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2,
				j | MathHelper.ceil(this.alpha * 255.0F) << 24);
	}

	protected void renderBg(MinecraftClient client, int mouseX, int mouseY)
	{
		client.getTextureManager().bindTexture(WIDGETS_LOCATION);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		int i = (this.isHovered() ? 2 : 1) * 20;
		this.blit(this.x + (int) (this.value * (double) (this.width - 8)), this.y, 0, 46 + i, 4, 20);
		this.blit(this.x + (int) (this.value * (double) (this.width - 8)) + 4, this.y, 196, 46 + i, 4, 20);
	}

	public void setRunnable(Runnable runnable)
	{
		this.runnable = runnable;
	}

	@Override protected void updateMessage()
	{
		this.value = configColour / 255;
		this.setMessage(name + ": " + ((int) (configColour)) + "");
	}

	@Override protected void applyValue()
	{
		sliderValue = this.value;
		configColour = this.value * 255;
		runnable.run();
	}

	public double getSliderValue()
	{
		return sliderValue;
	}
}
