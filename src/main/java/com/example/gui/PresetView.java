package com.example.gui;

import com.example.configs.ClientConfigs;
import com.example.examplemod.ExampleMod;
import com.example.utils.Presets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;

public class PresetView extends ImageButton {
	
	private int preset = 0;
	
	public PresetView( int x, int y, int width, int height) {
		super( x, y, width, height, 0, 0, 0, getCurrentPreview(), width, height, OnPress -> {}, NO_TOOLTIP, CommonComponents.EMPTY);		
	}

	public static ResourceLocation getCurrentPreview() {
		if ( !ClientConfigs.SPEC.isLoaded() ) {return new ResourceLocation( ExampleMod.MODID, "fullscreen.png" ); }
		try { return new ResourceLocation( ExampleMod.MODID, ClientConfigs.PRESET.get().toString().toLowerCase() + ".png" ); }//[CONFIGURABLE]
		catch ( Exception e ) { return new ResourceLocation( ExampleMod.MODID, "fullscreen.png" ); }
	}
	
	@Override
	public void renderButton(PoseStack stack, int p_94283_, int p_94284_, float p_94285_) {
		
		try { this.preset = ClientConfigs.PRESET.get(); }//[CONFIGURABLE]
		catch ( Exception e ) { /*Currently Unhandled*/ }
		ResourceLocation rl = new ResourceLocation( ExampleMod.MODID, Presets.presetByIndex( this.preset ).toString().toLowerCase() + ".png" );      
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, rl);
		RenderSystem.enableDepthTest();
		blit( stack, this.x, this.y, 0, 0, this.width, this.height, this.width, this.height);
	}
}
