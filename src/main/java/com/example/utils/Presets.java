package com.example.utils;

import java.awt.image.BufferedImage;

import com.mojang.blaze3d.platform.NativeImage;

public class Presets {

	public static enum PRESET {
		FULLSCREEN,
		HEART,
		EXPERIENCE,
		UPPER,
		ITEMS,
	}
	
	
	public static PRESET presetByIndex( int index ) {
		return PRESET.values()[index];
	}
	
	public static int getCount() {
		return PRESET.values().length;
	}
	
	//BufferedImage
	public static PresetData retrievePresetData( BufferedImage img, PRESET preset ) {
		return new PresetData( img.getWidth(), img.getHeight(),  preset );
	}

	//NativeImage 
	public static PresetData retrievePresetData( NativeImage img, PRESET preset ) {
		return new PresetData( img.getWidth(), img.getHeight(),  preset );
	}
}

class PresetData {
	
	public int x;
	public int y;
	public int width;
	public int height;
	
	//NativeImage Constructor
	public PresetData( int width, int height, Presets.PRESET preset ) {
		switch ( preset ) {
			case HEART: 
			{
				this.x = width/2 - 180;
				this.y = height - 71;
				this.width = 160;
				this.height = 2;
				break;//I always forget the break
			}
			case EXPERIENCE: 
			{
				this.x = width/2 - 180;
				this.y = height - 55;
				this.width = 360;
				this.height = 5;
				break;
			}
			case UPPER: 
			{
				this.x = 0;
				this.y = 0;
				this.width = width;
				this.height = height - 70;
				break;
			}
			case ITEMS: 
			{
				this.x = width/2 - 180;
				this.y = height - 50;
				this.width = 100;
				this.height = 40;
				break;
			}
			case FULLSCREEN: 
			{
				this.x = 0;
				this.y = 0;
				this.width = width;
				this.height = height;
				break;
			}
			default:
			{
				this.x = 0;
				this.y = 0;
				this.width = width;
				this.height = height;
				break;
			}
		}
	}
}
