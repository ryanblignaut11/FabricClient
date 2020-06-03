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

public class g extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>
{
	private final ModelPart bone2;

	public g(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context)
	{
		super(context);
		bone2 = new ModelPart(64, 32, 0, 0);

		bone2.setPivot(0.0F, 24.0F, 0.0F);

		bone2.setTextureOffset(64, 32).addCuboid(-13.5F, -25.0F, -0.5F, 27.0F, 25.0F, 1.0F, false);

		bone2.setTextureOffset(64, 32).addCuboid(-13.5F, -25.0F, -0.5F, 27.0F, 25.0F, 1.0F, false);

	}

	@Override public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity,
			float limbAngle, float limbDistance, float tickDelta, float customAngle, float headYaw, float headPitch)
	{
		VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(new Identifier("tweaks", "capes/wings/mech-wings.png")));
		bone2.render(matrices, buffer, light, OverlayTexture.DEFAULT_UV);
	}
}