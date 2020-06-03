package theSilverEcho.tweaks.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import theSilverEcho.tweaks.Tweaks;

@Mixin(GameRenderer.class) public abstract class ChunkMixin
{
	@Inject(method = "renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", args = {
			"ldc=hand" })) private void onRenderCenterLast(float partialTicks, final long nanoTime, MatrixStack stack, CallbackInfo info)
	{
		MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("tweaks", "models/items/tech_bow_orange/bow.png"));
		if (!Tweaks.finalVertices.isEmpty())
		{
			final MinecraftClient client = MinecraftClient.getInstance();
			final Camera camera = client.gameRenderer.getCamera();
			RenderSystem.pushMatrix();
			RenderSystem.multMatrix(stack.peek().getModel());

			RenderSystem.translated(-camera.getPos().x, -camera.getPos().y, -camera.getPos().z);
//			RenderSystem.rotatef(90,0,0,1);
			//						RenderSystem.scalef(0.5F,0.5F,0.5F);

			final Tessellator tess = Tessellator.getInstance();
			final BufferBuilder buffer = tess.getBuffer();
			buffer.begin(GL11.GL_TRIANGLES, VertexFormats.POSITION_TEXTURE);

			int x = 5, y = 7, z = 4;
			for (int i = 0; i < Tweaks.finalVertices.size(); i++)
			{
				buffer.vertex(x + Tweaks.finalVertices.get(i).getX(), y + Tweaks.finalVertices.get(i).getY(), z + Tweaks.finalVertices.get(i).getZ())
				      .texture(Tweaks.finalTextures.get(i).x, 1 - Tweaks.finalTextures.get(i).y).next();
			}

			buffer.end();

			BufferRenderer.draw(buffer);

			RenderSystem.popMatrix();

		}
	}

}
