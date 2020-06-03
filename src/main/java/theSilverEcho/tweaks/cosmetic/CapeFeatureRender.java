package theSilverEcho.tweaks.cosmetic;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;

public class CapeFeatureRender extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>
{
	private final ModelPart cape, capeHalf, capebit1, capebit2;

	private final ArrayList<Identifier> capeList = new ArrayList<>();
	private int tickCount = 0, capeCounter = 0;

	public CapeFeatureRender(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context)
	{
		super(context);
		this.cape = new ModelPart(22, 23, 0, 0);
		this.cape.addCuboid(-5.0F, 0.0F, -1.0F, 10.0F, 8.0F, 1.0F);
		capeHalf = new ModelPart(22, 23, 0, 8);
		capeHalf.addCuboid(-5.0F, 8.0F, -1.0F, 10.0F, 8.0F, 1.0F);

		capebit1 = (new ModelPart(22, 23, 0, 17).addCuboid(3, -0.5F, -1, 2, 1, 5));
		capebit2 = (new ModelPart(22, 23, 0, 17).addCuboid(-5, -0.5F, -1, 2, 1, 5));

		cape.addChild(capeHalf);

		for (int i = 0; i < 9; i++)
		{
			capeList.add(new Identifier("tweaks", "textures/capes/128/cloak_128-" + i + ".png"));
		}
	}

	@Override public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity,
			float limbAngle, float limbDistance, float tickDelta, float customAngle, float headYaw, float headPitch)
	{
		renderCape(entity, light, matrices, vertexConsumers, tickDelta);
	}

	private void renderCape(AbstractClientPlayerEntity abstractClientPlayerEntity, int light, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, float tickDelta)
	{
		matrixStack.push();
		matrixStack.translate(0.0D, 0.0D, 0.125D);
		double d = MathHelper.lerp(tickDelta, abstractClientPlayerEntity.field_7524, abstractClientPlayerEntity.field_7500) - MathHelper.lerp(
				tickDelta, abstractClientPlayerEntity.prevX, abstractClientPlayerEntity.getX());
		double e = MathHelper.lerp(tickDelta, abstractClientPlayerEntity.field_7502, abstractClientPlayerEntity.field_7521) - MathHelper.lerp(
				tickDelta, abstractClientPlayerEntity.prevY, abstractClientPlayerEntity.getY());
		double m = MathHelper.lerp(tickDelta, abstractClientPlayerEntity.field_7522, abstractClientPlayerEntity.field_7499) - MathHelper.lerp(
				tickDelta, abstractClientPlayerEntity.prevZ, abstractClientPlayerEntity.getZ());
		float n = abstractClientPlayerEntity.prevBodyYaw + (abstractClientPlayerEntity.bodyYaw - abstractClientPlayerEntity.prevBodyYaw);
		double o = MathHelper.sin(n * 0.017453292F);
		double p = -MathHelper.cos(n * 0.017453292F);
		float q = (float) e * 10.0F;
		q = MathHelper.clamp(q, -6.0F, 32.0F);
		float r = (float) (d * o + m * p) * 100.0F;
		r = MathHelper.clamp(r, 0.0F, 150.0F);
		float s = (float) (d * p - m * o) * 100.0F;
		s = MathHelper.clamp(s, -20.0F, 20.0F);
		if (r < 0.0F)
		{
			r = 0.0F;
		}

		float t = MathHelper.lerp(tickDelta, abstractClientPlayerEntity.field_7505, abstractClientPlayerEntity.field_7483);
		q += MathHelper.sin(MathHelper
				.lerp(tickDelta, abstractClientPlayerEntity.prevHorizontalSpeed, abstractClientPlayerEntity.horizontalSpeed) * 6.0F) * 32.0F * t;
		if (abstractClientPlayerEntity.isInSneakingPose())
		{
			q += 25.0F;
		}
		float f11 = System.currentTimeMillis() % 1000L / 1000.0f * 3.1415927f * 2.0F;
		//		cape.pitch = (float) Math.toRadians(20.0) + (float) Math.sin(f11) * 0.4F;
		//		cape.roll/*yaw*/ = s /36/ 2.0F;
		//		cape.roll = 180.0F - s /36/ 2.0F;

		//		matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(6.0F + r / 2.0F + q));
		//		matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(s / 2.0F));
		//		capeHalf.pitch = 180 - s / 2;
		//		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - s / 2.0F));
		VertexConsumer vertexConsumer;
		if (tickCount >= 10)
		{
			if (capeCounter < capeList.size() - 1)
				capeCounter++;
			else
				capeCounter = 0;
			tickCount = 0;
		}
		vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(capeList.get(capeCounter)));

		cape.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
		matrixStack.pop();
		matrixStack.push();
		capebit1.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
		capebit2.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
		matrixStack.pop();

		tickCount++;

	}
}
