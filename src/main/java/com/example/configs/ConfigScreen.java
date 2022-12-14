package com.example.configs;



import com.example.gui.PrecisionSlider;
import com.example.gui.PresetButton;
import com.example.gui.PresetView;
import com.example.gui.VibranceSlider;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends Screen {
	
	private static final Button.OnPress reset = ( press ) -> {
		ClientConfigs.PRECISION.set( ClientConfigs.PRECISION.getDefault() );
		ClientConfigs.VIBRANCE.set( ClientConfigs.VIBRANCE.getDefault() );
		ClientConfigs.PRESET.set( ClientConfigs.PRESET.getDefault() );
	};
	
	
	public ConfigScreen() {
		super(MutableComponent.create(new LiteralContents("ImmersiveLEDs Configuration")));
	}
	
	@Override
	protected void init() {
		
		//X & Y values of the center-point
		int cX = (int) this.width/2;
		int cY = (int) this.height/2;
		
		//Refer to minecraft's native screen's code for guidance

		this.addRenderableWidget( new PresetButton( cX + 75, cY - 20, 130, 20));
		this.addRenderableWidget( new PrecisionSlider( cX + 75, cY + 5, 130, 20));
		this.addRenderableWidget( new VibranceSlider( cX + 75, cY + 30, 130, 20));
		this.addRenderableWidget( new Button( cX + 75, cY + 55, 130, 20, Component.literal("Done"), pressed -> this.onClose()));
		this.addRenderableWidget( new Button( cX + 75, cY + 80, 130, 20, Component.literal("Reset"), reset ));
		//this.addRenderableWidget( new EditBox(this.font, cX - 50, cY + 10, 40, 20, Component.literal("X")));
		//this.addRenderableWidget( new ImageComponent( cX - 200, cY - 20, 400, 200, showcase));
		this.addRenderableWidget( new PresetView( cX - 200, cY - 100, 265, 200));
		
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(poseStack);
		drawCenteredString(poseStack, this.font, this.title.getString(), this.width/2, 8, 0xFFFFFF);
		super.render(poseStack, mouseX, mouseY, partialTicks);
	}
}
