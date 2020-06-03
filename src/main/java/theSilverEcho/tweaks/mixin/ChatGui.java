package theSilverEcho.tweaks.mixin;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import theSilverEcho.tweaks.ColourHelper;
import theSilverEcho.tweaks.config.Config;

@Mixin(ChatScreen.class) public abstract class ChatGui
{
	@ModifyConstant(method = "render", constant = @Constant(intValue = Integer.MIN_VALUE)) private int overrideChatBackgroundColor(int original)
	{
		return ColourHelper.rgbInt();
	}

	@Shadow protected TextFieldWidget chatField;

	@Inject(method = "keyPressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;openScreen(Lnet/minecraft/client/gui/screen/Screen;)V", ordinal = 1), cancellable = true) public void keyPressed(
			int int_1, int int_2, int int_3, CallbackInfoReturnable<Boolean> ci)
	{
		String[] split = this.chatField.getText().trim().toLowerCase().split(" ");
		if (split.length == 1 && split[0].contentEquals("Config"))
		{
			Config.setHarvestSugarCane(!Config.isHarvestSugarCaneActive());
			chatField.setMessage("Harvest is set to " + (Config.isHarvestSugarCaneActive() ? "On" : "Off"));
//			ci.setReturnValue(true);
		} else if (split.length > 1 && split[0].contentEquals("Config"))
		{
			int i = Integer.parseInt(split[1]);
			Config.setHarvestSugarCaneRange(i);
			chatField.setMessage(("Set range to: " + Config.getHarvestSugarCaneRange() + ""));
//			ci.setReturnValue(true);
		}
	}

}
