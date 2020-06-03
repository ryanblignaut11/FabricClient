package theSilverEcho.tweaks.registration;

import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;

public class KeyBinds
{
	public static final FabricKeyBinding keyBinding = FabricKeyBinding.Builder.create(new Identifier("tweaks", "step"), InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_M, "keys").build();
	public static final FabricKeyBinding reload = FabricKeyBinding.Builder.create(new Identifier("tweaks", "spook"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F4,
			"keys").build();
	public static final FabricKeyBinding glintScreenKey = FabricKeyBinding.Builder.create(new Identifier("tweaks", "open_glint_screen"), InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_F10, "keys").build();
	public static final FabricKeyBinding swapArmour = FabricKeyBinding.Builder.create(new Identifier("tweaks", "q"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H,
			"keys").build();
	public static final FabricKeyBinding perspective = FabricKeyBinding.Builder.create(new Identifier("tweaks", "change_perspective"), InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_V, "keys").build();

	public static void registerKeys()
	{
		for (Field field : KeyBinds.class.getFields())
			if (field.getGenericType() instanceof FabricKeyBinding)
				KeyBindingRegistry.INSTANCE.register((FabricKeyBinding) field.getGenericType());
	}
}
