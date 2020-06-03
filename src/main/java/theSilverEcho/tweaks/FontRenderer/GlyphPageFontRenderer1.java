package theSilverEcho.tweaks.FontRenderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import java.awt.*;
import java.util.Locale;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class GlyphPageFontRenderer1
{
	public Random fontRandom = new Random();
	/**
	 * Current X coordinate at which to draw the next character.
	 */
	private float posX;
	/**
	 * Current Y coordinate at which to draw the next character.
	 */
	private float posY;
	/**
	 * Array of RGB triplets defining the 16 standard chat colors followed by 16 darker version of the same colors for
	 * drop shadows.
	 */
	private int[] colorCode = new int[32];
	/**
	 * Used to specify new red value for the current color.
	 */
	private float red;
	/**
	 * Used to specify new blue value for the current color.
	 */
	private float blue;
	/**
	 * Used to specify new green value for the current color.
	 */
	private float green;
	/**
	 * Used to speify new alpha value for the current color.
	 */
	private float alpha;
	/**
	 * Text color of the currently rendering string.
	 */
	private int textColor;

	/**
	 * Set if the "k" style (random) is active in currently rendering string
	 */
	private boolean randomStyle;
	/**
	 * Set if the "l" style (bold) is active in currently rendering string
	 */
	private boolean boldStyle;
	/**
	 * Set if the "o" style (italic) is active in currently rendering string
	 */
	private boolean italicStyle;
	/**
	 * Set if the "n" style (underlined) is active in currently rendering string
	 */
	private boolean underlineStyle;
	/**
	 * Set if the "m" style (strikethrough) is active in currently rendering string
	 */
	private boolean strikethroughStyle;

	private GlyphP2 regularGlyphP2, boldGlyphP2, italicGlyphP2, boldItalicGlyphP2;

	public GlyphPageFontRenderer1(GlyphP2 regularGlyphP2, GlyphP2 boldGlyphP2, GlyphP2 italicGlyphP2, GlyphP2 boldItalicGlyphP2)
	{
		this.regularGlyphP2 = regularGlyphP2;
		this.boldGlyphP2 = boldGlyphP2;
		this.italicGlyphP2 = italicGlyphP2;
		this.boldItalicGlyphP2 = boldItalicGlyphP2;

		for (int i = 0; i < 32; ++i)
		{
			int j = (i >> 3 & 1) * 85;
			int k = (i >> 2 & 1) * 170 + j;
			int l = (i >> 1 & 1) * 170 + j;
			int i1 = (i & 1) * 170 + j;

			if (i == 6)
			{
				k += 85;
			}

			if (i >= 16)
			{
				k /= 4;
				l /= 4;
				i1 /= 4;
			}

			this.colorCode[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
		}
	}

	public static GlyphPageFontRenderer1 create(String fontName, int size, boolean bold, boolean italic, boolean boldItalic)
	{
		char[] chars = new char[256];

		for (int i = 0; i < chars.length; i++)
		{
			chars[i] = (char) i;
		}

		GlyphP2 regularPage;

		regularPage = new GlyphP2(new Font(fontName, Font.PLAIN, size), true, true);

		regularPage.generateGlyphPage(chars);
		regularPage.setupTexture();

		GlyphP2 boldPage = regularPage;
		GlyphP2 italicPage = regularPage;
		GlyphP2 boldItalicPage = regularPage;

		if (bold)
		{
			boldPage = new GlyphP2(new Font(fontName, Font.BOLD, size), true, true);

			boldPage.generateGlyphPage(chars);
			boldPage.setupTexture();
		}

		if (italic)
		{
			italicPage = new GlyphP2(new Font(fontName, Font.ITALIC, size), true, true);

			italicPage.generateGlyphPage(chars);
			italicPage.setupTexture();
		}

		if (boldItalic)
		{
			boldItalicPage = new GlyphP2(new Font(fontName, Font.BOLD | Font.ITALIC, size), true, true);

			boldItalicPage.generateGlyphPage(chars);
			boldItalicPage.setupTexture();
		}

		return new GlyphPageFontRenderer1(regularPage, boldPage, italicPage, boldItalicPage);
	}

	/**
	 * Draws the specified string.
	 */
	public int drawString(String text, float x, float y, int color, boolean dropShadow)
	{
		GlStateManager.enableAlphaTest();
		//		GlStateManager.enableAlphaTest();
		this.resetStyles();
		int i;

		if (dropShadow)
		{
			i = this.renderString(text, x + 1.0F, y + 1.0F, color, true);
			i = Math.max(i, this.renderString(text, x, y, color, false));
		} else
		{
			i = this.renderString(text, x, y, color, false);
		}

		return i;
	}

	/**
	 * Render single line string by setting GL color, current (posX,posY), and calling renderStringAtPos()
	 */
	private int renderString(String text, float x, float y, int color, boolean dropShadow)
	{
		if (text == null)
		{
			return 0;
		} else
		{

			if ((color & -67108864) == 0)
			{
				color |= -16777216;
			}

			if (dropShadow)
			{
				color = (color & 16579836) >> 2 | color & -16777216;
			}

			this.red = (float) (color >> 16 & 255) / 255.0F;
			this.blue = (float) (color >> 8 & 255) / 255.0F;
			this.green = (float) (color & 255) / 255.0F;
			this.alpha = (float) (color >> 24 & 255) / 255.0F;
			RenderSystem.color4f(this.red, this.blue, this.green, this.alpha);
			this.posX = x * 2.0f;
			this.posY = y * 2.0f;
			this.renderStringAtPos(text, dropShadow);
			return (int) (this.posX / 4.0f);
		}
	}

	/**
	 * Render a single line string at the current (posX,posY) and update posX
	 */
	private void renderStringAtPos(String text, boolean shadow)
	{

		GlyphP2 GlyphP2 = getCurrentGlyphP2();
//		RenderSystem.pushMatrix();
				glPushMatrix();

//		RenderSystem.scaled(0.5, 0.5, 0.5);
				glScaled(0.5, 0.5, 0.5);

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableTexture();

		GlyphP2.bindTexture();

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		for (int i = 0; i < text.length(); ++i)
		{
			char c0 = text.charAt(i);

			if (c0 == 167 && i + 1 < text.length())
			{
				int i1 = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));

				if (i1 < 16)
				{
					this.randomStyle = false;
					this.boldStyle = false;
					this.strikethroughStyle = false;
					this.underlineStyle = false;
					this.italicStyle = false;

					if (i1 < 0)
					{
						i1 = 15;
					}

					if (shadow)
					{
						i1 += 16;
					}

					int j1 = this.colorCode[i1];
					this.textColor = j1;

					RenderSystem.color4f((float) (j1 >> 16) / 255.0F, (float) (j1 >> 8 & 255) / 255.0F, (float) (j1 & 255) / 255.0F, this.alpha);
				} else if (i1 == 16)
				{
					this.randomStyle = true;
				} else if (i1 == 17)
				{
					this.boldStyle = true;
				} else if (i1 == 18)
				{
					this.strikethroughStyle = true;
				} else if (i1 == 19)
				{
					this.underlineStyle = true;
				} else if (i1 == 20)
				{
					this.italicStyle = true;
				} else
				{
					this.randomStyle = false;
					this.boldStyle = false;
					this.strikethroughStyle = false;
					this.underlineStyle = false;
					this.italicStyle = false;

					RenderSystem.color4f(this.red, this.blue, this.green, this.alpha);
				}

				++i;
			} else
			{
				GlyphP2 = getCurrentGlyphP2();

				GlyphP2.bindTexture();

				float f = GlyphP2.drawChar(c0, posX, posY);

				doDraw(f, GlyphP2);
			}
		}

		GlyphP2.unbindTexture();

		glPopMatrix();
//		RenderSystem.disableAlphaTest();
	}

	private void doDraw(float f, GlyphP2 GlyphP2)
	{
		if (this.strikethroughStyle)
		{
			System.out.println("strikethroughStyle");
			//			Tessellator tessellator = Tessellator.getInstance();
			//			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			//			GlStateManager.disableTexture2D();
			//			worldrenderer.begin(7, DefaultVertexFormats.POSITION);
			//			worldrenderer.pos((double) this.posX, (double) (this.posY + (float) (GlyphP2.getMaxFontHeight() / 2)), 0.0D).endVertex();
			//			worldrenderer.pos((double) (this.posX + f), (double) (this.posY + (float) (GlyphP2.getMaxFontHeight() / 2)), 0.0D).endVertex();
			//			worldrenderer.pos((double) (this.posX + f), (double) (this.posY + (float) (GlyphP2.getMaxFontHeight() / 2) - 1.0F), 0.0D).endVertex();
			//			worldrenderer.pos((double) this.posX, (double) (this.posY + (float) (GlyphP2.getMaxFontHeight() / 2) - 1.0F), 0.0D).endVertex();
			//			tessellator.draw();
			//			GlStateManager.enableTexture2D();
		}

		if (this.underlineStyle)
		{
			System.out.println("underlineStyle");
			//			Tessellator tessellator1 = Tessellator.getInstance();
			//			WorldRenderer worldrenderer1 = tessellator1.getWorldRenderer();
			//			GlStateManager.disableTexture();
			//			worldrenderer1.begin(7, DefaultVertexFormats.POSITION);
			//			int l = this.underlineStyle ? -1 : 0;
			//			worldrenderer1.pos((double) (this.posX + (float) l), (double) (this.posY + (float) GlyphP2.getMaxFontHeight()), 0.0D).endVertex();
			//			worldrenderer1.pos((double) (this.posX + f), (double) (this.posY + (float) GlyphP2.getMaxFontHeight()), 0.0D).endVertex();
			//			worldrenderer1.pos((double) (this.posX + f), (double) (this.posY + (float) GlyphP2.getMaxFontHeight() - 1.0F), 0.0D).endVertex();
			//			worldrenderer1.pos((double) (this.posX + (float) l), (double) (this.posY + (float) GlyphP2.getMaxFontHeight() - 1.0F), 0.0D)
			//			              .endVertex();
			//			tessellator1.draw();
			//			GlStateManager.enableTexture2D();
		}

		this.posX += f;
	}

	private GlyphP2 getCurrentGlyphP2()
	{
		if (boldStyle && italicStyle)
			return boldItalicGlyphP2;
		else if (boldStyle)
			return boldGlyphP2;
		else if (italicStyle)
			return italicGlyphP2;
		else
			return regularGlyphP2;
	}

	/**
	 * Reset all style flag fields in the class to false; called at the start of string rendering
	 */
	private void resetStyles()
	{
		this.randomStyle = false;
		this.boldStyle = false;
		this.italicStyle = false;
		this.underlineStyle = false;
		this.strikethroughStyle = false;
	}

	public int getFontHeight()
	{
		return regularGlyphP2.getMaxFontHeight() / 2;
	}

	public int getStringWidth(String text)
	{
		if (text == null)
		{
			return 0;
		}
		int width = 0;

		GlyphP2 currentPage;

		int size = text.length();

		boolean on = false;

		for (int i = 0; i < size; i++)
		{
			char character = text.charAt(i);

			if (character == '§')
				on = true;
			else if (on && character >= '0' && character <= 'r')
			{
				int colorIndex = "0123456789abcdefklmnor".indexOf(character);
				if (colorIndex < 16)
				{
					boldStyle = false;
					italicStyle = false;
				} else if (colorIndex == 17)
				{
					boldStyle = true;
				} else if (colorIndex == 20)
				{
					italicStyle = true;
				} else if (colorIndex == 21)
				{
					boldStyle = false;
					italicStyle = false;
				}
				i++;
				on = false;
			} else
			{
				if (on)
					i--;

				character = text.charAt(i);

				currentPage = getCurrentGlyphP2();

				width += currentPage.getWidth(character) - 8;
			}
		}

		return width / 2;
	}

	/**
	 * Trims a string to fit a specified Width.
	 */
	public String trimStringToWidth(String text, int width)
	{
		return this.trimStringToWidth(text, width, false);
	}

	/**
	 * Trims a string to a specified width, and will reverse it if par3 is set.
	 */
	public String trimStringToWidth(String text, int maxWidth, boolean reverse)
	{
		StringBuilder stringbuilder = new StringBuilder();

		boolean on = false;

		int j = reverse ? text.length() - 1 : 0;
		int k = reverse ? -1 : 1;
		int width = 0;

		GlyphP2 currentPage;

		for (int i = j; i >= 0 && i < text.length() && i < maxWidth; i += k)
		{
			char character = text.charAt(i);

			if (character == '§')
				on = true;
			else if (on && character >= '0' && character <= 'r')
			{
				int colorIndex = "0123456789abcdefklmnor".indexOf(character);
				if (colorIndex < 16)
				{
					boldStyle = false;
					italicStyle = false;
				} else if (colorIndex == 17)
				{
					boldStyle = true;
				} else if (colorIndex == 20)
				{
					italicStyle = true;
				} else if (colorIndex == 21)
				{
					boldStyle = false;
					italicStyle = false;
				}
				i++;
				on = false;
			} else
			{
				if (on)
					i--;

				character = text.charAt(i);

				currentPage = getCurrentGlyphP2();

				width += (currentPage.getWidth(character) - 8) / 2;
			}

			if (i > width)
			{
				break;
			}

			if (reverse)
			{
				stringbuilder.insert(0, character);
			} else
			{
				stringbuilder.append(character);
			}
		}

		return stringbuilder.toString();
	}
}
