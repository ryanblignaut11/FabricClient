package theSilverEcho.tweaks.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) @interface IntConfigValue
{
	int maxValue() default 255;

	int minValue() default 0;

	int defaultValue() default 100;
}

@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) @interface FloatConfigValue
{
	float maxValue() default 1F;

	float minValue() default 0F;

	float defaultValue() default 0.5F;
}

@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) @interface BooleanConfigValue
{
	boolean defaultValue() default true;
}
