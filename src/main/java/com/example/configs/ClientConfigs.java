package com.example.configs;

import net.minecraftforge.common.ForgeConfigSpec;
public class ClientConfigs {
	
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	
	//This is where i can save configuration information. The notable ones are as follows:
	public static final ForgeConfigSpec.ConfigValue<Integer> PRECISION; //How Precise The Color Collection Is
	public static final ForgeConfigSpec.ConfigValue<Integer> PRESET; //Current Preset | Decided to store it as an Integer because storing it as an enum encountered many bugs and erros
	public static final ForgeConfigSpec.ConfigValue<Double> VIBRANCE; //How Prominent The Lead Color Is
	
	static {
		BUILDER.push("Configs for Immersive LEDs");
		PRECISION = BUILDER.comment("How precise the color collection process should be.").define("Precision", 32);
		VIBRANCE = BUILDER.comment("How prominent the leading color channel should appear within the LED.").define("Vibrance", 3.0d);
		PRESET =  BUILDER.comment("The preset to be loaded initially.").define("Preset", 0);
		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
