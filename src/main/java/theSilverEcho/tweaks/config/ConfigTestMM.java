package theSilverEcho.tweaks.config;

import net.minecraft.client.util.math.Rotation3;

import java.lang.annotation.*;
import java.lang.reflect.Field;

public class ConfigTestMM
{

	@IntConfigValue(maxValue = 10,minValue = 1) public static int wingRed = 2000, wingBlue, wingGreen;

	public static void main(String[] args) throws IllegalAccessException
	{
		float w = 0;
		for (w = 0; w <= 360; w += 0.1)
		{
			System.out.println(w);
		}
		/*for (Field declaredField : ConfigTestMM.class.getDeclaredFields())
		{

			for (Annotation declaredAnnotation : declaredField.getDeclaredAnnotations())
			{
				System.out.println(declaredAnnotation instanceof IntConfigValue);
			}
			*//*if (annotation != null)
			{
				System.out.println(declaredField.getInt(declaredField.getName()));
				System.out.println("name: " + annotation.maxValue());
				System.out.println("value: " + annotation.minValue());
			}*//*
		}*/
	}

}

