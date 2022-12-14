package com.example.events;

import com.example.client.CustomOverlay;
import com.example.examplemod.ExampleMod;
import com.example.utils.CustomKeyBindings;
import com.example.configs.ConfigScreen;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
	
	@Mod.EventBusSubscriber(modid = ExampleMod.MODID, value = Dist.CLIENT)
	public static class ClientForgeEvents {
		
		private static final Minecraft MINECRAFT = Minecraft.getInstance();

		@SubscribeEvent
		public static void onKeyInput(InputEvent.Key event) {
			if (CustomKeyBindings.CONFIG_KEY.consumeClick()) 
			{ 
				MINECRAFT.setScreen( new ConfigScreen() );
			}
		}
	}
	
	@Mod.EventBusSubscriber(modid = ExampleMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModBusEvents {
		
		@SubscribeEvent
		public static void registerOverlays( RegisterGuiOverlaysEvent event ) {
			//Read the crash report at C:\Users\*\Downloads\Immersive LED's mod\run\crash-reports,
			//Turns out you can't use special characters or capital letters in the ID field.
			event.registerAboveAll("rgba", CustomOverlay.COLOR_WINDOW );
		}
		
		@SubscribeEvent
		public static void onKeyRegister( RegisterKeyMappingsEvent event ) {
			event.register(CustomKeyBindings.CONFIG_KEY);
		}
		
	}
}