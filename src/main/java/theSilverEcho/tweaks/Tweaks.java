package theSilverEcho.tweaks;

import com.google.common.collect.Lists;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Items;
import net.minecraft.resource.Resource;
import net.minecraft.server.network.packet.PlayerActionC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import theSilverEcho.tweaks.FontRenderer.GlyphPageFontRenderer;
import theSilverEcho.tweaks.command.ModCommands;
import theSilverEcho.tweaks.config.Config;
import theSilverEcho.tweaks.config.GlintColourConfig;
import theSilverEcho.tweaks.cosmetic.Brightness;
import theSilverEcho.tweaks.cosmetic.ItemPhysics;
import theSilverEcho.tweaks.gui.CustomSidebar;
import theSilverEcho.tweaks.gui.CustomSidebarConfigScreen;
import theSilverEcho.tweaks.gui.GuiHelper;
import theSilverEcho.tweaks.keyStrokes.KeystrokesRenderer;
import theSilverEcho.tweaks.notification.Notification;
import theSilverEcho.tweaks.notification.NotificationManager;
import theSilverEcho.tweaks.registration.KeyBinds;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused") public class Tweaks implements ModInitializer
{
	public static CustomSidebar customSidebar = new CustomSidebar();
	private static final MinecraftClient minecraft = MinecraftClient.getInstance();
	private KeystrokesRenderer keystrokesRenderer;
	public static ArrayList<Vector3f> vertices = new ArrayList<>();
	public static ArrayList<Vector3f> finalVertices = new ArrayList<>();
	public static ArrayList<Vec2f> textures = new ArrayList<>();
	public static ArrayList<Vec2f> finalTextures = new ArrayList<>();
	public static GlyphPageFontRenderer renderer;

	@Override public void onInitialize()
	{




		/*------Register Keys------*/
		KeyBinds.registerKeys();
		/*------Register Config------*/
		Config.loadConfig();

		CommandRegistry.INSTANCE.register(false, ModCommands::Register);
		HudRenderCallback.EVENT.register(v ->
		{
			NotificationManager.render();
			renderer.drawString("wow test abcd ", 20, 20, -1, false);
			if (keystrokesRenderer == null)
				keystrokesRenderer = new KeystrokesRenderer();
			keystrokesRenderer.renderKeystrokes();

			int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
			int scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

			if (minecraft.currentScreen instanceof GlintColourConfig)
			{
				GuiHelper.fill(2, 1, 0, (int) width * 20 / 100/*width * 20 / 100*/, scaledHeight - 1, -1);
				GuiHelper.fill(7, 1, 0, width * 20 / 100, scaledHeight - 1, new Color(255, 255, 255, 120).getRGB());
			}

		});
		ClientTickCallback.EVENT.register(minecraftClient ->
		{

			ItemPhysics.tick = System.nanoTime();
			ClientPlayerEntity player = minecraftClient.player;
			ClientWorld world = minecraftClient.world;

			if (player == null || world == null)
				return;

			if (Config.isHarvestSugarCaneActive())
			{
				Vec3d eyes = new Vec3d(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ());
				Vec3d eyesVec = eyes.subtract(0.5, 0.5, 0.5);
				BlockPos eyesBlock = new BlockPos(eyes);

				double rangeSq = Math.pow(Config.getHarvestSugarCaneRange(), 2);

				List<BlockPos> blocks = getBlockStream(new BlockPos(eyesVec), Config.getHarvestSugarCaneRange()).filter(
						blockPos -> eyesVec.distanceTo(new Vec3d(blockPos)) <= rangeSq).collect(Collectors.toList());
				List<BlockPos> blocksToHarvest;

				blocksToHarvest = blocks.parallelStream().filter(this::isCane).sorted(
						Comparator.comparingDouble(pos -> eyesVec.squaredDistanceTo(new Vec3d(pos)))).collect(Collectors.toList());

				harvest(blocksToHarvest);

			}

			if (KeyBinds.glintScreenKey.wasPressed())
			{
				minecraft.openScreen(new GlintColourConfig());
				NotificationManager.show(new Notification("hello", "test", 10));
			}
			if (KeyBinds.perspective.wasPressed())
				Brightness.toggleBrightness();
			if (KeyBinds.reload.wasPressed())
			{
				try
				{
					Identifier tweaks = new Identifier("tweaks", "models/items/tech_bow/bow.obj");
					Resource resource = minecraft.getResourceManager().getResource(tweaks);
					InputStreamReader in = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
					BufferedReader bufferedReader = new BufferedReader(in);
					vertices.clear();
					finalVertices.clear();
					textures.clear();
					finalTextures.clear();

					bufferedReader.lines().forEach(s ->
					{
						String[] tokens = s.split(" ");
						switch (tokens[0])
						{
						case "v":
							vertices.add(
									new Vector3f(Float.parseFloat(tokens[1]), -1 + Float.parseFloat(tokens[2]), -1 + Float.parseFloat(tokens[3])));
							break;
						case "vt":
							textures.add(new Vec2f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])));
							break;
						case "vn":
							//							normals.add(
							//									new Vector3f(Float.parseFloat(tokens[1]), -1 + Float.parseFloat(tokens[2]), -1 + Float.parseFloat(tokens[3])));
							break;
						case "f":
							String[] v1 = tokens[1].split("/");
							String[] v2 = tokens[2].split("/");
							String[] v3 = tokens[3].split("/");
							finalVertices.add(vertices.get(Integer.parseInt(v1[0]) - 1));
							finalVertices.add(vertices.get(Integer.parseInt(v2[0]) - 1));
							finalVertices.add(vertices.get(Integer.parseInt(v3[0]) - 1));

							finalTextures.add(textures.get(Integer.parseInt(v1[1]) - 1));
							finalTextures.add(textures.get(Integer.parseInt(v2[1]) - 1));
							finalTextures.add(textures.get(Integer.parseInt(v3[1]) - 1));

							//							finalNormals.add(Normals.get(Integer.parseInt(v1[2]) - 1));
							//							finalNormals.add(Normals.get(Integer.parseInt(v2[2]) - 1));
							//							finalNormals.add(Normals.get(Integer.parseInt(v3[2]) - 1));
							break;
						}

					});
					bufferedReader.close();

				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			if (KeyBinds.keyBinding.wasPressed())
			{
				switch (Config.getNum())
				{
				case 0:
					Config.setNum(1);
					player.addChatMessage(new LiteralText("step changed to vanilla"), true);
					break;
				case 1:
					Config.setNum(2);
					player.addChatMessage(new LiteralText("step changed to off"), true);
					break;
				case 2:
					Config.setNum(0);
					player.addChatMessage(new LiteralText("step changed to modded"), true);
					break;
				}
			}

			if (Config.getNum() == 0 || Config.getNum() == 2 && minecraftClient.options.autoJump)
				minecraftClient.options.autoJump = false;
			else if (Config.getNum() == 1 && !minecraftClient.options.autoJump)
				minecraftClient.options.autoJump = true;

			if (player.isSneaking())
				player.stepHeight = 0.6F;
			else if (player.stepHeight < 1 && Config.getNum() == 0)
				player.stepHeight = 1.5F;
			else if (player.stepHeight >= 1 && Config.getNum() == 1)
				player.stepHeight = 0.6F;
			else if (player.stepHeight >= 1 && Config.getNum() == 2)
				player.stepHeight = 0.6F;

			if (KeyBinds.swapArmour.wasPressed())
				MinecraftClient.getInstance().openScreen(new CustomSidebarConfigScreen());

		});
		List<String> blockList = Lists.newArrayList("minecraft:large_fern", "minecraft:tall_grass", "minecraft:grass_block", "minecraft:fern",
				"minecraft:grass", "minecraft:potted_fern", "minecraft:sugar_cane");

		List<Block> blocks = Lists.newArrayList(Blocks.LARGE_FERN, Blocks.FERN, Blocks.TALL_GRASS, Blocks.GRASS_BLOCK, Blocks.GRASS,
				Blocks.POTTED_FERN, Blocks.SUGAR_CANE);
		if (MinecraftClient.getInstance().getBlockColorMap() != null)
		{
			//			registerGreenerColor(blockList,false);
			//
			ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) ->
			{
				BlockColors blockColorMap = MinecraftClient.getInstance().getBlockColorMap();
				int color = blockColorMap.getColor(state, view, pos, tintIndex);
				System.out.println(color);
				int r = color / (256 ^ 2), g = (color / 256) % 256, b = color % 256;
				System.out.println(String.format("Red: %s , Green: %s , Blue: %s", r, g, b));

				return r * (256 ^ 2);
			}, Blocks.GRASS_BLOCK);
		}

	}

	private void harvest(List<BlockPos> blocksToHarvest)
	{
		breakBlocksWithPacketSpam(blocksToHarvest);
	}

	private boolean isCane(BlockPos blockPos)
	{
		ClientWorld world = MinecraftClient.getInstance().world;
		ClientPlayerEntity player = MinecraftClient.getInstance().player;
		if (world != null && player != null)
		{
			Block block = world.getBlockState(blockPos).getBlock();
			if (block instanceof SugarCaneBlock)
				return world.getBlockState(blockPos.down()).getBlock() instanceof SugarCaneBlock && !(world.getBlockState(blockPos.down(2))
				                                                                                           .getBlock() instanceof SugarCaneBlock);
			if (block instanceof BambooBlock)
				return world.getBlockState(blockPos.down()).getBlock() instanceof BambooBlock && !(world.getBlockState(blockPos.down(2))
				                                                                                        .getBlock() instanceof BambooBlock);
			if (block instanceof KelpPlantBlock)
				return world.getBlockState(blockPos.down()).getBlock() instanceof KelpPlantBlock && !(world.getBlockState(blockPos.down(2))
				                                                                                           .getBlock() instanceof KelpPlantBlock);
			if (block instanceof CropBlock)
				return ((CropBlock) block).isMature(world.getBlockState(blockPos));

			if (block instanceof MushroomPlantBlock)
				return true;

			if (block instanceof MushroomBlock)
				return player.getMainHandStack().getItem() == Items.GOLDEN_AXE;

			if (block == Blocks.STONE)
				return player.getMainHandStack().getItem() == Items.GOLDEN_PICKAXE;

			if (block instanceof OreBlock || block == Blocks.DIAMOND_BLOCK)
				return player.getMainHandStack().getItem() == Items.DIAMOND_PICKAXE;

			if (block == Blocks.JUNGLE_LOG)
				return player.getMainHandStack().getItem() == Items.GOLDEN_AXE;

			if (block == Blocks.COCOA)
				return player.getMainHandStack().getItem() == Items.DIAMOND_HOE;

		}
		return false;
	}

	private static void breakBlocksWithPacketSpam(Iterable<BlockPos> blocks)
	{
		ClientPlayerEntity player = MinecraftClient.getInstance().player;
		if (player != null)
		{
			ClientPlayNetworkHandler networkHandler = player.networkHandler;
			for (BlockPos pos : blocks)
			{
				networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, Direction.DOWN/*side*/));
				networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, Direction.DOWN));

			}
		}
	}

	private static Stream<BlockPos> getBlockStream(BlockPos center, int range)
	{
		BlockPos min = center.add(-range, -range, -range);
		BlockPos max = center.add(range, range, range);
		return getBlocksInArea(min, max).stream();

	}

	private static ArrayList<BlockPos> getBlocksInArea(BlockPos min, BlockPos max)
	{
		ArrayList<BlockPos> blocks = new ArrayList<>();
		for (int x = min.getX(); x <= max.getX(); x++)
			for (int y = min.getY(); y <= max.getY(); y++)
				for (int z = min.getZ(); z <= max.getZ(); z++)
					blocks.add(new BlockPos(x, y, z));
		return blocks;
	}

}
