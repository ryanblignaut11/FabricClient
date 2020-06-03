package theSilverEcho.tweaks.command;//package theSilverEcho.tweaks.command;
//
//import com.mojang.brigadier.CommandDispatcher;
//import com.mojang.brigadier.arguments.IntegerArgumentType;
//import com.mojang.brigadier.builder.LiteralArgumentBuilder;
//import net.minecraft.server.command.CommandManager;
//import net.minecraft.server.command.ServerCommandSource;
//import net.minecraft.text.LiteralText;
//
//public class SugarCaneHarvest
//{
//	public static void register(CommandDispatcher<ServerCommandSource> dist)
//	{
//		LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("HSC").requires(source -> source.hasPermissionLevel(0))
//				.executes(context ->
//				{
//					Config.setHarvestSugarCane(!Config.isHarvestSugarCaneActive());
//					context.getSource()
//							.sendFeedback(new LiteralText("Harvest is set to " + (Config.isHarvestSugarCaneActive() ? "On" : "Off")), false);
//					return 0;
//				}).then(CommandManager.argument("modifier", IntegerArgumentType.integer(0)).executes(context ->
//				{
//					Config.setHarvestSugarCaneRange(IntegerArgumentType.getInteger(context, "modifier"));
//					context.getSource().sendFeedback(new LiteralText("Set range to: " + Config.getHarvestSugarCaneRange() + ""), false);
//					return 0;
//				}));
//		dist.register(builder);
//	}
//
//}
