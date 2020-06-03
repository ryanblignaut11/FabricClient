package theSilverEcho.tweaks.gui;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.opengl.GL11;
import theSilverEcho.tweaks.ColourHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParticleSystem
{
	private static final float SPEED = 1f;
	private static final float MIN_DIST = 100;
	private static final MinecraftClient MINECRAFT_CLIENT = MinecraftClient.getInstance();
	private final List<CustomParticle> customParticles = new ArrayList<>();
	private int dist;
	private boolean mouse;
	private boolean rainbow;

	public ParticleSystem(int num, int dist, boolean mouse, boolean rainbow)
	{
		addParticle(num);
		this.dist = dist;
		this.mouse = mouse;
		this.rainbow = rainbow;
	}

	private void addParticle(int num)
	{
		for (int i = 0; i < num; i++)
			customParticles.add(CustomParticle.genParticle());
	}

	public void tick(float delta)
	{
		for (CustomParticle particle : customParticles)
		{
			particle.tick(delta, SPEED);
		}
	}

	public void render(int mouseX, int mouseY)
	{
		GL11.glPushMatrix();
		GL11.glLineWidth(0.05f);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		for (CustomParticle particle : customParticles)
		{
			GL11.glColor4f(0, 0, 0, 1);
			GL11.glPointSize(particle.getSize());
			GL11.glBegin(GL11.GL_POINTS);

			GL11.glVertex2f(particle.getX(), particle.getY());
			GL11.glEnd();
			if (mouse)
			{

				java.awt.Color c = Color.BLACK;
				if (rainbow)
				{
					c = ColourHelper.rgb();
				}
				float distance = MathUtil.distance(particle.getX(), particle.getY(), mouseX, MINECRAFT_CLIENT.getWindow().getHeight() - mouseY);

				//				System.out.println(distance);
				//				System.out.println(dist);

				if (distance < dist)
				{

					float alpha = Math.min(1.0f, Math.min(1.0f, 1.0f - distance / dist));
					GL11.glColor4f(0, 1, 0, alpha);
					GL11.glLineWidth(0.75f);
					GL11.glBegin(GL11.GL_LINES);
					GL11.glVertex2f(particle.getX(), particle.getY());
					GL11.glVertex2f(mouseX, mouseY);
					GL11.glEnd();

				}

			}
			for (CustomParticle particle1 : customParticles)
			{
				float distance = particle.getDistanceTo(particle1);
				if (distance < MIN_DIST /*&& (particle.getDistanceTo(MINECRAFT_CLIENT.getWindow().getHeight() - mouseX,
						MINECRAFT_CLIENT.getWindow().getWidth() - mouseY)*/)
				{
					float alpha = Math.min(1.0f, Math.min(1.0F, 1.0F - distance / MIN_DIST));
					GL11.glColor4f(0, 0, 0, alpha);
					GL11.glBegin(GL11.GL_LINES);
					GL11.glVertex2f(particle.getX(), particle.getY());
					GL11.glVertex2f(particle1.getX(), particle1.getY());
					GL11.glEnd();

				}
			}
		}
		GL11.glPopMatrix();
		GL11.glLineWidth(1);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);

	}
}
