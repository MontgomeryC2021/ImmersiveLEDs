package com.example.utils;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;

public class ImageUtils {

	//Average of NativeImage
	public static int averageRGBA( int precision, NativeImage img ) {
		
		int avgColor = 0xFFFFFFFF;
		
		int imgWidth = img.getWidth();
		int imgHeight = img.getHeight();
		int cellWidth = (int) imgWidth/precision;
		int cellHeight = (int) imgHeight/precision;
		
		//Cant Let The Cells Be Smaller Then A Pixel
		cellWidth = cellWidth < 1 ? 1 : cellWidth;
		cellHeight = cellHeight < 1 ? 1 : cellHeight;
		
		double rAvg = 0;
		double gAvg = 0;
		double bAvg = 0;
		double aAvg = 0;
		
		int pixels = 0;
		
		for ( int y = 0; y + (int) cellHeight/2 < imgHeight; y += cellHeight ) {
			for ( int x = 0; x + (int) cellWidth/2 < imgWidth; x += cellWidth ) {
				
				int midX = x + (int) cellWidth/2;
				int midY = y + (int) cellHeight/2;
				int color = img.getPixelRGBA( midX, midY);
				
				int[] components = ColorUtils.splitColors(color);
				pixels++;
			
				rAvg += components[3];
				gAvg += components[2];
				bAvg += components[1];
				aAvg += components[0];
				
			}	
		}
		
		
		rAvg /= pixels;
		gAvg /= pixels;
		bAvg /= pixels;
		aAvg /= pixels;
		
		
		int[] components = new int[] { 
			(int) aAvg,
			(int) bAvg <= 255 ? (int) bAvg : 255, 
			(int) gAvg <= 255 ? (int) gAvg : 255,
			(int) rAvg <= 255 ? (int) rAvg : 255
		};
		avgColor = ColorUtils.combineComponents(components);
		return avgColor;
	}
	
	//Average of BuffeedImage
	public static int averageRGBA( int precision, BufferedImage img ) {
		
		int avgColor = 0xFFFFFFFF;
		
		int imgWidth = img.getWidth();
		int imgHeight = img.getHeight();
		int cellWidth = (int) imgWidth/precision;
		int cellHeight = (int) imgHeight/precision;
		
		//Can't Let The Cells Be Smaller Then A Pixel
		cellWidth = cellWidth < 1 ? 1 : cellWidth;
		cellHeight = cellHeight < 1 ? 1 : cellHeight;
		
		double rAvg = 0;
		double gAvg = 0;
		double bAvg = 0;
		double aAvg = 0;
		
		int pixels = 0;
		
		for ( int y = 0; y + (int) cellHeight/2 < imgHeight; y += cellHeight ) {
			for ( int x = 0; x + (int) cellWidth/2 < imgWidth; x += cellWidth ) {
				
				int midX = x + (int) cellWidth/2;
				int midY = y + (int) cellHeight/2;
				int color = img.getRGB( midX, midY);
				
				int[] components = ColorUtils.splitColors(color);
				pixels++;
			
				rAvg += components[3];
				gAvg += components[2];
				bAvg += components[1];
				aAvg += components[0];
				
			}	
		}
		
		
		rAvg /= pixels;
		gAvg /= pixels;
		bAvg /= pixels;
		aAvg /= pixels;
		
		
		int[] components = new int[] { 
			(int) aAvg,
			(int) bAvg <= 255 ? (int) bAvg : 255, 
			(int) gAvg <= 255 ? (int) gAvg : 255,
			(int) rAvg <= 255 ? (int) rAvg : 255
		};
		
		avgColor = ColorUtils.combineComponents(components);
		return avgColor;
	}
	
	public static BufferedImage cropFromPreset( Presets.PRESET preset, BufferedImage img ) {
		PresetData data = Presets.retrievePresetData( img, preset);
		BufferedImage sub = img.getSubimage( data.x, data.y, data.width, data.height);
		return sub;	
	}
	
	public static NativeImage cropFromPreset( Presets.PRESET preset, NativeImage img ) {
		PresetData data = Presets.retrievePresetData( img, preset);
		LogUtils.getLogger().info( "Preset: " + preset.toString() + " X: " + data.x + " Y: " + data.y + " Width: " + data.width + " Height: " + data.height );
		NativeImage sub = ImageUtils.crop( data.x, data.y, data.width, data.height, img);
		return sub;	
	}
	
	public static NativeImage crop( int x, int y, int width, int height, NativeImage img ) {
		NativeImage output = new NativeImage( width, height, false);//Copy process of screenshot
		img.resizeSubRectTo(x, y, width, height, output);//Assuming it puts it into the 'output' image
		return output;
	}
	
	public static BufferedImage toBufferedImage( NativeImage img ) throws IOException {
		byte[] imgBytes = img.asByteArray();
		InputStream is = new ByteArrayInputStream(imgBytes);
		BufferedImage image = ImageIO.read(is);
		return image;
	}
}
