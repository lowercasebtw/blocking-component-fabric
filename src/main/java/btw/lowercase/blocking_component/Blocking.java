package btw.lowercase.blocking_component;

import btw.lowercase.blocking_component.component.Components;
import net.fabricmc.api.ModInitializer;

public class Blocking implements ModInitializer {
	@Override
	public void onInitialize() {
		Components.initalize();
	}
}