package theSilverEcho.tweaks.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import theSilverEcho.tweaks.ColourHelper;
import theSilverEcho.tweaks.config.Config;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomSidebar extends DrawableHelper
{
	private int height, width, posX, posY;
	private final boolean numbers = Config.isSidebarNumbers();

	public CustomSidebar()
	{
	}

	public boolean isMouseIn(int mouseX, int mouseY)
	{

		float scale = Config.getSidebarSize() - 1.0f;
		float minX = posX - width * scale;
		float maxX = minX + width * Config.getSidebarSize();
		float maxY = posY + (height >> 1) * scale;
		float minY = maxY - height * Config.getSidebarSize();
		return mouseX > minX && mouseX < maxX && mouseY > minY - MinecraftClient.getInstance().textRenderer.fontHeight * Config.getSidebarSize() && mouseY < maxY;
	}

	public void drawSidebar(ScoreboardObjective sidebar, int scaleX, int scaleY/*ScaledResolution res*/)
	{
		TextRenderer fr = MinecraftClient.getInstance().textRenderer;
		Scoreboard scoreboard = sidebar.getScoreboard();
		List<ScoreboardPlayerScore> scores = new ArrayList<>();

		this.width = fr.getStringWidth(sidebar.getName()) / 10;

		for (ScoreboardPlayerScore scoreboardPlayerScore : scoreboard.getAllPlayerScores(sidebar))
		{
			String name = scoreboardPlayerScore.getPlayerName();
			if (scores.size() < 15 && name != null && !name.startsWith("#"))
			{
				Team team = scoreboard.getTeam(name);
				String s2 = this.numbers ? ": " + Formatting.RED + scoreboardPlayerScore.getScore() : "";
				String str = Team.modifyText(team, new LiteralText(name)) + s2;
				this.width = Math.max(this.width, fr.getStringWidth(str) / 10);
				scores.add(scoreboardPlayerScore);
			}
		}

		this.height = scores.size() * fr.fontHeight;
		this.posX = scaleX - this.width - 3 + Config.getSidebarXOffset();
		this.posY = scaleY / 2 + this.height / 3 + Config.getSidebarYOffset();
		int scalePointX = this.posX + this.width;
		int scalePointY = this.posY - this.height / 2;
		float scale = Config.getSidebarSize() - 1.0F;
		RenderSystem.translatef((float) (-scalePointX) * scale, (float) (-scalePointY) * scale, 0.0F);
		RenderSystem.scalef(Config.getSidebarSize(), Config.getSidebarSize(), 1.0F);
		int index = 0;

		for (ScoreboardPlayerScore scoreboardPlayerScore : scores)
		{
			++index;
			Team team = scoreboard.getTeam(scoreboardPlayerScore.getPlayerName());
			Text s1 = Team.modifyText(team, new LiteralText(scoreboardPlayerScore.getPlayerName()));
			String s2 = Formatting.RED + "" + scoreboardPlayerScore.getScore();
			if (!this.numbers)
			{
				s2 = "";
			}

			int scoreX = this.posX + this.width + 1;
			int scoreY = this.posY - index * fr.fontHeight;
			fill(this.posX - 2, scoreY, scoreX, scoreY + fr.fontHeight,
					new Color(Config.getSidebarRed(), Config.getSidebarGreen(), Config.getSidebarBlue(), Config.getSidebarAlpha())
							.getRGB()/*ColourHelper.rgb().getRGB() | 23 << 24*/);
			//player names
			this.drawCustomString(s1.asString(), this.posX, scoreY, -1);
			//red number scores
			this.drawCustomString(s2, scoreX - fr.getStringWidth(s2), scoreY, /*553648127*/-1);
			if (index == scores.size())
			{
				String s3 = sidebar.getName();
				fill(this.posX - 2, scoreY - fr.fontHeight - 1, scoreX, scoreY - 1,
						new Color(Config.getSidebarRed(), Config.getSidebarGreen(), Config.getSidebarBlue(), Config.getSidebarAlpha())
								.getRGB()/*getColor(true)*/);
				fill(this.posX - 2, scoreY - 1, scoreX, scoreY, ColourHelper.rgb().getRGB()/*this.getColor(false)*/);
				drawCustomString(s3, this.posX + (this.width - fr.getStringWidth(s3)) / 2, scoreY - fr.fontHeight, -1);
			}
		}

		RenderSystem.scalef(1.0F / Config.getSidebarSize(), 1.0F / Config.getSidebarSize(), 1.0F);
		RenderSystem.translatef((float) scalePointX * scale, (float) scalePointY * scale, 0.0F);
		//		}
	}

	private void drawCustomString(String str, int x, int y, int color)
	{
		ColourHelper.drawChromaString(str, x, y, 2);
		//		this.textRenderer.drawWithShadow(str, x, y, color);
	}

}
