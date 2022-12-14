package com.example.client;

import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.gui.GuiComponent;

import com.example.configs.ClientConfigs;
import com.example.examplemod.ExampleMod;
import com.example.utils.ColorUtils;
import com.example.utils.ImageUtils;
import com.example.utils.Presets;

public class CustomOverlay {
	
	//Render Constants
	private static final int MAX_TICK = 200;
	private static int tick = MAX_TICK;
	private static final RenderTarget SCREEN =  Minecraft.getInstance().getMainRenderTarget();
	
	//Resources | For UI
	private static final String RESOURCE_PATH = "sheathe.png";
	private static final ResourceLocation UI = new ResourceLocation( ExampleMod.MODID, RESOURCE_PATH );
	
	//Track Preset
	private static Presets.PRESET preset = Presets.PRESET.FULLSCREEN;
	
	public static final IGuiOverlay COLOR_WINDOW = ((gui, poseStack, partialTick, width, height) -> {
		
		int color = 0xFFFFFFFF;
		int centerX = width/2;

		//Run only every [MAX_TICK] ticks
		if ( tick++ >= MAX_TICK  ) 
		{
			//Color Retrieval
			NativeImage img = Screenshot.takeScreenshot(SCREEN);
			try { preset = Presets.presetByIndex( ClientConfigs.PRESET.get() ); }//[CONFIGURABLE]
			catch ( Exception e ) { /*Currently Unhandled*/ }//No need to do anything as the preset will just remain unchanged
			NativeImage i = ImageUtils.cropFromPreset( preset, img );
			img.close(); //Close the image so that the memory allocation doesn't stack and cause crashes
			
			
			//Precision Defined By Configs
			int PRECISION = ClientConfigs.PRECISION.get();//[CONFIGURABLE]
			color = ImageUtils.averageRGBA( PRECISION, i);
			i.close();//Close this one too | We don't need more crashing again
			LogUtils.getLogger().info("ORIGINAL_DIMENSIONS: " + img.getWidth() + "x" + img.getHeight());
			
			//Color hexadecimal is formatted as ABGR (ex. ff336b44)
			//Must be converted from ABGR ---> to ARGB (ex. ff446b33)
			String hex = Integer.toHexString(color);
			hex = ColorUtils.addHexPadding( hex, 8);
			String formatHex = "ff" + hex.substring(6,8) + hex.substring(4,6) + hex.substring(2,4);
		
			LogUtils.getLogger().info("ORIGINAL_VALUE: " + color);
			LogUtils.getLogger().info("ORIGINAL_HEX: " + hex);
			LogUtils.getLogger().info("CORRECTED_HEX: " + formatHex);
			
			int trueColor = Integer.parseUnsignedInt(formatHex, 16);//Revert from fixed string back to integer
			String h = Integer.toHexString( trueColor );
			boolean isCorrect = h.equals(formatHex);
			String isCorrectString = isCorrect ? "TRUE" : "FALSE";
			LogUtils.getLogger().info("REREAD_HEX: " + h + " | CORRECT: " + isCorrectString);
			
			color = trueColor;//Update With Corrected Coloring
	
		}
		
		
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor( 1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture( 0, UI);
		GuiComponent.blit(poseStack, centerX + 91, height - 22, 0,0, 8, 22, 8, 22);//Draw UI Element
		GuiComponent.fill(poseStack, centerX + 91, height - 19, centerX + 96, height - 3, color);//Draw Color
		//GuiComponent.fill(poseStack, centerX - 91, height - 40, centerX - 8, height - 30, color);
		
		
		//Vibrance Defined By Configs
		double VIBRANCE = ClientConfigs.VIBRANCE.get();//[CONFIGURABLE]
		
		//Setting LED Color | This math ensures that the strongest color is the most prominent in the LED by scaling the other two values down relative to the strength of the strongest component
		int[] ARGB = ColorUtils.splitColors(color);
		int highest = Math.max( Math.max( ARGB[1], ARGB[2]), ARGB[3]);
		long relativeStrength = (int) ( Math.pow( highest, (double) VIBRANCE ) );
		int r = (int) (( Math.pow( (double) ARGB[1], (double) VIBRANCE )/relativeStrength) * highest)  ; 
		int g = (int) (( Math.pow( (double) ARGB[2], (double) VIBRANCE )/relativeStrength) * highest) ;
		int b = (int) (( Math.pow( (double) ARGB[3], (double) VIBRANCE )/relativeStrength) * highest) ;
		
		//Sending Color Data
		String uriString = "http://10.0.0.112:80/?r" + r + "g" + g + "b" + b + "&";// 'http://' part is important, it is apparently a 'scheme name'
		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder( URI.create(uriString) );
		HttpRequest request = requestBuilder.build();
		HttpClient client = HttpClient.newHttpClient();
		client.sendAsync( request, BodyHandlers.ofString() );
		LogUtils.getLogger().info("REQUEST_SENT_TO: " + uriString + "\n");
		
		//GuiComponent.blit(poseStack, x - 94, y - 54, 0,0,12,12,12,12);
	
		
	});
	
}
