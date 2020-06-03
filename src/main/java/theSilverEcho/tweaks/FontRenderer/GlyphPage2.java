package theSilverEcho.tweaks.FontRenderer;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class GlyphPage2
{
	private int imgSize;
	private int fontHeight = -1;
	private Font font;
	private boolean antiAlising;
	private boolean fractionalMatracies;
	private HashMap<Character, customGlyph> characterGlyphHashMap;

	public GlyphPage2(Font font, boolean antiAlising, boolean fractionalMatracies)
	{
		this.font = font;
		this.antiAlising = antiAlising;
		this.fractionalMatracies = fractionalMatracies;
	}

	public void genGlyphPage(char[] chars)
	{
		double maxWidth = -1;
		double maxHeight = -1;

		AffineTransform affineTransform = new AffineTransform();
		FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, antiAlising, fractionalMatracies);

		for (char c : chars)
		{
			Rectangle2D stringBounds = font.getStringBounds(Character.toString(c), fontRenderContext);
			if (maxWidth < stringBounds.getWidth())
				maxWidth = stringBounds.getWidth();
			if (maxHeight < stringBounds.getHeight())
				maxHeight = stringBounds.getHeight();

		}
		maxWidth += 2;
		maxHeight += 2;

		imgSize = (int) Math.ceil(Math.max(Math.ceil(Math.sqrt(maxWidth * maxWidth * chars.length) / maxWidth),
				Math.ceil(Math.sqrt(maxHeight * maxHeight * chars.length) / maxHeight)) * Math.max(maxWidth, maxHeight)) + 1;

		BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();

		graphics.setFont(font);
		graphics.setColor(new Color(255, 255, 255, 0));

		graphics.fillRect(0, 0, imgSize, imgSize);

		graphics.setColor(Color.WHITE);

		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				fractionalMatracies ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				antiAlising ? RenderingHints.VALUE_ANTIALIAS_OFF : RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				antiAlising ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

		FontMetrics fontMetrics = graphics.getFontMetrics();

		int charHeight = 0;
		int posX = 0, posY = 0;

		for (char c : chars)
		{
			customGlyph glyph = new customGlyph();

			Rectangle2D bounds = fontMetrics.getStringBounds(Character.toString(c), graphics);

			glyph.width = bounds.getBounds().width + 8;
			glyph.height = bounds.getBounds().height;

			if (posY + glyph.height >= imgSize)
				throw new IllegalStateException("Not all characters will fit");

		}
	}

	static class customGlyph
	{
		private int x, y, width, height;

		public customGlyph()
		{
		}

		public customGlyph(int x, int y, int width, int height)
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		public int getX()
		{
			return x;
		}

		public customGlyph setX(int x)
		{
			this.x = x;
			return this;
		}

		public int getY()
		{
			return y;
		}

		public customGlyph setY(int y)
		{
			this.y = y;
			return this;
		}

		public int getWidth()
		{
			return width;
		}

		public customGlyph setWidth(int width)
		{
			this.width = width;
			return this;
		}

		public int getHeight()
		{
			return height;
		}

		public customGlyph setHeight(int height)
		{
			this.height = height;
			return this;
		}
	}

}
