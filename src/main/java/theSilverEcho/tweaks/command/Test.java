package theSilverEcho.tweaks.command;/*
package theSilverEcho.tweaks.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class Test
{
	public static void register(CommandDispatcher<ServerCommandSource> dist)
	{
		LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("build").requires(source -> source.hasPermissionLevel(0))
		                                                                    .executes(context ->
		                                                                    {
			                                                                    ClientPlayerEntity player = MinecraftClient.getInstance().player;
			                                                                    Vec3d eyes = new Vec3d(player.getX(),
					                                                                    player.getY() + player.getEyeHeight(player.getPose()),
					                                                                    player.getZ());

			                                                                    MinecraftClient.getInstance().interactionManager.interactBlock(player,
					                                                                    MinecraftClient.getInstance().world, Hand.MAIN_HAND, )

			                                                                    return 0;
		                                                                    }); dist.register(builder);
	}

}
*/
