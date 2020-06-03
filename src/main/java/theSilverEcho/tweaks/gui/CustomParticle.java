package theSilverEcho.tweaks.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec2f;

import java.util.Random;

public class CustomParticle
{
	private static final Random RANDOM = new Random();
	private static final MinecraftClient MINECRAFT_CLIENT = MinecraftClient.getInstance();

	private Vec2f vel;
	private float x;
	private float y;
	private float size;

	public CustomParticle(Vec2f vel, float x, float y, float size)
	{
		this.vel = vel;
		this.x = x;
		this.y = y;
		this.size = size;
	}

	public static CustomParticle genParticle()
	{
		Vec2f vel = new Vec2f(((float) (Math.random() * 2.0f - 1.0f)), ((float) (Math.random() * 2.0 - 1.0f)));
		float x = RANDOM.nextInt(MINECRAFT_CLIENT.getWindow().getScaledWidth());
		float y = RANDOM.nextInt(MINECRAFT_CLIENT.getWindow().getScaledHeight());
		System.out.println(MINECRAFT_CLIENT.getWindow().getScaledWidth());
		float size = (float) (Math.random() * 4f) + 1f;
		return new CustomParticle(vel, x, y, size);
	}

	public Vec2f getVel()
	{
		return vel;
	}

	public CustomParticle setVel(Vec2f vel)
	{
		this.vel = vel;
		return this;
	}

	public float getX()
	{
		return x;
	}

	public CustomParticle setX(float x)
	{
		this.x = x;
		return this;
	}

	public float getY()
	{
		return y;
	}

	public CustomParticle setY(float y)
	{
		this.y = y;
		return this;
	}

	public float getSize()
	{
		return size;
	}

	public CustomParticle setSize(float size)
	{
		this.size = size;
		return this;
	}

	public void tick(float delta, float speed)
	{
		x += vel.x * delta * speed;
		y += vel.y * delta * speed;

		if (getX() > MINECRAFT_CLIENT.getWindow().getScaledWidth())
			setX(0);
		if (getX() < 0)
			setX(MINECRAFT_CLIENT.getWindow().getScaledWidth());

		if (getY() > MINECRAFT_CLIENT.getWindow().getScaledHeight())
			setY(0);
		if (getY() < 0)
			setY(MINECRAFT_CLIENT.getWindow().getScaledHeight());

	}

	public float getDistanceTo(CustomParticle particle1)
	{
		return getDistanceTo(particle1.getX(), particle1.getY());
	}

	public float getDistanceTo(float x, float y)
	{
		return MathUtil.distance(getX(), getY(), x, y);
		//		return (float) Math.sqrt((x + getX()) * (x + getX()) + (y + getY()) * (y + getY()));
	}
}
