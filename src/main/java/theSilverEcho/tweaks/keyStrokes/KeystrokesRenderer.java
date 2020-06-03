package theSilverEcho.tweaks.keyStrokes;

import net.minecraft.client.MinecraftClient;

import java.util.Arrays;

public class KeystrokesRenderer
{

	private final Key[] movementKeys = new Key[4];

	public KeystrokesRenderer()
	{
		movementKeys[0] = new Key(MinecraftClient.getInstance().options.keyForward, 26, 2);
		movementKeys[1] = new Key(MinecraftClient.getInstance().options.keyBack, 26, 26);
		movementKeys[2] = new Key(MinecraftClient.getInstance().options.keyLeft, 2, 26);
		movementKeys[3] = new Key(MinecraftClient.getInstance().options.keyRight, 50, 26);
	}

	public void renderKeystrokes()
	{
		drawMovementKeys(50, 50);
	}

	private void drawMovementKeys(int x, int y)
	{
			Arrays.stream(movementKeys).forEach(key -> key.renderKey(x, y));
	}

}
