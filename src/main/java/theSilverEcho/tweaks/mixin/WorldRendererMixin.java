package theSilverEcho.tweaks.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.shape.VoxelShape;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import theSilverEcho.tweaks.gui.GuiHelper;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;

@Mixin(WorldRenderer.class) public abstract class WorldRendererMixin
{
	//	@Shadow public abstract void drawBox(BufferBuilder buffer, double x1, double y1, double z1, double x2, double y2, double z2, float red,
	//			float green, float blue, float alpha);
	//
	//	@Shadow public abstract void drawBox(MatrixStack matrixStack, VertexConsumer vertexConsumer, double d, double e, double f, double g, double h,
	//			double i, float j, float k, float l, float m);

	@Inject(method = "drawShapeOutline", at = @At("INVOKE"), cancellable = true) private static void drawBlockOutline(MatrixStack matrixStack,
			VertexConsumer vertexConsumer, VoxelShape voxelShape, double d, double e, double f, float g, float h, float i, float j, CallbackInfo ci)
	{
		ci.cancel();

		RenderSystem.pushMatrix();
		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();
		RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE,
				GlStateManager.DstFactor.ZERO);
		RenderSystem.depthMask(false);
		RenderSystem.color4f(0, 1, 1, 0.2f);
		RenderSystem.defaultAlphaFunc();
		RenderSystem.enableAlphaTest();
		RenderSystem.disableCull();
		RenderSystem.disableTexture();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		bufferBuilder.begin(GL_TRIANGLE_STRIP, VertexFormats.POSITION);
		voxelShape.forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> GuiHelper
				.drawBox1(matrixStack, bufferBuilder, minX + d - 0.005, minY + e - 0.005, minZ + f - 0.005, maxX + d + 0.005, maxY + e + 0.005,
						maxZ + f + 0.005, 1, 1, 1, 0.2F));
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);

		RenderSystem.enableCull();
		RenderSystem.disableAlphaTest();
		RenderSystem.enableAlphaTest();
		RenderSystem.disableBlend();
		RenderSystem.depthMask(true);
		RenderSystem.popMatrix();

		//		Matrix4f matrix4f = matrixStack.peek().getModel();
		//
		//		GL11.glPushMatrix();
		//		GL11.glLineWidth(2);
		//		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		//		RenderSystem.enableDepthTest();
		//		RenderSystem.enableBlend();
		//		RenderSystem.defaultBlendFunc();
		//		RenderSystem.disableTexture();
		//		RenderSystem.enableCull();
		//		bufferBuilder.begin(GL11.GL_TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

		//		voxelShape.forEachEdge((minX, minY, minZ, maxX, maxY, maxZ) ->
		//		{
		//						bufferBuilder.vertex(matrix4f, (float) (minX + d), (float) (minY + e), (float) (minZ + f)).color(ColourHelper.rgb().getRed(),
		//								ColourHelper.rgb().getGreen(), ColourHelper.rgb().getBlue(), 120).next();
		//						bufferBuilder.vertex(matrix4f, (float) (maxX + d), (float) (maxY + e), (float) (maxZ + f)).color(ColourHelper.rgb().getRed(),
		//								ColourHelper.rgb().getGreen(), ColourHelper.rgb().getBlue(), 120).next();
		//		});
		//
		////			System.out.println(String.format("minx->%s miny->%s minz->%s", (float) (minX + d), (float) (minY + e), (float) (minZ + f)));
		////			System.out.println(String.format("maxx->%s maxy->%s maxz->%s", (float) (maxX + d), (float) (maxY + e), (float) (maxZ + f)));
		//
		//			bufferBuilder.vertex(matrix4f, (float) (minX + d), (float) (minY + e), (float) (minZ + f)).color(ColourHelper.rgb().getRed(),
		//					ColourHelper.rgb().getGreen(), ColourHelper.rgb().getBlue(), 120).next();
		//			bufferBuilder.vertex(matrix4f, (float) (maxX + d), (float) (maxY + e), (float) (maxZ + f)).color(ColourHelper.rgb().getRed(),
		//					ColourHelper.rgb().getGreen(), ColourHelper.rgb().getBlue(), 120).next();
		//		});

		//		voxelShape.forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> GuiHelper
		//				.drawBox1(matrixStack, bufferBuilder, minX + d - 0.005, minY + e - 0.005, minZ + f - 0.005, maxX + d + 0.005, maxY + e + 0.005,
		//						maxZ + f + 0.005, 1, 1, 1, 0.2F));

		//		bufferBuilder.vertex( Rotation3.identity().getMatrix(), (float) x1, (float) y1, 0.0F).color(g, h, k, f).next();
		//		bufferBuilder.vertex(Rotation3.identity().getMatrix(), (float) x2, (float) y2, 0.0F).color(g, h, k, f).next();

		//		bufferBuilder.end();
		//		BufferRenderer.draw(bufferBuilder);
		//		RenderSystem.disableDepthTest();
		//		RenderSystem.disableBlend();
		//		RenderSystem.enableTexture();
		//		RenderSystem.disableCull();
		//
		//		GL11.glPopMatrix();

	}

}
