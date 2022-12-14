package com.example.gui;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import com.example.configs.ClientConfigs;

public class VibranceSlider extends AbstractSliderButton{
	
	private static final int MAXIMUM_VALUE = 4;
	private static final int MINIMUM_VALUE = 1;
	private static final int RANGE = MAXIMUM_VALUE - MINIMUM_VALUE;
	private static final Component DEFAULT_MESSAGE = Component.literal("Color Vibrance: DEFAULT"); 
	private static final Component MAXIMUM_VALUE_MESSAGE = Component.literal("Color Vibrance: MAXIMUM"); 
	//private static final Options OPTIONS = new OptionInstance<T>();

	public VibranceSlider( int x, int y, int width, int height ) {
		super( x, y, width, height, CommonComponents.EMPTY, 3 );
		double configValue = ClientConfigs.VIBRANCE.get();
		this.value = VibranceSlider.revertValue( configValue );
		this.applyValue();
	}
	
	@Override
	protected void applyValue() {
		// TODO Auto-generated method stub
		double val = VibranceSlider.convertValue(this.value);  
		Component message = Component.literal( "Color Vibrance: " + ( (double) Math.round(val * 100) / 100));
		
		try { ClientConfigs.VIBRANCE.set( val ); }//Save Converted Value Instead Of 0-1 Value
		catch (Exception e) { /*TODO: handle*/}
		
		if ( val == 3 ) { this.setMessage( DEFAULT_MESSAGE ); }
		else if ( val == MAXIMUM_VALUE ) { this.setMessage( MAXIMUM_VALUE_MESSAGE ); }
		else this.setMessage( message );
	}
	
	//Input between 1 & 0
	private static double convertValue( double v ) {
		return MINIMUM_VALUE + (double) (RANGE * v);
	}
	
	private static double revertValue( double v ) {
		return (double) ( v - MINIMUM_VALUE ) / RANGE;
	}

	@Override
	protected void updateMessage() {
		// TODO Auto-generated method stub
		
	}
	
}
