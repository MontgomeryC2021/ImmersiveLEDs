package com.example.utils;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class CustomKeyBindings {
	public static final String KEY_CATEGORY_IMMERSIVELEDS = "key.category.immersiveleds.immersiveleds";
	public static final String KEY_OPEN_CONFIG = "key.immersiveleds.open_configs";
	public static final KeyMapping CONFIG_KEY = new KeyMapping(KEY_OPEN_CONFIG, KeyConflictContext.IN_GAME,
			InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY_IMMERSIVELEDS);

}
