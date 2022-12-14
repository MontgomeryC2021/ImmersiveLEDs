package com.example.gui;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import com.example.configs.ClientConfigs;

public class PrecisionSlider extends AbstractSliderButton{
	
	private static final int MAXIMUM_VALUE = 100;
	private static final int MINIMUM_VALUE = 0;
	private static final int RANGE = MAXIMUM_VALUE - MINIMUM_VALUE;
	private static final Component DEFAULT_MESSAGE = Component.literal("Color Precision: DEFAULT"); 
	private static final Component MAXIMUM_VALUE_MESSAGE = Component.literal("Color Precision: MAXIMUM"); 
	//private static final Options OPTIONS = new OptionInstance<T>();

	public PrecisionSlider( int x, int y, int width, int height ) {
		super( x, y, width, height, CommonComponents.EMPTY, 64 );
		int configValue = ClientConfigs.PRECISION.get();
		this.value = PrecisionSlider.revertValue( configValue );
		this.applyValue();
	}
	
	@Override
	protected void applyValue() {
		
		int val = PrecisionSlider.convertValue(this.value);  
		Component message = Component.literal( "Color Precision: " + val);
		
		try { ClientConfigs.PRECISION.set( val ); }//Save Converted Value Instead Of 0-1 Value
		catch (Exception e) { /*TODO: handle*/}
		
		if ( val == 32 ) { this.setMessage( DEFAULT_MESSAGE ); }
		else if ( val == MAXIMUM_VALUE ) { this.setMessage( MAXIMUM_VALUE_MESSAGE ); }
		else this.setMessage( message );
	}
	
	//Input between 1 & 0
	private static int convertValue( double v ) {
		return MINIMUM_VALUE + (int) (RANGE * v);
	}
	
	private static double revertValue( int v ) {
		return (double) ( v - MINIMUM_VALUE ) / RANGE;
	}

	@Override
	protected void updateMessage() {
		// TODO Auto-generated method stub
		
	}
	
}
