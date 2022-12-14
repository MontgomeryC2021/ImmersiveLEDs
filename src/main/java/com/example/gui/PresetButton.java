package com.example.gui;

import com.example.configs.ClientConfigs;
import com.example.utils.Presets;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class PresetButton extends Button{
	
	
	 
	//Its requiring me to pass in a static onPress to the super
	//Since i need it to be non static ill just override it in this class
	private static final Button.OnPress onPress = pressed -> {};
	
	//INSTANCES
	private int state = ClientConfigs.PRESET.get();//[CONFIGURABLE];
	
	public PresetButton(int x, int y, int width, int height) {
		super(
			x, 
			y, 
			width, 
			height, 
			Component.literal("Preset: " + Presets.presetByIndex( ClientConfigs.PRESET.get() ).toString()), 
			onPress
		);
	}
	
	public int getState(){
		return this.state;
	}
	
	@Override
	public void onPress() {
		this.state = ++this.state < Presets.getCount() ? this.state : 0;
		
		try{ ClientConfigs.PRESET.set( this.state ); }//Save New Configuration
		catch (Exception e) { /*TODO: handle*/}
		
		this.refresh();
	}
	
	public void refresh() {
		Component mes = Component.literal("Preset: " + Presets.presetByIndex(state).toString());
		this.setMessage(mes);
	}
	
}
