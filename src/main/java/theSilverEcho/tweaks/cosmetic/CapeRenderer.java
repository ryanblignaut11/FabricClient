package theSilverEcho.tweaks.cosmetic;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

public class CapeRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>
{
	private boolean wow = false;

	public CapeRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context)
	{
		super(context);
	}

	public void rendererCape(AbstractClientPlayerEntity abstractClientPlayerEntity, int light, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, float tickDelta)
	{
		GL11.glTexParameteri(3553, 10242, 10496);
		GL11.glTexParameteri(3553, 10243, 10496);
		GlStateManager.enableTexture();
		RenderSystem.pushMatrix();
		double d1 = abstractClientPlayerEntity.prevX;
		double d2 = abstractClientPlayerEntity.prevY;
		double d3 = abstractClientPlayerEntity.prevZ;
		float f1 = abstractClientPlayerEntity.prevBodyYaw;
		double d4 = aF.a((f1 * 3.1415927F / 180.0F));
		double d5 = -aF.b((f1 * 3.1415927F / 180.0F));
		float f2 = (float) d2 * 10.0F;
		f2 = aF.a(f2, -6.0F, 32.0F);
		float f3 = (float) (d1 * d4 + d3 * d5) * 100.0F;
		float f4 = (float) (d1 * d5 - d3 * d4) * 100.0F;
		if (f3 < 0.0F)
			f3 = 0.0F;
		if (f3 > 165.0F)
			f3 = 165.0F;
		boolean bool = true;
		boolean bool1 = true;//(paramCosmeticInfo != null && cloakRenderType.equals(BetterframesConfig.CloakRenderType.DYNAMIC_CURVE)) ? true : false;
		float f5 = bool1 ? 16.0F : 32.0F;
		float f6 = abstractClientPlayerEntity.bodyYaw;
		f2 += (float) aF.a(abstractClientPlayerEntity.distanceTraveled * 6.0D) * f5 * f6;
		if (abstractClientPlayerEntity.isSneaking())
		{
			f3 += 5.0F;
			RenderSystem.translatef(0.0F, 0.1F, 0.0F);
			f2 += 25.0F;
			RenderSystem.translatef(0.0F, 0.05F, -0.0178F);
		}
		if (/*paramCosmeticInfo != null*/true)
		{
			float f7, f8;
			DynamicCapeFeatureRender dynamicCloak;
			RenderSystem.translatef(0.0625F, 0.0625F, 0.0625F);
			RenderSystem.translatef(0.0F, 0.0F, 2.0F);
			RenderSystem.rotatef(6.0F + Math.min(f3 / 2.0F + f2, 90.0F), 1.0F, 0.0F, 0.0F);
			RenderSystem.rotatef(f4 / 2.0F, 0.0F, 0.0F, 1.0F);
			RenderSystem.rotatef(-f4 / 2.0F, 0.0F, 1.0F, 0.0F);
			RenderSystem.rotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			RenderSystem.rotatef(180.0F, 1.0F, 0.0F, 0.0F);
			f7 = Math.max(Math.min(0.0F, f2), -3.0F);
			f8 = Math.min(f3 + f2, 90.0F);
			if (!wow)
			{
				dynamicCloak = new DynamicCapeFeatureRender();
				dynamicCloak.updateCloak(0.0F, 0.0F, true);
				wow = true;
			} else
			{
				dynamicCloak = new DynamicCapeFeatureRender();
				dynamicCloak.updateCloak(f8, f7, false);
			}
			dynamicCloak.renderDynamicCloak();
		}
		RenderSystem.pushMatrix();
		if (bool)
		{
			GlStateManager.enableTexture();
			if (abstractClientPlayerEntity.isSneaking())
			{
				RenderSystem.scalef(0.0F, 0.2F, 0.0F);
				RenderSystem.rotatef(10F, 1.0F, 0.0F, 0.0F);
			}
			RenderSystem.rotatef(-f4 / 2.0F, 0.0F, 1.0F, 0.0F);
			//			this.bipedCloakShoulders.a(0.0625F, paramCosmeticRenderData.isIngame());
			RenderSystem.popMatrix();
		}
		RenderSystem.popMatrix();
	}

	@Override public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity,
			float limbAngle, float limbDistance, float tickDelta, float customAngle, float headYaw, float headPitch)
	{
		rendererCape(entity, light, matrices, vertexConsumers, tickDelta);
	}

	//	private void setupSecondPass(CosmeticInfo paramCosmeticInfo)
	//	{
	//		String str = paramCosmeticInfo.getCosmeticId() + "-" + paramCosmeticInfo.getCosmeticType().name() + "-1";
	//		GlStateManager.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
	//		MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tweaks", ""));
	//		BadlionClient.glStateManager.a(514, 514);
	//		BadlionClient.glStateManager.t();
	//	}
	//	private void clearSecondPass()
	//	{
	//		GlStateManager.pushMatrix();
	//		GlStateManager.bindFramebuffer(770, 771);
	//	}

}
