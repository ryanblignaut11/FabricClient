package theSilverEcho.tweaks.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import theSilverEcho.tweaks.ColourHelper;

import java.text.DecimalFormat;

@Mixin(ItemRenderer.class) public abstract class MyMixin
{
	@Inject(at = @At("HEAD"), method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V") private void onRenderDurability(
			TextRenderer fr, ItemStack stack, int xPosition, int yPosition, String s, CallbackInfo info)
	{

		if (!stack.isEmpty())
		{
			if (stack.isDamaged())
			{
				float damage = (float) stack.getDamage();
				float maxDamage = (float) stack.getMaxDamage();
				String string = format(maxDamage - damage);
				int stringWidth = fr.getStringWidth(string);

				int x = ((xPosition + 8) * 2 + 1 + stringWidth / 2 - stringWidth);
				int y = (yPosition * 2) + 18;

				// Color
				int color = ColourHelper.rgb().getRGB();
				// Draw
				MatrixStack matrixStack = new MatrixStack();
				matrixStack.translate(0.0D, 0.0D, 500.0F);
				matrixStack.scale(0.5F, 0.5F, 0.5F);

				VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
				fr.draw(string, x, y, color, true, matrixStack.peek().getModel(), immediate, false, 0, 15728880);
				immediate.draw();
			}
		}
	}

	private static String format(float number)
	{
		DecimalFormat decimalFormat = new DecimalFormat("0.#");

		if (number >= 1000000000)
			return decimalFormat.format(number / 1000000000) + "b";
		if (number >= 1000000)
			return decimalFormat.format(number / 1000000) + "m";
		if (number >= 1000)
			return decimalFormat.format(number / 1000) + "k";

		return Float.toString(number).replaceAll("\\.?0*$", "");
	}

}
