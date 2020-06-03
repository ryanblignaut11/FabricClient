package theSilverEcho.tweaks.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class) public abstract class RenderItem
{

	@Shadow public abstract void renderItem(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model);

	@Shadow public float zOffset;

	@Inject(method = "renderGuiItemModel", at = @At("HEAD"), cancellable = true) public void renderGuiItem(ItemStack stack, int x, int y,
			BakedModel model, CallbackInfo ci)
	{
		if (stack.getItem() instanceof PotionItem )
		{
			ci.cancel();
			RenderSystem.pushMatrix();

			//			MinecraftClient.getInstance().getTextureManager().bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
//			MinecraftClient.getInstance().getTextureManager().getTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX).setFilter(false, false);
			RenderSystem.enableRescaleNormal();
			RenderSystem.enableAlphaTest();
			RenderSystem.defaultAlphaFunc();
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
			RenderSystem.color4f(1.0F, 0.0F, 1.0F, 1.0F);
			RenderSystem.translatef((float) x, (float) y, 100.0F + this.zOffset);
			RenderSystem.translatef(8.0F, 8.0F, 0.0F);
			RenderSystem.scalef(1.0F, -1.0F, 1.0F);
			RenderSystem.scalef(16.0F, 16.0F, 16.0F);
			RenderSystem.enableDepthTest();
			MatrixStack matrixStack = new MatrixStack();
			VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
			boolean bl = !model.isSideLit();
			if (bl)
				DiffuseLighting.disableGuiDepthLighting();



			this.renderItem(stack, ModelTransformation.Mode.GUI, false, matrixStack, immediate, 15728880, OverlayTexture.DEFAULT_UV, model);
			immediate.draw();
//			RenderSystem.enableDepthTest();
			if (bl)
			{
				DiffuseLighting.enableGuiDepthLighting();
			}

			RenderSystem.disableAlphaTest();
			RenderSystem.disableRescaleNormal();
			RenderSystem.popMatrix();

		}
	}
}
