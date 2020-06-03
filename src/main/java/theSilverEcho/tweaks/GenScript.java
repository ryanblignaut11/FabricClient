package theSilverEcho.tweaks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenScript
{
	private static final String toolType = "sword";
	private static final String pathName = "run\\resourcepacks\\enchants\\assets\\tweaks\\models\\item\\" + toolType + "\\";

	public static void main(String[] args) throws IOException
	{
		HashMap<String, Integer> words = new HashMap<>();
		words.put("sharpness", 11);
		words.put("knockback", 14);
		words.put("fire_aspect", 15);
		words.put("looting", 16);
		words.put("sweeping", 17);
		words.put("unbreaking", 20);
		words.put("mending", 35);

		List<String> words1 = new ArrayList<>();
		//=new String[] { "sharpness", "knockback", "fire_aspect", "looting", "sweeping", "unbreaking", "mending" };

		words.forEach((s, integer) -> System.out.println(words1.add(s)));

		for (int i = 0; i < words1.size(); i++)
		{
			//			System.out.println(words.entrySet().toArray()[i]);
			generateJson(words1.get(i));

			System.out.println("{\n" + "\t\t\t\"predicate\": {\n" + "\t\t\t\t\"tweaks:enchant\": " + words
					.get(words1.get(i)) + "\n" + "\t\t\t},\n" + "\t\t\t\"model\": \"tweaks:item/"+toolType+"/" + words1.get(i) + "\"\n" + "\t\t},");

			//			generateJson(words.get(i));
			for (int j = i + 1; j < words.size(); j++)
			{
				//				int val = Integer.parseInt(words.get(words1.get(i)))+Integer.parseInt(words.get(words1.get(j)));
				int x1 = words.get(words1.get(i)) + words.get(words1.get(j));
				String s = "" + x1;
				System.out.println(
						"{\n" + "\t\t\t\"predicate\": {\n" + "\t\t\t\t\"tweaks:enchant\": " + s + "\t\t\t},\n" + "\t\t\t\"model\": \"tweaks:item/"+toolType+"/" + words1
								.get(i) + words1.get(j) + "\"\n" + "\t\t},");
				generateJson(words1.get(i), words1.get(j));

				//				generateJson(words[i], words[j]);
				for (int k = j + 1; k < words.size(); k++)
				{
					int x2 = words.get(words1.get(i)) + words.get(words1.get(j)) + words.get(words1.get(k));
					String t = "" + x2;
					System.out.println(
							"{\n" + "\t\t\t\"predicate\": {\n" + "\t\t\t\t\"tweaks:enchant\": " + t + "\t\t\t},\n" + "\t\t\t\"model\": \"tweaks:item/"+toolType+"/" + words1
									.get(i) + words1.get(j) + words1.get(k) + "\"\n" + "\t\t},");
					generateJson(words1.get(i), words1.get(j), words1.get(k));
					//					generateJson(words[i], words[j], words[k]);
				}
			}
		}
	}

	private static void generateJson(String... names)
	{
		StringBuilder fullName = new StringBuilder();
		for (String s : names)
			fullName.append(s);
		String path = pathName; //"C:\\Users\\Ryan Blignaut\\Desktop\\Minecraft Modding\\1.15.1\\run\\resourcepacks\\test\\assets\\tweaks\\models\\item\\pickaxe\\";
		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
		JsonObject jsonObject = new JsonObject();
		JsonObject textures = new JsonObject();
		textures.addProperty("layer0", "minecraft:item/diamond_"+toolType);
		textures.addProperty("layer1", "tweaks:item/"+toolType+"/mod_" + names[0]);
		if (names.length >= 2)
		{
			textures.addProperty("layer2", "tweaks:item/"+toolType+"/mod_" + names[1]);
		}
		if (names.length >= 3)
		{
			textures.addProperty("layer3", "tweaks:item/"+toolType+"/mod_" + names[2]);
		}
		jsonObject.addProperty("parent", "minecraft:item/handheld");
		jsonObject.add("textures", textures);

		try
		{
			File file = new File(path + fullName + ".json");
			boolean newFile = file.createNewFile();
			//			System.out.println(newFile);
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.close();

			JsonWriter jsonWriter = gson.newJsonWriter(new FileWriter(file));
			String s = gson.toJson(jsonObject);

			jsonWriter.jsonValue(s);
			jsonWriter.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
