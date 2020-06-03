package theSilverEcho.tweaks.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import theSilverEcho.tweaks.ColourHelper;

import java.util.stream.StreamSupport;

@Mixin(WorldRenderer.class) public abstract class NameTagMixin
{
	private static final Identifier TEXTURE = new Identifier("tweaks", "textures/ui/default_health_bar.png");

	//	@Shadow public abstract void drawBox(BufferBuilder buffer, double x1, double y1, double z1, double x2, double y2, double z2, float red,
	//			float green, float blue, float alpha);

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;checkEmpty(Lnet/minecraft/client/util/math/MatrixStack;)V", ordinal = 0)) private void render(
			MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer,
			LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci)
	{
		StreamSupport.stream(MinecraftClient.getInstance().world.getEntities().spliterator(), false).filter(
				entity -> entity instanceof ItemEntity && entity.isAlive()).map(ItemEntity.class::cast).forEach(
				entity -> renderHealthBar(entity, matrices, tickDelta, camera,
						camera.getFocusedEntity() != null ? camera.getFocusedEntity() : MinecraftClient.getInstance().player));

		//				HealthBarRenderer.render(matrices, tickDelta, camera, gameRenderer, lightmapTextureManager, matrix4f, this.capturedFrustum);

	}

	private static void renderHealthBar(ItemEntity passedEntity, MatrixStack matrices, float partialTicks, Camera camera, Entity viewPoint)
	{
		MinecraftClient mc = MinecraftClient.getInstance();
		matrices.push();
		{
			double x = passedEntity.prevX + (passedEntity.getX() - passedEntity.prevX) * partialTicks;
			double y = passedEntity.prevY + (passedEntity.getY() - passedEntity.prevY) * partialTicks;
			double z = passedEntity.prevZ + (passedEntity.getZ() - passedEntity.prevZ) * partialTicks;
			EntityRenderDispatcher renderManager = mc.getEntityRenderManager();
			matrices.push();
			{
				matrices.translate(x - renderManager.camera.getPos().x, y - renderManager.camera.getPos().y + passedEntity.getHeight() + 1,
						z - renderManager.camera.getPos().z);
				GL11.glNormal3f(0.0F, 1.0F, 0.0F);
				RenderSystem.disableLighting();
				VertexConsumerProvider.Immediate immediate = mc.getBufferBuilders().getEntityVertexConsumers();
				final int light = 0xF000F0;
				renderEntity(matrices, immediate, camera, passedEntity, light);

			}
			matrices.pop();
			//			matrices.translate(0, 1, 0);
		}
		matrices.pop();

	}

	private static int getRed(int argb)
	{
		return (argb >> 16) & 0xFF;
	}

	private static int getGreen(int argb)
	{
		return (argb >> 8) & 0xFF;
	}

	private static int getBlue(int argb)
	{
		return argb & 0xFF;
	}

	private static void renderEntity(MatrixStack matrices, VertexConsumerProvider.Immediate immediate, Camera camera, ItemEntity passedEntity,
			int light)
	{
		String toDraw = passedEntity.getStack().getCount() + " " + passedEntity.getStack().getName().asString();
		if (!toDraw.isEmpty())
		{
			MinecraftClient mc = MinecraftClient.getInstance();
			Quaternion rotation = camera.getRotation().copy();
			rotation.scale(-1.0F);
			matrices.multiply(rotation);
			float scale = 0.026666672F;
			matrices.scale(-scale, -scale, -scale);
			float size = 3;
			float textScale = 2 * 0.5F;

			//		matrices.scale(-scale, -scale, -scale);
			float namel = mc.textRenderer.getStringWidth(toDraw) * textScale;
			if (namel + 20 > size * 2)
			{
				size = namel / 2.0F + 10.0F;
			}

			MatrixStack.Entry entry = matrices.peek();
			Matrix4f modelViewMatrix = entry.getModel();

			Vector3f normal = new Vector3f(0.0F, 1.0F, 0.0F);
			normal.transform(entry.getNormal());
			VertexConsumer buffer = immediate.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE, false));
			int defaultUv = OverlayTexture.DEFAULT_UV;
			int col = getColor(passedEntity);
			int red = getRed(col);
			int green = getGreen(col);
			int blue = getBlue(col);

			buffer.vertex(modelViewMatrix, -size, -5, 0.0F).color(red, green, blue, 127).texture(0.0F, 0.0F).overlay(defaultUv).light(light).normal(
					normal.getX(), normal.getY(), normal.getZ()).next();
			buffer.vertex(modelViewMatrix, -size, 5, 0.0F).color(red, green, blue, 127).texture(0.0F, 0.5F).overlay(defaultUv).light(light).normal(
					normal.getX(), normal.getY(), normal.getZ()).next();
			buffer.vertex(modelViewMatrix, size, 5, 0.0F).color(red, green, blue, 127).texture(1.0F, 0.5F).overlay(defaultUv).light(light).normal(
					normal.getX(), normal.getY(), normal.getZ()).next();
			buffer.vertex(modelViewMatrix, size, -5, 0.0F).color(red, green, blue, 127).texture(1.0F, 0.0F).overlay(defaultUv).light(light).normal(
					normal.getX(), normal.getY(), normal.getZ()).next();
			matrices.push();
			matrices.translate(-size, -3.5F, 0.0F);
			matrices.scale(textScale, textScale, textScale);
			Matrix4f model = matrices.peek().getModel();
			mc.textRenderer.draw(toDraw, /*-mc.textRenderer.getStringWidth(toDraw)*/5/*(size / (textScale * 1))*/, 0, 0xFFFFFF, false, model,
					immediate, false, 0x000000, light);
			matrices.pop();
			RenderSystem.disableBlend();
			RenderSystem.enableDepthTest();
			RenderSystem.depthMask(true);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	private static int getColor(Entity entity)
	{
		int r = 0;
		int g = 255;
		int b = 0;
		if(entity instanceof ItemEntity) {
			r = 128;
			g = 0;
			b = 128;
		}
		return 0xff000000 | r << 16 | g << 8 | b;
	}

}
	/*@Final
	@Shadow
	protected EntityRenderDispatcher renderManager;

	@Inject(method = "hasLabel(Lnet/minecraft/entity/Entity;)Z", at = @At(value = "HEAD")) private void hasLabel(Entity entity,
			CallbackInfoReturnable<Boolean> cir)
	{
		if (entity instanceof CowEntity)
			cir.setReturnValue(true);
	}

	@Inject(at = @At("HEAD"), method = "renderLabelIfPresent(Lnet/minecraft/entity/Entity;Ljava/lang/String;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", cancellable = true) private void onRenderDurability(
			Entity entity, String string, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci)
	{
		if (entity instanceof LivingEntity)
		{
			string += " " + ((LivingEntity) entity).getHealth();
		} else if (entity instanceof ItemEntity)
		{
			string += " " + ((ItemEntity) entity).getStack().getCount();
		}
		renderCustomLabel(entity, string, matrixStack, vertexConsumerProvider, i);
		ci.cancel();

	}

	protected void renderCustomLabel(Entity entity, String string, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i)
	{
		double d = this.renderManager.getSquaredDistanceToCamera(entity);
		if (d > 4096)
			return;
		boolean bl = !entity.isSneaky();
		int j = "deadmau5".equals(string) ? -10 : 0;
		float f = entity.getHeight() + 0.5F;
		matrixStack.push();
		matrixStack.translate(0.0D, (double) f, 0.0D);
		matrixStack.multiply(this.renderManager.getRotation());
		matrixStack.scale(-0.025F, -0.025F, 0.025F);
		Matrix4f matrix4f = matrixStack.peek().getModel();
		float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
		int k = (int) (g * 255.0F) << 24;
		TextRenderer textRenderer = this.getFontRenderer();
		float h = (float) (-textRenderer.getStringWidth(string) / 2);
		textRenderer.draw(string, h, (float) j, 553648127, false, matrix4f, vertexConsumerProvider, bl, k, i);
		if (bl)
			textRenderer.draw(string, h, (float) j, -1, false, matrix4f, vertexConsumerProvider, false, 0, i);
		matrixStack.pop();
	}

	@Shadow public TextRenderer getFontRenderer()
	{
		return null;
	}*/

