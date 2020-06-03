package theSilverEcho.tweaks.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Config
{
	private static final File configFile;
	private static boolean enabled;
	private static int num;
	private static boolean harvestSugarCane;
	private static boolean goToItem;
	private static int harvestSugarCaneRange;
	private static boolean tracers;
	@IntConfigValue private static int glintRed, glintGreen, glintBlue;
	@IntConfigValue private static int sidebarRed, sidebarGreen, sidebarBlue, sidebarAlpha;
	@IntConfigValue(minValue = -500, maxValue = 200, defaultValue = 0) private static int sidebarXOffset, sidebarYOffset;
	@FloatConfigValue(minValue = 0.5F, maxValue = 3F, defaultValue = 0.8F) private static float sidebarSize;
	@BooleanConfigValue private static boolean sidebarNumbers, sidebarEnabled;
	@BooleanConfigValue private static boolean wingChroma;
	@IntConfigValue private static int wingRed, wingBlue, wingGreen;
	@BooleanConfigValue private static boolean potsGlow;

	@BooleanConfigValue private static boolean brightnessEnabled;
	@FloatConfigValue private static float previousBrightness;

	static
	{
		configFile = new File(MinecraftClient.getInstance().runDirectory, "Config" + File.separator + "Tweaks.json");
	}

	public static void loadConfig()
	{
		try
		{
			configFile.getParentFile().mkdirs();
			String content = configFile.exists() ? FileUtils.readFileToString(configFile, "utf-8") : "{}";
			JsonElement jsonElement = (new JsonParser().parse(content));
			JsonObject jsonObject = jsonElement.getAsJsonObject();

			for (Field declaredField : Config.class.getDeclaredFields())
			{

				for (Annotation declaredAnnotation : declaredField.getDeclaredAnnotations())
				{
					if (declaredAnnotation instanceof IntConfigValue)
					{
						IntConfigValue value = (IntConfigValue) declaredAnnotation;
						declaredField.set(declaredField, jsonObject.has(declaredField.getName()) && jsonObject.get(declaredField.getName())
						                                                                                      .getAsInt() < value
								.maxValue() && jsonObject.get(declaredField.getName()).getAsInt() > value.minValue() ?
								jsonObject.get(declaredField.getName()).getAsInt() :
								value.defaultValue());
					} else if (declaredAnnotation instanceof FloatConfigValue)
					{
						FloatConfigValue value = (FloatConfigValue) declaredAnnotation;
						declaredField.set(declaredField, jsonObject.has(declaredField.getName()) && jsonObject.get(declaredField.getName())
						                                                                                      .getAsFloat() < value
								.maxValue() && jsonObject.get(declaredField.getName()).getAsFloat() > value.minValue() ?
								jsonObject.get(declaredField.getName()).getAsFloat() :
								value.defaultValue());
					} else if (declaredAnnotation instanceof BooleanConfigValue)
					{
						BooleanConfigValue value = (BooleanConfigValue) declaredAnnotation;
						declaredField.set(declaredField,
								jsonObject.has(declaredField.getName()) ? jsonObject.has(declaredField.getName()) : value.defaultValue());
					}
				}

			}
		} catch (IllegalAccessException | IOException e)
		{
			e.printStackTrace();
		}
		updateConfig();

	}

	public static void updateConfig()
	{
		JsonObject jsonObject = new JsonObject();
		for (Field declaredField : Config.class.getDeclaredFields())
		{

			try
			{
				for (Annotation declaredAnnotation : declaredField.getDeclaredAnnotations())
				{
					if (declaredAnnotation instanceof IntConfigValue)
						jsonObject.addProperty(declaredField.getName(), declaredField.getInt(declaredField.getName()));
					else if (declaredAnnotation instanceof FloatConfigValue)
					{

						if (declaredField.getFloat(declaredField.getName()) > ((FloatConfigValue) declaredAnnotation).maxValue())
							jsonObject.addProperty(declaredField.getName(), ((FloatConfigValue) declaredAnnotation).maxValue());
						else if (declaredField.getFloat(declaredField.getName()) < ((FloatConfigValue) declaredAnnotation).minValue())
						{
							jsonObject.addProperty(declaredField.getName(), ((FloatConfigValue) declaredAnnotation).minValue());

						} else
							jsonObject.addProperty(declaredField.getName(), declaredField.getFloat(declaredField.getName()));
					} else if (declaredAnnotation instanceof BooleanConfigValue)
						jsonObject.addProperty(declaredField.getName(), declaredField.getBoolean(declaredField.getName()));

				}

			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}

		if (configFile.exists())
		{
			configFile.delete();
		}

		PrintWriter writer;
		try
		{
			writer = new PrintWriter(configFile);
			writer.print(objectToString(jsonObject));
			writer.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	private static String objectToString(JsonObject object)
	{
		try
		{
			StringWriter stringWriter = new StringWriter();
			JsonWriter jsonWriter = new JsonWriter(stringWriter);
			jsonWriter.setLenient(true);
			jsonWriter.setIndent("\t");
			Streams.write(object, jsonWriter);
			return stringWriter.toString();
		} catch (IOException var3)
		{
			throw new AssertionError(var3);
		}
	}

	public static boolean isEnabled()
	{
		return enabled;
	}

	public static void setEnabled(boolean enabled)
	{
		Config.enabled = enabled;
		updateConfig();
	}

	public static int getNum()
	{
		return num;
	}

	public static void setNum(int num)
	{
		Config.num = num;
		updateConfig();
	}

	public static boolean isHarvestSugarCane()
	{
		return harvestSugarCane;
	}

	public static void setHarvestSugarCane(boolean harvestSugarCane)
	{
		Config.harvestSugarCane = harvestSugarCane;
		updateConfig();
	}

	public static boolean isGoToItem()
	{
		return goToItem;
	}

	public static void setGoToItem(boolean goToItem)
	{
		Config.goToItem = goToItem;
		updateConfig();
	}

	public static int getHarvestSugarCaneRange()
	{
		return harvestSugarCaneRange;
	}

	public static void setHarvestSugarCaneRange(int harvestSugarCaneRange)
	{
		Config.harvestSugarCaneRange = harvestSugarCaneRange;
		updateConfig();
	}

	public static boolean isTracers()
	{
		return tracers;
	}

	public static void setTracers(boolean tracers)
	{
		Config.tracers = tracers;
		updateConfig();
	}

	public static int getGlintRed()
	{
		return glintRed;
	}

	public static void setGlintRed(int glintRed)
	{
		Config.glintRed = glintRed;
		updateConfig();
	}

	public static int getGlintGreen()
	{
		return glintGreen;
	}

	public static void setGlintGreen(int glintGreen)
	{
		Config.glintGreen = glintGreen;
		updateConfig();
	}

	public static int getGlintBlue()
	{
		return glintBlue;
	}

	public static void setGlintBlue(int glintBlue)
	{
		Config.glintBlue = glintBlue;
		updateConfig();
	}

	public static int getSidebarRed()
	{
		return sidebarRed;
	}

	public static void setSidebarRed(int sidebarRed)
	{
		Config.sidebarRed = sidebarRed;
		updateConfig();
	}

	public static int getSidebarGreen()
	{
		return sidebarGreen;
	}

	public static void setSidebarGreen(int sidebarGreen)
	{
		Config.sidebarGreen = sidebarGreen;
		updateConfig();
	}

	public static int getSidebarBlue()
	{
		return sidebarBlue;
	}

	public static void setSidebarBlue(int sidebarBlue)
	{
		Config.sidebarBlue = sidebarBlue;
		updateConfig();
	}

	public static int getSidebarAlpha()
	{
		return sidebarAlpha;
	}

	public static void setSidebarAlpha(int sidebarAlpha)
	{
		Config.sidebarAlpha = sidebarAlpha;
		updateConfig();
	}

	public static int getSidebarXOffset()
	{
		return sidebarXOffset;
	}

	public static void setSidebarXOffset(int sidebarXOffset)
	{
		Config.sidebarXOffset = sidebarXOffset;
		updateConfig();
	}

	public static int getSidebarYOffset()
	{
		return sidebarYOffset;
	}

	public static void setSidebarYOffset(int sidebarYOffset)
	{
		Config.sidebarYOffset = sidebarYOffset;
		updateConfig();
	}

	public static float getSidebarSize()
	{
		return sidebarSize;
	}

	public static void setSidebarSize(float sidebarSize)
	{
		Config.sidebarSize = sidebarSize;
		updateConfig();
	}

	public static boolean isSidebarNumbers()
	{
		return sidebarNumbers;
	}

	public static void setSidebarNumbers(boolean sidebarNumbers)
	{
		Config.sidebarNumbers = sidebarNumbers;
		updateConfig();
	}

	public static boolean isSidebarEnabled()
	{
		return sidebarEnabled;
	}

	public static void setSidebarEnabled(boolean sidebarEnabled)
	{
		Config.sidebarEnabled = sidebarEnabled;
		updateConfig();
	}

	public static boolean isWingChroma()
	{
		return wingChroma;
	}

	public static void setWingChroma(boolean wingChroma)
	{
		Config.wingChroma = wingChroma;
		updateConfig();
	}

	public static int getWingRed()
	{
		return wingRed;
	}

	public static void setWingRed(int wingRed)
	{
		Config.wingRed = wingRed;
		updateConfig();
	}

	public static int getWingBlue()
	{
		return wingBlue;
	}

	public static void setWingBlue(int wingBlue)
	{
		Config.wingBlue = wingBlue;
		updateConfig();
	}

	public static int getWingGreen()
	{
		return wingGreen;
	}

	public static void setWingGreen(int wingGreen)
	{
		Config.wingGreen = wingGreen;
		updateConfig();
	}

	public static boolean isHarvestSugarCaneActive()
	{
		return false;
	}

	public static boolean isBrightnessEnabled()
	{
		return brightnessEnabled;
	}

	public static void setBrightnessEnabled(boolean brightnessEnabled)
	{
		Config.brightnessEnabled = brightnessEnabled;
		updateConfig();
	}

	public static float getPreviousBrightness()
	{
		return previousBrightness;
	}

	public static void setPreviousBrightness(float previousBrightness)
	{
		Config.previousBrightness = previousBrightness;
		updateConfig();
	}

	public static boolean doPotsGlow()
	{
		return potsGlow;
	}

	public static void setPotsGlow(boolean potsGlow)
	{
		Config.potsGlow = potsGlow;
		updateConfig();
	}
}


