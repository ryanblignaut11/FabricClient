package theSilverEcho.tweaks.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import theSilverEcho.tweaks.cosmetic.ItemPhysics;

@Mixin(ItemEntityRenderer.class) public abstract class ItemEntityRenderMixin extends EntityRenderer<ItemEntity>
{
	protected ItemEntityRenderMixin(EntityRenderDispatcher entityRenderDispatcher)
	{
		super(entityRenderDispatcher);
	}

	@Inject(method = "render", at = @At(value = "HEAD"), cancellable = true) private void doRender(ItemEntity itemEntity, float f, float g,
			MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci)
	{

		ItemPhysics.render2(itemEntity, matrixStack, vertexConsumerProvider, i);
		ci.cancel();
		/*MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tweaks", "models/items/tech_bow_orange/bow.png"));
		if (!Tweaks.finalVertices.isEmpty())
		{
			final MinecraftClient client = MinecraftClient.getInstance();
			final Camera camera = client.gameRenderer.getCamera();
			RenderSystem.pushMatrix();
			RenderSystem.multMatrix(matrixStack.peek().getModel());

			RenderSystem.translated(-camera.getPos().x, -camera.getPos().y, -camera.getPos().z);
			//			RenderSystem.rotatef(90,0,0,1);
			//						RenderSystem.scalef(0.5F,0.5F,0.5F);

			final Tessellator tess = Tessellator.getInstance();
			final BufferBuilder buffer = tess.getBuffer();
			buffer.begin(GL11.GL_TRIANGLES, VertexFormats.POSITION_TEXTURE);

			int x = 5, y = 7, z = 4;
			for (int q = 0; q < Tweaks.finalVertices.size(); q++)
			{
				buffer.vertex(x + Tweaks.finalVertices.get(q).getX(), y + Tweaks.finalVertices.get(q).getY(), z + Tweaks.finalVertices.get(q).getZ())
				      .texture(Tweaks.finalTextures.get(q).x, 1 - Tweaks.finalTextures.get(q).y).next();
			}

			buffer.end();

			BufferRenderer.draw(buffer);

			RenderSystem.popMatrix();

		}*/
	}
}
