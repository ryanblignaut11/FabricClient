package theSilverEcho.tweaks.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.TridentItem;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class CustomCrossHair extends GuiHelper
{
	private static final MinecraftClient mc = MinecraftClient.getInstance();
	private static final double width = 3.5, height = 3.5;

	public static void render()
	{
		float progress = mc.player.getAttackCooldownProgress(0.0F);
		if (mc.player.getMainHandStack().getItem() instanceof BowItem || mc.player.getMainHandStack().getItem() instanceof CrossbowItem || mc.player
				.getMainHandStack().getItem() instanceof TridentItem)
		{
			progress = (float) (mc.player.getMainHandStack().getMaxUseTime() - mc.player.getItemUseTimeLeft()) / 20.0F;
			if (progress == 3600)
				progress = 0;
			if (progress > 1)
				progress = 1;

		}
		Color color = new Color(255, 255, 255, 50);
		if (mc.targetedEntity instanceof LivingEntity)
		{
			LivingEntity targetedEntity = (LivingEntity) mc.targetedEntity;
			color = new Color(0, 255, 0, 255);

			if (targetedEntity instanceof Monster)
				color = new Color(255, 0, 0, 255);
			if (targetedEntity instanceof PlayerEntity)
				color = new Color(0, 255, 255, 255);

		}

		drawXCrosshair(mc.getWindow().getScaledWidth() / 2, mc.getWindow().getScaledHeight() / 2, 3 + 5 - (int) (progress * 5), color);
		GL11.glLineWidth(1);

	}

	private static void drawArrowCrosshair(int screenWidth, int screenHeight, int renderGap, int width, int height, Color renderColour)
	{

		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		RenderSystem.disableAlphaTest();
		RenderSystem.disableBlend();
		if (true)
		{
			GL11.glLineWidth((float) (3 + 6));
			GuiHelper.line(GL11.GL_LINE_STRIP, screenWidth - width - 1, screenHeight + height + 1, screenWidth, screenHeight, Color.pink.getRGB());
			GuiHelper.line(GL11.GL_LINE_STRIP, screenWidth, screenHeight, screenWidth + width + 1, screenHeight + height + 1, Color.pink.getRGB());
		}
		GL11.glLineWidth((float) (5 + 1));
		GuiHelper.line(GL11.GL_LINE_STRIP, screenWidth - width, screenHeight + height, screenWidth, screenHeight, renderColour.getRGB());
		GuiHelper.line(GL11.GL_LINE_STRIP, screenWidth, screenHeight, screenWidth + width, screenHeight + height, renderColour.getRGB());
	}

	private static void drawXCrosshair(int screenWidth, int screenHeight, int renderGap, Color renderColour)
	{
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(0xde1);
		/*if (true)
		{
			GL11.glLineWidth((float) (3 + 5));
			GuiHelper.line(GL11.GL_LINE_STRIP, screenWidth - width - 1, screenHeight + height + 1, screenWidth, screenHeight, Color.pink.getRGB());
			GuiHelper.line(GL11.GL_LINE_STRIP, screenWidth, screenHeight, screenWidth + width + 1, screenHeight + height + 1, Color.pink.getRGB());
			GuiHelper.line(GL11.GL_LINE_STRIP, screenWidth + width + 1, screenHeight - height - 1, screenWidth, screenHeight, Color.pink.getRGB());
			GuiHelper.line(GL11.GL_LINE_STRIP, screenWidth, screenHeight, screenWidth - width - 1, screenHeight - height - 1, Color.pink.getRGB());
		}*/
		GL11.glLineWidth((float) (4 + 1));
		//bottom left
		GuiHelper.line(GL11.GL_LINES, screenWidth - width - renderGap, screenHeight + height + renderGap, screenWidth - renderGap,
				screenHeight + renderGap, renderColour.getRGB());
		//bottom right
		GuiHelper.line(GL11.GL_LINE_STRIP, screenWidth + renderGap, screenHeight + renderGap, screenWidth + width + renderGap,
				screenHeight + height + renderGap, renderColour.getRGB());
		//top right
		GuiHelper.line(GL11.GL_LINE_STRIP, screenWidth + width + renderGap, screenHeight - height - renderGap, screenWidth + renderGap,
				screenHeight - renderGap, renderColour.getRGB());
		//top left
		GuiHelper.line(GL11.GL_LINE_STRIP, screenWidth - renderGap, screenHeight - renderGap, screenWidth - width - renderGap,
				screenHeight - height - renderGap, renderColour.getRGB());
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);

	}

}
