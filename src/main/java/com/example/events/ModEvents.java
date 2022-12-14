package com.example.events;


//This is apparently important for rendering based on the see through portal code i read
import com.example.examplemod.ExampleMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = ExampleMod.MODID)
public class ModEvents {
	
	//Only have to build to update, not generate run configuration

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		
		Player player = event.player;
		Level world = player.level;
		BlockPos pos = player.blockPosition();
		int brightness = world.getRawBrightness(pos, world.getSkyDarken());
		//LogUtils.getLogger().info("Detected Light Level: " + brightness);
		
	}
	

	
	//@SubscribeEvent
	//public static void onRender( RenderGuiOverlayEvent event ) {}
}
