package theSilverEcho.tweaks.mixin;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import theSilverEcho.tweaks.ColourHelper;
import theSilverEcho.tweaks.Tweaks;
import theSilverEcho.tweaks.config.Config;
import theSilverEcho.tweaks.gui.CustomCrossHair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.String.format;

// renderScoreboardSidebar in InGameHud
@Mixin(value = InGameHud.class) public abstract class Gui extends DrawableHelper
{


	@Inject(method = "renderScoreboardSidebar", at = @At(value = "HEAD"), cancellable = true) private void renderScoreboardSidebar(
			ScoreboardObjective scoreboardObjective, CallbackInfo ci)
	{
		Tweaks.customSidebar.drawSidebar(scoreboardObjective, MinecraftClient.getInstance().getWindow().getScaledWidth(),
				MinecraftClient.getInstance().getWindow().getScaledHeight());
		ci.cancel();
	}


	@Inject(method = "renderCrosshair",at = @At(value = "HEAD"),cancellable = true)private void customCrossHair(CallbackInfo ci)
	{
		ci.cancel();
		CustomCrossHair.render();
	}

	@Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;hudHidden:Z", ordinal = 2)) private void onDraw(
			float esp, CallbackInfo ci)
	{
		if (!MinecraftClient.getInstance().options.debugEnabled)
		{
			// Draw Game info on every GameHud render
			if (Config.isEnabled())
			{
				renderRight(callRight(), MinecraftClient.getInstance());
			}
		}
	}

	private List<String> callRight()
	{

		ArrayList<String> strings = Lists.newArrayList(getTime(), getMemory(), getFPS(), getMovementSpeed());
		strings.addAll(horseDev());
		return strings;

	}

	private static void renderRight(List<String> list, MinecraftClient minecraft)
	{
		for (int i = 0; i < list.size(); ++i)
		{
			String s = list.get(i);
			if (!Strings.isNullOrEmpty(s))
			{
				int j = 9;
				int k = minecraft.textRenderer.getStringWidth(s);
				int l = minecraft.getWindow().getScaledWidth() - 2 - k;
				int i1 = 2 + j * i;
				try
				{
					fill(l - 1, i1 - 1, l + k + 1, i1 + j - 1, ColourHelper.rgbInt());
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				MinecraftClient.getInstance().textRenderer.drawWithShadow(s, (float) l, (float) i1, 14737632);
			}
		}
	}

	private static String getTime()
	{
		Calendar cal = Calendar.getInstance();
		return format("%02d/%02d/%02d @ %02d:%02d:%02d", cal.get(Calendar.DATE), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR),
				cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
	}

	private static String getMovementSpeed()
	{
		PlayerEntity player = MinecraftClient.getInstance().player;
		if (player == null)
		{
			return "";
		} else
		{

			Vec3d oldPos = new Vec3d(player.prevX, 0, player.prevZ);
			Vec3d newPos = new Vec3d(player.getX(), 0, player.getZ());

			double perTick = newPos.distanceTo(oldPos);
			return format("GlintColour: %.1f", perTick * 20.0D);

		}
	}

	private static String getMemory()
	{
		long max = Runtime.getRuntime().maxMemory();
		long total = Runtime.getRuntime().totalMemory();
		long free = Runtime.getRuntime().freeMemory();
		long used = total - free;
		return format("%2d%% (%03d/%03dMB)", used * 100L / max, (used) / 1024L / 1024L, (max) / 1024L / 1024L);
	}

	private static String getFPS()
	{
		try
		{

			String[] debug = MinecraftClient.getInstance().fpsDebugString.split(" ");
			int fps = Integer.parseInt(debug[0]);
			String stringfps = (fps < 10 ? "" : "") + (fps < 100 ? "" : "") + fps;
			return debug[4].equals("vsync") ? stringfps + " fps" : stringfps + "/" + debug[3] + " fps";
		} catch (Exception e)
		{
			return "";
		}
	}

	private List<String> horseDev()
	{
		List<String> list = new ArrayList<>();
		HitResult hitResult = MinecraftClient.getInstance().crosshairTarget;
		if (hitResult instanceof EntityHitResult)
		{
			EntityHitResult entityRayTraceResult = (EntityHitResult) hitResult;
			if (entityRayTraceResult.getEntity() instanceof HorseBaseEntity)
			{
				HorseBaseEntity entity = (HorseBaseEntity) entityRayTraceResult.getEntity();

				double x = entity.getJumpStrength();

				//				jump height bad -> 2.75 average -> 2.9 great-> 5.0
				//                  0.1125 0.225 0.3375   4.8375 , 9.675 , 14.5125‬
				//				System.out.println(entity.getHorseJumpStrength());
				double jumpHeight = -0.1817584952 * x * x * x + 3.689713992 * x * x + 2.128599134 * x - 0.343930367;
				list.add(formatNums(jumpHeight, 2.175, /*2.9,*/ 5.0, 5.29, 1) + "m high");
				list.add(formatNums(entity.getHealth() / 2, 10, 15, 20, 0));
				double speed = entity.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).getValue() * 42;
				list.add(formatNums(speed, 6, /*9.675,*/ 13.2, 14.175, 1) + " B/S");
			}
		}
		return list;
	}

	private String formatNums(double value, double bad, double good, double best, int numsOfDigits)
	{
		return (value >= best ?
				net.minecraft.util.Formatting.DARK_PURPLE :
				value >= good ?
						net.minecraft.util.Formatting.GREEN :
						value <= good && value >= bad ?
								net.minecraft.util.Formatting.GOLD :
								value < bad ? net.minecraft.util.Formatting.RED : "") + String.format("%." + numsOfDigits + "f", value);
	}

}
