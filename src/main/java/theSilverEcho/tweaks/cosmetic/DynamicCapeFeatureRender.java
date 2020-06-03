package theSilverEcho.tweaks.cosmetic;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;

public class DynamicCapeFeatureRender
{
	private static long COUNTER = 0L;
	private static long lastNanoTime = 0L;
	private int vertexVbo;
	private int textureVbo;
	private FloatBuffer vertexBuffer;
	private FloatBuffer textureBuffer;
	private int vertexCount = 0;
	private float lastHorz = 0.0F;
	private float lastVert = 0.0F;
	private float lastAmplitude = 0.0F;
	private boolean deleted = false;

	public DynamicCapeFeatureRender()
	{
		this.vertexBuffer = BufferUtils.createFloatBuffer(294);
		this.vertexBuffer.flip();
		this.vertexVbo = GL15.glGenBuffers();
		GL15.glBindBuffer(34962, this.vertexVbo);
		GL15.glBufferData(34962, this.vertexBuffer, 35048);
		this.textureBuffer = BufferUtils.createFloatBuffer(196);
		this.textureBuffer.flip();
		this.textureVbo = GL15.glGenBuffers();
		GL15.glBindBuffer(34962, this.textureVbo);
		GL15.glBufferData(34962, this.textureBuffer, 35044);
		GL15.glBindBuffer(34962, 0);
	}

	public static void tickCloaks(long paramLong)
	{
		if (paramLong - lastNanoTime > 16666666L)
		{
			lastNanoTime = paramLong;
			COUNTER++;
		}
	}

