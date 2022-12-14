package com.example.gui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.example.examplemod.ExampleMod;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;


import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;


//[THIS IS AN UNUSED CLASS, ITS IMPLEMENTATION IS MEANT FOR FUTURE UPDATES]
public class ImageComponent extends ImageButton{
	
	
	public static String fullPath( String path ) {
		return ExampleMod.MODID + ":" + path;
	}
	
	//Had to make assets folder in resources and make a [MODID] folder within it to store the images
	private static final String RESOURCE_PATH = "configimage.png";
	private static final String RELATIVE_PATH = "../src/main/resources/assets/" + ExampleMod.MODID + "/configimage.png";

	
	public ImageComponent(int x, int y, int width, int height, NativeImage img){
		
		/* I wanted to make it so that the image was updated each time the config 
		 * screen if opened but apparently minecraft's resouce locations are 
		 * finalized at the beginning of runtime so once it reads the first image 
		 * saved it cannot be changed until the game closes. This means that the image
		 *  will be unique but will only change each time the game is run */
		
		//Constructor Fomat : ImageButton( x, y, width, height, x Texture Start, y Texture Start, y Texture Diff, ResourceLocation Of Image, Texture Width, Texture Height, onPress, onTooltip, Component/Label )
		super( x, y, width, height, x, y, 0, nativeImageToResourceLocation(img), img.getWidth(), img.getHeight(), OnPress -> {}, NO_TOOLTIP, CommonComponents.EMPTY);		
	}
	
	private static ResourceLocation nativeImageToResourceLocation( NativeImage img ) {
		
		//Relative Path Begins In The 'run' Directory
		LogUtils.getLogger().info("ABS_PATH: " + new File(RELATIVE_PATH).getAbsolutePath());
		try {LogUtils.getLogger().info("CAN_PATH: " + new File(RELATIVE_PATH).getCanonicalPath());}
		catch (IOException e) { LogUtils.getLogger().info("Cannot Print Canonical File Path."); }
		
		//Saving the image relatively difficult but doable
		try { 
			byte[] imgBytes = img.asByteArray();
			InputStream is = new ByteArrayInputStream(imgBytes);
			BufferedImage image = ImageIO.read(is);
			File outputfile = new File(RELATIVE_PATH);
			ImageIO.write(image, "png", outputfile);
			return new ResourceLocation( ExampleMod.MODID, RESOURCE_PATH);
		}
		catch (IOException e) { 
			LogUtils.getLogger().info("Failed To Obtain Resource Location."); 
		}
		
		return null;
	};
	
}
