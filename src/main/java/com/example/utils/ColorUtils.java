package com.example.utils;

import com.mojang.logging.LogUtils;

public class ColorUtils {
	//Output Same Order As Input (ex. ABGR ---> ABGR)
		public static int[] splitColors ( int color ) {
			
			String sHex = Integer.toHexString(color);
			String component0 = sHex.substring(0,2);
			String component1 = sHex.substring(2,4);
			String component2 = sHex.substring(4,6);
			String component3 = sHex.substring(6,8);
			
			return new int[] {
					Integer.parseUnsignedInt(component0, 16),
					Integer.parseUnsignedInt(component1, 16),
					Integer.parseUnsignedInt(component2, 16),
					Integer.parseUnsignedInt(component3, 16)
			};
		}	
		
		public static int combineComponents( int[] components ) {
			
			final int LENGTH = 2;//toHexString fails to include leading zeroes, causing problems when attaching the components together
			String hex0 = Integer.toHexString(components[0]);
			hex0 = ColorUtils.addHexPadding( hex0, LENGTH);
			
			String hex1 = Integer.toHexString(components[1]);
			hex1 = ColorUtils.addHexPadding( hex1, LENGTH);
			
			String hex2 = Integer.toHexString(components[2]);
			hex2 = ColorUtils.addHexPadding( hex2, LENGTH);
			
			String hex3 = Integer.toHexString(components[3]);
			hex3 = ColorUtils.addHexPadding( hex3, LENGTH);
			
			String fullHex = hex0 + hex1 + hex2 + hex3;
			LogUtils.getLogger().info("Components:  " + hex0 + ", " + hex1 + ", " + hex2 + ", " + hex3 + " Full Hex: " + fullHex);
			return Integer.parseUnsignedInt(fullHex, 16);
			
		}
		
		public static String addHexPadding( String hex, int padTill ) {
			int padding = padTill - hex.length();
			for ( int i = 0; i < padding; i++ ) { hex = "0" + hex; }
			return hex;
		}
}