	public void updateCloak(float paramFloat1, float paramFloat2, boolean paramBoolean)
	{
		paramFloat1 /= 55.0F;
		float f1 = (float) COUNTER / 10.0F;
		float f2 = paramFloat1;
		float f3 = 1.5F;
		if (this.deleted)
			return;
		if (!paramBoolean && paramFloat1 == this.lastHorz && paramFloat2 == this.lastVert && this.lastAmplitude == f2 && f2 == 0.0F)
			return;
		this.lastAmplitude = f2;
		this.vertexBuffer.clear();
		this.vertexCount = 0;
		if (paramBoolean)
			this.textureBuffer.clear();
		float f4 = 22.0F;
		float f5 = 23.0F;
		float f6 = 1.0F / f5;
		float f7 = 17.0F / f5;
		float f8 = 1.0F / f4;
		float f9 = 11.0F / f4;
		float f10 = 12.0F / f4;
		float f11 = 22.0F / f4;
		float f12 = 21.0F / f4;
		b b1 = new b(0.0F, 0.0F, 5.0F);
		b b2 = new b(0.0F, -16.0F, 5.0F);
		b b3 = new b(0.0F, -16.0F, -5.0F);
		b b4 = new b(0.0F, 0.0F, -5.0F);
		b b5 = new b(1.0F, 0.0F, 5.0F);
		b b6 = new b(1.0F, -16.0F, 5.0F);
		b b7 = new b(1.0F, -16.0F, -5.0F);
		b b8 = new b(1.0F, 0.0F, -5.0F);
		b b9 = new b(0.0F, -10.0F, -5.0F);
		b b10 = new b(0.0F, -10.0F, -5.0F);
		float f13 = 10.0F;
		boolean bool = true;
		float f14 = (float) Math.sin(Math.PI * f3 + f1) * f2;
		float f15 = -f14;
		byte b11;
		for (b11 = 0; b11 <= f13; b11++)
		{
			float f17 = b11 / f13;
			float f18 = (1.0F - f17) * (1.0F - f17) * b7.a + 2.0F * (1.0F - f17) * f17 * b9.a + f17 * f17 * b8.a;
			float f19 = (1.0F - f17) * (1.0F - f17) * b7.b + 2.0F * (1.0F - f17) * f17 * b9.b + f17 * f17 * b8.b;
			if (bool)
				f18 = (float) Math.sin(f17 * Math.PI * f3 + f1) * f2 + 1.0F + f15;
			if (paramBoolean)
			{
				this.textureBuffer.put(new float[] { f8, f7 - (f7 - f6) * f17 });
				this.textureBuffer.put(new float[] { f9, f7 - (f7 - f6) * f17 });
			}
			this.vertexBuffer.put(new float[] { f18, f19, b5.c });
			this.vertexBuffer.put(new float[] { f18, f19, b8.c });
			this.vertexCount += 2;
		}
		if (paramBoolean)
		{
			this.textureBuffer.put(new float[] { 0.045454547F, 0.04347826F });
			this.textureBuffer.put(new float[] { 0.5F, 0.04347826F });
			this.textureBuffer.put(new float[] { 0.045454547F, 0.0F });
			this.textureBuffer.put(new float[] { 0.5F, 0.0F });
		}
		this.vertexBuffer.put(new float[] { b5.a, b5.b, b5.c });
		this.vertexBuffer.put(new float[] { b8.a, b8.b, b8.c });
		this.vertexBuffer.put(new float[] { b1.a, b1.b, b1.c });
		this.vertexBuffer.put(new float[] { b4.a, b4.b, b4.c });
		this.vertexCount += 4;
		for (b11 = 0; b11 <= f13; b11++)
		{
			float f17 = b11 / f13;
			float f18 = (1.0F - f17) * (1.0F - f17) * b4.a + 2.0F * (1.0F - f17) * f17 * b10.a + f17 * f17 * b3.a;
			float f19 = (1.0F - f17) * (1.0F - f17) * b4.b + 2.0F * (1.0F - f17) * f17 * b10.b + f17 * f17 * b3.b;
			if (bool)
				f18 = (float) Math.sin((1.0F - f17) * Math.PI * f3 + f1) * f2 + f15;
			if (paramBoolean)
			{
				this.textureBuffer.put(new float[] { f10, f6 + (f7 - f6) * f17 });
				this.textureBuffer.put(new float[] { f9, f6 + (f7 - f6) * f17 });
			}
			this.vertexBuffer.put(new float[] { f18, f19, b8.c });
			f18 = (1.0F - f17) * (1.0F - f17) * b8.a + 2.0F * (1.0F - f17) * f17 * b9.a + f17 * f17 * b7.a;
			f19 = (1.0F - f17) * (1.0F - f17) * b8.b + 2.0F * (1.0F - f17) * f17 * b9.b + f17 * f17 * b7.b;
			if (bool)
				f18 = (float) Math.sin((1.0F - f17) * Math.PI * f3 + f1) * f2 + 1.0F + f15;
			this.vertexBuffer.put(new float[] { f18, f19, b8.c });
			this.vertexCount += 2;
		}
		if (paramBoolean)
		{
			this.textureBuffer.put(new float[] { 0.95454544F, 0.0F });
			this.textureBuffer.put(new float[] { 0.5F, 0.0F });
			this.textureBuffer.put(new float[] { 0.95454544F, 0.04347826F });
			this.textureBuffer.put(new float[] { 0.5F, 0.04347826F });
		}
		float f16 = 0.0F;
		if (bool)
			f16 = (float) Math.sin(0.0D * f3 + f1) * f2 + f15;
		this.vertexBuffer.put(new float[] { b7.a + f16, b7.b, b7.c });
		this.vertexBuffer.put(new float[] { b6.a + f16, b6.b, b6.c });
		this.vertexBuffer.put(new float[] { b3.a + f16, b3.b, b3.c });
		this.vertexBuffer.put(new float[] { b2.a + f16, b2.b, b2.c });
		this.vertexCount += 4;
		byte b12;
		for (b12 = 0; b12 <= f13; b12++)
		{
			float f17 = b12 / f13;
			float f18 = (1.0F - f17) * (1.0F - f17) * b3.a + 2.0F * (1.0F - f17) * f17 * b10.a + f17 * f17 * b4.a;
			float f19 = (1.0F - f17) * (1.0F - f17) * b3.b + 2.0F * (1.0F - f17) * f17 * b10.b + f17 * f17 * b4.b;
			if (bool)
				f18 = (float) Math.sin(f17 * Math.PI * f3 + f1) * f2 + f15;
			if (paramBoolean)
			{
				this.textureBuffer.put(new float[] { f10, f7 - (f7 - f6) * f17 });
				this.textureBuffer.put(new float[] { f11, f7 - (f7 - f6) * f17 });
			}
			this.vertexBuffer.put(new float[] { f18, f19, b8.c });
			this.vertexBuffer.put(new float[] { f18, f19, b5.c });
			this.vertexCount += 2;
		}
		for (b12 = 0; b12 <= f13; b12++)
		{
			float f17 = b12 / f13;
			if (b12 == 0)
			{
				float f20 = (1.0F - f17) * (1.0F - f17) * b4.a + 2.0F * (1.0F - f17) * f17 * b10.a + f17 * f17 * b3.a;
				float f21 = (1.0F - f17) * (1.0F - f17) * b4.b + 2.0F * (1.0F - f17) * f17 * b10.b + f17 * f17 * b3.b;
				if (paramBoolean)
				{
					this.textureBuffer.put(new float[] { 0.0F, f6 + (f7 - f6) * f17 });
					this.textureBuffer.put(new float[] { 0.0F, f6 + (f7 - f6) * f17 });
				}
				this.vertexBuffer.put(new float[] { f20, f21, b5.c });
				this.vertexBuffer.put(new float[] { f20, f21, b5.c });
				this.vertexCount += 2;
			}
			float f18 = (1.0F - f17) * (1.0F - f17) * b8.a + 2.0F * (1.0F - f17) * f17 * b9.a + f17 * f17 * b7.a;
			float f19 = (1.0F - f17) * (1.0F - f17) * b8.b + 2.0F * (1.0F - f17) * f17 * b9.b + f17 * f17 * b7.b;
			if (bool)
				f18 = (float) Math.sin((1.0F - f17) * Math.PI * f3 + f1) * f2 + 1.0F + f15;
			if (paramBoolean)
			{
				this.textureBuffer.put(new float[] { f8, f6 + (f7 - f6) * f17 });
				this.textureBuffer.put(new float[] { 0.0F, f6 + (f7 - f6) * f17 });
			}
			this.vertexBuffer.put(new float[] { f18, f19, b5.c });
			f18 = (1.0F - f17) * (1.0F - f17) * b4.a + 2.0F * (1.0F - f17) * f17 * b10.a + f17 * f17 * b3.a;
			f19 = (1.0F - f17) * (1.0F - f17) * b4.b + 2.0F * (1.0F - f17) * f17 * b10.b + f17 * f17 * b3.b;
			if (bool)
				f18 = (float) Math.sin((1.0F - f17) * Math.PI * f3 + f1) * f2 + f15;
			this.vertexBuffer.put(new float[] { f18, f19, b5.c });
			this.vertexCount += 2;
		}
		this.vertexBuffer.flip();
		GL15.glBindBuffer(34962, this.vertexVbo);
		GL15.glBufferData(34962, this.vertexBuffer, 35048);
		if (paramBoolean)
		{
			this.textureBuffer.flip();
			GL15.glBindBuffer(34962, this.textureVbo);
			GL15.glBufferData(34962, this.textureBuffer, 35044);
		}
		GL15.glBindBuffer(34962, 0);
		this.lastHorz = paramFloat1;
		this.lastVert = paramFloat2;
	}

	public void renderDynamicCloak()
	{
		if (this.deleted)
			return;
		boolean bool = false;
		if (bool)
		{
			GL11.glPolygonMode(1029, 6913);
			GL11.glPolygonMode(1028, 6913);
		}
		GL15.glBindBuffer(34962, this.vertexVbo);
		GL11.glVertexPointer(3, 5126, 0, 0L);
		GL15.glBindBuffer(34962, this.textureVbo);
		GL11.glTexCoordPointer(2, 5126, 0, 0L);
		GL11.glEnableClientState(32884);
		GL11.glEnableClientState(32888);
		GL11.glDrawArrays(5, 0, this.vertexCount);
		GL11.glDisableClientState(32888);
		GL11.glDisableClientState(32884);
		GL15.glBindBuffer(34962, 0);
		if (bool)
		{
			GL11.glPolygonMode(1029, 6914);
			GL11.glPolygonMode(1028, 6914);
		}
	}

	public void deleteData()
	{
		this.deleted = true;
		GL15.glDeleteBuffers(this.vertexVbo);
		GL15.glDeleteBuffers(this.textureVbo);
	}
}
