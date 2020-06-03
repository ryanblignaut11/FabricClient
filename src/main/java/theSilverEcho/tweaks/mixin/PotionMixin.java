package theSilverEcho.tweaks.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.PotionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import theSilverEcho.tweaks.ColourHelper;

import java.util.Collection;

@Mixin(PotionUtil.class) public abstract class PotionMixin
{
	/**
	 * @author theSilverEcho
	 * @reason potionColour
	 */
	@Overwrite public static int getColor(Collection<StatusEffectInstance> effects)
	{
		int i = 3694022;
		if (effects.isEmpty())
		{
			return ColourHelper.fullRgbInt(6000);
		} else
		{
			float f = 0.0F;
			float g = 0.0F;
			float h = 0.0F;
			int j = 0;

			for (StatusEffectInstance statusEffectInstance : effects)
			{
				//								System.out.println(statusEffectInstance);
				//				System.out.println(effects);
				if (statusEffectInstance.shouldShowParticles())
				{
					int k = statusEffectInstance.getEffectType().getColor();
//					k *= 0.3;
					int l = statusEffectInstance.getAmplifier() + 1;
					f += (float) (l * (k >> 16 & 255)) / 255.0F;
					g += (float) (l * (k >> 8 & 255)) / 255.0F;
					h += (float) (l * (k & 255)) / 255.0F;
					j += l;
				}
			}

			if (j == 0)
			{
				return 0;
			} else
			{
				f = f / (float) j * 255.0F;
				g = g / (float) j * 255.0F;
				h = h / (float) j * 255.0F;
				return (int) f << 16 | (int) g << 8 | (int) h;
			}
		}
	}
}
