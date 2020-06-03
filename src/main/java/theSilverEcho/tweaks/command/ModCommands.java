package theSilverEcho.tweaks.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;

public class ModCommands
{
	public static void Register(CommandDispatcher<ServerCommandSource> dispatcher)
	{
		LiteralCommandNode node = dispatcher.register(LiteralArgumentBuilder.literal("Tweaks"));
//		SugarCaneHarvest.register(dispatcher);
	}
}
