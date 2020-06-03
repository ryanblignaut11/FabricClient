package theSilverEcho.tweaks.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import theSilverEcho.tweaks.ColourHelper;
import theSilverEcho.tweaks.config.Config;

@Mixin(RenderPhase.class) public abstract class GlintColour
{

	//	@ModifyVariable(method = "<init>", at = "GLINT_TRANSPARENCY" ) private static void setCreativeTabsArray() { }

	//	@Final
	//	@Shadow
	//	protected static RenderPhase.Transparency GLINT_TRANSPARENCY;

	/**
	 * @author theSilverEcho
	 * @reason glintColour
	 */
	@Overwrite private static void setupGlintTexturing(float scale)
	{

		RenderSystem.matrixMode(5890);
		//				MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tweaks", "textures/glint/glint.png"));
		//		RenderSystem.enableBlend();
		//		RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.DST_COLOR);
		//		RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_COLOR, GlStateManager.DstFactor.ONE);

		//				RenderSystem.blendFuncSeparate(GL_ONE_MINUS_SRC_COLOR, GL_ONE, GL_ONE, GL_ONE);
		//		RenderSystem.disableDepthTest();
		if (Config.getGlintRed() / 255F == 0 && Config.getGlintGreen() / 255F == 0 && Config.getGlintBlue() / 255F == 0)
			RenderSystem.color4f(ColourHelper.rgb().getRed() / 255F, ColourHelper.rgb().getGreen() / 255F, ColourHelper.rgb().getBlue() / 255F, 1);
		else
			RenderSystem.color4f(Config.getGlintRed() / 255F, Config.getGlintGreen() / 255F, Config.getGlintBlue() / 255F, 1);
		RenderSystem.pushMatrix();
		RenderSystem.loadIdentity();
		long l = Util.getMeasuringTimeMs() * 8L;
		float f = (float) (l % 110000L) / 110000.0F;
		float g = (float) (l % 30000L) / 30000.0F;
		RenderSystem.translatef(-f, g, 0.0F);
		RenderSystem.rotatef(10.0F, 0.0F, 0.0F, 1.0F);
		RenderSystem.scalef(scale, scale, scale);
		RenderSystem.matrixMode(5888);
		//		RenderSystem.enableDepthTest();
		//		RenderSystem.disableBlend();

	}
}