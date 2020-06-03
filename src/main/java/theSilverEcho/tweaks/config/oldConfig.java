package theSilverEcho.tweaks.config;

/*package theSilverEcho.tweaks.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.io.FileUtils;

import java.io.*;

public class Config
{

	private static final File configFile;
	private static boolean enabled;
	private static int num;
	private static boolean harvestSugarCane;
	private static boolean goToItem;
	private static int harvestSugarCaneRange;
	private static boolean tracers;
	private static int glintRed, glintGreen, glintBlue;
	private static int sidebarRed, sidebarGreen, sidebarBlue, sidebarAlpha, sidebarXOffset, sidebarYOffset;
	private static float sidebarSize;
	private static boolean sidebarNumbers, sidebarEnabled;
	private static boolean wingChroma;
	private static int wingRed, wingBlue, wingGreen;


	static
	{
		configFile = new File(MinecraftClient.getInstance().runDirectory, "Config" + File.separator + "Tweaks.json");
	}

	public static void loadConfig() throws IOException
	{
		configFile.getParentFile().mkdirs();
		String content = configFile.exists() ? FileUtils.readFileToString(configFile, "utf-8") : "{}";
		JsonElement jsonElement = (new JsonParser().parse(content));
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		enabled = !jsonObject.has("enabled") || jsonObject.get("enabled").getAsBoolean();
		harvestSugarCane = !jsonObject.has("harvestSugarCane") || jsonObject.get("harvestSugarCane").getAsBoolean();
		goToItem = !jsonObject.has("goToItem") || jsonObject.get("goToItem").getAsBoolean();
		tracers = !jsonObject.has("tracers") || jsonObject.get("tracers").getAsBoolean();

		num = jsonObject.has("num") ? jsonObject.get("num").getAsInt() : 0;
		harvestSugarCaneRange = jsonObject.has("harvestSugarCaneRange") ? jsonObject.get("harvestSugarCaneRange").getAsInt() : 6;

		glintRed = jsonObject.has("glintRed") ? jsonObject.get("glintRed").getAsInt() : 122;
		glintGreen = jsonObject.has("glintGreen") ? jsonObject.get("glintGreen").getAsInt() : 122;
		glintBlue = jsonObject.has("glintBlue") ? jsonObject.get("glintBlue").getAsInt() : 122;

		sidebarRed = jsonObject.has("sidebarRed") ? jsonObject.get("sidebarRed").getAsInt() : 122;
		sidebarGreen = jsonObject.has("sidebarGreen") ? jsonObject.get("sidebarGreen").getAsInt() : 122;
		sidebarBlue = jsonObject.has("sidebarBlue") ? jsonObject.get("sidebarBlue").getAsInt() : 122;
		sidebarAlpha = jsonObject.has("sidebarAlpha") ? jsonObject.get("sidebarAlpha").getAsInt() : 120;
		sidebarXOffset = jsonObject.has("sidebarXOffset") ? jsonObject.get("sidebarXOffset").getAsInt() : 0;
		sidebarYOffset = jsonObject.has("sidebarYOffset") ? jsonObject.get("sidebarYOffset").getAsInt() : 0;
		sidebarSize = jsonObject.has("sidebarSize") ? jsonObject.get("sidebarSize").getAsFloat() : 0.8F;
		sidebarNumbers = !jsonObject.has("sidebarNumbers") || jsonObject.get("sidebarNumbers").getAsBoolean();
		sidebarEnabled = !jsonObject.has("sidebarEnabled") || jsonObject.get("sidebarEnabled").getAsBoolean();

		wingChroma = !jsonObject.has("wingChroma") || jsonObject.get("wingChroma").getAsBoolean();
		wingRed = jsonObject.has("wingRed") ? jsonObject.get("wingRed").getAsInt() : 255;
		wingBlue = jsonObject.has("wingBlue ") ? jsonObject.get("wingBlue ").getAsInt() : 0;
		wingGreen = jsonObject.has("wingGreen") ? jsonObject.get("wingGreen").getAsInt() : 0;

		updateConfig();

	}

	public static void updateConfig()
	{
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("enabled", enabled);
		jsonObject.addProperty("num", num);

		jsonObject.addProperty("harvestSugarCane", harvestSugarCane);
		jsonObject.addProperty("harvestSugarCaneRange", harvestSugarCaneRange);

		jsonObject.addProperty("goToItem", goToItem);
		jsonObject.addProperty("tracers", tracers);

		jsonObject.addProperty("glintRed", glintRed);
		jsonObject.addProperty("glintGreen", glintGreen);
		jsonObject.addProperty("glintBlue", glintBlue);

		jsonObject.addProperty("sidebarRed", sidebarRed);
		jsonObject.addProperty("sidebarGreen", sidebarGreen);
		jsonObject.addProperty("sidebarBlue", sidebarBlue);
		jsonObject.addProperty("sidebarAlpha", sidebarAlpha);
		jsonObject.addProperty("sidebarYOffset", sidebarYOffset);
		jsonObject.addProperty("sidebarXOffset", sidebarXOffset);

		jsonObject.addProperty("sidebarSize", sidebarSize);
		jsonObject.addProperty("sidebarNumbers", sidebarNumbers);
		jsonObject.addProperty("sidebarEnabled", sidebarEnabled);

		jsonObject.addProperty("wingChroma", wingChroma);
		jsonObject.addProperty("wingRed", wingRed);
		jsonObject.addProperty("wingBlue ", wingBlue);
		jsonObject.addProperty("wingGreen", wingGreen);

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

	public static int getNum()
	{
		return num;
	}

	public static void setNum(int num)
	{
		Config.num = num;
		updateConfig();
	}

	public static boolean isHarvestSugarCaneActive()
	{
		return harvestSugarCane;
	}

	public static void setHarvestSugarCane(boolean harvestSugarCane)
	{
		Config.harvestSugarCane = harvestSugarCane;
		updateConfig();
	}

	public static int getHarvestSugarCaneRange()
	{
		return harvestSugarCaneRange;
	}

	public static void setHarvestSugarCaneRange(int harvestSugarCaneRange)
	{
		if (harvestSugarCaneRange >= 7)
			Config.harvestSugarCaneRange = 7;
		else if (Config.harvestSugarCaneRange <= 0)
			Config.harvestSugarCaneRange = 0;
		else
			Config.harvestSugarCaneRange = harvestSugarCaneRange;
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

	public static boolean isGoToItemOn()
	{
		return goToItem;
	}

	public static void setGoToItem(boolean goToItem)
	{
		Config.goToItem = goToItem;
	}

	public static boolean isTracers()
	{
		return tracers;
	}

	public static void setTracers(boolean tracers)
	{
		Config.tracers = tracers;
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
}
*/