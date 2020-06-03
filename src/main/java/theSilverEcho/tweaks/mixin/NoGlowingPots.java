package theSilverEcho.tweaks.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import theSilverEcho.tweaks.config.Config;

@Mixin(PotionItem.class) public abstract class NoGlowingPots
{
	@Inject(method = "hasEnchantmentGlint", at = @At("HEAD"), cancellable = true) public void mustGlow(ItemStack stack,
			CallbackInfoReturnable<Boolean> cir)
	{
		cir.setReturnValue(Config.doPotsGlow());
	}
}
