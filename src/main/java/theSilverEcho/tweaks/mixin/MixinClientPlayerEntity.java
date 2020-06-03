package theSilverEcho.tweaks.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import theSilverEcho.tweaks.config.Config;
import theSilverEcho.tweaks.notification.Notification;
import theSilverEcho.tweaks.notification.NotificationManager;

@Mixin(ClientPlayerEntity.class) public abstract class MixinClientPlayerEntity
{
	@Shadow
	@Final
	protected MinecraftClient client;

	@Shadow public abstract void addChatMessage(Text message, boolean bl);

	@Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true) public void sendChatMessage(String message, CallbackInfo ci)
	{
		String[] split = message.toLowerCase().split(" ");
		if (split.length == 1 && split[0].contentEquals("Config"))
		{
			Config.setHarvestSugarCane(!Config.isHarvestSugarCaneActive());
			ci.cancel();
		} else if (split.length > 1 && split[0].contentEquals("Config"))
		{
			try
			{
				int i = Integer.parseInt(split[1]);
				Config.setHarvestSugarCaneRange(i);
				NotificationManager.show(new Notification("wow", "range" + Config.getHarvestSugarCaneRange(), 1));
			} catch (Exception e)
			{
				addChatMessage(new LiteralText(split[1] + " not an int"), true);
			}
			ci.cancel();
		}
	}




}
