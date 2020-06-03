package theSilverEcho.tweaks.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Rotation3;

public class GuiHelper extends DrawableHelper
{

	public static void fillGradient(int mode, int top, int left, int right, int bottom, int color1, int color2)
	{
		float f = (float) (color1 >> 24 & 255) / 255.0F;
		float g = (float) (color1 >> 16 & 255) / 255.0F;
		float h = (float) (color1 >> 8 & 255) / 255.0F;
		float i = (float) (color1 & 255) / 255.0F;
		float j = (float) (color2 >> 24 & 255) / 255.0F;
		float k = (float) (color2 >> 16 & 255) / 255.0F;
		float l = (float) (color2 >> 8 & 255) / 255.0F;
		float m = (float) (color2 & 255) / 255.0F;
		RenderSystem.disableTexture();
		RenderSystem.enableBlend();
		RenderSystem.disableAlphaTest();
		RenderSystem.defaultBlendFunc();
		RenderSystem.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(mode, VertexFormats.POSITION_COLOR);
		bufferBuilder.vertex(right, left, 0).color(g, h, i, f).next();
		bufferBuilder.vertex(top, left, 0).color(g, h, i, f).next();
		bufferBuilder.vertex(top, bottom, 0).color(k, l, m, j).next();
		bufferBuilder.vertex(right, bottom, 0).color(k, l, m, j).next();
		tessellator.draw();
		RenderSystem.shadeModel(7424);
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.enableTexture();
	}

	public static void fill(int mode, int x1, int y1, int x2, int y2, int color)
	{
		int j;
		if (x1 < x2)
		{
			j = x1;
			x1 = x2;
			x2 = j;
		}

		if (y1 < y2)
		{
			j = y1;
			y1 = y2;
			y2 = j;
		}

		float f = (float) (color >> 24 & 255) / 255.0F;
		float g = (float) (color >> 16 & 255) / 255.0F;
		float h = (float) (color >> 8 & 255) / 255.0F;
		float k = (float) (color & 255) / 255.0F;
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.enableBlend();
		RenderSystem.disableTexture();
		RenderSystem.defaultBlendFunc();
		bufferBuilder.begin(mode, VertexFormats.POSITION_COLOR);
		bufferBuilder.vertex(Rotation3.identity().getMatrix(), (float) x1, (float) y2, 0.0F).color(g, h, k, f).next();
		bufferBuilder.vertex(Rotation3.identity().getMatrix(), (float) x2, (float) y2, 0.0F).color(g, h, k, f).next();
		bufferBuilder.vertex(Rotation3.identity().getMatrix(), (float) x2, (float) y1, 0.0F).color(g, h, k, f).next();
		bufferBuilder.vertex(Rotation3.identity().getMatrix(), (float) x1, (float) y1, 0.0F).color(g, h, k, f).next();
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
	}

	public static void drawBox1(MatrixStack matrixStack, BufferBuilder buffer, double x1, double y1, double z1, double x2, double y2, double z2,
			float red, float green, float blue, float alpha)
	{
		Matrix4f matrix4f = matrixStack.peek().getModel();

		buffer.vertex(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x1, (float) y1, (float) z2).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x1, (float) y2, (float) z1).color(red, green, blue, alpha).next();

		buffer.vertex(matrix4f, (float) x1, (float) y2, (float) z2).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x1, (float) y2, (float) z2).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x1, (float) y1, (float) z2).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x2, (float) y1, (float) z2).color(red, green, blue, alpha).next();

		buffer.vertex(matrix4f, (float) x2, (float) y1, (float) z2).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x2, (float) y1, (float) z1).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x2, (float) y2, (float) z1).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x2, (float) y2, (float) z1).color(red, green, blue, alpha).next();

		buffer.vertex(matrix4f, (float) x2, (float) y1, (float) z1).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x1, (float) y2, (float) z1).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x2, (float) y1, (float) z1).color(red, green, blue, alpha).next();

		buffer.vertex(matrix4f, (float) x1, (float) y1, (float) z2).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x2, (float) y1, (float) z2).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x2, (float) y1, (float) z2).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x1, (float) y2, (float) z1).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x1, (float) y2, (float) z1).color(red, green, blue, alpha).next();

		buffer.vertex(matrix4f, (float) x1, (float) y2, (float) z2).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x2, (float) y2, (float) z1).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).next();
		buffer.vertex(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).next();
	}

	public static void line(int mode, double x1, double y1, double x2, double y2, int color)
	{

		float f = (float) (color >> 24 & 255) / 255.0F;
		float g = (float) (color >> 16 & 255) / 255.0F;
		float h = (float) (color >> 8 & 255) / 255.0F;
		float k = (float) (color & 255) / 255.0F;
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.enableBlend();
		RenderSystem.disableTexture();
		RenderSystem.defaultBlendFunc();
		bufferBuilder.begin(mode, VertexFormats.POSITION_COLOR);
		bufferBuilder.vertex(Rotation3.identity().getMatrix(), (float) x1, (float) y1, 0.0F).color(g, h, k, f).next();
		bufferBuilder.vertex(Rotation3.identity().getMatrix(), (float) x2, (float) y2, 0.0F).color(g, h, k, f).next();

		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
	}

	public static void fillCircle(int mode, int x, int y, int radius, int color)
	{
		float f = (float) (color >> 24 & 255) / 255.0F;
		float g = (float) (color >> 16 & 255) / 255.0F;
		float h = (float) (color >> 8 & 255) / 255.0F;
		float k = (float) (color & 255) / 255.0F;
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.enableBlend();
		RenderSystem.disableTexture();
		RenderSystem.defaultBlendFunc();
		bufferBuilder.begin(mode, VertexFormats.POSITION_COLOR);
		for (float w = 0; w <= 360; w += 0.01)
		{
			bufferBuilder.vertex(Rotation3.identity().getMatrix(), (float) (x + radius * Math.cos(Math.toRadians(w))), (float) (y - radius * Math.sin(
					Math.toRadians(w))), 0.0F).color(g, h, k, f).next();
		}
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
	}

	public static void fillEllipse(int mode, int x, int y, float radiusX, float radiusY, int color)
	{

		float f = (float) (color >> 24 & 255) / 255.0F;
		float g = (float) (color >> 16 & 255) / 255.0F;
		float h = (float) (color >> 8 & 255) / 255.0F;
		float k = (float) (color & 255) / 255.0F;
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.enableBlend();
		RenderSystem.disableTexture();
		RenderSystem.defaultBlendFunc();
		bufferBuilder.begin(mode, VertexFormats.POSITION_COLOR);

		for (float i = 0; i <= 360; i += 0.01)
		{
			bufferBuilder.vertex(Rotation3.identity().getMatrix(), ((float) (x + Math.cos(i * (Math.PI / 180)) * radiusX)), ((float) (y + Math.sin(
					i * (Math.PI / 180)) * radiusY)), 0.0F).color(g, h, k, f).next();
		}
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
	}

}
