package theSilverEcho.tweaks.FontRenderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class GlyphPagep
{
	private int imgSize;
	private int maxFontHeight = -1;
	private Font font;
	private boolean antiAliasing;
	private boolean fractionalMetrics;
	private HashMap<Character, Glyph> glyphCharacterMap = new HashMap<>();

	private BufferedImage bufferedImage;
	private NativeImageBackedTexture loadedTexture;

	public GlyphPagep(Font font, boolean antiAliasing, boolean fractionalMetrics)
	{
		this.font = font;
		this.antiAliasing = antiAliasing;
		this.fractionalMetrics = fractionalMetrics;
	}

	public void generateGlyphPage(char[] chars)
	{
		// Calculate glyphPageSize
		double maxWidth = -1;
		double maxHeight = -1;

		AffineTransform affineTransform = new AffineTransform();
		FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, antiAliasing, fractionalMetrics);

		for (char ch : chars)
		{
			Rectangle2D bounds = font.getStringBounds(Character.toString(ch), fontRenderContext);

			if (maxWidth < bounds.getWidth())
				maxWidth = bounds.getWidth();
			if (maxHeight < bounds.getHeight())
				maxHeight = bounds.getHeight();
		}

		// Leave some additional space

		maxWidth += 2;
		maxHeight += 2;

		imgSize = (int) Math.ceil(Math.max(Math.ceil(Math.sqrt(maxWidth * maxWidth * chars.length) / maxWidth),
				Math.ceil(Math.sqrt(maxHeight * maxHeight * chars.length) / maxHeight)) * Math.max(maxWidth, maxHeight)) + 1;

		bufferedImage = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = (Graphics2D) bufferedImage.getGraphics();

		g.setFont(font);
		// Set Color to Transparent
		g.setColor(new Color(255, 255, 255, 0));
		// Set the image background to transparent
		g.fillRect(0, 0, imgSize, imgSize);

		g.setColor(Color.white);

		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAliasing ? RenderingHints.VALUE_ANTIALIAS_OFF : RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				antiAliasing ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

		FontMetrics fontMetrics = g.getFontMetrics();

		int currentCharHeight = 0;
		int posX = 0;
		int posY = 1;

		for (char ch : chars)
		{
			Glyph glyph = new Glyph();

			Rectangle2D bounds = fontMetrics.getStringBounds(Character.toString(ch), g);

			glyph.width = bounds.getBounds().width + 8; // Leave some additional space
			glyph.height = bounds.getBounds().height;

			if (posY + glyph.height >= imgSize)
			{
				throw new IllegalStateException("Not all characters will fit");
			}

			if (posX + glyph.width >= imgSize)
			{
				posX = 0;
				posY += currentCharHeight;
				currentCharHeight = 0;
			}

			glyph.x = posX;
			glyph.y = posY;

			if (glyph.height > maxFontHeight)
				maxFontHeight = glyph.height;

			if (glyph.height > currentCharHeight)
				currentCharHeight = glyph.height;

			g.drawString(Character.toString(ch), posX + 2, posY + fontMetrics.getAscent());

			posX += glyph.width;

			glyphCharacterMap.put(ch, glyph);

		}
	}

	public void setupTexture()
	{


		NativeImage nativeImage = new NativeImage(bufferedImage.getWidth(), bufferedImage.getHeight(), true);
		loadedTexture = new NativeImageBackedTexture(nativeImage);
		System.out.println("created file");
		File file = new File("C:\\Users\\Ryan Blignaut\\Pictures/12334.png");
		try
		{
			loadedTexture.setImage(nativeImage);
//			ImageIO.write(bufferedImage, "png", file);
//			loadedTexture.getImage().writeFile(file);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		//		Identifier identifier = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("tweaks", loadedTexture);
		//		MinecraftClient.getInstance().getTextureManager().bindTexture(identifier);
		TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
	}

	public void bindTexture()
	{
		RenderSystem.bindTexture(loadedTexture.getGlId());
////		System.out.println(loadedTexture.getImage());
		//		GlStateManager.bindTexture(loadedTexture.getGlId());
//				MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tweaks", "textures/capes/img.png"));
		//		identifier;
		//		MinecraftClient.getInstance().getTextureManager().registerTexture(identifier,loadedTexture);
		//		MinecraftClient.getInstance().getTextureManager().bindTexture(identifier);
		//		System.out.println(identifier);
		//		System.out.println(MinecraftClient.getInstance().getTextureManager().getTexture(identifier));
		//		RenderSystem.bindTexture(loadedTexture.getGlId());
		//		loadedTexture.bindTexture();
		//		GlStateManager.bindTexture(loadedTexture.bindTexture(););
		//		GlStateManager.bindTexture(loadedTexture.getGlId());
	}

	public void unbindTexture()
	{
		//		MinecraftClient.getInstance().getTextureManager().destroyTexture(identifier);
		//		GlStateManager.bindTexture(0);
		//		MinecraftClient.getInstance().textRenderer.draw()
		RenderSystem.bindTexture(0);
	}

	public float drawChar(char ch, float x, float y)
	{
		Glyph glyph = glyphCharacterMap.get(ch);

		if (glyph == null)
			throw new IllegalArgumentException("'" + ch + "' wasn't found");

		float pageX = glyph.x / (float) imgSize;
		float pageY = glyph.y / (float) imgSize;

		float pageWidth = glyph.width / (float) imgSize;
		float pageHeight = glyph.height / (float) imgSize;

		float width = glyph.width;
		float height = glyph.height;
//				MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tweaks", "textures/capes/128/cloak_128-1.png"));
//		MinecraftClient.getInstance().getTextureManager().bindTexture(identifier);
		MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tweaks", "textures/capes/img.png"));

//		Tessellator tessellator = Tessellator.getInstance();
//		BufferBuilder bufferBuilder = tessellator.getBuffer();
//		bufferBuilder.begin(GL_TRIANGLES, VertexFormats.POSITION_TEXTURE);
//		bufferBuilder.vertex(x + width, y, 0).texture(pageX + pageWidth, pageY).next();
//		bufferBuilder.vertex(x, y, 0).texture(pageX, pageY).next();
//		bufferBuilder.vertex(x, y + height, 0).texture(pageX, pageY + pageHeight).next();
//		bufferBuilder.vertex(x, y + height, 0).texture(pageX, pageY + pageHeight).next();
//		bufferBuilder.vertex(x + width, y + height, 0).texture(pageX + pageWidth, pageY + pageHeight).next();
//		bufferBuilder.vertex(x + width, y, 0).texture(pageX + pageWidth, pageY).next();
//		//		bufferBuilder.end();
//		tessellator.draw();

						glBegin(GL_TRIANGLES);

						glTexCoord2f(pageX + pageWidth, pageY);
						glVertex2f(x + width, y);

						glTexCoord2f(pageX, pageY);
						glVertex2f(x, y);

						glTexCoord2f(pageX, pageY + pageHeight);
						glVertex2f(x, y + height);

						glTexCoord2f(pageX, pageY + pageHeight);
						glVertex2f(x, y + height);

						glTexCoord2f(pageX + pageWidth, pageY + pageHeight);
						glVertex2f(x + width, y + height);

						glTexCoord2f(pageX + pageWidth, pageY);
						glVertex2f(x + width, y);

						glEnd();

		return width - 8;
	}

	public float getWidth(char ch)
	{
		return glyphCharacterMap.get(ch).width;
	}

	public int getMaxFontHeight()
	{
		return maxFontHeight;
	}

	public boolean isAntiAliasingEnabled()
	{
		return antiAliasing;
	}

	public boolean isFractionalMetricsEnabled()
	{
		return fractionalMetrics;
	}

	static class Glyph
	{
		private int x;
		private int y;
		private int width;
		private int height;

		Glyph(int x, int y, int width, int height)
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		Glyph()
		{
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}

		public int getWidth()
		{
			return width;
		}

		public int getHeight()
		{
			return height;
		}
	}
}
